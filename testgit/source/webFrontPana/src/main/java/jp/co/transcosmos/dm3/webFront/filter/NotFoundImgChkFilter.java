package jp.co.transcosmos.dm3.webFront.filter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.servlet.BaseFilter;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * 物件画像の存在チェックフィルター.
 * rsync による同期タイムラグにより、物件画像ファイルが存在しない場合は No Image 画像を復帰する。<br/>
 * この Filter がターゲットとするのは、Tomcat 管理となる会員限定ファイルとなる。<br/>
 * 一般公開される画像ファイルの場合は Apache 側の環境設定で対応する事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.05.14	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class NotFoundImgChkFilter extends BaseFilter {

	private static final Log log = LogFactory.getLog(NotFoundImgChkFilter.class);

	/** アプリケーションのコンテキストパス */
	private String contextPath = "";

	/**
	 * チェック対象とする拡張子<br/>
	 *　未設定の場合、.jpeg、.jpg が設定される。<br/>
	 */
	private Set<String> targetExtensions = new HashSet<>();
	
	/**
	 * No Image 画像ファイル名を格納した Map オブジェクト<br/>
	 * 該当するファイル名が見つからない場合、そのままリクエスト先へ Chain して 404 を復帰する。<br/>
	 * Key = 画像サイズ、 Value = 画像ファイル名<br/>
	 */
	private Map<String, String> noImageFileNames = new HashMap<>();

	/** 共通パラメータオブジェクト */
	private PanaCommonParameters commonParameters;



	/**
	 * Filter の初期化処理<br/>
	 * web.xml からパラメータが設定された場合、その値で Filter を初期化する。<br/>
	 * <br/>
	 * @param config パラメータオブジェクト
	 * @exception ServletException
	 * 
	 */
    @Override
	public void init(FilterConfig config) throws ServletException {

    	// Context パスを取得
    	this.contextPath = config.getServletContext().getContextPath();


    	// 初期化パラメータからチェック対象とする拡張子を取得する。
    	String extensions = config.getInitParameter("targetExtensions");
    	if (!StringValidateUtil.isEmpty(extensions)){
    		String[] extensionList = extensions.split(",");

    		for (String extension : extensionList){
        		this.targetExtensions.add(extension.toLowerCase());
    			log.info("setting targetExtensions:" + extension);
    		}
    	} else {
    		// 未設定の場合、下記の拡張子をデフォルト設定する。
    		this.targetExtensions.add(".jpeg");
    		this.targetExtensions.add(".jpg");
    	}


    	// 初期化パラメータから No Image 画像ファイル名の情報を取得する。
    	// 画像ファイル名は、以下の書式で記述され、カンマ区切りで複数記述する事ができる。
    	// 「画像サイズ」:「ファイル名」
    	String fileNames = config.getInitParameter("noImageFileNames");
    	if (!StringValidateUtil.isEmpty(fileNames)){
    		String[] fileNameList = fileNames.split(",");

    		for (String fileName : fileNameList){
        		String[] fileNamePats = fileName.split(":");
        		this.noImageFileNames.put(fileNamePats[0], fileNamePats[1]);
    			log.info("setting noImageFileNames:" + fileNamePats[0] + "=" + fileNamePats[1]);
    		}
    	}


		// Spring コンテキストから共通パラメータオブジェクトを取得
		try {
			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			this.commonParameters = (PanaCommonParameters)springContext.getBean("commonParameters");
		} catch (NoSuchBeanDefinitionException e){
			log.warn("CommonParameters bean not found !!!");
		}
    	
    }



    /**
     * Filter の実質的な処理<br/>
     * リクエストされた画像ファイルが存在しない場合、No Image 画像を復帰する。<br/>
     * 復帰する画像ファイルのサイズは URL から取得する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
     * @param chain Chain オブジェクト
     * 
     * @exception IOException
     * @exception ServletException
     */
	@Override
	protected void filterAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException,	ServletException {

		// リクエストされた URL から物理パスを取得する。
		String targetFile = getRealPath(request);

		log.debug("call NotFoundImgChkFilter physical path =" + targetFile);

		// リクエスト先の拡張子をチェックする。
		// もしチェック対象外のリクエストの場合、そのまま Chain 先を実行する。
		if (!isTargetExtensions(targetFile)){
			chain.doFilter(request, response);
			return;
		}
		

		// 元の処理へ Chain する前にファイルが存在するのかをチェックする。
		File file = new File(targetFile);
		if (!file.exists()){

			log.warn(request.getRequestURI() + " is not found.");

			// ファイル名の直前のフォルダがファイルサイズなので、そこからファイルサイズに該当する
			// NoImage 画像ファイル名を取得する。
			// もし取得できない場合は通常の 404 として処理するのでそのまま Chain 先へ誘導する。
			String noImgFileName = this.noImageFileNames.get(getImgSize(targetFile));
			if (StringValidateUtil.isEmpty(noImgFileName)){
				chain.doFilter(request, response);
				return;
			}


			// 画像ファイル読込用、レスポンス書込用の Stream を用意する。
			String fileName = this.commonParameters.getHousImgOpenPhysicalPath() + "nophoto/" + noImgFileName;
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
				 ServletOutputStream out = response.getOutputStream();){

				// 読み込んだデータをレスポンスへ書込む
				int data;
				int fileSize = 0;
				while ((data = in.read()) != -1) {
					out.write(data);
					++fileSize;
				}

				// レスポンスヘッダを設定する。
				response.addHeader("Content-Type", "image/jpeg");
				response.addHeader("Content-Length", String.valueOf(fileSize));
				// 一応、404 のスタータスコードを設定しておくが、実際には 200 が復帰されてしまう...。
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			return;
		}

		// ファイルが存在する場合は、そのまま先の処理へ Chain する。
		chain.doFilter(request, response);

	}



	/**
	 * リクエストされた URL から物理パスに変換する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return　物理ファイル名（フルパス）
	 */
	protected String getRealPath(HttpServletRequest request) {

		// リクエスト URL を取得
		String targetURL = request.getRequestURI();

		// 画像ファイルの ROOT URL を取得
		String rootURL = this.contextPath + this.commonParameters.getHousImgMemberUrl();


		// 画像 URL の root 部分を取り除く
		if (targetURL.startsWith(rootURL)){
			targetURL = targetURL.substring(rootURL.length());
		}

		// 物理パスを復帰
		return this.commonParameters.getHousImgOpenPhysicalMemberPath() + targetURL;

	}
	
	
	
	/**
	 * チェック対象となる拡張子かをチェックする。<br/>
	 * チェック時、大文字・小文字は無視してチェックする。<br/>
	 * <br/>
	 * @param fileName 物理ファイル名（フルパス）
	 * @return チェック対象拡張子の場合、true を復帰する。
	 */
	protected boolean isTargetExtensions(String fileName) {

		// ファイル名の後ろからピリオドを取得する。
		// もし見つからない場合は false （チェック対象外）を復帰。
		int point = fileName.lastIndexOf(".");
		if (point < 0) return false;

		// 小文字に変換した拡張子を取得する。
		// 取得した拡張子がターゲットとして設定されている場合は true を復帰する。
		String extension = fileName.substring(point).toLowerCase();
		if (this.targetExtensions.contains(extension)) return true;

		return false;
	}
	
	
	
	/**
	 * No Image 画像のサイズをファイルパスから取得する。<br/>
	 * ファイル名の一階層上が画像サイズなので、その値を取得する。　もし１階層上が存在しない場合は空文字列を復帰する。
	 * @param fileName 物理ファイル名（フルパス）
	 * @return 画像サイズ（一階層上のフォルダ名）
	 */
	protected String getImgSize(String fileName){

		// スラッシュでファイル名（フルパス）を分割
		String parts[] = fileName.split("\\/");

		// ファイル名の１階層下をファイルサイズとみなすが、階層自体が存在しない場合は空文字列を復帰する。
		if (parts.length-2 < 0) return "";

		return parts[parts.length-2];
	}
}
