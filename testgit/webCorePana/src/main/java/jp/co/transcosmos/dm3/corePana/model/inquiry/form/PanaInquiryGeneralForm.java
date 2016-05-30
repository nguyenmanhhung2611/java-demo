package jp.co.transcosmos.dm3.corePana.model.inquiry.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.inquiry.form.HousingInquiryForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.EmailRFCValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

/**
 * �����̂��₢���킹���̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * qiao.meng	2015.04.28   �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaInquiryGeneralForm extends HousingInquiryForm {

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	private LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/** ���⍇��ID */
	private String inquiryId;

	/** �Z�~�i�[�E�C�x���g�� */
	private String eventName;

	/** ���� */
	private String eventDatetime;
	/** �����iMM��DD���@hh�Fmm�j */
	private String eventDatetimeWithFormat;

	/** �����i���j */
	private String eventDatetimeMonth;

	/** �����i���j */
	private String eventDatetimeDay;

	/** �����i���j */
	private String eventDatetimeHour;

	/** �����i���j */
	private String eventDatetimeMinute;

	/** ����]�̘A�����@ */
	private String inquiryContactType;

	/** �A���\�Ȏ��ԑ� */
	private String[] inquiryContactTime;

	/** �A���P�[�g */
	private String[] ansCd;

	/** �A���P�[�g���e1 */
	private String etcAnswer1;

	/** �A���P�[�g���e2 */
	private String etcAnswer2;

	/** �A���P�[�g���e3 */
	private String etcAnswer3;


	/** ���⍇�����e�ڍׁi��ʕ\���p�j */
	private String inquiryText1;

	/** ���⍇�����e�i���[���\���p�j */
	private String inquiryDtlTypeMail;

	/** ����]�̘A�����@�i���[���\���p�j */
	private String inquiryContactTypeMail;

	/** �A���\�Ȏ��ԑсi���[���\���p�j */
	private String inquiryContactTimeMail;

	/** Url�p�^�[�� */
	private String urlPattern;

	/** command �p�����[�^ */
	private String command;
	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	PanaInquiryGeneralForm(){
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	PanaInquiryGeneralForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * @return inquiryId
	 */
	public String getInquiryId() {
		return inquiryId;
	}

	/**
	 * @param inquiryId �Z�b�g���� inquiryId
	 */
	public void setInquiryId(String inquiryId) {
		this.inquiryId = inquiryId;
	}

	/**
	 * @return eventName
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param eventName �Z�b�g���� eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * @return eventDatetime
	 */
	public String getEventDatetime() {
		return eventDatetime;
	}


	/**
	 * @param eventDatetime �Z�b�g���� eventDatetime
	 */
	public void setEventDatetime(String eventDatetime) {
		this.eventDatetime = eventDatetime;
	}

	/**
	 * @return eventDatetimeWithFormat
	 */
	public String getEventDatetimeWithFormat() {
		return eventDatetimeWithFormat;
	}

	/**
	 * @param eventDatetimeWithFormat �Z�b�g���� eventDatetimeWithFormat
	 */
	public void setEventDatetimeWithFormat(String eventDatetimeWithFormat) {
		this.eventDatetimeWithFormat = eventDatetimeWithFormat;
	}

	/**
	 * @return eventDatetimeMonth
	 */
	public String getEventDatetimeMonth() {
		return eventDatetimeMonth;
	}

	/**
	 * @param eventDatetimeMonth �Z�b�g���� eventDatetimeMonth
	 */
	public void setEventDatetimeMonth(String eventDatetimeMonth) {
		this.eventDatetimeMonth = eventDatetimeMonth;
	}

	/**
	 * @return eventDatetimeDay
	 */
	public String getEventDatetimeDay() {
		return eventDatetimeDay;
	}

	/**
	 * @param eventDatetimeDay �Z�b�g���� eventDatetimeDay
	 */
	public void setEventDatetimeDay(String eventDatetimeDay) {
		this.eventDatetimeDay = eventDatetimeDay;
	}

	/**
	 * @return eventDatetimeHour
	 */
	public String getEventDatetimeHour() {
		return eventDatetimeHour;
	}

	/**
	 * @param eventDatetimeHour �Z�b�g���� eventDatetimeHour
	 */
	public void setEventDatetimeHour(String eventDatetimeHour) {
		this.eventDatetimeHour = eventDatetimeHour;
	}

	/**
	 * @return eventDatetimeMinute
	 */
	public String getEventDatetimeMinute() {
		return eventDatetimeMinute;
	}

	/**
	 * @param eventDatetimeMinute �Z�b�g���� eventDatetimeMinute
	 */
	public void setEventDatetimeMinute(String eventDatetimeMinute) {
		this.eventDatetimeMinute = eventDatetimeMinute;
	}

	/**
	 * @return inquiryContactType
	 */
	public String getInquiryContactType() {
		return inquiryContactType;
	}

	/**
	 * @param inquiryContactType �Z�b�g���� inquiryContactType
	 */
	public void setInquiryContactType(String inquiryContactType) {
		this.inquiryContactType = inquiryContactType;
	}


	/**
	 * @return inquiryContactTime
	 */
	public String[] getInquiryContactTime() {
		return inquiryContactTime;
	}

	/**
	 * @param inquiryContactTime �Z�b�g���� inquiryContactTime
	 */
	public void setInquiryContactTime(String[] inquiryContactTime) {
		this.inquiryContactTime = inquiryContactTime;
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
	 * @return inquiryText1
	 */
	public String getInquiryText1() {
		return inquiryText1;
	}

	/**
	 * @param inquiryText1 �Z�b�g���� inquiryText1
	 */
	public void setInquiryText1(String inquiryText1) {
		this.inquiryText1 = inquiryText1;
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
	 * @return inquiryContactTypeMail
	 */
	public String getInquiryContactTypeMail() {
		return inquiryContactTypeMail;
	}

	/**
	 * @param inquiryContactTypeMail �Z�b�g���� inquiryContactTypeMail
	 */
	public void setInquiryContactTypeMail(String inquiryContactTypeMail) {
		this.inquiryContactTypeMail = inquiryContactTypeMail;
	}

	/**
	 * @return inquiryContactTimeMail
	 */
	public String getInquiryContactTimeMail() {
		return inquiryContactTimeMail;
	}

	/**
	 * @param inquiryContactTimeMail �Z�b�g���� inquiryContactTimeMail
	 */
	public void setInquiryContactTimeMail(String inquiryContactTimeMail) {
		this.inquiryContactTimeMail = inquiryContactTimeMail;
	}

	/**
	 * @return urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern �Z�b�g���� urlPattern
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

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
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�̃o���f�[�V�������s��<br/>
	 * <br/>
	 *
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 * @throws ParseException
	 */
	public boolean validate(List<ValidationFailure> errors) throws ParseException {
		int startSize = errors.size();

		// ���₢���킹���e�̃o���f�[�V��������
		validInquiryDtlType(errors);

		// �Z�~�i�[�E�C�x���g���̃o���f�[�V��������
		validEventName(errors);

		// �����̃o���f�[�V��������
		validEventDateTime(errors);

		// ���₢���킹���e�ڍׂ̃o���f�[�V��������
		validInquiryText(errors);

		// �����O_���̃o���f�[�V��������
		validLname(errors);

		// �����O_���̃o���f�[�V��������
		validFname(errors);

		// �����O�i�t���K�i�j_���̃o���f�[�V��������
		validLnameKana(errors);

		// �����O�i�t���K�i�j_���̃o���f�[�V��������
		validFnameKana(errors);

		// ���[���A�h���X�̃o���f�[�V��������
		validEmail(errors);

		// �d�b�ԍ��̃o���f�[�V��������
		validTel(errors);

		// ����]�̘A�����@�̃o���f�[�V��������
		validInquiryContactType(errors);

		// �A���\�Ȏ��ԑт̃o���f�[�V��������
		validInquiryContactTime(errors);

		// �A���P�[�g�񓚂̃o���f�[�V��������
		validAnsCd(errors);

		// �A���P�[�g_���̑��񓚂̃o���f�[�V��������
		validEtcAnswer(errors);

		return (startSize == errors.size());
	}

	/**
	 * �A���P�[�g�� �o���f�[�V����<br/>
	 * �E�p�^�[�����̓`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAnsCd(List<ValidationFailure> errors) {

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				ValidationChain valid = new ValidationChain("inquiryGeneral.input.ansCd", ansCd);

				// �p�^�[�����̓`�F�b�N
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));

				valid.validate(errors);
			}
		}
	}

	/**
	 * �A���P�[�g_���̑��� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validEtcAnswer(List<ValidationFailure> errors) {

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				if ("008".equals(ansCd)) {
					/** �A���P�[�g1���e1 */
			        ValidationChain etcAnswer1 = new ValidationChain("inquiryGeneral.input.etcAnswer1", this.getEtcAnswer1());
			        // �����`�F�b�N
			        etcAnswer1.addValidation(new MaxLengthValidation(50));
			        etcAnswer1.validate(errors);
				}

				if ("009".equals(ansCd)) {
					/** �A���P�[�g1���e2 */
			        ValidationChain etcAnswer2 = new ValidationChain("inquiryGeneral.input.etcAnswer2", this.getEtcAnswer2());
			        // �����`�F�b�N
			        etcAnswer2.addValidation(new MaxLengthValidation(50));
			        etcAnswer2.validate(errors);
				}

				if ("010".equals(ansCd)) {
					/** �A���P�[�g1���e3 */
			        ValidationChain etcAnswer3 = new ValidationChain("inquiryGeneral.input.etcAnswer3", this.getEtcAnswer3());
			        // �����`�F�b�N
			        etcAnswer3.addValidation(new MaxLengthValidation(50));
			        etcAnswer3.validate(errors);
				}
			}
		}

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
	 * �A���\�Ȏ��ԑ� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validInquiryContactTime(List<ValidationFailure> errors) {

		if (this.getInquiryContactTime() == null) return;

		for (int i = 0; i < this.getInquiryContactTime().length; i++) {
	        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryContactTime", this.getInquiryContactTime()[i]);
	        // �p�^�[�����̓`�F�b�N
	        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_time"));
	        valid.validate(errors);
		}
	}

	/**
	 * ����]�̘A�����@ �o���f�[�V����<br/>
	 * <ul>
	 * <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validInquiryContactType(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryContactType", this.getInquiryContactType());
        // �p�^�[�����̓`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_contact_type"));
        valid.validate(errors);
	}

	/**
	 * �d�b�ԍ� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * <li>���p�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validTel(List<ValidationFailure> errors) {
		// �S�p�����˔��p����
		this.getInquiryHeaderForm().setTel(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getTel()));
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.tel", this.getInquiryHeaderForm().getTel());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
        valid.addValidation(new NumericValidation());

        valid.validate(errors);
	}

	/**
	 * ���[���A�h���X �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * <li>���[���A�h���X�̏����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validEmail(List<ValidationFailure> errors) {
		// �S�p�����˔��p����
		this.getInquiryHeaderForm().setEmail(PanaStringUtils.changeToHankakuNumber(this.getInquiryHeaderForm().getEmail()));
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.email", this.getInquiryHeaderForm().getEmail());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(255));
        // ���[���A�h���X�̏����`�F�b�N
        valid.addValidation(new EmailRFCValidation());

        valid.validate(errors);
	}

	/**
	 * �����O_�� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validLname(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.lname", this.getInquiryHeaderForm().getLname());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(30));
		// �S�p�`�F�b�N
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
	}

	/**
	 * �����O_�� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validFname(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.fname", this.getInquiryHeaderForm().getFname());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(30));
		// �S�p�`�F�b�N
        valid.addValidation(new ZenkakuOnlyValidation());

        valid.validate(errors);
	}

	/**
	 * �����O�i�t���K�i�j_�� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�J�^�J�i�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validLnameKana(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.lnameKana", this.getInquiryHeaderForm().getLnameKana());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(30));
		// �S�p�J�^�J�i�`�F�b�N
        valid.addValidation(new ZenkakuKanaValidator());

        valid.validate(errors);
	}

	/**
	 * �����O�i�t���K�i�j_�� �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�S�p�J�^�J�i�`�F�b�N</li>
	 * <li>�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	private void validFnameKana(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.fnameKana", this.getInquiryHeaderForm().getFnameKana());

        // �K�{�`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(30));
		// �S�p�J�^�J�i�`�F�b�N
        valid.addValidation(new ZenkakuKanaValidator());

        valid.validate(errors);
	}

	/**
     * ���₢���킹���e�ڍ� �o���f�[�V����<br/>
     * �E�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
	private void validInquiryText(List<ValidationFailure> errors) {
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryText", this.getInquiryHeaderForm().getInquiryText());
        // �����`�F�b�N
        valid.addValidation(new MaxLengthValidation(1000));
        valid.validate(errors);

	}

	/**
     * ���� �o���f�[�V����<br/>
     * �E�u���₢���킹���e�v�Łu�Z�~�i�[�E�C�x���g�Ɋւ��āv��I�������ꍇ�̂ݕK�{�`�F�b�N
     * �E�����`�F�b�N
     * �E���p�����`�F�b�N
     * �E�����̗L���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
	private void validEventDateTime(List<ValidationFailure> errors) throws ParseException {
		int size = errors.size();

		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}

        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inquiryDtlType)){
        	/** �����i���j�̃o���f�[�V���� */
            ValidationChain eventDateTimeMonth = new ValidationChain("inquiryGeneral.input.eventDateTimeMonth", this.getEventDatetimeMonth());
            // �K�{���̓`�F�b�N
            eventDateTimeMonth.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            eventDateTimeMonth.addValidation(new MaxLengthValidation(2));
            // ���p�����`�F�b�N
            eventDateTimeMonth.addValidation(new NumericValidation());
            eventDateTimeMonth.validate(errors);

        	/** �����i���j�̃o���f�[�V���� */
            ValidationChain eventDateTimeDay = new ValidationChain("inquiryGeneral.input.eventDateTimeDay", this.getEventDatetimeDay());
            // �K�{���̓`�F�b�N
            eventDateTimeDay.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            eventDateTimeDay.addValidation(new MaxLengthValidation(2));
            // ���p�����`�F�b�N
            eventDateTimeDay.addValidation(new NumericValidation());
            eventDateTimeDay.validate(errors);

        	/** �����i���j�̃o���f�[�V���� */
            ValidationChain eventDateTimeHour = new ValidationChain("inquiryGeneral.input.eventDateTimeHour", this.getEventDatetimeHour());
            // �K�{���̓`�F�b�N
            eventDateTimeHour.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            eventDateTimeHour.addValidation(new MaxLengthValidation(2));
            // ���p�����`�F�b�N
            eventDateTimeHour.addValidation(new NumericValidation());
            eventDateTimeHour.validate(errors);

        	/** �����i���j�̃o���f�[�V���� */
            ValidationChain eventDateTimeMinute = new ValidationChain("inquiryGeneral.input.eventDateTimeMinute", this.getEventDatetimeMinute());
            // �K�{���̓`�F�b�N
            eventDateTimeMinute.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            eventDateTimeMinute.addValidation(new MaxLengthValidation(2));
            // ���p�����`�F�b�N
            eventDateTimeMinute.addValidation(new NumericValidation());
            eventDateTimeMinute.validate(errors);

        	/** �����̗L���`�F�b�N */
            // ��L�����o���f�[�V�����ɃG���[���Ȃ��ꍇ�A�`�F�b�N����
            if (size == errors.size())
            {
        		DateFormat format =  new SimpleDateFormat("yyyy");
        		String year = format.format(new Date());

				String datetime = year
						+ "-"
						+ (this.getEventDatetimeMonth().length() == 1 ? ("0" + this
								.getEventDatetimeMonth()) : this
								.getEventDatetimeMonth())
						+ "-"
						+ (this.getEventDatetimeDay().length() == 1 ? ("0" + this
								.getEventDatetimeDay()) : this
								.getEventDatetimeDay())
						+ " "
						+ (this.getEventDatetimeHour().length() == 1 ? ("0" + this
								.getEventDatetimeHour()) : this
								.getEventDatetimeHour())
						+ ":"
						+ (this.getEventDatetimeMinute().length() == 1 ? ("0" + this
								.getEventDatetimeMinute()) : this
								.getEventDatetimeMinute()) + ":00";

        		this.setEventDatetime(datetime);
        		try {
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        			if (!sdf.format(sdf.parse(this.getEventDatetime())).equals(
        					this.getEventDatetime())) {
            			errors.add(new ValidationFailure("validDateTime", "����",  "YYYY/MM/DD hh:mm", null));
        			}
        		} catch (ParseException localParseException) {
        			errors.add(new ValidationFailure("validDateTime", "����", "YYYY/MM/DD hh:mm", null ));
        		}

            }
        }

	}

	/**
     * �Z�~�i�[�E�C�x���g�� �o���f�[�V����<br/>
     * �E�u���₢���킹���e�v�Łu�Z�~�i�[�E�C�x���g�Ɋւ��āv��I�������ꍇ�̂ݕK�{�`�F�b�N
     * �E�����`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
	private void validEventName(List<ValidationFailure> errors) {
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}
        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(inquiryDtlType)){
            ValidationChain valid = new ValidationChain("inquiryGeneral.input.eventName", this.getEventName());
            // �K�{���̓`�F�b�N
            valid.addValidation(new NullOrEmptyCheckValidation());
            // �����`�F�b�N
            valid.addValidation(new MaxLengthValidation(50));
            valid.validate(errors);
        }

	}

	/**
     * ���₢���킹��� �o���f�[�V����<br/>
     * �E�K�{�`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
	private void validInquiryDtlType(List<ValidationFailure> errors) {
		String inquiryDtlType = null;
		if (this.getInquiryHeaderForm().getInquiryDtlType() != null && this.getInquiryHeaderForm().getInquiryDtlType().length > 0) {
			inquiryDtlType = this.getInquiryHeaderForm().getInquiryDtlType()[0];
		}
        ValidationChain valid = new ValidationChain("inquiryGeneral.input.inquiryDtlType", inquiryDtlType);
        // �K�{���̓`�F�b�N
        valid.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[�����̓`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "inquiry_dtl_type"));
        valid.validate(errors);

	}



	/**
	 * �ėp�⍇�����̃o���[�I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param inquiryGeneral
	 * @param InquiryId
	 * @return �ėp�⍇�����o���[�I�u�W�F�N�g�̔z��
	 */
	public void copyToInquiryGeneral(InquiryGeneral[] inquiryGeneral, String InquiryId) throws ParseException {

		// ���⍇��ID��ݒ�
		inquiryGeneral[0].setInquiryId(InquiryId);

        if (PanaCommonConstant.INQUIRY_DTL_TYPE_SEMINAR.equals(this.getInquiryHeaderForm().getInquiryDtlType()[0])){
			// �C�x���g�E�Z�~�i�[��
			inquiryGeneral[0].setEventName(this.eventName);

			// �C�x���g�E�Z�~�i�[����
			DateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			inquiryGeneral[0].setEventDatetime(format.parse(this.eventDatetime));
        }

		// �A�����@��ݒ�
		inquiryGeneral[0].setContactType(this.inquiryContactType);
		this.setInquiryContactTypeMail(this.codeLookupManager.lookupValue("inquiry_contact_type", this.inquiryContactType));
		this.setInquiryDtlTypeMail(this.codeLookupManager.lookupValue("inquiry_dtl_type", this.getInquiryHeaderForm().getInquiryDtlType()[0]));

		// �A���\�Ȏ��ԑт�ݒ�
		StringBuffer contactTime = new StringBuffer();
		StringBuffer contactTimeMail = new StringBuffer();

		if(this.inquiryContactTime != null){
			for (int i = 0; i < this.inquiryContactTime.length; i++) {
				if (!"".equals(this.inquiryContactTime[i])) {
					contactTime.append(this.inquiryContactTime[i]).append(",");
					contactTimeMail.append(this.codeLookupManager.lookupValue("inquiry_contact_time", this.inquiryContactTime[i])).append("  ");
				}
			}
			// �A�C�R������ݒ�
			if (contactTime.length() > 0) {
				inquiryGeneral[0].setContactTime(contactTime.toString().substring(0, contactTime.toString().length() - 1));
				this.setInquiryContactTimeMail(contactTimeMail.toString());
			}
		} else {
			inquiryGeneral[0].setContactTime(contactTime.toString());
		}
	}

	/**
	 * �⍇���A���P�[�g�̃o���[�I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param inquiryHousingQuestion�@
	 * @return �⍇���A���P�[�g�o���[�I�u�W�F�N�g�̔z��
	 */
	public void copyToInquiryHousingQuestion(InquiryHousingQuestion[] inquiryHousingQuestion, String inquiryId) {

		int i = 0;

		if (this.getAnsCd() != null) {
			for (String ansCd : this.getAnsCd()) {
				InquiryHousingQuestion question = new InquiryHousingQuestion();
				// ���⍇��ID��ݒ�
				question.setInquiryId(inquiryId);
				// �A���P�[�g�ԍ�
				question.setCategoryNo(PanaCommonConstant.COMMON_CATEGORY_NO);
				// ��CD
				question.setAnsCd(ansCd);
				// ���̑�����
				if ("008".equals(ansCd)) {
					question.setNote(this.getEtcAnswer1());
				}

				if ("009".equals(ansCd)) {
					question.setNote(this.getEtcAnswer2());
				}

				if ("010".equals(ansCd)) {
					question.setNote(this.getEtcAnswer3());
				}

				inquiryHousingQuestion[i] = question;
				i++;
			}
		}

	}

}
