package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.model.inquiry.form.InquiryHeaderForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingStationInfo;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.core.vo.InquiryHousing;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.util.PanaCalcUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaFileUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
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
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * �����̂��₢���킹���̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * �s�����C    2015.04.23 �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaInquiryHousingForm extends HousingInquiryForm implements Validateable {

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/**
	* �R���X�g���N�^�[<br/>
	* Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	* <br/>
	*/
	protected PanaInquiryHousingForm(){
		super();
	}

	/**
	* �R���X�g���N�^�[<br/>
	* Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	* <br/>
	* @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	* @param codeLookupManager ���ʃR�[�h�ϊ�����
	*/
	protected PanaInquiryHousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** Url�p�^�[�� */
	private String urlPattern;
	/** ���₢���킹�ԍ� */
	private String inquiryId;
	/** �����ԍ� */
	private String housingCd;
	/** command �p�����[�^ */
	private String command;
	/** ���⍇�����e�i�\���p�j */
	private String showInquiryText;
	/** �s���{���� */
	private String prefName;
	/** �s���{��back */
	private String backPrefCd;
	/** �A�����@ */
	private String contactType;
	/** �A���\�Ȏ��ԑ� */
	private String[] contactTime;
	/** �A���P�[�g�񓚊i�[ */
	private String[] ansCd;
	/** �A���P�[�g�񓚊i�[�i���͂P�j */
	private String etcAnswer1;
	/** �A���P�[�g�񓚊i�[�i���͂Q�j */
	private String etcAnswer2;
	/** �A���P�[�g�񓚊i�[�i���͂R�j */
	private String etcAnswer3;

	/** ���⍇�����e�i���[���\���p�j */
	private String inquiryDtlTypeMail;

	/**
	* Url�p�^�[�� ���擾����B<br/>
	* <br/>
	* @return Url�p�^�[��
	*/
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	* Url�p�^�[�� ��ݒ肷��B<br/>
	* <br/>
	* @param urlPattern Url�p�^�[��
	*/
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	/**
	* ���₢���킹�ԍ� ���擾����B<br/>
	* <br/>
	* @return ���₢���킹�ԍ�
	*/
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	* ���₢���킹�ԍ� ��ݒ肷��B<br/>
	* <br/>
	* @param inquiryId ���₢���킹�ԍ�
	*/
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	* �����ԍ� ���擾����B<br/>
	* <br/>
	* @return �����ԍ�
	*/
	public String getHousingCd() {
		return housingCd;
	}

	/**
	* �����ԍ� ��ݒ肷��B<br/>
	* <br/>
	* @param housingCd �����ԍ�
	*/
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	* command �p�����[�^���擾����B<br/>
	* <br/>
	* @return command �p�����[�^
	*/
	public String getCommand() {
		return command;
	}

	/**
	* command �p�����[�^��ݒ肷��B<br/>
	* <br/>
	* @param command command �p�����[�^
	*/
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	* ���⍇�����e�i�\���p�j ���擾����B<br/>
	* <br/>
	* @return ���⍇�����e�i�\���p�j
	*/
	public String getShowInquiryText() {
		return showInquiryText;
	}

	/**
	* ���⍇�����e�i�\���p�j ��ݒ肷��B<br/>
	* <br/>
	* @param showInquiryText
	*/
	public void setShowInquiryText(String showInquiryText) {
		this.showInquiryText = showInquiryText;
	}

	/**
	* �s���{�������擾����B<br/>
	* <br/>
	* @return �s���{����
	*/
	public String getPrefName() {
		return prefName;
	}

	/**
	* �s���{������ݒ肷��B<br/>
	* <br/>
	* @param prefName �s���{����
	*/
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	*�s���{��back���擾����B<br/>
	* <br/>
	* @return �s���{��back
	*/
	public String getBackPrefCd() {
		return backPrefCd;
	}

	/**
	* �s���{��back��ݒ肷��B<br/>
	* <br/>
	* @param backPrefCd �s���{��back
	*/
	public void setBackPrefCd(String backPrefCd) {
		this.backPrefCd = backPrefCd;
	}

	/**
	* �A�����@���擾����B<br/>
	* <br/>
	* @return �A�����@
	*/
	public String getContactType() {
		return contactType;
	}

	/**
	* �A�����@��ݒ肷��B<br/>
	* <br/>
	* @param contactType �A�����@
	*/
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	/**
	* �A���\�Ȏ��ԑт��擾����B<br/>
	* <br/>
	* @return �A���\�Ȏ��ԑ�
	*/
	public String[] getContactTime() {
		return contactTime;
	}

	/**
	* �A���\�Ȏ��ԑт�ݒ肷��B<br/>
	* <br/>
	* @param contactTime �A���\�Ȏ��ԑ�
	*/
	public void setContactTime(String[] contactTime) {
		this.contactTime = contactTime;
	}

	/**
	* �A���P�[�g�񓚊i�[���擾����B<br/>
	* <br/>
	* @return �A���P�[�g�񓚊i�[
	*/
	public String[] getAnsCd() {
		return ansCd;
	}

	/**
	* �A���P�[�g�񓚊i�[��ݒ肷��B<br/>
	* <br/>
	* @param ansCd �A���P�[�g�񓚊i�[
	*/
	public void setAnsCd(String[] ansCd) {
		this.ansCd = ansCd;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂P�j���擾����B<br/>
	* <br/>
	* @return �A���P�[�g�񓚊i�[�i���͂P�j
	*/
	public String getEtcAnswer1() {
		return etcAnswer1;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂P�j��ݒ肷��B<br/>
	* <br/>
	* @param etcAnswer1 �A���P�[�g�񓚊i�[�i���͂P�j
	*/
	public void setEtcAnswer1(String etcAnswer1) {
		this.etcAnswer1 = etcAnswer1;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂Q�j���擾����B<br/>
	* <br/>
	* @return �A���P�[�g�񓚊i�[�i���͂Q�j
	*/
	public String getEtcAnswer2() {
		return etcAnswer2;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂Q�j��ݒ肷��B<br/>
	* <br/>
	* @param etcAnswer2 �A���P�[�g�񓚊i�[�i���͂Q�j
	*/
	public void setEtcAnswer2(String etcAnswer2) {
		this.etcAnswer2 = etcAnswer2;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂R�j���擾����B<br/>
	* <br/>
	* @return �A���P�[�g�񓚊i�[�i���͂R�j
	*/
	public String getEtcAnswer3() {
		return etcAnswer3;
	}

	/**
	* �A���P�[�g�񓚊i�[�i���͂R�j��ݒ肷��B<br/>
	* <br/>
	* @param etcAnswer3 �A���P�[�g�񓚊i�[�i���͂R�j
	*/
	public void setEtcAnswer3(String etcAnswer3) {
		this.etcAnswer3 = etcAnswer3;
	}

	/**
	* @return inquiryDtlTypeMail
	*/
	public String getInquiryDtlTypeMail() {
		return inquiryDtlTypeMail;
	}

	/**
	* @param inquiryDtlTypeMail �Z�b�g���� inquiryDtlTypeMail
	*/
	public void setInquiryDtlTypeMail(String inquiryDtlTypeMail) {
		this.inquiryDtlTypeMail = inquiryDtlTypeMail;
	}

	/**
	* �o���f�[�V��������<br/>
	* ���N�G�X�g�̃o���f�[�V�������s��<br/>
	* <br/>
	*
	* @param errors
	*            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	* @return ���펞 true�A�G���[�� false
	*/

	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		// ���₢���킹���e�͕K�{���͂���B
		valInquiryDtlType(errors);

        // ���₢���킹���e�ڍׂ̌����`�F�b�N
        valInquiryText(errors);

        // �����O�̓��̓`�F�b�N
        valLnameFname(errors);

        // �����O�i�t���K�i�j�̓��̓`�F�b�N
        valLnameKanaFnameKana(errors);

        // ���[���A�h���X�̓��̓`�F�b�N
        valEmail(errors);

        // �d�b�ԍ��̓��̓`�F�b�N
        valTel(errors);

		// FAX�ԍ��̓��̓`�F�b�N
		valFax(errors);

		// �X�֔ԍ��̓��̓`�F�b�N
		valZip(errors);

		// �s���{�����̓��̓`�F�b�N
		valPrefName(errors);

		// �s�撬���Ԓn�̓��̓`�F�b�N
		valAddress(errors);

		// �������̓��̓`�F�b�N
		valAddressOther(errors);

		// ����]�̘A�����@�̓��̓`�F�b�N
		valContactType(errors);

		// �A���\�Ȏ��ԑт̓��̓`�F�b�N
		valContactTime(errors);

		// �A���P�[�g�񓚃`�F�b�N
		validAnsCd(errors);

		// �A���P�[�g_���̑��񓚃`�F�b�N
		validEtcAnswer1(errors);

		// �A���P�[�g_���̑��񓚃`�F�b�N
		validEtcAnswer2(errors);

		// �A���P�[�g_���̑��񓚃`�F�b�N
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
     * ���₢���킹��� �o���f�[�V����<br/>
     * �E�K�{�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valInquiryDtlType(List<ValidationFailure> errors) {
    	// ���₢���킹���
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}

		// ���₢���킹���
		ValidationChain valinquiryDtlType = new ValidationChain("inquiryHousing.input.valInquiryDtlType", inquiryDtlType);
		// �K�{���̓`�F�b�N
		valinquiryDtlType.addValidation(new NullOrEmptyCheckValidation());
		// �p�^�[���`�F�b�N
		valinquiryDtlType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_housing_dtl_type"));
		valinquiryDtlType.validate(errors);
    }

	/**
     * ���₢���킹���e �o���f�[�V����<br/>
     * �E�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valInquiryText(List<ValidationFailure> errors) {
    	// ���₢���킹���e
    	ValidationChain valInquiryText = new ValidationChain("inquiryHousing.input.valInquiryText", this.getInquiryHeaderForm().getInquiryText());
        // �����`�F�b�N
    	valInquiryText.addValidation(new MaxLengthValidation(300));
    	valInquiryText.validate(errors);
    }

	/**
     * �����O �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valLnameFname(List<ValidationFailure> errors) {
    	// �����O_��
    	ValidationChain valLname = new ValidationChain("inquiryHousing.input.valLname", this.getInquiryHeaderForm().getLname());
    	// �K�{���̓`�F�b�N
    	valLname.addValidation(new NullOrEmptyCheckValidation());
    	// �����`�F�b�N
    	valLname.addValidation(new MaxLengthValidation(30));
    	// �S�p�`�F�b�N
    	valLname.addValidation(new ZenkakuOnlyValidation());
    	valLname.validate(errors);

    	// �����O_��
    	ValidationChain valFname = new ValidationChain("inquiryHousing.input.valFname", this.getInquiryHeaderForm().getFname());
    	// �K�{���̓`�F�b�N
    	valFname.addValidation(new NullOrEmptyCheckValidation());
    	// �����`�F�b�N
    	valFname.addValidation(new MaxLengthValidation(30));
    	// �S�p�`�F�b�N
    	valFname.addValidation(new ZenkakuOnlyValidation());
    	valFname.validate(errors);
    }

	/**
     * �����O�i�t���K�i�j �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valLnameKanaFnameKana(List<ValidationFailure> errors) {
    	// �����O�i�t���K�i�j_��
    	ValidationChain valLnameKana = new ValidationChain("inquiryHousing.input.valLnameKana", this.getInquiryHeaderForm().getLnameKana());
    	// �K�{���̓`�F�b�N
    	valLnameKana.addValidation(new NullOrEmptyCheckValidation());
    	// �����`�F�b�N
    	valLnameKana.addValidation(new MaxLengthValidation(30));
    	// �S�p�`�F�b�N
    	valLnameKana.addValidation(new ZenkakuKanaValidator());
    	valLnameKana.validate(errors);

    	// �����O�i�t���K�i�j_��
    	ValidationChain valFnameKana = new ValidationChain("inquiryHousing.input.valFnameKana", this.getInquiryHeaderForm().getFnameKana());
    	// �K�{���̓`�F�b�N
    	valFnameKana.addValidation(new NullOrEmptyCheckValidation());
    	// �����`�F�b�N
    	valFnameKana.addValidation(new MaxLengthValidation(30));
    	// �S�p�`�F�b�N
    	valFnameKana.addValidation(new ZenkakuKanaValidator());
    	valFnameKana.validate(errors);
    }

	/**
     * ���[���A�h���X �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valEmail(List<ValidationFailure> errors) {
    	// �S�p�����˔��p����
    	this.getInquiryHeaderForm().setEmail(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getEmail()));
    	// ���[���A�h���X
    	ValidationChain valEmail = new ValidationChain("inquiryHousing.input.valEmail", this.getInquiryHeaderForm().getEmail());
    	// �K�{�`�F�b�N
    	valEmail.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
    	valEmail.addValidation(new MaxLengthValidation(255));
        // ���[���A�h���X�̏����`�F�b�N
    	valEmail.addValidation(new EmailRFCValidation());
    	valEmail.validate(errors);
    }

	/**
     * �d�b�ԍ� �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valTel(List<ValidationFailure> errors) {
    	// �S�p�����˔��p����
   		this.getInquiryHeaderForm().setTel(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getTel()));
    	// �d�b�ԍ�
    	ValidationChain valTel = new ValidationChain("inquiryHousing.input.valTel", this.getInquiryHeaderForm().getTel());
    	// �K�{�`�F�b�N
    	valTel.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
    	valTel.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
    	valTel.addValidation(new NumericValidation());
    	valTel.validate(errors);
    }

	/**
     * FAX�ԍ� �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valFax(List<ValidationFailure> errors) {
    	// �S�p�����˔��p����
    	((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setFax(PanaStringUtils.changeToHankakuNumber(((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax()));
    	// FAX�ԍ�
    	ValidationChain valFax = new ValidationChain("inquiryHousing.input.valFax", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getFax());
    	// �����`�F�b�N
    	valFax.addValidation(new MaxLengthValidation(11));
    	// ���p�����`�F�b�N
    	valFax.addValidation(new NumericValidation());
    	valFax.validate(errors);
    }

	/**
     * �X�֔ԍ� �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valZip(List<ValidationFailure> errors) {
    	// �S�p�����˔��p����
    	((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).setZip(PanaStringUtils.changeToHankakuNumber(((PanaInquiryHeaderForm) this.getInquiryHeaderForm()).getZip()));
		// �X�֔ԍ�
		String zip = ((PanaInquiryHeaderForm) this.getInquiryHeaderForm()).getZip();
		ValidationChain valZip = new ValidationChain("inquiryHousing.input.valZip", zip);
    	// �K�{�`�F�b�N
		valZip.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
		valZip.addValidation(new LengthValidator(7));
		// ���p�����`�F�b�N
		valZip.addValidation(new NumericValidation());
		valZip.validate(errors);
    }

	/**
     * �s���{���� �o���f�[�V����<br/>
     * �E�K�{�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valPrefName(List<ValidationFailure> errors) {
    	// �s���{����
    	ValidationChain valPrefName = new ValidationChain("inquiryHousing.input.valPrefName", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getPrefCd());
    	// �K�{���̓`�F�b�N
    	valPrefName.addValidation(new NullOrEmptyCheckValidation());
    	valPrefName.validate(errors);
    }

	/**
     * �s�撬���Ԓn �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valAddress(List<ValidationFailure> errors) {
    	// �s�撬���Ԓn
    	ValidationChain valAddress = new ValidationChain("inquiryHousing.input.valAddress", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getAddress());
    	// �K�{���̓`�F�b�N
    	valAddress.addValidation(new NullOrEmptyCheckValidation());
    	// �����`�F�b�N
    	valAddress.addValidation(new MaxLengthValidation(50));
    	valAddress.validate(errors);
    }

	/**
     * ������ �o���f�[�V����<br/>
     * �E���̓`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void valAddressOther(List<ValidationFailure> errors) {
    	// ������
    	ValidationChain valAddressOther = new ValidationChain("inquiryHousing.input.valAddressOther", ((PanaInquiryHeaderForm)this.getInquiryHeaderForm()).getAddressOther());
    	// �����`�F�b�N
    	valAddressOther.addValidation(new MaxLengthValidation(30));
    	valAddressOther.validate(errors);
    }

	/**
	* ����]�̘A�����@ �o���f�[�V����<br/>
	* �E���̓`�F�b�N <br/>
	*
	* @param errors
	*            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	*/
	protected void valContactType(List<ValidationFailure> errors) {
		// ����]�̘A�����@
		ValidationChain valContactType = new ValidationChain("inquiryHousing.input.valContactType", this.getContactType());
		// �p�^�[���`�F�b�N
		valContactType.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));
		valContactType.validate(errors);
	}

	/**
	* �A���\�Ȏ��ԑ� �o���f�[�V����<br/>
	* �E���̓`�F�b�N <br/>
	*
	* @param errors
	*            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	*/
	protected void valContactTime(List<ValidationFailure> errors) {

		String[] contactTimes = getContactTime();
		if (contactTimes != null) {
			for (int i = 0; i < contactTimes.length; i++) {
				String contactTime = contactTimes[i];
				// �A���\�Ȏ��ԑ�
				ValidationChain valContactTime = new ValidationChain("inquiryHousing.input.valContactTime", contactTime);
				// �p�^�[���`�F�b�N
				valContactTime.addValidation(new CodeLookupValidation(this.codeLookupManager,"inquiry_contact_time"));
				valContactTime.validate(errors);
			}
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

		String label = "inquiryHousing.input.ansCd";
		for (String ans : this.ansCd) {
			ValidationChain valid = new ValidationChain(label,ans);

			// �p�^�[�����̓`�F�b�N
			valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

			valid.validate(errors);
		}
	}

	/**
	* �A���P�[�g_���̑��� �o���f�[�V����<br/>
	* �E�����`�F�b�N
	*
	* @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	*/
	protected void validEtcAnswer1(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("008".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer1",this.getEtcAnswer1());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* �A���P�[�g_���̑��� �o���f�[�V����<br/>
	* �E�����`�F�b�N
	*
	* @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	*/
	protected void validEtcAnswer2(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("009".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer2",this.getEtcAnswer2());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* �A���P�[�g_���̑��� �o���f�[�V����<br/>
	* �E�����`�F�b�N
	*
	* @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	*/
	protected void validEtcAnswer3(List<ValidationFailure> errors) {

		if (this.ansCd == null || this.ansCd.length == 0) return;
		boolean checkFG = false;
		for (String ans : this.ansCd) {
			if ("010".equals(ans)) checkFG = true;
		}
		if (!checkFG) return;

		ValidationChain valid = new ValidationChain("inquiryHousing.input.etcAnswer3",this.getEtcAnswer3());
		// �����`�F�b�N
		valid.addValidation(new MaxLengthValidation(50));

		valid.validate(errors);
	}

	/**
	* �����⍇�����̃o���[�I�u�W�F�N�g���쐬����B<br/>
	* <br/>
	* @param inquiryHousing�@
	* @return �����⍇�����o���[�I�u�W�F�N�g�̔z��
	*/
    @Override
	public void copyToInquiryHousing(InquiryHousing inquiryHousing) {

    	super.copyToInquiryHousing(inquiryHousing);

		// �A�����@��ݒ�
		((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactType(this.contactType);

		// �A���\�Ȏ��ԑт�ݒ�
		StringBuffer contactTime = new StringBuffer();

		// ���⍇�����e�i���[���\���p�j��ݒ�
		this.setInquiryDtlTypeMail(this.codeLookupManager.lookupValue("inquiry_housing_dtl_type", this.getInquiryHeaderForm().getInquiryDtlType()[0]));

		if(this.contactTime != null){
			for (int i = 0; i < this.contactTime.length; i++) {
				if (!"".equals(this.contactTime[i])) {
					contactTime.append(this.contactTime[i]).append(",");
				}
			}
			// �A�C�R������ݒ�
			if (contactTime.length() > 0) {
				((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactTime(contactTime.toString().substring(0, contactTime.toString().length() - 1));
			}
		} else {
			((jp.co.transcosmos.dm3.corePana.vo.InquiryHousing)inquiryHousing).setContactTime(contactTime.toString());
		}

	}


	/**
	* �⍇���A���P�[�g�̃o���[�I�u�W�F�N�g���쐬����B<br/>
	* <br/>
	* @param inquiryHousingQuestion�@
	* @return �⍇���A���P�[�g�o���[�I�u�W�F�N�g�̔z��
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

	/**
	* �n���ꂽ�o���[�I�u�W�F�N�g���珉���l��ݒ肷��B<br/>
	* <br/>
	*/
	public void setDefaultDataHousing(Housing housing, PanaCommonParameters commonParameters, List<HousingImageInfo> housingImageInfoList, Map<String, Object> model, PanaFileUtil fileUtil) {

		// ������{�����擾
		HousingInfo housingInfo = (HousingInfo)housing.getHousingInfo().getItems().get("housingInfo");
		// ������{�����擾
		BuildingInfo buildingInfo = (BuildingInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
		// �����ڍ׏����擾
		BuildingDtlInfo buildingDtlInfo = (BuildingDtlInfo)housing.getBuilding().getBuildingInfo().getItems().get("buildingDtlInfo");
		// �s���{���}�X�^���擾
		PrefMst prefMst = (PrefMst)housing.getBuilding().getBuildingInfo().getItems().get("prefMst");

		// 1�T�Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 7���j�ɒǉ����ꂽ�����ɑ΂��ĐV���A�C�R�����o���B
		// �ŏI�X�V�����擾����B
		Date updDate = housingInfo.getUpdDate();
		// �V�X�e������
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		double nowTime = cal.getTimeInMillis();
		double updTime = 0;
		if (updDate != null) {
			cal.setTime(updDate);
			updTime = cal.getTimeInMillis();
		} else {
			cal.setTime(now);
			updTime = cal.getTimeInMillis();
		}
		// 1�T�Ԉȓ��i�V�X�e�����t - �ŏI�X�V��<= 7���j�v�Z
		double betweenDays = (nowTime - updTime) / (1000 * 3600 * 24);
		String dateFlg = "0";
		// �����ɑ΂��ĐV���A�C�R�����o���B
		if (betweenDays <= 7) {
			dateFlg = "1";
		}

		// �����摜��ݒ肷��B
		String pathName = "";
		if (housingImageInfoList.size() > 0) {
			jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo housingImageInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo) housingImageInfoList.get(0);
			if(housingImageInfo != null){
				if (!StringValidateUtil.isEmpty(housingImageInfo.getRoleId())) {
					if (PanaCommonConstant.ROLE_ID_PUBLIC.equals(housingImageInfo.getRoleId())) {
						// �{���������S���̏ꍇ
						pathName = fileUtil.getHousFileOpenUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), commonParameters.getHousingInquiryImageSize());
					} else if (PanaCommonConstant.ROLE_ID_PRIVATE.equals(housingImageInfo.getRoleId())) {
						// �{������������݂̂̏ꍇ
						pathName = fileUtil.getHousFileMemberUrl(housingImageInfo.getPathName(), housingImageInfo.getFileName(), commonParameters.getHousingInquiryImageSize());
					}
				}
			}
		}


		// ���ݒn��ݒ肷��B
		StringBuffer address = new StringBuffer();
		if (!StringValidateUtil.isEmpty(buildingInfo.getPrefCd())) {
			if (prefMst.getPrefName() != null) {
				address.append(prefMst.getPrefName()).append(" ");
				address.append(buildingInfo.getAddressName() == null?"":buildingInfo.getAddressName()).append(" ");
				address.append(buildingInfo.getAddressOther1() == null?"":buildingInfo.getAddressOther1()).append(" ");
				address.append(buildingInfo.getAddressOther2() == null?"":buildingInfo.getAddressOther2());
			}
		}


		// �A�N�Z�X��ݒ肷��B
		// �����Ŋ��w���List���擾
		List<JoinResult> buildingStationInfoList = housing.getBuilding().getBuildingStationInfoList();
		BuildingStationInfo buildingStationInfo = new BuildingStationInfo();
		RrMst rrMst = new RrMst();
		RouteMst routeMst = new RouteMst();
		StationMst stationMst = new StationMst();
		StringBuffer nearStation = new StringBuffer();
		List<String> nearStationList = new ArrayList<String>();
		for (int i = 0; i < buildingStationInfoList.size(); i++) {

			// �����Ŋ��w���̎擾
			buildingStationInfo = new BuildingStationInfo();
			buildingStationInfo =  (BuildingStationInfo)buildingStationInfoList.get(i).getItems().get("buildingStationInfo");
			// �S����Ѓ}�X�^�̎擾
			rrMst = new RrMst();
			rrMst = (RrMst)buildingStationInfoList.get(i).getItems().get("rrMst");
			// �H���}�X�g�̎擾
			routeMst = new RouteMst();
			routeMst = (RouteMst)buildingStationInfoList.get(i).getItems().get("routeMst");
			// �w�}�X�g�̎擾
			stationMst = new StationMst();
			stationMst = (StationMst)buildingStationInfoList.get(i).getItems().get("stationMst");

			// �A�N�Z�X
			nearStation = new StringBuffer();
			// �S����Ж�
			if (!StringValidateUtil.isEmpty(rrMst.getRrName())) {
				nearStation.append(rrMst.getRrName());
			}

			// ��\�H����
			if (!StringValidateUtil.isEmpty(routeMst.getRouteName())) {
				nearStation.append(routeMst.getRouteName()).append(" ");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getDefaultRouteName())) {
					nearStation.append(buildingStationInfo.getDefaultRouteName()).append(" ");
				}
			}
			// �w��
			if (!StringValidateUtil.isEmpty(stationMst.getStationName())) {
				nearStation.append(stationMst.getStationName()).append("�w").append(" ");
			} else {
				if (!StringValidateUtil.isEmpty(buildingStationInfo.getStationName())) {
					nearStation.append(buildingStationInfo.getStationName()).append("�w").append(" ");
				}
			}
			// �o�X��Ж�
			if (!StringValidateUtil.isEmpty(buildingStationInfo.getBusCompany())) {
				nearStation.append(buildingStationInfo.getBusCompany()).append(" ");
			}
			// �o�X�₩��̓k������
			if (buildingStationInfo.getTimeFromBusStop() != null) {
				nearStation.append("�k��").append(String.valueOf(buildingStationInfo.getTimeFromBusStop())).append("��");
			}
			nearStationList.add(nearStation.toString());
		}


		// �z�N��
		String compDate = "";
		if (buildingInfo != null && buildingInfo.getCompDate() != null) {
			compDate = new SimpleDateFormat("yyyy�NM���z").format(buildingInfo.getCompDate());
		}

		// �������CD�𔻒f
		String personalArea = "";
		String personalAreaSquare = "";
		String buildingArea = "";
		String buildingAreaSquare = "";
		String landArea = "";
		String landAreaSquare = "";
		// �K��
		String totalFloor = "";
		// ���݊K
		String floorNo = "";
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(buildingInfo.getHousingKindCd())) {
			if (housingInfo.getPersonalArea() != null) {
				// ��L�ʐ�
				personalArea = housingInfo.getPersonalArea().toString();
				// ��L�ʐ� ��
				personalAreaSquare = ((housingInfo.getPersonalArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getPersonalArea()).toString() + "�؁j") +
						(housingInfo.getPersonalAreaMemo() == null? "" : housingInfo.getPersonalAreaMemo()) ;
			}
			// �K���^���݊K
			if (buildingInfo != null) {
				// �K��
				totalFloor = (buildingInfo.getTotalFloors() == null) ? "" : buildingInfo.getTotalFloors() + "�K��";
				// ���݊K
				floorNo = (housingInfo.getFloorNo() == null) ? "" : housingInfo.getFloorNo() + "�K";
			}
		} else {
			if (buildingDtlInfo != null && buildingDtlInfo.getBuildingArea() != null) {
				// �����ʐ�
				buildingArea = buildingDtlInfo.getBuildingArea().toString();
				// �����ʐρ@��
				buildingAreaSquare = ((buildingDtlInfo.getBuildingArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(buildingDtlInfo.getBuildingArea()).toString() + "�؁j") +
						(buildingDtlInfo.getBuildingAreaMemo() == null? "" : buildingDtlInfo.getBuildingAreaMemo()) ;
			}
			if (housingInfo.getLandArea() != null) {
				// �y�n�ʐ�
				landArea = housingInfo.getLandArea().toString();
				// �y�n�ʐρ@��
				landAreaSquare = ((housingInfo.getLandArea() == null) ? "" : "�i��" + PanaCalcUtil.calcTsubo(housingInfo.getLandArea()).toString() + "�؁j") +
						(housingInfo.getLandAreaMemo() == null? "" : housingInfo.getLandAreaMemo()) ;
			}
		}

		// �s���{��back
		this.backPrefCd = buildingInfo.getPrefCd();

		// 1�T�Ԉȓ��v�Z���
		model.put("dateFlg", dateFlg);
		// ������{���
		model.put("buildingInfo", buildingInfo);
		// ������{���
		model.put("housingInfo", housingInfo);
		// �����摜���
		model.put("pathName", pathName);
		// ���ݒn���
		model.put("address", address);
		// �A�N�Z�X���
		model.put("nearStationList", nearStationList);
		// �z�N��
		model.put("compDate", compDate);
		// ��L�ʐ�
		model.put("personalArea", personalArea);
		// ��L�ʐ� ��
		model.put("personalAreaSquare", personalAreaSquare);
		// �����ʐ�
		model.put("buildingArea", buildingArea);
		// �����ʐ� ��
		model.put("buildingAreaSquare", buildingAreaSquare);
		// �y�n�ʐ�
		model.put("landArea", landArea);
		// �y�n�ʐ� ��
		model.put("landAreaSquare", landAreaSquare);
		// �K���A���݊K
		model.put("totalFloor", totalFloor);
		model.put("floorNo", floorNo);
	}

	/**
	* �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	* <br/>
	*/
	public void setDefaultDataUserInfo(JoinResult userInfo, InquiryHeaderForm inquiryHeaderForm) {

		// �}�C�y�[�W��������擾
		if(userInfo != null){
			MemberInfo memberInfo = (MemberInfo)userInfo.getItems().get("memberInfo");
			PrefMst prefMst_member = (PrefMst)userInfo.getItems().get("prefMst");
			if(memberInfo != null){
				// �����O_��
				inquiryHeaderForm.setLname(memberInfo.getMemberLname());
				// �����O_��
				inquiryHeaderForm.setFname(memberInfo.getMemberFname());
				// �����O�i�t���K�i�j_��
				inquiryHeaderForm.setLnameKana(memberInfo.getMemberLnameKana());
				// �����O�i�t���K�i�j_��
				inquiryHeaderForm.setFnameKana(memberInfo.getMemberFnameKana());
				// ���[���A�h���X
				inquiryHeaderForm.setEmail(memberInfo.getEmail());
				// �d�b�ԍ�
				inquiryHeaderForm.setTel(memberInfo.getTel());
				// FAX�ԍ�
				((PanaInquiryHeaderForm)inquiryHeaderForm).setFax(memberInfo.getFax());
				// �X�֔ԍ�
				((PanaInquiryHeaderForm)inquiryHeaderForm).setZip(memberInfo.getZip());
				// �s���{��key
				((PanaInquiryHeaderForm)inquiryHeaderForm).setPrefCd(memberInfo.getPrefCd());
				// �s���{����
				if(prefMst_member.getPrefName() != null){
					this.prefName = prefMst_member.getPrefName();
				}
				// �s�撬���Ԓn
				((PanaInquiryHeaderForm)inquiryHeaderForm).setAddress(memberInfo.getAddress());
				// ������
				((PanaInquiryHeaderForm)inquiryHeaderForm).setAddressOther(memberInfo.getAddressOther());
			}
		}
		this.setCommonInquiryForm(inquiryHeaderForm);
	}
}
