package jp.co.transcosmos.dm3.corePana.model.adminUser.form;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserFormFactory;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchCsvForm;
import jp.co.transcosmos.dm3.core.model.adminUser.form.AdminUserSearchForm;

public class PanaAdminUserFormFactory extends AdminUserFormFactory {

	/**
	 * PanaAdminUserFormFactory のインスタンスを取得する。<br/>
	 * Spring のコンテキストから、 adminUserFormFactory で定義された PanaAdminUserFormFactory の
	 * インスタンスを取得する。<br/>
	 * 取得されるインスタンスは、adminUserFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return PanaAdminUserFormFactory、または継承して拡張したクラスのインスタンス
	 */
	public static PanaAdminUserFormFactory getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (PanaAdminUserFormFactory)springContext.getBean(PanaAdminUserFormFactory.FACTORY_BEAN_ID);
	}



	/**
	 * 管理ユーザーの検索結果、および検索条件を格納する空の PanaAdminUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の PanaAdminUserSearchForm インスタンス
	 */
	@Override
	public AdminUserSearchForm createUserSearchForm(){
		return new PanaAdminUserSearchForm(this.lengthUtils);
	}



	/**
	 * CSV 出力用の管理ユーザーの検索結果、および検索条件を格納する空の AdminUserSearchCsvForm
	 * のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の AdminUserSearchCsvForm インスタンス
	 */
	@Override
	public AdminUserSearchCsvForm createUserSearchCsvForm(){
		AdminUserSearchCsvForm adminUserSearchCsvForm = super.createUserSearchCsvForm();
		adminUserSearchCsvForm.setRowsPerPage(Integer.MAX_VALUE);
		return adminUserSearchCsvForm;
	}

}
