package jp.co.transcosmos.dm3.core.model.housing;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.building.Building;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;

/**
 * �������N���X.
 * �����A�J�X�^�}�C�Y�ŕ��������\������e�[�u�������������ꍇ�A���̃N���X���p�����Ċg�����邱�ƁB<br/>
 * �܂��A�p�������ꍇ�͕K�� HousingManageImpl �� createHousingInstace() ���I�[�o�[���C�h���鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.12	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�͌ʃJ�X�^�}�C�Y�Ŋg�������\��������̂Œ��ڃC���X�^���X�𐶐����Ȃ����B
 * �K�� model �N���X����擾���鎖�B
 * 
 */
public class Housing {

	/** ������� */
	private Building building;

	/** ������{��� �i������{���{�����ڍ׏��{�����X�e�[�^�X���j */
	private JoinResult housingInfo;

	/**
	 * �����ݔ����� Map<br/>
	 * Key = �ݔ�CD�A Value = �ݔ����}�X�^<br/>
	 */
	private Map<String, EquipMst> housingEquipInfos = new LinkedHashMap<>();

	/** �����摜���̃��X�g */
	private List<HousingImageInfo> housingImageInfos = new ArrayList<>();

	/**
	 *  �����g���������̃}�b�v<br/>
	 *     �EKey = �����g�����̃J�e�S�����icategory�j
	 *     �EValue = �J�e�S���̊Y������AKey�l���ݒ肳�ꂽ Map �I�u�W�F�N�g�iKey = keyName��AValue = dataValue��j
	 */
	private Map<String, Map<String,String>> housingExtInfos = new LinkedHashMap<>();



	/**
	 * �R���X�g���N�^�[<br/>
	 * �������N�G�X�g�̃��f���ȊO����C���X�^���X�𐶐��o���Ȃ��l�ɃR���X�g���N�^�𐧌�����B<br/>
	 * <br/>
	 */
	protected Housing() {
		super();
	}



	/**
	 * ���������擾����B<br/>
	 * <br/>
	 * @return �������
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * ��������ݒ肷��B<br/>
	 * <br/>
	 * @param building �������
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * ������{�����擾����B<br/>
	 * <br/>
	 * @return ������{��� �i������{���{�s���{���}�X�^�{�s�撬���}�X�^�j
	 */
	public JoinResult getHousingInfo() {
		return housingInfo;
	}

	/**
	 * ������{����ݒ肷��B<br/>
	 * <br/>
	 * @param housingInfo ������{��� �i������{���{�s���{���}�X�^�{�s�撬���}�X�^�j
	 */
	public void setHousingInfo(JoinResult housingInfo) {
		this.housingInfo = housingInfo;
	}
	
	/**
	 * �����ݔ����� Map ���擾����B<br/>
	 * <br/>
	 * @return �����ݔ����� Map
	 */
	public Map<String, EquipMst> getHousingEquipInfos() {
		return housingEquipInfos;
	}

	/**
	 * �����ݔ����� Map ��ݒ肷��B<br/>
	 * <br/>
	 * @return �����ݔ����� Map
	 */
	public void setHousingEquipInfos(Map<String, EquipMst> housingEquipInfos) {
		this.housingEquipInfos = housingEquipInfos;
	}

	/**
	 * �����g���������̃}�b�v���擾����B<br/>
	 * <br/>
	 * @return �����g���������̃}�b�v
	 */
	public Map<String, Map<String,String>> getHousingExtInfos() {
		return housingExtInfos;
	}
	
	/**
	 * �����g���������̃}�b�v��ݒ肷��B<br/>
	 * <br/>
	 * @param housingExtInfos �����g���������̃}�b�v
	 */
	public void setHousingExtInfos(Map<String, Map<String,String>> housingExtInfos) {
		this.housingExtInfos = housingExtInfos;
	}

	/**
	 * �����摜���̃��X�g���擾����B<br/>
	 * <br/>
	 * @return �����摜���̃��X�g
	 */
	public List<HousingImageInfo> getHousingImageInfos() {
		return housingImageInfos;
	}

	/**
	 * �����摜���̃��X�g��ݒ肷��B<br/>
	 * <br/>
	 * @param housingImageInfos �����摜���̃��X�g
	 */
	public void setHousingImageInfos(List<HousingImageInfo> housingImageInfos) {
		this.housingImageInfos = housingImageInfos;
	}

}
