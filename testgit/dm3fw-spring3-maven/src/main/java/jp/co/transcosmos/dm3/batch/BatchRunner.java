package jp.co.transcosmos.dm3.batch;

import java.io.File;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class BatchRunner implements BeanFactoryAware, Callable<Void> {
    private final Log log = LogFactory.getLog(BatchRunner.class);

    private Integer threadCount = 1;
    private BeanFactory beanFactory;
    private String masterTaskBeanName;

    @Override
    public Void call() throws Exception {
        if (threadCount == null) {
            threadCount = 1;
        }
        if (beanFactory == null) {
            throw new IllegalArgumentException("No beanFactory supplied");
        } else if (masterTaskBeanName == null) {
            throw new IllegalArgumentException("No taskBeanName supplied");
        }
        
        long startTime = System.currentTimeMillis();
        log.info("Starting task execution at " + new Date());
        
        TaskDefinition<?> masterTask = (TaskDefinition<?>) beanFactory.getBean(masterTaskBeanName);
        TaskQueue taskQueue = new TaskQueue(threadCount);        
        taskQueue.addTask(masterTask);
        taskQueue.waitForEmptyQueue(startTime);
        
        long endTime = System.currentTimeMillis();
        log.info("Completed task execution at " + new Date(endTime) + " in " + (endTime - startTime) + "ms");
        return null;
    }
    
    class TaskQueue {
        private ExecutorService executor;
        private CompletionService<Void> completionService;
        private int waitCount = 0;
        private int currentInvokeAndWaitCount = 0;
        
        public TaskQueue(int threadCount) {
            this.executor = Executors.newFixedThreadPool(threadCount);
            this.completionService = new ExecutorCompletionService<Void>(executor);
        }
        
        public <X> void addTask(final TaskDefinition<X> taskDefinition) {
            log.debug("Queueing new task: " + taskDefinition);
            synchronized (this) {
                waitCount++;
            }
            final Callable<X> me = TaskDefinitionCallable.newTask(taskDefinition, this);
            completionService.submit(new Callable<Void>() {
                public Void call() throws Exception {
                    me.call();
                    return null;
                }
            });
        }
        
        public boolean invokeAllAndWait(final Iterable<TaskDefinition<?>> taskDefinitions) throws InterruptedException, ExecutionException {
            synchronized (this) {
                if (this.currentInvokeAndWaitCount >= threadCount - 1) {
                    return false;
                }
                this.currentInvokeAndWaitCount++;
            }
            try {
                CompletionService<Void> childCompletionService = new ExecutorCompletionService<Void>(executor);
                int childWaitCount = 0;
                long startTime = System.currentTimeMillis();
                for (TaskDefinition<?> td : taskDefinitions) {
                    log.debug("Queueing new task: " + td);
                    final Callable<?> me = TaskDefinitionCallable.newTask(td, this);
                    childCompletionService.submit(new Callable<Void>() {
                        public Void call() throws Exception {
                            me.call();
                            return null;
                        }
                    });
                    childWaitCount++;
                }
                
                while (childWaitCount > 0) {
                    log.debug("Current child wait queue size: " + childWaitCount);
                    Future<Void> fut = childCompletionService.poll(1, TimeUnit.MINUTES);
                    if (fut == null) {
                        log.debug("TaskQueue child has waited for completion for " + 
                                ((System.currentTimeMillis() - startTime) / 60000L) + " minutes");
                    } else {
                        childWaitCount--;
                        fut.get();
                    }
                }
                return true;
            } finally {
                synchronized (this) {
                    this.currentInvokeAndWaitCount--;
                }
            }
        }
        
        /**
         * Logs wait messages while waiting for shutdown
         * @param executor
         * @param startTime
         */
        public void waitForEmptyQueue(long startTime) throws InterruptedException, ExecutionException {
            int localWaitCount;
            synchronized (this) {
                localWaitCount = waitCount;
            }
            while (localWaitCount > 0) {
                log.debug("Current execution queue size: " + localWaitCount);
                Future<Void> fut = completionService.poll(1, TimeUnit.MINUTES);
                if (fut == null) {
                    log.debug("TaskQueue has waited for completion for " + 
                            ((System.currentTimeMillis() - startTime) / 60000L) + " minutes");
                } else {
                    synchronized (this) {
                        waitCount--;
                        localWaitCount = waitCount;
                    }
                    fut.get();
                }
            }
            executor.shutdown();
        }
    }
    
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setMasterTaskBeanName(String masterTaskBeanName) {
        this.masterTaskBeanName = masterTaskBeanName;
    }

    /**
     * Main method. Builds a spring context, configures with an optional properties file and optionally sets a 
     * worker thread count
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File log4jConfig = new File(System.getProperty("log4jConfig", "log4j.xml"));
        if (log4jConfig.isFile()) {
            DOMConfigurator.configure(log4jConfig.getAbsolutePath());
        } else {
            BasicConfigurator.configure();
        }
        
        BatchRunner runner = new BatchRunner();
        
        File springContextFile = new File(System.getProperty("dm3fw.batch.context", "batchContext.xml"));
        String props = System.getProperty("dm3fw.batch.props", "");
        File propsFile = props.equals("") ? null : new File(props);
        String masterTaskBeanName = System.getProperty("dm3fw.batch.masterTaskBeanName", "taskQueue");
        String threadCountStr = System.getProperty("dm3fw.batch.threadCount", "");
        Integer threadCount = threadCountStr.equals("") ? null : Integer.valueOf(threadCountStr);

        runner.log.info("Args: springContextFile = " + springContextFile + ", props=" + props + 
                ", masterTaskBeanName=" + masterTaskBeanName + ", threadCount=" + threadCount);
        
        if (!springContextFile.isFile()) {
            runner.log.info("Usage (defaults in brackets): " +
                    "java -Ddm3fw.batch.context=<spring context xml file (batchContext.xml)> " +
                    "-Ddm3fw.batch.props=<props file (unused)> -Ddm3fw.batch.threadCount=<threads (1)> " +
                    "-Ddm3fw.batch.masterTaskBeanName=<masterTaskBeanName (taskQueue)> " +
                    "jp.co.transcosmos.dm3.batch.BatchRunner");
        }

        ConfigurableListableBeanFactory context = new XmlBeanFactory(new FileSystemResource(springContextFile));
        if (propsFile != null && propsFile.isFile()) {
            PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
            configurer.setLocation(new FileSystemResource(propsFile));
            configurer.postProcessBeanFactory(context);
        } else {
            runner.log.warn("WARNING: No properties file supplied, skipping property configuration");
        }
        
        runner.setBeanFactory(context);
        if (threadCount != null) {
            runner.setThreadCount(threadCount);
        }
        runner.setMasterTaskBeanName(masterTaskBeanName);
        runner.call();
    }   
}
