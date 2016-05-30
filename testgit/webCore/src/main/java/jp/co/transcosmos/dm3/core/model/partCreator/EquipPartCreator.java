package jp.co.transcosmos.dm3.core.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.EquipMst;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * �ݔ���񂩂炱���������𐶐�����.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.04.13	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class EquipPartCreator implements HousingPartCreator {

	/** �����������������p DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * �ݔ�CD�A����������CD �ϊ� map<br/>
	 * �����݂̕��@�́A�P�̐ݔ�CD �𕡐��̂����������Ƃ��ēo�^���鎖���T�|�[�g���Ă��Ȃ��B<br/>
	 * Key = �ݔ�CD�AValue = ����������CD<br/>
	 */
	protected Map<String, String> equipToPart;

	/** ValueObject �� Factory �N���X */
	protected ValueObjectFactory valueObjectFactory;

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD �� Set �I�u�W�F�N�g */
	protected String myPartCds[];



	/**
	 * ���̃N���X�̂����������𐶐����郁�\�b�h�𔻒肷��B<br/>
	 * <br/>
	 * @param methodName ���ꂩ����s���郁�\�b�h��
	 * @return ���\�b�h���� updateHousingEquip �̏ꍇ�Atrue �𕜋A
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// �ݔ����X�V�������Ɏ��s����B
		// �����A�J�X�^�}�C�Y���� updateHousingEquip() ��ʂ̃��\�b�h������s���Ă���
		// �ꍇ�́A�����̃��\�b�h����ł����s�����l�ɕύX���鎖�B
		if ("updateHousingEquip".equals(methodName)){
			return true;
		}
		return false;
	}



	/**
	 * �o�^�����������I�u�W�F�N�g����ݔ������쐬����B<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {

		// �o�^�����ݔ������擾����B
		Map<String, EquipMst> equipMap = housing.getHousingEquipInfos();
		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");


		// ���݂̂���������CD ���폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", housingInfo.getSysHousingCd());
		criteria.addInSubQuery("partSrchCd", this.myPartCds);

		this.housingPartInfoDAO.deleteByFilter(criteria);

		
		// �ǉ����邱������������ Map �I�u�W�F�N�g
		// Key = ����������CD�AValue = ���ꂩ��ǉ����镨���������������� Value �I�u�W�F�N�g
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// �o�^�����ݔ������[�v���A�����������̕ϊ��e�[�u���ɑ��݂��邩���`�F�b�N����B
		for (Entry<String, EquipMst> e : equipMap.entrySet()){
			String partCd = this.equipToPart.get(e.getValue().getEquipCd());
			if (!StringValidateUtil.isEmpty(partCd)){

				// �ϊ��e�[�u���ɐݔ�CD ���o�^����Ă���ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd, e.getValue()));
				}
			}
		}

		// ������������ǉ�����B
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}

	

	/**
	 * �V�K�ǉ�����A������������������ Value �I�u�W�F�N�g�𐶐�����B<br/>
	 * �{���́A�V�X�e������CD �ƁA����������CD �����ő����͂������A�g�������l�����āA���̑������n���Ă����B<br/>
	 * <br/>
	 * @param housingInfo �������
	 * @param partCd�@����������CD
	 * @param equipMst �ݔ�CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd, EquipMst equipMst) {
		// Value �I�u�W�F�N�g�𐶐����ăv���p�e�B�l��ݒ肷��B
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}



	/**
	 * �ݔ�CD�A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �ݔ�CD�AValue = ����������CD
	 * <br/>
	 * @param �ݔ�CD�A����������CD �ϊ� map
	 */
	public synchronized void setEquipToPart(Map<String, String> equipToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.equipToPart = equipToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyPartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : equipToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myPartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * �����������������DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param housingPartInfoDAO �����������������DAO
	 */
	public void setHousingPartInfoDAO(DAO<HousingPartInfo> housingPartInfoDAO) {
		this.housingPartInfoDAO = housingPartInfoDAO;
	}

	/**
	 * Value �I�u�W�F�N�g�� Factory �N���X��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory Value �I�u�W�F�N�g�� Factory
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

}
