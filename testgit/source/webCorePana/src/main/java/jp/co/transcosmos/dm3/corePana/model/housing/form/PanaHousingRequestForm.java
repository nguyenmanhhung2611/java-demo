package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.transcosmos.dm3.adminCore.request.form.HousingRequestForm;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.util.PanaCommonUtil;
import jp.co.transcosmos.dm3.corePana.validation.DataCheckValidation;
import jp.co.transcosmos.dm3.corePana.validation.PriceReformCheckValidation;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.CodeLookupValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.springframework.util.StringUtils;
/**
 * �������N�G�X�g���́A�m�F�A������ʂ̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS     2015.04.22  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */

public class PanaHousingRequestForm extends HousingRequestForm implements Validateable {

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;
	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;
	
	private jp.co.transcosmos.dm3.utils.StringUtils DateUtils;

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	PanaHousingRequestForm(CodeLookupManager codeLookupManager) {
		super();
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	PanaHousingRequestForm() {
		super();
	}

	/** �R�}���h */
	private String command;

	/** �������[�h */
	private String model;

	/** ���Ã}���V�����̗\�Z���� */
	private String priceLowerMansion;

	/** ���Ã}���V�����̗\�Z��� */
	private String priceUpperMansion;

	/** ���Ìˌ��̗\�Z���� */
	private String priceLowerHouse;

	/** ���Ìˌ��̗\�Z��� */
	private String priceUpperHouse;

	/** ���Óy�n�̗\�Z���� */
	private String priceLowerLand;

	/** ���Óy�n�̗\�Z��� */
	private String priceUpperLand;

	/** ��L�ʐω��� */
	private String personalAreaLowerMansion;

	/** ��L�ʐϏ�� */
	private String personalAreaUpperMansion;

	/** ���Ìˌ��̌����ʐω��� */
	private String buildingAreaLowerHouse;

	/** ���Ìˌ��̌����ʐϏ�� */
	private String buildingAreaUpperHouse;

	/** ���Óy�n�̌����ʐω��� */
	private String buildingAreaLowerLand;

	/** ���Óy�n�̌����ʐϏ�� */
	private String buildingAreaUpperLand;

	/** ���Ìˌ��̓y�n�ʐω��� */
	private String landAreaLowerHouse;

	/** ���Ìˌ��̓y�n�ʐϏ�� */
	private String landAreaUpperHouse;

	/** ���Óy�n�̓y�n�ʐω��� */
	private String landAreaLowerLand;

	/** ���Óy�n�̓y�n�ʐϏ�� */
	private String landAreaUpperLand;

	/** ���Ã}���V�����̊Ԏ�� */
	private String[] layoutCdMansion;

	/** ���Ìˌ��̊Ԏ�� */
	private String[] layoutCdHouse;

	/** ���Ã}���V�����̒z�N�� */
	private String compDateMansion;

	/** ���Ìˌ��̒z�N�� */
	private String compDateHouse;

	/** ���Ã}���V�����̂������߂̃|�C���g */
	private String[] iconCdMansion;

	/** ���Ìˌ��̂������߂̃|�C���g */
	private String[] iconCdHouse;

	/** ���Ã}���V�����̃��t�H�[�����i���݂Ō������� */
	private String reformCheckMansion;

	/** ���Ìˌ��̃��t�H�[�����i���݂Ō������� */
	private String reformCheckHouse;

	/** Url�p�^�[�� */
	private String urlPattern;
	
	
	private String hopeRequestTest;

	public String getHopeRequestTest() {
		return hopeRequestTest;
	}

	public void setHopeRequestTest(String hopeRequestTest) {
		this.hopeRequestTest = hopeRequestTest;
	}
	
	private String startDate;
	private String endDate;
	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern
	 *            �Z�b�g���� urlPattern
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	/**
	 * �R�����g���擾����B<br/>
	 * �R�����g�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return �R�����g
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * �R�����g��ݒ肷��B<br/>
	 * �R�����g�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �R�����g �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @param command
	 *            �R�����g
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * �������[�h ���擾����B<br/>
	 * �������[�h �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������[�h �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return �������[�h
	 */
	public String getModel() {
		return model;
	}

	/**
	 * �������[�h ��ݒ肷��B<BR/>
	 * �������[�h �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �������[�h �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM prefCd �������[�h
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * ���Ã}���V�����̗\�Z���� ���擾����B<br/>
	 * ���Ã}���V�����̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B
	 * <br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̗\�Z����
	 */
	public String getPriceLowerMansion() {
		return priceLowerMansion;
	}

	/**
	 * ���Ã}���V�����̗\�Z���� ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B
	 * <BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerMansion ���Ã}���V�����̗\�Z����
	 */
	public void setPriceLowerMansion(String priceLowerMansion) {
		this.priceLowerMansion = priceLowerMansion;
	}

	/**
	 * ���Ã}���V�����̗\�Z��� ���擾����B<br/>
	 * ���Ã}���V�����̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B
	 * <br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̗\�Z���
	 */
	public String getPriceUpperMansion() {
		return priceUpperMansion;
	}

	/**
	 * ���Ã}���V�����̗\�Z��� ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B
	 * <BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperMansion ���Ã}���V�����̗\�Z���
	 */
	public void setPriceUpperMansion(String priceUpperMansion) {
		this.priceUpperMansion = priceUpperMansion;
	}

	/**
	 * ���Ìˌ��̗\�Z���� ���擾����B<br/>
	 * ���Ìˌ��̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̗\�Z����
	 */
	public String getPriceLowerHouse() {
		return priceLowerHouse;
	}

	/**
	 * ���Ìˌ��̗\�Z���� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerHouse ���Ìˌ��̗\�Z����
	 */
	public void setPriceLowerHouse(String priceLowerHouse) {
		this.priceLowerHouse = priceLowerHouse;
	}

	/**
	 * ���Ìˌ��̗\�Z��� ���擾����B<br/>
	 * ���Ìˌ��̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̗\�Z���
	 */
	public String getPriceUpperHouse() {
		return priceUpperHouse;
	}

	/**
	 * ���Ìˌ��̗\�Z��� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperHouse ���Ìˌ��̗\�Z���
	 */
	public void setPriceUpperHouse(String priceUpperHouse) {
		this.priceUpperHouse = priceUpperHouse;
	}

	/**
	 * ���Óy�n�̗\�Z���� ���擾����B<br/>
	 * ���Óy�n�̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̗\�Z����
	 */
	public String getPriceLowerLand() {
		return priceLowerLand;
	}

	/**
	 * ���Óy�n�̗\�Z���� ��ݒ肷��B<BR/>
	 * ���Óy�n�̗\�Z���� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̗\�Z���� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM priceLowerLand ���Óy�n�̗\�Z����
	 */
	public void setPriceLowerLand(String priceLowerLand) {
		this.priceLowerLand = priceLowerLand;
	}

	/**
	 * ���Óy�n�̗\�Z��� ���擾����B<br/>
	 * ���Óy�n�̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̗\�Z���
	 */
	public String getPriceUpperLand() {
		return priceUpperLand;
	}

	/**
	 * ���Óy�n�̗\�Z��� ��ݒ肷��B<BR/>
	 * ���Óy�n�̗\�Z��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̗\�Z��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM priceUpperLand ���Óy�n�̗\�Z���
	 */
	public void setPriceUpperLand(String priceUpperLand) {
		this.priceUpperLand = priceUpperLand;
	}

	/**
	 * ��L�ʐω��� ���擾����B<br/>
	 * ��L�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐω���
	 */
	public String getPersonalAreaLowerMansion() {
		return personalAreaLowerMansion;
	}

	/**
	 * ��L�ʐω��� ��ݒ肷��B<BR/>
	 * ��L�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM personalAreaLowerMansion ��L�ʐω���
	 */
	public void setPersonalAreaLowerMansion(String personalAreaLowerMansion) {
		this.personalAreaLowerMansion = personalAreaLowerMansion;
	}

	/**
	 * ��L�ʐϏ�� ���擾����B<br/>
	 * ��L�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ��L�ʐϏ��
	 */
	public String getPersonalAreaUpperMansion() {
		return personalAreaUpperMansion;
	}

	/**
	 * ��L�ʐϏ�� ��ݒ肷��B<BR/>
	 * ��L�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ��L�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM personalAreaUpperMansion ��L�ʐϏ��
	 */
	public void setPersonalAreaUpperMansion(String personalAreaUpperMansion) {
		this.personalAreaUpperMansion = personalAreaUpperMansion;
	}

	/**
	 * ���Ìˌ��̌����ʐω��� ���擾����B<br/>
	 * ���Ìˌ��̌����ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̌����ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̌����ʐω���
	 */
	public String getBuildingAreaLowerHouse() {
		return buildingAreaLowerHouse;
	}

	/**
	 * ���Ìˌ��̌����ʐω��� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̌����ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̌����ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaLowerHouse ���Ìˌ��̌����ʐω���
	 */
	public void setBuildingAreaLowerHouse(String buildingAreaLowerHouse) {
		this.buildingAreaLowerHouse = buildingAreaLowerHouse;
	}

	/**
	 * ���Ìˌ��̌����ʐϏ�� ���擾����B<br/>
	 * ���Ìˌ��̌����ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̌����ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̌����ʐϏ��
	 */
	public String getBuildingAreaUpperHouse() {
		return buildingAreaUpperHouse;
	}

	/**
	 * ���Ìˌ��̌����ʐϏ�� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̌����ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̌����ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaUpperHouse ���Ìˌ��̌����ʐϏ��
	 */
	public void setBuildingAreaUpperHouse(String buildingAreaUpperHouse) {
		this.buildingAreaUpperHouse = buildingAreaUpperHouse;
	}

	/**
	 * ���Óy�n�̌����ʐω��� ���擾����B<br/>
	 * ���Óy�n�̌����ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̌����ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̌����ʐω���
	 */
	public String getBuildingAreaLowerLand() {
		return buildingAreaLowerLand;
	}

	/**
	 * ���Óy�n�̌����ʐω��� ��ݒ肷��B<BR/>
	 * ���Óy�n�̌����ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̌����ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaLowerLand ���Óy�n�̌����ʐω���
	 */
	public void setBuildingAreaLowerLand(String buildingAreaLowerLand) {
		this.buildingAreaLowerLand = buildingAreaLowerLand;
	}

	/**
	 * ���Óy�n�̌����ʐϏ�� ���擾����B<br/>
	 * ���Óy�n�̌����ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̌����ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̌����ʐϏ��
	 */
	public String getBuildingAreaUpperLand() {
		return buildingAreaUpperLand;
	}

	/**
	 * ���Óy�n�̌����ʐϏ�� ��ݒ肷��B<BR/>
	 * ���Óy�n�̌����ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̌����ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM buildingAreaUpperLand ���Óy�n�̌����ʐϏ��
	 */
	public void setBuildingAreaUpperLand(String buildingAreaUpperLand) {
		this.buildingAreaUpperLand = buildingAreaUpperLand;
	}

	/**
	 * ���Ìˌ��̓y�n�ʐω��� ���擾����B<br/>
	 * ���Ìˌ��̓y�n�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̓y�n�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̓y�n�ʐω���
	 */
	public String getLandAreaLowerHouse() {
		return landAreaLowerHouse;
	}

	/**
	 * ���Ìˌ��̓y�n�ʐω��� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̓y�n�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̓y�n�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaLowerHouse ���Ìˌ��̓y�n�ʐω���
	 */
	public void setLandAreaLowerHouse(String landAreaLowerHouse) {
		this.landAreaLowerHouse = landAreaLowerHouse;
	}

	/**
	 * ���Ìˌ��̓y�n�ʐϏ�� ���擾����B<br/>
	 * ���Ìˌ��̓y�n�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̓y�n�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̓y�n�ʐϏ��
	 */
	public String getLandAreaUpperHouse() {
		return landAreaUpperHouse;
	}

	/**
	 * ���Ìˌ��̓y�n�ʐϏ�� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̓y�n�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̓y�n�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaUpperHouse ���Ìˌ��̓y�n�ʐϏ��
	 */
	public void setLandAreaUpperHouse(String landAreaUpperHouse) {
		this.landAreaUpperHouse = landAreaUpperHouse;
	}

	/**
	 * ���Óy�n�̓y�n�ʐω��� ���擾����B<br/>
	 * ���Óy�n�̓y�n�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̓y�n�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̓y�n�ʐω���
	 */
	public String getLandAreaLowerLand() {
		return landAreaLowerLand;
	}

	/**
	 * ���Óy�n�̓y�n�ʐω��� ��ݒ肷��B<BR/>
	 * ���Óy�n�̓y�n�ʐω��� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̓y�n�ʐω��� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaLowerLand ���Óy�n�̓y�n�ʐω���
	 */
	public void setLandAreaLowerLand(String landAreaLowerLand) {
		this.landAreaLowerLand = landAreaLowerLand;
	}

	/**
	 * ���Óy�n�̓y�n�ʐϏ�� ���擾����B<br/>
	 * ���Óy�n�̓y�n�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̓y�n�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Óy�n�̓y�n�ʐϏ��
	 */
	public String getLandAreaUpperLand() {
		return landAreaUpperLand;
	}

	/**
	 * ���Óy�n�̓y�n�ʐϏ�� ��ݒ肷��B<BR/>
	 * ���Óy�n�̓y�n�ʐϏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Óy�n�̓y�n�ʐϏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM landAreaUpperLand ���Óy�n�̓y�n�ʐϏ��
	 */
	public void setLandAreaUpperLand(String landAreaUpperLand) {
		this.landAreaUpperLand = landAreaUpperLand;
	}

	/**
	 * ���Ã}���V�����̊Ԏ�� ���擾����B<br/>
	 * ���Ã}���V�����̊Ԏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̊Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̊Ԏ��
	 */
	public String[] getLayoutCdMansion() {
		return layoutCdMansion;
	}

	/**
	 * ���Ã}���V�����̊Ԏ�� ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̊Ԏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̊Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM layoutCdMansion ���Ã}���V�����̊Ԏ��
	 */
	public void setLayoutCdMansion(String[] layoutCdMansion) {
		this.layoutCdMansion = layoutCdMansion;
	}

	/**
	 * ���Ìˌ��̊Ԏ�� ���擾����B<br/>
	 * ���Ìˌ��̊Ԏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̊Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̊Ԏ��
	 */
	public String[] getLayoutCdHouse() {
		return layoutCdHouse;
	}

	/**
	 * ���Ìˌ��̊Ԏ�� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̊Ԏ�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̊Ԏ�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM layoutCdHouse ���Ìˌ��̊Ԏ��
	 */
	public void setLayoutCdHouse(String[] layoutCdHouse) {
		this.layoutCdHouse = layoutCdHouse;
	}

	/**
	 * ���Ã}���V�����̒z�N�� ���擾����B<br/>
	 * ���Ã}���V�����̒z�N�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̒z�N�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̒z�N��
	 */
	public String getCompDateMansion() {
		return compDateMansion;
	}

	/**
	 * ���Ã}���V�����̒z�N�� ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̒z�N�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̒z�N�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM compDateMansion ���Ã}���V�����̒z�N��
	 */
	public void setCompDateMansion(String compDateMansion) {
		this.compDateMansion = compDateMansion;
	}

	/**
	 * ���Ìˌ��̒z�N�� ���擾����B<br/>
	 * ���Ìˌ��̒z�N�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̒z�N�� �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̒z�N��
	 */
	public String getCompDateHouse() {
		return compDateHouse;
	}

	/**
	 * ���Ìˌ��̒z�N�� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̒z�N�� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̒z�N�� �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM compDateHouse ���Ìˌ��̒z�N��
	 */
	public void setCompDateHouse(String compDateHouse) {
		this.compDateHouse = compDateHouse;
	}

	/**
	 * ���Ã}���V�����̂������߂̃|�C���g ���擾����B<br/>
	 * ���Ã}���V�����̂������߂̃|�C���g �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̂������߂̃|�C���g
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̂������߂̃|�C���g
	 */
	public String[] getIconCdMansion() {
		return iconCdMansion;
	}

	/**
	 * ���Ã}���V�����̂������߂̃|�C���g ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̂������߂̃|�C���g �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̂������߂̃|�C���g
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM iconCdMansion ���Ã}���V�����̂������߂̃|�C���g
	 */
	public void setIconCdMansion(String[] iconCdMansion) {
		this.iconCdMansion = iconCdMansion;
	}

	/**
	 * ���Ìˌ��̂������߂̃|�C���g ���擾����B<br/>
	 * ���Ìˌ��̂������߂̃|�C���g �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̂������߂̃|�C���g
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̂������߂̃|�C���g
	 */
	public String[] getIconCdHouse() {
		return iconCdHouse;
	}

	/**
	 * ���Ìˌ��̂������߂̃|�C���g ��ݒ肷��B<BR/>
	 * ���Ìˌ��̂������߂̃|�C���g �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̂������߂̃|�C���g
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM iconCdHouse ���Ìˌ��̂������߂̃|�C���g
	 */
	public void setIconCdHouse(String[] iconCdHouse) {
		this.iconCdHouse = iconCdHouse;
	}

	/**
	 * ���Ã}���V�����̃��t�H�[�����i���݂Ō������� ���擾����B<br/>
	 * ���Ã}���V�����̃��t�H�[�����i���݂Ō������� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̃��t�H�[�����i���݂Ō�������
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ã}���V�����̃��t�H�[�����i���݂Ō�������
	 */
	public String getReformCheckMansion() {
		return reformCheckMansion;
	}

	/**
	 * ���Ã}���V�����̃��t�H�[�����i���݂Ō������� ��ݒ肷��B<BR/>
	 * ���Ã}���V�����̃��t�H�[�����i���݂Ō������� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ã}���V�����̃��t�H�[�����i���݂Ō�������
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM reformCheckMansion ���Ã}���V�����̃��t�H�[�����i���݂Ō�������
	 */
	public void setReformCheckMansion(String reformCheckMansion) {
		this.reformCheckMansion = reformCheckMansion;
	}

	/**
	 * ���Ìˌ��̃��t�H�[�����i���݂Ō������� ���擾����B<br/>
	 * ���Ìˌ��̃��t�H�[�����i���݂Ō������� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̃��t�H�[�����i���݂Ō�������
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
	 * <br/>
	 *
	 * @return ���Ìˌ��̃��t�H�[�����i���݂Ō�������
	 */
	public String getReformCheckHouse() {
		return reformCheckHouse;
	}

	/**
	 * ���Ìˌ��̃��t�H�[�����i���݂Ō������� ��ݒ肷��B<BR/>
	 * ���Ìˌ��̃��t�H�[�����i���݂Ō������� �̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���Ìˌ��̃��t�H�[�����i���݂Ō�������
	 * �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
	 * <BR/>
	 *
	 * @PARAM reformCheckHouse ���Ìˌ��̃��t�H�[�����i���݂Ō�������
	 */
	public void setReformCheckHouse(String reformCheckHouse) {
		this.reformCheckHouse = reformCheckHouse;
	}

	/**
	 * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
	 * <br/>
	 */
	public void setDefaultData(HousingRequest requestInfo) {
		
		
		if(0 != requestInfo.getHousingReqRoutes().size()) {
			this.setRouteCd(requestInfo.getHousingReqRoutes().get(0).getRouteCd());
		}
		if(0 != requestInfo.getHousingReqStations().size()) {
			this.setStationCd(requestInfo.getHousingReqStations().get(0).getStationCd());
		}
		this.setPrefCd(requestInfo.getHousingRequestAreas().get(0).getPrefCd());
		this.setHousingKindCd(requestInfo.getHousingReqKinds().get(0).getHousingKindCd());
		// ����/���i�E�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerMansion = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerHouse = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceLower())) {
			this.priceLowerLand = String.valueOf(requestInfo.getHousingRequestInfo().getPriceLower() / 10000);
		}
		// ����/���i�E����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperMansion = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperHouse = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingRequestInfo().getPriceUpper())) {
			this.priceUpperLand = String.valueOf(requestInfo.getHousingRequestInfo().getPriceUpper() / 10000);
		}

		jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo panaHousingRequestInfo =
				(jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo) requestInfo.getHousingRequestInfo();
		
		
		this.setHopeRequestTest(panaHousingRequestInfo.getHopeRequestTest());
		
		String tempStartDate = jp.co.transcosmos.dm3.utils.StringUtils.dateToString(panaHousingRequestInfo.getStartDate(), "dd/MM/yyyy");
		this.setStartDate(tempStartDate);
		
		String tempEndDate = jp.co.transcosmos.dm3.utils.StringUtils.dateToString(panaHousingRequestInfo.getEndDate(), "dd/MM/yyyy");
		this.setEndDate(tempEndDate);
		
		// ���t�H�[�����i��
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			this.reformCheckMansion = panaHousingRequestInfo.getUseReform();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			this.reformCheckHouse = panaHousingRequestInfo.getUseReform();
		}
		// ��L�ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getPersonalAreaLower())) {
			this.personalAreaLowerMansion = String.valueOf(panaHousingRequestInfo.getPersonalAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getPersonalAreaLower()).length() - 3);
		}
		// ��L�ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getPersonalAreaUpper())) {
			this.personalAreaUpperMansion = String.valueOf(panaHousingRequestInfo.getPersonalAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getPersonalAreaUpper()).length() - 3);
		}
		// �����ʐ�_����
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaLower())) {
			this.buildingAreaLowerHouse = String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaLower())) {
			this.buildingAreaLowerLand = String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaLower()).length() - 3);
		}
		// �����ʐ�_���
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaUpper())) {
			this.buildingAreaUpperHouse = String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuildingAreaUpper())) {
			this.buildingAreaUpperLand = String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getBuildingAreaUpper()).length() - 3);
		}
		// �y�n�ʐ�_����
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaLower())) {
			this.landAreaLowerHouse = String.valueOf(panaHousingRequestInfo.getLandAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaLower()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaLower())) {
			this.landAreaLowerLand = String.valueOf(panaHousingRequestInfo.getLandAreaLower()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaLower()).length() - 3);
		}
		// �y�n�ʐ�_���
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaUpper())) {
			this.landAreaUpperHouse = String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).length() - 3);
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getLandAreaUpper())) {
			this.landAreaUpperLand = String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).substring(0,
					String.valueOf(panaHousingRequestInfo.getLandAreaUpper()).length() - 3);
		}
		// �Ԏ�CD
		String[] layoutArray = new String[requestInfo.getHousingReqLayouts().size()];
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqLayouts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqLayouts().size(); idx++) {
				layoutArray[idx] = requestInfo.getHousingReqLayouts().get(idx).getLayoutCd();
			}
			this.layoutCdMansion = layoutArray;
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqLayouts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqLayouts().size(); idx++) {
				layoutArray[idx] = requestInfo.getHousingReqLayouts().get(idx).getLayoutCd();
			}
			this.layoutCdHouse = layoutArray;
		}
		// �z�N��
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuiltMonth())) {
			this.compDateMansion = String.valueOf(panaHousingRequestInfo.getBuiltMonth());
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(panaHousingRequestInfo.getBuiltMonth())) {
			this.compDateHouse = String.valueOf(panaHousingRequestInfo.getBuiltMonth());
		}
		// �������߂̃|�C���g ����������CD
		String[] iconArray = new String[requestInfo.getHousingReqParts().size()];
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqParts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqParts().size(); idx++) {
				iconArray[idx] = requestInfo.getHousingReqParts().get(idx).getPartSrchCd();
			}
			this.iconCdMansion = iconArray;
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())
				&& !StringUtils.isEmpty(requestInfo.getHousingReqParts())) {
			for (int idx = 0; idx < requestInfo.getHousingReqParts().size(); idx++) {
				iconArray[idx] = requestInfo.getHousingReqParts().get(idx).getPartSrchCd();
			}
			this.iconCdHouse = iconArray;
		}

	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�̃o���f�[�V�������s��<br/>
	 * <br/>
	 *
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();
		
//		hien.nt create validate for 2 fields combo box and hope request  
		valCombobox(errors);		
		valHopeRequestText(errors);
		valDate(errors);

		// ������ʓ��̓`�F�b�N�B
		valHousingKindCd(errors);

		// �s���{�������̓`�F�b�N�B
		valPrefCd(errors);

		// �\�Z_�������\�Z_����̏ꍇ
		valPrice(errors);

		// ��L�ʐ�_��������L�ʐ�_����̏ꍇ
		valPersonalArea(errors);

		// �����ʐ�_�����������ʐ�_����̏ꍇ
		valBuildingArea(errors);

		// �y�n�ʐ�_�������y�n�ʐ�_����̏ꍇ
		valLandArea(errors);

		// �u���t�H�[�����i���݂Ō�������v���`�F�b�N�I���ꍇ�A�\�Z�����A�\�Z����������I�����Ă��Ȃ��ꍇ�A
		valReformCheck(errors);

		// �Ԏ�`�F�b�N
		valLayoutCheck(errors);

		// �z�N���`�F�b�N
		valCompDateCheck(errors);

		// �������߂̃|�C���g�`�F�b�N
		valIconCdCheck(errors);

		return (startSize == errors.size());
	}

	/**
	 * to validate logic StartDate and EndDate
	 * startDate > EndDate --> add Error
	 * startDate || EndDate --> add Error
	 * startDate && EndDate --> add Error
	 * @param errors
	 */
	@SuppressWarnings("static-access")
	private void valDate(List<ValidationFailure> errors) {				
		Date start = null;
		Date end = null;
		
		if(StringUtils.isEmpty(this.getStartDate()) || StringUtils.isEmpty(this.getEndDate())){			
			ValidationFailure valDate = new 
					ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
			errors.add(valDate);
			System.out.println("case empty");
		}else{		
			try {	
				start= DateUtils.stringToDate(this.getStartDate());
				end = DateUtils.stringToDate(this.getEndDate());
				
				if(start.compareTo(end) > 0)
				{
					ValidationFailure valDate = new ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
					errors.add(valDate);
				}
			
			} catch (ParseException e)  {			
				ValidationFailure valDate = new 
						ValidationFailure("hienDate","housingRequest.input.valDate",null, null);	
				errors.add(valDate);
			}
		}
	}
	

	/**
	 * ������� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valHousingKindCd(List<ValidationFailure> errors) {
		// ������ʓ��̓`�F�b�N
		ValidationChain valHousingKindCd = new ValidationChain("housingRequest.input.valHousingKindCd",
				this.getHousingKindCd());
		// �K�{�`�F�b�N
		valHousingKindCd.addValidation(new NullOrEmptyCheckValidation());

		valHousingKindCd.validate(errors);
	}

	protected void valHopeRequestText(List<ValidationFailure> errors) {
		// ������ʓ��̓`�F�b�N
		ValidationChain valHopeRequestText = new ValidationChain("housingRequest.input.valHopeRequestText", this.getHopeRequestTest());
		// �K�{�`�F�b�N
		valHopeRequestText.addValidation(new MaxLengthValidation(1000));
		
		valHopeRequestText.validate(errors);
	}

	/**
	 * �Ԏ�`�F�b�N �o���f�[�V����<br/>
	 * �Ecode-lookUp�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valLayoutCheck(List<ValidationFailure> errors) {

		ValidationChain valLayoutCd = null;

		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {

			if (this.getLayoutCdMansion() != null) {
				for (int i = 0; i < this.getLayoutCdMansion().length; i++) {
					valLayoutCd = new ValidationChain("housingRequest.input.valLayoutCd", this.getLayoutCdMansion()[i]);

					if (valLayoutCd != null) {

						// code-lookup�`�F�b�N
						valLayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

						valLayoutCd.validate(errors);
					}
				}
			}

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {

			if (this.getLayoutCdHouse() != null) {
				for (int i = 0; i < this.getLayoutCdHouse().length; i++) {
					valLayoutCd = new ValidationChain("housingRequest.input.valLayoutCd", this.getLayoutCdHouse()[i]);

					if (valLayoutCd != null) {

						// code-lookup�`�F�b�N
						valLayoutCd.addValidation(new CodeLookupValidation(this.codeLookupManager, "layoutCd"));

						valLayoutCd.validate(errors);
					}
				}
			}

		}

	}

	/**
	 * �z�N���`�F�b�N �o���f�[�V����<br/>
	 * �Ecode-lookUp�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valCompDateCheck(List<ValidationFailure> errors) {

		ValidationChain valCompDate = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valCompDate = new ValidationChain("housingRequest.input.valCompDate", this.getCompDateMansion());
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valCompDate = new ValidationChain("housingRequest.input.valCompDate", this.getCompDateHouse());
		}

		if (valCompDate != null) {
			// code-lookup�`�F�b�N
			valCompDate.addValidation(new CodeLookupValidation(this.codeLookupManager, "compDate"));

			valCompDate.validate(errors);
		}

	}

	/**
	 * �������߂̃|�C���g�`�F�b�N �o���f�[�V����<br/>
	 * �Ecode-lookUp�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valIconCdCheck(List<ValidationFailure> errors) {

		ValidationChain valIconCd = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (this.getIconCdMansion() != null) {
				for (int i = 0; i < this.getIconCdMansion().length; i++) {
					valIconCd = new ValidationChain("housingRequest.input.valIcon", this.getIconCdMansion()[i]);

					if (valIconCd != null) {

						// code-lookup�`�F�b�N
						valIconCd.addValidation(
								new CodeLookupValidation(this.codeLookupManager, "recommend_point_icon_list"));

						valIconCd.validate(errors);
					}
				}
			}
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (this.getIconCdHouse() != null) {
				for (int i = 0; i < this.getIconCdHouse().length; i++) {
					valIconCd = new ValidationChain("housingRequest.input.valIcon", this.getIconCdHouse()[i]);

					if (valIconCd != null) {

						// code-lookup�`�F�b�N
						valIconCd.addValidation(
								new CodeLookupValidation(this.codeLookupManager, "recommend_point_icon_list"));

						valIconCd.validate(errors);
					}
				}
			}
		}

	}

	/**
	 * �s���{���� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valPrefCd(List<ValidationFailure> errors) {
		// �s���{�������̓`�F�b�N
		ValidationChain valSwitchSelect = new ValidationChain("housingRequest.input.valPrefCd", this.getPrefCd());
		// �����`�F�b�N
		valSwitchSelect.addValidation(new NullOrEmptyCheckValidation());

		valSwitchSelect.validate(errors);
	}
	
	/**
	 * hien.nt
	 * validate code logic for combo box routeCd and stationCd
	 * 
	 * @param errors
	 */
	protected void valCombobox(List<ValidationFailure> errors) {
		if((StringValidateUtil.isEmpty(this.getRouteCd()) 
						&& StringValidateUtil.isEmpty(this.getStationCd())
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest())
			|| (StringValidateUtil.isEmpty(this.getRouteCd())
						&& !StringValidateUtil.isEmpty(this.getStationCd())	
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest()))
			|| (StringValidateUtil.isEmpty(this.getStationCd())	
						&& StringValidateUtil.isEmpty(this.getHopeRequestTest())))
			){
			// �s���{���̐��l�`�F�b�N
			ValidationFailure valCombobox = new ValidationFailure("hiencombo","housingRequest.input.valCombobox",
					null, null);	
			errors.add(valCombobox);
		} 
	}
	
	/**
	 * �\�Z �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valPrice(List<ValidationFailure> errors) {
		// �\�Z���̓`�F�b�N
		ValidationChain valPriceLower = null;
		ValidationChain valPriceUpper = null;
		String priceUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerMansion());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperMansion());
			priceUpper = this.getPriceUpperMansion();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerHouse());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperHouse());
			priceUpper = this.getPriceUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerLand());
			valPriceUpper = new ValidationChain("housingRequest.input.valPriceUpper", this.getPriceUpperLand());
			priceUpper = this.getPriceUpperLand();
		}

		if (valPriceLower != null) {
			// code-lookup�`�F�b�N
			valPriceLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "price"));

			// �\�Z_�������\�Z_���
			valPriceLower.addValidation(new DataCheckValidation(priceUpper, "�\�Z_���"));
			valPriceLower.validate(errors);
		}
		if (valPriceUpper != null) {
			valPriceUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "price"));
			valPriceUpper.validate(errors);
		}

	}

	/**
	 * ��L�ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valPersonalArea(List<ValidationFailure> errors) {
		// ��L�ʐϓ��̓`�F�b�N
		ValidationChain valPersonalAreaLower = null;
		ValidationChain valPersonalAreaUpper = null;
		String areaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPersonalAreaLower = new ValidationChain("housingRequest.input.valPersonalAreaLower",
					this.getPersonalAreaLowerMansion());
			valPersonalAreaUpper = new ValidationChain("housingRequest.input.valPersonalAreaUpper",
					this.getPersonalAreaUpperMansion());
			areaUpper = this.getPersonalAreaUpperMansion();
		}

		if (valPersonalAreaLower != null) {
			// code-lookup�`�F�b�N
			valPersonalAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// �\�Z_�������\�Z_���
			valPersonalAreaLower.addValidation(new DataCheckValidation(areaUpper, "��L�ʐ�_���"));
			valPersonalAreaLower.validate(errors);
		}
		if (valPersonalAreaUpper != null) {
			// code-lookup�`�F�b�N
			valPersonalAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valPersonalAreaUpper.validate(errors);
		}

	}

	/**
	 * �����ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valBuildingArea(List<ValidationFailure> errors) {
		// �����ʐϓ��̓`�F�b�N
		ValidationChain valBuildingAreaLower = null;
		ValidationChain valBuildingAreaUpper = null;
		String buildingAreaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valBuildingAreaLower = new ValidationChain("housingRequest.input.valBuildingAreaLower",
					this.getBuildingAreaLowerHouse());
			valBuildingAreaUpper = new ValidationChain("housingRequest.input.valBuildingAreaUpper",
					this.getBuildingAreaUpperHouse());
			buildingAreaUpper = this.getBuildingAreaUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valBuildingAreaLower = new ValidationChain("housingRequest.input.valBuildingAreaLower",
					this.getBuildingAreaLowerLand());
			valBuildingAreaUpper = new ValidationChain("housingRequest.input.valBuildingAreaUpper",
					this.getBuildingAreaUpperLand());
			buildingAreaUpper = this.getBuildingAreaUpperLand();
		}

		if (valBuildingAreaLower != null) {
			// code-lookup�`�F�b�N
			valBuildingAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// �����ʐ�_�����������ʐ�_���
			valBuildingAreaLower.addValidation(new DataCheckValidation(buildingAreaUpper, "�����ʐ�_���"));
			valBuildingAreaLower.validate(errors);
		}
		if (valBuildingAreaUpper != null) {
			// code-lookup�`�F�b�N
			valBuildingAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valBuildingAreaUpper.validate(errors);
		}
	}

	/**
	 * �y�n�ʐ� �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valLandArea(List<ValidationFailure> errors) {
		// �y�n�ʐϓ��̓`�F�b�N
		ValidationChain valLandAreaLower = null;
		ValidationChain valLandAreaUpper = null;
		String landAreaUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valLandAreaLower = new ValidationChain("housingRequest.input.valLandAreaLower",
					this.getLandAreaLowerHouse());
			valLandAreaUpper = new ValidationChain("housingRequest.input.valLandAreaUpper",
					this.getLandAreaUpperHouse());
			landAreaUpper = this.getLandAreaUpperHouse();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			valLandAreaLower = new ValidationChain("housingRequest.input.valLandAreaLower",
					this.getLandAreaLowerLand());
			valLandAreaUpper = new ValidationChain("housingRequest.input.valLandAreaUpper",
					this.getLandAreaUpperLand());
			landAreaUpper = this.getLandAreaUpperLand();
		}

		if (valLandAreaLower != null) {
			// code-lookup�`�F�b�N
			valLandAreaLower.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			// �y�n�ʐ�_�������y�n�ʐ�_���
			valLandAreaLower.addValidation(new DataCheckValidation(landAreaUpper, "�y�n�ʐ�_���"));
			valLandAreaLower.validate(errors);
		}
		if (valLandAreaUpper != null) {
			// code-lookup�`�F�b�N
			valLandAreaUpper.addValidation(new CodeLookupValidation(this.codeLookupManager, "area"));
			valLandAreaUpper.validate(errors);
		}
	}

	/**
	 * �u���t�H�[�����i���݂Ō�������v �o���f�[�V����<br/>
	 * �E�K�{�`�F�b�N <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 */
	protected void valReformCheck(List<ValidationFailure> errors) {
		// �\�Z���̓`�F�b�N
		ValidationChain valPriceLower = null;
		String priceUpper = null;
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerMansion());
			priceUpper = this.getPriceUpperMansion();
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			valPriceLower = new ValidationChain("housingRequest.input.valPriceLower", this.getPriceLowerHouse());
			priceUpper = this.getPriceUpperHouse();
		}

		if ("1".equals(this.getReformCheckHouse()) || "1".equals(this.getReformCheckMansion())) {

			if (valPriceLower != null) {
				// �\�Z_�������\�Z_���
				valPriceLower.addValidation(new PriceReformCheckValidation(priceUpper));
				valPriceLower.validate(errors);
			}
		}
	}

	/**
	 * �����œn���ꂽ�������N�G�X�g���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 *
	 * @param userId
	 *            ���[�UID
	 * @param housingRequestInfo
	 *            �������N�G�X�g���
	 */
	@SuppressWarnings("static-access")
	@Override
	public void copyToHousingRequestInfo(String userId, HousingRequestInfo housingRequestInfo) {

		if (StringValidateUtil.isEmpty(userId)) {
			return;
		}

		jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo panaHousingRequestInfo = (jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo) housingRequestInfo;

		super.copyToHousingRequestInfo(userId, housingRequestInfo);

		// ���[�UID
		housingRequestInfo.setUserId(userId);	
		SimpleDateFormat targetFormatToDate = new SimpleDateFormat("dd/MM/yyyy" );
		try {
			panaHousingRequestInfo.setStartDate(targetFormatToDate.parse(this.getStartDate()));
			panaHousingRequestInfo.setEndDate(targetFormatToDate.parse(this.getEndDate()));
			
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		panaHousingRequestInfo.setHopeRequestTest(this.getHopeRequestTest());

		// ����/���i�E�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerMansion)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerMansion) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// ����/���i�E�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerHouse)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerHouse) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// ����/���i�E�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceLowerLand)) {
				housingRequestInfo.setPriceLower(Long.valueOf(this.priceLowerLand) * 10000);
			} else {
				housingRequestInfo.setPriceLower(null);
			}

		}
		// ����/���i�E����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperMansion)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperMansion) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}

		}
		// ����/���i�E����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperHouse)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperHouse) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}

		}
		// ����/���i�E����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.priceUpperLand)) {
				housingRequestInfo.setPriceUpper(Long.valueOf(this.priceUpperLand) * 10000);
			} else {
				housingRequestInfo.setPriceUpper(null);
			}
		}
		// ��L�ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.personalAreaLowerMansion)) {
				housingRequestInfo
						.setPersonalAreaLower(BigDecimal.valueOf(Long.valueOf(this.personalAreaLowerMansion)));
			} else {
				housingRequestInfo.setPersonalAreaLower(null);
			}
		}
		// ��L�ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.personalAreaUpperMansion)) {
				housingRequestInfo
						.setPersonalAreaUpper(BigDecimal.valueOf(Long.valueOf(this.personalAreaUpperMansion)));
			} else {
				housingRequestInfo.setPersonalAreaUpper(null);
			}
		}
		// �����ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaLowerHouse)) {
				panaHousingRequestInfo
						.setBuildingAreaLower(BigDecimal.valueOf(Long.valueOf(this.buildingAreaLowerHouse)));
			} else {
				panaHousingRequestInfo.setBuildingAreaLower(null);
			}
		}
		// �����ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaLowerLand)) {
				panaHousingRequestInfo
						.setBuildingAreaLower(BigDecimal.valueOf(Long.valueOf(this.buildingAreaLowerLand)));
			} else {
				panaHousingRequestInfo.setBuildingAreaLower(null);
			}
		}
		// �����ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaUpperHouse)) {
				panaHousingRequestInfo
						.setBuildingAreaUpper(BigDecimal.valueOf(Long.valueOf(this.buildingAreaUpperHouse)));
			} else {
				panaHousingRequestInfo.setBuildingAreaUpper(null);
			}
		}
		// �����ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.buildingAreaUpperLand)) {
				panaHousingRequestInfo
						.setBuildingAreaUpper(BigDecimal.valueOf(Long.valueOf(this.buildingAreaUpperLand)));
			} else {
				panaHousingRequestInfo.setBuildingAreaUpper(null);
			}
		}

		// �y�n�ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaLowerHouse)) {
				panaHousingRequestInfo.setLandAreaLower(BigDecimal.valueOf(Long.valueOf(this.landAreaLowerHouse)));
			} else {
				panaHousingRequestInfo.setLandAreaLower(null);
			}
		}
		// �y�n�ʐρE�����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaLowerLand)) {
				panaHousingRequestInfo.setLandAreaLower(BigDecimal.valueOf(Long.valueOf(this.landAreaLowerLand)));
			} else {
				panaHousingRequestInfo.setLandAreaLower(null);
			}
		}
		// �y�n�ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaUpperHouse)) {
				panaHousingRequestInfo.setLandAreaUpper(BigDecimal.valueOf(Long.valueOf(this.landAreaUpperHouse)));
			} else {
				panaHousingRequestInfo.setLandAreaUpper(null);
			}
		}
		// �y�n�ʐρE����l
		if (PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.landAreaUpperLand)) {
				panaHousingRequestInfo.setLandAreaUpper(BigDecimal.valueOf(Long.valueOf(this.landAreaUpperLand)));
			} else {
				panaHousingRequestInfo.setLandAreaUpper(null);
			}
		}

		// �z�N��
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.compDateMansion) && !"999".equals(this.compDateMansion)) {
				panaHousingRequestInfo.setBuiltMonth(Integer.valueOf(this.compDateMansion));
			} else {
				panaHousingRequestInfo.setBuiltMonth(null);
			}
		}

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			if (!StringValidateUtil.isEmpty(this.compDateHouse) && !"999".equals(this.compDateHouse)) {
				panaHousingRequestInfo.setBuiltMonth(Integer.valueOf(this.compDateHouse));
			} else {
				panaHousingRequestInfo.setBuiltMonth(null);
			}
		}

		// ���t�H�[�����i���݂Ō�������
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			panaHousingRequestInfo.setUseReform(this.reformCheckMansion);
		}

		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			panaHousingRequestInfo.setUseReform(this.reformCheckHouse);
		}

		// �s���{��CD
		setPrefCd(this.getPrefCd());
		
		// �������CD
		setHousingKindCd(this.getHousingKindCd());

		// �Ԏ��CD
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			String strLayout = "";
			if (this.layoutCdMansion != null) {
				for (int i = 0; i < this.layoutCdMansion.length; i++) {
					if ("".equals(strLayout)) {
						strLayout = this.layoutCdMansion[i];
					} else {
						strLayout = strLayout + "," + this.layoutCdMansion[i];
					}
				}
				super.setLayoutCd(strLayout);
			}

		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			String strLayout = "";
			if (this.layoutCdHouse != null) {
				for (int i = 0; i < this.layoutCdHouse.length; i++) {
					if ("".equals(strLayout)) {
						strLayout = this.layoutCdHouse[i];
					} else {
						strLayout = strLayout + "," + this.layoutCdHouse[i];
					}
				}
				super.setLayoutCd(strLayout);
			}
		}

		// �������߂̃|�C���gCD
		if (PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(this.getHousingKindCd())) {
			String strIconCd = "";
			if (this.iconCdMansion != null) {
				for (int i = 0; i < this.iconCdMansion.length; i++) {
					if ("".equals(strIconCd)) {
						strIconCd = this.iconCdMansion[i];
					} else {
						strIconCd = strIconCd + "," + this.iconCdMansion[i];
					}
				}
				super.setPartSrchCd(strIconCd);
			}
		}
		if (PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(this.getHousingKindCd())) {
			String strIconCd = "";
			if (this.iconCdHouse != null) {
				for (int i = 0; i < this.iconCdHouse.length; i++) {
					if ("".equals(strIconCd)) {
						strIconCd = this.iconCdHouse[i];
					} else {
						strIconCd = strIconCd + "," + this.iconCdHouse[i];
					}
				}
				super.setPartSrchCd(strIconCd);
			}
		}

		Date sysDate = new Date();
		housingRequestInfo.setUpdDate(sysDate);
		housingRequestInfo.setUpdUserId(userId);
	}

	@Override
	public void setHousingKindCd(String housingKindCd) {
		// ���l�ȊO�̕s���ȃp�����[�^�����͂��ꂽ�ꍇ�ɗ�O�����������邽�߂ɁA���l�ɕϊ�
		if (!StringValidateUtil.isEmpty(housingKindCd)) {
			Integer.valueOf(housingKindCd);
		}
		super.setHousingKindCd(housingKindCd);
	}

	@Override
	public void setPrefCd(String prefCd) {
		// ���l�ȊO�̕s���ȃp�����[�^�����͂��ꂽ�ꍇ�ɗ�O�����������邽�߂ɁA���l�ɕϊ�
		if (!StringValidateUtil.isEmpty(prefCd)) {
			Integer.valueOf(prefCd);
		}
		super.setPrefCd(prefCd);
	}
		
}

