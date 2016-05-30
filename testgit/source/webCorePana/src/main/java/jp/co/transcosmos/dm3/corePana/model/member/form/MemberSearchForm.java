package jp.co.transcosmos.dm3.corePana.model.member.form;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.ZenkakuKanaValidator;
import jp.co.transcosmos.dm3.corePana.validation.DateFromToValidation;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.DateUtils;
import jp.co.transcosmos.dm3.utils.StringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;
import jp.co.transcosmos.dm3.validation.ZenkakuOnlyValidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ������ꗗ�p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����        �C����     �C�����e
 * ------------  ----------- -----------------------------------------------------
 * tang.tianyun  2015.04.15  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class MemberSearchForm extends MypageUserSearchForm implements Validateable {

	private static final Log log = LogFactory.getLog(MypageUserSearchForm.class);

	/** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;

	/**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    MemberSearchForm() {
        super();
    }

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected MemberSearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/** ���[�U�[ID �i���������j */
	private String keyUserNo;
	/** �v�����R�[�h �i���������j */
	private String keyPromo;
	/** ����Z��CD �i���������j */
	private String keyPrefCd;
	/** ����Z���� �i���������j */
	private String keyPrefName;
	/** �o�^�o�H �i���������j */
	private String keyEntryRoute;
	/** �����o�H �i���������j */
	private String[] keyInflowRoute;

	/**
	 * @return keyUserNo
	 */
	public String getKeyUserNo() {
		return keyUserNo;
	}

	/**
	 * @param keyUserNo �Z�b�g���� keyUserNo
	 */
	public void setKeyUserNo(String keyUserNo) {
		this.keyUserNo = keyUserNo;
	}

	/**
	 * @return keyPromo
	 */
	public String getKeyPromo() {
		return keyPromo;
	}

	/**
	 * @param keyPromo �Z�b�g���� keyPromo
	 */
	public void setKeyPromo(String keyPromo) {
		this.keyPromo = keyPromo;
	}

	/**
	 * @return keyPrefCd
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * @param keyPrefCd �Z�b�g���� keyPrefCd
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}

	/**
	 * @return keyPrefName
	 */
	public String getKeyPrefName() {
		return keyPrefName;
	}

	/**
	 * @param keyPrefName �Z�b�g���� keyPrefName
	 */
	public void setKeyPrefName(String keyPrefName) {
		this.keyPrefName = keyPrefName;
	}

	/**
	 * @return keyEntryRoute
	 */
	public String getKeyEntryRoute() {
		return keyEntryRoute;
	}

	/**
	 * @param keyEntryRoute �Z�b�g���� keyEntryRoute
	 */
	public void setKeyEntryRoute(String keyEntryRoute) {
		this.keyEntryRoute = keyEntryRoute;
	}

	/**
	 * @return keyInflowRoute
	 */
	public String[] getKeyInflowRoute() {
		return keyInflowRoute;
	}

	/**
	 * @param keyInflowRoute �Z�b�g���� keyInflowRoute
	 */
	public void setKeyInflowRoute(String[] keyInflowRoute) {
		this.keyInflowRoute = keyInflowRoute;
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

        // ����ԍ� �i���������j ���̓`�F�b�N
        valiUserId(errors);

        // �v����CD �i���������j ���̓`�F�b�N
        valiKeyPromoCd(errors);

        // �����O�i���j�i���������j ���̓`�F�b�N
        valiKeyMemberLname(errors);

        // �����O�i���j �i���������j ���̓`�F�b�N
        valiKeyMemberFname(errors);

        // �t���K�i(�Z�C) �i���������j���̓`�F�b�N
        valiKeyMemberLnameKana(errors);

        // �t���K�i(���C) �i���������j ���̓`�F�b�N
        valiKeyMemberFnameKana(errors);

        // �o�^���E�J�n �i���������j ���̓`�F�b�N
        valiKeyInsDateFrom(errors);

        // �o�^���E�I�� �i���������j ���̓`�F�b�N
        valiKeyInsDateTo(errors);

        // �o�^���E�J�n �o�^���E�I�� �i���������j ���t��r�`�F�b�N
        valiKeyInsDate(errors);

        // �o�^�o�H���̓`�F�b�N
        validKeyEntryRoute(errors);

        // �����o�H���̓`�F�b�N
        validKeyInflowRoute(errors);

        return (startSize == errors.size());
	}

	/**
	 * ����ԍ��i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiUserId(List<ValidationFailure> errors) {
		String label = "user.search.userId";
        ValidationChain valUserId = new ValidationChain(label,this.getKeyUserNo());

        // �����`�F�b�N
        valUserId.addValidation(new MaxLengthValidation(20));
        // ���p�p�����`�F�b�N
        valUserId.addValidation(new AlphanumericOnlyValidation());
        valUserId.validate(errors);
	}

	/**
	 * �v����CD �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyPromoCd(List<ValidationFailure> errors) {
		String label = "member.search.KeyPromoCd";
        ValidationChain valMemberLname = new ValidationChain(label,this.getKeyPromo());

        // �����`�F�b�N
        valMemberLname.addValidation(new MaxLengthValidation(20));
        // ���p�p�����`�F�b�N
        valMemberLname.addValidation(new AlphanumericOnlyValidation());

        valMemberLname.validate(errors);
	}

	/**
	 * �����O�i���j �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�S�p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberLname(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberLname";
        ValidationChain valMemberLname = new ValidationChain(label,this.getKeyMemberLname());

        // �����`�F�b�N
        valMemberLname.addValidation(new MaxLengthValidation(30));
        // �S�p�����`�F�b�N
        valMemberLname.addValidation(new ZenkakuOnlyValidation());

        valMemberLname.validate(errors);
	}

	/**
	 * �����O�i���j �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�S�p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberFname(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberFname";
        ValidationChain valMemberFname = new ValidationChain(label,this.getKeyMemberFname());

        // �����`�F�b�N
        valMemberFname.addValidation(new MaxLengthValidation(30));
        // �S�p�����`�F�b�N
        valMemberFname.addValidation(new ZenkakuOnlyValidation());

        valMemberFname.validate(errors);
	}

	/**
	 * �t���K�i(�Z�C) �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�S�p�J�^�J�i�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberLnameKana(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberLnameKana";
        ValidationChain valMemberLnameKana = new ValidationChain(label,this.getKeyMemberLnameKana());

        // �����`�F�b�N
        valMemberLnameKana.addValidation(new MaxLengthValidation(30));
        // �S�p�J�^�J�i�`�F�b�N
        valMemberLnameKana.addValidation(new ZenkakuKanaValidator());

        valMemberLnameKana.validate(errors);
	}

	/**
	 * �t���K�i(���C) �i���������j �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E�S�p�J�^�J�i�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyMemberFnameKana(List<ValidationFailure> errors) {
		String label = "member.search.keyMemberFnameKana";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.getKeyMemberFnameKana());

        // �����`�F�b�N
        valMemberFnameKana.addValidation(new MaxLengthValidation(30));
        // �S�p�J�^�J�i�`�F�b�N
        valMemberFnameKana.addValidation(new ZenkakuKanaValidator());

        valMemberFnameKana.validate(errors);
	}

	/**
	 * �o�^���E�J�n �o�^���E�I�� �i���������j �o���f�[�V����<br/>
	 * �E���t��r�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valiKeyInsDate(List<ValidationFailure> errors) {
		String label = "member.search.keyInsDateFrom";
        ValidationChain valMemberFnameKana = new ValidationChain(label,this.getKeyInsDateFrom());

        // ���t��r�`�F�b�N
        valMemberFnameKana.addValidation(new DateFromToValidation("yyyy/MM/dd", "�o�^���E�I��", this.getKeyInsDateTo()));

        valMemberFnameKana.validate(errors);
	}

	/**
     * �o�^�o�H �o���f�[�V����<br/>
     * �E�p�^�[���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyEntryRoute(List<ValidationFailure> errors) {
    	String label = "member.search.keyEntryRoute";
        ValidationChain valid = new ValidationChain(label, this.getKeyEntryRoute());
        // �p�^�[���`�F�b�N
        valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "entryRoute"));
        valid.validate(errors);
    }

    /**
     * �����o�H �o���f�[�V����<br/>
     * �E�p�^�[���`�F�b�N
     * <br/>
     * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
     */
    protected void validKeyInflowRoute(List<ValidationFailure> errors) {
    	String label = "member.search.keyInflowRoute";
    	if (this.getKeyInflowRoute() != null) {
    		for (int i = 0; i < this.getKeyInflowRoute().length; i++) {
        		ValidationChain valid = new ValidationChain(label, this.keyInflowRoute[i]);
                // �p�^�[���`�F�b�N
                valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "refCd"));
                valid.validate(errors);
        	}
    	}
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

		// ����ԍ��̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyUserNo())) {
			criteria.addWhereClause("userId", this.getKeyUserNo(), DAOCriteria.EQUALS);
		}

		// ���[���A�h���X�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyEmail())) {
			criteria.addWhereClause("email", "%" + this.getKeyEmail() + "%", DAOCriteria.LIKE);
		}

		// �v����CD�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyPromo())) {
			criteria.addWhereClause("promoCd", this.getKeyPromo(), DAOCriteria.EQUALS);
		}

		// ����Z���̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyPrefCd())) {
			criteria.addWhereClause("prefCd", this.getKeyPrefCd(), DAOCriteria.EQUALS);
		}

		// �����O�i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyMemberLname())) {
			criteria.addWhereClause("memberLname", "%" + this.getKeyMemberLname() + "%", DAOCriteria.LIKE);
		}

		// �����O�i���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyMemberFname())) {
			criteria.addWhereClause("memberFname", "%" + this.getKeyMemberFname() + "%", DAOCriteria.LIKE);
		}

		// �t���K�i�i�Z�C�j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyMemberLnameKana())) {
			criteria.addWhereClause("memberLnameKana", "%" + this.getKeyMemberLnameKana() + "%", DAOCriteria.LIKE);
		}

		// �t���K�i�i���C�j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyMemberFnameKana())) {
			criteria.addWhereClause("memberFnameKana", "%" + this.getKeyMemberFnameKana() + "%", DAOCriteria.LIKE);
		}

		// �o�^�o�H�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyEntryRoute()) && !"000".equals(this.getKeyEntryRoute())) {
			criteria.addWhereClause("entryRoute", this.getKeyEntryRoute(), DAOCriteria.EQUALS);
		}

		// �����o�H�̌�������������
		if (this.keyInflowRoute != null && this.keyInflowRoute.length > 0) {
			criteria.addInSubQuery("refCd", this.keyInflowRoute);
		}

		// �o�^���i�J�n���j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateFrom())) {

			try {
				criteria.addWhereClause("insDate", StringUtils.stringToDate(this.getKeyInsDateFrom(), "yyyy/MM/dd"), DAOCriteria.GREATER_THAN_EQUALS);

			} catch (ParseException e) {
				// �o���f�[�V���������{���Ă���̂ŁA���̃P�[�X�ɂȂ鎖�͒ʏ킠�肦�Ȃ��B
				// ������O�����������ꍇ�͌x�������O�o�͂��Č��������Ƃ��Ă͖�������B
				log.warn("keyInsDateFrom is invalid date string (" + this.getKeyInsDateFrom() + ")");
				e.printStackTrace();
			}
		}

		// �o�^���i�I�����j�̌�������������
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateTo())) {

			try {
				// �^�C���X�^���v���ɂ͎��ԏ�񂪂���̂ŁA�I�����͗��������Ō�������B
				Date toDate = DateUtils.addDays(this.getKeyInsDateTo(), "yyyy/MM/dd", 1);
				criteria.addWhereClause("insDate", toDate, DAOCriteria.LESS_THAN);

			} catch (ParseException e) {
				// �o���f�[�V���������{���Ă���̂ŁA���̃P�[�X�ɂȂ鎖�͒ʏ킠�肦�Ȃ��B
				// ������O�����������ꍇ�͌x�������O�o�͂��Č��������Ƃ��Ă͖�������B
				log.warn("keyInsDateTo is invalid date string (" + this.getKeyInsDateTo() + ")");
				e.printStackTrace();
			}

		}

		criteria.addOrderByClause("userId", false);

		return criteria;
	}
}
