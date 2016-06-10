package jp.co.transcosmos.dm3.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import jp.co.transcosmos.dm3.batch.BatchRunner.TaskQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;

public class SimpleTaskDefinition<T> implements TaskDefinition<T>, BeanNameAware {
    private final Log log = LogFactory.getLog(SimpleTaskDefinition.class);

    private Callable<T> task;
    private TaskDefinition<T> taskDefinition;
    private Iterable<TaskDefinition<?>> onCompletionTasks;
    private OnCompletionExecuteMode onCompletionExecuteMode = OnCompletionExecuteMode.SERIAL;
    private String beanName;
    private boolean suppressExceptions = false;
    private boolean transactional = false;
    
    public SimpleTaskDefinition() {}
    
    public static <X> SimpleTaskDefinition<X> newParallelNoWaitTaskset(Callable<X> task, TaskDefinition<?>... onCompletionTasks) {
        SimpleTaskDefinition<X> taskSet = new SimpleTaskDefinition<X>();
        taskSet.setTask(task);
        if (onCompletionTasks != null && onCompletionTasks.length > 0) {
            taskSet.setOnCompletionTasks(Arrays.asList(onCompletionTasks));
        }
        taskSet.onCompletionExecuteMode = OnCompletionExecuteMode.PARALLEL_NO_WAIT;
        return taskSet;
    }
    
    public static <X> SimpleTaskDefinition<X> newParallelWaitTaskset(Callable<X> task, TaskDefinition<?>... onCompletionTasks) {
        SimpleTaskDefinition<X> taskSet = new SimpleTaskDefinition<X>();
        taskSet.setTask(task);
        if (onCompletionTasks != null && onCompletionTasks.length > 0) {
            taskSet.setOnCompletionTasks(Arrays.asList(onCompletionTasks));
        }
        taskSet.onCompletionExecuteMode = OnCompletionExecuteMode.PARALLEL_WAIT;
        return taskSet;
    }
    
    public static <X> SimpleTaskDefinition<X> newSerialTaskset(Callable<X> task, TaskDefinition<?>... onCompletionTasks) {
        SimpleTaskDefinition<X> taskSet = new SimpleTaskDefinition<X>();
        taskSet.setTask(task);
        if (onCompletionTasks != null && onCompletionTasks.length > 0) {
            taskSet.setOnCompletionTasks(Arrays.asList(onCompletionTasks));
        }
        taskSet.onCompletionExecuteMode = OnCompletionExecuteMode.SERIAL;
        return taskSet;
    }
    
    public void setTask(Callable<T> task) {
        this.task = task;
    }

    public void setTaskDefinition(TaskDefinition<T> taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public void setOnCompletionTask(Object onCompletionTask) {
        setOnCompletionTasks(Arrays.asList(new Object[] {onCompletionTask}));
    }
    
    public void setOnCompletionTasks(Iterable<?> onCompletionTasks) {
        if (onCompletionTasks != null) {
            List<TaskDefinition<?>> out = new ArrayList<TaskDefinition<?>>();
            for (final Object o : onCompletionTasks) {
                if (o instanceof TaskDefinition<?>) {
                    out.add((TaskDefinition<?>) o);
                } else if (o instanceof Callable<?>) {
                    out.add(newSerialTaskset((Callable<?>) o));
                } else if (o instanceof Runnable) {
                    out.add(newSerialTaskset(new Callable<Void>() {
                        public Void call() throws Exception {
                            ((Runnable) o).run();
                            return null;
                        }
                    }));
                } else {
                    throw new IllegalArgumentException("Unknown task type: " + o.toString());
                }
            }
            setOnCompletionTaskDefinitions(out);
        }
    }

    public void setOnCompletionTaskDefinitions(Iterable<TaskDefinition<?>> onCompletionTasks) {
        this.onCompletionTasks = onCompletionTasks;
    }

    @Override
    public T executeTask(TaskQueue taskQueue) throws Exception {
        Callable<T> localTask = this.task;
        if (localTask == null && this.taskDefinition != null) {
            localTask = TaskDefinitionCallable.newTask(this.taskDefinition, taskQueue);
        }
        if (localTask != null) {
            log.debug("Run task: " + localTask);
            return localTask.call();
        }
        return null;
    }

    @Override
    public Iterable<TaskDefinition<?>> getOnCompletionTaskQueue() {
        return onCompletionTasks;
    }
    
    public OnCompletionExecuteMode getOnCompleteExecutionMode() {
        return onCompletionExecuteMode;
    }

    public void setOnCompletionExecuteMode(
            OnCompletionExecuteMode onCompletionExecuteMode) {
        this.onCompletionExecuteMode = onCompletionExecuteMode;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public boolean isSuppressExceptions() {
        return suppressExceptions;
    }

    public void setSuppressExceptions(boolean suppressExceptions) {
        this.suppressExceptions = suppressExceptions;
    }

    public boolean isTransactional() {
        return transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    @Override
    public String toString() {
        if (beanName != null) {
            return beanName;
        }
        return ((task != null ? "Task=" + task  + " " : "") + 
                (onCompletionTasks != null && onCompletionTasks.iterator().hasNext() 
                    ? "OnCompleteMode(" + this.onCompletionExecuteMode + ")" +
                    "=" + onCompletionTasks : "")).trim();
    }

}
