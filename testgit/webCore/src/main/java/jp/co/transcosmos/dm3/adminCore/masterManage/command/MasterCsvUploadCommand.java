package jp.co.transcosmos.dm3.adminCore.masterManage.command;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadForm;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * マスター情報更新用アップロード画面.
 * <p>
 * マスター情報更新用の CSV ファイルをアップロードし、マスター情報を更新する。
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class MasterCsvUploadCommand implements Command {

	/** 駅マスタメンテナンスを行う Model のインスタンス */
	protected CsvMasterManage csvMasterManage;
	
	
	
	/**
	 * 駅マスタメンテナンスを行う Model のインスタンスを設定する。<br/>
	 * <br/>
	 * @param stationManage 駅マスタメンテナンスを行う Model
	 */
	public void setCsvMasterManage(CsvMasterManage csvMasterManage) {
		this.csvMasterManage = csvMasterManage;
	}
	


	/**
	 * CSV ファイルによるマスター情報更新処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
        // リクエストパラメータを取得
		CsvUploadForm form = createForm(request);
		

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!form.validate(errors)) {
			return new ModelAndView("validFail", "errors", errors);
		}

		
		// ログインユーザーの情報を取得
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);


		// CSV ファイルのロード処理実行
		try (InputStream inputStream = form.getCsvFile().getInputStream()){
			boolean ret = this.csvMasterManage.csvLoad(inputStream, (String)loginUser.getUserId(), errors);

			// 取り込み時にエラーが発生している場合
			if (!ret){
				return new ModelAndView("validFail", "errors", errors);
			}
		}


		return new ModelAndView("success");
	}

	

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクト
	 */
	protected CsvUploadForm createForm(HttpServletRequest request) {

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		CsvUploadFormFactory factory = CsvUploadFormFactory.getInstance(request);
		return factory.createCsvUploadForm(request);

	}
}
