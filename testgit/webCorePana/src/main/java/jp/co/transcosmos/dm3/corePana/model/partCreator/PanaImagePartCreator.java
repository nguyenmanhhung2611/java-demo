package jp.co.transcosmos.dm3.corePana.model.partCreator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.co.transcosmos.dm3.core.model.HousingPartCreator;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.HousingPartInfo;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;

/**
 * �����摜��񂩂炱���������𐶐�����.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.04.14	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class PanaImagePartCreator implements HousingPartCreator {

	/** �����������������p DAO */
	private DAO<HousingPartInfo> housingPartInfoDAO;

	/**
	 * �Ԏ��}���A����������CD �ϊ� map<br/>
	 * Key = �Ԏ��}���AValue = ����������CD<br/>
	 */
	private Map<String, String> mImageToPart;

	/**
	 * �ڍ׎ʐ^���A����������CD �ϊ� map<br/>
	 * Key = �ڍ׎ʐ^���AValue = ����������CD<br/>
	 */
	private Map<String, String> imageToPart;

	/** ValueObject �� Factory �N���X */
	private ValueObjectFactory valueObjectFactory;

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�Ԏ��}) �� Set �I�u�W�F�N�g */
	private String myMImagePartCds[];

	/** ���̃N���X�̐����ΏۂƂȂ�A����������CD(�ڍ׎ʐ^) �� Set �I�u�W�F�N�g */
	private String myImagePartCds[];



	/**
	 * ���̃N���X�̂����������𐶐����郁�\�b�h�𔻒肷��B<br/>
	 * <br/>
	 * @param methodName ���ꂩ����s���郁�\�b�h��
	 * @return ���\�b�h���� addHousingImg �̏ꍇ�Atrue �𕜋A
	 * @return ���\�b�h���� updHousingImg �̏ꍇ�Atrue �𕜋A
	 * @return ���\�b�h���� delHousingImg �̏ꍇ�Atrue �𕜋A
	 */
	@Override
	public boolean isExecuteMethod(String methodName) {

		// �����摜���V�݁^�X�V�^�폜�������Ɏ��s����B
		// �����A�J�X�^�}�C�Y���� ���L�֐� ��ʂ̃��\�b�h������s���Ă���
		// �ꍇ�́A�����̃��\�b�h����ł����s�����l�ɕύX���鎖�B
		if ("addHousingImg".equals(methodName)){
			return true;
		}
		if ("updHousingImg".equals(methodName)){
			return true;
		}
		if ("delHousingImg".equals(methodName)){
			return true;
		}

		return false;
	}



	/**
	 * �o�^�����������I�u�W�F�N�g���炱���������쐬����B<br/>
	 * <br/>
	 */
	@Override
	public void createPart(Housing housing) throws Exception {

		// �X�V�ΏۂƂȂ镨����{�����擾����B
		HousingInfo housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
		// �����摜���̃��X�g���擾����B
		List<HousingImageInfo> housingImageInfos = housing.getHousingImageInfos();

		// ���݂̂���������CD ���폜����B
		delPartCds(housingInfo.getSysHousingCd(), this.myMImagePartCds);
		delPartCds(housingInfo.getSysHousingCd(), this.myImagePartCds);

		// �����摜��񂪂Ȃ��ꍇ�͏����I���B
		if (housingImageInfos == null || housingImageInfos.size() == 0) return;

		// �Ԏ��}��������������B
		int mimageCount = 0;
		// �ڍ׎ʐ^��������������B
		int imageCount = 0;
		for (HousingImageInfo housingImageInfo : housingImageInfos){
			// �Ԏ��}�����J�E���g����B
			if (PanaCommonConstant.IMAGE_TYPE_00.equals(housingImageInfo.getImageType())) mimageCount++;
			// �ڍ׎ʐ^�����J�E���g����B�i�u02:����v���ΏۊO�j
			if (!PanaCommonConstant.IMAGE_TYPE_02.equals(housingImageInfo.getImageType())) imageCount++;
		}

		// �ǉ����邱������������ Map �I�u�W�F�N�g
		// Key = ����������CD�AValue = ���ꂩ��ǉ����镨���������������� Value �I�u�W�F�N�g
		Map<String, HousingPartInfo> addPartMap = new HashMap<>();

		// �Ԏ��}���A����������CD �ϊ� map�����[�v���A�����摜���̊Ԏ��}���Ɣ�r����B
		for (Entry<String, String> e : this.mImageToPart.entrySet()){
			if (mimageCount < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// �Ԏ��}�� ���ϊ��e�[�u���ɊԎ��}���ȏ゠��ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// �ڍ׎ʐ^���A����������CD �ϊ� map�����[�v���A�����摜���̏ڍ׎ʐ^���Ɣ�r����B
		for (Entry<String, String> e : this.imageToPart.entrySet()){
			if (imageCount < Integer.parseInt(e.getKey())) continue;
			String partCd = e.getValue();
			if (!StringValidateUtil.isEmpty(partCd)){

				// �ڍ׎ʐ^�� ���ϊ��e�[�u���ɏڍ׎ʐ^���ȏ゠��ꍇ�A�ǉ����邱������������ Map �ɁA
				// ���������������݂��邩���`�F�b�N����B
				// �������݂���ꍇ�͓o�^�ςȂ̂ŁA���̃f�[�^����������B
				if (addPartMap.get(partCd) == null){
					addPartMap.put(partCd, createHousingPartInfo(housingInfo, partCd));
				}
			}
		}

		// ������������ǉ�����B
		this.housingPartInfoDAO.insert(addPartMap.values().toArray(new HousingPartInfo[addPartMap.size()]));
	}



	/**
	 * ���݂̂���������CD ���폜����B<br/>
	 * <br/>
	 */
	protected void delPartCds(String sysHousingCd, String partCds[]) {
		// ���݂̂���������CD ���폜����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("sysHousingCd", sysHousingCd);
		criteria.addInSubQuery("partSrchCd", partCds);
		this.housingPartInfoDAO.deleteByFilter(criteria);
	}



	/**
	 * �V�K�ǉ�����A������������������ Value �I�u�W�F�N�g�𐶐�����B<br/>
	 * �{���́A�V�X�e������CD �ƁA����������CD �����ő����͂������A�g�������l�����āA���̑������n���Ă����B<br/>
	 * <br/>
	 * @param housingInfo �������
	 * @param partCd�@����������CD
	 * @return
	 */
	protected HousingPartInfo createHousingPartInfo(HousingInfo housingInfo, String partCd) {
		// Value �I�u�W�F�N�g�𐶐����ăv���p�e�B�l��ݒ肷��B
		HousingPartInfo housingPartInfo = (HousingPartInfo) this.valueObjectFactory.getValueObject("HousingPartInfo");
		housingPartInfo.setSysHousingCd(housingInfo.getSysHousingCd());
		housingPartInfo.setPartSrchCd(partCd);
		return housingPartInfo;
	}



	/**
	 * �Ԏ��}���A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �Ԏ��}���AValue = ����������CD
	 * <br/>
	 * @param �Ԏ��}���A����������CD �ϊ� map
	 */
	public synchronized void setMImageToPart(Map<String, String> mImageToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.mImageToPart = mImageToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyMImagePartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : mImageToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myMImagePartCds = partCdSet.toArray(new String[partCdSet.size()]);
	}



	/**
	 * �ڍ׎ʐ^���A����������CD �ϊ� map ��ݒ肷��B<br/>
	 * �܂��A���̃N���X�������ΏۂƂ��邱��������CD �̃��X�g���쐬����B<br/>
	 * Key = �ڍ׎ʐ^���AValue = ����������CD
	 * <br/>
	 * @param �ڍ׎ʐ^���A����������CD �ϊ� map
	 */
	public synchronized void setImageToPart(Map<String, String> imageToPart) {

		// ���̃��\�b�h�̎��s�ɂ͓��������K�v�Ȃ̂ŁA���̃N���X�̃C���X�^���X�͕K���V���O���g���Œ�`���鎖�B
		// �܂��A���̃��\�b�h�́ADI �R���e�i�� Bean ��`�ȊO�ł͎g�p���Ȃ����B

		// �ϊ� Map ��ݒ肷��B
		this.imageToPart = imageToPart;

		// ����������CD �� Set ���g�p���ďd������菜���AmyImagePartCds �֐ݒ肷��B
		Set<String> partCdSet = new HashSet<>();
		for (Entry<String, String> e : imageToPart.entrySet()){
			partCdSet.add(e.getValue());
		}
		this.myImagePartCds = partCdSet.toArray(new String[partCdSet.size()]);
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
