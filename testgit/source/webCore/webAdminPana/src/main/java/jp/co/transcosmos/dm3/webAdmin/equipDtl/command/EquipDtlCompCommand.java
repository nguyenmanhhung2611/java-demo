package jp.co.transcosmos.dm3.webAdmin.equipDtl.command;

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
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.equipDtl.form.EquipDtlInfoFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 管理者用設備情報の追加、削除処理.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータを受け取り、バリデーションを実行する。</li>
 * <li>バリデーションが正常終了した場合、管理者用設備情報を新規登録する。</li>
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
 * <pre>
 * 担当者       修正日     修正内容
 * ------------ ----------- -----------------------------------------------------
 * 郭中レイ     2015.4.14  新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class EquipDtlCompCommand implements Command {

	/** 項目数量 */
	private final static int _CONT = 30;

	/** 設備項目 */
	private final static String _EQUIP_CATEGORY = "adminEquipName";

	/** 設備 */
	private final static String _EQUIP = "adminEquipInfo";

	/** リフォーム */
	private final static String _REFORM = "adminEquipReform";

	/** 処理モード (insert = 新規登録処理、 update=更新処理) */
	private String mode;

	/** 物件情報用 Model オブジェクト */
	private PanaHousingPartThumbnailProxy panaHousingManage;

	/**
	 * 処理モードを設定する<br/>
	 * <br/>
	 *
	 * @param mode
	 *            "insert" = 新規登録処理、"update" = 更新処理
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 物件情報用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaHousingPartThumbnailProxy 物件情報用 Model オブジェクト
	 */
	public void setPanaHousingManage(PanaHousingPartThumbnailProxy panaHousingManage) {
		this.panaHousingManage = panaHousingManage;
	}

	/**
	 * 設備情報の追加、削除処理<br>
	 * <br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// リクエストパラメータを格納した model オブジェクトを生成する。
		// このオブジェクトは View 層への値引渡しに使用される。
		Map<String, Object> model = createModel(request);
		EquipDtlInfoForm inputForm = (EquipDtlInfoForm) model.get("inputForm");

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

		// 事前チェック
		if (!targetExist(inputForm)) {
			throw new NotFoundException();
		}

		// 各種処理を実行
		execute(inputForm, loginUser);

		model.put("inputForm", inputForm);

		return new ModelAndView(viewName, model);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 *
	 * @param request
	 *            HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	private Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		EquipDtlInfoFormFactory factory = EquipDtlInfoFormFactory.getInstance(request);
		EquipDtlInfoForm requestForm = factory.createEquipDtlInfoForm(request);

		model.put("inputForm", requestForm);

		PanaHousingFormFactory housingFactory = PanaHousingFormFactory.getInstance(request);
		PanaHousingSearchForm searchForm = housingFactory.createPanaHousingSearchForm(request);
		model.put("searchForm", searchForm);

		return model;
	}

	/**
	 * 事前チェックを行う。<br/>
	 * <br/>
	 *
	 * @param inputForm
	 *            入力値が格納された Form オブジェクト
	 * @return true データが存在の場合、 false データが存在しない場合
	 * @exception Exception
	 *                実装クラスによりスローされる任意の例外
	 * @exception NotFoundException
	 *                更新対象が存在しない場合
	 */
	private boolean targetExist(EquipDtlInfoForm inputForm) throws Exception,
			NotFoundException {

		// 物件情報を取得する。
		Housing housing = this.panaHousingManage.searchHousingPk(inputForm.getSysHousingCd(), true);

		// データの存在しない場合。
		if (housing == null) {
			return false;
		}
		return true;
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
	private void execute(EquipDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception, NotFoundException {

		if ("update".equals(this.mode)) {
			// 登録処理
			update(inputForm, loginUser);
		}
	}

	/**
	 * 新規登録処理<br/>
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
	private void update(EquipDtlInfoForm inputForm, AdminUserInterface loginUser)
			throws Exception {

		// 登録情報となる Map オブジェクト
		Map<String, String> inputData = new HashMap<String, String>();

		int idx = 0;

		for (int i = 0; i < _CONT; i++) {
			if (!StringUtils.isEmpty(inputForm.getEquipCategory()[i])) {

				idx++;

				// 設備項目
				inputData.put(getkeyNameNm(_EQUIP_CATEGORY, idx), inputForm.getEquipCategory()[i]);

				// 設備
				inputData.put((getkeyNameNm(_EQUIP, idx)), inputForm.getEquip()[i]);

				// リフォーム
				inputData.put((getkeyNameNm(_REFORM, idx)), (StringUtils.isEmpty(inputForm.getReform()[i])) ? "0" : inputForm.getReform()[i]);
			}
		}

		// Map が null の場合、削除のみを実行して復帰する。
		if (inputData.isEmpty()) {
			inputData = null;
		}

		// 登録処理
		this.panaHousingManage.updExtInfo(inputForm.getSysHousingCd(), "adminEquip", inputData, (String) loginUser.getUserId());

	}

	/**
	 * Key名を生成する<br/>
	 * <br/>
	 *
	 * @param str Key名
	 * @param i Key名番号
	 * @return Key名
	 */
	private String getkeyNameNm(String str, int i) {
		StringBuffer keyName = new StringBuffer();
		keyName.append(str);
		keyName.append(String.format("%02d", i));
		return keyName.toString();
	}
}
