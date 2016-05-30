package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.util.List;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �}�C�y�[�W����̃p�X���[�h�⍇���A����сA�p�X���[�h�ύX�p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.31	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class RemindForm implements Validateable {

	/** ���[���A�h���X */
	private String email;



	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * 
	 */
	protected RemindForm(){
		super();
	}

	
	
	/**
	 * ���͂��ꂽ���[���A�h���X�����݂��邩���`�F�b�N���錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�}�C�y�[�W������̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildMailCriteria(){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���[�U�[�Ǘ������Ă���e�[�u���̃��[���A�h���X���`�F�b�N����B 
		criteria.addWhereClause("email", this.email);

		return criteria;
	}

	
	
	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */

	@Override
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();
		
		// TODO ���̓~�X�̖�������̂ŁA���[���A�h���X�̐������`�F�b�N�݂͎̂������鎖�B

		return (startSize == errors.size());
	}



	/**
	 * ���[���A�h���X���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ���[���A�h���X��ݒ肷��B<br/>
	 * <br/>
	 * @param email ���[���A�h���X �i�⍇���o�^�p�j
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
