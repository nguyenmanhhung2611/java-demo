package jp.co.transcosmos.dm3.corePana.model.housing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInspection;

/**
 * �������N���X.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.04.16     �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�͌ʃJ�X�^�}�C�Y�Ŋg�������\��������̂Œ��ڃC���X�^���X�𐶐����Ȃ����B
 * �K�� model �N���X����擾���鎖�B
 *
 */
public class PanaHousing extends Housing {

	/** �����C���X�y�N�V�����̃��X�g */
	private List<HousingInspection> housingInspections = new ArrayList<>();

	/** ���t�H�[������Map */
	private  List<Map<String, Object>> reforms =new ArrayList<Map<String,Object>>();

	/** ������{���ŏI�X�V�� */
	private AdminLoginInfo housingInfoUpdUser;

	/**
	 * �R���X�g���N�^�[<br/>
	 * �������N�G�X�g�̃��f���ȊO����C���X�^���X�𐶐��o���Ȃ��l�ɃR���X�g���N�^�𐧌�����B<br/>
	 * <br/>
	 */
	protected PanaHousing() {
		super();
	}



	/**
	 * �����C���X�y�N�V�����̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �����C���X�y�N�V�����̃��X�g
	 */
	public List<HousingInspection> getHousingInspections() {
		return housingInspections;
	}

	/**
	 * �����C���X�y�N�V�����̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingInspections �����C���X�y�N�V�����̃��X�g
	 */
	public void setHousingInspections(List<HousingInspection> housingInspections) {
		this.housingInspections = housingInspections;
	}


	/**
	 * ���t�H�[������Map���擾����B<br/>
	 * <br/>
	 * @return ���t�H�[������Map
	 */
	public List<Map<String, Object>> getReforms() {
		return reforms;
	}

	/**
	 * ���t�H�[������Map��ݒ肷��B<br/>
	 * <br/>
	 * @param reforms ���t�H�[������Map
	 */
	public void setReforms(List<Map<String, Object>> reforms) {
		this.reforms = reforms;
	}

	/**
	 * ������{���ŏI�X�V�Җ����擾����B<br/>
	 * <br/>
	 * @return ������{���ŏI�X�V�Җ�
	 */
	public AdminLoginInfo getHousingInfoUpdUser() {
		return housingInfoUpdUser;
	}

	/**
	 * ������{���ŏI�X�V�Җ���ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfoUpdUser ������{���ŏI�X�V�Җ�
	 */
	public void setHousingInfoUpdUser(AdminLoginInfo housingInfoUpdUser) {
		this.housingInfoUpdUser = housingInfoUpdUser;
	}
}
