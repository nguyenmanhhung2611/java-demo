package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ���{�X�ւ��񋟂���X�֔ԍ�CSV �ɂ��}�X�^�[�f�[�^�X�V���ɉ��H�σf�[�^���Ǘ�����t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.19	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class AddressCsvForm implements Validateable {

	/** �s���{��CD */
	private String prefCd;
	
	/** �s�撬��CD */
	private String addressCd;

	/** �s�撬���� */
	private String addressName;

	/** �X�֔ԍ� */
	private String zip;

	/** �G���A������\���t���O �i0:�ʏ�A1:�G���A�����Ŕ�\���j */
	private String areaNotDsp = "0";



	/**
	 * �X�V�̕\��<br/>
	 * ���{�X�֒񋟂� CSV �t�@�C���ł́A14 ���ڂ́u�X�V�̕\���v���Ӗ�����B<br/>
	 * ���̒l���u2�v�̏ꍇ�A�u�p�~�f�[�^�v���Ӗ����邪�A�S���f�[�^�̏ꍇ�u�p�~�f�[�^�v�͑��݂��Ȃ��Ǝv����
	 * ���A�ꉞ�`�F�b�N�ΏۂƂ��Ă����B<br/>
	 */
	private String updFlg;

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;



	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected AddressCsvForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected AddressCsvForm(LengthValidationUtils lengthUtils){
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

		// �s���{��CD�@���p�����Q���Œ�A�K�{
		validPrefCd(errors);
		// �s�撬��CD�@���p�����T���Œ�A�K�{
		validAddressCd(errors);
		// �s�撬�����@�K�{
		validAddressName(errors);
		// �X�֔ԍ��@���p����7���Œ�A�K�{
		validZip(errors);

		return startSize == errors.size();
	}

	/**
	 * �s���{��CD �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�Q���Œ�`�F�b�N
	 * �E���p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// �s���{��CD�@���p�����Q���Œ�A�K�{
		ValidationChain valid = new ValidationChain("addressCsv.prefCd", this.prefCd);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.prefCd", 2)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * �s�撬��CD �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�T���Œ�`�F�b�N
	 * �E���p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddressCd(List<ValidationFailure> errors) {
		// �s�撬��CD�@���p�����T���Œ�A�K�{
		ValidationChain valid = new ValidationChain("addressCsv.addressCd", this.addressCd);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.addressCd", 5)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * �s�撬���� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validAddressName(List<ValidationFailure> errors) {
		// �s�撬�����@�K�{�A����
		ValidationChain valid = new ValidationChain("addressCsv.addressName", this.addressName);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("addressCsv.addressName", 60)));
		valid.validate(errors);
	}

	/**
	 * �X�֔ԍ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N
	 * �E7���Œ�`�F�b�N
	 * �E���p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validZip(List<ValidationFailure> errors) {
		// �X�֔ԍ��@���p����7���Œ�A�K�{
		ValidationChain valid = new ValidationChain("addressCsv.zip", this.zip);
		valid.addValidation(new NullOrEmptyCheckValidation());
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("addressCsv.zip", 7)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 * �s���{��CD ���擾����B<br/>
	 * <br/>
	 * @return �s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * �s���{��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefCd�@�s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * �s�撬��CD ���擾����B<br/>
	 * <br/>
	 * @return �s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * �s�撬��CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param addressCd�@�s�撬��CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
	 * �s�撬�������擾����B<br/>
	 * <br/>
	 * @return �s�撬����
	 */
	public String getAddressName() {
		return addressName;
	}

	/**
	 * �s�撬������ݒ肷��B<br/>
	 * <br/>
	 * @param addressName �s�撬����
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * �X�֔ԍ����擾����B<br/>
	 * <br/>
	 * @return �X�֔ԍ�
	 */
	public String getZip() {
		return zip;
	}
	
	/**
	 * �X�֔ԍ���ݒ肷��B<br/>
	 * <br/>
	 * @param zip�@�X�֔ԍ�
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * �u�X�V�̕\���v���擾����B<br/>
	 * <br/>
	 * @return �u�X�V�̕\���v
	 */
	public String getUpdFlg() {
		return updFlg;
	}

	/**
	 * �u�X�V�̕\���v��ݒ肷��B<br/>
	 * <br/>
	 * @param updFlg �u�X�V�̕\���v
	 */
	public void setUpdFlg(String updFlg) {
		this.updFlg = updFlg;
	}

	/**
	 * �G���A������\���t���O���擾����B<br/>
	 * <br/>
	 * @return �G���A������\���t���O�@�i0:�ʏ�A1:�G���A���Ŕ�\���j
	 */
	public String getAreaNotDsp() {
		return areaNotDsp;
	}

	/**
	 * �G���A������\���t���O��ݒ肷��B<br/>
	 * <br/>
	 * @param �G���A������\���t���O�@�i0:�ʏ�A1:�G���A���Ŕ�\���j
	 */
	public void setAreaNotDsp(String areaNotDsp) {
		this.areaNotDsp = areaNotDsp;
	}
	
	
}
