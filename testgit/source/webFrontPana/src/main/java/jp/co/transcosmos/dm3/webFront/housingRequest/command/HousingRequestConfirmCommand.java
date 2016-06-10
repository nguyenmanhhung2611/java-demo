package jp.co.transcosmos.dm3.webFront.housingRequest.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.transcosmos.dm3.command.Command;
import jp.co.transcosmos.dm3.core.model.HousingRequestManage;
import jp.co.transcosmos.dm3.core.model.housingRequest.HousingRequest;
import jp.co.transcosmos.dm3.core.model.mypage.MypageUserInterface;
import jp.co.transcosmos.dm3.core.util.FrontLoginUserUtils;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.corePana.constant.PanaCommonParameters;
import jp.co.transcosmos.dm3.corePana.model.PanaCommonManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingRequest;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingFormFactory;
import jp.co.transcosmos.dm3.corePana.model.housing.form.PanaHousingRequestForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
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
public class HousingRequestConfirmCommand implements Command {

	/** ���ʃp�����[�^�I�u�W�F�N�g */
	private PanaCommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �������p Model �I�u�W�F�N�g */
	private HousingRequestManage housingRequestManage;

	/**
	 * ���ʃp�����[�^�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param commonParameters
	 *            ���ʃp�����[�^�I�u�W�F�N�g
	 */
	public void setCommonParameters(PanaCommonParameters commonParameters) {
		this.commonParameters = commonParameters;
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

	/** �������p Model �I�u�W�F�N�g */
	private PanaCommonManage panaCommonManage;

	/**
	 * ���ʏ���p Model �I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param PanaCommonManage
	 *            ���ʏ���p Model �I�u�W�F�N�g
	 */
	public void setPanaCommonManage(PanaCommonManage panaCommonManage) {
		this.panaCommonManage = panaCommonManage;
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
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();

		PanaHousingFormFactory factory = PanaHousingFormFactory.getInstance(request);

		// �y�[�W�����p�̃t�H�[���I�u�W�F�N�g���쐬
		PanaHousingRequestForm housingRequestForm = factory.createPanaHousingRequestForm(request);

		System.out.println("startDate :" + housingRequestForm.getStartDate());
		System.out.println("endDate :" + housingRequestForm.getEndDate());

		// �o���f�[�V���������s
		List<ValidationFailure> errors = new ArrayList<ValidationFailure>();

		if (!"confirm".equals(housingRequestForm.getModel())) {
			if (!housingRequestForm.validate(errors)) {

				// �s���{�����X�g�̐ݒ�
				List<PrefMst> prefMstList = this.panaCommonManage.getPrefMstList();
				List<RouteMst> routeMstList = this.panaCommonManage
						.getPrefCdToRouteMstListWithGroupBy(housingRequestForm.getPrefCd());
				List<StationMst> stationList = this.panaCommonManage.getRouteCdToStationMstList(housingRequestForm
						.getRouteCd());

				model.put("prefMstList", prefMstList);
				model.put("routeMstList", routeMstList);
				model.put("stationMstList", stationList);

				// �o���f�[�V�����G���[����
				model.put("errors", errors);
				model.put("housingRequestForm", housingRequestForm);

				String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
				String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
				String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

				model.put("prefName", prefName);
				model.put("routeName", routeName);
				model.put("stationName", stationName);

				return new ModelAndView("validFail", model);
			}
		}
		model.put("housingRequestForm", housingRequestForm);
		// ���O�C�����[�U�[�̏����擾
		MypageUserInterface loginUser = FrontLoginUserUtils.getInstance(request).getMypageLoginUserInfo(request,
				response);
		// ���[�UID���擾
		String userId = "";
		if (loginUser != null) {
			userId = loginUser.getUserId().toString();
			model.put("loginFlg", 0);
		} else {
			userId = FrontLoginUserUtils.getInstance(request).getAnonLoginUserInfo(request, response).getUserId()
					.toString();
			model.put("loginFlg", 1);
		}

		if ("confirm".equals(housingRequestForm.getModel())) {
			// �`�B���̕������N�G�X�gID�ihousing_request_id�j
			if (StringUtils.isEmpty(housingRequestForm.getHousingRequestId())) {
				throw new RuntimeException("�������N�G�X�gID���w�肳��Ă��܂���.");
			}

			// �������N�G�X�g���
			List<HousingRequest> requestList = new ArrayList<HousingRequest>();
			HousingRequest requestInfo = new PanaHousingRequest();
			String housingRequestId = housingRequestForm.getHousingRequestId();

			requestList = this.housingRequestManage.searchRequest(userId);

			int searchCount = 0;
			for (int i = 0; i < requestList.size(); i++) {
				if (!StringUtils.isEmpty(housingRequestId)) {
					if (housingRequestId.equals(requestList.get(i).getHousingRequestInfo().getHousingRequestId())) {
						requestInfo = requestList.get(i);
						searchCount++;
					}
				}
			}
			System.out.println("request form . route: " + requestInfo.getHousingRequestInfo().getHousingRequestId());
			model.put("housingRequestForm", housingRequestForm);

			String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
			String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
			String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

			model.put("prefName", prefName);
			model.put("routeName", routeName);
			model.put("stationName", stationName);

			if (searchCount == 0) {
				return new ModelAndView("404");
			}

			// Form �֏����l��ݒ肷��B
			if (requestList != null && requestList.size() > 0) {
				housingRequestForm.setDefaultData(requestInfo);
			}
			valueToKey(housingRequestForm);

		}

		if ("insert".equals(housingRequestForm.getModel())) {
			// �o�^��������`�F�b�N
			int nowCnt = this.housingRequestManage.searchRequestCnt(userId);
			if (!(nowCnt < this.commonParameters.getMaxHousingRequestCnt())) {

				ValidationFailure vf = new ValidationFailure("maxAddError", String.valueOf(this.commonParameters
						.getMaxHousingRequestCnt()), "", null);
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
		}

		// �s���{�����X�g�̐ݒ�
		String prefName = this.panaCommonManage.getPrefName(housingRequestForm.getPrefCd());
		String routeName = this.panaCommonManage.getRouteName(housingRequestForm.getRouteCd());
		String stationName = this.panaCommonManage.getStationName(housingRequestForm.getStationCd());

		model.put("prefName", prefName);
		model.put("routeName", routeName);
		model.put("stationName", stationName);
		// �擾�����f�[�^�������_�����O�w�֓n��
		return new ModelAndView("success", model);
	}

	protected void valueToKey(PanaHousingRequestForm housingRequestForm) {

		if ("01".equals(housingRequestForm.getHousingKindCd())) {

			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// �\�Z�E����
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerMansion())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerMansion())) {

						housingRequestForm.setPriceLowerMansion(price);
					}
				}

				// �\�Z�E���
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperMansion())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperMansion())) {

						housingRequestForm.setPriceUpperMansion(price);
					}
				}
			}

			// ��L�ʐ�
			Iterator<String> personalAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (personalAreaIte.hasNext()) {

				String personalArea = personalAreaIte.next();
				// ��L�ʐρE����
				if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaLowerMansion())) {

					String value = this.codeLookupManager.lookupValue("area", personalArea);

					if (value.equals(housingRequestForm.getPersonalAreaLowerMansion())) {

						housingRequestForm.setPersonalAreaLowerMansion(personalArea);
					}
				}

				// ��L�ʐρE���
				if (!StringUtils.isEmpty(housingRequestForm.getPersonalAreaUpperMansion())) {

					String value = this.codeLookupManager.lookupValue("area", personalArea);

					if (value.equals(housingRequestForm.getPersonalAreaUpperMansion())) {

						housingRequestForm.setPersonalAreaUpperMansion(personalArea);
					}
				}

			}
		}

		if ("02".equals(housingRequestForm.getHousingKindCd())) {
			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// �\�Z�E����
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerHouse())) {

						housingRequestForm.setPriceLowerHouse(price);
					}
				}

				// �\�Z�E���
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperHouse())) {

						housingRequestForm.setPriceUpperHouse(price);
					}
				}
			}

			// �����ʐ�
			Iterator<String> buildingAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				// �����ʐρE����
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaLowerHouse())) {

						housingRequestForm.setBuildingAreaLowerHouse(buildingArea);
					}
				}

				// �����ʐρE���
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaUpperHouse())) {

						housingRequestForm.setBuildingAreaUpperHouse(buildingArea);
					}
				}
			}

			// �y�n�ʐ�
			Iterator<String> landAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				// �y�n�ʐρE����
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerHouse())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaLowerHouse())) {

						housingRequestForm.setLandAreaLowerHouse(landArea);
					}
				}

				// �y�n�ʐρE���
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperHouse())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaUpperHouse())) {

						housingRequestForm.setLandAreaUpperHouse(landArea);
					}
				}
			}

		}

		if ("03".equals(housingRequestForm.getHousingKindCd())) {
			// �\�Z
			Iterator<String> priceIte = this.codeLookupManager.getKeysByLookup("price");

			while (priceIte.hasNext()) {

				String price = priceIte.next();
				// �\�Z�E����
				if (!StringUtils.isEmpty(housingRequestForm.getPriceLowerLand())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceLowerLand())) {

						housingRequestForm.setPriceLowerLand(price);
					}
				}

				// �\�Z�E���
				if (!StringUtils.isEmpty(housingRequestForm.getPriceUpperLand())) {

					String value = this.codeLookupManager.lookupValue("price", price);

					if (value.equals(housingRequestForm.getPriceUpperLand())) {

						housingRequestForm.setPriceUpperLand(price);
					}
				}
			}

			// �����ʐ�
			Iterator<String> buildingAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (buildingAreaIte.hasNext()) {

				String buildingArea = buildingAreaIte.next();
				// �����ʐρE����
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaLowerLand())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaLowerLand())) {

						housingRequestForm.setBuildingAreaLowerLand(buildingArea);
					}
				}

				// �����ʐρE���
				if (!StringUtils.isEmpty(housingRequestForm.getBuildingAreaUpperLand())) {

					String value = this.codeLookupManager.lookupValue("area", buildingArea);

					if (value.equals(housingRequestForm.getBuildingAreaUpperLand())) {

						housingRequestForm.setBuildingAreaUpperLand(buildingArea);
					}
				}
			}

			// �y�n�ʐ�
			Iterator<String> landAreaIte = this.codeLookupManager.getKeysByLookup("area");

			while (landAreaIte.hasNext()) {

				String landArea = landAreaIte.next();
				// �y�n�ʐρE����
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaLowerLand())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaLowerLand())) {

						housingRequestForm.setLandAreaLowerLand(landArea);
					}
				}

				// �y�n�ʐρE���
				if (!StringUtils.isEmpty(housingRequestForm.getLandAreaUpperLand())) {

					String value = this.codeLookupManager.lookupValue("area", landArea);

					if (value.equals(housingRequestForm.getLandAreaUpperLand())) {

						housingRequestForm.setLandAreaUpperLand(landArea);
					}
				}
			}
		}
	}

}
