package jp.co.transcosmos.dm3.webFront.housingListMation.command;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingSearchForm;
import jp.co.transcosmos.dm3.form.FormPopulator;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.StringUtils;

/**
 * �����ꗗ���
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.04.10    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingListMatinoSearchCommand implements Command {

	/** �������p Model �I�u�W�F�N�g */
	private PanaHousingPartThumbnailProxy panaHousingManager;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;


	/** ������� */
	private String housingKindCd = "";

	/**
	 * ������ނ�ݒ肷��B<br>
	 *
	 * @param housingKindCd
	 *            �������
	 */
	public void setHousingKindCd(String housingKindCd) {
		this.housingKindCd = housingKindCd;
	}

	/**
	 * ���ʃR�[�h�ϊ��I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param codeLookupManager
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �������p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param PanaHousingManage
	 *            �������p Model �I�u�W�F�N�g
	 */
	public void setPanaHousingManager(
			PanaHousingPartThumbnailProxy panaHousingManager) {
		this.panaHousingManager = panaHousingManager;
	}

	/**
	 * �����ꗗ��ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaHousingSearchForm housingListMationForm = factory
				.createPanaHousingSearchForm();

		FormPopulator.populateFormBeanFromRequest(request,
				housingListMationForm);

		// �\�Z����
		String priceLower = request.getParameter("priceLower");
		housingListMationForm.setPriceLower(priceLower);
		// �\�Z���
		String priceUpper = request.getParameter("priceUpper");
		// �u���t�H�[�����i���݂Ō�������v�̃`�F�b�N���
		String reformPriceCheck = request.getParameter("reformPriceCheck");
		housingListMationForm.setReformPriceCheck(reformPriceCheck);
		// ��L�ʐω���
		String personalAreaLower = request.getParameter("personalAreaLower");
		// ��L�ʐϏ��
		String personalAreaUpper = request.getParameter("personalAreaUpper");
		// �����ʐω���
		String buildingAreaLower = request.getParameter("buildingAreaLower");
		// �����ʐϏ��
		String buildingAreaUpper = request.getParameter("buildingAreaUpper");
		// �y�n�ʐω���
		String landAreaLower = request.getParameter("landAreaLower");
		// �y�n�ʐϏ��
		String landAreaUpper = request.getParameter("landAreaUpper");
		// �Ԏ��
		String layoutCd = request.getParameter("layoutCd");
		// �z�N��
		String keyCompDate = request.getParameter("keyCompDate");
		if (!StringUtils.isNullOrEmpty(keyCompDate)) {
			housingListMationForm.setKeyCompDate(keyCompDate);
		}
		// �������߂̃|�C���g
		String iconCd = request.getParameter("iconCd");
		if (!StringUtils.isNullOrEmpty(iconCd)) {
			housingListMationForm.setKeyIconCd(iconCd);
		}
		// �w�k��
		String partSrchCdWalk = request.getParameter("partSrchCdWalkArray");
		if (!StringUtils.isNullOrEmpty(partSrchCdWalk)) {
			housingListMationForm.setPartSrchCdWalkArray(partSrchCdWalk);
		}
		// �����摜���A��������
		String partSrchCd = request.getParameter("partSrchCdArray");
		if (!StringUtils.isNullOrEmpty(partSrchCd)) {
			housingListMationForm.setPartSrchCdArray(partSrchCd);
		}
		// ���n����
		String moveinTiming = request.getParameter("moveinTiming");
		if (!StringUtils.isNullOrEmpty(moveinTiming)) {
			housingListMationForm.setMoveinTiming(moveinTiming);
		}
		// ���݊K��
		String partSrchCdFloor = request.getParameter("partSrchCdFloorArray");
		if (!StringUtils.isNullOrEmpty(partSrchCdFloor)) {
			housingListMationForm.setPartSrchCdFloorArray(partSrchCdFloor);
		}
		// ���r�ی�
		String insurExist = request.getParameter("insurExist");
		if (!StringUtils.isNullOrEmpty(insurExist)) {
			housingListMationForm.setInsurExist(insurExist);
		}
		// �����ԍ�
		String keyHousingCd = request.getParameter("keyHousingCd");
		if (!StringUtils.isNullOrEmpty(keyHousingCd)) {
			housingListMationForm.setKeyHousingCd(keyHousingCd);
		}
		// keyRouteCd
		String keyRouteCd = request.getParameter("keyRouteCd");
		housingListMationForm.setKeyRouteCd(keyRouteCd);
		// keyStationCd
		String keyStationCd = request.getParameter("keyStationCd");
		housingListMationForm.setKeyStationCd(keyStationCd);
		// keyAddressCd
		String keyAddressCd = request.getParameter("keyAddressCd");
		housingListMationForm.setKeyAddressCd(keyAddressCd);

		// �\�Z
		Iterator<String> priceIte = this.codeLookupManager
				.getKeysByLookup("price");

		while (priceIte.hasNext()) {

			String price = priceIte.next();
			if (price.equals(priceLower)) {
				// ����/���i�E�����i���������j
				if (!StringUtils.isNullOrEmpty(housingListMationForm
						.getPriceLower())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceLower(Long
							.valueOf(temp + "0000"));

				}
			}

			if (price.equals(priceUpper)) {
				// ����/���i�E����i���������j
				if (!StringUtils.isNullOrEmpty(housingListMationForm
						.getPriceUpper())) {

					String temp = this.codeLookupManager.lookupValue(
							"price", price);
					housingListMationForm.setKeyPriceUpper(Long
							.valueOf(temp + "0000"));
				}
			}
		}

		// ��L�ʐ�
		Iterator<String> personalAreaIte = this.codeLookupManager
				.getKeysByLookup("area");
		while (personalAreaIte.hasNext()) {
			String personalArea = personalAreaIte.next();
			if (personalArea.equals(personalAreaLower)) {
				// ��L�ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(personalAreaLower)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}
			if (personalArea.equals(personalAreaUpper)) {
				// ��L�ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(personalAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", personalArea);
					housingListMationForm.setKeyPersonalAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}
		}

		// �����ʐ�
		Iterator<String> buildingAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (buildingAreaIte.hasNext()) {

			String buildingArea = buildingAreaIte.next();
			if (buildingArea.equals(buildingAreaLower)) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(buildingAreaLower)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

			if (buildingArea.equals(buildingAreaUpper)) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(buildingAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", buildingArea);
					housingListMationForm.setKeyBuildingAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}

		// �y�n�ʐ�
		Iterator<String> landAreaIte = this.codeLookupManager
				.getKeysByLookup("area");

		while (landAreaIte.hasNext()) {

			String landArea = landAreaIte.next();
			if (landArea.equals(landAreaLower)) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(landAreaLower)) {
					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaLower(BigDecimal
							.valueOf(Double.valueOf(temp)));
				}
			}

			if (landArea.equals(landAreaUpper)) {
				// �����ʐρE�����i���������j
				if (!StringUtils.isNullOrEmpty(landAreaUpper)) {

					String temp = this.codeLookupManager.lookupValue(
							"area", landArea);
					housingListMationForm.setKeyLandAreaUpper(BigDecimal
							.valueOf(Double.valueOf(temp)));

				}
			}

		}
		// �Ԏ��
		housingListMationForm.setKeyLayoutCd(layoutCd);

		// �s���{��CD���擾����B
		String prefCd = request.getParameter("prefCd");

		housingListMationForm.setKeyHiddenFlg("0");
		housingListMationForm.setHousingKindCd(this.housingKindCd);
		housingListMationForm.setKeyPrefCd(prefCd);

		// �s���{��CD�ɂ��A�������̎擾
		int listSize = this.panaHousingManager
				.searchHousing(housingListMationForm);

		Map<String, String> map = null;
		map = new HashMap<String, String>();
		map.put("listSize", String.valueOf(listSize));

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", "listSize", map);
	}

}
