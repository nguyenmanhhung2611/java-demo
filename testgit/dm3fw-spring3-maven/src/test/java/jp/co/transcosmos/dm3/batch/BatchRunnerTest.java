package jp.co.transcosmos.dm3.batch;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchRunnerTest extends TestCase {
    
    static {
        BasicConfigurator.configure();
    }

    public void testSimple() throws Exception {
        BatchRunner br = new BatchRunner();
        br.setBeanFactory(new ClassPathXmlApplicationContext("jp/co/transcosmos/dm3/batch/batchContext.xml"));
        br.setMasterTaskBeanName("taskQueue");
        br.setThreadCount(1);
        br.call();
    }

    public void testParallelWait() throws Exception {
        BatchRunner br = new BatchRunner();
        br.setBeanFactory(new ClassPathXmlApplicationContext("jp/co/transcosmos/dm3/batch/batchContext.xml"));
        br.setMasterTaskBeanName("test2TaskQueue");
        br.setThreadCount(10);
        br.call();
    }

}
