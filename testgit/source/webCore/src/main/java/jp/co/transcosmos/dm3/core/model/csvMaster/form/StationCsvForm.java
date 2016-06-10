package jp.co.transcosmos.dm3.core.model.csvMaster.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.LengthValidator;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NumberValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * ���@�������񋟂���w���CSV �ɂ��}�X�^�[�f�[�^�X�V���ɉ��H�σf�[�^���Ǘ�����t�H�[��.
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.06	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * 
 */
public class StationCsvForm implements Validateable {

	/** �S�����CD */
	private String rrCd;
	/** �S����Е\���� �iCSV �t�@�C���ɂ͑��݂��Ȃ������j */
	private Integer rrSortOrder;
	/** �H��CD */
	private String routeCd;
	/** �S����Ж� */
	private String rrName;
	/** �H�������ʕt�� */
	private String routeNameFull;
	/** �H�������ʕt���A�S����Еt�� */
	private String routeNameRr;
	/** �H���\����  �iCSV �t�@�C���ɂ͑��݂��Ȃ������j */
	private Integer routeSortOrder;
	/** �wCD */
	private String stationCd;
	/** �w�����ʂ��� */
	private String stationNameFull;
	/** �s���{��CD */
	private String prefCd;
	/** ��ԏ�1 */
	private String stationRouteDispOrder;
	/** �H���� */
	private String routeName; 

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	
	
	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected StationCsvForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected StationCsvForm(LengthValidationUtils lengthUtils){
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

		// �H���R�[�h�@���p�p��7���Œ�
		validRouteCd(errors);

		// �S����Ж��@80�����ȓ�
		validRrName(errors);

		// �t���H�����@80�����ȓ�
		validRouteNameFull(errors);

		// �S����Еt���H�����@80�����ȓ�
		validRouteNameRr(errors);

		// �w�R�[�h�@���p�p��5���Œ�
		validStationCd(errors);

		// �w���@80�����ȓ�
		validStationNameFull(errors);

		// �w�E�H�� ���я�
		validStationRouteDispOrder(errors);

		// �s���{���R�[�h�@���p����2���Œ�
		validPrefCd(errors);

		return startSize == errors.size();
	}

	/**
	 * �H���R�[�h �o���f�[�V����<br/>
	 * �E7���Œ�`�F�b�N
	 * �E���p�p���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRouteCd(List<ValidationFailure> errors) {
		// �H���R�[�h�@���p�p��7���Œ�
		ValidationChain valid = new ValidationChain("stationCsv.routeCd", this.routeCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.routeCd", 7)));
		valid.addValidation(new AlphanumericOnlyValidation());
		valid.validate(errors);
	}

	/**
	 * �S����Ж� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRrName(List<ValidationFailure> errors) {
		// �S����Ж��@80�����ȓ�
		ValidationChain valid = new ValidationChain("stationCsv.rrName", this.rrName);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.rrName", 80)));
		valid.validate(errors);
	}

	/**
	 * �t���H���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRouteNameFull(List<ValidationFailure> errors) {
		// �t���H�����@80�����ȓ�
		ValidationChain valid = new ValidationChain("stationCsv.routeNameFull", this.routeNameFull);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.routeNameFull", 80)));
		valid.validate(errors);
	}

	/**
	 * �S����Еt���H���� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validRouteNameRr(List<ValidationFailure> errors) {
		// �S����Еt���H�����@80�����ȓ�
		ValidationChain valid = new ValidationChain("stationCsv.routeNameRr", this.routeNameRr);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.routeNameRr", 80)));
		valid.validate(errors);
	}

	/**
	 * �w�R�[�h �o���f�[�V����<br/>
	 * �E5���Œ�`�F�b�N
	 * �E���p�p���`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validStationCd(List<ValidationFailure> errors) {
		// �w�R�[�h�@���p�p��5���Œ�
		ValidationChain valid = new ValidationChain("stationCsv.stationCd", this.stationCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.stationCd", 5)));
		valid.addValidation(new AlphanumericOnlyValidation());
		valid.validate(errors);
	}

	/**
	 *  �w�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validStationNameFull(List<ValidationFailure> errors) {
		// �w���@80�����ȓ�
		ValidationChain valid = new ValidationChain("stationCsv.stationName", this.stationNameFull);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.stationName", 80)));
		valid.validate(errors);
	}

	/**
	 *  �w�E�H�� �o���f�[�V����<br/>
	 * �E�����`�F�b�N
	 * �E���p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validStationRouteDispOrder(List<ValidationFailure> errors) {
		// �w�E�H�� ���я�
		ValidationChain valid = new ValidationChain("stationCsv.srSortOrder", this.stationRouteDispOrder);
		valid.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("stationCsv.srSortOrder", 5)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}

	/**
	 *  �s���{���R�[�h �o���f�[�V����<br/>
	 * �E2���Œ�`�F�b�N
	 * �E���p�����`�F�b�N
	 * <br/>
	 * @param errors �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void validPrefCd(List<ValidationFailure> errors) {
		// �s���{���R�[�h�@���p����2���Œ�
		ValidationChain valid = new ValidationChain("stationCsv.prefCd", this.prefCd);
		valid.addValidation(new LengthValidator(this.lengthUtils.getLength("stationCsv.prefCd", 2)));
		valid.addValidation(new NumberValidation());
		valid.validate(errors);
	}


	/**
	 * �S�����CD ���擾����B<br/>
	 * <br/>
	 * @return �S�����CD
	 */
	public String getRrCd() {
		return rrCd;
	}

	/**
	 * �S�����CD ��ݒ肷��B<br/>
	 * <br/>
	 * @param rrCd
	 */
	public void setRrCd(String rrCd) {
		this.rrCd = rrCd;
	}

	/**
	 * �S����Е\�������擾����B<br/>
	 * <br/>
	 * @return�@�S����Е\����
	 */
	public Integer getRrSortOrder() {
		return rrSortOrder;
	}

	/**
	 * �S����Е\������ݒ肷��B<br/>
	 * <br/>
	 * @param rrSortOrder�@�S����Е\����
	 */
	public void setRrSortOrder(Integer rrSortOrder) {
		this.rrSortOrder = rrSortOrder;
	}

	/**
	 * �H��CD ���擾����B<br/>
	 * <br/>
	 * @return �H��CD
	 */
	public String getRouteCd() {
		return routeCd;
	}

	/**
	 * �H��CD ��ݒ肷��B<br/>
	 * @param routeCd �H��CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
	 * �S����Ж����擾����B<br/>
	 * <br/>
	 * @return �S����Ж�
	 */
	public String getRrName() {
		return rrName;
	}

	/**
	 * �S����Ж���ݒ肷��B<br/>
	 * <br/>
	 * @param rrName�@�S����Ж�
	 */
	public void setRrName(String rrName) {
		this.rrName = rrName;
	}

	/**
	 * �H�������ʕt�����擾����B<br/>
	 * <br/>
	 * @return �H�������ʕt��
	 */
	public String getRouteNameFull() {
		return routeNameFull;
	}

	/**
	 * �H�������ʕt����ݒ肷��B<br/>
	 * <br/>
	 * @param routeNameFull �H�������ʕt��
	 */
	public void setRouteNameFull(String routeNameFull) {
		this.routeNameFull = routeNameFull;
	}

	/**
	 * �u�H�������ʕt���A�S����Еt���v���擾����B<br/>
	 * <br/>
	 * @return �u�H�������ʕt���A�S����Еt���v
	 */
	public String getRouteNameRr() {
		return routeNameRr;
	}

	/**
	 * �u�H�������ʕt���A�S����Еt���v��ݒ肷��B<br/>
	 * <br/>
	 * @param routeNameRr �u�H�������ʕt���A�S����Еt���v
	 */
	public void setRouteNameRr(String routeNameRr) {
		this.routeNameRr = routeNameRr;
	}

	/**
	 * �H���\�������擾����B<br/>
	 * <br/>
	 * @return �H���\����
	 */
	public Integer getRouteSortOrder() {
		return routeSortOrder;
	}

	/**
	 * �H���\������ݒ肷��B<br/>
	 * <br/>
	 * @param routeSortOrder �H���\����
	 */
	public void setRouteSortOrder(Integer routeSortOrder) {
		this.routeSortOrder = routeSortOrder;
	}

	/**
	 * �wCD ���擾����B<br/>
	 * <br/>
	 * @return �wCD
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * �wCD ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationCd �wCD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * �u�w�����ʂ���v���擾����B<br/>
	 * <br/>
	 * @return �u�w�����ʂ���v
	 */
	public String getStationNameFull() {
		return stationNameFull;
	}

	/**
	 *  �u�w�����ʂ���v��ݒ肷��B<br/>
	 *  <br/>
	 * @param stationNameFull �u�w�����ʂ���v
	 */
	public void setStationNameFull(String stationNameFull) {
		this.stationNameFull = stationNameFull;
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
	 * @param prefCd �s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * ��ԏ�1 ���擾����B<br/>
	 * <br/>
	 * @return ��ԏ�1
	 */
	public String getStationRouteDispOrder() {
		return stationRouteDispOrder;
	}

	/**
	 * ��ԏ�1 ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationRouteDispOrder ��ԏ�1
	 */
	public void setStationRouteDispOrder(String stationRouteDispOrder) {
		this.stationRouteDispOrder = stationRouteDispOrder;
	}

	/**
	 * �H�������擾����B<br/>
	 * <br/>
	 * @return �H����
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * �H������ݒ肷��B<br/>
	 * <br/>
	 * @param routeName �H����
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

}
