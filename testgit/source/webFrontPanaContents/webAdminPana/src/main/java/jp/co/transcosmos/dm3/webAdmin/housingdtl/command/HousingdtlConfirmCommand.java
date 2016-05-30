package jp.co.transcosmos.dm3.webAdmin.housingdtl.command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.util.ImgUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.corePana.model.reform.ReformPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.io.FileUtils;
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
 * liu.yandong  2015.04.06  新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class HousingdtlConfirmCommand implements Command {
	/** 共通パラメータオブジェクト */
	protected PanaCommonParameters commonParameters;

    /** リフォーム情報メンテナンスを行う Model オブジェクト */
    private ReformPartThumbnailProxy reformManager;

	/** サムネイル画像作成クラス */
	private ImgUtils imgUtils;

	/** Panasonic用ファイル処理関連共通Util */
	private PanaFileUtil panaFileUtil;

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

	/**
	 * @param commonParameters セットする commonParameters
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * @param imgUtils セットする imgUtils
	 */
	public void setImgUtils(ImgUtils imgUtils) {
		this.imgUtils = imgUtils;
	}

	/**
	 * @param panaFileUtil セットする panaFileUtil
	 */
	public void setPanaFileUtil(PanaFileUtil panaFileUtil) {
		this.panaFileUtil = panaFileUtil;
	}

	/**
	 * 物件詳細情報確認画面表示処理<br>
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

		// view 名の初期値を設定
		String viewName = "success";

		// バリデーションを実行
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
		if (!inputForm.validate(errors)) {
			// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
			model.put("errors", errors);
			model.put("inputForm", inputForm);
			viewName = "input";
			return new ModelAndView(viewName, model);
		}

		//担当者写真アップロード
		if (StringValidateUtil.isEmpty(inputForm.getPictureDataDelete()) && inputForm.getPictureDataValue().getSize() > 0) {
			// サムネイルの作成元フォルダ名　（日付を指定したフォルダ階層まで）
			String srcRoot = this.commonParameters.getHousImgTempPhysicalPath();
            // 仮フォルダ名　（日付部分）
            String tempDate = PanaFileUtil.getUploadTempPath();
            // ファイル名を取得
            String fileName = reformManager.getReformJpgFileName();

			inputForm.setPictureDataPath(srcRoot + tempDate + "/");
			inputForm.setPictureDataFileName(fileName);
			inputForm.setPictureUpFlg("1");
			// テンプレートフォルダへアップロード
			PanaFileUtil.uploadFile(inputForm.getPictureDataValue(),
							PanaFileUtil.conPhysicalPath(srcRoot, tempDate),
							fileName, 1);

			/** サムネイルを作成するファイル名のマップオブジェクトを作成する。 **/
			Map<String, String> thumbnailMap = new HashMap<>();

			// Map の Key は、サムネイル作成元のファイル名（フルパス）
			String key = inputForm.getPictureDataPath() + inputForm.getPictureDataFileName();
			// Map の Value は、サムネイルの出力先パス（ルート～システム物件番号までのパス）
			String value = inputForm.getPictureDataPath();

			thumbnailMap.put(key, value);
			// サムネイルを作成
			createStaffImage(thumbnailMap, PanaStringUtils.toInteger(this.commonParameters.getAdminSiteStaffImageSize()));
			// プレビュー画像用パス設定
			String previewImgPath = this.panaFileUtil.getHousFileTempUrl(tempDate + "/" + this.commonParameters.getAdminSiteStaffFolder(), fileName);
			inputForm.setPreviewImgPath(previewImgPath);
		}
		inputForm.setDtlComment1(PanaStringUtils.encodeHtml(inputForm.getDtlComment()));
		inputForm.setBasicComment1(PanaStringUtils.encodeHtml(inputForm.getBasicComment()));
		inputForm.setVendorComment1(PanaStringUtils.encodeHtml(inputForm.getVendorComment()));
		inputForm.setReformComment1(PanaStringUtils.encodeHtml(inputForm.getReformComment()));
		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);

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
		Object[] forms = factory.createPanaHousingDtlInfoFormAndSearchForm(request);

		model.put("inputForm", (PanaHousingDtlInfoForm)forms[0]);
		model.put("searchForm", (PanaHousingSearchForm)forms[1]);

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
