package jp.co.transcosmos.dm3.view;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 2013.04.03 H.Mizuno Jackson 2.x �Ή��@Start
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
//2013.04.03 H.Mizuno Jackson 2.x �Ή��@End

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
// 2015.03.05 H.Mizuno �ύX start
// AbstractView �� �f�t�H���g�l��ݒ肵�Ă���ׁAtext/html;charset=ISO-8859-1 �����A�����B
// ���̏ꍇ�A�߂�l��������������\��������B�@�܂��Atext/html �� html �Ƃ��ĉ��߂����ׁA���A����
// �f�[�^�ɂ���Ă͈Ӑ}�����X�N���v�g�����s�����\��������B
// ����āAJSON �̏ꍇ�́Aapplication/json;charset=UTF-8�A
// JSONP �̏ꍇ�́Aapplication/javascript;charset=UTF-8�@�𕜋A����l�Ƀw�b�_��ύX�����B
//
//        response.setContentType(getContentType());
// 2015.03.05 H.Mizuno �ύX end
        
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        
        JsonFactory jsonFactory = new MappingJsonFactory();
        JsonGenerator jg = jsonFactory.createJsonGenerator(outBytes, JsonEncoding.UTF8);
        
        String cb = buildCallback(request);
        if (cb != null) {
// 2015.03.05 H.Mizuno �ύX start
        	response.setContentType("application/javascript;charset=UTF-8");
// 2015.03.05 H.Mizuno �ύX end
            jg.writeObject(new JSONPObject(cb, model));
        } else {
// 2015.03.05 H.Mizuno �ύX start
        	response.setContentType("application/json;charset=UTF-8");
// 2015.03.05 H.Mizuno �ύX end
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
