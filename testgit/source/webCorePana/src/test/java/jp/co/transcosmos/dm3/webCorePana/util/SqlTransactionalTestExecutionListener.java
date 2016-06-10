package jp.co.transcosmos.dm3.webCorePana.util;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestContext;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * This TestExecutionListener reads @{@link SqlFile} to collect external SQL files
 * and execute them before run test method <br/>
 * Example code: 
 * <pre>
 * &commat;Test
 * &commat;SqlFile("sql/data1.sql")
 * public void testMethod() throws Exception {}
 *
 * </pre>
 * ’S“–ŽÒ        C³“ú       C³“à—e
 * ------------ ----------- -----------------------------------------------------
 * Duong.Nguyen 2015.08.26  Create this file
 *
 * 
 */

public class SqlTransactionalTestExecutionListener
		extends org.springframework.test.context.transaction.TransactionalTestExecutionListener {
	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		super.beforeTestMethod(testContext);
		// after execute beforeTestMethod of super class, the transaction has been began already.
		// now, manipulate data through executing sql files
		run(testContext);
	}

	public void run(TestContext testContext) throws Exception {
	    // read @SqlFile annotation to collect external sql files
		SqlFile sqlAnt = testContext.getTestMethod().getAnnotation(SqlFile.class);
		// If sql files are specified for this the test method
		if (sqlAnt != null && sqlAnt.value() != null && sqlAnt.value().length > 0) {
			String[] sqlFiles = sqlAnt.value();
			// Create database populator which is used to store metadata of the execution
			final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
			
			// collect sql files and put them into the populator
			for (int i = 0; i < sqlFiles.length; i++) {
				populator.addScript(new ClassPathResource(sqlFiles[i]));
			}
			
			final PlatformTransactionManager txManager = getTransactionManager(testContext);
			// retrieve datasource from transaction
			final DataSource dataSource = getDataSourceFromTransactionManager(txManager);

			// Execute sql files
			DatabasePopulatorUtils.execute(populator, dataSource);
		}
	}

	private DataSource getDataSourceFromTransactionManager(PlatformTransactionManager transactionManager) throws Exception {
        if(transactionManager instanceof DataSourceTransactionManager){
            DataSourceTransactionManager txManager = (DataSourceTransactionManager) transactionManager;
            return txManager.getDataSource();
        }else{
            throw new UnsupportedTransactionManagerException("The transaction manager is not supported, please use " + DataSourceTransactionManager.class);
        }
    }
	
	public class UnsupportedTransactionManagerException extends Exception{

        private static final long serialVersionUID = 1L;
        public UnsupportedTransactionManagerException() {
            super();
        }
        public UnsupportedTransactionManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
        public UnsupportedTransactionManagerException(String message, Throwable cause) {
            super(message, cause);
        }
        public UnsupportedTransactionManagerException(String message) {
            super(message);
        }
        public UnsupportedTransactionManagerException(Throwable cause) {
            super(cause);
        }
	    
	}
}
