package jp.co.transcosmos.dm3.core.healthchk.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.dao.ReadOnlyDAO;


/**
 * �w���X�`�F�b�N�N���X.
 * <p>
 * �w�肳�ꂽ DAO ���g�p���ăf�[�^�������擾���A���ʂ��P���ȏゾ�����ꍇ�AOK �y�[�W�𕜋A����B<br/>
 * ����ȊO�̏ꍇ�͗�O���X���[����B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.22	Shamaison �̃\�[�X���Q�l�ɍ쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class HealthChkCommand implements Command {

	/**
	 * �w���X�`�F�b�N�Ŏg�p���� DAO<br/>
	 * ���ł��ǂ����A�������J�E���g����̂Ō����̏��Ȃ� DAO ���g�p���鎖�B
	 */
	protected ReadOnlyDAO<?> checkDAO;

	/**
	 * �w���X�`�F�b�N�Ŏg�p���� DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param checkDAO�@�w���X�`�F�b�N�Ŏg�p���� DAO
	 */
	public void setCheckDAO(ReadOnlyDAO<?> checkDAO) {
		this.checkDAO = checkDAO;
	}



	/**
	 * �w���X�`�F�b�N�p���N�G�X�g�ɑ΂��鉞������<br/>
	 * �`�F�b�N�p�Ɏw�肳�ꂽ DAO ���g�p���ă��R�[�h�������擾���A�Y���������擾�o���Ȃ��ꍇ�͗�O���X���[����B<br/>
	 * �`�F�b�N�p DAO ���Q�Ƃ���e�[�u���ɂ̓f�[�^�����݂��鎖�B�@�܂��A��ʂ̃f�[�^�����݂���e�[�u���͕��ׂ�������
	 * �̂Ŕ����鎖�B<br/>
	 * <br/>
	 * @param request HTTP ���N�G�X�g
	 * @param response HTTP ���X�|���X
	 * @return ModelAndView �̃C���X�^���X
	 * 
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ���R�[�h�������擾����B
		// �{���͌������`�F�b�N����K�v���͖����i��O���X���[�����ׁj���A�ꉞ�A�������`�F�b�N����B
		if (this.checkDAO.getRowCountMatchingFilter(null) <=0 ){
			throw new RuntimeException("check table is empty");
		}

		return new ModelAndView("success");
	}

}
