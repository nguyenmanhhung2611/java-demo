package jp.co.transcosmos.dm3.webAdmin.housinginspection.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;
/**
 * 住宅診断情報変更完了画面
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータスを新規登録する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、ステータス情報を更新する。</li>
 * <li>もし、更新対象データが存在しない場合、更新処理が継続できないので該当無し画面を表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>notFound</li>:該当データが存在しない場合（更新処理の場合）
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 *
 * <pre>
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
public class HousingInspectionUpdateCommand implements Command {

	/** 住宅診断情報メンテナンスを行う Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/**
	 * 住宅診断情報メンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 *
	 * @param housingManager
	 *            住宅診断情報メンテナンスの model オブジェクト
	 */
	public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
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

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);
		// リクエストパラメータを格納した form オブジェクトを生成する。
		PanaHousingInspectionForm form = factory.createPanaHousingInspectionForm(request);
		PanaHousingSearchForm searchForm = factory.createPanaHousingSearchForm(request);
		// ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// ユーザIDを取得
        String userId = String.valueOf(loginUser.getUserId().toString());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("searchForm", searchForm);

		String command = form.getCommand();
		String sysHousingCd = form.getSysHousingCd();
		if (command != null && command.equals("redirect")) {
			model.put("sysHousingCd", sysHousingCd);
			return new ModelAndView("comp", model);
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

		// 事前チェック:物件基本情報テーブルの読込
		Housing housing = this.panaHousingManager.searchHousingPk(form
				.getSysHousingCd(),true);
		if (housing == null) {
			// メッセージ：FORM.物件番号物件情報が存在しない"表示
			throw new NotFoundException();
		}
		// 物件インスペクション情報のDB処理
		// 物件インスペクション情報のupdate処理
		this.panaHousingManager.delHousingInspection(form.getSysHousingCd());

		// リフォーム情報の追加処理
		if (form.getInspectionKey() != null) {

			for (int i = 0; i < form.getInspectionKey().length; i++) {
				if(!StringValidateUtil.isEmpty(form.getInspectionTrust_result()[i])){
					this.panaHousingManager.addHousingInspection(form, i);
				}
			}
		}
		// 住宅診断実施の物件拡張属性情報のDB処理
		this.panaHousingManager.updExtInfo(form.getSysHousingCd(), "housingInspection","inspectionExist", form.getHousingInspection(), userId);

		// 画像ファイルのパスを取得用
		HousingImageInfo imgInfo = new HousingImageInfo();
		imgInfo.setRoleId(PanaCommonConstant.ROLE_ID_PRIVATE);

		// 住宅診断ファイル情報の処理
		if ("on".equals(form.getHousingDel()) && !"1".equals(form.getLoadFlg())) {
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionPathName", userId);
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionFileName", userId);

			// 住宅診断ファイルの削除処理を実行する。
			this.panaHousingManager.deleteImgFile(form.getLoadFilePath(), form.getLoadFile() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
		}else if("1".equals(form.getLoadFlg())){

			// パス名を設定する。
			String filePath = this.panaHousingManager.createImagePath(housing);

			// 住宅診断ファイル情報の処理
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionPathName", filePath, userId);
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionFileName", form.getAddHidFileName(), userId);

			// ロード済みのファイルを削除し、最新のファイルをロードする。
			this.panaHousingManager.deleteImgFile(form.getLoadFilePath(), form.getLoadFile() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
			this.panaHousingManager.addImgFile(form.getSysHousingCd(), form.getAddHidFileName() ,imgInfo,this.commonParameters.getAdminSitePdfFolder());
		}

		// 画像ファイル情報の処理
		if ("on".equals(form.getHousingImgDel()) && !"1".equals(form.getImgFlg())) {
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImagePathName", userId);
			this.panaHousingManager.delExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImageFileName", userId);

			// 画像ファイルの削除処理を実行する。
			this.panaHousingManager.deleteImgFile(form.getImgFilePath(), form.getImgFile() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
		}else if("1".equals(form.getImgFlg())){
			// パス名を設定する。
			String filePath = this.panaHousingManager.createImagePath(housing);

			// 画像ファイル情報の処理
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImagePathName", filePath, userId);
			this.panaHousingManager.updExtInfo(form.getSysHousingCd(),"housingInspection", "inspectionImageFileName", form.getAddHidImgName(), userId);

			// ロード済みのファイルを削除し、最新のファイルをロードする。
			this.panaHousingManager.deleteImgFile(form.getImgFilePath(), form.getImgFile() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
			this.panaHousingManager.addImgFile(form.getSysHousingCd(), form.getAddHidImgName() ,imgInfo,this.commonParameters.getAdminSiteChartFolder());
		}



        // 物件基本情報の最終更新日と最終更新者を更新の処理
		this.panaHousingManager.updateEditTimestamp(form.getSysHousingCd(),userId);

		model.put("HousingInspectionForm", form);
		return new ModelAndView("success", model);

	}

}