package jp.co.transcosmos.dm3.corePana.model.member.form;

import javax.servlet.http.HttpServletRequest;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserFormFactory;
import jp.co.transcosmos.dm3.form.FormPopulator;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MemberFormFactory extends MypageUserFormFactory {

	/**
     * PanaMemberFormFactory �̃C���X�^���X���擾����B<br/>
     * Spring �̃R���e�L�X�g����A PanaMemberFormFactory �Œ�`���ꂽ PanaMemberFormFactory ��
     * �C���X�^���X���擾����B<br/>
     * �擾�����C���X�^���X�́APanaMemberFormFactory ���p�������g���N���X�����A�����ꍇ������B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return PanaMemberFormFactory�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
     */
    public static MemberFormFactory getInstance(HttpServletRequest request) {
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        return (MemberFormFactory) springContext.getBean(MemberFormFactory.FACTORY_BEAN_ID);
    }

    /**
     * ������� MemberInfoForm �̃C���X�^���X�𐶐�����B<br/>
     * <br/>
     * @return ��� MemberInfoForm �C���X�^���X
     */
    @Override
    public MypageUserForm createMypageUserForm() {
        return new MemberInfoForm(this.lengthUtils, this.codeLookupManager, this.commonParameters);
    }
    /**
     * ������̌������ʁA����ь����������i�[���� MemberSearchForm �̃C���X�^���X�𐶐�����B<br/>
     * MemberSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
     * <br/>
     * @param request HTTP ���N�G�X�g
     * @return ���N�G�X�g�p�����[�^��ݒ肵�� MemberSearchForm �C���X�^���X
     */
    @Override
    public MypageUserForm createMypageUserForm(HttpServletRequest request) {
    	MypageUserForm form = createMypageUserForm();
        FormPopulator.populateFormBeanFromRequest(request, form);
        return form;
    }

	/**
	 * �}�C�y�[�W����̌������ʁA����ь����������i�[������ MypageUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * <br/>
	 * @return ��� MypageUserSearchForm �C���X�^���X
	 */
    @Override
	public MemberSearchForm createMypageUserSearchForm() {
		return new MemberSearchForm(this.lengthUtils, this.codeLookupManager);
	}

	/**
	 * �}�C�y�[�W����̌������ʁA����ь����������i�[���� MypageUserSearchForm �̃C���X�^���X�𐶐�����B<br/>
	 * MypageUserSearchForm �ɂ́A���N�G�X�g�p�����[�^�ɊY������l��ݒ肷��B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return ���N�G�X�g�p�����[�^��ݒ肵�� MypageUserSearchForm �C���X�^���X
	 */
    @Override
	public MemberSearchForm createMypageUserSearchForm(HttpServletRequest request) {
    	MemberSearchForm form = createMypageUserSearchForm();
		FormPopulator.populateFormBeanFromRequest(request, form);
		return form;
	}

}
