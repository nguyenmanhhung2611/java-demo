package jp.co.transcosmos.dm3.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.login.LoginUser;
import jp.co.transcosmos.dm3.login.v3.Authentication;

/**
 * �}�C�y�[�W�A����сA�������O�C�����Ă��郆�[�U�[�����擾����B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.16	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class FrontLoginUserUtils {

	/** ���̃��[�e�B���e �� Bean ID */
	protected static String FACTORY_BEAN_ID = "loginUserUtils";

	/** �������O�C���p�̔F�؃N���X��ݒ肷�� */
	protected Authentication anonymousAuth;

	/** �}�C�y�[�W���O�C���p�̔F�؃N���X��ݒ肷��B */
	protected Authentication mypageAuth;
	
	

	/**
	 * �������O�C���p�̔F�؃N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param anonymousAuth �������O�C���p�̔F�؃N���X
	 */
	public void setAnonymousAuth(Authentication anonymousAuth) {
		this.anonymousAuth = anonymousAuth;
	}

	/**
	 * �}�C�y�[�W���O�C���p�̔F�؃N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageAuth �}�C�y�[�W���O�C���p�̔F�؃N���X
	 */
	public void setMypageAuth(Authentication mypageAuth) {
		this.mypageAuth = mypageAuth;
	}



	/**
	 * FrontLoginUserUtils �̃C���X�^���X���擾����B<br/>
	 * Spring �̃R���e�L�X�g����A loginUserUtils �Œ�`���ꂽ FrontLoginUserUtils ��
	 * �C���X�^���X���擾����B<br/>
	 * �擾�����C���X�^���X�́AFrontLoginUserUtils ���p�������g���N���X�����A�����ꍇ������B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @return FrontLoginUserUtils�A�܂��͌p�����Ċg�������N���X�̃C���X�^���X
	 */
	public static FrontLoginUserUtils getInstance(HttpServletRequest request){
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		return (FrontLoginUserUtils)springContext.getBean(FrontLoginUserUtils.FACTORY_BEAN_ID);
	}

	
	
	/**
	 * ���݃��O�C�����Ă��铽�����O�C�����[�U�[�����擾����B<br/>
	 * �}�C�y�[�W�Ƀ��O�C�����Ă���ꍇ�́AMypageUserInterface �C���^�[�t�F�[�X�����������A�}�C�y�[�W������
	 * �i�ʏ�́AMemberInfo�j�𕜋A����B<br/>
	 * �}�C�y�[�W�Ƀ��O�C�����Ă��Ȃ��ꍇ�́ALoginUser �C���^�[�t�F�[�X�����������A�������O�C�����i�ʏ�́A
	 * UserInfo�j�𕜋A����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ���O�C�����[�U�[���
	 */
	public LoginUser getAnonLoginUserInfo(HttpServletRequest request, HttpServletResponse response){

		// �����F�ؗp�̃��O�C�����[�U�[�擾���������s����B
		// ���̃N���X�̃��\�b�h�́A�}�C�y�[�W�Ƀ��O�C���ς̏ꍇ�̓}�C�y�[�W�̔F�؏��𕜋A���A
		// �}�C�y�[�W�Ƀ��O�C�����Ă��Ȃ��ꍇ�͓������O�C���̔F�؏��𕜋A����B

		// ���ʂȎ����������A�������O�C���̔F�؏��͑��݂���B
		return this.anonymousAuth.getLoggedInUser(request, response);
	}

	
	
	/**
	 * ���݃��O�C�����Ă���}�C�y�[�W�̃��[�U�[�����擾����B<br/>
	 * �}�C�y�[�W�Ƀ��O�C�����Ă���ꍇ�́AMypageUserInterface �C���^�[�t�F�[�X�����������A�}�C�y�[�W������
	 * �i�ʏ�́AMemberInfo�j�𕜋A����B<br/>
	 * �}�C�y�[�W�Ƀ��O�C�����Ă��Ȃ��ꍇ�́Anull �𕜋A����B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ���O�C�����[�U�[���
	 */
	public MypageUserInterface getMypageLoginUserInfo(HttpServletRequest request, HttpServletResponse response){

		// �}�C�y�[�W�̔F�؏����擾����B
		return (MypageUserInterface) this.mypageAuth.getLoggedInUser(request, response);
	}

}
