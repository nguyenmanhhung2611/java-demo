package jp.co.transcosmos.dm3.batch;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JobTwo implements Callable<Void>{
    private final Log log = LogFactory.getLog(JobTwo.class);

    @Override
    public Void call() throws Exception {
        log.info("Start job two");
        Thread.sleep(1500);
        log.info("End job two");
        return null;
    }

}
