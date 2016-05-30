package jp.co.transcosmos.dm3.corePana.model.assessment.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.model.inquiry.form.PanaInquiryHeaderForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.PonitNumberValidation;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * ����̂��\�����݂̓��͓��e����p�t�H�[��.
 * <p>
 * <pre>
 * �S����	   �C����	  �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �`����		2015.04.28	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class AssessmentInputForm extends HousingInquiryForm implements Validateable {

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	protected CommonParameters commonParameters;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 *
	 */
	protected AssessmentInputForm(){
		super();
	}



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 *
	 */
	protected AssessmentInputForm(LengthValidationUtils lengthUtils,
							 CommonParameters commonParameters,
							 CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.commonParameters = commonParameters;
		this.codeLookupManager = codeLookupManager;
	}

	/** command �p�����[�^ */
	private String command;

	/** ���p�����̎�� */
	private String buyHousingType;
	/** �Ԏ�CD(�}���V����) */
	private String layoutCd1;
	/** ��L�ʐ� */
	private String personalArea;
	/** �z�N��(�}���V����) */
	private String buildAge1;
	/** �Ԏ�CD(�ˌ�) */
	private String layoutCd2;
	/** �z�N��(�ˌ�) */
	private String buildAge2;
	/** �y�n�ʐ�(�ˌ�) */
	private String landArea2;
	/** �y�n�ʐϒP��(�ˌ�) */
	private String landAreaCrs2;
	/** �����ʐ�(�ˌ�) */
	private String buildingArea;
	/** �����ʐϒP��(�ˌ�) */
	private String buildingAreaCrs;
	/** �y�n�ʐ�(�y�n) */
	private String landArea3;
	/** �y�n�ʐϒP��(�y�n) */
	private String landAreaCrs3;
	/** ���p�����E�X�֔ԍ� */
	private String zip;
	/** ���p�����E�s���{��CD */
	private String prefCd;
	/** ���p�����E�s���{���� */
	private String prefName;
	/** ���p�����E�s�撬���Ԓn */
	private String address;
	/** ���p�����E������ */
	private String addressOther;
	/** ���� */
	private String presentCd;
	/** ���p�\�莞�� */
	private String buyTimeCd;
	/** �����ւ��̗L�� */
	private String replacementFlg;
	/** �v�]�E���� */
	private String requestText;
	/** �v�]�E����i�\���p�j */
	private String requestTextHyoji;
	/** �A�����@ */
	private String contactType;
	/** �A���\�Ȏ��ԑ� */
	private String[] contactTime;
	/** ���p�����Ɠ��� */
	private String sameWithHousing;
	/** �⍇�ҏZ���E�X�֔ԍ� */
	private String userZip;
	/** �⍇�ҏZ���E�s���{��CD */
	private String userPrefCd;
	/** �⍇�ҏZ���E�s���{���� */
	private String userPrefName;
	/** �⍇�ҏZ���E�s�撬���Ԓn */
	private String userAddress;
	/** �⍇�ҏZ���E������ */
	private String userAddressOther;
	/** �A���P�[�g�񓚊i�[ */
	private String[] ansCd;
	/** �A���P�[�g�񓚊i�[�i���͂P�j */
	private String etcAnswer1;
	/** �A���P�[�g�񓚊i�[�i���͂Q�j */
	private String etcAnswer2;
	/** �A���P�[�g�񓚊i�[�i���͂R�j */
	private String etcAnswer3;



	/**
	 * @return command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command �Z�b�g���� command
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return buyHousingType
	 */
	public String getBuyHousingType() {
		return buyHousingType;
	}
	/**
	 * @param buyHousingType �Z�b�g���� buyHousingType
	 */
	public void setBuyHousingType(String buyHousingType) {
		this.buyHousingType = buyHousingType;
	}
	/**
	 * @return layoutCd1
	 */
	public String getLayoutCd1() {
		return layoutCd1;
	}
	/**
	 * @param layoutCd1 �Z�b�g���� layoutCd1
	 */
	public void setLayoutCd1(String layoutCd1) {
		this.layoutCd1 = layoutCd1;
	}
	/**
	 * @return personalArea
	 */
	public String getPersonalArea() {
		return personalArea;
	}
	/**
	 * @param personalArea �Z�b�g���� personalArea
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}
	/**
	 * @return buildAge1
	 */
	public String getBuildAge1() {
		return buildAge1;
	}
	/**
	 * @param buildAge1 �Z�b�g���� buildAge1
	 */
	public void setBuildAge1(String buildAge1) {
		this.buildAge1 = buildAge1;
	}
	/**
	 * @return layoutCd2
	 */
	public String getLayoutCd2() {
		return layoutCd2;
	}
	/**
	 * @param layoutCd2 �Z�b�g���� layoutCd2
	 */
	public void setLayoutCd2(String layoutCd2) {
		this.layoutCd2 = layoutCd2;
	}
	/**
	 * @return buildAge2
	 */
	public String getBuildAge2() {
		return buildAge2;
	}
	/**
	 * @param buildAge2 �Z�b�g���� buildAge2
	 */
	public void setBuildAge2(String buildAge2) {
		this.buildAge2 = buildAge2;
	}
	/**
	 * @return landArea2
	 */
	public String getLandArea2() {
		return landArea2;
	}
	/**
	 * @param landArea2 �Z�b�g���� landArea2
	 */
	public void setLandArea2(String landArea2) {
		this.landArea2 = landArea2;
	}
	/**
	 * @return landAreaCrs2
	 */
	public String getLandAreaCrs2() {
		return landAreaCrs2;
	}
	/**
	 * @param landAreaCrs2 �Z�b�g���� landAreaCrs2
	 */
	public void setLandAreaCrs2(String landAreaCrs2) {
		this.landAreaCrs2 = landAreaCrs2;
	}
	/**
	 * @return buildingArea
	 */
	public String getBuildingArea() {
		return buildingArea;
	}
	/**
	 * @param buildingArea �Z�b�g���� buildingArea
	 */
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	/**
	 * @return buildingAreaCrs
	 */
	public String getBuildingAreaCrs() {
		return buildingAreaCrs;
	}
	/**
	 * @param buildingAreaCrs �Z�b�g���� buildingAreaCrs
	 */
	public void setBuildingAreaCrs(String buildingAreaCrs) {
		this.buildingAreaCrs = buildingAreaCrs;
	}
	/**
	 * @return landArea3
	 */
	public String getLandArea3() {
		return landArea3;
	}
	/**
	 * @param landArea3 �Z�b�g���� landArea3
	 */
	public void setLandArea3(String landArea3) {
		this.landArea3 = landArea3;
	}
	/**
	 * @return landAreaCrs3
	 */
	public String getLandAreaCrs3() {
		return landAreaCrs3;
	}
	/**
	 * @param landAreaCrs3 �Z�b�g���� landAreaCrs3
	 */
	public void setLandAreaCrs3(String landAreaCrs3) {
		this.landAreaCrs3 = landAreaCrs3;
	}
	/**
	 * @return zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip �Z�b�g���� zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return prefCd
	 */
	public String getPrefCd() {
		return prefCd;
	}
	/**
	 * @param prefCd �Z�b�g���� prefCd
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}
	/**
	 * @return prefName
	 */
	public String getPrefName() {
		return prefName;
	}
	/**
	 * @param prefName �Z�b�g���� prefName
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address �Z�b�g���� address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return addressOther
	 */
	public String getAddressOther() {
		return addressOther;
	}
	/**
	 * @param addressOther �Z�b�g���� addressOther
	 */
	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}
	/**
	 * @return presentCd
	 */
	public String getPresentCd() {
		return presentCd;
	}
	/**
	 * @param presentCd �Z�b�g���� presentCd
	 */
	public void setPresentCd(String presentCd) {
		this.presentCd = presentCd;
	}
	/**
	 * @return buyTimeCd
	 */
	public String getBuyTimeCd() {
		return buyTimeCd;
	}
	/**
	 * @param buyTimeCd �Z�b�g���� buyTimeCd
	 */
	public void setBuyTimeCd(String buyTimeCd) {
		this.buyTimeCd = buyTimeCd;
	}
	/**
	 * @return replacementFlg
	 */
	public String getReplacementFlg() {
		return replacementFlg;
	}
	/**
	 * @param replacementFlg �Z�b�g���� replacementFlg
	 */
	public void setReplacementFlg(String replacementFlg) {
		this.replacementFlg = replacementFlg;
	}
	/**
	 * @return requestText
	 */
	public String getRequestText() {
		return requestText;
	}
	/**
	 * @param requestText �Z�b�g���� requestText
	 */
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	/**
	 * @return requestTextHyoji
	 */
	public String getRequestTextHyoji() {
		return requestTextHyoji;
	}
	/**
	 * @param requestTextHyoji �Z�b�g���� requestTextHyoji
	 */
	public void setRequestTextHyoji(String requestTextHyoji) {
		this.requestTextHyoji = requestTextHyoji;
	}
	/**
	 * @return contactType
	 */
	public String getContactType() {
		return contactType;
	}
	/**
	 * @param contactType �Z�b�g���� contactType
	 */
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	/**
	 * @return contactTime
	 */
	public String[] getContactTime() {
		return contactTime;
	}
	/**
	 * @param contactTime �Z�b�g���� contactTime
	 */
	public void setContactTime(String[] contactTime) {
		this.contactTime = contactTime;
	}
	/**
	 * @return sameWithHousing
	 */
	public String getSameWithHousing() {
		return sameWithHousing;
	}
	/**
	 * @param sameWithHousing �Z�b�g���� sameWithHousing
	 */
	public void setSameWithHousing(String sameWithHousing) {
		this.sameWithHousing = sameWithHousing;
	}
	/**
	 * @return userZip
	 */
	public String getUserZip() {
		return userZip;
	}
	/**
	 * @param userZip �Z�b�g���� userZip
	 */
	public void setUserZip(String userZip) {
		this.userZip = userZip;
	}
	/**
	 * @return userPrefCd
	 */
	public String getUserPrefCd() {
		return userPrefCd;
	}
	/**
	 * @param userPrefCd �Z�b�g���� userPrefCd
	 */
	public void setUserPrefCd(String userPrefCd) {
		this.userPrefCd = userPrefCd;
	}
	/**
	 * @return userPrefName
	 */
	public String getUserPrefName() {
		return userPrefName;
	}
	/**
	 * @param userPrefName �Z�b�g���� userPrefName
	 */
	public void setUserPrefName(String userPrefName) {
		this.userPrefName = userPrefName;
	}
	/**
	 * @return userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}
	/**
	 * @param userAddress �Z�b�g���� userAddress
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	/**
	 * @return userAddressOther
	 */
	public String getUserAddressOther() {
		return userAddressOther;
	}
	/**
	 * @param userAddressOther �Z�b�g���� userAddressOther
	 */
	public void setUserAddressOther(String userAddressOther) {
		this.userAddressOther = userAddressOther;
	}
	/**
	 * @return ansCd
	 */
	public String[] getAnsCd() {
		return ansCd;
	}
	/**
	 * @param ansCd �Z�b�g���� ansCd
	 */
	public void setAnsCd(String[] ansCd) {
		this.ansCd = ansCd;
	}
	/**
	 * @return etcAnswer1
	 */
	public String getEtcAnswer1() {
		return etcAnswer1;
	}
	/**
	 * @param etcAnswer1 �Z�b�g���� etcAnswer1
	 */
	public void setEtcAnswer1(String etcAnswer1) {
		this.etcAnswer1 = etcAnswer1;
	}
	/**
	 * @return etcAnswer2
	 */
	public String getEtcAnswer2() {
		return etcAnswer2;
	}
	/**
	 * @param etcAnswer2 �Z�b�g���� etcAnswer2
	 */
	public void setEtcAnswer2(String etcAnswer2) {
		this.etcAnswer2 = etcAnswer2;
	}
	/**
	 * @return etcAnswer3
	 */
	public String getEtcAnswer3() {
		return etcAnswer3;
	}
	/**
	 * @param etcAnswer3 �Z�b�g���� etcAnswer3
	 */
	public void setEtcAnswer3(String etcAnswer3) {
		this.etcAnswer3 = etcAnswer3;
	}



	/**
	 *�n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 */
	public void setDefaultData(JoinResult userInfo) {

		// �}�C�y�[�W��������擾
		if(userInfo != null){
			MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
			PrefMst prefMst_member = (PrefMst)userInfo.getItems().get("prefMst");
			if(memberInfo != null){
				// �����O_��
				this.getInquiryHeaderForm().setLname(memberInfo.getMemberLname());
				// �����O_��
				this.getInquiryHeaderForm().setFname(memberInfo.getMemberFname());
				// �����O�i�t���K�i�j_��
				this.getInquiryHeaderForm().setLnameKana(memberInfo.getMemberLnameKana());
				// �����O�i�t���K�i�j_��
				this.getInquiryHeaderForm().setFnameKana(memberInfo.getMemberFnameKana());
				// ���[���A�h���X
				this.getInquiryHeaderForm().setEmail(memberInfo.getEmail());
				// �d�b�ԍ�
				this.getInquiryHeaderForm().setTel(memberInfo.getTel());
				// FAX�ԍ�
				((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setFax(memberInfo.getFax());
				// �X�֔ԍ�
				this.setUserZip(memberInfo.getZip());
				// �s���{��CD
				this.setUserPrefCd(memberInfo.getPrefCd());
				// �s���{����
				if(!StringValidateUtil.isEmpty(prefMst_member.getPrefName())){
					this.setUserPrefName(prefMst_member.getPrefName());
				}
				// �s�撬���Ԓn
				this.setUserAddress(memberInfo.getAddress());
				// ������
				this.setUserAddressOther(memberInfo.getAddressOther());
			}
		}
	}


	/**
	 * Form �l�̎����ϊ�������B<br/>
	 * <br/>
	 */
	public void setAutoChange() {

		// �S�p�������甼�p�����ւ̎����ϊ�
		this.setZip(PanaStringUtils.changeToHankakuNumber(this.zip));
		this.setUserZip(PanaStringUtils.changeToHankakuNumber(this.userZip));

		// InquiryHeaderForm�̎����ϊ�
		if (this.getInquiryHeaderForm() != null) {
			PanaInquiryHeaderForm panaInquiryHeaderForm = (PanaInquiryHeaderForm)this.getInquiryHeaderForm();

			if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

				panaInquiryHeaderForm.setZip(this.userZip);
				panaInquiryHeaderForm.setPrefCd(this.userPrefCd);
				panaInquiryHeaderForm.setAddress(this.userAddress);
				panaInquiryHeaderForm.setAddressOther(this.userAddressOther);

			}

			// �S�p�������甼�p�����ւ̎����ϊ�
			panaInquiryHeaderForm.setTel(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getTel()));
			panaInquiryHeaderForm.setFax(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getFax()));
			panaInquiryHeaderForm.setEmail(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getEmail()));
			panaInquiryHeaderForm.setZip(PanaStringUtils.changeToHankakuNumber(panaInquiryHeaderForm.getZip()));
		}
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

		// ���p�����̎�ʃ`�F�b�N
		validBuyHousingType(errors);
		// �Ԏ�CD�`�F�b�N
		validLayoutCd(errors);
		// ��L�ʐσ`�F�b�N
		validPersonalArea(errors);
		// �z�N���`�F�b�N
		validBuildAge(errors);
		// �y�n�ʐσ`�F�b�N
		validLandArea(errors);
		// �y�n�ʐϒP�ʃ`�F�b�N
		validLandAreaCrs(errors);
		// �����ʐσ`�F�b�N
		validBuildingArea(errors);
		// �����ʐϒP�ʃ`�F�b�N
		validBuildingAreaCrs(errors);
		// �X�֔ԍ��`�F�b�N
		validZip(errors);
		// �s���{���`�F�b�N
		validPrefCd(errors);
		// �s�撬���Ԓn�`�F�b�N
		validAddress(errors);
		// �������`�F�b�N
		validAddressOther(errors);
		// �����`�F�b�N
		validPresentCd(errors);
		// ���p�\�莞���`�F�b�N
		validBuyTimeCd(errors);
		// �����ւ��̗L���`�F�b�N
		validReplacementFlg(errors);
		// �v�]�E����`�F�b�N
		validRequestText(errors);
		// ����(��)�`�F�b�N
		validLname(errors);
		// ����(��)�`�F�b�N
		validFname(errors);
		// �����E�J�i(��)�`�F�b�N
		validLnameKana(errors);
		// �����E�J�i(��)�`�F�b�N
		validFnameKana(errors);
		// ���[���A�h���X�`�F�b�N
		validEmail(errors);
		// �d�b�ԍ��`�F�b�N
		validTel(errors);
		// FAX�ԍ��`�F�b�N
		validFax(errors);
		// �A�����@�`�F�b�N
		validContactType(errors);
		// �A���\�Ȏ��ԑу`�F�b�N
		validContactTime(errors);
		// �⍇�ҏZ���E�X�֔ԍ��`�F�b�N
		validUserZip(errors);
		// �⍇�ҏZ���E�s���{���`�F�b�N
		validUserPrefCd(errors);
		// �⍇�ҏZ���E�s�撬���Ԓn�`�F�b�N
		validUserAddress(errors);
		// �⍇�ҏZ���E�������`�F�b�N
		validUserAddressOther(errors);
		// �A���P�[�g�񓚃`�F�b�N
		validAnsCd(errors);
		// �A���P�[�g_���̑��񓚂P�`�F�b�N
		validEtcAnswer1(errors);
		// �A���P�[�g_���̑��񓚂Q�`�F�b�N
		validEtcAnswer2(errors);
		// �A���P�[�g_���̑��񓚂R�`�F�b�N
		validEtcAnswer3(errors);
		// �A���P�[�g�`�F�b�N�{�b�N�X�ƃA���P�[�g���e�̐���`�F�b�N
		validAnsCdAndEtcAnswer(errors);


		return (startSize == errors.size());
	}

	/**
     * �A���P�[�g�`�F�b�N�{�b�N�X�ƃA���P�[�g���e�̐���`�F�b�N<br/>
     * �E�֘A�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
	protected void validAnsCdAndEtcAnswer(List<ValidationFailure> errors) {
        // �A���P�[�g�`�F�b�N�{�b�N�X�ƃA���P�[�g���e�̐���`�F�b�N
        List<String> list = new ArrayList<String>();
        if (this.getAnsCd() != null) {
        	list =  Arrays.asList(this.getAnsCd());
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer1()) && !list.contains("008")) {
        	errors.add(new ValidationFailure("mustInput1", "�A���P�[�g���e1",  "�p�i�\�j�b�N�V���b�v", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer2()) && !list.contains("009")) {
        	errors.add(new ValidationFailure("mustInput1", "�A���P�[�g���e2",  "���̑�", null));
        }
        if (!StringValidateUtil.isEmpty(this.getEtcAnswer3()) && !list.contains("010")) {
        	errors.add(new ValidationFailure("mustInput1", "�A���P�[�g���e3",  "�v�����R�[�h", null));
        }
	}
	/**
	 * ���p�����̎�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuyHousingType(List<ValidationFailure> errors) {
		String label = "assessment.input.buyHousingType";
		ValidationChain valid = new ValidationChain(label,this.buyHousingType);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[�����̓`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "buy_housing_type"));

		valid.validate(errors);
	}

	/**
	 * �Ԏ�CD �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLayoutCd(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.layoutCd1";
			ValidationChain valid = new ValidationChain(label,this.layoutCd1);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.layoutCd2";
			ValidationChain valid = new ValidationChain(label,this.layoutCd2);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

			valid.validate(errors);

		}
	}

	/**
	 * ��L�ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���p�����`�F�b�N
	 * �E�������A�����������`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPersonalArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.personalArea";
			ValidationChain valid = new ValidationChain(label,this.personalArea);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// ���p�����`�F�b�N
			valid.addValidation(new NumberValidation());
			// �������A�����������`�F�b�N
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * �z�N�� �o���f�[�V����<br/>
	 * �E���p�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuildAge(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {

			String label = "assessment.input.buildAge1";
			ValidationChain valid = new ValidationChain(label,this.buildAge1);

			// ���p�����`�F�b�N
			valid.addValidation(new NumericValidation());
			// �����`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildAge2";
			ValidationChain valid = new ValidationChain(label,this.buildAge2);

			// ���p�����`�F�b�N
			valid.addValidation(new NumericValidation());
			// �����`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));

			valid.validate(errors);

		}
	}

	/**
	 * �y�n�ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���p�����`�F�b�N
	 * �E�������A�����������`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLandArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.landArea2";
			ValidationChain valid = new ValidationChain(label,this.landArea2);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// ���p�����`�F�b�N
			valid.addValidation(new NumberValidation());
			// �������A�����������`�F�b�N
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {

			String label = "assessment.input.landArea3";
			ValidationChain valid = new ValidationChain(label,this.landArea3);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// ���p�����`�F�b�N
			valid.addValidation(new NumberValidation());
			// �������A�����������`�F�b�N
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * �y�n�ʐϒP�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLandAreaCrs(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.landAreaCrs2";
			ValidationChain valid = new ValidationChain(label,this.landAreaCrs2);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {

			String label = "assessment.input.landAreaCrs3";
			ValidationChain valid = new ValidationChain(label,this.landAreaCrs3);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		}
	}

	/**
	 * �����ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���p�����`�F�b�N
	 * �E�������A�����������`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuildingArea(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildingArea";
			ValidationChain valid = new ValidationChain(label,this.buildingArea);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// ���p�����`�F�b�N
			valid.addValidation(new NumberValidation());
			// �������A�����������`�F�b�N
			valid.addValidation(new PonitNumberValidation(5,2));

			valid.validate(errors);

		}
	}

	/**
	 * �����ʐϒP�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuildingAreaCrs(List<ValidationFailure> errors) {

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {

			String label = "assessment.input.buildingAreaCrs";
			ValidationChain valid = new ValidationChain(label,this.buildingAreaCrs);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "area_crs"));

			valid.validate(errors);

		}
	}

	/**
	 * �X�֔ԍ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���p�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validZip(List<ValidationFailure> errors) {
		String label = "assessment.input.zip";
		ValidationChain valid = new ValidationChain(label,this.zip);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());
		// �����`�F�b�N
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength(label, 7)));

		valid.validate(errors);
	}

	/**
	 * �s���{�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		String label = "assessment.input.prefCd";
		ValidationChain valid = new ValidationChain(label,this.prefCd);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());

		valid.validate(errors);
	}

	/**
	 * �s�撬���Ԓn �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddress(List<ValidationFailure> errors) {
		String label = "assessment.input.address";
		ValidationChain valid = new ValidationChain(label,this.address);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * ������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddressOther(List<ValidationFailure> errors) {
		String label = "assessment.input.addressOther";
		ValidationChain valid = new ValidationChain(label,this.addressOther);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * ���� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPresentCd(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.presentCd)) {

			String label = "assessment.input.presentCd";
			ValidationChain valid = new ValidationChain(label,this.presentCd);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_present_cd"));

			valid.validate(errors);

		}
	}

	/**
	 * ���p�\�莞�� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validBuyTimeCd(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.buyTimeCd)) {

			String label = "assessment.input.buyTimeCd";
			ValidationChain valid = new ValidationChain(label,this.buyTimeCd);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_buy_time_cd"));

			valid.validate(errors);

		}
	}

	/**
	 * �����ւ��̗L�� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validReplacementFlg(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.replacementFlg)) {

			String label = "assessment.input.replacementFlg";
			ValidationChain valid = new ValidationChain(label,this.replacementFlg);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "replacement_flg"));

			valid.validate(errors);

		}
	}

	/**
	 * �v�]�E���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRequestText(List<ValidationFailure> errors) {
		String label = "assessment.input.requestText";
		ValidationChain valid = new ValidationChain(label,this.requestText);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 300)));

		valid.validate(errors);
	}

	/**
	 * ����(��) �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�S�p�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLname(List<ValidationFailure> errors) {
		String label = "assessment.input.lname";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getLname());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�`�F�b�N
		valid.addValidation(new ZenkakuOnlyValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * ����(��) �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�S�p�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validFname(List<ValidationFailure> errors) {
		String label = "assessment.input.fname";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getFname());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�`�F�b�N
		valid.addValidation(new ZenkakuOnlyValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * �����E�J�i(��) �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�S�p�J�^�J�i�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validLnameKana(List<ValidationFailure> errors) {
		String label = "assessment.input.lnameKana";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getLnameKana());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�J�^�J�i�`�F�b�N
		valid.addValidation(new ZenkakuKanaValidator());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * �����E�J�i(��) �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�S�p�J�^�J�i�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validFnameKana(List<ValidationFailure> errors) {
		String label = "assessment.input.fnameKana";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getFnameKana());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �S�p�J�^�J�i�`�F�b�N
		valid.addValidation(new ZenkakuKanaValidator());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���[���A�h���X�̏����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEmail(List<ValidationFailure> errors) {
		String label = "assessment.input.email";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getEmail());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// ���[���A�h���X�̏����`�F�b�N
		valid.addValidation(new EmailRFCValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 255)));

		valid.validate(errors);
	}

	/**
	 * �d�b�ԍ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���l�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTel(List<ValidationFailure> errors) {
		String label = "assessment.input.tel";
		ValidationChain valid = new ValidationChain(label,this.getInquiryHeaderForm().getTel());

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// ���l�`�F�b�N
		valid.addValidation(new NumericValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));

		valid.validate(errors);
	}

	/**
	 * FAX�ԍ� �o���f�[�V����<br/>
	 * �E���l�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validFax(List<ValidationFailure> errors) {
		String label = "assessment.input.fax";
		ValidationChain valid = new ValidationChain(label,((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax());

		// ���l�`�F�b�N
		valid.addValidation(new NumericValidation());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));

		valid.validate(errors);
	}

	/**
	 * �A�����@ �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validContactType(List<ValidationFailure> errors) {

		if (!StringValidateUtil.isEmpty(this.contactType)) {

			String label = "assessment.input.contactType";
			ValidationChain valid = new ValidationChain(label,this.contactType);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));

			valid.validate(errors);

		}
	}

	/**
	 * �A���\�Ȏ��ԑ� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validContactTime(List<ValidationFailure> errors) {

		if (this.contactTime != null && this.contactTime.length > 0) {

			String label = "assessment.input.contactTime";
			for (String time : this.contactTime) {

				ValidationChain valid = new ValidationChain(label,time);

				// �p�^�[�����̓`�F�b�N
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_time"));

				valid.validate(errors);
			}
		}
	}

	/**
	 * �⍇�ҏZ���E�X�֔ԍ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E���p�����`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserZip(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userZip";
			ValidationChain valid = new ValidationChain(label,this.userZip);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// ���p�����`�F�b�N
			valid.addValidation(new NumericValidation());
			// �����`�F�b�N
			valid.addValidation(new LengthValidator(this.lengthUtils.getLength(label, 7)));

			valid.validate(errors);

		}
	}

	/**
	 * �⍇�ҏZ���E�s���{�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserPrefCd(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userPrefCd";
			ValidationChain valid = new ValidationChain(label,this.userPrefCd);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());

			valid.validate(errors);

		}
	}

	/**
	 * �⍇�ҏZ���E�s�撬���Ԓn �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserAddress(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userAddress";
			ValidationChain valid = new ValidationChain(label,this.userAddress);

			// �K�{�`�F�b�N
			valid.addValidation(new NullOrEmptyCheckValidation());
			// �����`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

			valid.validate(errors);

		}
	}

	/**
	 * �⍇�ҏZ���E������ �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserAddressOther(List<ValidationFailure> errors) {

		if (StringValidateUtil.isEmpty(this.sameWithHousing) || !"1".equals(this.sameWithHousing)) {

			String label = "assessment.input.userAddressOther";
			ValidationChain valid = new ValidationChain(label,this.userAddressOther);

			// �����`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

			valid.validate(errors);

		}
	}

	/**
	 * �A���P�[�g�� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAnsCd(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;

		String label = "assessment.input.ansCd";
		for (String ans : this.ansCd) {
			ValidationChain valid = new ValidationChain(label,ans);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

			valid.validate(errors);
		}
	}

	/**
	 * �A���P�[�g_���̑��񓚂P �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEtcAnswer1(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("008".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer1";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer1);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * �A���P�[�g_���̑��񓚂Q �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEtcAnswer2(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("009".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer2";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer2);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * �A���P�[�g_���̑��񓚂R �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEtcAnswer3(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("010".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		String label = "assessment.input.etcAnswer3";
		ValidationChain valid = new ValidationChain(label,this.etcAnswer3);

		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}



	/**
	 * �����œn���ꂽ������̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param inquiryAssessment �l��ݒ肷�鍸����̃o���[�I�u�W�F�N�g
	 *
	 */
	public void copyToInquiryAssessment(InquiryAssessment inquiryAssessment) {

		// ���p������ʂ�ݒ�
		inquiryAssessment.setBuyHousingType(this.buyHousingType);

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.buyHousingType)) {
			// �}���V�����̏ꍇ

			// �Ԏ�CD��ݒ�
			inquiryAssessment.setLayoutCd(this.layoutCd1);

			// ��L�ʐς�ݒ�
			inquiryAssessment.setPersonalArea(new BigDecimal(this.personalArea));

			// �z�N����ݒ�
			if (!StringValidateUtil.isEmpty(this.buildAge1)) {
				inquiryAssessment.setBuildAge(Integer.valueOf(this.buildAge1));
			}

		} else if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.buyHousingType)) {
			// �ˌ��̏ꍇ

			// �Ԏ�CD��ݒ�
			inquiryAssessment.setLayoutCd(this.layoutCd2);

			// �z�N����ݒ�
			if (!StringValidateUtil.isEmpty(this.buildAge2)) {
				inquiryAssessment.setBuildAge(Integer.valueOf(this.buildAge2));
			}

			// �y�n�ʐς�ݒ�
			inquiryAssessment.setLandArea(new BigDecimal(this.landArea2));

			// �y�n�ʐϒP�ʂ�ݒ�
			inquiryAssessment.setLandAreaCrs(this.landAreaCrs2);

			// �����ʐς�ݒ�
			inquiryAssessment.setBuildingArea(new BigDecimal(this.buildingArea));

			// �����ʐϒP�ʂ�ݒ�
			inquiryAssessment.setBuildingAreaCrs(this.buildingAreaCrs);

		} else if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.buyHousingType)) {
			// �y�n�̏ꍇ

			// �y�n�ʐς�ݒ�
			inquiryAssessment.setLandArea(new BigDecimal(this.landArea3));

			// �y�n�ʐϒP�ʂ�ݒ�
			inquiryAssessment.setLandAreaCrs(this.landAreaCrs3);

		}

		// ���p�����E�X�֔ԍ���ݒ�
		inquiryAssessment.setZip(this.zip);

		// ���p�����E�s���{��CD��ݒ�
		inquiryAssessment.setPrefCd(this.prefCd);

		// ���p�����E�s�撬���Ԓn��ݒ�
		inquiryAssessment.setAddress(this.address);

		// ���p�����E��������ݒ�
		inquiryAssessment.setAddressOther(this.addressOther);

		// �����ݒ�
		inquiryAssessment.setPresentCd(this.presentCd);

		// ���p�\�莞����ݒ�
		inquiryAssessment.setBuyTimeCd(this.buyTimeCd);

		// �����ւ��̗L����ݒ�
		inquiryAssessment.setReplacementFlg(this.replacementFlg);

		// �v�]�E�����ݒ�
		inquiryAssessment.setRequestText(requestText);

		// �A�����@��ݒ�
		inquiryAssessment.setContactType(this.contactType);

		if (this.contactTime != null) {
			// �A�����@��ݒ�
			StringBuilder sb = new StringBuilder();
			for (String time : this.contactTime) {
				sb.append(time);
				sb.append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			inquiryAssessment.setContactTime(sb.toString());
		}

	}

	/**
	 * �����œn���ꂽ������̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param inquiryHousingQuestions �l��ݒ肷�鍸����̃o���[�I�u�W�F�N�g
	 *
	 */
	public void copyToInquiryHousingQuestion(InquiryHousingQuestion[] inquiryHousingQuestions) {
		int i = 0;
		if (this.ansCd != null && this.ansCd.length > 0) {
			for (String questionId : this.ansCd) {
				inquiryHousingQuestions[i].setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
				inquiryHousingQuestions[i].setAnsCd(questionId);
				if ("008".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer1);
				} else if ("009".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer2);
				} else if ("010".equals(questionId)) {
					inquiryHousingQuestions[i].setNote(this.etcAnswer3);
				}
				i++;
			}
		}
	}

}
