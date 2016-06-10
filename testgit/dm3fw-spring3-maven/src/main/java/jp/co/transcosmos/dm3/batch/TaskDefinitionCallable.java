package jp.co.transcosmos.dm3.batch;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import jp.co.transcosmos.dm3.batch.BatchRunner.TaskQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Internal utility class for wrapping the call to the task so it has the necessary local 
 * variables e.g. the executor.
 */
public class TaskDefinitionCallable<T> implements Callable<T> {
    private final Log log = LogFactory.getLog(TaskDefinitionCallable.class);

    private TaskDefinition<T> taskDefinition;
    private TaskQueue taskQueue;
        
    @Override
    public T call() throws Exception {
        log.debug("Starting: " + taskDefinition);
        T ret = taskDefinition.executeTask(taskQueue);
        Iterable<TaskDefinition<?>> onCompletes = taskDefinition.getOnCompletionTaskQueue();
        if (onCompletes != null) {
            log.debug("Executing on completes: " + onCompletes);
            switch (taskDefinition.getOnCompleteExecutionMode()) {
            case SERIAL: 
                doSerial(onCompletes);
                break;
                
            case PARALLEL_NO_WAIT: 
                for (final TaskDefinition<?> childTD : onCompletes) {
                    taskQueue.addTask(childTD);
                }
                break;
                
            case PARALLEL_WAIT: 
                if (!taskQueue.invokeAllAndWait(onCompletes)) {
                    log.warn("WARNING: Thread starvation possible, executing invokeAllAndWait serially instead");
                    doSerial(onCompletes);
                }
                break;
            }
        }
        log.debug("Completed: " + taskDefinition);
        return ret;
    }
    
    private void doSerial(Iterable<TaskDefinition<?>> onCompletes) throws Exception {
        for (TaskDefinition<?> childTD : onCompletes) {
            try {
                newTask(childTD, taskQueue).call();
            } catch (ExecutionException err) {
                if (childTD.isSuppressExceptions()) {
                    log.error("Error suppressed in oncomplete", err);
                }
                throw err;
            }
        }
    }

    @Override
    public String toString() {
        return "TaskDefinitionCallable [" + taskDefinition + "]";
    }

    /**
     * Make a new TaskAdder to submit into the queue as a java.util.Callable
     */
    public static <X> TaskDefinitionCallable<X> newTask(TaskDefinition<X> td, TaskQueue taskQueue) {
        TaskDefinitionCallable<X> me = new TaskDefinitionCallable<X>();
        me.taskDefinition = td;
        me.taskQueue = taskQueue;
        return me;
    }
}
