package jp.co.transcosmos.dm3.core.model.housing.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.validation.FileExtensionValidation;
import jp.co.transcosmos.dm3.core.validation.LineAdapter;
import jp.co.transcosmos.dm3.core.validation.TraversalValidation;
import jp.co.transcosmos.dm3.core.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.ArrayMemberValidation;
import jp.co.transcosmos.dm3.validation.AsciiOnlyValidation;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumericValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * �����摜��񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * ���̃t�H�[���́A�A�b�v���[�h��̊m�F��ʁA����сA�t�@�C���ȊO�̑����X�V�p�B<br/>
 * �t�@�C���A�b�v���[�h�͕ʂ̃t�H�[���ōs�����B�imodel ���̃p�b�P�[�W�ł͊Ǘ����Ȃ��B�j<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA �t���[�����[�N���񋟂��� Validateable
 * �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 * 
 */
public class HousingImgForm {

	/** �V�X�e������CD �i�X�V�p�j */
	private String sysHousingCd;
	/** ���t�H���_���@�i���t�����j */
	private String tempDate;

	/** �摜�^�C�v �i00:�Ԏ�}�^01:�O�ρ^99:���̑��j*/
	private String imageType[];
	/** �}��  �i�X�V�p�j*/
	private String divNo[];
	/** �\���� */
	private String sortOrder[];
	/** �t�@�C����  �i�V�K�o�^�p�j */
	private String fileName[];
	/** �L���v�V���� */
	private String caption[];
	/** �R�����g */
	private String imgComment[];
	/** �폜�t���O �i�X�V�p�j �폜���� 1 ��ݒ肷��B */
	private String delFlg[];

	/** ���摜�^�C�v �i�摜�^�C�v�͎�L�[�\���v�f�̈�Ȃ̂ŁA�ύX�O�̒l�������ƍX�V�ł��Ȃ��B �j*/
	private String oldImageType[];
	
	// �u���C���摜�t���O�v�A�u�c���E�����t���O�v�͎����X�V���ڂȂ̂� Form �ł͖���`
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;



	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingImgForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected HousingImgForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}


	/**
	 * �����œn���ꂽ�����摜���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �����œn���ꂽ idx �ɊY������s�� Form �l��ݒ肷��B<br/>
	 * �����摜���̍X�V�́A��L�[�l�̍X�V���K�v�ȈׁA�ʏ�� update() �ł͍X�V�ł��Ȃ��B<br/>
	 * ���̃��\�b�h�́A��{�I�ɐV�K�o�^���̎g�p��z�肵�Ă���B<br/>
	 * <br/>
	 * @param housingImageInfo �����摜���
	 * @param idx �s�ʒu
	 */
	public void copyToHousingImageInfo(HousingImageInfo housingImageInfo, int idx){

		// �}�Ԃ́A�Ăяo�������ŁA�V�X�e�������ԍ��A�摜�^�C�v���ŘA�Ԃ�ݒ肷��B
		// �p�X���́A�摜�t�@�C�����A������{��񓙂����ɁA�Ăяo�����Œl��ݒ肷��B
		// ���C���摜�t���O�́A�o�b�`�X�V�I�ɒǉ��E�ύX�E�폜��ɓZ�߂ď�������B
		// �c���E�����t���O�́A�Ăяo�����Ŏ��ۂ̉摜�t�@�C����������擾���Đݒ肷��B

		// �V�X�e������CD
		housingImageInfo.setSysHousingCd(this.sysHousingCd);

		// �摜�^�C�v
		housingImageInfo.setImageType(this.imageType[idx]);

		// �\����
		if (!StringValidateUtil.isEmpty(this.sortOrder[idx])){
			housingImageInfo.setSortOrder(Integer.valueOf(this.sortOrder[idx]));
		}

		// �t�@�C����
		// ���t�H���_�̒i�K�ŁA�V�[�P���X�P�O���̃t�@�C�����Ƀ��l�[������Ă��鎖�B
		housingImageInfo.setFileName(this.fileName[idx]);

		// �L���v�V����
		housingImageInfo.setCaption(this.caption[idx]);

		// �R�����g
		housingImageInfo.setImgComment(this.imgComment[idx]);

	}
		

	
	/**
	 * �����摜���̍X�V UpdateExpression �𐶐�����B<br/>
	 * <br/>
	 * �����摜���́A��L�[�l���X�V����ׁA�ʏ�� update() ���\�b�h�ł͍X�V�ł��Ȃ��B<br/>
	 * ���̃��\�b�h�����A����@UpdateExpression�@���g�p���čX�V����B<br/>
	 * <br/>
	 * @param idx �s�ʒu
	 * 
	 * @return �X�V�^�C���X�^���v UPDATE �p�@UpdateExpression
	 */
	public UpdateExpression[] buildUpdateExpression(int idx){

       // �V�X�e�������R�[�h�A�p�X���A�t�@�C�����͍X�V�Ώۂɂ͂Ȃ�Ȃ��̂Őݒ肵�Ȃ��B
       // ���C���摜�t���O���A�ʂ̏����Ńo�b�`�I�ɍX�V����̂Ōʂɂ͍X�V���Ȃ��B
       // �c���E�����t���O���A�摜�t�@�C�����̂��X�V����Ȃ��̂ŕύX�͔������Ȃ��B

       return new UpdateExpression[] {new UpdateValue("imageType", this.imageType[idx]),
       							      new UpdateValue("divNo", this.divNo[idx]),
        							  new UpdateValue("sortOrder", this.sortOrder[idx]),
        							  new UpdateValue("caption", this.caption[idx]),
        							  new UpdateValue("imgComment", this.imgComment[idx])};

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
	 * 
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validate(List<ValidationFailure> errors, String mode) {

		int startSize = errors.size();

		// �V�X�e������CD �̃o���f�[�V�����́ACommand ���őΉ�����̂� From ���ł͎������Ȃ��B
		// �i�X�V���Ƀp�����[�^���������Ă���ꍇ��A�Y���f�[�^�������ꍇ�͗�O���X���[����B�j

		// tempDate �̃o���f�[�V�����́ACommand ���őΉ�����̂� From ���ł͎������Ȃ��B
		// �i�V�K�o�^���Ƀp�����[�^���������Ă���ꍇ�͗�O���X���[����B�j


		boolean inputLineChk = false;		// ���͍s�̑��݃`�F�b�N�t���O

		for (int lineNo = 0; lineNo < imageType.length; ++lineNo){
			// �P�s�P�ʂŉ�������̓��͂��������ꍇ�̂݃`�F�b�N���s���B
			// �S�Ẵf�[�^�������͂̏ꍇ�̓o���f�[�V�������s��Ȃ��B
			if (isLineInput(lineNo)) {
				validate(errors, mode, lineNo);
				inputLineChk = true; 
			}
		}

		// ���͂����s���P���������ꍇ�A�o���f�[�V�����G���[
		if (!inputLineChk) {

			// TODO �G���[���b�Z�[�W�͕ʓr�����B
			
		}

		return (startSize == errors.size());
	}

	/**
	 * �s���̃o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA
	 * �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒���
	 * ���鎖�B<br/>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 * 
	 * @return ���펞 true�A�G���[�� false
	 */
	protected boolean validate(List<ValidationFailure> errors, String mode, int lineNo) {

		int startSize = errors.size();

		// �t�@�C����
		validFileName(errors, mode, lineNo);
		// �\����
		validSortOrder(errors, mode, lineNo);
		// �摜�^�C�v
		validImageType(errors, mode, lineNo);
		// �L���v�V����
		validCaption(errors, mode, lineNo);
		// �R�����g
		validImgComment(errors, mode, lineNo);
		// �폜�t���O
		validDelFlg(errors, mode, lineNo);
		// �}��
		validDivNo(errors, mode, lineNo);

		return (startSize == errors.size());
	}

	

	/**
	 * �t�@�C�����o���f�[�V����<br/>
	 * <ul>
	 *   <li>�s�P�ʂœ��͂��������ꍇ�͕K�{</li>
	 *   <li>���p�p���L�������`�F�b�N</li>
	 *   <li>�f�B���N�g���g���o�[�T���`�F�b�N</li>
	 *   <li>�g���q�`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validFileName(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.fileName";
		ValidationChain valid = new ValidationChain(label, this.fileName[lineNo]);

		// �s�P�ʂŉ�������̓��͂��������ꍇ�̂ݎ��s�����̂ŁA���ʂɕK�{�`�F�b�N���s��
		valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));

		// ���p�p���L�������`�F�b�N
		valid.addValidation(new LineAdapter(new AsciiOnlyValidation(),lineNo));

		// �f�B���N�g���g���o�[�T���`�F�b�N
		valid.addValidation(new LineAdapter(new TraversalValidation(),lineNo));

		// �t�@�C���g���q�̃`�F�b�N
		valid.addValidation(new LineAdapter(new FileExtensionValidation(new String[]{".jpg",".jpeg"}),lineNo));

		// �t�@�C���T�C�Y���́A���t�H���_�ɃA�b�v���鎞�_�Ń`�F�b�N�ςȂ͂��Ȃ̂ŁA���̃^�C�~���O�ł̓`�F�b�N���Ȃ��B
		// �t�@�C���g���q�̃`�F�b�N�͔O�̂��߁B�@�i�p�����[�^��₎��͕����t�@�C�������݂��Ȃ��͂��Ȃ̂ŁA�{����
		// ���̃^�C�~���O�ł̃`�F�b�N�͕s�v�B�j

		valid.validate(errors);
	}

	/**
	 * �\�����o���f�[�V����<br/>
	 * <ul>
	 *   <li>���p�����`�F�b�N</li>
	 *   <li>�ő啶�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validSortOrder(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.sortOrder";
		ValidationChain valid = new ValidationChain(label, this.sortOrder[lineNo]);

		// ���p�����`�F�b�N
		valid.addValidation(new LineAdapter(new NumericValidation(),lineNo));
		// �ő包���`�F�b�N
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 3)),lineNo));

		valid.validate(errors);
	}

	/**
	 * �摜�^�C�v�o���f�[�V����<br/>
	 * <ul>
	 *   <li>�s�P�ʂœ��͂��������ꍇ�͕K�{</li>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validImageType(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.imageType";
		ValidationChain valid = new ValidationChain(label, this.imageType);

		// �s�P�ʂŉ�������̓��͂��������ꍇ�̂ݎ��s�����̂ŁA���ʂɕK�{�`�F�b�N���s��
		valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		// �p�^�[���`�F�b�N
		valid.addValidation(new LineAdapter(new CodeLookupValidation(this.codeLookupManager, "housingImageType"),lineNo));

		valid.validate(errors);
	}
	
	/**
	 * �L���v�V�����o���f�[�V����<br/>
	 * <ul>
	 *   <li>�ő啶�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validCaption(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.caption";
		ValidationChain valid = new ValidationChain(label, this.caption);
		
		// �ő包���`�F�b�N
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 20)),lineNo));

		valid.validate(errors);
	}

	/**
	 * �R�����g�o���f�[�V����<br/>
	 * <ul>
	 *   <li>�ő啶�����`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validImgComment(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.imgComment";
		ValidationChain valid = new ValidationChain(label, this.imgComment);
		
		// �ő包���`�F�b�N
		valid.addValidation(new LineAdapter(new MaxLengthValidation(this.lengthUtils.getLength(label, 200)),lineNo));

		valid.validate(errors);
	}

	
	/**
	 * �폜�t���O�o���f�[�V����<br/>
	 * <ul>
	 *   <li>�p�^�[���`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validDelFlg(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.delFlg";
		ValidationChain valid = new ValidationChain(label, this.delFlg);

		// �p�^�[���`�F�b�N
		valid.addValidation(new ArrayMemberValidation(new String[]{"1"}));

		valid.validate(errors);
	}

	/**
	 * �}�ԃo���f�[�V����<br/>
	 * <ul>
	 *   <li>�X�V�����̏ꍇ�A�K�{�`�F�b�N</li>
	 * </ul>
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode �������[�h ("insert" or "update")
	 * @param lineNo �s�ԍ�
	 */
	protected void validDivNo(List<ValidationFailure> errors, String mode, int lineNo){
		String label = "housingImg.input.divNo";
		ValidationChain valid = new ValidationChain(label, this.divNo);

		// �s�P�ʂŉ�������̓��͂��������ꍇ�̂ݎ��s�����̂ŁA�X�V�����̏ꍇ�A���ʂɕK�{�`�F�b�N���s��
		if (mode.equals("update")) {
			valid.addValidation(new LineAdapter(new NullOrEmptyCheckValidation(),lineNo));
		}

		// �s���Ȓl�ɉ�₂����ꍇ�A�X�V��폜�Ώۂ����������Ȃ̂ŁA����ȏ�̃`�F�b�N�͍s��Ȃ��B

		valid.validate(errors);
	}

	
	
	/**
	 * �s�f�[�^�̑S�Ă������͂����`�F�b�N����B<br/>
	 * <br/>
	 * @param lineNo �s�ԍ�
	 * @return true ���͂���Afalse ���͂Ȃ�
	 */
	protected boolean isLineInput(int lineNo) {
		
		// �摜�^�C�v�̓��͂����������H
		if (!StringValidateUtil.isEmpty(this.imageType[lineNo])) return true;

		// �}�Ԃ��p�����[�^�œn����Ă��邩�H
		if (!StringValidateUtil.isEmpty(this.divNo[lineNo])) return true;

		// �\�����̓��͂����������H
		if (!StringValidateUtil.isEmpty(this.sortOrder[lineNo])) return true;
		
		// �t�@�C�������p�����[�^�œn����Ă��邩�H
		if (!StringValidateUtil.isEmpty(this.fileName[lineNo])) return true;

		// �L���v�V�����̓��͂����������H
		if (!StringValidateUtil.isEmpty(this.caption[lineNo])) return true;
		
		// �R�����g�̓��͂����������H
		if (!StringValidateUtil.isEmpty(this.imgComment[lineNo])) return true;
		
		// �폜�t���O�̓��͂����������H
		if (!StringValidateUtil.isEmpty(this.delFlg[lineNo])) return true;

		return false;
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
	 * �T�u�t�H���_���Ŏg�p���Ă�����t���擾����B<br/>
	 * ���t���܂����鎖��z�肵�A���t�H���_�i�[���ɍ쐬�����T�u�z���_���i YYYYMMDD�j���擾����B<br/>
	 * <br/>
	 * @return �T�u�t�H���_���Ŏg�p���Ă�����t
	 */
	public String getTempDate() {
		return tempDate;
	}

	/**
	 * �T�u�t�H���_���Ŏg�p���Ă�����t��ݒ肷��B<br/>
	 * ���t���܂����鎖��z�肵�A���t�H���_�i�[���ɍ쐬�����T�u�z���_���i YYYYMMDD�j��ݒ肷��B<br/>
	 * <br/>
	 * @param tempDate �T�u�t�H���_���Ŏg�p���Ă�����t
	 */
	public void setTempDate(String tempDate) {
		this.tempDate = tempDate;
	}

	/**
	 * �摜�^�C�v �i00:�Ԏ�}�^01:�O�ρ^99:���̑��j���擾����B<br/>
	 * <br/>
	 * @return �摜�^�C�v
	 */
	public String[] getImageType() {
		return imageType;
	}

	/**
	 * �摜�^�C�v �i00:�Ԏ�}�^01:�O�ρ^99:���̑��j��ݒ肷��B<br/>
	 * <br/>
	 * @param imageType �摜�^�C�v
	 */
	public void setImageType(String[] imageType) {
		this.imageType = imageType;
	}

	/**
	 * �}�Ԃ��擾����B<br/>
	 * <br/>
	 * @return �}��
	 */
	public String[] getDivNo() {
		return divNo;
	}

	/**
	 * �}�Ԃ�ݒ肷��B<br/>
	 * <br/>
	 * @param divNo �}��
	 */
	public void setDivNo(String[] divNo) {
		this.divNo = divNo;
	}

	/**
	 * �\�������擾����B<br/>
	 * <br/>
	 * @return �\����
	 */
	public String[] getSortOrder() {
		return sortOrder;
	}

	/**
	 * �\������ݒ肷��B<br/>
	 * <br/>
	 * @param sortOrder �\����
	 */
	public void setSortOrder(String[] sortOrder) {
		this.sortOrder = sortOrder;
	}


	/**
	 * �t�@�C�������擾����B<br/>
	 * <br/>
	 * @return �t�@�C����
	 */
	public String[] getFileName() {
		return fileName;
	}

	/**
	 * �t�@�C������ݒ肷��B<br/>
	 * <br/>
	 * @param fileName �t�@�C����
	 */

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}



	/**
	 * �L���v�V�������擾����B<br/>
	 * <br/>
	 * @return �L���v�V����
	 */
	public String[] getCaption() {
		return caption;
	}

	/**
	 * �L���v�V������ݒ肷��B<br/>
	 * <br/>
	 * @param caption �L���v�V����
	 */
	public void setCaption(String[] caption) {
		this.caption = caption;
	}

	/**
	 * �R�����g���擾����B<br/>
	 * <br/>
	 * @return �R�����g
	 */
	public String[] getImgComment() {
		return imgComment;
	}

	/**
	 * �R�����g��ݒ肷��B<br/>
	 * <br/>
	 * @param imgComment �R�����g
	 */
	public void setImgComment(String[] imgComment) {
		this.imgComment = imgComment;
	}

	/**
	 * ���摜�^�C�v���擾����B<br/>
	 * <br/>
	 * @return ���摜�^�C�v
	 */
	public String[] getOldImageType() {
		return oldImageType;
	}

	/**
	 * ���摜�^�C�v���擾����B<br/>
	 * <br/>
	 * @param oldImageType ���摜�^�C�v
	 */
	public void setOldImageType(String[] oldImageType) {
		this.oldImageType = oldImageType;
	}

	/**
	 * �폜�t���O���擾����B<br/>
	 * <br/>
	 * @return �폜�t���O
	 */
	public String[] getDelFlg() {
		return delFlg;
	}

	/**
	 * �폜�t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param delFlg �폜�t���O
	 */
	public void setDelFlg(String[] delFlg) {
		this.delFlg = delFlg;
	}

}
