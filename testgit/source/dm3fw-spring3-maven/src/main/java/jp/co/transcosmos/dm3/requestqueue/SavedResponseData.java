/*
 * &copy; Trans-cosmos Inc. 2006, 2007.
 * All rights reserved.
 */
package jp.co.transcosmos.dm3.requestqueue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Value-object that holds enough of the content of a response to be 
 * able to re-play it if we determine that another request can use this response.
 * 
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: SavedResponseData.java,v 1.2 2007/05/31 07:54:21 rick Exp $
 */
public class SavedResponseData {
    private static final Log log = LogFactory.getLog(SavedResponseData.class);
    
    private List<Instruction> commands = new ArrayList<Instruction>();
    protected ByteArrayOutputStream savingOutputStream;
    
    public void addCookie(Cookie cookie) {
        this.commands.add(new Instruction("addCookie", cookie));
    }

    public void addDateHeader(String name, long value) {
        this.commands.add(new Instruction("addDateHeader", name, value));
    }

    public void addHeader(String name, String value) {
        this.commands.add(new Instruction("addHeader", name, value));
    }

    public void addIntHeader(String name, int value) {
        this.commands.add(new Instruction("addIntHeader", name, value));
    }

    public void setDateHeader(String name, long value) {
        this.commands.add(new Instruction("setDateHeader", name, value));
    }

    public void setHeader(String name, String value) {
        this.commands.add(new Instruction("setHeader", name, value));
    }

    public void setIntHeader(String name, int value) {
        this.commands.add(new Instruction("setIntHeader", name, value));
    }

    public void setStatus(int status) {
        this.commands.add(new Instruction("setStatus", status));
    }

    public void setCharacterEncoding(String encoding) {
        this.commands.add(new Instruction("setCharacterEncoding", encoding));
    }

    public void setContentLength(int length) {
        this.commands.add(new Instruction("setContentLength", length));
    }

    public void setContentType(String type) {
        this.commands.add(new Instruction("setContentType", type));
    }

    public void setLocale(Locale locale) {
        this.commands.add(new Instruction("setLocale", locale));
    }

    public void sendError(int status, String msg) throws IOException {
        this.commands.add(new Instruction("sendErrorMsg", status, msg));
    }

    public void sendError(int status) throws IOException {
        this.commands.add(new Instruction("sendError", status));
    }

    public void sendRedirect(String msg) throws IOException {
        this.commands.add(new Instruction("sendRedirect", msg));
    }
    
    public void reset() {
        this.commands.add(new Instruction("reset", null));
    }

    public void resetBuffer() {
        if (this.savingOutputStream != null) {
            this.savingOutputStream.reset();
        }
    }

    public ByteArrayOutputStream getSavingOutputStream() {
        if (this.savingOutputStream == null) {
            this.savingOutputStream = new ByteArrayOutputStream();
        }
        return this.savingOutputStream;
    }

    public void dumpIntoResponse(HttpServletResponse response) throws IOException {        
        // Playback instructions into response
        for (Iterator<Instruction> i = this.commands.iterator(); i.hasNext(); ) {
            Instruction command = i.next();
            log.debug("Replaying response command: " + command);
            if (command.command.equals("addCookie")) {
                response.addCookie((Cookie) command.arg1);
            } else if (command.command.equals("addDateHeader")) {
                response.addDateHeader((String) command.arg1, (Long) command.arg2);
            } else if (command.command.equals("addHeader")) {
                response.addHeader((String) command.arg1, (String) command.arg2);
            } else if (command.command.equals("addIntHeader")) {
                response.addIntHeader((String) command.arg1, (Integer) command.arg2);
            } else if (command.command.equals("setDateHeader")) {
                response.setDateHeader((String) command.arg1, (Long) command.arg2);
            } else if (command.command.equals("setHeader")) {
                response.setHeader((String) command.arg1, (String) command.arg2);
            } else if (command.command.equals("setIntHeader")) {
                response.setIntHeader((String) command.arg1, (Integer) command.arg2);
            } else if (command.command.equals("setStatus")) {
                response.setStatus((Integer) command.arg1);
            } else if (command.command.equals("setCharacterEncoding")) {
                response.setCharacterEncoding((String) command.arg1);
            } else if (command.command.equals("setContentLength")) {
                response.setContentLength((Integer) command.arg1);
            } else if (command.command.equals("setContentType")) {
                response.setContentType((String) command.arg1);
            } else if (command.command.equals("setLocale")) {
                response.setLocale((Locale) command.arg1);
            } else if (command.command.equals("sendErrorMsg")) {
                response.sendError((Integer) command.arg1, (String) command.arg2);
            } else if (command.command.equals("sendError")) {
                response.sendError((Integer) command.arg1);
            } else if (command.command.equals("sendRedirect")) {
                response.sendRedirect((String) command.arg1);
            } else if (command.command.equals("reset")) {
                response.reset();
            }
        }
        
        // Write the body stream
        if (this.savingOutputStream != null) {
            log.debug("Replaying response body: length=" + this.savingOutputStream.size());
            this.savingOutputStream.writeTo(response.getOutputStream());
        }
    }
    
    /**
     * Local storage class - we use this to store operations
     */
    class Instruction {
        String command;
        Object arg1;
        Object arg2;

        Instruction(String command, Object arg) {
            this(command, arg, null);
        }
        
        Instruction(String command, Object arg1, Object arg2) {
            this.command = command;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public String toString() {
            return "Command: " + command + ", Arg1=" + arg1 + ", Arg2=" + arg2;
        }
    }
}
