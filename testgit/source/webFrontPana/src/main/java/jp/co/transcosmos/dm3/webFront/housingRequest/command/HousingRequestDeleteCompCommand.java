package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * �������N�G�X�g���͉��
 *
 * <pre>
 * �y���A���� View ���z
 *    �E"success" : ����I��
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   ��		  2015.04.22    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HousingRequestDeleteCompCommand implements Command {

	/** �������p Model �I�u�W�F�N�g */
	private HousingRequestManage housingRequestManage;

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** �������p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �폜�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestDeleteMansion;

	/** �폜�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestDeleteHouse;

	/** �폜�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestDeleteLand;

    /**
     * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
     * <br/>
     * @param commonParameters ���ʃp�����[�^�I�u�W�F�N�g
     */
    public void setCommonParameters(PanaCommonParameters commonParameters) {
        this.commonParameters = commonParameters;
    }

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param PanaCommonManage ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
	}

	/**
	 * @param sendDeleteTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestDeleteMansion(
			ReplacingMail sendRequestDeleteMansion) {
		this.sendRequestDeleteMansion = sendRequestDeleteMansion;
	}

	/**
	 * @param sendDeleteTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestDeleteHouse(
			ReplacingMail sendRequestDeleteHouse) {
		this.sendRequestDeleteHouse = sendRequestDeleteHouse;
	}

	/**
	 * @param sendDeleteTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestDeleteLand(
			ReplacingMail sendRequestDeleteLand) {
		this.sendRequestDeleteLand = sendRequestDeleteLand;
	}

	/**
	 * �������N�G�X�g���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param housingRequestManage
	 *            �������N�G�X�g���p Model �I�u�W�F�N�g
	 */
	public void setHousingRequestManage(HousingRequestManage housingRequestManage) {
		this.housingRequestManage = housingRequestManage;
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
	 * �������N�G�X�g���͉�ʕ\������<br>
	 * <br>
	 *
	 * @param request
	 *            HTTP ���N�G�X�g
	 * @param response
	 *            HTTP ���X�|���X
	 */
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory
				.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaHousingRequestForm housingRequestForm = factory
				.createPanaHousingRequestForm(request);

		String command = housingRequestForm.getCommand();
		if (command != null && command.equals("redirect")) {
			model.put("housingRequestForm", housingRequestForm);
			return new ModelAndView("comp", model);
		}

		// �`�B���̕������N�G�X�gID�ihousing_request_id�j
		if(StringUtils.isEmpty(housingRequestForm.getHousingRequestId())){
			throw new RuntimeException("�������N�G�X�gID���w�肳��Ă��܂���.");
		}

		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request, response);
		// ���[�UID���擾
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		}else{
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId().toString();
			model.put("loginFlg", 1);
		}

		this.housingRequestManage.delRequest(userId, housingRequestForm);

		// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
		if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteMansion.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteMansion.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteMansion.setParameter("housingKindName", "�}���V����");
			this.sendRequestDeleteMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

			// �\�Z
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
				this.sendRequestDeleteMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"���~");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("priceFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"���~");
			}else{
				this.sendRequestDeleteMansion.setParameter("priceUpper", "");
			}

			if("1".equals(housingRequestForm.getReformCheckMansion())){
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
			}else{
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "");
			}

			// ���t�H�[�����i���݂Ō�������
			if("1".equals(housingRequestForm.getReformCheckMansion())){
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
			}else{
				this.sendRequestDeleteMansion.setParameter("reformCheckValue", "");
			}

			// ��L�ʐ�
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
				this.sendRequestDeleteMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
			}else{
				this.sendRequestDeleteMansion.setParameter("personalAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("perAreaFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteMansion.setParameter("perAreaFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
				this.sendRequestDeleteMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
			}else{
				this.sendRequestDeleteMansion.setParameter("personalAreaUpper", "");
			}

			// �z�N��
			if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
				this.sendRequestDeleteMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" �N�ȓ�");
			}else{
				this.sendRequestDeleteMansion.setParameter("compDate", "");
			}

			// �Ԏ��
			String layoutName = "";

			Iterator<String> layoutIte = this.codeLookupManager
					.getKeysByLookup("layoutCd");

			while (layoutIte.hasNext()) {
				String layoutCdName = layoutIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getLayoutCdMansion())){

					for(int i=0;i<housingRequestForm.getLayoutCdMansion().length;i++){

						if (layoutCdName.equals(housingRequestForm.getLayoutCdMansion()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"layoutCd", layoutCdName);

							if(StringUtils.isEmpty(layoutName)){
								layoutName = layoutName + temp;
							}else{
								layoutName = layoutName + ", " + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteMansion.setParameter("layoutName", layoutName);

			// �������߂̃|�C���g
			String iconName = "";

			Iterator<String> iconIte = this.codeLookupManager
					.getKeysByLookup("recommend_point_icon_list");

			while (iconIte.hasNext()) {
				String iconCdName = iconIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getIconCdMansion())){
					for(int i=0;i<housingRequestForm.getIconCdMansion().length;i++){

						if (iconCdName.equals(housingRequestForm.getIconCdMansion()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"recommend_point_icon_list", iconCdName);

							if(StringUtils.isEmpty(iconName)){
								iconName = iconName + temp;
							}else{
								iconName = iconName + "," + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteMansion.setParameter("iconName", iconName);

			// ���[�����M
			this.sendRequestDeleteMansion.send();
		}
		if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteHouse.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteHouse.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteHouse.setParameter("housingKindName", "�ˌ�");
			this.sendRequestDeleteHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteHouse.setParameter("commonParameters", CommonParameters.getInstance(request));

			// �\�Z
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"���~");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("priceFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"���~");
			}else{
				this.sendRequestDeleteHouse.setParameter("priceUpper", "");
			}

			// ���t�H�[�����i���݂Ō�������
			if("1".equals(housingRequestForm.getReformCheckHouse())){
				this.sendRequestDeleteHouse.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
			}else{
				this.sendRequestDeleteHouse.setParameter("reformCheckValue", "");
			}

			// �����ʐ�
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("buildingAreaUpper", "");
			}

			// �y�n�ʐ�
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
				this.sendRequestDeleteHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("landAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("landFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteHouse.setParameter("landFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
				this.sendRequestDeleteHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
			}else{
				this.sendRequestDeleteHouse.setParameter("landAreaUpper", "");
			}

			// �z�N��
			if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse()) && !"999".equals(housingRequestForm.getCompDateMansion())){
				this.sendRequestDeleteHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" �N�ȓ�");
			}else{
				this.sendRequestDeleteHouse.setParameter("compDate", "");
			}

			// �Ԏ��
			String layoutName = "";

			Iterator<String> layoutIte = this.codeLookupManager
					.getKeysByLookup("layoutCd");

			while (layoutIte.hasNext()) {
				String layoutCdName = layoutIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getLayoutCdHouse())){
					for(int i=0;i<housingRequestForm.getLayoutCdHouse().length;i++){

						if (layoutCdName.equals(housingRequestForm.getLayoutCdHouse()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"layoutCd", layoutCdName);

							if(StringUtils.isEmpty(layoutName)){
								layoutName = layoutName + temp;
							}else{
								layoutName = layoutName + ", " + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteHouse.setParameter("layoutName", layoutName);

			// �������߂̃|�C���g
			String iconName = "";

			Iterator<String> iconIte = this.codeLookupManager
					.getKeysByLookup("recommend_point_icon_list");

			while (iconIte.hasNext()) {
				String iconCdName = iconIte.next();

				if(!StringUtils.isEmpty(housingRequestForm.getIconCdHouse())){
					for(int i=0;i<housingRequestForm.getIconCdHouse().length;i++){

						if (iconCdName.equals(housingRequestForm.getIconCdHouse()[i])) {

							String temp = this.codeLookupManager.lookupValue(
									"recommend_point_icon_list", iconCdName);

							if(StringUtils.isEmpty(iconName)){
								iconName = iconName + temp;
							}else{
								iconName = iconName + "," + temp;
							}

						}

					}
				}

			}
			this.sendRequestDeleteHouse.setParameter("iconName", iconName);

			// ���[�����M
			this.sendRequestDeleteHouse.send();
		}
		if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
			this.sendRequestDeleteLand.setParameter("housingRequestForm", housingRequestForm);
			this.sendRequestDeleteLand.setParameter("email", loginUser.getEmail());
			this.sendRequestDeleteLand.setParameter("housingKindName", "�y�n");
			this.sendRequestDeleteLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
			this.sendRequestDeleteLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
			this.sendRequestDeleteLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
			this.sendRequestDeleteLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
			this.sendRequestDeleteLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
			this.sendRequestDeleteLand.setParameter("commonParameters", CommonParameters.getInstance(request));

			// �\�Z
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
				this.sendRequestDeleteLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"���~");
			}else{
				this.sendRequestDeleteLand.setParameter("priceLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
				this.sendRequestDeleteLand.setParameter("priceFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteLand.setParameter("priceFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
				this.sendRequestDeleteLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"���~");
			}else{
				this.sendRequestDeleteLand.setParameter("priceUpper", "");
			}

			// �����ʐ�
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
				this.sendRequestDeleteLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("buildingFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("buildingAreaUpper", "");
			}

			// �y�n�ʐ�
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
				this.sendRequestDeleteLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("landAreaLower", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("landFromTo", "�@�`�@");
			}else{
				this.sendRequestDeleteLand.setParameter("landFromTo", "");
			}
			if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
				this.sendRequestDeleteLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
			}else{
				this.sendRequestDeleteLand.setParameter("landAreaUpper", "");
			}
			// ���[�����M
			this.sendRequestDeleteLand.send();
		}




		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

}
