package jp.co.transcosmos.dm3.frontCore.auth;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.UserRoleSet;
import jp.co.transcosmos.dm3.login.v3.AutoLoginAuth;



public class MypageAuth extends AutoLoginAuth {

	/** �}�C�y�[�W���O�C���ώ��̃��[���� */
	public static final String MYPAGE_ROLE_NAME = "mypage";



	/**
	 * ���O�C�����̃Z�b�V�����i�[����<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param user ���O�C�����[�U�[���
	 */
	@Override
	protected void setLoggedInUser(HttpServletRequest request, LoginUser user) {
		// �p�����̏����𗘗p���ăZ�b�V�������Ƀ��O�C��������������
		super.setLoggedInUser(request, user);
		
		// �t���[�����[�N�ł́A���[���p�� DAO ���ݒ肳��Ȃ��ꍇ�AROLE �����Z�b�V�����ɐݒ肷�鏈����
		// �s���Ȃ��B
		// �}�C�y�[�W�̃��O�C�������ł́A�����I�Ƀ��[������ݒ肷��B
		HttpSession session = request.getSession();
        Set<String> myRoles = getMyRolenamesFromDB(user.getUserId());
        session.setAttribute(this.rolesetSessionParameter, new UserRoleSet(user.getUserId(), myRoles));
	}



    /**
     * ���[�������擾<br/>
     * �}�C�y�[�W�̃��O�C�������̏ꍇ�A�Œ�l�����[�����Ƃ��ĕ��A����B<br/>
     * <br/>
     * @param userId �L�[�ƂȂ郆�[�U�[ID
     * @return �擾�������[������ Set �I�u�W�F�N�g
     */
	@Override
    protected Set<String> getMyRolenamesFromDB(Object userId) {

        Set<String> myRoles = new HashSet<String>();
        myRoles.add(MYPAGE_ROLE_NAME);
        return myRoles; 
    }

}
