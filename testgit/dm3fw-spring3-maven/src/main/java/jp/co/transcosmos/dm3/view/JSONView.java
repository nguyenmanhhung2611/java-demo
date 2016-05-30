package jp.co.transcosmos.dm3.view;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2013.04.03 H.Mizuno Jackson 2.x 対応　Start
//import org.codehaus.jackson.JsonEncoding;
//import org.codehaus.jackson.JsonFactory;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.map.MappingJsonFactory;
//import org.codehaus.jackson.map.util.JSONPObject;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
//2013.04.03 H.Mizuno Jackson 2.x 対応　End

import org.springframework.web.servlet.view.AbstractView;

public class JSONView extends AbstractView {
//    private static final Log log = LogFactory.getLog(JSONView.class);

    private String callback;
    private String callbackParam;
    
    public JSONView() {
    }
    
    public JSONView(String callbackParam) {
        this();
        setCallbackParam(callbackParam);
    }
    
    public void setCallback(String callback) {
        this.callback = callback;
    }

    public void setCallbackParam(String callbackParam) {
        this.callbackParam = callbackParam;
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        response.setCharacterEncoding("UTF-8");
// 2015.03.05 H.Mizuno 変更 start
// AbstractView の デフォルト値を設定している為、text/html;charset=ISO-8859-1 が復帰される。
// その場合、戻り値が文字化けする可能性がある。　また、text/html は html として解釈される為、復帰する
// データによっては意図せずスクリプトが実行される可能性がある。
// よって、JSON の場合は、application/json;charset=UTF-8、
// JSONP の場合は、application/javascript;charset=UTF-8　を復帰する様にヘッダを変更した。
//
//        response.setContentType(getContentType());
// 2015.03.05 H.Mizuno 変更 end
        
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        
        JsonFactory jsonFactory = new MappingJsonFactory();
        JsonGenerator jg = jsonFactory.createJsonGenerator(outBytes, JsonEncoding.UTF8);
        
        String cb = buildCallback(request);
        if (cb != null) {
// 2015.03.05 H.Mizuno 変更 start
        	response.setContentType("application/javascript;charset=UTF-8");
// 2015.03.05 H.Mizuno 変更 end
            jg.writeObject(new JSONPObject(cb, model));
        } else {
// 2015.03.05 H.Mizuno 変更 start
        	response.setContentType("application/json;charset=UTF-8");
// 2015.03.05 H.Mizuno 変更 end
            jg.writeObject(model);
        }
        
        response.setContentLength(outBytes.size());
        ServletOutputStream out = response.getOutputStream();
        outBytes.writeTo(out);
        out.flush();
    }

    protected String buildCallback(HttpServletRequest request) {        
        String callback = this.callback;
        if (callback == null && this.callbackParam != null) {
            callback = request.getParameter(this.callbackParam);
            if (callback == null) {
                throw new RuntimeException("No callback parameter supplied: " + this.callbackParam);
            }
        }
        return callback;
    }
    
}
