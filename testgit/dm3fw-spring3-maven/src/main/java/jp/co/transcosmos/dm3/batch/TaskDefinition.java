package jp.co.transcosmos.dm3.batch;

import jp.co.transcosmos.dm3.batch.BatchRunner.TaskQueue;

public interface TaskDefinition<T> {

    public T executeTask(TaskQueue taskQueue) throws Exception ;
    
    public Iterable<TaskDefinition<?>> getOnCompletionTaskQueue();
    
    public OnCompletionExecuteMode getOnCompleteExecutionMode();
    
    public boolean isSuppressExceptions();
    
    public enum OnCompletionExecuteMode {
        SERIAL, PARALLEL_NO_WAIT, PARALLEL_WAIT;
    }
}
