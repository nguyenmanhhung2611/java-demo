package jp.co.transcosmos.dm3.adminCore.request.form;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.transcosmos.dm3.core.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

public class HousingRequestForm {

	/** �������N�G�X�gID */
	private String housingRequestId;

	/** ���[�UID */
	private String userId;

	/** ���N�G�X�g���� */
	private String requestName;

	/** ����/���i�E�����l */
	private Long priceLower;

	/** ����/���i�E����l */
	private Long priceUpper;

	/** ��L�ʐρE�����l */
	private BigDecimal personalAreaLower;

	/** ��L�ʐρE����l */
	private BigDecimal personalAreaUpper;

	/** �s���{��CD */
	private String prefCd;

	/** �s�撬��CD */
	private String addressCd;

	/** �H��CD */
	private String routeCd;

	/** �wCD */
	private String stationCd;

	/** �������CD */
	private String housingKindCd;

	/** �Ԏ��CD */
	private String layoutCd;

	/** ����������CD */
	private String partSrchCd;

	/**
	 * �������N�G�X�gID ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �������N�G�X�gID
	 */
	public String getHousingRequestId() {
		return housingRequestId;
	}

	/**
	 * �������N�G�X�gID ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param housingRequestId �������N�G�X�gID
	 */
	public void setHousingRequestId(String housingRequestId) {
		this.housingRequestId = housingRequestId;
	}

	/**
	 * ���[�UID ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���[�UID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ���[�UID ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param userId ���[�UID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ���N�G�X�g���� ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���N�G�X�g����
	 */

	public String getRequestName() {
		return requestName;
	}

	/**
	 * ���N�G�X�g���� ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param housingRequestName ���N�G�X�g����
	 */
	public void setRequestName(String housingRequestName) {
		this.requestName = housingRequestName;
	}

	/**
	 * ����/���i�E�����l ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����/���i�E�����l
	 */

	public Long getPriceLower() {
		return priceLower;
	}

	/**
	 * ����/���i�E�����l ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param priceLower ����/���i�E�����l
	 */
	public void setPriceLower(Long priceLower) {
		this.priceLower = priceLower;
	}

	/**
	 * ����/���i�E����l ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����/���i�E����l
	 */

	public Long getPriceUpper() {
		return priceUpper;
	}

	/**
	 * ����/���i�E����l ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param priceUpper ����/���i�E����l
	 */
	public void setPriceUpper(Long priceUpper) {
		this.priceUpper = priceUpper;
	}

	/**
	 * ��L�ʐρE�����l ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ��L�ʐρE�����l
	 */

	public BigDecimal getPersonalAreaLower() {
		return personalAreaLower;
	}

	/**
	 * ��L�ʐρE�����l ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param personalAreaLower ��L�ʐρE�����l
	 */
	public void setPersonalAreaLower(BigDecimal personalAreaLower) {
		this.personalAreaLower = personalAreaLower;
	}

	/**
	 * ��L�ʐρE����l ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ��L�ʐρE����l
	 */

	public BigDecimal getPersonalAreaUpper() {
		return personalAreaUpper;
	}

	/**
	 * ��L�ʐρE����l ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param personalAreaUpper ��L�ʐρE����l
	 */
	public void setPersonalAreaUpper(BigDecimal personalAreaUpper) {
		this.personalAreaUpper = personalAreaUpper;
	}

	/**
	 * �s���{��CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �s���{��CD
	 */
	public String getPrefCd() {
		return prefCd;
	}

	/**
	 * �s���{��CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param prefCd �s���{��CD
	 */
	public void setPrefCd(String prefCd) {
		this.prefCd = prefCd;
	}

	/**
	 * �s�撬��CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �s�撬��CD
	 */
	public String getAddressCd() {
		return addressCd;
	}

	/**
	 * �s�撬��CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param prefCd �s�撬��CD
	 */
	public void setAddressCd(String addressCd) {
		this.addressCd = addressCd;
	}

	/**
	 * ����CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����CD
	 */
	public String getRouteCd() {
		return routeCd;
	}

	/**
	 * ����CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param routeCd ����CD
	 */
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}

	/**
	 * �wCD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �wCD
	 */
	public String getStationCd() {
		return stationCd;
	}

	/**
	 * �wCD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param stationCd �wCD
	 */
	public void setStationCd(String stationCd) {
		this.stationCd = stationCd;
	}

	/**
	 * �������CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �������CD
	 */
	public String getHousingKindCd() {
		return housingKindCd;
	}

	/**
	 * �������CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param housingKindCd �������CD
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * �Ԏ�CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return �Ԏ�CD
	 */
	public String getLayoutCd() {
		return layoutCd;
	}

	/**
	 * �Ԏ�CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param layoutCd �Ԏ�CD
	 */
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}

	/**
	 * ����������CD ���擾����B<br/>
	 * <br/>
	 * 
	 * @return ����������CD
	 */
	public String getPartSrchCd() {
		return partSrchCd;
	}

	/**
	 * ����������CD ��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param layoutCd ����������CD
	 */
	public void setPartSrchCd(String partSrchCd) {
		this.partSrchCd = partSrchCd;
	}

	/**
	 * �����œn���ꂽ�������N�G�X�g���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param userId ���[�UID
	 * @param housingRequestInfo �������N�G�X�g���
	 */
	public void copyToHousingRequestInfo(String userId, HousingRequestInfo housingRequestInfo) {

		if (StringValidateUtil.isEmpty(userId)) {
			return;
		}

		housingRequestInfo.setUserId(userId);
		housingRequestInfo.setRequestName(this.requestName);
		housingRequestInfo.setPriceLower(this.priceLower);
		housingRequestInfo.setPriceUpper(this.priceUpper);
		housingRequestInfo.setPersonalAreaLower(this.personalAreaLower);
		housingRequestInfo.setPersonalAreaUpper(this.personalAreaUpper);

		Date sysDate = new Date();
		housingRequestInfo.setUpdDate(sysDate);
		housingRequestInfo.setUpdUserId(userId);
	}

}
