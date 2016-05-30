/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * Builds a wrapped servlet output stream, so we can capture the output
 * written to the response. This allows invisible saving and replay later.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SavingOutputStream.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class SavingOutputStream extends ServletOutputStream {

    private ServletOutputStream wrapped;
    private OutputStream saving;
    
    public SavingOutputStream(ServletOutputStream wrapped, OutputStream saving) {
        this.wrapped = wrapped;
        this.saving = saving;
    }
    
    public void write(int b) throws IOException {
        this.wrapped.write(b);
        this.saving.write(b);
    }
    
    public void close() throws IOException {
        this.wrapped.close();
    }
    
    public void flush() throws IOException {
        this.wrapped.flush();
        this.saving.flush();
    }
}