package jp.co.transcosmos.dm3.webAdmin.reformImg.command;

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
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformFormFactory;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;

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
public class ReformImgInputCommand implements Command {

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
     * @param reformManager
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
     * リフォーム画像編集画面表示処理<br>
     * <br>
     *
     * @param request
     *            HTTP リクエスト
     * @param response
     *            HTTP レスポンス
     */
    @SuppressWarnings("unchecked")
    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        // 結果用のmodel
        Map<String, Object> model = new HashMap<String, Object>();

    	ReformFormFactory factory = ReformFormFactory.getInstance(request);

        // ページ処理用のフォームオブジェクトを作成
        ReformImgForm reformImgform = factory.createRefromImgForm(request);

        PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
        PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
        model.put("searchForm", searchForm);

        // データの取得
        // 物件基本情報テーブルの読込
        HousingInfo housingInfo = this.reformManager.searchHousingInfo(reformImgform.getSysHousingCd());
        if (housingInfo.getSysHousingCd() == null) {
            throw new NotFoundException();
        }
        model.put("housingInfo", housingInfo);

        // リフォーム関連情報を一括読込
        Map<String, Object> reform = this.reformManager.searchReform(reformImgform.getSysReformCd());

        // リフォームプラン情報の読込
        ReformPlan reformPlan = (ReformPlan) reform.get("reformPlan");

        if (reformPlan == null) {
            throw new NotFoundException();
        }

        // リフォーム画像情報の取得
        List<ReformImg> reformImgresults = (List<ReformImg>) reform.get("imgList");

        // 戻るボタン押し
        if ("back".equals(reformImgform.getCommand())) {
            model.put("ReformImgForm", reformImgform);
            // 取得したデータをレンダリング層へ渡す
            return new ModelAndView("success", model);
        }

        // 画面項目を設定する処理
        if (reformImgresults != null && reformImgresults.size() > 0) {

            reformImgform.setDefaultData(reformImgresults);
            setUrlList(reformImgresults,model,reformImgform);
        }

        model.put("ReformImgForm", reformImgform);

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
    private void setUrlList(List<ReformImg> reformImgresults,Map<String, Object> model , ReformImgForm form) {

    	if(form.getDivNo()!=null){

    		String[] beforeHidPathMax = new String[form.getDivNo().length];
            String[] beforeHidPathMin = new String[form.getDivNo().length];
            String[] afterHidPathMax = new String[form.getDivNo().length];
            String[] afterHidPathMin = new String[form.getDivNo().length];
            int count =0;
            for (ReformImg rd : reformImgresults) {
                String urlBeforePathMin = "";
                String urlBeforePathMax = "";
                String urlAfterPathMin = "";
                String urlAfterPathMax = "";
                // 閲覧権限が会員のみの場合
                if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(rd.getRoleId())) {
                	urlBeforePathMin = fileUtil.getHousFileMemberUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlBeforePathMax = fileUtil.getHousFileMemberUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                	urlAfterPathMin = fileUtil.getHousFileMemberUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlAfterPathMax = fileUtil.getHousFileMemberUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                } else {
                    // 閲覧権限が全員の場合
                	urlBeforePathMin = fileUtil.getHousFileOpenUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlBeforePathMax = fileUtil.getHousFileOpenUrl(rd.getBeforePathName(),rd.getBeforeFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                	urlAfterPathMin = fileUtil.getHousFileOpenUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(0).toString());
                	urlAfterPathMax = fileUtil.getHousFileOpenUrl(rd.getAfterPathName(),rd.getAfterFileName(),this.commonParameters.getThumbnailSizes().get(this.commonParameters.getThumbnailSizes().size()-1).toString());
                }

                beforeHidPathMin[count]=urlBeforePathMin;

                beforeHidPathMax[count]=urlBeforePathMax;

                afterHidPathMin[count]=urlAfterPathMin;

                afterHidPathMax[count]=urlAfterPathMax;

                count++;
            }
            form.setBeforeHidPathMin(beforeHidPathMin);
            form.setBeforeHidPathMax(beforeHidPathMax);
            form.setAfterHidPathMin(afterHidPathMin);
            form.setAfterHidPathMax(afterHidPathMax);
    	}

    }
}
