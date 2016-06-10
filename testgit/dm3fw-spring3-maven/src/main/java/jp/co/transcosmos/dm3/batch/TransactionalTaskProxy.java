package jp.co.transcosmos.dm3.batch;

import jp.co.transcosmos.dm3.batch.BatchRunner.TaskQueue;
import jp.co.transcosmos.dm3.transaction.SingleConnectionDataSource;

import org.springframework.beans.factory.BeanNameAware;

public class TransactionalTaskProxy<T> implements TaskDefinition<T>, BeanNameAware {

    private SingleConnectionDataSource dataSource;
    private TaskDefinition<T> taskDefinition;
    private String beanName;
    
    @Override
    public T executeTask(TaskQueue taskQueue) throws Exception {
        boolean failed = false;
        try {
            return TaskDefinitionCallable.newTask(taskDefinition, taskQueue).call();
        } catch (Exception err) {
            failed = true;
            throw err;
        } catch (Throwable err) {
            failed = true;
            throw new RuntimeException("Error caught for rollback", err);
        } finally {
            if (dataSource != null) {
                dataSource.closeConnection(failed);
            }
        }
    }

    @Override
    public OnCompletionExecuteMode getOnCompleteExecutionMode() {
        return OnCompletionExecuteMode.SERIAL;
    }

    @Override
    public Iterable<TaskDefinition<?>> getOnCompletionTaskQueue() {
        return null;
    }

    @Override
    public boolean isSuppressExceptions() {
        return false;
    }

    public void setDataSource(SingleConnectionDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setTaskDefinition(TaskDefinition<T> taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return beanName;
    }

}
