package jp.co.transcosmos.dm3.adminCore.pwdChange.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.AdminUserManage;
import jp.co.transcosmos.dm3.core.model.adminUser.AdminUserInterface;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.PwdChangeForm;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.util.AdminLoginUserUtils;
import jp.co.transcosmos.dm3.core.validation.CompareValidation;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.EncodingUtils;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 管理ユーザーパスワード変更処理.
 * <p>
 * ログインユーザー自身のパスワードを変更する。<br/>
 * <br/>
 * 【復帰する View 名】<br/>
 * <ul>
 * <li>success</li>:完了画面表示
 * <li>input</li>:入力画面表示
 * </ul>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.04	Shamaison を参考に新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 
 */
public class PwdChangeCompCommand implements Command {

	/** 管理ユーザーメンテナンスを行う Model オブジェクト */
	protected AdminUserManage userManager;

	/** フォームのファクトリーオブジェクト */
	protected AdminUserFormFactory formFactory;

	/** 共通パラメータオブジェクト */
    protected CommonParameters commonParameters;

	/** 共通コード変換処理 */
    protected CodeLookupManager codeLookupManager;
	


	/**
	 * 管理ユーザーメンテナンスを行う Model　オブジェクトを設定する。<br/>
	 * <br/>
	 * @param userManager 管理ユーザーメンテナンスの model オブジェクト
	 */
	public void setUserManager(AdminUserManage userManager) {
		this.userManager = userManager;
	}



	/**
	 * 管理ユーザパスワードの変更処理<br>
	 * <br>
	 * @param request クライアントからのHttpリクエスト。
	 * @param response クライアントに返すHttpレスポンス。
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> model = new HashMap<>();

		// Form のファクトリーを取得
		this.formFactory = AdminUserFormFactory.getInstance(request);

		// 共通パラメータオブジェクトを取得
	    this.commonParameters = CommonParameters.getInstance(request);

	    // 共通コード変換処理を取得
	    this.codeLookupManager = (CodeLookupManager) request.getAttribute("codeLookupManager");


		// リクエストパラメータの取得
		PwdChangeForm inputform = this.formFactory.createPwdChangeForm(request);
		model.put("inputform", inputform);


		// ログイン情報をセッションから取得
		AdminUserInterface userInfo = AdminLoginUserUtils.getInstance(request).getLoginUserInfo(request, response);

		// パスワードの最終更新日を取得
		model.put("pwdChangeDate", userInfo.getLastPasswdChange());


		// バリデーションの実行
        List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
        if (!inputform.validate(errors)){

        	// バリデーションエラー時は、エラー情報を model に設定し、入力画面を表示する。
        	model.put("errors", errors);
        	return new ModelAndView("input", model);
        }


        // 旧パスワードの照合を行う
        if (!oldPasswordCheck(userInfo, inputform, errors)) {

        	// 旧パスワードの入力間違時は、エラー情報を model に設定し、入力画面を表示する。
        	model.put("errors", errors);
        	return new ModelAndView("input", model);

        }

        // パスワードを更新する。
        updatePassword(userInfo, inputform);

        // note
        // PasswordExpireFilter　は、期限切れを認識した場合、passwordExpireFailed の
        // アトリビュート名でセッションにフラグを設定する。
        // この画面は、そのフラグを見て、期限切れである事を伝えるメッセージを表示している。
        // このフラグは、フィルターの対象画面に遷移すればリセットされるが、フィルターの対象外を遷移して
        // いる間はリセットされない。
        // よって、パスワードの変更が行われた場合は明示的にセッションからフラグを削除する。
        request.getSession().removeAttribute("passwordExpireFailed");


        return new ModelAndView("success", model);
	}



	/**
	 * 旧パスワードの妥当性をチェックする。<br/>
	 * セッションから取得したログインユーザー情報 のユーザーID をキーとして、現在のログインパスワードを取得する。<br/>
	 * パスワードの値はハッシュされた値で格納されている為、入力した値もハッシュしてから比較する。<br/>
	 * <br/>
	 * レアケースだが、セッションに格納されたユーザー情報が DB に存在しない可能性がある。　その場合、処理の継続が
	 * 困難な為、システムエラーとする。<br/>
	 * <br/>
	 * @param userInfo ログインユーザー情報
	 * @param inputform 画面入力した値 （旧パスワードの値）
	 * @param errors エラーメッセージ用リストオブジェクト
	 * 
	 * @return true = 正常、false = エラー
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 */
	protected boolean oldPasswordCheck(AdminUserInterface userInfo, PwdChangeForm inputform, List<ValidationFailure> errors)
			throws Exception {

		// 現在のパスワードを取得する。
        AdminUserSearchForm searchForm = this.formFactory.createUserSearchForm();
        searchForm.setUserId((String)userInfo.getUserId());
        JoinResult result = this.userManager.searchAdminUserPk(searchForm);
        if (result == null){
        	// ログイン中に管理ユーザーメンテナンスでアカウントを削除すると、この状態になる可能性がある。
        	// アカウントが存在しない場合、どうする事も出来ないので、そのままシステムエラーにする。
        	throw new RuntimeException ("admin user is deleted.");
        }

        AdminUserInterface adminUser = (AdminUserInterface)result.getItems().get(this.commonParameters.getAdminUserDbAlias());

        // パスワードはハッシュされた値で格納されているので、ハッシュした値で照合する。
        ValidationChain oldPwd = new ValidationChain("user.pwd.oldPassword", EncodingUtils.md5Encode(inputform.getOldPassword()));

        // メッセージで使用するラベルを取得
        String label = this.codeLookupManager.lookupValue("errorCustom", "db_old_value");
        
        // 旧パスワードの照合
        oldPwd.addValidation(new CompareValidation(adminUser.getPassword(), label));
        
        return oldPwd.validate(errors);

	}

	

	/**
	 * ログインユーザーのパスワードを変更する。<br/>
	 * <br/>
	 * @param userInfo ログインユーザー情報
	 * @param inputform 画面入力した値 （旧パスワードの値）
	 * 
	 * @exception Exception 実装クラスによりスローされる任意の例外
	 * @exception NotFoundException 更新対象が存在しない場合
	 */
	protected void updatePassword(AdminUserInterface userInfo, PwdChangeForm inputform)
			throws Exception, NotFoundException {

		// セッションから取得したログイン情報からユーザーＩＤを取得
		String userId = (String)userInfo.getUserId();

		// パスワードの変更処理
		// パスワード変更で更新対象レコードが存在しない場合、処理を継続させる事自体が困難なので
		// そのまま　NotFoundException　例外を上位へスローする。
        this.userManager.changePassword(inputform, userId, userId);


        // パスワードの変更が成立したので、セッションに格納されているログイン情報のパスワード最終更新日を
        // 現在のシステム日付に変更する。

        // note
        // この方法だと、DB に実際に更新した値と誤差が生じるが、実用的には問題ない範囲なので、
        // インターフェースを変更せずにこの方法で実装している。
        userInfo.setLastPasswdChange(new Date());

	}

}
