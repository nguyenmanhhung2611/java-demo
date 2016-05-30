package jp.co.transcosmos.dm3.corePana.model.exception;
/**
 * Affiliate exception that is thrown when there is exception while processing affiliation
 * �S����         �C����      �C�����e
 * -------------- ----------- -----------------------------------------------------
 * Thi Tran     2015.10.16      Create
 * ���ӎ���
 *
 * </pre>
 */
public class AffiliateException extends Exception{
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor with message error
     * @param message
     */
    public AffiliateException(String message){
        super(message);
    }

    /**
     * Constructor without parameter
     */
    public AffiliateException() {
        super();
    }
    /**
     * Constructor with message error and cause
     * @param message
     * @param cause
     */
    public AffiliateException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor with cause
     * @param cause
     */
    public AffiliateException(Throwable cause) {
        super(cause);
    }
    
}
