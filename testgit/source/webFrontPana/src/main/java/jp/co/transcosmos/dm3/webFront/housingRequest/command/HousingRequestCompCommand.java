package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonConstant;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.mail.ReplacingMail;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

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
public class HousingRequestCompCommand implements Command {

    /** ���ʃp�����[�^�I�u�W�F�N�g */
    private PanaCommonParameters commonParameters;

	/** �������p Model �I�u�W�F�N�g */
	private HousingRequestManage housingRequestManage;
	/** �������p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestInsertMansion;
	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestInsertHouse;
	/** �V�K�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestInsertLand;

	/** �X�V�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestUpdateMansion;
	/** �X�V�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestUpdateHouse;
	/** �X�V�ʒm���[���e���v���[�g */
	private ReplacingMail sendRequestUpdateLand;

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
	 * @param sendUpdateTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestInsertMansion(
			ReplacingMail sendRequestInsertMansion) {
		this.sendRequestInsertMansion = sendRequestInsertMansion;
	}

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestInsertHouse(
			ReplacingMail sendRequestInsertHouse) {
		this.sendRequestInsertHouse = sendRequestInsertHouse;
	}

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendUpdateTemplate
	 */
	public void setSendRequestInsertLand(
			ReplacingMail sendRequestInsertLand) {
		this.sendRequestInsertLand = sendRequestInsertLand;
	}

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendInsertTemplate
	 */
	public void setSendRequestUpdateMansion(
			ReplacingMail sendRequestUpdateMansion) {
		this.sendRequestUpdateMansion = sendRequestUpdateMansion;
	}

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendInsertTemplate
	 */
	public void setSendRequestUpdateHouse(
			ReplacingMail sendRequestUpdateHouse) {
		this.sendRequestUpdateHouse = sendRequestUpdateHouse;
	}

	/**
	 * @param sendUpdateTemplate �Z�b�g���� sendInsertTemplate
	 */
	public void setSendRequestUpdateLand(
			ReplacingMail sendRequestUpdateLand) {
		this.sendRequestUpdateLand = sendRequestUpdateLand;
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
		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		String command = housingRequestForm.getCommand();		

		if (command != null && command.equals("redirect")) {
			model.put("housingRequestForm", housingRequestForm);
			return new ModelAndView("comp", model);
		}

		if (!housingRequestForm.validate(errors)) {

			// �s���{�����X�g�̐ݒ�
			List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
			model.put("prefMstList", prefMstList);

			// �o���f�[�V�����G���[����
			model.put("errors", errors);

			model.put("housingRequestForm", housingRequestForm);

			return new ModelAndView("validFail", model);
		}

		keyToValue(housingRequestForm);

		model.put("housingRequestForm", housingRequestForm);

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

		if("update".equals(housingRequestForm.getModel())){
			this.housingRequestManage.updateRequest(userId, housingRequestForm);

			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestUpdateMansion.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateMansion.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateMansion.setParameter("housingKindName", "�}���V����");
				this.sendRequestUpdateMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateMansion.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
					this.sendRequestUpdateMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"���~");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"���~");
				}else{
					this.sendRequestUpdateMansion.setParameter("priceUpper", "");
				}

				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "");
				}

				// ���t�H�[�����i���݂Ō�������
				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestUpdateMansion.setParameter("reformCheckValue", "");
				}

				// ��L�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
					this.sendRequestUpdateMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
				}else{
					this.sendRequestUpdateMansion.setParameter("personalAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("perAreaFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateMansion.setParameter("perAreaFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestUpdateMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
				}else{
					this.sendRequestUpdateMansion.setParameter("personalAreaUpper", "");
				}

				// �z�N��
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
					this.sendRequestUpdateMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" �N�ȓ�");
				}else{
					this.sendRequestUpdateMansion.setParameter("compDate", "");
				}

				// �Ԏ��
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestUpdateMansion.setParameter("layoutName", layoutName);

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
				this.sendRequestUpdateMansion.setParameter("iconName", iconName);

				// ���[�����M
				this.sendRequestUpdateMansion.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestUpdateHouse.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateHouse.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateHouse.setParameter("housingKindName", "�ˌ�");
				this.sendRequestUpdateHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateHouse.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateHouse.setParameter("commonParameters", CommonParameters.getInstance(request));
				
				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"���~");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"���~");
				}else{
					this.sendRequestUpdateHouse.setParameter("priceUpper", "");
				}

				// ���t�H�[�����i���݂Ō�������
				if("1".equals(housingRequestForm.getReformCheckHouse())){
					this.sendRequestUpdateHouse.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestUpdateHouse.setParameter("reformCheckValue", "");
				}

				// �����ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("buildingAreaUpper", "");
				}

				// �y�n�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
					this.sendRequestUpdateHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("landFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateHouse.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestUpdateHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
				}else{
					this.sendRequestUpdateHouse.setParameter("landAreaUpper", "");
				}

				// �z�N��
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse()) && !"999".equals(housingRequestForm.getCompDateHouse())){
					this.sendRequestUpdateHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" �N�ȓ�");
				}else{
					this.sendRequestUpdateHouse.setParameter("compDate", "");
				}

				// �Ԏ��
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestUpdateHouse.setParameter("layoutName", layoutName);

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
				this.sendRequestUpdateHouse.setParameter("iconName", iconName);

				// ���[�����M
				this.sendRequestUpdateHouse.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestUpdateLand.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestUpdateLand.setParameter("email", loginUser.getEmail());
				this.sendRequestUpdateLand.setParameter("housingKindName", "�y�n");
				this.sendRequestUpdateLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestUpdateLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestUpdateLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestUpdateLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestUpdateLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestUpdateLand.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestUpdateLand.setParameter("commonParameters", CommonParameters.getInstance(request));

				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
					this.sendRequestUpdateLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"���~");
				}else{
					this.sendRequestUpdateLand.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestUpdateLand.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateLand.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestUpdateLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"���~");
				}else{
					this.sendRequestUpdateLand.setParameter("priceUpper", "");
				}

				// �����ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
					this.sendRequestUpdateLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("buildingFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("buildingAreaUpper", "");
				}

				// �y�n�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
					this.sendRequestUpdateLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("landFromTo", "�@�`�@");
				}else{
					this.sendRequestUpdateLand.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestUpdateLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
				}else{
					this.sendRequestUpdateLand.setParameter("landAreaUpper", "");
				}

				// ���[�����M
				this.sendRequestUpdateLand.send();
			}


		}

		if("insert".equals(housingRequestForm.getModel())){
			// �o�^��������`�F�b�N
			int nowCnt = this.housingRequestManage.searchRequestCnt(userId);
			if (!(nowCnt < this.commonParameters.getMaxHousingRequestCnt())) {

				ValidationFailure vf = new ValidationFailure(
				        "maxAddError", String.valueOf(this.commonParameters.getMaxHousingRequestCnt()), "", null);
				errors.add(vf);
				// �G���[����
				// �G���[�I�u�W�F�N�g�ƁA�t�H�[���I�u�W�F�N�g��ModelAndView �ɓn���Ă���
				model.put("errors", errors);
				// �s���{�����X�g�̐ݒ�
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				model.put("prefMstList", prefMstList);
				// ���X�g���擾����
				model.put("housingRequestForm", housingRequestForm);
				return new ModelAndView("validFail", model);
			}
			String housingRequestId = this.housingRequestManage.addRequest(userId, housingRequestForm);

			housingRequestForm.setHousingRequestId(housingRequestId);

			if(PanaCommonConstant.HOUSING_KIND_CD_MANSION.equals(housingRequestForm.getHousingKindCd())){
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestInsertMansion.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertMansion.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertMansion.setParameter("housingKindName", "�}���V����");
				this.sendRequestInsertMansion.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertMansion.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertMansion.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertMansion.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertMansion.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertMansion.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertMansion.setParameter("commonParameters", CommonParameters.getInstance(request));

				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())){
					this.sendRequestInsertMansion.setParameter("priceLower", housingRequestForm.getPriceLowerMansion()+"���~");
				}else{
					this.sendRequestInsertMansion.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestInsertMansion.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertMansion.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())){
					this.sendRequestInsertMansion.setParameter("priceUpper", housingRequestForm.getPriceUpperMansion()+"���~");
				}else{
					this.sendRequestInsertMansion.setParameter("priceUpper", "");
				}

				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "");
				}

				// ���t�H�[�����i���݂Ō�������
				if("1".equals(housingRequestForm.getReformCheckMansion())){
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestInsertMansion.setParameter("reformCheckValue", "");
				}

				// ��L�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())){
					this.sendRequestInsertMansion.setParameter("personalAreaLower", housingRequestForm.getPersonalAreaLowerMansion()+"m2");
				}else{
					this.sendRequestInsertMansion.setParameter("personalAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion()) || !StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestInsertMansion.setParameter("perAreaFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertMansion.setParameter("perAreaFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())){
					this.sendRequestInsertMansion.setParameter("personalAreaUpper", housingRequestForm.getPersonalAreaUpperMansion()+"m2");
				}else{
					this.sendRequestInsertMansion.setParameter("personalAreaUpper", "");
				}

				// �z�N��
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateMansion()) && !"999".equals(housingRequestForm.getCompDateMansion())){
					this.sendRequestInsertMansion.setParameter("compDate", housingRequestForm.getCompDateMansion()+" �N�ȓ�");
				}else{
					this.sendRequestInsertMansion.setParameter("compDate", "");
				}

				// �Ԏ��
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();

					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestInsertMansion.setParameter("layoutName", layoutName);

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
				this.sendRequestInsertMansion.setParameter("iconName", iconName);
				// ���[�����M
				this.sendRequestInsertMansion.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_HOUSE.equals(housingRequestForm.getHousingKindCd())){
				
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestInsertHouse.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertHouse.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertHouse.setParameter("housingKindName", "�ˌ�");
				this.sendRequestInsertHouse.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertHouse.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertHouse.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertHouse.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertHouse.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertHouse.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertHouse.setParameter("commonParameters", CommonParameters.getInstance(request));

				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())){
					this.sendRequestInsertHouse.setParameter("priceLower", housingRequestForm.getPriceLowerHouse()+"���~");
				}else{
					this.sendRequestInsertHouse.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestInsertHouse.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertHouse.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())){
					this.sendRequestInsertHouse.setParameter("priceUpper", housingRequestForm.getPriceUpperHouse()+"���~");
				}else{
					this.sendRequestInsertHouse.setParameter("priceUpper", "");
				}

				// ���t�H�[�����i���݂Ō�������
				if("1".equals(housingRequestForm.getReformCheckHouse())){
					this.sendRequestInsertHouse.setParameter("reformCheckValue", "�i���t�H�[�����i���݂Ō�������j");
				}else{
					this.sendRequestInsertHouse.setParameter("reformCheckValue", "");
				}

				// �����ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())){
					this.sendRequestInsertHouse.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("buildingFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("buildingAreaUpper", "");
				}

				// �y�n�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())){
					this.sendRequestInsertHouse.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("landFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertHouse.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())){
					this.sendRequestInsertHouse.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperHouse()+"m2");
				}else{
					this.sendRequestInsertHouse.setParameter("landAreaUpper", "");
				}

				// �z�N��
				if(!StringUtils.isEmpty(housingRequestForm.getCompDateHouse())  && !"999".equals(housingRequestForm.getCompDateHouse())){
					this.sendRequestInsertHouse.setParameter("compDate", housingRequestForm.getCompDateHouse()+" �N�ȓ�");
				}else{
					this.sendRequestInsertHouse.setParameter("compDate", "");
				}

				// �Ԏ��
				String layoutName = "";

				Iterator<String> layoutIte = this.codeLookupManager
						.getKeysByLookup("layoutCd");

				while (layoutIte.hasNext()) {
					String layoutCdName = layoutIte.next();
					if(!StringUtils.isEmpty(housingRequestForm.getLayoutCd())){
						for(int i=0;i<housingRequestForm.getLayoutCd().split(",").length;i++){

							if (layoutCdName.equals(housingRequestForm.getLayoutCd().split(",")[i])) {

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
				this.sendRequestInsertHouse.setParameter("layoutName", layoutName);

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
				this.sendRequestInsertHouse.setParameter("iconName", iconName);

				// ���[�����M
				this.sendRequestInsertHouse.send();
			}

			if(PanaCommonConstant.HOUSING_KIND_CD_GROUND.equals(housingRequestForm.getHousingKindCd())){
				// ���[���e���v���[�g�Ŏg�p����p�����[�^��ݒ肷��B
				this.sendRequestInsertLand.setParameter("housingRequestForm", housingRequestForm);
				this.sendRequestInsertLand.setParameter("email", loginUser.getEmail());
				this.sendRequestInsertLand.setParameter("housingKindName", "�y�n");
				this.sendRequestInsertLand.setParameter("prefName", this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd()));
				this.sendRequestInsertLand.setParameter("mypagePath", this.commonParameters.getMypageTopUrl());
				this.sendRequestInsertLand.setParameter("addressName", this.panaCommonManage.getAddressName(housingRequestForm.getAddressCd()));
				this.sendRequestInsertLand.setParameter("stationName", this.panaCommonManage.getStationName(housingRequestForm.getStationCd()));
				this.sendRequestInsertLand.setParameter("rootName", this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd()));
				this.sendRequestInsertLand.setParameter("hopeName", housingRequestForm.getHopeRequestTest());
				this.sendRequestInsertLand.setParameter("commonParameters", CommonParameters.getInstance(request));

				// �\�Z
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())){
					this.sendRequestInsertLand.setParameter("priceLower", housingRequestForm.getPriceLowerLand()+"���~");
				}else{
					this.sendRequestInsertLand.setParameter("priceLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestInsertLand.setParameter("priceFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertLand.setParameter("priceFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())){
					this.sendRequestInsertLand.setParameter("priceUpper", housingRequestForm.getPriceUpperLand()+"���~");
				}else{
					this.sendRequestInsertLand.setParameter("priceUpper", "");
				}

				// �����ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())){
					this.sendRequestInsertLand.setParameter("buildingAreaLower", housingRequestForm.getBuildingAreaLowerLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("buildingAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("buildingFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertLand.setParameter("buildingFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("buildingAreaUpper", housingRequestForm.getBuildingAreaUpperLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("buildingAreaUpper", "");
				}

				// �y�n�ʐ�
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())){
					this.sendRequestInsertLand.setParameter("landAreaLower", housingRequestForm.getLandAreaLowerLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("landAreaLower", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand()) || !StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("landFromTo", "�@�`�@");
				}else{
					this.sendRequestInsertLand.setParameter("landFromTo", "");
				}
				if(!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())){
					this.sendRequestInsertLand.setParameter("landAreaUpper", housingRequestForm.getLandAreaUpperLand()+"m2");
				}else{
					this.sendRequestInsertLand.setParameter("landAreaUpper", "");
				}

				// ���[�����M
				this.sendRequestInsertLand.send();
			}

		}

		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

	protected void keyToValue(PanaHousingRequestForm housingRequestForm){

		if("01".equals(housingRequestForm.getHousingKindCd())){

			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerMansion())) {
					// �\�Z�E����
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerMansion(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperMansion())) {
					// �\�Z�E���
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperMansion(value);

					}
				}
			}

			// ��L�ʐ�
			Iterator<String> personalAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (personalAreaIte.hasNext()) {

				String personalArea = personalAreaIte.next();
				if (personalArea.equals(housingRequestForm.getPersonalAreaLowerMansion())) {
					// ��L�ʐρE����
					if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())) {

						String value = this.codeLookupManager.lookupValue("area", personalArea);
						housingRequestForm.setPersonalAreaLowerMansion(value);

					}
				}

				if (personalArea.equals(housingRequestForm.getPersonalAreaUpperMansion())) {
					// ��L�ʐρE���
					if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())) {

						String value = this.codeLookupManager.lookupValue("area", personalArea);

						housingRequestForm.setPersonalAreaUpperMansion(value);

					}
				}
			}

		}

		if("02".equals(housingRequestForm.getHousingKindCd())){

			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerHouse())) {
					// �\�Z�E����
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerHouse(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperHouse())) {
					// �\�Z�E���
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperHouse(value);

					}
				}
			}

			// �����ʐ�
			Iterator<String> buildingAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				if (buildingArea.equals(housingRequestForm.getBuildingAreaLowerHouse())) {
					// �����ʐρE����
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);
						housingRequestForm.setBuildingAreaLowerHouse(value);

					}
				}

				if (buildingArea.equals(housingRequestForm.getBuildingAreaUpperHouse())) {
					// �����ʐρE���
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);

						housingRequestForm.setBuildingAreaUpperHouse(value);

					}
				}
			}

			// �y�n�ʐ�
			Iterator<String> landAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				if (landArea.equals(housingRequestForm.getLandAreaLowerHouse())) {
					// �����ʐρE����
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);
						housingRequestForm.setLandAreaLowerHouse(value);

					}
				}

				if (landArea.equals(housingRequestForm.getLandAreaUpperHouse())) {
					// �����ʐρE���
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);

						housingRequestForm.setLandAreaUpperHouse(value);

					}
				}
			}

		}

		if("03".equals(housingRequestForm.getHousingKindCd())){

			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager
					.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				if (price.equals(housingRequestForm.getPriceLowerLand())) {
					// �\�Z�E����
					if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())) {

						String value = this.codeLookupManager.lookupValue("price", price);
						housingRequestForm.setPriceLowerLand(value);

					}
				}

				if (price.equals(housingRequestForm.getPriceUpperLand())) {
					// �\�Z�E���
					if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())) {

						String value = this.codeLookupManager.lookupValue("price", price);

						housingRequestForm.setPriceUpperLand(value);

					}
				}
			}

			// �����ʐ�
			Iterator<String> buildingAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				if (buildingArea.equals(housingRequestForm.getBuildingAreaLowerLand())) {
					// �����ʐρE����
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);
						housingRequestForm.setBuildingAreaLowerLand(value);

					}
				}

				if (buildingArea.equals(housingRequestForm.getBuildingAreaUpperLand())) {
					// �����ʐρE���
					if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())) {

						String value = this.codeLookupManager.lookupValue("area", buildingArea);

						housingRequestForm.setBuildingAreaUpperLand(value);

					}
				}
			}

			// �y�n�ʐ�
			Iterator<String> landAreaIte = this.codeLookupManager
					.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				if (landArea.equals(housingRequestForm.getLandAreaLowerLand())) {
					// �����ʐρE����
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);
						housingRequestForm.setLandAreaLowerLand(value);

					}
				}

				if (landArea.equals(housingRequestForm.getLandAreaUpperLand())) {
					// �����ʐρE���
					if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())) {

						String value = this.codeLookupManager.lookupValue("area", landArea);

						housingRequestForm.setLandAreaUpperLand(value);

					}
				}
			}

		}

	}

}
