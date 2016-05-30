package jp.co.transcosmos.dm3.webAdmin.reformPlan.command;

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
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * リフォーム情報
 *
 * 【新規登録の場合】
 *  リクエストパラメータを受け取り、バリデーションを実行する。
 *  遷移先画面から取得したシステム物件CD、システムリフォームCDにより、
 *  DB検索して、画面各項目を初期設定。
 *
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Zhang.		2015.03.11	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class ReformInfoInputCommand implements Command {

    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

	/** 物件情報メンテナンス用 Model */
	private PanaHousingPartThumbnailProxy panaHousingManager;

    /** 共通パラメータオブジェクト */
    private PanaCommonParameters commonParameters;

    /** Panasonic用ファイル処理関連共通 */
    private PanaFileUtil fileUtil;
    
    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     * @param reformManage リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformPartThumbnailProxy reformManager) {
        this.reformManager = reformManager;
    }

	/**
	 * 物件情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param panaHousingManager
	 *            物件情報メンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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
     *リフォーム情報入力画面処理<br>
     * <br>
     * @param request HTTP リクエスト
     * @param response HTTP レスポンス
    */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // リクエストパラメータを格納した model オブジェクトを生成する。
        // このオブジェクトは View 層への値引渡しに使用される。
        Map<String, Object> model = createModel(request);
        ReformInfoForm form = (ReformInfoForm) model.get("inputForm");

        // 戻るボタンを押下（新規）
        if ("iBack".equals(form.getCommand())) {

        	// 画面項目格納
            form.setCommand("insert");
            form.setImgSelFlg("0");
            form.setImgFile1(form.getImgFile2());

            return new ModelAndView("success", model);

            // 戻るボタンを押下（更新）
        } else if ("uBack".equals(form.getCommand())) {

            // 画面項目格納
            updateFormat(model, form, "");
            form.setImgSelFlg("0");

            return new ModelAndView("success", model);
        }

        // 各種処理を実行
        execute(model, form);
        
        return new ModelAndView("success", model);
    }

    /**
     * 処理の振り分けと実行を行う。<br/>
     * <br/>
     * @param model View 層へ引き渡す model オブジェクト
     * @param inputForm 入力値が格納された Form オブジェクト
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    protected void execute(Map<String, Object> model,
            ReformInfoForm form) throws Exception {

    	// 物件名称の取得
        String displayHousingName = this.reformManager.searchHousingInfo(form.getSysHousingCd())
                .getDisplayHousingName();

        // 画面項目格納
        if (StringValidateUtil.isEmpty(form.getSysReformCd())) {

            // 新規の場合
            form.insertFormat(displayHousingName);

        } else {

            // 更新の場合
            updateFormat(model, form, displayHousingName);
        }
    }

    /**
     * リフォーム情報（更新）入力画面格納<br/>
     * <br/>
     * @param model View 層へ引き渡す model オブジェクト
     * @param form 入力値が格納された Form オブジェクト
     * @param displayHousingName 物件名称
     *
     * @exception Exception 実装クラスによりスローされる任意の例外
     */
    @SuppressWarnings("unchecked")
    private void updateFormat( Map<String, Object> model,
            ReformInfoForm form,
            String displayHousingName) throws Exception {

        // リフォームプラン情報を取得
        Map<String, Object> result =
                (Map<String, Object>) this.reformManager.searchReform(form.getSysReformCd());

		// 物件情報の取得
        Housing housing =
        		panaHousingManager.searchHousingPk(form.getSysHousingCd(), true);
        if (housing == null) {
        	throw new NotFoundException();
        }

        // リフォーム詳細リスト
        model.put("dtlList", form.setDtlList((List<ReformDtl>) result.get("dtlList"), commonParameters, fileUtil));
        // リフォーム画像情報リスト
        model.put("imgList", form.setImgList((List<ReformImg>) result.get("imgList"), commonParameters, fileUtil));
        // リフォーム情報（更新）入力画面格納
        form.updateFormat(result, displayHousingName, housing, commonParameters, fileUtil);

    }


	/**
	 * model オブジェクトを作成する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	 protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		ReformFormFactory factory = ReformFormFactory.getInstance(request);
		ReformInfoForm requestForm = factory.createRefromInfoForm(request);
        model.put("inputForm", requestForm);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        return model;
	}
}
