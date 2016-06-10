package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

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
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * リフォーム情報入力確認画面
 * リクエストパラメータで渡されたリフォーム情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * zhang		2015.03.11	新規作成
 *
 * 注意事項
 *
 * </pre>
*/
public class ReformInfoConfirmCommand implements Command {

    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /** Panasonic用ファイル処理関連共通 */
    private PanaFileUtil fileUtil;

    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     * @param reformManager リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

    /**
     * 共通パラメータオブジェクトを設定する。<br/>
     * <br/>
     * @param commonParameters 共通パラメータオブジェクト
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

    /**
     * Panasonic用ファイル処理関連共通を設定する。<br/>
     * <br/>
     * @param fileUtil Panasonic用ファイル処理関連共通
     */
    public void setFileUtil(PanaFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    /**
     *リフォーム情報入力確認画面表示処理<br>
     * <br>
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
    */
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // リクエストパラメータを格納した model オブジェクトを生成する。
        // このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        ReformInfoForm inputForm = (ReformInfoForm) model.get("inputForm");

        // view 名の初期値を設定
        String viewName = "success";

        // バリデーションを実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputForm.validate(errors)) {

            // 再検索
            reSearch(model, inputForm);

            // バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
            model.put("errors", errors);

            viewName = "input";

        } else {
        	// アップロード画像格納、URLパスを設定
            getImgPath(inputForm);
            inputForm.setSalesPoint1(PanaStringUtils.encodeHtml(inputForm.getSalesPoint()));
            inputForm.setNote1(PanaStringUtils.encodeHtml(inputForm.getNote()));
        }

        return new ModelAndView(viewName, model);
    }

    /**
     * リフォーム情報入力確認画面表示処理<br>
     * <br/>
     * @param model Map リクエストパラメータを格納用
     * @param inputForm ReformInfoForm リフォームプラン情報の入力値を格納した Form オブジェクト
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    private void reSearch(Map<String, Object> model,
            ReformInfoForm inputForm) throws Exception {

        // 「新規追加」ボタンを押す
        Map<String, Object> result = new HashMap<String, Object>();
        if ("insert".equals(inputForm.getCommand())) {

            // 画面項目格納
            pageFormat(result, model, inputForm);

            // 「更新」ボタンを押す
        } else if ("update".equals(inputForm.getCommand())) {

            // リフォームプラン情報を取得
            result = (Map<String, Object>) this.reformManager.searchReform(inputForm.getSysReformCd());

            // 画面項目格納
            pageFormat(result, model, inputForm);
        }
    }

    /**
     * リフォーム情報入力画面格納<br>
     * <br/>
     * @param result リフォームプラン情報
     * @param model Map リクエストパラメータを格納用
     * @param form ReformInfoForm リフォームプラン情報の入力値を格納した Form オブジェクト
     *
     */
    @SuppressWarnings("unchecked")
    private void pageFormat(Map<String, Object> result,
            Map<String, Object> model,
            ReformInfoForm form) {

        if ("update".equals(form.getCommand())) {
            // リフォーム詳細リスト
            model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
            // リフォーム画像情報リスト
            model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        }
    }

    /**
     * アップロード画像格納、URLパスを設定<br>
     * <br/>
     * @param inputForm ReformInfoForm リフォームプラン情報の入力値を格納した Form オブジェクト
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    private String getImgPath(ReformInfoForm inputForm) throws Exception {

    	String urlPath = "";
        FileItem fi = inputForm.getReformImgFile();
        if (fi != null && !StringUtils.isEmpty(fi.getName())) {

        	// tempPath = 「年月日」
            String temPath = PanaFileUtil.getUploadTempPath();
        	// fileName = 「XXX.jpg」
            String fileName = reformManager.getReformJpgFileName();

            // アップロード画像格納
            PanaFileUtil.uploadFile(fi,
                    PanaFileUtil.conPhysicalPath(this.commonParameters.getHousImgTempPhysicalPath(), temPath),
                    fileName);

            // 画像URLパスを設定
            urlPath = fileUtil.getHousFileTempUrl(temPath, fileName);

            inputForm.setImgName(fileName);
            inputForm.setTemPath(temPath);
            inputForm.setImgFile1(urlPath);
            inputForm.setImgSelFlg("1");
        }

        return urlPath;
    }

    /**
     * リクエストパラメータから Form オブジェクトを作成する。<br/>
     * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
     * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト
     * <br/>
     * @param request HTTP リクエストパラメータ
     * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
     */
    protected Map<String, Object> createModel(HttpServletRequest request) {

        Map<String, Object> model = new HashMap<String, Object>();

        // リクエストパラメータを取得して Form オブジェクトを作成する。
		ReformFormFactory factory = ReformFormFactory.getInstance(request);
		Object[] requestForms = factory.createRefromInfoFormAndSearchForm(request);

		ReformInfoForm reformForm = (ReformInfoForm)requestForms[0];
		PanaHousingSearchForm searchForm = (PanaHousingSearchForm)requestForms[1];

        model.put("inputForm", reformForm);
        model.put("searchForm", searchForm);

        return model;
    }
}
