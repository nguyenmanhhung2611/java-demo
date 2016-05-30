package jp.co.transcosmos.dm3.webAdmin.memberInfo.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.adminCore.mypage.command.MypageInputCommand;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.member.PanaMypageUserManageImpl;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberInfoForm;
import jp.co.transcosmos.dm3.corePana.model.member.form.MemberSearchForm;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 会員情報編集画面
 * リクエストパラメータで渡された会員情報のバリデーションを行い、編集画面を表示する。
 * もしバリデーションエラーが発生した場合は入力画面として表示する。
 *
 * 【復帰する View 名】
 *    ・"success" : 検索処理正常終了
 *    ・"input" : バリデーションエラーによる再入力
 *
 * 担当者         修正日      修正内容
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun	  2015.04.13	新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class MemberInfoEditCommand extends MypageInputCommand {

	/** 共通情報取用 Model オブジェクト */
	private PanaCommonManage panamCommonManager;

	/** 会員情報用 Model オブジェクト */
	private PanaMypageUserManageImpl mypageUserManager;


	/**
	 * 共通情報取用 Model オブジェクトを設定する。<br/>
	 * <br/>
	 * @param PanaCommonManage 共通情報取用 Model オブジェクト
	 */
	public void setPanamCommonManager(PanaCommonManage panamCommonManager) {
		this.panamCommonManager = panamCommonManager;
	}

	/**
	 * @param mypageUserManager セットする mypageUserManager
	 */
	public void setMypageUserManager(PanaMypageUserManageImpl mypageUserManager) {
		this.mypageUserManager = mypageUserManager;
	}

	/**
	 * 会員情報編集画面表示処理<br>
	 *
	 * @param request
	 *            クライアントからのHttpリクエスト。
	 * @param response
	 *            クライアントに返すHttpレスポンス。
	 */
    @Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        // model オブジェクトを取得する。
        ModelAndView modelAndView = super.handleRequest(request, response);
        Map<String, Object> model = modelAndView.getModel();

        // form取得
		MemberInfoForm inputForm = (MemberInfoForm) model.get("inputForm");
		MemberSearchForm searchForm = (MemberSearchForm) model.get("searchForm");

		String command = inputForm.getCommand();

		// 処理モードを判断する。
		if ("insert".equals(this.mode) && !"back".equals(command)) {
				// ラジオボタン初期化
				inputForm.setMailSendFlg("1");
				inputForm.setEntryRoute("001");
				inputForm.setLockFlg("0");
		}
		if ("update".equals(this.mode) && !"back".equals(command)){
				// マイページアンケート情報取得を行う。
				execute(inputForm, searchForm);
				inputForm.setPassword(null);
				inputForm.setPasswordChk(null);

		}
		if ("getAddress".equals(this.mode)) {
			// 住所検索処理
			// バリデーションを実行
			List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
			if (!inputForm.validate(errors, this.mode)) {
				// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
				model.put("errors", errors);
			} else {
				// 市区町村マスタを取得する
				String[] zipMst = panamCommonManager.getZipToAddress(inputForm.getZip());
				// 郵便番号の対する住所がない場合
				if(zipMst == null || zipMst[0] != "0"){
					ValidationFailure vf = new ValidationFailure(
							"housingInfoZipInput","", "",  null);
		            errors.add(vf);
		            model.put("errors", errors);
				}
				inputForm.setPrefCd(zipMst[1]);
				inputForm.setAddress(panamCommonManager.getAddressName(zipMst[2]));
			}
		}

		// 都道府県マスタを取得する
 		List<PrefMst> prefMstList = this.panamCommonManager.getPrefMstList();
		model.put("prefMstList", prefMstList);


		return modelAndView;
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
	private void execute(MemberInfoForm inputForm, MemberSearchForm searchForm) throws Exception, NotFoundException  {

		// マイページアンケート情報取得
		List<MemberQuestion> memberQuestionList = this.mypageUserManager.searchMemberQuestionPk(searchForm.getUserId());

		// Formをセットする
		inputForm.setMemberQuestion(memberQuestionList);
	}

}