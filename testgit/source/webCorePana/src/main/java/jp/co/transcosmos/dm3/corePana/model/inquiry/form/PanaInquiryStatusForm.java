package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.inquiry.InquiryInterface;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryStatusForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryDtlInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.model.inquiry.InquiryInfo;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;



/**
 * �⍇���X�e�[�^�X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C		2015.04.03	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaInquiryStatusForm extends InquiryStatusForm {

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected PanaInquiryStatusForm(){
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected PanaInquiryStatusForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** �������� */
	private String displayHousingName;
	/** �Ή����e(�\���p�j */
	private String showAnswerText;

	/** �����ԍ��@�i���������j */
	private String keyHousingCd;
	/** �������@�i���������j */
	private String keyDisplayHousingName;
	/** ����ԍ��@�i���������j */
	private String keyUserId;
	/** ���[���A�h���X�@�i���������j */
	private String keyEmail;
	/** �⍇�����J�n���@�i���������j */
	private String keyInquiryDateStart;
	/** �⍇�����I�����@�i���������j */
	private String keyInquiryDateEnd;
	/** �⍇��ʁ@�i���������j */
	private String[] keyInquiryType;
	/** ���⍇�����e��ʁ@�i���������j */
	private String[] keyInquiryDtlType;
	/** �Ή��X�e�[�^�X�@�i���������j */
	private String keyAnswerStatus;
	/** ���⍇��ID�@�i���������j*/
	private String keyInquiryId;

	/**
	 * �������̂��擾����B<br/>
	 * <br/>
	 * @return ��������
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * �������̂�ݒ肷��B<br/>
	 * <br/>
	 * @param ��������
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * �Ή����e(�\���p�j���擾����B<br/>
	 * <br/>
	 * @return �Ή����e(�\���p�j
	 */
	public String getShowAnswerText() {
		return showAnswerText;
	}

	/**
	 * �Ή����e(�\���p�j��ݒ肷��B<br/>
	 * <br/>
	 * @param �Ή����e(�\���p�j
	 */
	public void setShowAnswerText(String showAnswerText) {
		this.showAnswerText = showAnswerText;
	}

	/**
	 * �����ԍ��@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �����ԍ��@�i���������j
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * �����ԍ��@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �����ԍ��@�i���������j
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * �������@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �������@�i���������j
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * �������@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �������@�i���������j
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * ����ԍ��@�i���������j���擾����B<br/>
	 * <br/>
	 * @return ����ԍ��@�i���������j
	 */
	public String getKeyUserId() {
		return keyUserId;
	}

	/**
	 * ����ԍ��@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param ����ԍ��@�i���������j
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * ���[���A�h���X�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X�@�i���������j
	 */
	public String getKeyEmail() {
		return keyEmail;
	}

	/**
	 * ���[���A�h���X�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param ���[���A�h���X�@�i���������j
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * �⍇�����J�n���@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �⍇�����J�n���@�i���������j
	 */
	public String getKeyInquiryDateStart() {
		return keyInquiryDateStart;
	}

	/**
	 * �⍇�����J�n���@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �⍇�����J�n���@�i���������j
	 */
	public void setKeyInquiryDateStart(String keyInquiryDateStart) {
		this.keyInquiryDateStart = keyInquiryDateStart;
	}

	/**
	 * �⍇�����I�����@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �⍇�����I�����@�i���������j
	 */
	public String getKeyInquiryDateEnd() {
		return keyInquiryDateEnd;
	}

	/**
	 * �⍇�����I�����@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �⍇�����I�����@�i���������j
	 */
	public void setKeyInquiryDateEnd(String keyInquiryDateEnd) {
		this.keyInquiryDateEnd = keyInquiryDateEnd;
	}

	/**
	 * �⍇��ʁ@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �⍇��ʁ@�i���������j
	 */
	public String[] getKeyInquiryType() {
		return keyInquiryType;
	}

	/**
	 * �⍇��ʁ@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �⍇��ʁ@�i���������j
	 */
	public void setKeyInquiryType(String[] keyInquiryType) {
		this.keyInquiryType = keyInquiryType;
	}

	/**
	 * ���⍇�����e��ʁ@�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���⍇�����e��ʁ@�i���������j
	 */
	public String[] getKeyInquiryDtlType() {
		return keyInquiryDtlType;
	}

	/**
	 * ���⍇�����e��ʁ@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param ���⍇�����e��ʁ@�i���������j
	 */
	public void setKeyInquiryDtlType(String[] keyInquiryDtlType) {
		this.keyInquiryDtlType = keyInquiryDtlType;
	}

	/**
	 * �Ή��X�e�[�^�X�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return �Ή��X�e�[�^�X�@�i���������j
	 */
	public String getKeyAnswerStatus() {
		return keyAnswerStatus;
	}

	/**
	 * �Ή��X�e�[�^�X�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param �Ή��X�e�[�^�X�@�i���������j
	 */
	public void setKeyAnswerStatus(String keyAnswerStatus) {
		this.keyAnswerStatus = keyAnswerStatus;
	}

	/**
	 * ���⍇��ID�@�i���������j���擾����B<br/>
	 * <br/>
	 * @return ���⍇��ID�@�i���������j
	 */
	public String getKeyInquiryId() {
		return keyInquiryId;
	}

	/**
	 * ���⍇��ID�@�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param ���⍇��ID�@�i���������j
	 */
	public void setKeyInquiryId(String keyInquiryId) {
		this.keyInquiryId = keyInquiryId;
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

		// �Ή��X�e�[�^�X
		 ValidationChain valAnswerStatus = new ValidationChain("inquiry.input.answerStatus",this.getAnswerStatus());
		// �K�{���̓`�F�b�N
		 valAnswerStatus.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		 valAnswerStatus.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_answerStatus"));
		 valAnswerStatus.validate(errors);

		 // �Ή����e
		 ValidationChain valAnswerText = new ValidationChain("inquiry.input.answerText",this.getAnswerText());
		 // �����`�F�b�N
		 valAnswerText.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("inquiry.input.answerText", 1000)));
		 valAnswerText.validate(errors);

        return (startSize == errors.size());
	}
	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo�@�����������⍇�����Ǘ��p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(InquiryInterface inquiryInfo, String inquiryType) {

		//�����⍇�����
		if (inquiryType.equalsIgnoreCase("00")) {
			if (((InquiryInfo)inquiryInfo).getHousings().size() > 0) {
				// �������� ��ݒ�
				HousingInfo housingInfo = (HousingInfo) ((InquiryInfo)inquiryInfo).getHousings().get(0).getHousingInfo().getItems().get("housingInfo");
				this.displayHousingName = housingInfo.getDisplayHousingName();
			}
			//�⍇��� ��ݒ�
			this.setInquiryType(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getInquiryType());
			// �Ή��X�e�[�^�X ��ݒ�
			this.setAnswerStatus(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getAnswerStatus());
			// �Ή����e ��ݒ�
			this.setAnswerText(inquiryInfo.getInquiryHeaderInfo().getInquiryHeader().getAnswerText());

			//�ėp�⍇�����
		} else if (inquiryType.equalsIgnoreCase("01")) {
			InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryHeader");
			//�⍇��� ��ݒ�
			this.setInquiryType(inquiryHeader.getInquiryType());
			// �Ή��X�e�[�^�X ��ݒ�
			this.setAnswerStatus(inquiryHeader.getAnswerStatus());
			// �Ή����e ��ݒ�
			this.setAnswerText(inquiryHeader.getAnswerText());

			//������
		} else if (inquiryType.equalsIgnoreCase("02")) {
			InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryHeader");
			//�⍇��� ��ݒ�
			this.setInquiryType(inquiryHeader.getInquiryType());
			// �Ή��X�e�[�^�X ��ݒ�
			this.setAnswerStatus(inquiryHeader.getAnswerStatus());
			// �Ή����e ��ݒ�
			this.setAnswerText(inquiryHeader.getAnswerText());
		}
	}

	/**
	 * �擾�����������model �֊i�[����B<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo�@��������������⍇���Ǘ��p�o���[�I�u�W�F�N�g
	 * * @param model     model �I�u�W�F�N�g
	 */
	public void setAssessmentData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryHeader");
		InquiryAssessment inquiryAssessment = (InquiryAssessment)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("inquiryAssessment");
		PrefMst headerPrefMst = (PrefMst)((InquiryInfo)inquiryInfo).getAssessmentInquiry().get(0).getItems().get("PrefMst");
		PrefMst assessmentPrefMst = (PrefMst)((InquiryInfo)inquiryInfo).getPrefMst();

		// ���ݒn
		 StringBuffer assessmentAddress = new StringBuffer();
		 // �⍇�ҏZ���E�X�֔ԍ�
		 if (inquiryAssessment.getZip() != null) {
			 assessmentAddress.append("��");
			 assessmentAddress.append(inquiryAssessment.getZip()).append(" ");
		 }
		 // �⍇�ҏZ���E�s���{��CD���s���{���}�X�^����擾�����s���{����
		 if (assessmentPrefMst != null) {
			 if (assessmentPrefMst.getPrefName() != null) {
				 assessmentAddress.append(assessmentPrefMst.getPrefName());
			 }
		 }
		 // �⍇�ҏZ���E�s�撬����
		 if (inquiryAssessment.getAddress() != null) {
			 assessmentAddress.append(inquiryAssessment.getAddress());
		 }
		 // �⍇�ҏZ���E�����Ԓn���̑�
		 if (inquiryAssessment.getAddressOther() != null) {
			 assessmentAddress.append(inquiryAssessment.getAddressOther());
		 }

		 //���Z��
		 StringBuffer headerAddress = new StringBuffer();
		 // �⍇�ҏZ���E�X�֔ԍ�
		 if (inquiryHeader.getZip() != null) {
			 headerAddress.append("��");
			 headerAddress.append(inquiryHeader.getZip()).append(" ");
		 }
		 // �⍇�ҏZ���E�s���{��CD���s���{���}�X�^����擾�����s���{����
		 if (headerPrefMst.getPrefName() != null) {
			 headerAddress.append(headerPrefMst.getPrefName());
		 }
		 // �⍇�ҏZ���E�s�撬����
		 if (inquiryHeader.getAddress() != null) {
			 headerAddress.append(inquiryHeader.getAddress());
		 }
		 // �⍇�ҏZ���E�����Ԓn���̑�
		 if (inquiryHeader.getAddressOther() != null) {
			 headerAddress.append(inquiryHeader.getAddressOther());
		 }

		// �A���\�Ȏ��ԑ�
		String[] contactTimes = null;
		if (inquiryAssessment.getContactTime() != null) {
			contactTimes = inquiryAssessment.getContactTime().split(",");
		}

		 // �A���P�[�g
		 int size = ((InquiryInfo)inquiryInfo).getAssessmentInquiry().size();
		 InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[size];
		 for ( int i = 0; i < ((InquiryInfo)inquiryInfo).getAssessmentInquiry().size(); i++) {
			InquiryHousingQuestion inquiryHousingQuestion = (InquiryHousingQuestion)((InquiryInfo)inquiryInfo).
					getAssessmentInquiry().get(i).getItems().get("inquiryHousingQuestion");
			inquiryHousingQuestions[i] = inquiryHousingQuestion;
		}

		// ��������i�[����
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryAssessment", inquiryAssessment);
		model.put("assessmentAddress", assessmentAddress.toString());
		model.put("headerAddress", headerAddress.toString());
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestions);
	}

	/**
	 * �擾�����ėp�⍇������model �֊i�[����B<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo�@�����������ėp�⍇�����Ǘ��p�o���[�I�u�W�F�N�g
	 * * @param model     model �I�u�W�F�N�g
	 */
	public void setGeneralData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryHeader");
		InquiryDtlInfo inquiryDtlInfo = (InquiryDtlInfo)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryDtlInfo");
		InquiryGeneral inquiryGeneral = (InquiryGeneral)((InquiryInfo)inquiryInfo).getGeneralInquiry().get(0).getItems().get("inquiryGeneral");

		// ����
		String fmtDate = null;
		if (inquiryGeneral.getEventDatetime() != null) {
			fmtDate = new SimpleDateFormat("M��d���@H:mm").format(inquiryGeneral.getEventDatetime());
		}

		// �A���\�Ȏ��ԑ�
		String[] contactTimes = null;
		if (inquiryGeneral.getContactTime() != null) {
			contactTimes = inquiryGeneral.getContactTime().split(",");
		}

		 // �A���P�[�g
		 int size = ((InquiryInfo)inquiryInfo).getGeneralInquiry().size();
		 InquiryHousingQuestion[] inquiryHousingQuestions = new InquiryHousingQuestion[size];
		 for ( int i = 0; i < ((InquiryInfo)inquiryInfo).getGeneralInquiry().size(); i++) {
			InquiryHousingQuestion inquiryHousingQuestion = (InquiryHousingQuestion)((InquiryInfo)inquiryInfo).
					getGeneralInquiry().get(i).getItems().get("inquiryHousingQuestion");
			inquiryHousingQuestions[i] = inquiryHousingQuestion;
		}

		// ��������i�[����
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryDtlInfo", inquiryDtlInfo);
		model.put("inquiryGeneral", inquiryGeneral);
		model.put("fmtDate", fmtDate);
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestions);
	}

	/**
	 * �擾���������⍇������model �֊i�[����B<br/>
	 * <br/>
	 * @param inquiryInfo InquiryInfo�@���������������⍇�����Ǘ��p�o���[�I�u�W�F�N�g
	 * * @param model     model �I�u�W�F�N�g
	 */
	public void setHousingData(InquiryInfo inquiryInfo, Map<String, Object> model) {

		HousingInfo housingInfo = new HousingInfo();
		BuildingInfo buildingInfo = new BuildingInfo();
		BuildingDtlInfo buildingDtlInfo = new BuildingDtlInfo();
		String fmtDate = null;
		StringBuffer address1 = new StringBuffer();
		List<String> nearStationList = new ArrayList<String>();

		if (((InquiryInfo)inquiryInfo).getHousings().size() > 0){
			housingInfo = (HousingInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getHousingInfo().getItems().get("housingInfo");
			buildingInfo = (BuildingInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("buildingInfo");
			buildingDtlInfo = (BuildingDtlInfo)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");

			// �z�N
			if (buildingInfo.getCompDate() != null) {
				fmtDate = new SimpleDateFormat("yyyy�NMM��").format(buildingInfo.getCompDate());
			}

			//�Z��1
			PrefMst prefMst1 = (PrefMst)((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingInfo().getItems().get("prefMst");
			 // ���ݒn�E�X�֔ԍ�
			 if (buildingInfo.getZip() != null) {
				 address1.append("��");
				 address1.append(buildingInfo.getZip()).append(" ");
			 }
			 // ���ݒn�E�s���{��CD���s���{���}�X�^����擾�����s���{����
			 if (prefMst1.getPrefName() != null) {
				 address1.append(prefMst1.getPrefName());
			 }
			 // ���ݒn�E�s�撬����
			 if (buildingInfo.getAddressName() != null) {
				 address1.append(buildingInfo.getAddressName());
			 }
			 // ���ݒn�E�����Ԓn
			 if (buildingInfo.getAddressOther1() != null) {
				 address1.append(buildingInfo.getAddressOther1());
			 }
			// ���ݒn�E���������̑�
			 if (buildingInfo.getAddressOther2() != null) {
				 address1.append(buildingInfo.getAddressOther2());
			 }

			 // �Ŋ��w
			 List<JoinResult> buildingStationInfoList =((InquiryInfo)inquiryInfo).getHousings().get(0).getBuilding().getBuildingStationInfoList();
			 BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
			 RouteMst routeMst = new RouteMst();
			 StationMst stationMst = new StationMst();
			 StringBuffer nearStation = new StringBuffer();
			 for (int i = 0; i < buildingStationInfoList.size(); i++) {

				 // �����Ŋ��w���̎擾
				 buildingStationInfo = new BuildingStationInfo();
				 buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
				 // �H���}�X�g�̎擾
				 routeMst = new RouteMst();
				 routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
				 // �w�}�X�g�̎擾
				 stationMst = new StationMst();
				 stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

				 // ��\�H����
				 nearStation = new StringBuffer();
				 if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
					 nearStation.append(routeMst.getRouteName());
				 } else {
					 if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
						 nearStation.append(buildingStationInfo.getDefaultRouteName());
					 }
				 }
				 nearStation.append(" ");
				 // �w��
				 if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
					 nearStation.append(stationMst.getStationName());
				 } else {
					 if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
						 nearStation.append(buildingStationInfo.getStationName());
					 }
				 }
				 nearStation.append(" ");
				 // �o�X��Ж�
				 if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
					 nearStation.append(buildingStationInfo.getBusCompany()).append(" ").append("�k��");
				 }
				 nearStation.append(" ");
				 // �o�X�₩��̓k������
				 if (buildingStationInfo.getTimeFromBusStop() != null) {
					 nearStation.append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("��");
				 }
				 nearStationList.add(nearStation.toString());
			 }
		}

		InquiryHeader inquiryHeader = (InquiryHeader)((InquiryInfo)inquiryInfo).getInquiryHeaderInfo().getInquiryHeader();
		InquiryDtlInfo[] inquiryDtlInfo = (InquiryDtlInfo[])((InquiryInfo)inquiryInfo).getInquiryHeaderInfo().getInquiryDtlInfos();
		InquiryHousing inquiryHousing = (InquiryHousing)((InquiryInfo)inquiryInfo).getInquiryHousing();

		//�Z��2
		 PrefMst prefMst2 = (PrefMst)((InquiryInfo)inquiryInfo).getPrefMst();
		 StringBuffer address2 = new StringBuffer();
		 // �⍇�ҏZ���E�X�֔ԍ�
		 if (inquiryHeader.getZip() != null) {
			 address2.append("��");
			 address2.append(inquiryHeader.getZip()).append(" ");
		 }
		// �⍇�ҏZ���E�s���{��CD���s���{���}�X�^����擾�����s���{����
		if (prefMst2 != null) {
			if (prefMst2.getPrefName() != null) {
				address2.append(prefMst2.getPrefName());
			}
		}
		 // �⍇�ҏZ���E�s�撬����
		 if (inquiryHeader.getAddress() != null) {
			 address2.append(inquiryHeader.getAddress());
		 }
		 // �⍇�ҏZ���E�����Ԓn���̑�
		 if (inquiryHeader.getAddressOther() != null) {
			 address2.append(inquiryHeader.getAddressOther());
		 }

		 // �A���\�Ȏ��ԑ�
		 String[] contactTimes = null;
		if (inquiryHousing != null) {
			if (!StringValidateUtil.isEmpty(inquiryHousing.getContactTime())) {
				contactTimes = inquiryHousing.getContactTime().split(",");
			}
		}

		 // �A���P�[�g
		 InquiryHousingQuestion[] inquiryHousingQuestion = (InquiryHousingQuestion[])((InquiryInfo)inquiryInfo).getInquiryHousingQuestion();

		// �����⍇�������i�[����
		if (inquiryDtlInfo != null) {
			model.put("inquiryDtlInfo", inquiryDtlInfo[0]);
		}
		model.put("housingInfo", housingInfo);
		model.put("buildingInfo", buildingInfo);
		model.put("buildingDtlInfo", buildingDtlInfo);
		model.put("fmtDate", fmtDate);
		model.put("address1", address1.toString());
		model.put("nearStationList", nearStationList);
		model.put("inquiryHeader", inquiryHeader);
		model.put("inquiryHousing", inquiryHousing);
		model.put("address2", address2.toString());
		model.put("contactTimes", contactTimes);
		model.put("inquiryHousingQuestion", inquiryHousingQuestion);
	}
}
