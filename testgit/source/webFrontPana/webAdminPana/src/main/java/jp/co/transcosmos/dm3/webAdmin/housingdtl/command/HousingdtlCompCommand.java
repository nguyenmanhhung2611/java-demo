package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.building.BuildingPartThumbnailProxy;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingForm;
import jp.co.transcosmos.dm3.corePana.model.building.form.PanaBuildingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 物件詳細情報登録処理
 * <p>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、物件詳細情報を登録する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:正常終了（リダイレクトページ）
 * <li>input</li>:バリデーションエラーによる再入力
 * <li>redirect</li>:redirect画面表示
 * <li>comp</li>:完了画面表示
 * </ul>
 * <p>
 *
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * liu.yandong  2015.04.06  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingdtlCompCommand implements Command {
	/** 処理モード (edit = 編集、editBack = 再編集) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;


	private BuildingPartThumbnailProxy buildingManager;


	/** 共通パラメータオブジェクト */
	protected PanaCommonParameters commonParameters;


	/** サムネイル画像作成クラス */
	private ImgUtils imgUtils;


	/**
	 * @param commonParameters セットする commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	public void setBuildingManager(BuildingPartThumbnailProxy buildingManager) {
		this.buildingManager = buildingManager;
	}

	/**
	 * @param imgUtils セットする imgUtils
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

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
	 * 物件詳細情報登録処理<br>
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

		// ログインユーザーの情報を取得する。　（タイムスタンプの更新用）
		AdminUserInterface loginUser = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// view 名の初期値を設定
		String viewName = "success";

		String command = inputForm.getCommand();

		if (command != null && command.equals("redirect")) {
			return new ModelAndView("comp", model);
		}
		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("inputForm", inputForm);
			model.put("errors", errors);
			viewName = "input";
			return new ModelAndView(viewName, model);
		}


		// 各種処理を実行
		execute(inputForm, loginUser);

		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);

	}

	/**
	 * 処理の振り分けと実行を行う。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private void execute(PanaHousingDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if ("update".equals(this.mode)) {
			// 登録処理
			update(inputForm, loginUser);
		}
	}


	/**
	 * 登録処理<br/>
	 * 引数で渡された内容でリフォーム情報を追加する。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @param loginUser
	 *            タイムスタンプ更新時に使用するログインユーザー情報
	 *
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 */
	private void update(PanaHousingDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 事前チェック
		// 物件情報を取得する。
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);
		// データの存在しない場合。
		if (housing == null) {
			throw new NotFoundException();
		}


		/** 物件基本情報テーブル更新処理  **/
        // 物件基本情報を取得する。
        HousingInfo housingInfo = ((HousingInfo) housing.getHousingInfo().getItems().get("housingInfo"));
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingFormFactory panaHousingFormFactory = new PanaHousingFormFactory();
		// PanaBuildingFormを設定する
		PanaHousingForm panaHousingForm = panaHousingFormFactory.createPanaHousingForm();
		PanaCommonUtil.copyProperties(panaHousingForm, housingInfo);
		//画面フォーム値を設定
		panaHousingForm.setSysHousingCd(inputForm.getSysHousingCd());
		panaHousingForm.setDisplayParkingInfo(inputForm.getDisplayParkingInfo());
		panaHousingForm.setFloorNo(inputForm.getFloorNo());
		panaHousingForm.setFloorNoNote(inputForm.getFloorNoNote());
		panaHousingForm.setUpkeep(inputForm.getUpkeep());
		panaHousingForm.setMenteFee(inputForm.getMenteFee());
		panaHousingForm.setBasicComment(inputForm.getBasicComment());
		panaHousingForm.setReformComment(inputForm.getReformComment());
		//物件基本情報テーブルのupdate処理
		this.panaHousingManage.updateHousing(panaHousingForm, String.valueOf(loginUser.getUserId()));


		/** 建物基本情報テーブル更新処理  **/
		// 建物基本情報を取得する。
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaBuildingFormFactory panaBuildingFormFactory = new PanaBuildingFormFactory();
		// PanaBuildingFormを設定する
		PanaBuildingForm panaBuildingForm = panaBuildingFormFactory.createPanaBuildingForm();
		PanaCommonUtil.copyProperties(panaBuildingForm, buildingInfo);
		//画面フォーム値を設定
		panaBuildingForm.setSysBuildingCd(inputForm.getSysBuildingCd());
		panaBuildingForm.setTotalFloors(inputForm.getTotalFloors());
		// 竣工年月を設定
		SimpleDateFormat beforeFormartDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat afterFormartDate = new SimpleDateFormat("yyyyMM");
		String strDate = panaBuildingForm.getCompDate();
		if (!StringValidateUtil.isEmpty(strDate)) {
			Date date = beforeFormartDate.parse(strDate);
			panaBuildingForm.setCompDate(afterFormartDate.format(date));
		}

		//建物基本情報テーブルのupdate処理
		this.buildingManager.updateBuildingInfo(panaBuildingForm, String.valueOf(loginUser.getUserId()));


		/** 物件詳細情報テーブル更新処理  **/
		//物件詳細情報テーブルのupdate処理
		this.panaHousingManage.updateHousingDtl(inputForm, String.valueOf(loginUser.getUserId()));


		/** 建物詳細情報テーブル更新処理  **/
		// 建物詳細情報を取得する。
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
		// リクエストパラメータを取得して Form オブジェクトを作成する。
		PanaHousingInfoForm panaHousingInfoForm = panaHousingFormFactory.createPanaHousingInfoForm();
		PanaCommonUtil.copyProperties(panaHousingInfoForm, buildingDtlInfo);
		//画面フォーム値を設定
		panaHousingInfoForm.setSysBuildingCd(inputForm.getSysBuildingCd());
		panaHousingInfoForm.setCoverageMemo(inputForm.getCoverageMemo());
		panaHousingInfoForm.setBuildingRateMemo(inputForm.getBuildingRateMemo());
		//建物詳細情報テーブルの更新処理
		this.panaHousingManage.updateBuildingDtlInfo(panaHousingInfoForm, String.valueOf(loginUser.getUserId()));

		// [物件種別CD]/[都道府県CD]/[市区町村CD]/システム物件番号/
		String prefCd="";
		String addressCd="";
		if (StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
			prefCd = "99";
		} else {
			prefCd = buildingInfo.getPrefCd();
		}
		if (StringValidateUtil.isEmpty(buildingInfo.getAddressCd())) {
			addressCd = "99999";
		} else {
			addressCd = buildingInfo.getAddressCd();
		}
		String upPath = buildingInfo.getHousingKindCd() + "/" +
						prefCd + "/" +
						addressCd + "/" +
						housingInfo.getSysHousingCd() + "/";

		/** 旧スタッフ画像のファイルを削除するため、物件拡張属性情報を取得する。 **/
        Map<String, Map<String, String>> extMap = housing.getHousingExtInfos();
        // カテゴリ名に該当する Map を取得する。
        Map<String, String> cateMap = extMap.get("housingDetail");
        String oldStaffImagePathName = "";
        String oldStaffImageFileName = "";
        if (cateMap != null) {
            oldStaffImagePathName = cateMap.get("staffImagePathName");
            oldStaffImageFileName = cateMap.get("staffImageFileName");
        }

		//担当者写真アップロード
		if (StringValidateUtil.isEmpty(inputForm.getPictureDataDelete()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureUpFlg()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureDataPath()) &&
				!StringValidateUtil.isEmpty(inputForm.getPictureDataFileName())) {

			/** サムネイルを作成するファイル名のマップオブジェクトを作成する。 **/
			Map<String, String> thumbnailMap = new HashMap<>();

			// Map の Key は、サムネイル作成元のファイル名（フルパス）
			String key = inputForm.getPictureDataPath() + inputForm.getPictureDataFileName();
			// Map の Value は、サムネイルの出力先パス（ルート～システム物件番号までのパス）
			String value = this.commonParameters.getHousImgOpenPhysicalPath() + upPath;

			thumbnailMap.put(key, value);
			// 仮フォルダのファイルを使用して公開フォルダへサムネイルを作成
			createStaffImage(thumbnailMap, PanaStringUtils.toInteger(this.commonParameters.getAdminSiteStaffImageSize()));

			/** 旧スタッフ画像のファイルを削除 **/
			if (!StringValidateUtil.isEmpty(oldStaffImagePathName)) {
				PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgOpenPhysicalPath() + oldStaffImagePathName, oldStaffImageFileName);
			}

			/** 仮フォルダのファイルを削除 **/
			PanaFileUtil.delPhysicalPathFile(inputForm.getPictureDataPath(), inputForm.getPictureDataFileName());

			inputForm.setPictureDataPath(upPath);
			inputForm.setPictureDataFileName(inputForm.getPictureDataFileName());
		}

		//画像削除チェックボックスを選択場合、画像ファイルを削除する。
		if(!StringValidateUtil.isEmpty(inputForm.getPictureDataDelete())) {
			// 仮フォルダのファイルを削除
			PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgTempPhysicalPath() + PanaFileUtil.getUploadTempPath(), inputForm.getPictureDataFileName());
			// 公開フォルダのファイルを削除
			if (!StringValidateUtil.isEmpty(oldStaffImagePathName)) {
				PanaFileUtil.delPhysicalPathFile(this.commonParameters.getHousImgOpenPhysicalPath() + oldStaffImagePathName, oldStaffImageFileName);
			}
			inputForm.setPictureDataPath(null);
			inputForm.setPictureDataFileName(null);
		}

		/** 物件拡張属性情報テーブル更新処理  **/
		// 登録情報となる Map オブジェクト
		Map<String, String> inputData = new HashMap<String, String>();
		inputData.put("struct", inputForm.getBuildingDataValue());
		inputData.put("status", inputForm.getPreDataValue());
		inputData.put("totalHouseCnt", inputForm.getTotalHouseCntDataValue());
		inputData.put("scale", inputForm.getScaleDataValue());
		inputData.put("direction", inputForm.getOrientedDataValue());
		inputData.put("staffName", inputForm.getWorkerDataValue());
		inputData.put("staffImagePathName", inputForm.getPictureDataPath());
		inputData.put("staffImageFileName", inputForm.getPictureDataFileName());
		inputData.put("companyName", inputForm.getCompanyDataValue());
		inputData.put("branchName", inputForm.getBranchDataValue());
		inputData.put("licenseNo", inputForm.getFreeCdDataValue());
		inputData.put("infrastructure", inputForm.getInfDataValue());
		inputData.put("movieUrl", inputForm.getUrlDataValue());
		inputData.put("vendorComment", inputForm.getVendorComment());

		// 物件拡張属性情報テーブルのupdate処理
		this.panaHousingManage.updExtInfo(inputForm.getSysHousingCd(), "housingDetail", inputData,
				String.valueOf(loginUser.getUserId()));


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
	 * スタッフサムネイル画像を作成する。<br/>
	 * また、オリジナルサイズの画像を、画像サイズの階層が full のフォルダへ配置する。<br/>
	 * thumbnailMap の構造は下記の通り。　ファイル名は元のファイル名が使用される。<br/>
	 * <ul>
	 * <li>Key : サムネイル作成元のファイル名（フルパス）</li>
	 * <li>value : サムネイルの出力先パス（ルート～システム物件番号までのパス。　サイズや、ファイル名は含まない。）</li>
	 * </ul>
	 * @param thumbnailMap 作成するファイルの情報
	 * @param size 作成するファイルのサイズ
	 *
	 * @throws IOException
	 * @throws Exception 委譲先がスローする任意の例外
	 */
	private void createStaffImage(Map<String, String> thumbnailMap, Integer size)
			throws IOException, Exception {

		// 作成するファイル分繰り返す
		for (Entry<String, String> e : thumbnailMap.entrySet()){

			// サムネイル作成元のファイルオブジェクトを作成する。
			File srcFile = new File(e.getKey());

			// サムネイル出力先のルートパス （画像サイズの直前までのフォルダ階層）
			String destRootPath = e.getValue();
			if (!destRootPath.endsWith("/")) destRootPath += "/";

			// オリジナル画像をフルサイズ画像として copy する。
			FileUtils.copyFileToDirectory(srcFile, new File(destRootPath + this.commonParameters.getAdminSiteFullFolder()));


			// サイズリストが未設定の場合はサムネイル画像を作成しない。
			if (size == null) return;


			// 出力先サブフォルダが存在しない場合、フォルダを作成する。
			File subDir = new File(destRootPath + this.commonParameters.getAdminSiteStaffFolder());
			if (!subDir.exists()){
				FileUtils.forceMkdir(subDir);
			}

			// サムネイルの出力先は～/staff/に生成する。
			File destFile = new File(destRootPath + this.commonParameters.getAdminSiteStaffFolder() + "/" + srcFile.getName());
			// サムネイル画像を作成
			this.imgUtils.createImgFile(srcFile, destFile, size.intValue());
		}
	}
}
