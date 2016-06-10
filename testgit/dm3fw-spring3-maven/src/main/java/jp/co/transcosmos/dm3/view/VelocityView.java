package jp.co.transcosmos.dm3.view;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * Velocity によるレンダリング用 View クラス
 * URL マッピング時に、「mv:テンプレート名」でマッピングを定義する。
 * 「mv:テンプレート名:プロパティファイル名」を指定すると、Velocity Engine が使用するプロパティファイルを
 * 別に設定する事が可能。 
 *
 * 担当者            修正日               修正内容
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.03.12  新規作成
 * H.Mizuno  2013.03.15  Velocity Propertie File 対応
 * H.Mizuno  2013.03.17  ToolContext に、リクエスト、レスポンス、のオブジェクトを格納
 *
 * </pre>
*/
public class VelocityView extends AbstractView {

	// Velocity テンプレートファイル名
	private String templateFileName ="";

	// Velocity プロパティ・ファイル名
	private String propertiesFile = "velocity.properties";
	


	/**
     * コンストラクター<br/>
     * <br/>
     * @param templateFileName テンプレートファイル名
     * @param propertiesFile Velocity　プロパティファイル名
     */
	public VelocityView(String templateFileName, String propertiesFile){
		this.templateFileName = templateFileName;
		this.propertiesFile = propertiesFile;
	}


	
	/**
     * コンストラクター<br/>
     * <br/>
     * @param templateFileName テンプレートファイル名
     */
	public VelocityView(String templateFileName){
		this.templateFileName = templateFileName;
	}



	/**
     * Velocity を使用した描画処理<br/>
     * Velocity tools を組み込んだ context を生成して描画する。<br />
     * Velocitu の初期化は、WEB-INF 内の propertiesFile で指定されたプロパティファイル<br />
     * を使用する。　デフォルトは、velocity.properties<br />
     * 次に、WEB-INF/velocity-tools.xml を使用して Velocity tools のコンテキストを取り込む。<br />
     * XSS 対応の問題もあり、このファイルは VelocityView を使用する場合、必須となる。<br />
     * <br/>
     * @param model 置換にしようする Map 情報
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
     * @exception Exception
     */
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// テンプレートファイル名が指定されているかをチェック
		if (templateFileName == null || templateFileName.length() == 0 )
			throw new RuntimeException("templateFileName is empty");

		// Velocity Engine のインスタンス生成（分離オブジェクト）
		VelocityEngine velocity = new VelocityEngine();

		// Velocity 初期化
		if (this.propertiesFile == null || this.propertiesFile.length() == 0){
			// プロパティファイル名が指定されていない場合、デフォルト値で初期化
			velocity.init();
		} else {
			// プロパティファイル名が指定されている場合、そのファイルを使用して初期化
			String file = this.getServletContext().getRealPath("WEB-INF/" + this.propertiesFile);
			velocity.init(file);
		}

		// Velocity tools を追加したコンテキストを生成
		// コードを見る限り、ToolManager が生成する Context は共有しても安全に思える。
		// 性能を評価し、メモリ消費量、レスポンスの劣化が激しい場合はリファクタリングを検討する。
		String toolsfile = this.getServletContext().getRealPath("WEB-INF/velocity-tools.xml");
		ToolManager velocityToolManager = new ToolManager();
		velocityToolManager.configure(toolsfile);
		
		ToolContext t_ctx = velocityToolManager.createContext();
		t_ctx.put("request", request);
		t_ctx.put("response", response);
		t_ctx.put("servletContext", this.getServletContext());
		
		VelocityContext context = new VelocityContext(t_ctx);
		// パラメータを追加したコンテキストを生成
		if (model != null) {
			for (Map.Entry<String, Object> entry : (Set<Map.Entry<String, Object>>) model.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}
		}
		

        // テンプレートの生成
		Template template = velocity.getTemplate(this.templateFileName);

        // テンプレートをマージして出力
        StringWriter writer = new StringWriter();
        template.merge(context,writer);
        String encoding = (String)velocity.getProperty("output.encoding");
        byte out[] = writer.toString().getBytes(encoding);

        // レスポンスに値を設定
        response.setContentType("text/html;charset=" + encoding);
        response.setContentLength(out.length);
        response.setBufferSize(out.length);
        response.getOutputStream().write(out);
        response.getOutputStream().flush();
	}


	
	// setter、getter
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}
}
