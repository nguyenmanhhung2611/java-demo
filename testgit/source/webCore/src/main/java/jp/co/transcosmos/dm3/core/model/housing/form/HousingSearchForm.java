package jp.co.transcosmos.dm3.core.model.housing.form;

import java.math.BigDecimal;

import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.form.PagingListForm;

/**
 * �������������̓��̓p�����[�^����p�t�H�[��.
 * <p>
 * 
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.03.11	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���� Form �̃o���f�[�V�����́A�g�p���郂�[�h�i�ǉ����� or �X�V�����j�ňقȂ�̂ŁA<br/>
 * �t���[�����[�N���񋟂��� Validateable �C���^�[�t�F�[�X�͎������Ă��Ȃ��B<br/>
 * �o���f�[�V�������s���̃p�����[�^���ʏ�ƈقȂ�̂Œ��ӂ��鎖�B<br/>
 * <br/>
 * ���W�y�[�W���̃N�G���[�����񂩂猟�������𐶐����鏈���ׁ̈A�v���p�e�B�͈ȉ��̒ʂ�̒�`�Ƃ��邱�ƁB<br/>
 * �E���̂� HousingFrom�AbuildingForm �̃t�B�[���h�����L���s�^���C�Y���A�擪�Ɂukey�v��t�^�������̂Ƃ��邱�ƁB<br/>
 * �E�v���~�e�B�u�^�͎g�p���Ȃ��B�Ή��������b�p�[�N���X�Œ�`���邱�ƁB
 * 
 */
public class HousingSearchForm extends PagingListForm<Housing> {

	// ���W�y�[�W���̃N�G���[�����񂩂猟�������𐶐����鏈���ׁ̈A
	// �v���p�e�B�͈ȉ��̒ʂ�̒�`�Ƃ���B
	// �E���̂� HousingFrom�AbuildingForm �̃t�B�[���h����
	// �@�L���s�^���C�Y���A�擪�Ɂukey�v��t�^�������̂Ƃ��邱�ƁB
	// �E�v���~�e�B�u�^�͎g�p���Ȃ��B�Ή��������b�p�[�N���X�Œ�`���邱�ƁB

	/** �����ԍ��i���������j */
	private String keyHousingCd;
	/** �\���p�������i���������j */
	private String keyDisplayHousingName;
	/** �s���{��CD�i���������j */
	private String keyPrefCd;
	/** �s�撬��CD�i���������j */
	private String keyAddressCd;

	/** ����CD�i���������j */
	private String keyRouteCd;
	/** �wCD�i���������j */
	private String keyStationCd;

	/** ����/���i�E�����i���������j */
	private Long keyPriceLower;
	/** ����/���i�E����i���������j */
	private Long keyPriceUpper;

	/** �y�n�ʐρE�����i���������j */
	private BigDecimal keyLandAreaLower;
	/** �y�n�ʐρE����i���������j */
	private BigDecimal keyLandAreaUpper;
	/** ��L�ʐρE�����i���������j */
	private BigDecimal keyPersonalAreaLower;
	/** ��L�ʐρE����i���������j */
	private BigDecimal keyPersonalAreaUpper;

	/** �������CD�i���������j */
	private String keyHousingKindCd;
	/** �Ԏ��CD�i���������j */
	private String keyLayoutCd;

	/** �����ʐρE�����i���������j */
	private BigDecimal keyBuildingAreaLower;
	/** �����ʐρE����i���������j */
	private BigDecimal keyBuildingAreaUpper;

	/** ����������CD �i���������j */
	private String keyPartSrchCd;

	/** �\�[�g���� */
	private String keyOrderType;

	/** �����ΏۃV�X�e������CD */
	private String sysHousingCd;

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected HousingSearchForm() {

	}

	/**
	 * �����ԍ��i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �����ԍ��i���������j
	 */
	public String getKeyHousingCd() {
		return keyHousingCd;
	}

	/**
	 * �����ԍ��i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyHousingCd �����ԍ��i���������j
	 */
	public void setKeyHousingCd(String keyHousingCd) {
		this.keyHousingCd = keyHousingCd;
	}

	/**
	 * �\���p�������i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �\���p������
	 */
	public String getKeyDisplayHousingName() {
		return keyDisplayHousingName;
	}

	/**
	 * �\���p�������i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyDisplayHousingName �\���p������
	 */
	public void setKeyDisplayHousingName(String keyDisplayHousingName) {
		this.keyDisplayHousingName = keyDisplayHousingName;
	}

	/**
	 * �s���{��CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �s���{��CD�i���������j
	 */
	public String getKeyPrefCd() {
		return keyPrefCd;
	}

	/**
	 * �s���{��CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPrefCd �s���{��CD�i���������j
	 */
	public void setKeyPrefCd(String keyPrefCd) {
		this.keyPrefCd = keyPrefCd;
	}

	/**
	 * �s�撬��CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �s�撬��CD �i���������j
	 */
	public String getKeyAddressCd() {
		return keyAddressCd;
	}

	/**
	 * �s�撬��CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyAddressCd �s�撬��CD �i���������j
	 */
	public void setKeyAddressCd(String keyAddressCd) {
		this.keyAddressCd = keyAddressCd;
	}

	/**
	 * ����CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����CD �i���������j
	 */
	public String getKeyRouteCd() {
		return keyRouteCd;
	}

	/**
	 * ����CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyRouteCd ����CD �i���������j
	 */
	public void setKeyRouteCd(String keyRouteCd) {
		this.keyRouteCd = keyRouteCd;
	}

	/**
	 * �wCD �i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �wCD�i���������j
	 */
	public String getKeyStationCd() {
		return keyStationCd;
	}

	/**
	 * �wCD �i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyStationCd �wCD �i���������j
	 */
	public void setKeyStationCd(String keyStationCd) {
		this.keyStationCd = keyStationCd;
	}

	/**
	 * ����/���i�E�����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����/���i�E���� �i���������j
	 */
	public Long getKeyPriceLower() {
		return keyPriceLower;
	}

	/**
	 * ����/���i�E�����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPriceLower ����/���i�E���� �i���������j
	 */
	public void setKeyPriceLower(Long keyPriceLower) {
		this.keyPriceLower = keyPriceLower;
	}

	/**
	 * ����/���i�E����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����/���i�E��� �i���������j
	 */
	public Long getKeyPriceUpper() {
		return keyPriceUpper;
	}

	/**
	 * ����/���i�E����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPriceUpper ����/���i�E��� �i���������j
	 */
	public void setKeyPriceUpper(Long keyPriceUpper) {
		this.keyPriceUpper = keyPriceUpper;
	}

	/**
	 * �y�n�ʐρE�����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �y�n�ʐρE���� �i���������j
	 */
	public BigDecimal getKeyLandAreaLower() {
		return keyLandAreaLower;
	}

	/**
	 * �y�n�ʐρE�����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyLandAreaLower �y�n�ʐρE�����i���������j
	 */
	public void setKeyLandAreaLower(BigDecimal keyLandAreaLower) {
		this.keyLandAreaLower = keyLandAreaLower;
	}

	/**
	 * �y�n�ʐρE����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �y�n�ʐρE����i���������j
	 */
	public BigDecimal getKeyLandAreaUpper() {
		return keyLandAreaUpper;
	}

	/**
	 * �y�n�ʐρE����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyLandAreaUpper �y�n�ʐρE����i���������j
	 */
	public void setKeyLandAreaUpper(BigDecimal keyLandAreaUpper) {
		this.keyLandAreaUpper = keyLandAreaUpper;
	}

	/**
	 * ��L�ʐρE�����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ��L�ʐρE�����i���������j
	 */
	public BigDecimal getKeyPersonalAreaLower() {
		return keyPersonalAreaLower;
	}

	/**
	 * ��L�ʐρE�����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaLower ��L�ʐρE�����i���������j
	 */
	public void setKeyPersonalAreaLower(BigDecimal keyPersonalAreaLower) {
		this.keyPersonalAreaLower = keyPersonalAreaLower;
	}

	/**
	 * ��L�ʐρE����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ��L�ʐρE����i���������j
	 */
	public BigDecimal getKeyPersonalAreaUpper() {
		return keyPersonalAreaUpper;
	}

	/**
	 * ��L�ʐρE����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaUpper ��L�ʐρE����i���������j
	 */
	public void setKeyPersonalAreaUpper(BigDecimal keyPersonalAreaUpper) {
		this.keyPersonalAreaUpper = keyPersonalAreaUpper;
	}

	/**
	 * �Ԏ��CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �Ԏ��CD�i���������j
	 */
	public String getKeyLayoutCd() {
		return keyLayoutCd;
	}

	/**
	 * �Ԏ��CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyLayoutCd �Ԏ��CD�i���������j
	 */
	public void setKeyLayoutCd(String keyLayoutCd) {
		this.keyLayoutCd = keyLayoutCd;
	}

	/**
	 * �������CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �������CD�i���������j
	 */
	public String getKeyHousingKindCd() {
		return keyHousingKindCd;
	}

	/**
	 * �������CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyHousingKindCd �������CD�i���������j
	 */
	public void setKeyHousingKindCd(String keyHousingKindCd) {
		this.keyHousingKindCd = keyHousingKindCd;
	}

	/**
	 * �����ʐρE�����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �����ʐρE�����i���������j
	 */
	public BigDecimal getKeyBuildingAreaLower() {
		return keyBuildingAreaLower;
	}

	/**
	 * �����ʐρE�����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPersonalAreaLower �����ʐρE�����i���������j
	 */
	public void setKeyBuildingAreaLower(BigDecimal keyBuildingAreaLower) {
		this.keyBuildingAreaLower = keyBuildingAreaLower;
	}

	/**
	 * �����ʐρE����i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return �����ʐρE����i���������j
	 */
	public BigDecimal getKeyBuildingAreaUpper() {
		return keyBuildingAreaUpper;
	}

	/**
	 * �����ʐρE����i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyBuildingAreaUpper �����ʐρE����i���������j
	 */
	public void setKeyBuildingAreaUpper(BigDecimal keyBuildingAreaUpper) {
		this.keyBuildingAreaUpper = keyBuildingAreaUpper;
	}

	/**
	 * ����������CD�i���������j���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����������CD�i���������j
	 */
	public String getKeyPartSrchCd() {
		return keyPartSrchCd;
	}

	/**
	 * ����������CD�i���������j��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyPartSrchCd ����������CD�i���������j
	 */
	public void setKeyPartSrchCd(String keyPartSrchCd) {
		this.keyPartSrchCd = keyPartSrchCd;
	}

	/**
	 * �\�[�g�������擾����B<br/>
	 * <br/>
	 * 
	 * @return �\�[�g����
	 */
	public String getKeyOrderType() {
		return keyOrderType;
	}

	/**
	 * �\�[�g������ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param keyOrder �\�[�g����
	 */
	public void setKeyOrderType(String keyOrderType) {
		this.keyOrderType = keyOrderType;
	}

	/**
	 * �����ΏۃV�X�e������CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �����ΏۃV�X�e������CD
	 */
	public String getSysHousingCd() {
		return sysHousingCd;
	}

	/**
	 * �����ΏۃV�X�e������CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param sysHousingCd �����ΏۃV�X�e������CD
	 */
	public void setSysHousingCd(String sysHousingCd) {
		this.sysHousingCd = sysHousingCd;
	}

}
