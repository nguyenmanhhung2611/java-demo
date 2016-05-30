package jp.co.transcosmos.dm3.core.model.information.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * ���m�点�����e�i���X�̓��̓p�����[�^����p�t�H�[��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.06	�V�K�쐬
 * H.Mizuno		2015.02.26	�p�b�P�[�W�ړ��A�R���X�g���N�^�[�̉B��
 *
 * ���ӎ���
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B
 *
 * </pre>
 */
public class InformationForm implements Validateable {

	private static final Log log = LogFactory.getLog(InformationForm.class);
	
	
	/** command �p�����[�^ */
	private String command;
	
	/** ���m�点�ԍ� */
	private String informationNo;
	/** ���m�点��� �i01 = ���m�点�A 02 = ���̑��j*/
	private String informationType;
	/** �^�C�g�� */
	private String title;
	/** �\���J�n�� */
	private String startDate;
	/** �\���I���� */
	private String endDate;
	/** �����N��URL */
	private String url;
	/** ���J�Ώۋ敪 (0:�����܂ޑS����A1:�S�{����A2:�l)*/
	private String dspFlg;
	/** ����� */
	private String memberName;
	/** ���[�����M�t���O  (0:���M���Ȃ��A1:���M����) */
	private String mailFlg;
	/** ���m�点���e */
	private String informationMsg;
	/** �Ώۉ�� */
	private String userId;
	
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	
	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected InformationForm(){
		super();
	}



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	protected InformationForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager){
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
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
	public boolean validate(List<ValidationFailure> errors) {

        int startSize = errors.size();

        // ���m�点��ʓ��̓`�F�b�N
        validInformationType(errors);


        // ���J�Ώۋ敪���̓`�F�b�N
        validDspFlg(errors);


        // �������̏ꍇ
        if (!StringUtils.isEmpty(this.dspFlg) && "2".equals(this.dspFlg)) {
            // ������̓`�F�b�N
            validUserId(errors);

            // �R�[�h�̑Ó����́ADB �ƍ����K�v�Ȃ̂� Command �N���X���ōs��
        }

        // �^�C�g�����̓`�F�b�N
        validTitle(errors);

        // ���m�点���e���̓`�F�b�N
        validInformationMsg(errors);

        // �J�n�� ���̓`�F�b�N
        validStartDate(errors);
        
        // �I���� ���̓`�F�b�N
        validEndDate(errors);
        
        // �����N�� URL ���̓`�F�b�N
        validInputUrl(errors);

        return (startSize == errors.size());
	}
	
	/**
	 * �J�n�� �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validStartDate(List<ValidationFailure> errors) {
        // �J�n�����̓`�F�b�N
        ValidationChain valStartDate = new ValidationChain("information.input.startDate", this.startDate);
		// ���t�����`�F�b�N
        valStartDate.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valStartDate.validate(errors);
	}
	
	/**
	 * �I���� �o���f�[�V����<br/>
	 * �E���t�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validEndDate(List<ValidationFailure> errors) {
        // �I�������̓`�F�b�N
        ValidationChain valEndDate = new ValidationChain("information.input.endDate", this.endDate);
		// ���t�����`�F�b�N
        valEndDate.addValidation(new ValidDateValidation("yyyy/MM/dd"));
        valEndDate.validate(errors);
	}
	
	/**
	 * ���m�点��� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInformationType(List<ValidationFailure> errors) {
        // ���m�点��ʓ��̓`�F�b�N
        ValidationChain valType = new ValidationChain("information.input.informationType", this.informationType);
		// �K�{�`�F�b�N
        valType.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        valType.addValidation(new CodeLookupValidation(this.codeLookupManager,"information_type"));
        valType.validate(errors);
	}

	/**
	 * ���J�Ώۋ敪 �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�p�^�[���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validDspFlg(List<ValidationFailure> errors) {
        // ���J�Ώۋ敪���̓`�F�b�N
        ValidationChain valDspFlg = new ValidationChain("information.input.dspFlg", this.dspFlg);
		// �K�{�`�F�b�N
        valDspFlg.addValidation(new NullOrEmptyCheckValidation());
        // �p�^�[���`�F�b�N
        valDspFlg.addValidation(new CodeLookupValidation(this.codeLookupManager,"information_dspFlg"));
        valDspFlg.validate(errors);
	}

	/**
	 * ������� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validUserId(List<ValidationFailure> errors) {
        // ������̓`�F�b�N
        ValidationChain valUserId = new ValidationChain("information.input.userId", this.userId);
		// �K�{�`�F�b�N
        valUserId.addValidation(new NullOrEmptyCheckValidation());
        valUserId.validate(errors);
	}

	/**
	 * �^�C�g�� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validTitle(List<ValidationFailure> errors) {
        // �^�C�g�����̓`�F�b�N
        ValidationChain valTitle = new ValidationChain("information.input.title", this.title);
		// �K�{�`�F�b�N
        valTitle.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.title", 200)));
        valTitle.validate(errors);
	}

	/**
	 * ���O�C��ID �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInformationMsg(List<ValidationFailure> errors) {
        // ���m�点���e���̓`�F�b�N
        ValidationChain valInformationMsg = new ValidationChain("information.input.informationMsg", this.informationMsg);
		// �K�{�`�F�b�N
        valInformationMsg.addValidation(new NullOrEmptyCheckValidation());
        // �����`�F�b�N
        valInformationMsg.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.informationMsg", 1000)));
        valInformationMsg.validate(errors);
	}

	/**
	 * �����N�� URL �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�p���L���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validInputUrl(List<ValidationFailure> errors) {
        // �����N�� URL ���̓`�F�b�N
        ValidationChain valUrl = new ValidationChain("information.input.url", this.url);
        // �����`�F�b�N
        valUrl.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.input.url", 255)));
        // ���p�p���L���`�F�b�N
        valUrl.addValidation(new AsciiOnlyValidation());
        valUrl.validate(errors);
	}



	/**
	 * command �p�����[�^��ݒ肷��B<br/>
	 * <br/>
	 * @param command command �p�����[�^
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command �p�����[�^���擾����B<br/>
	 * <br/>
	 * @return command �p�����[�^
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * ���m�点�ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param informationNo ���m�点�ԍ�
	 */
	public String getInformationNo() {
		return informationNo;
	}

	/**
	 * ���m�点�ԍ����擾����B<br/>
	 * <br/>
	 * @return ���m�点�ԍ�
	 */
	public void setInformationNo(String informationNo) {
		this.informationNo = informationNo;
	}

	/**
	 * ���m�点��ʂ�ݒ肷��B<br/>
	 * <br/>
	 * @param informationType ���m�点���
	 */
	public String getInformationType() {
		return informationType;
	}

	/**
	 * ���m�点��ʂ��擾����B<br/>
	 * <br/>
	 * @return ���m�点���
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	/**
	 * �^�C�g����ݒ肷��B<br/>
	 * <br/>
	 * @param title �^�C�g��
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * �^�C�g�����擾����B<br/>
	 * <br/>
	 * @return �^�C�g��
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �\���J�n����ݒ肷��B<br/>
	 * <br/>
	 * @param startDate �\���J�n��
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * �\���J�n�����擾����B<br/>
	 * <br/>
	 * @return �\���J�n��
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * �\���I������ݒ肷��B<br/>
	 * <br/>
	 * @param endDate �\���I����
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * �\���I�������擾����B<br/>
	 * <br/>
	 * @return �\���I����
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * �����N��URL��ݒ肷��B<br/>
	 * <br/>
	 * @param url �����N��URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * �����N��URL���擾����B<br/>
	 * <br/>
	 * @return �����N��URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ���J�Ώۋ敪��ݒ肷��B<br/>
	 * <br/>
	 * @param dspFlg ���J�Ώۋ敪
	 */
	public String getDspFlg() {
		return dspFlg;
	}

	/**
	 * ���J�Ώۋ敪���擾����B<br/>
	 * <br/>
	 * @return ���J�Ώۋ敪
	 */
	public void setDspFlg(String dspFlg) {
		this.dspFlg = dspFlg;
	}

	/**
	 * ���m�点���e��ݒ肷��B<br/>
	 * <br/>
	 * @param informationMsg ���m�点���e
	 */
	public String getInformationMsg() {
		return informationMsg;
	}

	/**
	 * ���m�点���e���擾����B<br/>
	 * <br/>
	 * @return ���m�点���e
	 */
	public void setInformationMsg(String informationMsg) {
		this.informationMsg = informationMsg;
	}

	/**
	 * �������ݒ肷��B<br/>
	 * <br/>
	 * @param memberName �����
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * ��������擾����B<br/>
	 * <br/>
	 * @return �����
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * ���[�����M�t���O ��ݒ肷��B<br/>
	 * <br/>
	 * @param mailFlg ���[�����M�t���O 
	 */
	public String getMailFlg() {
		return mailFlg;
	}

	/**
	 * ���[�����M�t���O ���擾����B<br/>
	 * <br/>
	 * @return ���[�����M�t���O 
	 */
	public void setMailFlg(String mailFlg) {
		this.mailFlg = mailFlg;
	}

	/**
	 * �Ώۉ����ݒ肷��B<br/>
	 * <br/>
	 * @param userId �Ώۉ��
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * �Ώۉ�����擾����B<br/>
	 * <br/>
	 * @return �Ώۉ��
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 * @param information Information�@�����������A���m�点���Ǘ��p�o���[�I�u�W�F�N�g
	 * @param informationTarget InformationTarget �������������m�点���J����Ǘ��p�o���[�I�u�W�F�N�g
	 * @param mypageUserInterface MypageUserInterface �����������}�C�y�[�W������Ǘ��p�o���[�I�u�W�F�N�g
	 */
	public void setDefaultData(Information information, InformationTarget informationTarget, MypageUserInterface mypageUserInterface){

		// �o���[�I�u�W�F�N�g�Ɋi�[����Ă���p�X���[�h�̓n�b�V���l�Ȃ̂� Form �ɂ͐ݒ肵�Ȃ��B
		// �p�X���[�h�̓��͂͐V�K�o�^���̂ݕK�{�ŁA�X�V�������͔C�ӂ̓��͂ƂȂ�B
		// �X�V�����Ńp�X���[�h�̓��͂��s���Ȃ��ꍇ�A�p�X���[�h�̍X�V�͍s��Ȃ��B

		// ���m�点�ԍ� ��ݒ�
		this.informationNo = (String) information.getInformationNo();
		// ���m�点��� ��ݒ�
		this.informationType = information.getInformationType();
		// �^�C�g����ݒ�
		this.title = information.getTitle();
		SimpleDateFormat formatSEDate = new SimpleDateFormat("yyyy/MM/dd"); 
		// �\���J�n����ݒ�
		if (information.getStartDate() != null) {
			this.startDate = formatSEDate.format(information.getStartDate());
		}
		
		// �\���I������ݒ�
		if (information.getEndDate() != null) {
			this.endDate = formatSEDate.format(information.getEndDate());
		}
		
		// �����N��URL��ݒ�
		this.url = information.getUrl();
		// ���J�Ώۋ敪 ��ݒ�
		this.dspFlg = information.getDspFlg();
		// ����� ��ݒ�
		if (!StringUtils.isEmpty(mypageUserInterface.getMemberLname())) {
			this.memberName = mypageUserInterface.getMemberLname() + " ";
		}
		if (!StringUtils.isEmpty(mypageUserInterface.getMemberFname())) {
			this.memberName += mypageUserInterface.getMemberFname();
		}
		// ���m�点���e��ݒ�
		this.informationMsg = information.getInformationMsg();
		// �Ώۉ��ID��ݒ�
		this.userId = informationTarget.getUserId();
		
	}
	


	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * @param information �l��ݒ肷�邨�m�点���̃o���[�I�u�W�F�N�g
	 * 
	 */
	public void copyToInformation(Information information, String editUserId) {
		
		// ���m�点�ԍ� ��ݒ�
		if (!StringValidateUtil.isEmpty(this.informationNo)) {
			information.setInformationNo(this.informationNo);
		}
		
		// ���m�点��� ��ݒ�
		information.setInformationType(this.informationType);

		// �^�C�g����ݒ�
		information.setTitle(this.title);

		// �\���J�n����ݒ�
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (!StringValidateUtil.isEmpty(this.startDate)) {
			try {
				information.setStartDate(sdf.parse(this.startDate));
			} catch (ParseException e) {
				// �o���f�[�V�����ň��S����S�ۂ��Ă���̂ŁA���̃^�C�~���O�ŃG���[���������鎖�͖������A
				// �ꉞ�A�x�������O�o�͂���B
				log.warn("date format error. (" + this.startDate + ")");
				information.setStartDate(null);
			}
		} else {
			information.setStartDate(null);
		}

		// �\���I������ݒ�
		if (!StringValidateUtil.isEmpty(this.endDate)) {
			SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try {
				information.setEndDate(sdfEnd.parse(this.endDate + " 23:59:59"));
			} catch (ParseException e) {
				// �o���f�[�V�����ň��S����S�ۂ��Ă���̂ŁA���̃^�C�~���O�ŃG���[���������鎖�͖������A
				// �ꉞ�A�x�������O�o�͂���B
				log.warn("date format error. (" + this.endDate + ")");
				information.setEndDate(null);;
			}
		} else {
			information.setEndDate(null);
		}

		// �����N��URL��ݒ�
		information.setUrl(this.url);
		
		// ���J�Ώۋ敪��ݒ�
		information.setDspFlg(this.dspFlg);
		
		// ���m�点���e��ݒ�
		information.setInformationMsg(this.informationMsg);

		// �X�V���t��ݒ�
		information.setUpdDate(new Date());;

		// �X�V�S���҂�ݒ�
		information.setUpdUserId(editUserId);

	}

	
	
	/**
	 * ���m�点���J����̃o���[�I�u�W�F�N�g���쐬����B<br/>
	 * <br/>
	 * @param informationTargets�@
	 * @return ���m�点���J����o���[�I�u�W�F�N�g�̔z��
	 */
	public void copyToInformationTarget(InformationTarget[] informationTargets){
		
		informationTargets[0].setInformationNo(this.informationNo);
		informationTargets[0].setUserId(this.userId);

	}
	
	/**
	 * ���m�点���̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAInformation �ȊO���g�p�����ꍇ�A���̃��\�b�h��
	 * �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria(){
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���m�点�ԍ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B 
		criteria.addWhereClause("informationNo", this.informationNo);

		return criteria;
	}


}
