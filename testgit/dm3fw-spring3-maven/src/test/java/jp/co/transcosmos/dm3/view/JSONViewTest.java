package jp.co.transcosmos.dm3.view;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;


// 2013.04.03 H.Mizuno Jackson 2.x 対応 Start
//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
//2013.04.03 H.Mizuno Jackson 2.x 対応 End


import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

public class JSONViewTest extends TestCase {

    public void testSimple() throws Exception {
        MockServletContext context = new MockServletContext();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        TestVO vo = new TestVO();
        vo.setId(345L);
        vo.setTest("abc'd");
        vo.setName("name");
        
        // HashMap だと、JSON テキストの出力順が変動してしまう事があるで Assert が失敗する。
        // LinkedHashMap だと、出力順が固定化できそうなので、Map のクラスを変更した。
        Map<String,Object> model = new LinkedHashMap<String,Object>();
        model.put("Test", "abc");
        model.put("number", 123L);
        model.put("array", new String[] {"1", "2"});
        model.put("date", new Date(0L));
        model.put("vo", vo);
        
        JSONView jv = new JSONView();
        jv.setServletContext(context);
        jv.render(model, request, response);
        
        String out = response.getContentAsString();
        assertEquals("{\"Test\":\"abc\",\"number\":123,\"array\":[\"1\",\"2\"],\"date\":0,\"vo\":{\"test\":\"abc'd\",\"number\":345}}", out);
    }
    
    public class TestVO {
        private Long id;
        private String test;
        private String name;
        
        @JsonProperty(value = "number")
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        @JsonIgnore
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getTest() {
            return test;
        }
        public void setTest(String test) {
            this.test = test;
        }
        
        
    }
}
