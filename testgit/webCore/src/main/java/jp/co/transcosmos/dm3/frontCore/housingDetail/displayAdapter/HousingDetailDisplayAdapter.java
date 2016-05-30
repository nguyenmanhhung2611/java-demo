package jp.co.transcosmos.dm3.frontCore.housingDetail.displayAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayAdapter;
import jp.co.transcosmos.dm3.core.displayAdapter.DisplayUtils;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * �����ڍ׉�ʗp DisplayAdapter.
 * �o���[�I�u�W�F�N�g�̒l����ʕ\���p�ɕϊ�����B�@�������A�G�X�P�[�v���� JSP ���ŕK�v�ɉ����čs�����B<br>
 * <br>
 * ���̃N���X�̃��\�b�h�́A�p�����N���X�� getDisplayValue() ���烊�t���N�V��������Ď��s�����B<br>
 * ���\�b�h���s���̃t�B�[���h���ɊY�����郁�\�b�h���A���̃N���X�ɑ��݂���ꍇ�́A���̃��\�b�h���g�p�����B<br>
 * �����A�Y�����郁�\�b�h�����݂��Ȃ��ꍇ�͈����œn���ꂽ�o���[�I�u�W�F�N�g�̃��\�b�h�����s����B<br>
 * <br>
 * �܂��A�N���X�Ɏ������郁�\�b�h�́AgetXXXX(T vo)�AisXXXX(T vo) �̏����� public ���\�b�h�ł���΁A
 * �Ϗ���o���[�I�u�W�F�N�g�ɑ��݂��Ȃ��Ă������\�ł���B<br>
 * <br>
 * JSP ����g�p����ꍇ�́Atld �ŌĂяo������`����Ă���̂ŁAwebcore:display() �Ŏ��s����B<br>
 * <br>
 * @author H.Mizuno
 *
 */
public class HousingDetailDisplayAdapter extends DisplayAdapter {

	/** ���ʕ\�����H�����N���X */
	private DisplayUtils displayUtils;

	/**
	 * ���ʕ\�����H�����N���X��ݒ肷��B<br>
	 * <br>
	 * @param displayUtils ���ʕ\�����H�����N���X
	 */
	public void setDisplayUtils(DisplayUtils displayUtils) {
		this.displayUtils = displayUtils;
	}



	/**
	 * �V���}�[�N�̕\���L�����擾����<br>
	 * �������̓o�^�����V�X�e�����t�Ɣ�r���A�V���ȓ��Ȃ� true �𕜋A����B<br>
	 * <br>
	 * @param housingInfo�@�������
	 * @return�@�V���̏ꍇ�Atrue �𕜋A����
	 */
	public boolean isNew(HousingInfo housingInfo){
		// �������̃V�X�e�����t���擾���A�����󂾂����ꍇ�� false �𕜋A����B
		Date insDate = housingInfo.getInsDate();
		if (insDate == null) return false;
		
		if ((new Date().getTime() - insDate.getTime()) <= 7 * (24 * 60 * 60 * 1000)) {
			return true;
		}
		return false;
	}

	
	
	/**
	 * �\���p�̒���/���i���擾����<br>
	 * �\���P�ʂ𖜉~�ɕϊ�����B<br>
	 * <br>
	 * @param housingInfo ������{���
	 * @return �\���p�ɕϊ����ꂽ������
	 */
	public String getPrice(HousingInfo housingInfo){
		return this.displayUtils.toTenThousandUnits(housingInfo.getPrice());
	}
	

	
	/**
	 * �\���p�ɗX�֔ԍ����n�C�t����؂�ɕϊ����Ď擾����B<br>
	 * <br>
	 * @param ������{���
	 * @return �\���p�ɕϊ����ꂽ������
	 */
	public String getZip(BuildingInfo buildingInfo){
		return this.displayUtils.toDspZip(buildingInfo.getZip());
	}
	
	

	/**
	 * �\���p�Z�����擾����B<br>
	 * �s���{�����@�{�@�s�撬�����@�{�@�����Ԓn�@�{�@���������̑��@��A������B<br>
	 * <br>
	 * @param building �������
	 * @return �\���p�ɕϊ����ꂽ������
	 */
	public String getDisplayAddress(Housing housing) {

		StringBuilder sb = new StringBuilder(256);

		// �������̌������ʂƂȂ�AJoinResult ���擾����B
		JoinResult buildingResult = housing.getBuilding().getBuildingInfo();

		// note
		// JoinResult �́AOuter �����ŊY�����R�[�h�����݂��Ȃ��ꍇ�Anull �ł͂Ȃ���̃o���[�I�u�W�F�N�g�𕜋A����̂ŁA
		// ������@�ɂ͒��ӂ��鎖�B

		// �������ʂ���A�Y������s���{���}�X�^���擾����B
		// core �̎����Ƃ��ẮA�s���{��CD �͕K�{�����A���݂��Ȃ��ꍇ���l�����Ă����B
		// �s���{���}�X�^���擾�ł����ꍇ�͓s���{������ݒ肷��B
		PrefMst prefMst = (PrefMst) buildingResult.getItems().get("prefMst");
		if (!StringValidateUtil.isEmpty(prefMst.getPrefName())){
			sb.append(prefMst.getPrefName() + " ");
		}


		// ������{�����擾
		BuildingInfo buildingInfo = (BuildingInfo) buildingResult.getItems().get("buildingInfo");


		// �������ʂ���A�Y������s�撬���}�X�^���擾����B
		// �����A�s�撬���}�X�^���擾�ł����ꍇ�A���̃I�u�W�F�N�g�̎s�撬�������g�p����B
		// �s�撬�������������������ꍇ�A�s�撬��CD �ɊY������f�[�^�����݂��Ȃ��ꍇ������B
		// ���̏ꍇ�́A������{���̎s�撬�������g�p����B
		AddressMst addressMst = (AddressMst) buildingResult.getItems().get("addressMst");
		if (!StringValidateUtil.isEmpty(addressMst.getAddressName())){
			sb.append(addressMst.getAddressName() + " ");
		} else if (!StringValidateUtil.isEmpty(buildingInfo.getAddressName()))  {
			sb.append(buildingInfo.getAddressName() + " ");
		}

		// ������{��񂩂�A�����E�Ԓn���擾����B
		if (!StringValidateUtil.isEmpty(buildingInfo.getAddressOther1())){
			sb.append(buildingInfo.getAddressOther1() + " ");
		}

		// ������{��񂩂�A���������̑����擾����B
		if (!StringValidateUtil.isEmpty(buildingInfo.getAddressOther2())){
			sb.append(buildingInfo.getAddressOther2() + " ");
		}

		return sb.toString();
	}
	
	
	
	/**
	 * �\���p�̒z�N�����擾����B<br>
	 * �v�H�N���́A�N�A�������{�v�H�{�{�u�z�v�𕜋A����B<br>
	 * <br>
	 * @param buildingInfo ������{���
	 * @return �\���p�ɕϊ����ꂽ������
	 */
	public String getCompDate(BuildingInfo buildingInfo){
		if (buildingInfo.getCompDate() == null) return "";
		String compDate = new SimpleDateFormat("yyyy�NM��").format(buildingInfo.getCompDate());
		if (!StringValidateUtil.isEmpty(buildingInfo.getCompTenDays())){
			return compDate + buildingInfo.getCompTenDays() + "�z";
		} else {
			return compDate + "�z";
		}
	}



	/**
	 * �\���p�ɁA�����ʐς𕽕����[�g������ؐ��ɕϊ����Ď擾����B<br>
	 * <br>
	 * @param buildingDtlInfo �����ڍ׏��
	 * @return �\���p�ɕϊ����ꂽ������
	 */
	public String getBuildingArea(BuildingDtlInfo buildingDtlInfo){
		return this.displayUtils.toTubo(buildingDtlInfo.getBuildingArea());
	}


	
	/**
	 * �J���}��؂�Ŋi�[����Ă���A�\���p�A�C�R��CD �� List �I�u�W�F�N�g�ɕϊ����ĕ��A����B<br>
	 * ���� Model �̕W�������ł́A�����̐ݔ����Ƃ��ēo�^�����ݔ�CD ���\���p�A�C�R��CD �Ƃ���
	 * �o�^����Ă���B<br>
	 * <br>
	 * @param housingInfo �������
	 * @return �A�C�R��CD �̔z��
	 */
	public String[] getIconCd(HousingInfo housingInfo){
		if (StringValidateUtil.isEmpty(housingInfo.getIconCd())) return new String[0];
		return housingInfo.getIconCd().split(",");
	}

}
