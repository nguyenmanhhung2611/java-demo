/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.PrintWriter;

/**
 * Builds a wrapped servlet output stream, so we can capture the output
 * written to the response. This allows invisible saving and replay later.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SavingOutputWriter.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class SavingOutputWriter extends PrintWriter {

    private PrintWriter saving;
    
    public SavingOutputWriter(PrintWriter wrapped, PrintWriter saving) {
        super(wrapped, true);
        this.saving = saving;
    }
    
    public void write(int pC) {
        super.write(pC);
        this.saving.write(pC);
    }

    public void write(char[] pBuf, int pOff, int pLen) {
        super.write(pBuf, pOff, pLen);
        this.saving.write(pBuf, pOff, pLen);
    }

    public void write(String pS, int pOff, int pLen) {
        super.write(pS, pOff, pLen);
        this.saving.write(pS, pOff, pLen);
    }

    public void println() {
        super.println();
        this.saving.println();
    }

    public void flush() {
        super.flush();
        this.saving.flush();
    }
    
    
}