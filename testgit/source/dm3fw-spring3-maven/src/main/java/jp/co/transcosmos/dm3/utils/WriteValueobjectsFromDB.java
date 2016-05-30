/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Utility class for writing out java valueobjects using the fields defined in the
 * tables of a database.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: WriteValueobjectsFromDB.java,v 1.2 2012/08/01 09:28:36 tanaka Exp $
 */
public class WriteValueobjectsFromDB {
    private static final Log log = LogFactory.getLog(WriteValueobjectsFromDB.class);
    
    private String jdbcURL = null;
    private String jdbcDriverClass = "oracle.jdbc.driver.OracleDriver";
    private String jdbcUsername = null;
    private String jdbcPassword = null;
    private String tableListSQL = "SELECT table_name FROM cat WHERE table_type = 'TABLE' AND table_name NOT LIKE '%$%'";
    private String tableNames = null;
    private String outputDirectory = ".";
    private String sourceEncoding = "Windows-31J";
    private String basePackage = "vo";
    
    private DataSource datasource = null;
    
    public String getJdbcDriverClass() {
        return jdbcDriverClass;
    }

    public void setJdbcDriverClass(String jdbcDriverClass) {
        this.jdbcDriverClass = jdbcDriverClass;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public void setJdbcURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getTableListSQL() {
        return tableListSQL;
    }

    public void setTableListSQL(String tableListSQL) {
        this.tableListSQL = tableListSQL;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }
    
    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public void write() throws IOException {
        ensureDataSource();

        // Open a connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.datasource);
        
        // Get the list of table names if needed
        String tableNames[] = null;
        if (this.tableNames != null) {
            tableNames = this.tableNames.split(",");
        } else {
            List<String> tableNameList = jdbcTemplate.query(this.tableListSQL, 
                    new ParameterizedRowMapper<String>() {
                public String mapRow(ResultSet rst, int index) throws SQLException {
                    return rst.getString(1);
                }
            });
            tableNames = tableNameList.toArray(new String[tableNameList.size()]);
        }
        
        log.info("Generating valueobjects for tables: " + Arrays.asList(tableNames));
        
        VelocityEngine engine = initEngine();
        File outputDir = new File(this.outputDirectory);
        outputDir.mkdirs();
        
        // write a valueobject per table
        for (String tableName : tableNames) {
            VelocityContext context = new VelocityContext();
            context.put("now", new Date());
            setOneValueObjectArguments(tableName, context);
            
            File outFile = new File(outputDir, 
                    context.get("className") + ".java");
            Writer outWriter = new OutputStreamWriter(new FileOutputStream(
                    outFile), this.sourceEncoding);
            try {
                engine.evaluate(context, outWriter, "valueobjectClass", 
                        getValueobjectTemplate());
            } catch (Throwable err) { 
                throw new RuntimeException("Error executing velocity template: " 
                        + tableName, err);
            } finally {
                outWriter.close();
            }
        }
        log.info("Generation of valueobjects complete");
    }
    
    protected void ensureDataSource() {
        if (this.datasource == null) {
            this.datasource = new SingleConnectionDataSource(this.jdbcDriverClass,
                    this.jdbcURL, this.jdbcUsername, this.jdbcPassword, true);
        }
    }
    
    protected Reader getValueobjectTemplate() throws IOException {
        String template = this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/" +
                "valueobjectTemplate.velocity";
        log.debug("Loading from template: " + template);
        InputStream templateStream = getClass().getClassLoader().getResourceAsStream(template);
        return new InputStreamReader(templateStream, this.sourceEncoding);
    }
    
    protected void setOneValueObjectArguments(String tableName, VelocityContext context) {
        // Open a connection
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.datasource);
        jdbcTemplate.setMaxRows(0);
        ColumnDescriptor[] columns = (ColumnDescriptor[]) jdbcTemplate.query(
                "SELECT * FROM " + tableName, 
                new ResultSetExtractor() {
                    public Object extractData(ResultSet rst) throws 
                            SQLException, DataAccessException {
                        ResultSetMetaData rsmd = rst.getMetaData();
                        ColumnDescriptor columns[] = new ColumnDescriptor[rsmd.getColumnCount()];
                        for (int n = 0; n < columns.length; n++) {
                            columns[n] = new ColumnDescriptor();
                            columns[n].setName(ReflectionUtils.underscoreToLmc(
                                    rsmd.getColumnName(n + 1).toLowerCase()));
                            columns[n].setType(typePolicy(rsmd.getColumnType(n + 1)));
                        }
                        return columns;
                    }
                });
        context.put("tableName", tableName);
        context.put("package", this.basePackage);
        context.put("className", ReflectionUtils.upperCaseFirstChar(
                ReflectionUtils.underscoreToLmc(tableName.toLowerCase())));
        context.put("memberVariables", Arrays.asList(columns));
    }
    
    private String typePolicy(int dbType) {
        switch (dbType) {
        case Types.VARCHAR: case Types.CHAR: case Types.CLOB: case Types.LONGVARCHAR:
            return "String";
        case Types.BOOLEAN: case Types.BIT:
            return "Boolean";
        case Types.TINYINT: 
            return "Integer";
        case Types.DECIMAL: case Types.DOUBLE: case Types.FLOAT: case Types.REAL:
            return "Double";
        case Types.BIGINT: case Types.INTEGER: case Types.NUMERIC:
            return "Long";
        case Types.DATE: case Types.TIME: case Types.TIMESTAMP:
            return "Date";
        case Types.LONGVARBINARY: case Types.VARBINARY:
            return "byte[]";
        default: 
            log.warn("Unknown SQL type: " + dbType + " - using string");
            return "String";
        }
    }
    
    /**
     * Simple container class for storing column attributes
     */
    public class ColumnDescriptor {
        private String name;
        private String type;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getFirstCharUpperName() {
            return ReflectionUtils.upperCaseFirstChar(this.name);
        }
        public String getGetterName() {
            if (this.type.equalsIgnoreCase("boolean")) {
                return "is" + getFirstCharUpperName();
            } else {
                return "get" + getFirstCharUpperName();
            }
        }
    }
    
    private VelocityEngine initEngine() {
        try {
            VelocityEngine engine = new VelocityEngine();
            engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                    "org.apache.velocity.runtime.log.SimpleLog4JLogSystem" );
            engine.setProperty("runtime.log.logsystem.log4j.category", 
                    getClass().getName() + ".velocity");
            engine.init();        
            return engine;
        } catch (Exception err) {
            throw new RuntimeException("Error initializing velocity", err);
        }
    }

    public static void main(String argv[]) throws Exception {
        BasicConfigurator.configure();
        WriteValueobjectsFromDB writer = new WriteValueobjectsFromDB();
        for (String arg : argv) {
            arg = arg.trim();
            
            // Get key/value
            if (arg.startsWith("--")){
                int equalPos = arg.indexOf('=', 2);
                if (equalPos == -1) {
                    log.info("Setting arg: " + arg.substring(2) + "=true");
                    ReflectionUtils.setFieldValueBySetter(writer, 
                            arg.substring(2), "true");
                } else {
                    log.info("Setting arg: " + arg.substring(2));
                    ReflectionUtils.setFieldValueBySetter(writer, 
                            arg.substring(2, equalPos), 
                            arg.substring(equalPos + 1));
                }
            } else {
                log.warn("Skipping arg, no hyphen prefix: " + arg);
            }
        }
        writer.write();
    }
}
