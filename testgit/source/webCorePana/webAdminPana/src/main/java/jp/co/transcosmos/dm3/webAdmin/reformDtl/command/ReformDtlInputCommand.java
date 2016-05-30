package jp.co.transcosmos.dm3.webAdmin.reformDtl.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

import org.springframework.web.servlet.ModelAndView;

/**
 * リフォーム詳細情報検索画面.
 * <p>
 * 入力された検索条件を元にリフォーム情報を検索し、一覧表示する。<br/>
 * 検索条件の入力に問題がある場合、検索処理は行わない。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success
 * <li>:検索処理正常終了
 * <li>validFail
 * <li>:バリデーションエラー
 * </ul>
 * <p>
 *
 * <pre>
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * fan	        2015.03.10  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class ReformDtlInputCommand implements Command {

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
    private ReformManage reformManager;

    /**
     * リフォーム情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
     * <br/>
     *
     * @param reformManage
     *            リフォーム情報メンテナンスの model オブジェクト
     */
    public void setReformManager(ReformManage reformManager) {
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
     * リフォーム詳細情報検索リクエスト処理<br>
     * リフォーム詳細情報検索のリクエストがあったときに呼び出される。 <br>
     *
     * @param request
     *            クライアントからのHttpリクエスト。
     * @param response
     *            クライアントに返すHttpレスポンス。
     */
    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // リクエストパラメータを、フォームオブジェクトへ設定する。
        Map<String, Object> model = new HashMap<String, Object>();
        ReformFormFactory factory = ReformFormFactory.getInstance(request);
        ReformDtlForm form = factory.createReformDtlForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);


        // 事前チェック:物件基本情報テーブルの読込
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(form.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            // データの存在しない場合,メッセージ："｛0｝物件情報が存在しない"表示
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);

        // リフォーム関連情報を一括読込
        Map<String, Object> reform = this.reformManager.searchReform(form.getSysReformCd());

        // リフォームプラン情報の読込
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        if (reformPlan == null) {
            throw new NotFoundException();
        }

        // リフォーム詳細情報入力確認画面の「戻る」ボタンから返す場合
        if ("back".equals(form.getCommand())) {
            // 画面表示用アップロード画像編集情報の再設定

            model.put("reformDtlForm", form);

            // 取得したデータをレンダリング層へ渡す
            return new ModelAndView("success", model);
        }

        // リフォーム詳細の検索
        List<ReformDtl> reformDtlList = (List<ReformDtl>) reform.get("dtlList");

        // 画面項目を設定する処理
        if (reformDtlList != null && reformDtlList.size() > 0) {

            form.setDefaultData(reformDtlList);
            setUrlList(reformDtlList, model, form);
        }

        model.put("reformDtlForm", form);

        // 取得したデータをレンダリング層へ渡す
        return new ModelAndView("success", model);
    }

    /**
	 * 画像表示用を設定する処理。 <br>
	 *
	 * @param housingImageInfoList
	 * @param model
	 *
	 * @return
	 */
	private void setUrlList(List<ReformDtl> reformDtlList,Map<String, Object> model, ReformDtlForm form) {

		String[] hidPath;

		if (null != form.getDivNo()) {
			hidPath = new String[form.getDivNo().length];
		} else {
			hidPath = new String[0];
		}

		int count = 0;
        for (ReformDtl rd : reformDtlList) {
            String urlPath = "";
            // 閲覧権限が会員のみの場合
            if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(rd.getRoleId())) {
                urlPath = fileUtil.getHousFileMemberUrl(rd.getPathName(),rd.getFileName(),this.commonParameters.getAdminSitePdfFolder());
            } else {
                // 閲覧権限が全員の場合
                urlPath = fileUtil.getHousFileOpenUrl(rd.getPathName(),rd.getFileName(),this.commonParameters.getAdminSitePdfFolder());
            }

            hidPath[count] = urlPath;
            count++;
        }
        form.setHidPath(hidPath);
	}
}
