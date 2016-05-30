package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingDtlInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �����ڍ׏�񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 */
public class HousingDtlForm implements Validateable {

	private static final Log log = LogFactory.getLog(HousingDtlForm.class);
	
	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �p�r�n��CD */
	private String usedAreaCd;
	/** ����`�ԋ敪 */
	private String transactTypeDiv;
	/** �\���p�_����� */
	private String displayContractTerm;
	/** �y�n���� */
	private String landRight;
	/** �����\�����t���O */
	private String moveinTiming;
	/** �����\���� */
	private String moveinTimingDay;
	/** �����\�����R�����g */
	private String moveinNote;
	/** �\���p�����\���� */
	private String displayMoveinTiming;
	/** �\���p���������� */
	private String displayMoveinProviso;
	/** �Ǘ��`�ԁE���� */
	private String upkeepType;
	/** �Ǘ���� */
	private String upkeepCorp;
	/** �X�V�� */
	private String renewChrg;
	/** �X�V���P�� */
	private String renewChrgCrs;
	/** �X�V���� */
	private String renewChrgName;
	/** �X�V�萔�� */
	private String renewDue;
	/** �X�V�萔���P�� */
	private String renewDueCrs;
	/** ����萔�� */
	private String brokerageChrg;
	/** ����萔���P�� */
	private String brokerageChrgCrs;
	/** �������� */
	private String changeKeyChrg;
	/** ���ۗL�� */
	private String insurExist;
	/** ���ۗ��� */
	private String insurChrg;
	/** ���۔N�� */
	private String insurTerm;
	/** ���۔F�胉���N */
	private String insurLank;
	/** ����p */
	private String otherChrg;
	/** �ړ��� */
	private String contactRoad;
	/** �ړ�����/���� */
	private String contactRoadDir;
	/** �������S */
	private String privateRoad;
	/** �o���R�j�[�ʐ� */
	private String balconyArea;
	/** ���L���� */
	private String specialInstruction;
	/** �ڍ׃R�����g */
	private String dtlComment;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;



	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingDtlForm() {
		
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
	 */
	protected HousingDtlForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	
	
	/**
	 * �����œn���ꂽ�����ڍ׏��̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param housingDtlInfo �����ڍ׏��
	 * @throws ParseException 
	 */
	public void copyToHousingDtlInfo(HousingDtlInfo housingDtlInfo) {

		// ���t�^�̓��͏����́A"yyyy/MM/dd"
		
		housingDtlInfo.setSysHousingCd(this.sysHousingCd);						// �V�X�e������CD
		housingDtlInfo.setUsedAreaCd(this.usedAreaCd);							// �p�r�n��CD
		housingDtlInfo.setTransactTypeDiv(this.transactTypeDiv);				// ����`�ԋ敪
		housingDtlInfo.setDisplayContractTerm(this.displayContractTerm);		// �\���p�_�����
		housingDtlInfo.setLandRight(this.landRight);							// �y�n����
		housingDtlInfo.setMoveinTiming(this.moveinTiming);						// �����\�����t���O

		try {																	// �����\����
			Date wkMoveinTimingDay = StringUtils.stringToDate(this.moveinTimingDay, "yyyy/MM/dd");
			housingDtlInfo.setMoveinTimingDay(wkMoveinTimingDay);
		} catch (ParseException e) {
			// �o���f�[�V�����ň��S����S�ۂ��Ă���̂ŁA���̃^�C�~���O�ŃG���[���������鎖�͖������A
			// �ꉞ�A�x�������O�o�͂���B
			log.warn("moveinTimingDay format error. (" + this.moveinTimingDay + ")");
			housingDtlInfo.setMoveinTimingDay(null);
		}

		housingDtlInfo.setMoveinNote(this.moveinNote);							// �����\�����R�����g
		housingDtlInfo.setDisplayMoveinTiming(this.displayMoveinTiming);		// �\���p�����\����
		housingDtlInfo.setDisplayMoveinProviso(this.displayMoveinProviso);		// �\���p����������
		housingDtlInfo.setUpkeepType(this.upkeepType);							// �Ǘ��`�ԁE����
		housingDtlInfo.setUpkeepCorp(this.upkeepCorp);							// �Ǘ����
		
		if (!StringValidateUtil.isEmpty(this.renewChrg)) {						// �X�V��
			housingDtlInfo.setRenewChrg(new BigDecimal(this.renewChrg));
		} else {
			housingDtlInfo.setRenewChrg(null);
		}

		housingDtlInfo.setRenewChrgCrs(this.renewChrgCrs);						// �X�V���P��
		housingDtlInfo.setRenewChrgName(this.renewChrgName);					// �X�V����

		if (!StringValidateUtil.isEmpty(this.renewDue)) {						// �X�V�萔��
			housingDtlInfo.setRenewDue(new BigDecimal(this.renewDue));
		} else {
			housingDtlInfo.setRenewDue(null);
		}

		housingDtlInfo.setRenewDueCrs(this.renewDueCrs);						// �X�V�萔���P��

		if (!StringValidateUtil.isEmpty(this.brokerageChrg)) {					// ����萔��
			housingDtlInfo.setBrokerageChrg(new BigDecimal(this.brokerageChrg));
		} else {
			housingDtlInfo.setBrokerageChrg(null);
		}

		housingDtlInfo.setBrokerageChrgCrs(this.brokerageChrgCrs);				// ����萔���P��

		if (!StringValidateUtil.isEmpty(this.changeKeyChrg)) {					// ��������
			housingDtlInfo.setChangeKeyChrg(new BigDecimal(this.changeKeyChrg));
		} else {
			housingDtlInfo.setChangeKeyChrg(null);
		}

		housingDtlInfo.setInsurExist(this.insurExist);							// ���ۗL��

		if (!StringValidateUtil.isEmpty(this.insurChrg)) {						// ���ۗ���
			housingDtlInfo.setInsurChrg(Long.valueOf(this.insurChrg));
		} else {
			housingDtlInfo.setInsurChrg(null);
		}

		if (!StringValidateUtil.isEmpty(this.insurTerm)) {						// ���۔N��
			housingDtlInfo.setInsurTerm(Integer.valueOf(this.insurTerm));
		} else {
			housingDtlInfo.setInsurTerm(null);
		}

		housingDtlInfo.setInsurLank(this.insurLank);							// ���۔F�胉���N

		if (!StringValidateUtil.isEmpty(this.otherChrg)){						// ����p
			housingDtlInfo.setOtherChrg(Long.valueOf(this.otherChrg));
		} else {
			housingDtlInfo.setOtherChrg(null);
		}

		housingDtlInfo.setContactRoad(this.contactRoad);						// �ړ���
		housingDtlInfo.setContactRoadDir(this.contactRoadDir);					// �ړ�����/����
		housingDtlInfo.setPrivateRoad(this.privateRoad);						// �������S

		if (!StringValidateUtil.isEmpty(this.balconyArea)){						// �o���R�j�[�ʐ�
			housingDtlInfo.setBalconyArea(new BigDecimal(this.balconyArea));
		} else {
			housingDtlInfo.setBalconyArea(null);
		}

		housingDtlInfo.setSpecialInstruction(this.specialInstruction);			// ���L����
		housingDtlInfo.setDtlComment(this.dtlComment);							// �ڍ׃R�����g

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

		// �V�X�e������CD �̃o���f�[�V�����́ACommand ���őΉ�����̂� From ���ł͎������Ȃ��B
		// �i�X�V���Ƀp�����[�^���������Ă���ꍇ��A�Y���f�[�^�������ꍇ�͗�O���X���[����B�j

		// �p�r�n��CD
		validUsedAreaCd(errors);
		// ����`�ԋ敪
		validTransactTypeDiv(errors);
		// �\���p�_�����
		validDisplayContractTerm(errors);
		// �y�n����
		validLandRight(errors);
		// �����\�����t���O
		validMoveinTiming(errors);
		// �����\����
		validMoveinTimingDay(errors);
		// �����\�����R�����g
		validMoveinNote(errors);
		// �\���p�����\����
		validDisplayMoveinTiming(errors);
		// �\���p����������
		validDisplayMoveinProviso(errors);
		// �Ǘ��`�ԁE����
		validUpkeepType(errors);
		// �Ǘ����
		validUpkeepCorp(errors);
		// �X�V��
		validRenewChrg(errors);
		// �X�V���P��
		validRenewChrgCrs(errors);
		// �X�V����
		validRenewChrgName(errors);
		// �X�V�萔��
		validRenewDue(errors);
		// �X�V�萔���P��
		validRenewDueCrs(errors);
		// ����萔��
		validBrokerageChrg(errors);
		// ����萔���P��
		validBrokerageChrgCrs(errors);
		// ��������
		validChangeKeyChrg(errors);
		// ���ۗL��
		validInsurExist(errors);
		// ���ۗ���
		validInsurChrg(errors);
		// ���۔N��
		validInsurTerm(errors);
		// ���۔F�胉���N
		validInsurLank(errors);
		// ����p
		validOtherChrg(errors);
		// �ړ���
		validContactRoad(errors);
		// �ړ�����/����
		validContactRoadDir(errors);
		// �������S
		validPrivateRoad(errors);
		// �o���R�j�[�ʐ�
		validBalconyArea(errors);
		// ���L����
		validSpecialInstruction(errors);
		// �ڍ׃R�����g
		validDtlComment(errors);

		return (startSize == errors.size());
	}
	
	/**
	 * �p�r�n��CD �̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validUsedAreaCd(List<ValidationFailure> errors){
		String label = "housingDtl.input.usedAreaCd";
		ValidationChain valid = new ValidationChain(label, this.usedAreaCd);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "usedArea"));

		valid.validate(errors);
	}
	
	/**
	 * ����`�ԋ敪 �̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validTransactTypeDiv(List<ValidationFailure> errors){
		String label = "housingDtl.input.transactTypeDiv";
		ValidationChain valid = new ValidationChain(label, this.transactTypeDiv);
		
		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "transactTypeDiv"));

		valid.validate(errors);
	}

	/**
	 * �\���p�_����� �̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayContractTerm(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayContractTerm";
		ValidationChain valid = new ValidationChain(label, this.displayContractTerm);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * �y�n�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validLandRight(List<ValidationFailure> errors){
		String label = "housingDtl.input.landRight";
		ValidationChain valid = new ValidationChain(label, this.landRight);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * �����\�����t���O�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validMoveinTiming(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinTiming";
		ValidationChain valid = new ValidationChain(label, this.moveinTiming);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "moveinTiming"));

		valid.validate(errors);
	}

	/**
	 * �����\�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>���t�`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validMoveinTimingDay(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinTimingDay";
		ValidationChain valid = new ValidationChain(label, this.moveinTimingDay);

		// ���t�`�F�b�N
		valid.addValidation(new ValidDateValidation("yyyy/MM/dd"));

		valid.validate(errors);
	}

	/**
	 * �����\�����R�����g�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validMoveinNote(List<ValidationFailure> errors){
		String label = "housingDtl.input.moveinNote";
		ValidationChain valid = new ValidationChain(label, this.moveinNote);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * �\���p�����\�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayMoveinTiming(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayMoveinTiming";
		ValidationChain valid = new ValidationChain(label, this.displayMoveinTiming);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 18)));

		valid.validate(errors);
	}

	/**
	 * �\���p�����������̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayMoveinProviso(List<ValidationFailure> errors){
		String label = "housingDtl.input.displayMoveinProviso";
		ValidationChain valid = new ValidationChain(label, this.displayMoveinProviso);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 300)));

		valid.validate(errors);
	}

	/**
	 * �Ǘ��`�ԁE�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validUpkeepType(List<ValidationFailure> errors){
		String label = "housingDtl.input.upkeepType";
		ValidationChain valid = new ValidationChain(label, this.upkeepType);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * �Ǘ���Ђ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validUpkeepCorp(List<ValidationFailure> errors){
		String label = "housingDtl.input.upkeepCorp";
		ValidationChain valid = new ValidationChain(label, this.upkeepCorp);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));

		valid.validate(errors);
	}

	/**
	 * �X�V���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�X�V���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRenewChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrg";
		ValidationChain valid = new ValidationChain(label, this.renewChrg);

		// �X�V���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.renewChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �X�V���P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�X�V���̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRenewChrgCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.renewChrgCrs);

		// �X�V���̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.renewChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * �X�V�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRenewChrgName(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewChrgName";
		ValidationChain valid = new ValidationChain(label, this.renewChrgName);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

		valid.validate(errors);
	}

	/**
	 * �X�V�萔���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�X�V�萔���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRenewDue(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewDue";
		ValidationChain valid = new ValidationChain(label, this.renewDue);
		
		// �X�V�萔���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.renewDueCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �X�V�萔���P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�X�V�萔���̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRenewDueCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.renewDueCrs";
		ValidationChain valid = new ValidationChain(label, this.renewDueCrs);

		// �X�V�萔���̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.renewDue)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * ����萔���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>����萔���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBrokerageChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.brokerageChrg";
		ValidationChain valid = new ValidationChain(label, this.brokerageChrg);

		// ����萔���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.brokerageChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * ����萔���P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>����萔���̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBrokerageChrgCrs(List<ValidationFailure> errors){
		String label = "housingDtl.input.brokerageChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.brokerageChrgCrs);
		
		// �X�V�萔���̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.brokerageChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * ���������̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validChangeKeyChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.changeKeyChrg";
		ValidationChain valid = new ValidationChain(label, this.changeKeyChrg);
		
		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * ���ۗL���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validInsurExist(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurExist";
		ValidationChain valid = new ValidationChain(label, this.insurExist);

		// �p�^�[���`�F�b�N
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * ���ۗ����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validInsurChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurChrg";
		ValidationChain valid = new ValidationChain(label, this.insurChrg);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * ���۔N���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validInsurTerm(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurTerm";
		ValidationChain valid = new ValidationChain(label, this.insurTerm);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}
	
	/**
	 * ���۔F�胉���N�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validInsurLank(List<ValidationFailure> errors){
		String label = "housingDtl.input.insurLank";
		ValidationChain valid = new ValidationChain(label, this.insurLank);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "insurLank"));

		valid.validate(errors);
	}

	/**
	 * ����p�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validOtherChrg(List<ValidationFailure> errors){
		String label = "housingDtl.input.otherChrg";
		ValidationChain valid = new ValidationChain(label, this.otherChrg);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * �ړ��󋵂̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validContactRoad(List<ValidationFailure> errors){
		String label = "housingDtl.input.contactRoad";
		ValidationChain valid = new ValidationChain(label, this.contactRoad);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));

		valid.validate(errors);
	}

	/**
	 * �ړ�����/�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validContactRoadDir(List<ValidationFailure> errors){
		String label = "housingDtl.input.contactRoadDir";
		ValidationChain valid = new ValidationChain(label, this.contactRoadDir);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));
		
		valid.validate(errors);
	}

	/**
	 * �������S�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validPrivateRoad(List<ValidationFailure> errors){
		String label = "housingDtl.input.privateRoad";
		ValidationChain valid = new ValidationChain(label, this.privateRoad);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 30)));
		
		valid.validate(errors);
	}

	/**
	 * �o���R�j�[�ʐς̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBalconyArea(List<ValidationFailure> errors){
		String label = "housingDtl.input.balconyArea";
		ValidationChain valid = new ValidationChain(label, this.balconyArea);

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * ���L�����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validSpecialInstruction(List<ValidationFailure> errors){
		String label = "housingDtl.input.specialInstruction";
		ValidationChain valid = new ValidationChain(label, this.specialInstruction);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 100)));

		valid.validate(errors);
	}

	/**
	 * �ڍ׃R�����g�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDtlComment(List<ValidationFailure> errors){
		String label = "housingDtl.input.dtlComment";
		ValidationChain valid = new ValidationChain(label, this.dtlComment);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 1000)));

		valid.validate(errors);
	}



	/**
	 * �V�X�e������CD ���擾����B<br/>
	 * <br/>
	 * @return �V�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}
	
	/**
	 * �V�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param sysHousingCd �V�X�e������CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

	/**
	 * �p�r�n��CD ���擾����B<br/>
	 * <br/>
	 * @return�@�p�r�n��CD
	 */
	public String getUsedAreaCd() {
		return usedAreaCd;
	}

	/**
	 * �p�r�n��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param usedAreaCd �p�r�n��CD
	 */
	public void setUsedAreaCd(String usedAreaCd) {
		this.usedAreaCd = usedAreaCd;
	}

	/**
	 * ����`�ԋ敪���擾����B<br/>
	 * <br/>
	 * @return ����`�ԋ敪
	 */
	public String getTransactTypeDiv() {
		return transactTypeDiv;
	}

	/**
	 * ����`�ԋ敪��ݒ肷��B<br/>
	 * <br/>
	 * @param transactTypeDiv ����`�ԋ敪
	 */
	public void setTransactTypeDiv(String transactTypeDiv) {
		this.transactTypeDiv = transactTypeDiv;
	}

	/**
	 * �\���p�_����Ԃ��擾����B<br/>
	 * <br/>
	 * @return �\���p�_�����
	 */
	public String getDisplayContractTerm() {
		return displayContractTerm;
	}

	/**
	 * �\���p�_����Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param displayContractTerm �\���p�_�����
	 */
	public void setDisplayContractTerm(String displayContractTerm) {
		this.displayContractTerm = displayContractTerm;
	}
	
	/**
	 * �y�n�������擾����B<br/>
	 * <br/>
	 * @return �y�n����
	 */
	public String getLandRight() {
		return landRight;
	}

	/**
	 * �y�n������ݒ肷��B<br/>
	 * <br/>
	 * @param landRight �y�n����
	 */
	public void setLandRight(String landRight) {
		this.landRight = landRight;
	}

	/**
	 * �����\�����t���O���擾����B<br/>
	 * <br/>
	 * @return�@�����\�����t���O
	 */
	public String getMoveinTiming() {
		return moveinTiming;
	}

	/**
	 * �����\�����t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param moveinTiming �����\�����t���O
	 */
	public void setMoveinTiming(String moveinTiming) {
		this.moveinTiming = moveinTiming;
	}

	/**
	 * �����\�������擾����B<br/>
	 * <br/>
	 * @return �����\����
	 */
	public String getMoveinTimingDay() {
		return moveinTimingDay;
	}

	/**
	 * �����\������ݒ肷��B<br/>
	 * <br/>
	 * @param moveinTimingDay �����\����
	 */
	public void setMoveinTimingDay(String moveinTimingDay) {
		this.moveinTimingDay = moveinTimingDay;
	}

	/**
	 * �����\�����R�����g���擾����B<br/>
	 * <br/>
	 * @return �����\�����R�����g
	 */
	public String getMoveinNote() {
		return moveinNote;
	}

	/**
	 * �����\�����R�����g��ݒ肷��B<br/>
	 * <br/>
	 * @param moveinNote �����\�����R�����g
	 */
	public void setMoveinNote(String moveinNote) {
		this.moveinNote = moveinNote;
	}

	/**
	 * �\���p�����\�������擾����B<br/>
	 * <br/>
	 * @return �\���p�����\����
	 */
	public String getDisplayMoveinTiming() {
		return displayMoveinTiming;
	}

	/**
	 * �\���p�����\������ݒ肷��B<br/>
	 * <br/>
	 * @param displayMoveinTiming �\���p�����\����
	 */
	public void setDisplayMoveinTiming(String displayMoveinTiming) {
		this.displayMoveinTiming = displayMoveinTiming;
	}

	/**
	 * �\���p�������������擾����B<br/>
	 * <br/>
	 * @return �\���p����������
	 */
	public String getDisplayMoveinProviso() {
		return displayMoveinProviso;
	}

	/**
	 * �\���p������������ݒ肷��B<br/>
	 * <br/>
	 * @param displayMoveinProviso�@�\���p����������
	 */
	public void setDisplayMoveinProviso(String displayMoveinProviso) {
		this.displayMoveinProviso = displayMoveinProviso;
	}

	/**
	 * �Ǘ��`�ԁE�������擾����B<br/>
	 * <br/>
	 * @return �Ǘ��`�ԁE����
	 */
	public String getUpkeepType() {
		return upkeepType;
	}

	/**
	 * �Ǘ��`�ԁE������ݒ肷��B<br/>
	 * <br/>
	 * @param upkeepType �Ǘ��`�ԁE����
	 */
	public void setUpkeepType(String upkeepType) {
		this.upkeepType = upkeepType;
	}

	/**
	 * �Ǘ���Ђ��擾����B<br/>
	 * <br/>
	 * @return �Ǘ����
	 */
	public String getUpkeepCorp() {
		return upkeepCorp;
	}

	/**
	 * �Ǘ���Ђ�ݒ肷��B<br/>
	 * <br/>
	 * @param upkeepCorp �Ǘ����
	 */
	public void setUpkeepCorp(String upkeepCorp) {
		this.upkeepCorp = upkeepCorp;
	}

	/**
	 * �X�V�����擾����B<br/>
	 * <br/>
	 * @return �X�V��
	 */
	public String getRenewChrg() {
		return renewChrg;
	}

	/**
	 * �X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param renewChrg �X�V��
	 */
	public void setRenewChrg(String renewChrg) {
		this.renewChrg = renewChrg;
	}

	/**
	 * �X�V���P�ʂ��擾����B<br/>
	 * <br/>
	 * @return �X�V���P��
	 */
	public String getRenewChrgCrs() {
		return renewChrgCrs;
	}

	/**
	 * �X�V���P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param renewChrgCrs �X�V���P��
	 */
	public void setRenewChrgCrs(String renewChrgCrs) {
		this.renewChrgCrs = renewChrgCrs;
	}

	/**
	 * �X�V�������擾����B<br/>
	 * <br/>
	 * @return �X�V����
	 */
	public String getRenewChrgName() {
		return renewChrgName;
	}

	/**
	 * �X�V������ݒ肷��B<br/>
	 * @param renewChrgName
	 */
	public void setRenewChrgName(String renewChrgName) {
		this.renewChrgName = renewChrgName;
	}

	/**
	 * �X�V�萔�����擾����B<br/>
	 * <br/>
	 * @return �X�V�萔��
	 */
	public String getRenewDue() {
		return renewDue;
	}

	/**
	 * �X�V�萔����ݒ肷��B<br/>
	 * <br/>
	 * @param renewDue �X�V�萔��
	 */
	public void setRenewDue(String renewDue) {
		this.renewDue = renewDue;
	}

	/**
	 * �X�V�萔���P�ʂ��擾����B<br/>
	 * <br/>
	 * @return �X�V�萔���P��
	 */
	public String getRenewDueCrs() {
		return renewDueCrs;
	}

	/**
	 * �X�V�萔���P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param renewDueCrs �X�V�萔���P��
	 */
	public void setRenewDueCrs(String renewDueCrs) {
		this.renewDueCrs = renewDueCrs;
	}

	/**
	 * ����萔�����擾����B<br/>
	 * <br/>
	 * @return ����萔��
	 */
	public String getBrokerageChrg() {
		return brokerageChrg;
	}

	/**
	 * ����萔����ݒ肷��B<br/>
	 * <br/>
	 * @param brokerageChrg�@����萔��
	 */
	public void setBrokerageChrg(String brokerageChrg) {
		this.brokerageChrg = brokerageChrg;
	}

	/**
	 * ����萔���P�ʂ��擾����B<br/>
	 * <br/>
	 * @return ����萔���P��
	 */
	public String getBrokerageChrgCrs() {
		return brokerageChrgCrs;
	}

	/**
	 * ����萔���P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param brokerageChrgCrs ����萔���P��
	 */
	public void setBrokerageChrgCrs(String brokerageChrgCrs) {
		this.brokerageChrgCrs = brokerageChrgCrs;
	}

	/**
	 * �����������擾����B<br/>
	 * <br/>
	 * @return ��������
	 */
	public String getChangeKeyChrg() {
		return changeKeyChrg;
	}

	/**
	 * ����������ݒ肷��B<br/>
	 * <br/>
	 * @param changeKeyChrg ��������
	 */
	public void setChangeKeyChrg(String changeKeyChrg) {
		this.changeKeyChrg = changeKeyChrg;
	}

	/**
	 * ���ۗL�����擾����B<br/>
	 * <br/>
	 * @return ���ۗL��
	 */
	public String getInsurExist() {
		return insurExist;
	}

	/**
	 * ���ۗL����ݒ肷��B<br/>
	 * <br/>
	 * @param insurExist ���ۗL��
	 */
	public void setInsurExist(String insurExist) {
		this.insurExist = insurExist;
	}

	/**
	 * ���ۗ������擾����B<br/>
	 * <br/>
	 * @return ���ۗ���
	 */
	public String getInsurChrg() {
		return insurChrg;
	}

	/**
	 * ���ۗ�����ݒ肷��B<br/>
	 * <br/>
	 * @param insurChrg ���ۗ���
	 */
	public void setInsurChrg(String insurChrg) {
		this.insurChrg = insurChrg;
	}

	/**
	 * ���۔N�����擾����B<br/>
	 * <br/>
	 * @return ���۔N��
	 */
	public String getInsurTerm() {
		return insurTerm;
	}

	/**
	 * ���۔N����ݒ肷��B<br/>
	 * <br/>
	 * @param insurTerm ���۔N��
	 */
	public void setInsurTerm(String insurTerm) {
		this.insurTerm = insurTerm;
	}

	/**
	 * ���۔F�胉���N���擾����B<br/>
	 * <br/>
	 * @return ���۔F�胉���N
	 */
	public String getInsurLank() {
		return insurLank;
	}

	/**
	 * ���۔F�胉���N��ݒ肷��B<br/>
	 * <br/>
	 * @param insurLank ���۔F�胉���N
	 */
	public void setInsurLank(String insurLank) {
		this.insurLank = insurLank;
	}

	/**
	 * ����p���擾����B<br/>
	 * <br/>
	 * @return ����p
	 */
	public String getOtherChrg() {
		return otherChrg;
	}

	/**
	 * ����p��ݒ肷��B<br/>
	 * <br/>
	 * @param otherChrg ����p
	 */
	public void setOtherChrg(String otherChrg) {
		this.otherChrg = otherChrg;
	}

	/**
	 * �ړ��󋵂��擾����B<br/>
	 * <br/>
	 * @return �ړ���
	 */
	public String getContactRoad() {
		return contactRoad;
	}

	/**
	 * �ړ��󋵂�ݒ肷��B<br/>
	 * <br/>
	 * @param contactRoad �ړ���
	 */
	public void setContactRoad(String contactRoad) {
		this.contactRoad = contactRoad;
	}

	/**
	 * �ړ�����/�������擾����B<br/>
	 * <br/>
	 * @return �ړ�����/����
	 */
	public String getContactRoadDir() {
		return contactRoadDir;
	}

	/**
	 * �ړ�����/������ݒ肷��B<br/>
	 * <br/>
	 * @param contactRoadDir �ړ�����/����
	 */
	public void setContactRoadDir(String contactRoadDir) {
		this.contactRoadDir = contactRoadDir;
	}

	/**
	 * �������S���擾����B<br/>
	 * <br/>
	 * @return �������S
	 */
	public String getPrivateRoad() {
		return privateRoad;
	}

	/**
	 * �������S��ݒ肷��B<br/>
	 * <br/>
	 * @param privateRoad �������S
	 */
	public void setPrivateRoad(String privateRoad) {
		this.privateRoad = privateRoad;
	}

	/**
	 * �o���R�j�[�ʐς��擾����B<br/>
	 * <br/>
	 * @return �o���R�j�[�ʐ�
	 */
	public String getBalconyArea() {
		return balconyArea;
	}

	/**
	 * �o���R�j�[�ʐς�ݒ肷��B<br/>
	 * <br/>
	 * @param balconyArea �o���R�j�[�ʐ�
	 */
	public void setBalconyArea(String balconyArea) {
		this.balconyArea = balconyArea;
	}

	/**
	 * ���L�������擾����B<br/>
	 * <br/>
	 * @return ���L����
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * ���L�������擾����B<br/>
	 * <br/>
	 * @param specialInstruction ���L����
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * �ڍ׃R�����g���擾����B<br/>
	 * <br/>
	 * @return �ڍ׃R�����g
	 */
	public String getDtlComment() {
		return dtlComment;
	}

	/**
	 * �ڍ׃R�����g���擾����B<br/>
	 * <br/>
	 * @param dtlComment �ڍ׃R�����g
	 */
	public void setDtlComment(String dtlComment) {
		this.dtlComment = dtlComment;
	}

}
