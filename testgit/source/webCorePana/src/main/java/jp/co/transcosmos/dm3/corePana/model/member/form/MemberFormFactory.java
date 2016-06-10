package jp.co.transcosmos.dm3.corePana.model.member.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MemberFormFactory extends MypageUserFormFactory {

	/**
     * PanaMemberFormFactory のインスタンスを取得する。<br/>
     * Spring のコンテキストから、 PanaMemberFormFactory で定義された PanaMemberFormFactory の
     * インスタンスを取得する。<br/>
     * 取得されるインスタンスは、PanaMemberFormFactory を継承した拡張クラスが復帰される場合もある。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return PanaMemberFormFactory、または継承して拡張したクラスのインスタンス
     */
    public static MemberFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (MemberFormFactory) springContext.getBean(MemberFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * 会員情報の MemberInfoForm のインスタンスを生成する。<br/>
     * <br/>
     * @return 空の MemberInfoForm インスタンス
     */
    @Override
    public MypageUserForm createMypageUserForm() {
        return new MemberInfoForm(this.lengthUtils, this.codeLookupManager, this.commonParameters);
    }
    /**
     * 会員情報の検索結果、および検索条件を格納する MemberSearchForm のインスタンスを生成する。<br/>
     * MemberSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
     * <br/>
     * @param request HTTP リクエスト
     * @return リクエストパラメータを設定した MemberSearchForm インスタンス
     */
    @Override
    public MypageUserForm createMypageUserForm(HttpServletRequest request) {
    	MypageUserForm form = createMypageUserForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * マイページ会員の検索結果、および検索条件を格納する空の MypageUserSearchForm のインスタンスを生成する。<br/>
	 * <br/>
	 * @return 空の MypageUserSearchForm インスタンス
	 */
    @Override
	public MemberSearchForm createMypageUserSearchForm() {
		return new MemberSearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * マイページ会員の検索結果、および検索条件を格納する MypageUserSearchForm のインスタンスを生成する。<br/>
	 * MypageUserSearchForm には、リクエストパラメータに該当する値を設定する。<br/>
	 * <br/>
	 * @param request HTTP リクエスト
	 * @return リクエストパラメータを設定した MypageUserSearchForm インスタンス
	 */
    @Override
	public MemberSearchForm createMypageUserSearchForm(HttpServletRequest request) {
    	MemberSearchForm form = createMypageUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
