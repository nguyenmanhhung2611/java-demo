package jp.co.transcosmos.dm3.core.model.housing.form;

import java.util.List;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �����ݔ���񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 * H.Mizuno		2015.04.06	����������CD ����ݔ�CD �֕ύX�@�i��������j
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class HousingEquipForm implements Validateable {

	/** �V�X�e������CD */
	private String sysHousingCd;
	
	/** �ݔ�CD */
	private String[] equipCd;



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingEquipForm() {
		super();
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

		// �V�X�e������CD �̃o���f�[�V�����́ACommand ���őΉ�����̂� From ���ł͎������Ȃ��B
		// �i�X�V���Ƀp�����[�^���������Ă���ꍇ��A�Y���f�[�^�������ꍇ�͗�O���X���[����B�j

		// �ݔ�CD �̃o���f�[�V����
		validEquipCd(errors);

		return (startSize == errors.size());
	}
	
	
	
	// �ݔ�CD �̃o���f�[�V����
	public void validEquipCd(List<ValidationFailure> errors){

		// �ʏ�I����͂Ȃ̂ŁA�����ł̃o���f�[�V�����͍s��Ȃ��B
		// �G���[���o��̂̓p�����[�^��₎��̂݁B

		// �����I�ȃo���f�[�V�����́ADB�X�V���̐���������ōs���B
	}



	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �ݔ�CD ���擾����B<br/>
	 * <br/>
	 * @return �ݔ�CD
	 */
	public String[] getEquipCd() {
		return equipCd;
	}

	/**
	 * �ݔ�CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param equipCd �ݔ�CD
	 */
	public void setEquipCd(String[] equipCd) {
		this.equipCd = equipCd;
	}

}
