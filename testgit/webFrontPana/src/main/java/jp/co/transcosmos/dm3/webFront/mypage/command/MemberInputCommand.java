package jp.co.transcosmos.dm3.webFront.mypage.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberFormFactory;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;

import org.springframework.web.servlet.ModelAndView;

/**
 * マイページ会員情報入力画面.
 * <p>
 * 【新規登録の場合】<br/>
 * <ul>
 * <li>入力画面を表示する。</li>
 * </ul>
 * <br/>
 * 【更新登録の場合】<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。（主キー値のみ）</li>
 * <li>リクエストパラメータ（userId）に該当するマイページ会員情報を取得する。</li>
 * <li>取得した値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【リクエストパラメータ（command） が "back"の場合】（確認画面からの復帰の場合）<br/>
 * <ul>
 * <li>リクエストパラメータで送信された値を受取る。</li>
 * <li>受け取った値を入力画面に表示する。</li>
 * </ul>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success<li>:検索処理正常終了
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * Zhang		2015.04.29	新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 *
 */
public class MemberInputCommand extends jp.co.transcosmos.dm3.frontCore.mypage.command.MemberInputCommand implements Command {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * 会員情報入力画面表示処理<br>
	 * <br>
	 * @param request HTTP リクエスト
	 * @param response HTTP レスポンス
	*/
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

        // form取得
		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");

		// ログインユーザーの情報を取得する。
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(
				request, response);

		String command = inputForm.getCommand();

		// 処理モードを判断する。
		if ("insert".equals(this.mode) && !"back".equals(command)) {
			// ラジオボタン初期化
			inputForm.setRefCd(request.getParameter("refCd"));
			// メール配信の初期値設定
			inputForm.setMailSendFlg(PanaCommonConstant.SEND_FLG_1);
		}

		if ("update".equals(this.mode) && !"back".equals(command)){

			// マイページアンケート情報取得を行う。
			execute(inputForm, loginUser.getUpdUserId());
			inputForm.setPassword(null);
			inputForm.setPasswordChk(null);

		}

		// 都道府県マスタを取得する
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);

		return new ModelAndView("success", model);
	}

	/**
	 * model オブジェクトを作成し、リクエストパラメータを格納した form オブジェクトを格納する。<br/>
	 * <br/>
	 * @param request HTTP リクエストパラメータ
	 * @return パラメータを設定した Form を格納した model オブジェクトを復帰する。
	 */
	@Override
	protected Map<String, Object> createModel(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<>();

		// リクエストパラメータを取得して Form オブジェクトを作成する。
		MemberFormFactory factory = MemberFormFactory.getInstance(request);


		// 入力フォームの受け取りは、command パラメータの値によって異なる。
		// command パラメータが渡されない場合、入力画面の初期表示とみなし、空のフォームを復帰する。
		// command パラメータで "back" が渡された場合、入力確認画面からの復帰を意味する。
		// その場合はリクエストパラメータを受け取り、入力画面へ初期表示する。
		// もし、"back" 以外の値が渡された場合、command パラメータが渡されない場合と同等に扱う。

		MypageUserForm inputForm = factory.createMypageUserForm(request);
		String command = inputForm.getCommand();

		if (command != null && command.equals("back")){
			model.put("inputForm", inputForm);
		} else {
			model.put("inputForm", factory.createMypageUserForm());
		}

		return model;

	}

	/**
	 * マイページアンケート情報取得を行う。<br/>
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
	private void execute(MemberInfoForm inputForm, String userId) throws Exception, NotFoundException  {

		// マイページアンケート情報取得
		List<MemberQuestion> memberQuestionList =
				((PanaMypageUserManageImpl) this.userManager).searchMemberQuestionPk(userId);

		// Formをセットする
		inputForm.setMemberQuestion(memberQuestionList);
	}

}
