package jp.co.transcosmos.dm3.corePana.model.housing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingLandmarkForm;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingDtlInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingImageInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInfoForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingInspectionForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSpecialtyForm;
import jp.co.transcosmos.dm3.corePana.model.housing.form.RecommendPointForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.HousingImageComparator;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.util.ReformDtlComparator;
import jp.co.transcosmos.dm3.corePana.util.ReformImageComparator;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;

/**
 * �����v���r���[��ʗp�t�H�[��.
 * <p>
 * <pre>
 * �S����        �C����        �C�����e
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang        2015.05.07     �V�K�쐬
 * </pre>
 * <p>
 *
 */
public class PanaHousingPreview extends PanaHousingDetailed {

	/**
	 * �R���X�g���N�^�[<br/>
	 * <br/>
	 */
	public PanaHousingPreview(CodeLookupManager codeLookupManager, PanaCommonParameters commonParameters, PanaFileUtil panaFileUtil) {
		super(codeLookupManager, commonParameters, panaFileUtil);
	}

	/**
	 * �n���ꂽ������{��񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm ������{���Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingInfoPreviewData(PanaHousingInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// ��������
		outPutForm.setDisplayHousingName(inputForm.getDisplayHousingName());

		// ���i
		outPutForm.setPrice(isEmpty(inputForm.getPrice()) ? "" : formatPrice(inputForm.getPrice(), true) + "���~");

		// �z�N
		outPutForm.setCompDate(isEmpty(inputForm.getCompDate()) ? "" : new SimpleDateFormat("yyyy�NM���z").format(new SimpleDateFormat("yyyyMM").parse(inputForm.getCompDate())));

		// �Ԏ��
		outPutForm.setLayoutCd(inputForm.getLayoutCd());

		// ���ݒn
		outPutForm.setAddress(defaultString(inputForm.getPrefName()) + " " + defaultString(inputForm.getAddressName()) + " " + defaultString(inputForm.getAddressOther1()) + " "
				+ defaultString(inputForm.getAddressOther2()));

		// �A�N�Z�X
		if (inputForm.getDefaultRouteCd() != null) {

			int cnt = inputForm.getDefaultRouteCd().length;
			String[] access = new String[cnt];

			for (int i = 0; i < cnt; i++) {
				StringBuilder sbAccess = new StringBuilder();
				// ��\�H����
				sbAccess.append(addString(defaultString(inputForm.getRouteNameRr()[i]), " "));
				// �w��
				sbAccess.append(addString(defaultString(inputForm.getStationName()[i]), "�w "));
				// �o�X��Ж�
				sbAccess.append(addString(defaultString(inputForm.getBusCompany()[i]), " "));
				// �o�X�₩��̓k������
				sbAccess.append(defaultString(isEmpty(inputForm.getTimeFromBusStop()[i]) ? "" : "�k��" + inputForm.getTimeFromBusStop()[i] + "��"));
				access[i] = sbAccess.toString();
			}

			// �A�N�Z�X�u�z��v
			outPutForm.setAccess(access);

		} else {

			outPutForm.setAccess(null);
		}

		// �����ʐ�
		outPutForm.setBuildingArea(defaultString(inputForm.getBuildingArea()));

		// �����ʐ� ��
		outPutForm.setBuildingAreaSquare(isEmpty(inputForm.getBuildingArea()) ? "" : "�i��" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getBuildingArea())).toString() + "�؁j");

		// �����ʐ�_�⑫
		outPutForm.setBuildingAreaMemo(inputForm.getBuildingAreaMemo());

		// �y�n�ʐ�
		outPutForm.setLandArea(defaultString(inputForm.getLandArea()));

		// �y�n�ʐ� ��
		outPutForm.setLandAreaSquare(isEmpty(inputForm.getLandArea()) ? "" : "�i��" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getLandArea())).toString() + "�؁j");

		// �y�n�ʐ�_�⑫
		outPutForm.setLandAreaMemo(inputForm.getLandAreaMemo());

		// ��L�ʐ�
		outPutForm.setPersonalArea(defaultString(inputForm.getPersonalArea()));

		// ��L�ʐ� ��
		outPutForm.setPersonalAreaSquare(isEmpty(inputForm.getPersonalArea()) ? "" : "(��" + PanaCalcUtil.calcTsubo(new BigDecimal(inputForm.getPersonalArea())).toString() + "��)");

		// ��L�ʐ�_�⑫
		outPutForm.setPersonalAreaMemo(inputForm.getPersonalAreaMemo());

		if (!isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			for (int i = 0; i < cnt; i++) {

				StringBuilder sbTotalPrice1 = new StringBuilder();
				StringBuilder sbTotalPrice2 = new StringBuilder();

				// ���z�P�u�z��v
				if (!isEmpty(inputForm.getPrice()) || !isEmpty(outPutForm.getPlanPrice()[i])) {
					sbTotalPrice1.append("��");
					sbTotalPrice1.append(formatPrice(sumPrice(inputForm.getPrice(), outPutForm.getPlanPrice()[i]), true));
					sbTotalPrice1.append("���~");
				}

				// ���z�Q�u�z��v
				sbTotalPrice2.append((isEmpty(outPutForm.getPrice())) ? "" : "�������i�F" + outPutForm.getPrice());
				sbTotalPrice2.append(((isEmpty(outPutForm.getPrice())) || (isEmpty(outPutForm.getPlanPrice()[i]))) ? "" : "�{");
				sbTotalPrice2.append((isEmpty(outPutForm.getPlanPrice()[i])) ? "" : "���t�H�[�����i�F��" + formatPrice(outPutForm.getPlanPrice()[i], true) + "���~");

				// ���z�P�u�z��v
				outPutForm.setTotalPrice1(setValue(i, outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));

				// ���z�Q�u�z��v
				outPutForm.setTotalPrice2(setValue(i, outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));

			}

			// ���̑��̃��t�H�[���v������ݒ肷��B
			outPutForm.setOtherReformPlan();
		}
	}

	/**
	 * �n���ꂽ�����ڍ׏�񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm �����ڍ׏��Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingDtlPreviewData(PanaHousingDtlInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// �u�������CD�v �� �u01�F�}���V�����v�̏ꍇ
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(getHousingKindCd())) {
			// �o���R�j�[�ʐ�
			outPutForm.setBalconyArea(defaultString(inputForm.getBalconyArea()));

			// �Ǘ��`��
			outPutForm.setUpkeepType(inputForm.getUpkeepType());

			// �~�n����
			outPutForm.setLandRight(inputForm.getLandRight());

			// �p�r�n��
			outPutForm.setUsedAreaCd(inputForm.getUsedAreaCd());

			// ���n��
			outPutForm.setMoveinTiming(inputForm.getMoveinTiming());

			// ���n�����R�����g
			outPutForm.setMoveinNote(inputForm.getMoveinNote());

			// ����`��
			outPutForm.setTransactTypeDiv(inputForm.getTransactTypeDiv());

			// ���L����
			outPutForm.setSpecialInstruction(inputForm.getSpecialInstruction());

			// ���l
			outPutForm.setUpkeepCorp(isEmpty(inputForm.getUpkeepCorp()) ? "" : "�Ǘ���ЁF" + inputForm.getUpkeepCorp());

			// �Ǘ��
			outPutForm.setUpkeep(isEmpty(inputForm.getUpkeep()) ? "" : formatPrice(inputForm.getUpkeep(), false) + "�~ &frasl; ��");

			// �C�U�ϗ���
			outPutForm.setMenteFee(isEmpty(inputForm.getMenteFee()) ? "" : formatPrice(inputForm.getMenteFee(), false) + "�~ &frasl; ��");

			// ���ԏ�
			outPutForm.setParkingSituation(inputForm.getDisplayParkingInfo());

			// �K���^���݊K
			StringBuilder sbFloor = new StringBuilder();
			// ���K��
			sbFloor.append(isEmpty(inputForm.getTotalFloors()) ? "" : inputForm.getTotalFloors() + "�K��");
			sbFloor.append((isEmpty(inputForm.getTotalFloors()) && isEmpty(inputForm.getFloorNo())) ? "" : "�@&frasl;�@");
			// �����̊K��
			sbFloor.append(isEmpty(inputForm.getFloorNo()) ? "" : inputForm.getFloorNo() + "�K");
			// �K���^���݊K
			outPutForm.setFloor(sbFloor.toString());

			// ��v�̌�
			outPutForm.setDirection(inputForm.getOrientedDataValue());

			// �\��
			outPutForm.setStruct(inputForm.getBuildingDataValue());

			// ���ː�
			outPutForm.setTotalHouseCnt(inputForm.getTotalHouseCntDataValue());

			// �K��
			outPutForm.setScale(inputForm.getScaleDataValue());

			// ����
			outPutForm.setStatus(inputForm.getPreDataValue());

			// �C���t��
			outPutForm.setInfrastructure(inputForm.getInfDataValue());

		} else {

			// �������S
			outPutForm.setPrivateRoad(inputForm.getPrivateRoad());

			// �y�n����
			outPutForm.setLandRight(inputForm.getLandRight());

			// �p�r�n��
			outPutForm.setUsedAreaCd(inputForm.getUsedAreaCd());

			// ���n��
			outPutForm.setMoveinTiming(inputForm.getMoveinTiming());

			// ���n�����R�����g
			outPutForm.setMoveinNote(inputForm.getMoveinNote());

			// ����`��
			outPutForm.setTransactTypeDiv(inputForm.getTransactTypeDiv());

			// �ړ�
			outPutForm.setContactRoad(inputForm.getContactRoad());

			// �ړ�����/����
			outPutForm.setContactRoadDir(inputForm.getContactRoadDir());

			// ���r�ی�
			outPutForm.setInsurExist(inputForm.getInsurExist());

			// ���L����
			outPutForm.setSpecialInstruction(inputForm.getSpecialInstruction());

			// ���l
			outPutForm.setUpkeepCorp(isEmpty(inputForm.getUpkeepCorp()) ? "" : "�Ǘ���ЁF" + inputForm.getUpkeepCorp());

			// ���ԏ�
			outPutForm.setParkingSituation(inputForm.getDisplayParkingInfo());

			// �\��
			outPutForm.setStruct(inputForm.getBuildingDataValue());

			// ����
			outPutForm.setStatus(inputForm.getPreDataValue());

			// �C���t��
			outPutForm.setInfrastructure(inputForm.getInfDataValue());

			// ���؂���
			outPutForm.setCoverage(inputForm.getCoverageMemo());

			// �e�ϗ�
			outPutForm.setBuildingRate(inputForm.getBuildingRateMemo());
		}

		// �������߉摜�p�X�yhidden�z
		if ("1".equals(inputForm.getPictureDataDelete())) {
			outPutForm.setStaffimagePathName(null);
		} else {
			outPutForm.setStaffimagePathName(inputForm.getPreviewImgPath());
		}

		// �S��
		outPutForm.setStaffName(inputForm.getWorkerDataValue());

		// ��Ж�
		outPutForm.setCompanyName(inputForm.getCompanyDataValue());

		// �x�X��
		outPutForm.setBranchName(inputForm.getBranchDataValue());

		// �Ƌ��ԍ�
		outPutForm.setLicenseNo(inputForm.getFreeCdDataValue());

		// �������ߓ��e
		outPutForm.setRecommendComment(PanaStringUtils.encodeHtml(inputForm.getBasicComment()));

		// �������߃|�C���g�̑i���G���A1
		outPutForm.setDtlComment(PanaStringUtils.encodeHtml(inputForm.getDtlComment()));

		// ����̃R�����g
		outPutForm.setSalesComment(PanaStringUtils.encodeHtml(inputForm.getVendorComment()));

		// ���t�H�[���v��������������
		outPutForm.setReformPlanReadyComment(PanaStringUtils.encodeHtml(inputForm.getReformComment()));

		// ����p�X�yhidden�z
		outPutForm.setMovieUrl(inputForm.getUrlDataValue());
	}

	/**
	 * �n���ꂽ������������ �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm ��������Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingSpecialtyPreviewData(PanaHousingSpecialtyForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// �u�������CD�v �� �u03�F�y�n�v�ȊO�̏ꍇ
		if (!PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(getHousingKindCd())) {

			if (inputForm.getEquipCd() != null) {

				// �����ݔ����Map
				Map<String, String> equipMstMap = new HashMap<String, String>();

				int cnt = inputForm.getMstEquipCd().length;

				// �����ݔ������J��Ԃ��A�����ݔ����Map�Ƀv�b�g����B
				for (int i = 0; i < cnt; i++) {
					equipMstMap.put(inputForm.getMstEquipCd()[i], inputForm.getEquipName()[i]);
				}

				StringBuilder sbName = new StringBuilder();

				// �ݔ�CD���J��Ԃ��A�����������쐬����B
				for (String equipCd : inputForm.getEquipCd()) {
					sbName.append(equipMstMap.get(equipCd)).append("/");
				}

				// ��������
				outPutForm.setEquipName(sbName.toString().substring(0, sbName.toString().lastIndexOf("/")));

			} else {

				// ��������
				outPutForm.setEquipName(null);
			}

		} else {

			// ���������̕\���t���O
			outPutForm.setHousingPropertyDisplayFlg(false);
		}
	}

	/**
	 * �n���ꂽ�������߃|�C���g��񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm �������߃|�C���g���Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setRecommendPointPreviewData(RecommendPointForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// �A�C�R�����yhidden�z�u�z��v
		outPutForm.setIconCd(inputForm.getIcon());
	}

	/**
	 * �n���ꂽ�Z��f�f��񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm �Z��f�f���Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingInspectionPreviewData(PanaHousingInspectionForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		String[] inspectionNo = new String[inputForm.getInspectionKey().length];

		for (int i = 0; i < inputForm.getInspectionKey().length; i++) {
			// �Z��f�f�yhidden�z�u�z��v
			inspectionNo[i] = String.valueOf(i);
		}

		// �Z��f�f���{�L��
		outPutForm.setInspectionExist(inputForm.getHousingInspection());

		outPutForm.setInspectionNoHidden(inspectionNo);
		outPutForm.setInspectionKey(inputForm.getInspectionKey());
		outPutForm.setInspectionTrust(inputForm.getInspectionTrust_result());

		if ("1".equals(inputForm.getImgFlg())) {
			outPutForm.setInspectionImagePathName(inputForm.getHidNewImgPath());
		} else {
			outPutForm.setInspectionImagePathName(inputForm.getHidImgPath());
		}
		if ("1".equals(inputForm.getLoadFlg())) {
			outPutForm.setInspectionPathName(inputForm.getHidNewPath());
		} else {
			if ("on".equals(inputForm.getHousingDel())) {
				outPutForm.setInspectionPathName(null);
			} else {
				outPutForm.setInspectionPathName(inputForm.getHidPath());
			}
		}
	}

	/**
	 * ���t�H�[����񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm ���t�H�[�����Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setReformPlanPreviewData(ReformInfoForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		// �����̃��t�H�[���v�����̕\���t���O
		outPutForm.setReformPlanDisplayFlg(true);

		// ���t�H�[���v���������������̕\���t���O
		outPutForm.setReformPlanReadyDisplayFlg(false);

		StringBuilder sbReformPlan = new StringBuilder();
		StringBuilder sbTotalPrice1 = new StringBuilder();
		StringBuilder sbTotalPrice2 = new StringBuilder();
		StringBuilder sbReformCdHidden = new StringBuilder();
		StringBuilder sbReformCategory = new StringBuilder();

		// ���t�H�[���v�������
		sbReformCdHidden.append(inputForm.getSysReformCd());

		// �v�����^�C�v�u�z��v
		sbReformPlan.append(defaultString(inputForm.getPlanName()));
		
		// category 1 array
		sbReformCategory.append(inputForm.getPlanCategory1());

		// ���z�P�u�z��v
		if (!isEmpty(outPutForm.getPriceHidden()) || !isEmpty(inputForm.getPlanPrice())) {
			sbTotalPrice1.append("��");
			sbTotalPrice1.append(formatPrice(sumPrice(outPutForm.getPriceHidden(), inputForm.getPlanPrice()), true));
			sbTotalPrice1.append("���~");
		}

		// ���z�Q�u�z��v
		sbTotalPrice2.append((isEmpty(outPutForm.getPrice())) ? "" : "�������i�F" + outPutForm.getPrice());
		sbTotalPrice2.append(((isEmpty(outPutForm.getPrice())) || (isEmpty(inputForm.getPlanPrice()))) ? "" : "�{");
		sbTotalPrice2.append((isEmpty(inputForm.getPlanPrice())) ? "" : "���t�H�[�����i�F��" + formatPrice(inputForm.getPlanPrice(), true) + "���~");

		// ���t�H�[���v������񂪂���ꍇ
		if (!isEmpty(inputForm.getSysReformCd()) && !isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			for (int i = 0; i < cnt; i++) {

				if (inputForm.getSysReformCd().equals(outPutForm.getReformCdHidden()[i])) {

					// �v�����^�C�v�u�z��v
					outPutForm.setPlanType(setValue(i, outPutForm.getPlanType(), sbReformPlan.toString()));

					// ���z�P�u�z��v
					outPutForm.setTotalPrice1(setValue(i, outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));
					outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

					// ���z�Q�u�z��v
					outPutForm.setTotalPrice2(setValue(i, outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));
					outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
					
					// reform category 1
					outPutForm.setReformCategory(setValue(i, outPutForm.getReformCategory(), sbReformCategory.toString()));
				}
			}

		} else if (isEmpty(inputForm.getSysReformCd()) && !isEmpty(outPutForm.getPlanNoHidden())) {

			int cnt = outPutForm.getPlanNoHidden().length;

			// �v�����ԍ��yhidden�z�u�z��v
			outPutForm.setPlanNoHidden(setValue(outPutForm.getPlanNoHidden(), String.valueOf(cnt)));

			// �v�����^�C�v�u�z��v
			outPutForm.setPlanType(setValue(outPutForm.getPlanType(), sbReformPlan.toString()));

			// ���z�P�u�z��v
			outPutForm.setTotalPrice1(setValue(outPutForm.getTotalPrice1(), sbTotalPrice1.toString()));
			outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

			// ���z�Q�u�z��v
			outPutForm.setTotalPrice2(setValue(outPutForm.getTotalPrice2(), sbTotalPrice2.toString()));
			outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
			
			outPutForm.setReformCategory(setValue(outPutForm.getReformCategory(), sbReformCategory.toString()));

		} else if (isEmpty(inputForm.getSysReformCd()) && isEmpty(outPutForm.getPlanNoHidden())) {

			// �v�����ԍ��yhidden�z�u�z��v
			outPutForm.setPlanNoHidden(new String[] { "0" });

			// �v�����^�C�v�u�z��v
			outPutForm.setPlanType(new String[] { sbReformPlan.toString() });

			// ���z�P�u�z��v
			outPutForm.setTotalPrice1(new String[] { sbTotalPrice1.toString() });
			outPutForm.setTotalDtlPrice1(sbTotalPrice1.toString());

			// ���z�Q�u�z��v
			outPutForm.setTotalPrice2(new String[] { sbTotalPrice2.toString() });
			outPutForm.setTotalDtlPrice2(sbTotalPrice2.toString());
			
			// reform category 1
			outPutForm.setReformCategory(new String[] { sbReformCategory.toString()});

		}

		// ���t�H�[���v������
		outPutForm.setPlanName(inputForm.getPlanName());

		// �Z�[���X�|�C���g
		outPutForm.setSalesPoint(PanaStringUtils.encodeHtml(inputForm.getSalesPoint()));

		// �H��
		outPutForm.setConstructionPeriod(inputForm.getConstructionPeriod());

		// ���l
		outPutForm.setReformNote(PanaStringUtils.encodeHtml(inputForm.getNote()));

		// ���t�H�[����_����p�T���l�C���yhidden�z�u�z��v
		outPutForm.setAfterMovieUrl(inputForm.getAfterMovieUrl());

		// ���t�H�[���O_����p�T���l�C���yhidden�z�u�z��v
		outPutForm.setBeforeMovieUrl(inputForm.getBeforeMovieUrl());
	}

	/**
	 * �n���ꂽ���t�H�[���ڍׂ��� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm ���t�H�[���ڍ�Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setReformDtlPreviewData(ReformDtlForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// ���t�H�[���摜��񃊃X�g���擾
		List<ReformDtl> reformDtlList = this.convertReformDtlList(inputForm);

		// �摜���Ȃ��ꍇ
		if (reformDtlList == null || reformDtlList.size() == 0) {

			// �������߃��t�H�[���v������̕\���t���O
			outPutForm.setRecommendReformPlanDisplayFlg(false);

			// �摜�̕\���t���O
			outPutForm.setReformImgDisplayFlg(false);

			// ���t�H�[����_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
			outPutForm.setAfterPathNoHidden(null);

			// ���t�H�[����_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
			outPutForm.setAfterPath1(null);

			// ���t�H�[����_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
			outPutForm.setAfterPath2(null);

			// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
			outPutForm.setAfterPathComment(null);

			// ���t�H�[���O_�O�ρE���ӎʐ^�ԍ��yhidden�z�u�z��v
			outPutForm.setBeforePathNoHidden(null);

			// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�P�yhidden�z�u�z��v
			outPutForm.setBeforePath1(null);

			// ���t�H�[���O_�O�ρE���ӎʐ^�p�X�Q�yhidden�z�u�z��v
			outPutForm.setBeforePath2(null);

			// ���t�H�[����_�O�ρE���ӎʐ^ �R�����g�yhidden�z�u�z��v
			outPutForm.setBeforePathComment(null);

			return;
		}

		outPutForm.setRecommendReformPlanDisplayFlg(true);
		// �摜�̕\���t���O
		outPutForm.setReformImgDisplayFlg(true);

		int cnt = reformDtlList.size();
		String[] imageNo = new String[cnt];
		String[] imgpath = new String[cnt];
		String[] imgName = new String[cnt];
		String[] imgprice = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// �摜���
			ReformDtl reformDtlSort = (ReformDtl) reformDtlList.get(i);

			// �摜�ԍ��yhidden�z�u�z��v
			imageNo[i] = String.valueOf(i);

			// �p�X���P�yhidden�z�u�z��v
			imgpath[i] = reformDtlSort.getPathName();

			// �R�����g�yhidden�z�u�z��v
			imgName[i] = reformDtlSort.getImgName();

			// ���i�yhidden�z�u�z��v
			imgprice[i] = (reformDtlSort.getReformPrice() == null) ? "" : "��" + formatPrice(reformDtlSort.getReformPrice(), true) + "���~";
		}

		// �摜�ԍ��yhidden�z�u�z��v
		outPutForm.setReformNoHidden(imageNo);

		// ���ږ��́yhidden�z�u�z��v
		outPutForm.setReformImgName(imgName);

		// �p�X���yhidden�z�u�z��v
		outPutForm.setReformPathName(imgpath);

		// ���i�yhidden�z�u�z��v
		outPutForm.setReformPrice(imgprice);

	}

	/**
	 * �n���ꂽ���t�H�[���ڍ׏������t�H�[���ڍ׏�񃊃X�g�ɕϊ�����B<br/>
	 * <br/>
	 *
	 * @param inputForm ���t�H�[���ڍ�Form �I�u�W�F�N�g
	 *
	 */
	private List<ReformDtl> convertReformDtlList(ReformDtlForm inputForm) {

		// ���t�H�[���摜
		ArrayList<ReformDtl> list = new ArrayList<ReformDtl>();

		ReformDtl image = null;

		if (inputForm.getAddHidFileName() != null) {
			for (int i = 0; i < inputForm.getAddHidFileName().length; i++) {
				if (inputForm.getAddHidFileName()[i] != "") {

					image = new ReformDtl();

					if (StringUtils.isEmpty(inputForm.getAddHidPath()[i])) {
						continue;
					}

					// �t�@�C����
					image.setFileName(inputForm.getAddHidFileName()[i]);

					// �R�����g
					image.setImgName(inputForm.getAddImgName()[i]);

					// temp�p�X
					image.setPathName(inputForm.getAddHidPath()[i]);

					// ���t�H�[�����i
					image.setReformPrice(Long.valueOf(inputForm.getAddReformPrice()[i]));

					// �\����
					image.setSortOrder(Integer.parseInt(inputForm.getAddSortOrder()[i]));
					// �{������
					image.setRoleId(inputForm.getAddRoleId()[i]);

					// �}��
					image.setDivNo(999);

					list.add(image);
				}
			}
		}

		if (inputForm.getUpdHidFileName() != null) {
			for (int i = 0; i < inputForm.getUpdHidFileName().length; i++) {

				// �폜�����摜�͔�\��
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new ReformDtl();

				// �t�@�C����
				image.setFileName(inputForm.getUpdHidFileName()[i]);

				// �R�����g
				image.setImgName(inputForm.getImgName()[i]);

				// temp�p�X�i�傫���摜�j
				image.setPathName(inputForm.getHidPath()[i]);

				// ���t�H�[�����i
				image.setReformPrice(Long.valueOf(inputForm.getReformPrice()[i]));
				// �\����
				image.setSortOrder(Integer.parseInt(inputForm.getSortOrder()[i]));
				// �{������
				image.setRoleId(inputForm.getRoleId()[i]);

				// �}��
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// ���t�H�[���ڍ׉�ʏ��̎}�Ԃ�ݒ�
		setRdDivNo(list);

		// �\�����A�摜�^�C�v�A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new ReformDtlComparator(false));

		return list;
	}

	/**
	 * �n���ꂽ���t�H�[���ڍ׉�ʏ��̎}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param list ���t�H�[���摜��񃊃X�g
	 *
	 */
	private void setRdDivNo(ArrayList<ReformDtl> list) {

		// �}�Ԑݒ�p�\�[�g�A�\�����A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new ReformDtlComparator(true));

		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (i == 0) {
				divNo = 1;
			} else {
				divNo++;
			}

			list.get(i).setDivNo(divNo);
		}
	}

	/**
	 * �n���ꂽ���t�H�[���摜���� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm ���t�H�[���摜Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setReformImgPreviewData(ReformImgForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// ���t�H�[���摜��񃊃X�g���擾
		List<ReformImg> reformImgList = this.convertReformImgList(inputForm);

		// �摜���Ȃ��ꍇ
		if (reformImgList == null || reformImgList.size() == 0) {

			// �������߃��t�H�[���v������̕\���t���O
			outPutForm.setRecommendReformPlanDisplayFlg(false);

			// �摜�̕\���t���O
			outPutForm.setReformImgDisplayFlg(false);

			// ���t�H�[���ڍ�_�ԍ��yhidden�z�u�z��v
			outPutForm.setReformNoHidden(null);

			// ���t�H�[���ڍ�_���ږ��́u�z��v
			outPutForm.setReformImgName(null);

			// ���t�H�[���ڍ�_�摜�p�X���yhidden�z�u�z��v
			outPutForm.setReformPathName(null);

			// ���t�H�[���ڍ�_���ڃ��t�H�[�����i�u�z��v
			outPutForm.setReformPrice(null);

			return;
		}

		outPutForm.setRecommendReformPlanDisplayFlg(true);
		// �摜�̕\���t���O
		outPutForm.setReformImgDisplayFlg(true);

		int cnt = reformImgList.size();
		String[] imageNo = new String[cnt];
		String[] pathBefore1 = new String[cnt];
		String[] pathBefore2 = new String[cnt];
		String[] pathAfter1 = new String[cnt];
		String[] pathAfter2 = new String[cnt];
		String[] imgCommentBefore = new String[cnt];
		String[] imgCommentAfter = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// �摜���
			ReformImg reformImgSort = (ReformImg) reformImgList.get(i);

			// �摜�ԍ��yhidden�z�u�z��v
			imageNo[i] = String.valueOf(i);

			// �p�X���P�yhidden�z�u�z��v
			pathBefore1[i] = reformImgSort.getBeforePathName().replace("/800/", "/200/");

			// �p�X���Q�yhidden�z�u�z��v
			pathBefore2[i] = reformImgSort.getBeforePathName();

			// �p�X���P�yhidden�z�u�z��v
			pathAfter1[i] = reformImgSort.getAfterPathName().replace("/800/", "/200/");

			// �p�X���Q�yhidden�z�u�z��v
			pathAfter2[i] = reformImgSort.getAfterPathName();

			// �R�����g�yhidden�z�u�z��v
			imgCommentBefore[i] = reformImgSort.getBeforeComment();
			// �R�����g�yhidden�z�u�z��v
			imgCommentAfter[i] = reformImgSort.getAfterComment();
		}

		// �摜�ԍ��yhidden�z�u�z��v
		outPutForm.setBeforePathNoHidden(imageNo);
		outPutForm.setAfterPathNoHidden(imageNo);

		// �p�X���P�yhidden�z�u�z��v
		outPutForm.setBeforePath1(pathBefore1);

		// �p�X���Q�yhidden�z�u�z��v
		outPutForm.setBeforePath2(pathBefore2);

		// �p�X���P�yhidden�z�u�z��v
		outPutForm.setAfterPath1(pathAfter1);

		// �p�X���Q�yhidden�z�u�z��v
		outPutForm.setAfterPath2(pathAfter2);

		// �R�����g�yhidden�z�u�z��v
		outPutForm.setBeforePathComment(imgCommentBefore);
		// �R�����g�yhidden�z�u�z��v
		outPutForm.setAfterPathComment(imgCommentAfter);

	}

	/**
	 * �n���ꂽ���t�H�[���摜�������t�H�[���摜��񃊃X�g�ɕϊ�����B<br/>
	 * <br/>
	 *
	 * @param inputForm ���t�H�[���摜Form �I�u�W�F�N�g
	 *
	 */
	private List<ReformImg> convertReformImgList(ReformImgForm inputForm) {

		// ���t�H�[���摜
		ArrayList<ReformImg> list = new ArrayList<ReformImg>();

		ReformImg image = null;

		if (inputForm.getUploadBeforeFileName() != null && inputForm.getUploadAfterFileName() != null) {
			for (int i = 0; i < inputForm.getUploadBeforeFileName().length; i++) {
				image = new ReformImg();

				if (StringUtils.isEmpty(inputForm.getUploadAfterHidPath()[i]) || StringUtils.isEmpty(inputForm.getUploadBeforeHidPath()[i])) {
					continue;
				}

				// �t�@�C����
				image.setBeforeFileName(inputForm.getUploadBeforeFileName()[i]);
				image.setAfterFileName(inputForm.getUploadAfterFileName()[i]);

				// �R�����g
				image.setBeforeComment(inputForm.getUploadBeforeComment()[i]);
				image.setAfterComment(inputForm.getUploadAfterComment()[i]);

				// temp�p�X�i�傫���摜�j
				image.setBeforePathName(inputForm.getUploadBeforeHidPath()[i]);

				// temp�p�X�i�傫���摜�j
				image.setAfterPathName(inputForm.getUploadAfterHidPath()[i]);

				// �\����
				image.setSortOrder(Integer.parseInt(inputForm.getUploadSortOrder()[i]));
				// �{������
				image.setRoleId(inputForm.getUploadRoleId()[i]);

				// �}��
				image.setDivNo(999);

				list.add(image);
			}
		}

		if (inputForm.getEditBeforeFileName() != null && inputForm.getEditAfterFileName() != null) {
			for (int i = 0; i < inputForm.getEditBeforeFileName().length; i++) {

				// �폜�����摜�͔�\��
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new ReformImg();

				// �t�@�C����
				image.setBeforeFileName(inputForm.getEditBeforeFileName()[i]);
				image.setAfterFileName(inputForm.getEditAfterFileName()[i]);

				// �R�����g
				image.setBeforeComment(inputForm.getEditBeforeComment()[i]);
				image.setAfterComment(inputForm.getEditAfterComment()[i]);

				// temp�p�X�i�傫���摜�j
				image.setBeforePathName(inputForm.getBeforeHidPathMax()[i]);

				// temp�p�X�i�傫���摜�j
				image.setAfterPathName(inputForm.getAfterHidPathMax()[i]);
				// �\����
				image.setSortOrder(Integer.parseInt(inputForm.getEditSortOrder()[i]));
				// �{������
				image.setRoleId(inputForm.getEditRoleId()[i]);

				// �}��
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// ���t�H�[���摜��ʏ��̎}�Ԃ�ݒ�
		setRiDivNo(list);

		// �\�����A�摜�^�C�v�A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new ReformImageComparator(false));

		return list;
	}

	/**
	 * �n���ꂽ���t�H�[���摜��ʏ��̎}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param list ���t�H�[���摜��񃊃X�g
	 *
	 */
	private void setRiDivNo(ArrayList<ReformImg> list) {

		// �}�Ԑݒ�p�\�[�g�A�\�����A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new ReformImageComparator(true));

		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (i == 0) {
				divNo = 1;
			} else {
				divNo++;
			}

			list.get(i).setDivNo(divNo);
		}
	}

	/**
	 * �n���ꂽ�����摜��ʏ�񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm �����摜���Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingImagePreviewData(PanaHousingImageInfoForm inputForm,
			PanaHousingDetailed outPutForm) throws Exception {

		// �����摜��񃊃X�g���擾
		List<HousingImageInfo> housingImageInfoList = this.convertHousingImageList(inputForm);

		// �摜���Ȃ��ꍇ
		if (housingImageInfoList == null || housingImageInfoList.size() == 0) {

			// �摜�̕\���t���O
			outPutForm.setImgDisplayFlg(false);

			// ���ω摜�t���O
			outPutForm.setIntrospectImgFlg(false);

			// �摜�ԍ��yhidden�z�u�z��v
			outPutForm.setImgNoHidden(null);

			// �p�X���P�yhidden�z�u�z��v
			outPutForm.setHousingImgPath1Hidden(null);

			// �p�X���Q�yhidden�z�u�z��v
			outPutForm.setHousingImgPath2Hidden(null);

			// �R�����g�yhidden�z�u�z��v
			outPutForm.setHousingImgCommentHidden(null);

			return;
		}

		// �摜�̕\���t���O
		outPutForm.setImgDisplayFlg(true);
		// ���ω摜�t���O
		outPutForm.setIntrospectImgFlg(true);

		int cnt = housingImageInfoList.size();
		String[] imageNo = new String[cnt];
		String[] imageType = new String[cnt];
		String[] path1 = new String[cnt];
		String[] path2 = new String[cnt];
		String[] imgComment = new String[cnt];

		for (int i = 0; i < cnt; i++) {

			// �摜���
			HousingImageInfo housingImageInfo = (HousingImageInfo) housingImageInfoList.get(i);

			// �摜�ԍ��yhidden�z�u�z��v
			imageNo[i] = String.valueOf(i);

			// �p�X���P�yhidden�z�u�z��v
			path1[i] = housingImageInfo.getPathName().replace("/800/", "/200/");

			// �p�X���Q�yhidden�z�u�z��v
			path2[i] = housingImageInfo.getPathName();

			// �R�����g�yhidden�z�u�z��v
			imgComment[i] = housingImageInfo.getImgComment();

			// �摜�^�C�v�yhidden�z�u�z��v
			imageType[i] = housingImageInfo.getImageType();
		}

		// �摜�ԍ��yhidden�z�u�z��v
		outPutForm.setImgNoHidden(imageNo);

		// �p�X���P�yhidden�z�u�z��v
		outPutForm.setHousingImgPath1Hidden(path1);

		// �p�X���Q�yhidden�z�u�z��v
		outPutForm.setHousingImgPath2Hidden(path2);

		// �R�����g�yhidden�z�u�z��v
		outPutForm.setHousingImgCommentHidden(imgComment);

	}

	/**
	 * �n���ꂽ�����摜��ʏ��𕨌��摜��񃊃X�g�ɕϊ�����B<br/>
	 * <br/>
	 *
	 * @param inputForm �����摜���Form �I�u�W�F�N�g
	 *
	 */
	private List<HousingImageInfo> convertHousingImageList(PanaHousingImageInfoForm inputForm) {

		// �����摜
		ArrayList<HousingImageInfo> list = new ArrayList<HousingImageInfo>();

		HousingImageInfo image = null;

		if (inputForm.getAddHidFileName() != null) {
			for (int i = 0; i < inputForm.getAddHidFileName().length; i++) {
				if (inputForm.getAddHidFileName()[i] != "") {

					image = new HousingImageInfo();

					if (StringUtils.isEmpty(inputForm.getAddHidPath()[i])) {
						continue;
					}

					// �t�@�C����
					image.setFileName(inputForm.getAddHidFileName()[i]);
					// �\����
					image.setSortOrder(Integer.parseInt(inputForm.getAddSortOrder()[i]));
					// �{������
					image.setRoleId(inputForm.getAddRoleId()[i]);
					// �摜�^�C�v
					image.setImageType(inputForm.getAddImageType()[i]);
					// �R�����g
					image.setImgComment(inputForm.getAddImgComment()[i]);
					// temp�p�X�i�傫���摜�j
					image.setPathName(inputForm.getAddHidPath()[i]);

					// �}��
					image.setDivNo(999);

					list.add(image);
				}
			}
		}

		if (inputForm.getFileName() != null) {
			for (int i = 0; i < inputForm.getFileName().length; i++) {

				// �폜�����摜�͔�\��
				if ("1".equals(inputForm.getDelFlg()[i])) {
					continue;
				}

				image = new HousingImageInfo();

				// �t�@�C����
				image.setFileName(inputForm.getFileName()[i]);
				// �\����
				image.setSortOrder(Integer.parseInt(inputForm.getSortOrder()[i]));
				// �{������
				image.setRoleId(inputForm.getRoleId()[i]);
				// �摜�^�C�v
				image.setImageType(inputForm.getImageType()[i]);
				// �R�����g
				image.setImgComment(inputForm.getImgComment()[i]);
				// temp�p�X�i�傫���摜�j
				image.setPathName(inputForm.getHidPathMax()[i]);

				// �}��
				image.setDivNo(Integer.parseInt(inputForm.getDivNo()[i]));

				list.add(image);
			}
		}

		// �����摜��ʏ��̎}�Ԃ�ݒ�
		setDivNo(list);

		// �\�����A�摜�^�C�v�A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new HousingImageComparator(false));

		return list;
	}

	/**
	 * �n���ꂽ�����摜��ʏ��̎}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param list �����摜��񃊃X�g
	 *
	 */
	private void setDivNo(ArrayList<HousingImageInfo> list) {

		// �}�Ԑݒ�p�\�[�g�A�摜�^�C�v�A�\�����A�}�Ԃ̏����̏����ɏ]���\�[�g����
		Collections.sort(list, new HousingImageComparator(true));

		String tempImageType = null;
		Integer divNo = 1;

		for (int i = 0; i < list.size(); i++) {

			if (tempImageType == null) {

				list.get(i).setDivNo(divNo);
				tempImageType = list.get(i).getImageType();
				continue;
			}

			// �摜��ʂ������ꍇ
			if (tempImageType.equals(list.get(i).getImageType())) {

				divNo++;
				// ��L�ȊO�̏ꍇ
			} else {
				divNo = 1;
				tempImageType = list.get(i).getImageType();
			}

			list.get(i).setDivNo(divNo);

		}
	}

	/**
	 * �n���ꂽ�n���񂩂� �o��Form �փv���r���[���e��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param inputForm �n����Form �I�u�W�F�N�g
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setHousingLandmarkPreviewData(BuildingLandmarkForm inputForm, PanaHousingPreview outPutForm) throws Exception {

		if (inputForm.getLandmarkType() != null) {

			int cnt = inputForm.getLandmarkType().length;

			String[] landmarkNo = new String[cnt];
			String[] landmarkType = new String[cnt];
			String[] landmarkName = new String[cnt];
			String[] distanceFromLandmark = new String[cnt];

			int j = 0;

			for (int i = 0; i < cnt; i++) {

				StringBuilder sbLandmark = new StringBuilder();

				// �n����ԍ��yhidden�z�u�z��v
				landmarkNo[i] = String.valueOf(i);

				// �����h�}�[�N�̎�ށu�z��v
				landmarkType[i] = inputForm.getLandmarkType()[i];

				// �n����i���́j�u�z��v
				landmarkName[i] = inputForm.getLandmarkName()[i];

				if (!isEmpty(inputForm.getLandmarkName()[i])) {
					j++;
				}

				// �n����i���v����/�����j�u�z��v
				if (!isEmpty(inputForm.getDistanceFromLandmark()[i])) {
					sbLandmark.append("�k��");
					sbLandmark.append(inputForm.getTimeFromLandmark()[i]);
					sbLandmark.append("���i");
					sbLandmark.append(inputForm.getDistanceFromLandmark()[i]);
					sbLandmark.append("m�j");
				}

				// �����h�}�[�N����̓k�����ԂƋ���
				distanceFromLandmark[i] = sbLandmark.toString();
			}

			if (j > 0) {

				// �n����ԍ��yhidden�z�u�z��v
				outPutForm.setLandmarkNoHidden(landmarkNo);

				// �����h�}�[�N�̎�ށu�z��v
				outPutForm.setLandmarkType(landmarkType);

				// �n����i���́j�u�z��v
				outPutForm.setLandmarkName(landmarkName);

				// �n����i���v����/�����j�u�z��v
				outPutForm.setDistanceFromLandmark(distanceFromLandmark);

			} else {

				// �n����ԍ��yhidden�z�u�z��v
				outPutForm.setLandmarkNoHidden(null);

				// �����h�}�[�N�̎�ށu�z��v
				outPutForm.setLandmarkType(null);

				// �n����i���́j�u�z��v
				outPutForm.setLandmarkName(null);

				// �n����i���v����/�����j�u�z��v
				outPutForm.setDistanceFromLandmark(null);
			}
		}
	}

	/**
	 * �v���r���[�̐V���t���O�ƍX�V����ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param outPutForm �o�͗pForm �I�u�W�F�N�g
	 *
	 * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
	 */
	public void setFreshAndUpdDate(PanaHousingPreview outPutForm) throws Exception {
		// �V���t���O
		outPutForm.setFreshFlg(true);

		Date nowDate = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy�NMM��dd��");

		// �X�V��
		outPutForm.setUpdDate(simpleDateFormat.format(nowDate));

		// ����X�V�\��
		outPutForm.setNextUpdDate("�i����X�V�\�� �F" + simpleDateFormat.format(dateAdd(nowDate)) + "�j");
	}

	private String[] setValue(int i, String[] str1, String str2) {
		if (str1 == null) {
			return null;
		}
		int cnt = str1.length;
		String[] strTemp = str1.clone();
		for (int j = 0; j < cnt; j++) {
			if (i == j) {
				strTemp[j] = str2;
			}
		}
		return strTemp;
	}

	private String[] setValue(String[] str1, String str2) {
		if (str1 == null) {
			return null;
		}
		int cnt = str1.length + 1;
		String[] strTemp = new String[cnt];
		for (int i = 0; i < cnt; i++) {
			if (i == cnt - 1) {
				strTemp[i] = str2;
			} else {
				strTemp[i] = str1[i];
			}
		}
		return strTemp;
	}
}
