package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.DecimalValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * ������{��񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class HousingForm implements Validateable {

	/** �V�X�e������CD */
	private String sysHousingCd;
	/** �����ԍ� */
	private String housingCd;
	/** �\���p������ */
	private String displayHousingName;
	/** �\���p�������i�J�i�j */
	private String displayHousingNameKana;
	/** �����ԍ� */
	private String roomNo;
	/** �V�X�e������CD */
	private String sysBuildingCd;
	/** �����E���i */
	private String price;
	/** �Ǘ��� */
	private String upkeep;
	/** ���v��@*/
	private String commonAreaFee;
	/** �C�U�ϗ��� */
	private String menteFee;
	/** �~�� */
	private String secDeposit;
	/** �~���P�� */
	private String secDepositCrs;
	/** �ۏ؋� */
	private String bondChrg;
	/** �ۏ؋��P�� */
	private String bondChrgCrs;
	/** �~������敪 */
	private String depositDiv;
	/** �~������z */
	private String deposit;
	/** �~������P�� */
	private String depositCrs;
	/** �Ԏ�CD */
	private String layoutCd;
	/** �Ԏ�ڍ׃R�����g */
	private String layoutComment;
	/** �����̊K�� */
	private String floorNo;
	/** �����̊K���R�����g */
	private String floorNoNote;
	/** �y�n�ʐ� */
	private String landArea;
	/** �y�n�ʐ�_�⑫ */
	private String landAreaMemo;
	/** ��L�ʐ� */
	private String personalArea;
	/** ��L�ʐ�_�⑫ */
	private String personalAreaMemo;
	/** ������ԃt���O */
	private String moveinFlg;
	/** ���ԏ�̏� */
	private String parkingSituation;
	/** ���ԏ��̗L�� */
	private String parkingEmpExist;
	/** �\���p���ԏ��� */
	private String displayParkingInfo;
	/** ���̌��� */
	private String windowDirection;
	/** ��{���R�����g */
	private String basicComment;

	// �A�C�R����� �́A�����I�Ɏ������������̂œ��͏��ł͂Ȃ��B

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
	 */
	protected HousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	

	/**
	 * �����œn���ꂽ������{���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param housingInfo ������{���
	 */
	public void copyToHousingInfo(HousingInfo housingInfo){

		// �V�X�e������CD �́A�����̔Ԃ��邩�AhousingInfo ���ŗ\�ߐݒ肳��Ă���l���g�p����̂�
		// Form �̒l�͐ݒ肵�Ȃ��B
		
		housingInfo.setHousingCd(this.housingCd);								// �����ԍ�
		housingInfo.setDisplayHousingName(this.displayHousingName);				// �\���p������
		housingInfo.setDisplayHousingNameKana(this.displayHousingNameKana);		// �\���p�������ӂ肪��
		housingInfo.setRoomNo(this.roomNo);										// �����ԍ�
		housingInfo.setSysBuildingCd(this.sysBuildingCd);						// �V�X�e������CD

		if (!StringValidateUtil.isEmpty(this.price)) {							// ����/���i
			housingInfo.setPrice(Long.valueOf(this.price));
		} else {
			housingInfo.setPrice(null);
		}

		if (!StringValidateUtil.isEmpty(this.upkeep)) {							// �Ǘ���
			housingInfo.setUpkeep(Long.valueOf(this.upkeep));
		} else {
			housingInfo.setUpkeep(null);
		}

		if (!StringValidateUtil.isEmpty(this.commonAreaFee)) {					// ���v��
			housingInfo.setCommonAreaFee(Long.valueOf(this.commonAreaFee));
		} else {
			housingInfo.setCommonAreaFee(null);
		}

		if (!StringValidateUtil.isEmpty(this.menteFee)) {						// �C�U�ϗ���
			housingInfo.setMenteFee(Long.valueOf(this.menteFee));
		} else {
			housingInfo.setMenteFee(null);
		}

		if (!StringValidateUtil.isEmpty(this.secDeposit)) {						// �~��
			housingInfo.setSecDeposit(new BigDecimal(this.secDeposit));
		} else {
			housingInfo.setSecDeposit(null);
		}

		housingInfo.setSecDepositCrs(this.secDepositCrs);						// �~���P��

		if (!StringValidateUtil.isEmpty(this.bondChrg)) {						// �ۏ؋�
			housingInfo.setBondChrg(new BigDecimal(this.bondChrg));
		} else {
			housingInfo.setBondChrg(null);
		}

		housingInfo.setBondChrgCrs(this.bondChrgCrs);							// �ۏ؋��P��
		housingInfo.setDepositDiv(this.depositDiv);								// �~������敪

		if (!StringValidateUtil.isEmpty(this.deposit)) {						// �~������z
			housingInfo.setDeposit(new BigDecimal(this.deposit));
		} else {
			housingInfo.setDeposit(null);
		}

		housingInfo.setDepositCrs(this.depositCrs);								// �~������P��
		housingInfo.setLayoutCd(this.layoutCd);									// �Ԏ�CD
		housingInfo.setLayoutComment(this.layoutComment);						// �Ԏ�ڍ׃R�����g

		if (!StringValidateUtil.isEmpty(this.floorNo)) {						// �����̊K��
			housingInfo.setFloorNo(Integer.valueOf(this.floorNo));
		} else {
			housingInfo.setFloorNo(null);
		}

		housingInfo.setFloorNoNote(this.floorNoNote);							// �����̊K���R�����g

		if (!StringValidateUtil.isEmpty(this.landArea)) {						// �y�n�ʐ�
			housingInfo.setLandArea(new BigDecimal(this.landArea));
		} else {
			housingInfo.setLandArea(null);
		}

		housingInfo.setLandAreaMemo(this.landAreaMemo);							// �y�n�ʐ�_�⑫

		if (!StringValidateUtil.isEmpty(this.personalArea)) {					// ��L�ʐ�
			housingInfo.setPersonalArea(new BigDecimal(this.personalArea));
		} else {
			housingInfo.setPersonalArea(null);
		}

		housingInfo.setPersonalAreaMemo(this.personalAreaMemo);					// ��L�ʐ�_�⑫
		housingInfo.setMoveinFlg(this.moveinFlg);								// ������ԃt���O
		housingInfo.setParkingSituation(this.parkingSituation);					// ���ԏ�̏�
		housingInfo.setParkingEmpExist(this.parkingEmpExist);					// ���ԏ��̗L��
		housingInfo.setDisplayParkingInfo(this.displayParkingInfo);				// �\���p���ԏ���
		housingInfo.setWindowDirection(this.windowDirection);					// ���̌���

		// �A�C�R�����́A�ݔ���񑤂��玩���X�V����̂œ��͍��ڂł͂Ȃ��B

		housingInfo.setBasicComment(this.basicComment);							// ��{���R�����g
		
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

		// �����ԍ�
		validHousingCd(errors);
		// �\���p������
		validDisplayHousingName(errors);
		// �\���p�������i�J�i�j
		validDisplayHousingNameKana(errors);
		// �����ԍ�
		validRoomNo(errors);
		// �V�X�e������CD
		validSysBuildingCd(errors);
		// �����E���i
		validPrice(errors);
		// �Ǘ���
		validUpkeep(errors);
		// ���v��
		validCommonAreaFee(errors);
		// �C�U�ϗ���
		validMenteFee(errors);
		// �~��
		validSecDeposit(errors);
		// �~���P��
		validSecDepositCrs(errors);
		// �ۏ؋�
		validBondChrg(errors);
		// �ۏ؋��P��
		validBondChrgCrs(errors);
		// �~������敪
		validDepositDiv(errors);
		// �~������z
		validDeposit(errors);
		// �~������P��
		validDepositCrs(errors);
		// �Ԏ�CD
		validLayoutCd(errors);
		// �Ԏ�ڍ׃R�����g
		validLayoutComment(errors);
		// �����̊K��
		validFloorNo(errors);
		// �����̊K���R�����g
		validFloorNoNote(errors);
		// �y�n�ʐ�
		validLandArea(errors);
		// �y�n�ʐ�_�⑫
		validLandAreaMemo(errors);
		// ��L�ʐ�
		validPersonalArea(errors);
		// ��L�ʐ�_�⑫
		validPersonalAreaMemo(errors);
		// ������ԃt���O
		validMoveinFlg(errors);
		// ���ԏ�̏�
		validParkingSituation(errors);
		// ���ԏ��̗L��
		validParkingEmpExist(errors);
		// �\���p���ԏ���
		validDisplayParkingInfo(errors);
		// ���̌���
		validWindowDirection(errors);
		// ��{���R�����g
		validBasicComment(errors);
		
		return (startSize == errors.size());

	}



	/**
	 * �����ԍ��̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�p���L���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validHousingCd(List<ValidationFailure> errors) {
		String label = "housing.input.housingCd";
		ValidationChain valid = new ValidationChain(label, this.housingCd);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)));
		// ���p�p���L���`�F�b�N
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * �\���p�������̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayHousingName(List<ValidationFailure> errors) {
		String label = "housing.input.displayHousingName";
		ValidationChain valid = new ValidationChain(label, this.displayHousingName);

		// �K�{�`�F�b�N
		valid.addValidation(new NullOrEmptyCheckValidation());
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * �\���p�������i�J�i�j�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�K�{�`�F�b�N</li>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>�S�p�J�^�J�i�`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayHousingNameKana(List<ValidationFailure> errors){
		String label = "housing.input.displayHousingNameKana";
		ValidationChain valid = new ValidationChain(label, this.displayHousingNameKana);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 80)));
		// �S�p�J�^�J�i�`�F�b�N
		valid.addValidation(new ZenkakuKanaValidator());

		valid.validate(errors);
	}

	/**
	 * �����ԍ��̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�p���L���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validRoomNo(List<ValidationFailure> errors){
		String label = "housing.input.roomNo";
		ValidationChain valid = new ValidationChain(label, this.roomNo);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 5)));
		// ���p�p���L���`�F�b�N
		valid.addValidation(new AsciiOnlyValidation());

		valid.validate(errors);
	}

	/**
	 * �V�X�e������CD�̃o���f�[�V����<br/>
	 * <br/>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validSysBuildingCd(List<ValidationFailure> errors){
		// Command ���ő��݃`�F�b�N���s���̂œ��ɂȂ��B
	}

	/**
	 * �����E���i�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validPrice(List<ValidationFailure> errors){
		String label = "housing.input.price";
		ValidationChain valid = new ValidationChain(label, this.price);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * �Ǘ���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validUpkeep(List<ValidationFailure> errors){
		String label = "housing.input.upkeep";
		ValidationChain valid = new ValidationChain(label, this.upkeep);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * ���v��̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validCommonAreaFee(List<ValidationFailure> errors){
		String label = "housing.input.commonAreaFee";
		ValidationChain valid = new ValidationChain(label, this.commonAreaFee);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * �C�U�ϗ���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validMenteFee(List<ValidationFailure> errors){
		String label = "housing.input.menteFee";
		ValidationChain valid = new ValidationChain(label, this.menteFee);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 11)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * �~���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�~���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validSecDeposit(List<ValidationFailure> errors){
		String label = "housing.input.secDeposit";
		ValidationChain valid = new ValidationChain(label, this.secDeposit);

		// �~���P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.secDepositCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �~���P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�~���̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validSecDepositCrs(List<ValidationFailure> errors){
		String label = "housing.input.secDepositCrs";
		ValidationChain valid = new ValidationChain(label, this.secDepositCrs);

		// �~���̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.secDeposit)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));
		
		valid.validate(errors);
	}

	/**
	 * �ۏ؋��̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ۏ؋��P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBondChrg(List<ValidationFailure> errors){
		String label = "housing.input.bondChrg";
		ValidationChain valid = new ValidationChain(label, this.bondChrg);
		
		// �ۏ؋��P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.bondChrgCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �ۏ؋��P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ۏ؋��̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBondChrgCrs(List<ValidationFailure> errors){
		String label = "housing.input.bondChrgCrs";
		ValidationChain valid = new ValidationChain(label, this.bondChrgCrs);

		// �ۏ؋��̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.bondChrg)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));
		
		valid.validate(errors);
	}

	/**
	 * �~������敪�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDepositDiv(List<ValidationFailure> errors){
		String label = "housing.input.depositDiv";
		ValidationChain valid = new ValidationChain(label, this.depositDiv);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "deposit"));

		valid.validate(errors);
	}

	/**
	 * �~������z�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�~������P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDeposit(List<ValidationFailure> errors){
		String label = "housing.input.deposit";
		ValidationChain valid = new ValidationChain(label, this.deposit);

		// �~������P�ʂ̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.depositCrs)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �~������P�ʂ̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�~������z�̓��͂��������ꍇ�͕K�{�`�F�b�N</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDepositCrs(List<ValidationFailure> errors){
		String label = "housing.input.depositCrs";
		ValidationChain valid = new ValidationChain(label, this.depositCrs);

		// �~������z�̓��͂��������ꍇ�͕K�{�`�F�b�N
		if (StringValidateUtil.isEmpty(this.deposit)){
			valid.addValidation(new NullOrEmptyCheckValidation());
		}

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "unit"));

		valid.validate(errors);
	}

	/**
	 * �Ԏ�CD �̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validLayoutCd(List<ValidationFailure> errors){
		String label = "housing.input.layoutCd";
		ValidationChain valid = new ValidationChain(label, this.layoutCd);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "layout"));
		
		valid.validate(errors);
	}

	/**
	 * �Ԏ�ڍ׃R�����g�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validLayoutComment(List<ValidationFailure> errors){
		String label = "housing.input.layoutComment";
		ValidationChain valid = new ValidationChain(label, this.layoutComment);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)));

		valid.validate(errors);
	}

	/**
	 * �����̊K���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 *   <li>���p�����`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validFloorNo(List<ValidationFailure> errors){
		String label = "housing.input.floorNo";
		ValidationChain valid = new ValidationChain(label, this.floorNo);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)));
		// ���p�����`�F�b�N
		valid.addValidation(new NumericValidation());

		valid.validate(errors);
	}

	/**
	 * �����̊K���R�����g�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validFloorNoNote(List<ValidationFailure> errors){
		String label = "housing.input.floorNoNote";
		ValidationChain valid = new ValidationChain(label, this.floorNoNote);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));

		valid.validate(errors);
	}

	/**
	 * �y�n�ʐς̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validLandArea(List<ValidationFailure> errors){
		String label = "housing.input.landArea";
		ValidationChain valid = new ValidationChain(label, this.landArea);

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * �y�n�ʐ�_�⑫�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validLandAreaMemo(List<ValidationFailure> errors){
		String label = "housing.input.landAreaMemo";
		ValidationChain valid = new ValidationChain(label, this.landAreaMemo);
		
		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));
		
		valid.validate(errors);
	}

	/**
	 * ��L�ʐς̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>���l�`�F�b�N</li>
	 *   <li>�������A�����������`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validPersonalArea(List<ValidationFailure> errors){
		String label = "housing.input.personalArea";
		ValidationChain valid = new ValidationChain(label, this.personalArea);

		// ���l�`�F�b�N
		valid.addValidation(new NumberValidation());
		// �������A�����������`�F�b�N
		valid.addValidation(new DecimalValidation(11,2));

		valid.validate(errors);
	}

	/**
	 * ��L�ʐ�_�⑫�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validPersonalAreaMemo(List<ValidationFailure> errors){
		String label = "housing.input.personalArea";
		ValidationChain valid = new ValidationChain(label, this.personalArea);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 10)));

		valid.validate(errors);
	}

	/**
	 * ������ԃt���O�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validMoveinFlg(List<ValidationFailure> errors){
		String label = "housing.input.moveinFlg";
		ValidationChain valid = new ValidationChain(label, this.moveinFlg);

		// �p�^�[���`�F�b�N
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * ���ԏ�̏󋵂̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validParkingSituation(List<ValidationFailure> errors){
		String label = "housing.input.parkingSituation";
		ValidationChain valid = new ValidationChain(label, this.parkingSituation);

		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "parkingSituation"));

		valid.validate(errors);
	}

	// 
	/**
	 * ���ԏ��̗L���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validParkingEmpExist(List<ValidationFailure> errors){
		String label = "housing.input.parkingEmpExist";
		ValidationChain valid = new ValidationChain(label, this.parkingEmpExist);
		
		// �p�^�[���`�F�b�N
		valid.addValidation(new ArrayMemberValidation(new String[]{"0","1"}));

		valid.validate(errors);
	}

	/**
	 * �\���p���ԏ���̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validDisplayParkingInfo(List<ValidationFailure> errors){
		String label = "housing.input.displayParkingInfo";
		ValidationChain valid = new ValidationChain(label, this.displayParkingInfo);

		// �ő包���`�F�b�N
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 50)));

		valid.validate(errors);
	}

	/**
	 * ���̌����̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validWindowDirection(List<ValidationFailure> errors){
		String label = "housing.input.windowDirection";
		ValidationChain valid = new ValidationChain(label, this.windowDirection);
		
		// �p�^�[���`�F�b�N
		valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "direction"));

		valid.validate(errors);
	}

	/**
	 * ��{���R�����g�̃o���f�[�V����<br/>
	 * <br/>
	 * <ul>
	 *   <li>�ő啶���񒷃`�F�b�N</li>
	 * </ul>
	 * @param errors �G���[���b�Z�[�W���X�g
	 */
	protected void validBasicComment(List<ValidationFailure> errors){
		String label = "housing.input.basicComment";
		ValidationChain valid = new ValidationChain(label, this.basicComment);

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
	 * �����ԍ����擾����B<br/>
	 * <br/>
	 * @return �����ԍ�
	 */
	public String getHousingCd() {
		return housingCd;
	}

	/**
	 * �����ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param housingCd �����ԍ�
	 */
	public void setHousingCd(String housingCd) {
		this.housingCd = housingCd;
	}

	/**
	 * �\���p���������擾����B<br/>
	 * <br/>
	 * @return �\���p������
	 */
	public String getDisplayHousingName() {
		return displayHousingName;
	}

	/**
	 * �\���p��������ݒ肷��B<br/>
	 * <br/>
	 * @param displayHousingName �\���p������
	 */
	public void setDisplayHousingName(String displayHousingName) {
		this.displayHousingName = displayHousingName;
	}

	/**
	 * �\���p�������i�J�i�j���擾����B<br/>
	 * <br/>
	 * @return �\���p�������i�J�i�j
	 */
	public String getDisplayHousingNameKana() {
		return displayHousingNameKana;
	}

	/**
	 * �\���p�������i�J�i�j��ݒ肷��B<br/>
	 * <br/>
	 * @param displayHousingNameKana �\���p�������i�J�i�j
	 */
	public void setDisplayHousingNameKana(String displayHousingNameKana) {
		this.displayHousingNameKana = displayHousingNameKana;
	}

	/**
	 * �����ԍ����擾����B<br/>
	 * <br/>
	 * @return �����ԍ�
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * �����ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param roomNo �����ԍ�
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	/**
	 * �V�X�e������CD �擾����B<br/>
	 * <br/>
	 * @return�@�V�X�e������CD
	 */
	public String getSysBuildingCd() {
		return sysBuildingCd;
	}

	/**
	 * �V�X�e������CD �ݒ肷��B<br/>
	 * <br/>
	 * @param sysBuildingCd�@�V�X�e������CD
	 */
	public void setSysBuildingCd(String sysBuildingCd) {
		this.sysBuildingCd = sysBuildingCd;
	}

	/**
	 * �����E���i���擾����B<br/>
	 * <br/>
	 * @return �����E���i
	 */
	public String getPrice() {
		return price;
	}
	
	/**
	 * �����E���i��ݒ肷��B<br/>
	 * <br/>
	 * @param price �����E���i
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * �Ǘ�����擾����B<br/>
	 * <br/>
	 * @return �Ǘ���
	 */
	public String getUpkeep() {
		return upkeep;
	}

	/**
	 * �Ǘ����ݒ肷��B<br/>
	 * <br/>
	 * @param upkeep �Ǘ���
	 */
	public void setUpkeep(String upkeep) {
		this.upkeep = upkeep;
	}
	
	/**
	 * ���v����擾����B<br/>
	 * <br/>
	 * @return ���v��
	 */
	public String getCommonAreaFee() {
		return commonAreaFee;
	}
	
	/**
	 * ���v���ݒ肷��B<br/>
	 * <br/>
	 * @param commonAreaFee�@���v��
	 */
	public void setCommonAreaFee(String commonAreaFee) {
		this.commonAreaFee = commonAreaFee;
	}

	/**
	 * �C�U�ϗ����ݒ肷��B<br/>
	 * <br/>
	 * @return �C�U�ϗ���
	 */
	public String getMenteFee() {
		return menteFee;
	}

	/**
	 * �C�U�ϗ����ݒ肷��B<br/>
	 * <br/>
	 * @param menteFee �C�U�ϗ���
	 */
	public void setMenteFee(String menteFee) {
		this.menteFee = menteFee;
	}

	/**
	 * �~�����擾����B<br/>
	 * <br/>
	 * @return �~��
	 */
	public String getSecDeposit() {
		return secDeposit;
	}

	/**
	 * �~����ݒ肷��B<br/>
	 * <br/>
	 * @param secDeposit
	 */
	public void setSecDeposit(String secDeposit) {
		this.secDeposit = secDeposit;
	}

	/**
	 * �~���P�ʂ��擾����B<br/>
	 * <br/>
	 * @return �~���P��
	 */
	public String getSecDepositCrs() {
		return secDepositCrs;
	}

	/**
	 * �~���P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param secDepositCrs �~���P��
	 */
	public void setSecDepositCrs(String secDepositCrs) {
		this.secDepositCrs = secDepositCrs;
	}

	/**
	 * �ۏ؋����擾����B<br/>
	 * <br/>
	 * @return �ۏ؋�
	 */
	public String getBondChrg() {
		return bondChrg;
	}

	/**
	 *  �ۏ؋���ݒ肷��B<br/>
	 *  <br/>
	 * @param bondChrg �ۏ؋�
	 */
	public void setBondChrg(String bondChrg) {
		this.bondChrg = bondChrg;
	}
	
	/**
	 * �ۏ؋��P�ʂ��擾����B<br/>
	 * <br/>
	 * @return �ۏ؋��P��
	 */
	public String getBondChrgCrs() {
		return bondChrgCrs;
	}

	/**
	 * �ۏ؋��P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param bondChrgCrs �ۏ؋��P��
	 */
	public void setBondChrgCrs(String bondChrgCrs) {
		this.bondChrgCrs = bondChrgCrs;
	}

	/**
	 * �~������敪���擾����B<br/>
	 * <br/>
	 * @return �~������敪
	 */
	public String getDepositDiv() {
		return depositDiv;
	}

	/**
	 * �~������敪��ݒ肷��B<br/>
	 * <br/>
	 * @param depositDiv�@�~������敪
	 */
	public void setDepositDiv(String depositDiv) {
		this.depositDiv = depositDiv;
	}

	/**
	 * �~������z���擾����B<br/>
	 * <br/>
	 * @return �~������z
	 */
	public String getDeposit() {
		return deposit;
	}

	/**
	 * �~������z��ݒ肷��B<br/>
	 * <br/>
	 * @param deposit�@�~������z
	 */
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	/**
	 * �~������P�ʂ��擾����B<br/>
	 * <br/>
	 * @return �~������P��
	 */
	public String getDepositCrs() {
		return depositCrs;
	}

	/**
	 * �~������P�ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param depositCrs �~������P��
	 */
	public void setDepositCrs(String depositCrs) {
		this.depositCrs = depositCrs;
	}
	
	/**
	 * �Ԏ�CD���擾����B<br/>
	 * <br/>
	 * @return �Ԏ�CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * �Ԏ�CD��ݒ肷��B<br/>
	 * @param layoutCd�@�Ԏ�CD
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * �Ԏ�ڍ׃R�����g���擾����B<br/>
	 * <br/>
	 * @return �Ԏ�ڍ׃R�����g
	 */
	public String getLayoutComment() {
		return layoutComment;
	}

	/**
	 * �Ԏ�ڍ׃R�����g��ݒ肷��B<br/>
	 * <br/>
	 * @param layoutComment �Ԏ�ڍ׃R�����g
	 */
	public void setLayoutComment(String layoutComment) {
		this.layoutComment = layoutComment;
	}

	/**
	 * �����̊K�����擾����B<br/>
	 * <br/>
	 * @return �����̊K��
	 */
	public String getFloorNo() {
		return floorNo;
	}

	/**
	 * �����̊K����ݒ肷��B<br/>
	 * <br/>
	 * @param floorNo�@�����̊K��
	 */
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	/**
	 * �����̊K���R�����g���擾����B<br/>
	 * <br/>
	 * @return �����̊K���R�����g
	 */
	public String getFloorNoNote() {
		return floorNoNote;
	}

	/**
	 * �����̊K���R�����g���擾����B<br/>
	 * <br/>
	 * @param floorNoNote �����̊K���R�����g
	 */
	public void setFloorNoNote(String floorNoNote) {
		this.floorNoNote = floorNoNote;
	}
	
	/**
	 * �y�n�ʐς��擾����B<br/>
	 * <br/>
	 * @return �y�n�ʐ�
	 */
	public String getLandArea() {
		return landArea;
	}

	/**
	 * �y�n�ʐς��擾����B<br/>
	 * <br/>
	 * @param landArea �y�n�ʐ�
	 */
	public void setLandArea(String landArea) {
		this.landArea = landArea;
	}
	
	/**
	 * �y�n�ʐ�_�⑫���擾����B<br/>
	 * <br/>
	 * @return �y�n�ʐ�_�⑫
	 */
	public String getLandAreaMemo() {
		return landAreaMemo;
	}

	/**
	 * �y�n�ʐ�_�⑫��ݒ肷��B<br/>
	 * <br/>
	 * @param landAreaMemo�@�y�n�ʐ�_�⑫
	 */
	public void setLandAreaMemo(String landAreaMemo) {
		this.landAreaMemo = landAreaMemo;
	}

	/**
	 * ��L�ʐς��擾����B<br/>
	 * <br/>
	 * @return ��L�ʐ�
	 */
	public String getPersonalArea() {
		return personalArea;
	}

	/**
	 * ��L�ʐς�ݒ肷��B<br/>
	 * <br/>
	 * @param personalArea�@��L�ʐ�
	 */
	public void setPersonalArea(String personalArea) {
		this.personalArea = personalArea;
	}
	
	/**
	 * ��L�ʐ�_�⑫���擾����B<br/>
	 * <br/>
	 * @return ��L�ʐ�_�⑫
	 */
	public String getPersonalAreaMemo() {
		return personalAreaMemo;
	}

	/**
	 * ��L�ʐ�_�⑫���擾����B<br/>
	 * <br/>
	 * @param personalAreaMemo ��L�ʐ�_�⑫
	 */
	public void setPersonalAreaMemo(String personalAreaMemo) {
		this.personalAreaMemo = personalAreaMemo;
	}

	/**
	 * ������ԃt���O���擾����B<br/>
	 * <br/>
	 * @return ������ԃt���O
	 */
	public String getMoveinFlg() {
		return moveinFlg;
	}

	/**
	 * ������ԃt���O���擾����B<br/>
	 * <br/>
	 * @param moveinFlg ������ԃt���O
	 */
	public void setMoveinFlg(String moveinFlg) {
		this.moveinFlg = moveinFlg;
	}

	/**
	 * ���ԏ�̏󋵂��擾����B<br/>
	 * <br/>
	 * @return ���ԏ�̏�
	 */
	public String getParkingSituation() {
		return parkingSituation;
	}

	/**
	 * ���ԏ�̏󋵂�ݒ肷��B<br/>
	 * <br/>
	 * @param parkingSituation�@���ԏ�̏�
	 */
	public void setParkingSituation(String parkingSituation) {
		this.parkingSituation = parkingSituation;
	}

	/**
	 * ���ԏ��̗L�����擾����B<br/>
	 * <br/>
	 * @return ���ԏ��̗L��
	 */
	public String getParkingEmpExist() {
		return parkingEmpExist;
	}

	/**
	 * ���ԏ��̗L����ݒ肷��B<br/>
	 * <br/>
	 * @param parkingEmpExist ���ԏ��̗L��
	 */
	public void setParkingEmpExist(String parkingEmpExist) {
		this.parkingEmpExist = parkingEmpExist;
	}

	/**
	 * �\���p���ԏ�����擾����B<br/>
	 * <br/>
	 * @return �\���p���ԏ���
	 */
	public String getDisplayParkingInfo() {
		return displayParkingInfo;
	}

	/**
	 * �\���p���ԏ����ݒ肷��B<br/>
	 * <br/>
	 * @param displayParkingInfo �\���p���ԏ���
	 */
	public void setDisplayParkingInfo(String displayParkingInfo) {
		this.displayParkingInfo = displayParkingInfo;
	}
	
	/**
	 * ���̌������擾����B<br/>
	 * <br/>
	 * @return ���̌���
	 */
	public String getWindowDirection() {
		return windowDirection;
	}
	
	/**
	 * ���̌�����ݒ肷��B<br/>
	 * <br/>
	 * @param windowDirection�@���̌���
	 */
	public void setWindowDirection(String windowDirection) {
		this.windowDirection = windowDirection;
	}

	/**
	 * ��{���R�����g���擾����B<br/>
	 * <br/>
	 * @return ��{���R�����g
	 */
	public String getBasicComment() {
		return basicComment;
	}

	/**
	 * ��{���R�����g��ݒ肷��B<br/>
	 * <br/>
	 * @param basicComment�@��{���R�����g
	 */
	public void setBasicComment(String basicComment) {
		this.basicComment = basicComment;
	}

}
