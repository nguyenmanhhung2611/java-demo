package jp.co.transcosmos.dm3.core.model.mypage.form;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �}�C�y�[�W����̌��������p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 * <br/>
 * �܂��A���� Form �̓t�����g������g�p���Ȃ����B<br/>
 * �iUserId �����N�G�X�g�p�����[�^�Ŏ󂯎���Ă��܂����X�N������ׁB�j<br/>
 * 
 */
public class MypageUserSearchForm  extends PagingListForm<JoinResult> implements Validateable {

	private static final Log log = LogFactory.getLog(MypageUserSearchForm.class);
	


	// command ���N�G�X�g�p�����[�^�́AURL �}�b�s���O�Œ�`����Ă���AnamedCommands �̐���ɂ��g�p�����B
	// �Ǘ����[�U�[�̃����e�i���X�@�\�ł́A�ȉ��̒l���ݒ肳���ꍇ������B
	//
	// list : �Ǘ����[�U�[������ʂŎ��ۂɌ������s���ꍇ�Ɏw�肷��B�@�i���w��̏ꍇ�A������ʂ������\���B�j
	// back : ���͊m�F��ʂ�����͉�ʂ֕��A�����ꍇ�B�@�i�w�肳�ꂽ�ꍇ�A�p�����[�^�̒l�������l�Ƃ��ē��͉�ʂɕ\������B�j

	/** ������ʂ� command �p�����[�^ */
	private String searchCommand;

	// note
	// �t�����g������̎g�p���l�������ꍇ�AuserId �����N�G�X�g�p�����[�^�Ŏ����ɂ͍s���Ȃ��̂� input �p
	// form �ɂ� userId ��z�u���Ȃ��B
	// �����p�t�H�[���̓t�����g������g�p���鎖�͖����̂ŁA�����p�t�H�[���ŏ����Ώۃ��[�U�[ID ���󂯎��B

	/** �����ΏۂƂȂ郆�[�U�[ID */
	private String userId;

	/** ���[���A�h���X �i���������j */
	private String keyEmail;
	/** �����E�� �i���������j */
	private String keyMemberLname;
	/** �����E���i���������j */
	private String keyMemberFname;
	/** �����E�J�i�E�Z�C �i���������j */
	private String keyMemberLnameKana;
	/** �����E�J�i�E���C �i���������j */
	private String keyMemberFnameKana;
	/** �o�^���E�J�n �i���������j */
	private String keyInsDateFrom;
	/** �o�^���E�I�� �i���������j */
	private String keyInsDateTo;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;


	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected MypageUserSearchForm(){
		super();
	}

	

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected MypageUserSearchForm(LengthValidationUtils lengthUtils){
		super();
		this.lengthUtils = lengthUtils;
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

        // ���[���A�h���X �i���������j ���̓`�F�b�N
        valiKeyEmail(errors);

        // �����E�� �i���������j ���̓`�F�b�N
        valiKeyMemberLname(errors);

        // �����E�� �i���������j ���̓`�F�b�N
        valiKeyMemberFname(errors);

        // �����E�J�i�E�Z�C �i���������j���̓`�F�b�N
        valiKeyMemberLnameKana(errors);

        // �����E�J�i�E���C �i���������j ���̓`�F�b�N
        valiKeyMemberFnameKana(errors);

        // �o�^���E�J�n �i���������j ���̓`�F�b�N
        valiKeyInsDateFrom(errors);

        // �o�^���E�I�� �i���������j ���̓`�F�b�N
        valiKeyInsDateTo(errors);

        return (startSize == errors.size());
	}

	/**
	 * ���[���A�h���X�i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyEmail(List<ValidationFailure> errors) {
        // ���[���A�h���X �i���������j ���̓`�F�b�N
        ValidationChain valMail = new ValidationChain("mypage.search.keyEmail",this.keyEmail);
        // �����`�F�b�N
        valMail.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("mypage.search.keyEmail", 255)));
        valMail.validate(errors);
	}

	/**
	 * �����E�� �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberLname(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberLname";
        ValidationChain valMemberLname = new ValidationChain(label,this.keyMemberLname);

        // �����`�F�b�N
        valMemberLname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberLname.validate(errors);
	}

	/**
	 * �����E�� �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberFname(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberFname";
        ValidationChain valMemberFname = new ValidationChain(label,this.keyMemberFname);

        // �����`�F�b�N
        valMemberFname.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberFname.validate(errors);
	}

	/**
	 * �����E�J�i�E�Z�C �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberLnameKana(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberLnameKana";
        ValidationChain valMemberLnameKana = new ValidationChain(label,this.keyMemberLnameKana);

        // �����`�F�b�N
        valMemberLnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));

        valMemberLnameKana.validate(errors);
	}

	/**
	 * �����E�J�i�E���C �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberFnameKana(List<ValidationFailure> errors) {
		String label = "mypage.search.keyMemberFnameKana";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.keyMemberFnameKana);

        // �����`�F�b�N
        valMemberFnameKana.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 40)));
        
        valMemberFnameKana.validate(errors);
	}

	/**
	 * �o�^���E�J�n �i���������j �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyInsDateFrom(List<ValidationFailure> errors) {
		String label = "mypage.search.keyInsDateFrom";
        ValidationChain valInsDateFrom = new ValidationChain(label,this.keyInsDateFrom);

        // ���t�����`�F�b�N
        valInsDateFrom.addValidation(new ValidDateValidation("yyyy/MM/dd"));

        valInsDateFrom.validate(errors);
	}

	/**
	 * �o�^���E�I�� �i���������j �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyInsDateTo(List<ValidationFailure> errors) {
		String label = "mypage.search.keyInsDateTo";
        ValidationChain valInsDateTo = new ValidationChain(label,this.keyInsDateTo);

        // ���t�����`�F�b�N
        valInsDateTo.addValidation(new ValidDateValidation("yyyy/MM/dd"));

        valInsDateTo.validate(errors);
	}



	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g
	 * �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria(){

		// �����p�I�u�W�F�N�g�̐���
		// �y�[�W�������s���ꍇ�APagingListForm�@�C���^�[�t�F�[�X���������� Form ����
		// DAOCriteria �𐶐�����K�v������B ����āA���g�� buildCriteria() ��
		// �g�p���Č��������𐶐����Ă���B
		return buildCriteria(super.buildCriteria());
		
	}

	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * �����Ŏ󂯎���������I�u�W�F�N�g�ɑ΂��āA�󂯎�������N�G�X�g�p�����[�^���猟�������𐶐�����
	 * �ݒ肷��B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�}�C�y�[�W������̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ���������I�u�W�F�N�g
	 */
	protected DAOCriteria buildCriteria(DAOCriteria criteria){

		// ���[���A�h���X�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyEmail)) {
			criteria.addWhereClause("email", "%" + this.keyEmail + "%", DAOCriteria.LIKE);
		}
		
		// �����O�i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyMemberLname)) {
			criteria.addWhereClause("memberLname", "%" + this.keyMemberLname + "%", DAOCriteria.LIKE);
		}

		// �����O�i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyMemberFname)) {
			criteria.addWhereClause("memberFname", "%" + this.keyMemberFname + "%", DAOCriteria.LIKE);
		}

		// �t���K�i�i�Z�C�j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyMemberLnameKana)) {
			criteria.addWhereClause("memberLnameKana", "%" + this.keyMemberLnameKana + "%", DAOCriteria.LIKE);
		}

		// �t���K�i�i���C�j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyMemberFnameKana)) {
			criteria.addWhereClause("memberFnameKana", "%" + this.keyMemberFnameKana + "%", DAOCriteria.LIKE);
		}

		// �o�^���i�J�n���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyInsDateFrom)) {

			try {
				criteria.addWhereClause("insDate", StringUtils.stringToDate(this.keyInsDateFrom, "yyyy/MM/dd"), DAOCriteria.GREATER_THAN_EQUALS);

			} catch (ParseException e) {
				// �o���f�[�V���������{���Ă���̂ŁA���̃P�[�X�ɂȂ鎖�͒ʏ킠�肦�Ȃ��B
				// ������O�����������ꍇ�͌x�������O�o�͂��Č��������Ƃ��Ă͖�������B
				log.warn("keyInsDateFrom is invalid date string (" + this.keyInsDateFrom + ")", e);
			}
		}

		// �o�^���i�I�����j�̌�������������
		if (!StringValidateUtil.isEmpty(this.keyInsDateTo)) {

			try {
				// �^�C���X�^���v���ɂ͎��ԏ�񂪂���̂ŁA�I�����͗��������Ō�������B
				Date toDate = DateUtils.addDays(this.keyInsDateTo, "yyyy/MM/dd", 1);
				criteria.addWhereClause("insDate", toDate, DAOCriteria.LESS_THAN);

			} catch (ParseException e) {
				// �o���f�[�V���������{���Ă���̂ŁA���̃P�[�X�ɂȂ鎖�͒ʏ킠�肦�Ȃ��B
				// ������O�����������ꍇ�͌x�������O�o�͂��Č��������Ƃ��Ă͖�������B
				log.warn("keyInsDateTo is invalid date string (" + this.keyInsDateTo + ")", e);
			}
			
		}

		return criteria;
	}
	
	/**
	 * �}�C�y�[�W������̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�}�C�y�[�W������̃e�[�u���Ƃ��āAMemberInfo �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// �����Ă���e�[�u���̎�L�[�̌��������𐶐�����B 
		criteria.addWhereClause("userId", this.userId);

		return criteria;
	}



	/**
	 * ������ʂŎg�p���Ă��� command �p�����[�^�̒l���擾����B<br/>
	 * ���̃p�����[�^�l�́A������ʂɕ��A����ہAcommand �p�����[�^�Ƃ��ēn���K�v������B<br/>
	 * <br/>
	 * @return command ����p�p�����[�^
	 */
	public String getSearchCommand() {
		return this.searchCommand;
	}

	/**
	 * ������ʂŎg�p���Ă��� command �p�����[�^�̒l��ݒ肷��B<br/>
	 * ���̃p�����[�^�l�́A������ʂɕ��A����ہAcommand �p�����[�^�Ƃ��ēn���K�v������B<br/>
	 * <br/>
	 * @param searchCommand ����p�p�����[�^
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * �����ΏۂƂȂ郆�[�U�[ID ���擾����B<br/>
	 * <br/>
	 * @return ���[�U�[ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * �����ΏۂƂȂ郆�[�U�[ID ��ݒ肷��B<br/>
	 * <br/>
	 * @param userId ���[�U�[ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ���[���A�h���X �i���������j���擾����B<br/>
	 * <br/>
	 * @return ���[���A�h���X �i���������j
	 */
	public String getKeyEmail() {
		return keyEmail;
	}

	/**
	 * ���[���A�h���X �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyEmail�@���[���A�h���X �i���������j
	 */
	public void setKeyEmail(String keyEmail) {
		this.keyEmail = keyEmail;
	}

	/**
	 * �����E�� �i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E�� �i���������j
	 */
	public String getKeyMemberLname() {
		return keyMemberLname;
	}

	/**
	 * �����E�� �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyMemberLname�@�����E�� �i���������j
	 */
	public void setKeyMemberLname(String keyMemberLname) {
		this.keyMemberLname = keyMemberLname;
	}
	
	/**
	 * �����E���i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E���i���������j
	 */
	public String getKeyMemberFname() {
		return keyMemberFname;
	}

	/**
	 * �����E���i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyMemberFname �����E���i���������j
	 */
	public void setKeyMemberFname(String keyMemberFname) {
		this.keyMemberFname = keyMemberFname;
	}
	
	/**
	 * �����E�J�i�E�Z�C �i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i�E�Z�C �i���������j
	 */
	public String getKeyMemberLnameKana() {
		return keyMemberLnameKana;
	}

	/**
	 * �����E�J�i�E�Z�C �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyMemberLnameKana �����E�J�i�E�Z�C �i���������j
	 */
	public void setKeyMemberLnameKana(String keyMemberLnameKana) {
		this.keyMemberLnameKana = keyMemberLnameKana;
	}
	
	/**
	 * �����E�J�i�E���C �i���������j���擾����B<br/>
	 * <br/>
	 * @return �����E�J�i�E���C �i���������j
	 */
	public String getKeyMemberFnameKana() {
		return keyMemberFnameKana;
	}

	/**
	 * �����E�J�i�E���C �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyMemberFnameKana �����E�J�i�E���C �i���������j
	 */
	public void setKeyMemberFnameKana(String keyMemberFnameKana) {
		this.keyMemberFnameKana = keyMemberFnameKana;
	}
	
	/**
	 * �o�^���E�J�n �i���������j���擾����B<br/>
	 * <br/>
	 * @return �o�^���E�J�n �i���������j
	 */
	public String getKeyInsDateFrom() {
		return keyInsDateFrom;
	}

	/**
	 * �o�^���E�J�n �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * @param keyInsDateFrom�@�o�^���E�J�n �i���������j
	 */
	public void setKeyInsDateFrom(String keyInsDateFrom) {
		this.keyInsDateFrom = keyInsDateFrom;
	}

	/**
	 * �o�^���E�I�� �i���������j ���擾����B<br/>
	 * <br/>
	 * @return �o�^���E�I�� �i���������j
	 */
	public String getKeyInsDateTo() {
		return keyInsDateTo;
	}

	/**
	 * �o�^���E�I�� �i���������j ��ݒ肷��B<br/>
	 * <br/>
	 * @param keyInsDateTo�@�o�^���E�I�� �i���������j
	 */
	public void setKeyInsDateTo(String keyInsDateTo) {
		this.keyInsDateTo = keyInsDateTo;
	}

}
