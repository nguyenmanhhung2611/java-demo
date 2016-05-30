package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム画像編集画面
 *
 * <pre>
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   焦		  2015.03.10    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class ReformImgConfirmCommand implements Command {


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
    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     *
     * @param reformManage
     *            リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
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

    /**
     * リフォーム画像編集画面表示処理<br>
     * <br>
     *
     * @param request
     *            HTTP リクエスト
     * @param response
     *            HTTP レスポンス
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();

    	ReformFormFactory factory = ReformFormFactory.getInstance(request);
        // リクエストパラメータを、フォームオブジェクトへ設定する。
    	Object[] forms = factory.createRefromImgForms(request);
    	ReformImgForm form = (ReformImgForm)forms[0];

    	// リクエストパラメータを取得して Form オブジェクトを作成する。
 		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)forms[1];
 		model.put("searchForm", searchForm);

        // 事前チェック:物件基本情報テーブルの読込
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        model.put("housingInfo", housingInfo);
        // リフォーム関連情報を一括読込
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // リフォーム詳細の検索
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");
        // 閲覧権限
        String[] roleId = new String[reformImgresults.size()];
        for (int i = 0; i < reformImgresults.size(); i++) {
        	 // 閲覧権限
            roleId[i] = reformImgresults.get(i).getRoleId();
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
         // 閲覧権限
            form.setEditOldRoleId(roleId);
            model.put("ReformImgForm", form);
            return new ModelAndView("validationError", model);
        }
        // 更新処理の場合
        if ("update".equals(form.getCommand())) {
            // 正常処理
            setResultLists(form);
            // フォームオブジェクトのみをModelAndView に渡している
            model.put("ReformImgForm", form);
            return new ModelAndView("success", model);
        }

        // path = "\data\reform\temp\"
        String temPath = PanaFileUtil.getUploadTempPath();

        // 新規登録処理の場合
        String[] addBeforePath = new String[form.getUploadBeforePathName().length];
        String[] addBeforeFileName = new String[form.getUploadBeforePathName().length];
        String[] addBeforePathMin = new String[form.getUploadBeforePathName().length];
        for (int idx = 0; idx < form.getUploadBeforePathName().length; ++idx) {
            FileItem fi = form.getUploadBeforePathName()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // イメージ１をアップロードし、戻り値：ファイル名を取得
                String fileName = reformManager.getReformJpgFileName();
                // アップロード
                this.reformManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // アップロードしたサーバー側の物理パスを保持
                addBeforePath[idx] = urlPathMax;
                addBeforeFileName[idx] = fileName;
                addBeforePathMin[idx] = urlPathMin;
                // アップロードしたサーバー側のURLパスを設定
            }
        }
        form.setUploadBeforeHidPathMin(addBeforePathMin);
        form.setUploadBeforeHidPath(addBeforePath);
        form.setUploadBeforeFileName(addBeforeFileName);

        // 新規登録処理の場合
        String[] addAfterPath = new String[form.getUploadAfterPathName().length];
        String[] addAfterFileName = new String[form.getUploadAfterPathName().length];
        String[] addAfterPathMin = new String[form.getUploadAfterPathName().length];
        for (int idx = 0; idx < form.getUploadAfterPathName().length; ++idx) {
            FileItem fi = form.getUploadAfterPathName()[idx];
            if (fi != null && !StringUtils.isEmpty(fi.getName())) {

                // イメージ１をアップロードし、戻り値：ファイル名を取得
                String fileName = reformManager.getReformJpgFileName();

                // アップロード
                this.reformManager.addTempFile(fi, temPath, fileName);

                String urlPathMax = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size() - 1).toString(),fileName);
                String urlPathMin = fileUtil.getHousFileTempUrl(temPath+"/"+this.commonParameters.getThumbnailSizes().get(0).toString(),fileName);
                // アップロードしたサーバー側の物理パスを保持
                addAfterPath[idx] = urlPathMax;
                addAfterFileName[idx] = fileName;
                addAfterPathMin[idx] = urlPathMin;
                // アップロードしたサーバー側のURLパスを設定
            }
        }
        form.setUploadAfterHidPath(addAfterPath);
        form.setUploadAfterFileName(addAfterFileName);
        form.setUploadAfterHidPathMin(addAfterPathMin);
        setResultLists(form);

        model.put("ReformImgForm", form);

        // 正常処理
        return new ModelAndView("success", model);

    }

    /**
     * 確認画面表示用formを設定する処理。 <br>
     *
     * @param form
     *            フォーム詳細情報画面form。
     * @return 確認画面表示用form
     */
    private void setResultLists(ReformImgForm form) {
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
