package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件詳細情報編集画面
 * リクエストパラメータで渡された物件詳細情報のバリデーションを行い、確認画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong     2015.03.17  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingdtlEditCommand implements Command {


	/** 処理モード (edit = 編集、editBack = 再編集) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/** 共通パラメータオブジェクト */
	protected PanaCommonParameters commonParameters;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil panaFileUtil;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 *
	 * @param mode "edit" = 編集 "editBack" = 再編集
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingManage 物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * @param commonParameters セットする commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * @param panaFileUtil セットする panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * 物件詳細情報編集画面表示処理<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP リクエスト
	 * @param response
	 *            HTTP レスポンス
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		PanaHousingDtlInfoForm inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");

		// 処理モードを判断する。
		if ("edit".equals(this.mode)) {
			inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");

			// 処理実行を行う。
			execute(inputForm);
			// プレビュー画像用パス設定
			if (!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
					!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {
				String previewImgPath = this.panaFileUtil.getHousFileOpenUrl(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName(), this.commonParameters.getAdminSiteStaffFolder());
				inputForm.setPreviewImgPath(previewImgPath);
			}
		} else if ("editBack".equals(this.mode)) {
			// 画面値を復帰表示する。
			inputForm = (PanaHousingDtlInfoForm) model.get("inputForm");
			// 担当者写真の再設定
			setStaffImgInfo(inputForm);
			// プレビュー画像用パス設定
			if (!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
					!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {
				String previewImgPath = this.panaFileUtil.getHousFileOpenUrl(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName(), this.commonParameters.getAdminSiteStaffFolder());
				inputForm.setPreviewImgPath(previewImgPath);
			} else {
				inputForm.setPreviewImgPath(null);
			}
		}

		model.put("inputForm", inputForm);

		return new ModelAndView("success", model);
	}

	/**
	 * リクエストパラメータから Form オブジェクトを作成する。<br/>
	 * 生成した Form オブジェクトは Map に格納して復帰する。<br/>
	 * key = フォームクラス名（パッケージなし）、Value = フォームオブジェクト <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータが設定されたフォームオブジェクトを格納した Map オブジェクト
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

        // リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		PanaHousingDtlInfoForm requestForm = factory.createPanaHousingDtlInfoForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);

		model.put("inputForm", requestForm);
		model.put("searchForm", searchForm);

		return model;
	}

	/**
	 * 処理実行を行う。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private void execute(PanaHousingDtlInfoForm inputForm) throws Exception, NotFoundException {

		// 物件情報を取得する。
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}

		// Formをセットする。
		inputForm.setDefaultData(housing);
	}
	/**
	 * 担当者写真の再設定<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private void setStaffImgInfo(PanaHousingDtlInfoForm inputForm) throws Exception, NotFoundException  {
		// 物件情報を取得する。
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}
        // 物件拡張属性情報を取得する。
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // カテゴリ名に該当する Map を取得する。
        Map<String, String> cateMap = extMap.get("housingDetail");
        if (cateMap != null) {
            inputForm.setPictureDataPath(cateMap.get("staffImagePathName"));
            inputForm.setPictureDataFileName(cateMap.get("staffImageFileName"));
            inputForm.setPictureUpFlg(null);
        } else {
            inputForm.setPictureDataPath(null);
            inputForm.setPictureDataFileName(null);
            inputForm.setPictureUpFlg(null);
        }

	}
}
