package jp.co.transcosmos.dm3.webAdmin.housingImageInfo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件画像情報入力確認画面
 * リクエストパラメータで渡された物件画像情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan			2015.04.11	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingImageInfoConfirmCommand implements Command {

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

	/** Housingメンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * Housingメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            Housingメンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

    /**
     * 入力確認画面表示処理<br>
     * <br>
     *
     * @param request
     *            HTTP リクエスト
     * @param response
     *            HTTP レスポンス
     */
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Map<String, Object> model = new HashMap<String, Object>();

    	// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// リクエストパラメータを格納した form オブジェクトを生成する。
		Object[] forms = factory.createPanaHousingImgInfoFormAndSearchForm(request);

		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingImageInfoForm form = (PanaHousingImageInfoForm)forms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];

		model.put("searchForm", searchForm);

		// 事前チェック:物件基本情報テーブルの読込
		Housing housingresults = this.panaHousingManager.searchHousingPk(
				form.getSysHousingCd(), true);

		if (housingresults == null) {
			// データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
			throw new NotFoundException();
		}

		List<jp.co.transcosmos.dm3.core.vo.HousingImageInfo> housingImageInfoList = housingresults
				.getHousingImageInfos();

		// 画面項目を設定する処理
    	// 画像タイプ
    	String[] imageType = new String[housingImageInfoList.size()];
        // 閲覧権限
    	String[] roleId = new String[housingImageInfoList.size()];

        for (int i = 0; i < housingImageInfoList.size(); i++) {
            imageType[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getImageType());
            roleId[i] = String.valueOf(((jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo)housingImageInfoList.get(i)).getRoleId());
        }

        // バリデーション処理
        Validateable validateableForm = (Validateable) form;
        // エラーメッセージ用のリストを作成
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

        // バリデーションを実行
        if (!validateableForm.validate(errors)) {
            // エラー処理
            setResultLists(form);
            // エラーオブジェクトと、フォームオブジェクトをModelAndView に渡している
            model.put("errors", errors);

            form.setOldRoleId(roleId);
            form.setOldImageType(imageType);
            model.put("housingImageInfoForm", form);

            return new ModelAndView("validationError", model);
        }

        // 更新処理の場合
        if ("update".equals(form.getCommand())) {
            // 正常処理
            setResultLists(form);
            // フォームオブジェクトのみをModelAndView に渡している
            model.put("housingImageInfoForm", form);
            return new ModelAndView("success", model);
        }

        // path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();

        // 新規登録処理の場合
        String[] addHidPath = new String[form.getAddFilePath().length];
        String[] addHidFileName = new String[form.getAddFilePath().length];
        String[] addHidPathMin = new String[form.getAddFilePath().length];
        for (int idx = 0; idx < form.getAddFilePath().length; ++idx) {
            FileItem fi = form.getAddFilePath()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // イメージ１をアップロードし、戻り値：ファイル名を取得
                String fileName = panaHousingManager.getReformJpgFileName();
                // アップロード
                this.panaHousingManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // アップロードしたサーバー側のURLパスを設定
                addHidPath[idx] =  urlPathMax;
                addHidFileName[idx] = fileName;
                addHidPathMin[idx] = urlPathMin;
            }
        }
        form.setAddHidPathMin(addHidPathMin);
        form.setAddHidPath(addHidPath);
        form.setAddHidFileName(addHidFileName);
        setResultLists(form);

        // フォームオブジェクトのみをModelAndView に渡している
        model.put("housingImageInfoForm", form);

        return new ModelAndView("success", model);
    }

    /**
     * 確認画面表示用formを設定する処理。 <br>
     *
     * @param form
     *            フォーム詳細情報画面form。
     * @return 確認画面表示用form
     */
    private void setResultLists(PanaHousingImageInfoForm form) {
        if (form.getDivNo() != null) {
            String[] delLength = new String[form.getDivNo().length];
            for (int i = 0; i < form.getDivNo().length; i++) {
                if (form.getDivNo()[i] != null && form.getDivNo()[i] != "") {

                    if (form.getDelFlg() != null) {
                        for (int j = 0; j < form.getDelFlg().length; j++) {
                            if (i == Integer.valueOf(form.getDelFlg()[j])) {
                                delLength[i] = "1";
                                break;
                            } else {
                                delLength[i] = "0";
                            }
                        }
                    } else {
                        delLength[i] = "0";
                    }
                }
            }
            form.setDelFlg(delLength);
        }
    }
}
