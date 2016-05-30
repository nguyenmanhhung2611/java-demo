package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
/**
 * <pre>
 * 住宅診断情報入力確認画面
 * リクエストパラメータで渡されたリフォーム情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.21	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingInspectionValidateCommand implements Command {

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil fileUtil;

	/**
	 * Panasonic用ファイル処理関連共通Utilを設定する。<br/>
	 * <br/>
	 *
	 * @param fileUtil
	 *            Panasonic用ファイル処理関連共通Util
	 */
	public void setFileUtil(PanaFileUtil fileUtil) {
		this.fileUtil = fileUtil;
	}
	/** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }
    /** 住宅診断情報メンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 住宅診断情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            住宅診断情報メンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// リクエストパラメータを格納した form オブジェクトを生成する。
		Object[] forms = factory.createPanaHousingInspectionFormAndSearchForm(request);

		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingInspectionForm form = (PanaHousingInspectionForm)forms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];

		model.put("searchForm", searchForm);

		// path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();
        FileItem fi = form.getHousingFile();
        if (fi != null && !StringUtils.isEmpty(fi.getName())) {

            // イメージ１をアップロードし、戻り値：ファイル名を取得
            String fileName = panaHousingManager.getReformPdfFileName();
            PanaFileUtil.uploadFile(fi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            String urlPath = fileUtil.getHousFileTempUrl(temPath,fileName);
            form.setHidNewPath(urlPath);
            form.setLoadFlg("1");
            form.setAddHidFileName(fileName);
        }
        FileItem imgFi = form.getHousingImgFile();
        if (imgFi != null && !StringUtils.isEmpty(imgFi.getName())) {

            // イメージ１をアップロードし、戻り値：ファイル名を取得
            String fileName = panaHousingManager.getReformJpgFileName();
            PanaFileUtil.uploadFile(imgFi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            String urlPath = fileUtil.getHousFileTempUrl(temPath,fileName);
            form.setHidNewImgPath(urlPath);
            form.setImgFlg("1");
            form.setAddHidImgName(fileName);
        }

        // バリデーション処理
		Validateable validateableForm = (Validateable) form;
		// エラーメッセージ用のリストを作成
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		// バリデーションを実行
		if (!validateableForm.validate(errors)) {
			// エラー処理
			// エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
			model.put("errors", errors);
			model.put("HousingInspectionForm", form);
			return new ModelAndView("validationError", model);
		}
		model.put("HousingInspectionForm", form);
		return new ModelAndView("success", model);
	}

}
