package jp.co.transcosmos.dm3.batch;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JobOne implements Callable<Void>{
    private final Log log = LogFactory.getLog(JobOne.class);

    @Override
    public Void call() throws Exception {
        log.info("Start");
        Thread.sleep(1000);
        log.info("End");
        return null;
    }

}
