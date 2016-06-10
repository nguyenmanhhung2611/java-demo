package jp.co.transcosmos.dm3.corePana.model.member.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.model.mypage.form.MypageUserForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.validation.SameValueValidation;
import jp.co.transcosmos.dm3.corePana.vo.MemberQuestion;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.MinLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.Validation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �}�C�y�[�W�����񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * <pre>
 * �S����         �C����     �C�����e
 * -------------- ----------- -----------------------------------------------------
 * tang.tianyun   2015.04.17  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class MemberInfoForm extends MypageUserForm {

	/** �Z���E�X�֔ԍ� */
	private String zip;
	/** �Z���E�s���{��CD */
	private String prefCd;
	/** �Z���E�s���{�� */
	private String prefName;
	/** �Z���E�s�撬���� */
	private String address;
	/** �Z���E�����Ԓn���̑� */
	private String addressOther;
	/** �d�b�ԍ� */
	private String tel;
	/** FAX */
	private String fax;
	/** ���[���z�M��] */
	private String mailSendFlg;
	/** ���Z��� */
	private String residentFlg;
	/** ��]�n��E�s���{��CD */
	private String hopePrefCd;
	/** ��]�n��E�s���{�� */
	private String hopePrefName;
	/** ��]�n��E�s�撬�� */
	private String hopeAddress;
	/** �o�^�o�H */
	private String entryRoute;
	/** �v����CD */
	private String promoCd;
	/** ������CD */
	private String refCd;
	/** ���b�N�t���O */
	private String lockFlg;
	/** �ŏI���O�C���� */
	private String lastLogin;
	/** �o�^�� */
	private String insDate;
	/** �ŏI�X�V�� */
	private String updDate;
	/** �A���P�[�g */
	private String[] questionId;
	/** �A���P�[�g���e1 */
	private String etcAnswer1;
	/** �A���P�[�g���e2 */
	private String etcAnswer2;
	/** �A���P�[�g���e3 */
	private String etcAnswer3;
	/** �@�\�t���O */
	private String projectFlg;

	private String redirectKey;

	/**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    MemberInfoForm() {
        super();
    }

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	MemberInfoForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager, CommonParameters commonParameters) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
		this.commonParameters = commonParameters;
	}


	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param mypageUser MypageUserInterface�@�����������A�}�C�y�[�W������p�o���[�I�u�W�F�N�g
	 */
	@Override
	public void setDefaultData(MypageUserInterface mypageUser){
		super.setDefaultData(mypageUser);
		PanaMypageUserInterface memberInfo = (PanaMypageUserInterface)mypageUser;
		//�����t�H�[�}�b�g
		DateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (memberInfo != null) {
			PanaCommonUtil.copyProperties(this, memberInfo);
			if (!StringValidateUtil.isEmpty(this.getLastLogin())){
				this.setLastLogin(format.format(memberInfo.getLastLogin()));
			}
			if (memberInfo.getInsDate() != null) {
				this.setInsDate(format.format(memberInfo.getInsDate()));
			}
			if (memberInfo.getUpdDate() != null) {
				this.setUpdDate(format.format(memberInfo.getUpdDate()));
			}
		}

	}

	/**
	 * �}�C�y�[�W�A���P�[�g��񂩂� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param memberQuestionList �}�C�y�[�W�A���P�[�g���
	 */
	public void setMemberQuestion(List<MemberQuestion> memberQuestionList){
		if (memberQuestionList != null && memberQuestionList.size() > 0) {
			int len = memberQuestionList.size();
			// �A���P�[�g�ԍ��A�I������CD
			String[] questionId = new String[len];
			for (int i = 0; i < len; i++) {
				questionId[i] = memberQuestionList.get(i).getQuestionId();
				if ("008".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer1(memberQuestionList.get(i).getEtcAnswer());
				}
				if ("009".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer2(memberQuestionList.get(i).getEtcAnswer());
				}
				if ("010".equals(memberQuestionList.get(i).getQuestionId())) {
					this.setEtcAnswer3(memberQuestionList.get(i).getEtcAnswer());
				}
			}
			this.setQuestionId(questionId);
		}
	}
	/**
	 * �����œn���ꂽ�}�C�y�[�W����̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @return MypageUserInterface �����������}�C�y�[�W����I�u�W�F�N�g
	 */
	@Override
	public void copyToMemberInfo(MypageUserInterface mypageUser){
		super.copyToMemberInfo(mypageUser);
		jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface memberInfo =
				(jp.co.transcosmos.dm3.corePana.model.mypage.PanaMypageUserInterface) mypageUser;

		// �Z���E�X�֔ԍ���ݒ�
		memberInfo.setZip(this.getZip());
		// �Z���E�s���{��CD��ݒ�
		memberInfo.setPrefCd(this.getPrefCd());
		// �Z���E�s�撬������ݒ�
		memberInfo.setAddress(this.getAddress());
		// �Z���E�s�撬������ݒ�
		memberInfo.setAddressOther(this.getAddressOther());
		// �d�b�ԍ�
		memberInfo.setTel(this.getTel());
		// FAX
		memberInfo.setFax(this.getFax());
		// ���[���z�M��]
		memberInfo.setMailSendFlg(this.getMailSendFlg());
		// ���Z���
		memberInfo.setResidentFlg(this.getResidentFlg());
		// ��]�n��E�s���{��
		memberInfo.setHopePrefCd(this.getHopePrefCd());
		// ��]�n��E�s�撬��
		memberInfo.setHopeAddress(this.getHopeAddress());
        // �Ǘ��T�C�g����o�^�����ꍇ
        if (!"front".equals(this.projectFlg)) {
    		// �o�^�o�H
    		memberInfo.setEntryRoute(this.getEntryRoute());
    		// ���b�N�t���O
    		memberInfo.setLockFlg(this.getLockFlg());
        }
		// �v����CD
		memberInfo.setPromoCd(this.getPromoCd());
		// ������CD
		memberInfo.setRefCd(this.getRefCd());

	}

	/**
	 * �Z���E�X�֔ԍ����擾����B<br/>
	 * <br/>
	 * @return �Z���E�X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * �Z���E�X�֔ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param zip �Z���E�X�֔ԍ�
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * �Z���E�s���{��CD���擾����B<br/>
	 * <br/>
	 * @return �Z���E�s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * �Z���E�s���{��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd �Z���E�s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * �Z���E�s�撬�������擾����B<br/>
	 * <br/>
	 * @return �Z���E�s�撬����
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * �Z���E�s�撬������ݒ肷��B<br/>
	 * <br/>
	 * @param address �Z���E�s�撬����
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * �Z���E�����Ԓn���̑����擾����B<br/>
	 * <br/>
	 * @return �Z���E�����Ԓn���̑�
	 */
	public String getAddressOther() {
		return addressOther;
	}

	/**
	 * �Z���E�����Ԓn���̑���ݒ肷��B<br/>
	 * <br/>
	 * @param addressOther �Z���E�����Ԓn���̑�
	 */
	public void setAddressOther(String addressOther) {
		this.addressOther = addressOther;
	}

	/**
	 * �d�b�ԍ����擾����B<br/>
	 * <br/>
	 * @return �d�b�ԍ�
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * �d�b�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param tel �d�b�ԍ�
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * FAX���擾����B<br/>
	 * <br/>
	 * @return FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * FAX��ݒ肷��B<br/>
	 * <br/>
	 * @param fax FAX
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * ���[���z�M��]���擾����B<br/>
	 * <br/>
	 * @return ���[���z�M��]
	 */
	public String getMailSendFlg() {
		return mailSendFlg;
	}

	/**
	 * ���[���z�M��]��ݒ肷��B<br/>
	 * <br/>
	 * @param mailSendFlg ���[���z�M��]
	 */
	public void setMailSendFlg(String mailSendFlg) {
		this.mailSendFlg = mailSendFlg;
	}

	/**
	 * ���Z��Ԃ��擾����B<br/>
	 * <br/>
	 * @return ���Z���
	 */
	public String getResidentFlg() {
		return residentFlg;
	}

	/**
	 * ���Z��Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param residentFlg ���Z���
	 */
	public void setResidentFlg(String residentFlg) {
		this.residentFlg = residentFlg;
	}

	/**
	 * ��]�n��E�s���{��CD���擾����B<br/>
	 * <br/>
	 * @return ��]�n��E�s���{��CD
	 */
	public String getHopePrefCd() {
		return hopePrefCd;
	}

	/**
	 * ��]�n��E�s���{��CD��ݒ肷��B<br/>
	 * <br/>
	 * @param hopePrefCd ��]�n��E�s���{��CD
	 */
	public void setHopePrefCd(String hopePrefCd) {
		this.hopePrefCd = hopePrefCd;
	}

	/**
	 * ��]�n��E�s�撬�����擾����B<br/>
	 * <br/>
	 * @return ��]�n��E�s�撬��
	 */
	public String getHopeAddress() {
		return hopeAddress;
	}

	/**
	 * ��]�n��E�s�撬����ݒ肷��B<br/>
	 * <br/>
	 * @param hopeAddress ��]�n��E�s�撬��
	 */
	public void setHopeAddress(String hopeAddress) {
		this.hopeAddress = hopeAddress;
	}

	/**
	 * �o�^�o�H���擾����B<br/>
	 * <br/>
	 * @return �o�^�o�H
	 */
	public String getEntryRoute() {
		return entryRoute;
	}

	/**
	 * �o�^�o�H��ݒ肷��B<br/>
	 * <br/>
	 * @param entryRoute �o�^�o�H
	 */
	public void setEntryRoute(String entryRoute) {
		this.entryRoute = entryRoute;
	}

	/**
	 * �v����CD���擾����B<br/>
	 * <br/>
	 * @return �v����CD
	 */
	public String getPromoCd() {
		return promoCd;
	}

	/**
	 * �v����CD��ݒ肷��B<br/>
	 * <br/>
	 * @param promoCd �v����CD
	 */
	public void setPromoCd(String promoCd) {
		this.promoCd = promoCd;
	}

	/**
	 * ���b�N�t���O���擾����B<br/>
	 * <br/>
	 * @return ���b�N�t���O
	 */
	public String getLockFlg() {
		return lockFlg;
	}

	/**
	 * ���b�N�t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param lockFlg ���b�N�t���O
	 */
	public void setLockFlg(String lockFlg) {
		this.lockFlg = lockFlg;
	}

	/**
	 * �ŏI���O�C�������擾����B<br/>
	 * <br/>
	 * @return �ŏI���O�C����
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * �ŏI���O�C������ݒ肷��B<br/>
	 * <br/>
	 * @param lastLogin �ŏI���O�C����
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * �o�^�����擾����B<br/>
	 * <br/>
	 * @return �o�^��
	 */
	public String getInsDate() {
		return insDate;
	}

	/**
	 * �o�^����ݒ肷��B<br/>
	 * <br/>
	 * @param insDate �o�^��
	 */
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	/**
	 * �ŏI�X�V�����擾����B<br/>
	 * <br/>
	 * @return �ŏI�X�V��
	 */
	public String getUpdDate() {
		return updDate;
	}

	/**
	 * �ŏI�X�V����ݒ肷��B<br/>
	 * <br/>
	 * @param updDate �ŏI�X�V��
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	/**
	 * �A���P�[�g���擾����B<br/>
	 * <br/>
	 * @return �A���P�[�g
	 */
	public String[] getQuestionId() {
		return questionId;
	}

	/**
	 * �A���P�[�g��ݒ肷��B<br/>
	 * <br/>
	 * @param questionId �A���P�[�g
	 */
	public void setQuestionId(String[] questionId) {
		this.questionId = questionId;
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
	 * �Z���E�s���{�����擾����B<br/>
	 * <br/>
	 * @return �Z���E�s���{��
	 */
	public String getPrefName() {
		return prefName;
	}

	/**
	 * �Z���E�s���{����ݒ肷��B<br/>
	 * <br/>
	 * @param prefName �Z���E�s���{��
	 */
	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	/**
	 * ��]�n��E�s���{�����擾����B<br/>
	 * <br/>
	 * @return ��]�n��E�s���{��
	 */
	public String getHopePrefName() {
		return hopePrefName;
	}

	/**
	 * ��]�n��E�s���{����ݒ肷��B<br/>
	 * <br/>
	 * @param hopePrefName ��]�n��E�s���{��
	 */
	public void setHopePrefName(String hopePrefName) {
		this.hopePrefName = hopePrefName;
	}

	/**
	 * ������CD���擾����B<br/>
	 * <br/>
	 * @return ������CD
	 */
	public String getRefCd() {
		return refCd;
	}

	/**
	 * ������CD��ݒ肷��B<br/>
	 * <br/>
	 * @param refCd ������CD
	 */
	public void setRefCd(String refCd) {
		this.refCd = refCd;
	}

	/**
	 * �@�\�t���O���擾����B<br/>
	 * <br/>
	 * @return �@�\�t���O
	 */
	public String getProjectFlg() {
		return projectFlg;
	}

	/**
	 * �@�\�t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param projectFlg �@�\�t���O
	 */
	public void setProjectFlg(String projectFlg) {
		this.projectFlg = projectFlg;
	}

	public String getRedirectKey() {
		return redirectKey;
	}

	public void setRedirectKey(String redirectKey) {
		this.redirectKey = redirectKey;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
	 * ���鎖�B<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors, String mode) {
        int startSize = errors.size();

        // �Z�����������̃`�F�b�N
        if ("getAddress".equals(mode)){
            // �Z���E�X�֔ԍ����̓`�F�b�N
            // �K�{�`�F�b�N
			if(StringValidateUtil.isEmpty(this.getZip())){
				ValidationFailure vf = new ValidationFailure(
                        "housingInfoZipNotInput", "", "", null);
                errors.add(vf);
			} else {
	            ValidationChain zip = new ValidationChain("mypage.input.zip", this.getZip());
	            // �����`�F�b�N
	            zip.addValidation(new LengthValidator(7));
	            // ���p�����`�F�b�N
	            zip.addValidation(new NumericValidation());
	            zip.validate(errors);
			}
    		return (startSize == errors.size());
        }

        super.validate(errors, mode);

        // �t�����g���̓��̓`�F�b�N
        if ("front".equals(this.getProjectFlg())) {
        	// Form �l�̎����ϊ�������
        	setAutoChange();

            // �Z�����������̃`�F�b�N
            if ("getAddress".equals(mode)){
                // �X�֔ԍ����̓`�F�b�N
                ValidationChain zip = new ValidationChain("member.input.zip", this.getZip());
                // �K�{�`�F�b�N
                zip.addValidation(new NullOrEmptyCheckValidation());
                // �����`�F�b�N
                zip.addValidation(new LengthValidator(7));
                // ���p�����`�F�b�N
                zip.addValidation(new NumericValidation());
                zip.validate(errors);
        		return (startSize == errors.size());
            }

        	return (startSize == frontValidate(errors, mode));
        }

        // �Z���E�X�֔ԍ����̓`�F�b�N
        ValidationChain zip = new ValidationChain("mypage.input.zip", this.getZip());
        // �K�{�`�F�b�N
        zip.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        zip.addValidation(new LengthValidator(7));
        // ���p�����`�F�b�N
        zip.addValidation(new NumericValidation());
        zip.validate(errors);

        // �Z��(�s���{��)���̓`�F�b�N
        ValidationChain prefCd = new ValidationChain("mypage.input.prefCd", this.getPrefCd());
        // �K�{�`�F�b�N
        prefCd.addValidation(new NullOrEmptyCheckValidation());
        prefCd.validate(errors);

        // �Z��(�s�撬���E�Ԓn)���̓`�F�b�N
        ValidationChain address = new ValidationChain("mypage.input.address", this.getAddress());
        // �K�{�`�F�b�N
        address.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        address.addValidation(new MaxLengthValidation(50));
        address.validate(errors);

        // �Z��(������)���̓`�F�b�N
        ValidationChain addressOther = new ValidationChain("mypage.input.addressOther", this.getAddressOther());
        // �����`�F�b�N
        addressOther.addValidation(new MaxLengthValidation(30));
        addressOther.validate(errors);

        // ���Z�`�ԓ��̓`�F�b�N
        ValidationChain residentFlg = new ValidationChain("mypage.input.residentFlg", this.getResidentFlg());
        // �p�^�[���`�F�b�N
        residentFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "residentFlg"));
        residentFlg.validate(errors);

        // ������]�n��(�s���{��)���̓`�F�b�N
        ValidationChain hopePrefCd = new ValidationChain("mypage.input.hopePrefCd", this.getHopePrefCd());
        // �K�{�`�F�b�N
        hopePrefCd.addValidation(new NullOrEmptyCheckValidation());
        hopePrefCd.validate(errors);

        // ������]�n��(�s�撬��)���̓`�F�b�N
        ValidationChain hopeAddress = new ValidationChain("mypage.input.hopeAddress", this.getHopeAddress());
        // �����`�F�b�N
        hopeAddress.addValidation(new MaxLengthValidation(50));
        hopeAddress.validate(errors);

        // �d�b�ԍ����̓`�F�b�N
        ValidationChain tel = new ValidationChain("mypage.input.tel", this.getTel());
        // �K�{�`�F�b�N
        tel.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        tel.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
        tel.addValidation(new NumericValidation());
        tel.validate(errors);

        // FAX�ԍ����̓`�F�b�N
        ValidationChain fax = new ValidationChain("mypage.input.fax", this.getFax());
        // �����`�F�b�N
        fax.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
        fax.addValidation(new NumericValidation());
        fax.validate(errors);

        // ���[���z�M���̓`�F�b�N
        ValidationChain mailSendFlg = new ValidationChain("mypage.input.mailSendFlg", this.getMailSendFlg());
        // �K�{�`�F�b�N
        mailSendFlg.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        mailSendFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "mailSendFlg"));
        mailSendFlg.validate(errors);

        // �A���P�[�g���̓`�F�b�N
		if (this.getQuestionId() != null) {
			for (String questionId : this.getQuestionId()) {
				ValidationChain valid = new ValidationChain("mypage.input.questionId", questionId);
				// �p�^�[�����̓`�F�b�N
				valid.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));
				valid.validate(errors);
			}
		}

        // �A���P�[�g���e���̓`�F�b�N
        if (this.getQuestionId() != null) {
        	for (String questionId : this.getQuestionId()) {
        		if ("008".equals(questionId)) {
        	        // �A���P�[�g���e1���̓`�F�b�N
        	        ValidationChain etcAnswer1 = new ValidationChain("mypage.input.etcAnswer1", this.getEtcAnswer1());
        	        // �����`�F�b�N
        	        etcAnswer1.addValidation(new MaxLengthValidation(50));
        	        etcAnswer1.validate(errors);
        		}
        		if ("009".equals(questionId)) {
        	        // �A���P�[�g���e2���̓`�F�b�N
        	        ValidationChain etcAnswer2 = new ValidationChain("mypage.input.etcAnswer2", this.getEtcAnswer2());
        	        // �����`�F�b�N
        	        etcAnswer2.addValidation(new MaxLengthValidation(50));
        	        etcAnswer2.validate(errors);
        		}
        		if ("010".equals(questionId)) {
        	        // �A���P�[�g���e3���̓`�F�b�N
        	        ValidationChain etcAnswer3 = new ValidationChain("mypage.input.etcAnswer3", this.getEtcAnswer3());
        	        // �����`�F�b�N
        	        etcAnswer3.addValidation(new MaxLengthValidation(50));
        	        etcAnswer3.validate(errors);
        		}
        	}
        }

        // �A���P�[�g�`�F�b�N�{�b�N�X�ƃA���P�[�g���e�̐���`�F�b�N
        List<String> list = new ArrayList<String>();
        if (this.getQuestionId() != null) {
        	list =  Arrays.asList(this.getQuestionId());
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

        // �o�^�o�H���̓`�F�b�N
        ValidationChain entryRoute = new ValidationChain("mypage.input.entryRoute", this.getEntryRoute());
        // �K�{�`�F�b�N
        entryRoute.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        entryRoute.addValidation(new CodeLookupValidation(this.codeLookupManager, "entryRoute"));
        entryRoute.validate(errors);


        // �L���敪���̓`�F�b�N
        ValidationChain lockFlg = new ValidationChain("mypage.input.lockFlg", this.getLockFlg());
        // �K�{�`�F�b�N
        lockFlg.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        lockFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "lockFlg"));
        lockFlg.validate(errors);

		return (startSize == errors.size());
	}

	/**
	 * �t�����g���o���f�[�V���������s�B<br/>
	 * <br/>
	 * @return errorsSize �`�F�b�N�G���[��
	 */
	private int frontValidate (List<ValidationFailure> errors, String mode) {

        // �X�֔ԍ����̓`�F�b�N
        ValidationChain zip = new ValidationChain("member.input.zip", this.getZip());
        // �K�{�`�F�b�N
        zip.addValidation(new NullOrEmptyCheckValidation());
		// �����`�F�b�N
        zip.addValidation(new LengthValidator(this.lengthUtils.getLength("member.input.zip", 7)));
        // ���p�����`�F�b�N
        zip.addValidation(new NumericValidation());
        zip.validate(errors);

        // �s���{����_���q�l�����̓`�F�b�N
        ValidationChain prefCd = new ValidationChain("member.input.prefCd", this.getPrefCd());
        // �K�{�`�F�b�N
        prefCd.addValidation(new NullOrEmptyCheckValidation());
        prefCd.validate(errors);

        // �s�撬���Ԓn���̓`�F�b�N
        ValidationChain address = new ValidationChain("member.input.address", this.getAddress());
        // �K�{�`�F�b�N
        address.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        address.addValidation(new MaxLengthValidation(50));
        address.validate(errors);

        // ���������̓`�F�b�N
        ValidationChain addressOther = new ValidationChain("member.input.addressOther", this.getAddressOther());
        // �����`�F�b�N
        addressOther.addValidation(new MaxLengthValidation(30));
        addressOther.validate(errors);

        // �d�b�ԍ����̓`�F�b�N
        ValidationChain tel = new ValidationChain("member.input.tel", this.getTel());
        // �K�{�`�F�b�N
        tel.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        tel.addValidation(new MaxLengthValidation(11));
        // ���p�����`�F�b�N
        tel.addValidation(new NumericValidation());
        tel.validate(errors);

        // ���Z�`�ԓ��̓`�F�b�N
        ValidationChain residentFlg = new ValidationChain("member.input.residentFlg", this.getResidentFlg());
        // �p�^�[���`�F�b�N
        residentFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "residentFlg"));
        residentFlg.validate(errors);

        // �s���{����_������]�n��
        ValidationChain hopePrefCd = new ValidationChain("member.input.hopePrefCd", this.getHopePrefCd());
        // �K�{�`�F�b�N
        hopePrefCd.addValidation(new NullOrEmptyCheckValidation());
        hopePrefCd.validate(errors);

        // ������]�n��(�s�撬��)���̓`�F�b�N
        ValidationChain hopeAddress = new ValidationChain("member.input.hopeAddress", this.getHopeAddress());
        // �����`�F�b�N
        hopeAddress.addValidation(new MaxLengthValidation(50));
        hopeAddress.validate(errors);

        // ���[���z�M���̓`�F�b�N
        ValidationChain mailSendFlg = new ValidationChain("member.input.mailSendFlg", this.getMailSendFlg());
        // �K�{�`�F�b�N
        mailSendFlg.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        mailSendFlg.addValidation(new CodeLookupValidation(this.codeLookupManager, "mailSendFlg"));
        mailSendFlg.validate(errors);

        if (this.questionId != null) {

        	for (int i = 0; i < this.questionId.length; i++) {
                // ���[���z�M���̓`�F�b�N
                ValidationChain questionId = new ValidationChain("member.input.ansCd", this.questionId[i]);
                // �p�^�[���`�F�b�N
            	questionId.addValidation(new CodeLookupValidation(this.codeLookupManager, "ans_all"));
            	questionId.validate(errors);
        	}


            // �A���P�[�g1���e1���̓`�F�b�N
            validetcAnswer(errors, "008", "member.input.etcAnswer1", this.etcAnswer1);

            // �A���P�[�g1���e2���̓`�F�b�N
            validetcAnswer(errors, "009", "member.input.etcAnswer2", this.etcAnswer2);

            // �A���P�[�g1���e3���̓`�F�b�N
            validetcAnswer(errors, "010", "member.input.etcAnswer3", this.etcAnswer3);

        }

        // �A���P�[�g�`�F�b�N�{�b�N�X�ƃA���P�[�g���e�̐���`�F�b�N
        List<String> list = new ArrayList<String>();
        if (this.getQuestionId() != null) {
        	list =  Arrays.asList(this.getQuestionId());
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

        return errors.size();
	}

	/**
	 * �A���P�[�g���e �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param chkbox �A���P�[�g�I���`�F�b�N�{�b�N�X
	 * @param label ��ʍ��ڂ̃��x��
	 * @param answer ��ʍ��ڂ̓��e
	 */
	private void validetcAnswer(List<ValidationFailure> errors, String chkbox, String label, String answer) {

		boolean isChk = false;

		for (int i = 0; i < this.questionId.length; i++) {
			if (chkbox.equals(this.questionId[i])) {
				isChk = true;
			}
		}

		if (isChk) {
			ValidationChain valid = new ValidationChain(label,answer);
	        // �����`�F�b�N
			valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("member.input.etcAnswer", 50)));

			valid.validate(errors);
		}
	}


	/**
	 * �p�X���[�h �o���f�[�V����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�ŏ������`�F�b�N</li>
	 * <li>�ő包���`�F�b�N</li>
	 * <li>�V�p�X���[�h�̔��p�p���L�������`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 */
	@Override
	protected void validPassword(List<ValidationFailure> errors, String mode) {
		String label = "mypage.input.password";
        ValidationChain valPassword = new ValidationChain(label, this.getPassword());

        if (mode.equals("insert")) {
        	// �V�K�o�^���͕K�{�`�F�b�N���s���B
        	// �X�V�o�^���̃p�X���[�h���͔͂C�ӂƂȂ�A���͂��ꂽ�ꍇ�̂݃p�X���[�h���X�V����B
        	valPassword.addValidation(new NullOrEmptyCheckValidation());						// �K�{�`�F�b�N
        }

        // �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�̃o���f�[�V�������s��
        if (!StringValidateUtil.isEmpty(this.getPassword())){

        	// �ŏ������`�F�b�N
        	valPassword.addValidation(new MinLengthValidation(this.commonParameters.getMinMypagePwdLength()));

        	// �ő包���`�F�b�N
        	valPassword.addValidation(new MaxLengthValidation(this.lengthUtils.getLength(label, 16)));

        	// �p�X���[�h���x�`�F�b�N�̃o���f�[�V�����I�u�W�F�N�g���擾�ł����ꍇ�A�p�X���[�h���x�̃o���f�[�V���������s����B
            Validation pwdValidation = createPwdValidation();
        	if (pwdValidation != null){
        		valPassword.addValidation(pwdValidation);
        	}
        }
        valPassword.validate(errors);
	}

	/**
	 * �p�X���[�h�m�F �o���f�[�V����<br/>
	 * �p�X���[�h�̓��͂��������ꍇ�̂݁A�ȉ��̃o���f�[�V���������{����<br/>
	 * <ul>
	 * <li>�K�{�`�F�b�N</li>
	 * <li>�p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	@Override
	protected void validRePassword(List<ValidationFailure> errors) {

		// �p�X���[�h�̓��͂��������ꍇ�A�p�X���[�h�m�F�̃o���f�[�V�������s��
        if (!StringValidateUtil.isEmpty(this.getPassword())){

        	String label = "mypage.input.rePassword";
            ValidationChain valNewRePwd = new ValidationChain(label, this.getPasswordChk());

            // �K�{�`�F�b�N
            valNewRePwd.addValidation(new NullOrEmptyCheckValidation());

            valNewRePwd.validate(errors);

            ValidationChain valPwd = new ValidationChain("mypage.input.password", this.getPassword());

            // �p�X���[�h�ƁA�m�F���͂̏ƍ��`�F�b�N
            String cmplabel = this.codeLookupManager.lookupValue("errorLabels", "mypage.input.rePassword");
            valPwd.addValidation(new SameValueValidation(cmplabel, this.getPasswordChk()));

            valPwd.validate(errors);
        }
	}

	/**
	 * Form �l�̎����ϊ�������B<br/>
	 * <br/>
	 */
	private void setAutoChange() {
		// �S�p�������甼�p�����ւ̎����ϊ�
		this.setZip(PanaStringUtils.changeToHankakuNumber(this.zip));
		this.setEmail(PanaStringUtils.changeToHankakuNumber(this.getEmail()));
		this.setTel(PanaStringUtils.changeToHankakuNumber(this.tel));
		this.setPrefCd(PanaStringUtils.changeToHankakuNumber(this.prefCd));
		this.setHopePrefCd(PanaStringUtils.changeToHankakuNumber(this.hopePrefCd));
	}
}
