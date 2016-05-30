package jp.co.transcosmos.dm3.corePana.model.building.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.building.form.BuildingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationFailure;


/**
 * ������{��񃁃��e�i���X�̓��̓p�����[�^����p�t�H�[��.
 * <p>
 *
 * <pre>
 * �S����      �C����     �C�����e
 * ------------ ----------- -----------------------------------------------------
 * fan         2015.03.11  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */

public class PanaBuildingForm extends BuildingForm implements Validateable {
	/** ���K�� */
	private String totalFloorsTxt;
    /**
     * �p���p<br/>
     * <br/>
     */
    protected PanaBuildingForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     */
    protected PanaBuildingForm(LengthValidationUtils lengthUtils) {
        super();
        this.lengthUtils = lengthUtils;
    }

    /**
	 * @return totalFloorsTxt
	 */
	public String getTotalFloorsTxt() {
		return totalFloorsTxt;
	}

	/**
	 * @param totalFloorsTxt �Z�b�g���� totalFloorsTxt
	 */
	public void setTotalFloorsTxt(String totalFloorsTxt) {
		this.totalFloorsTxt = totalFloorsTxt;
	}

	/**
     * �����œn���ꂽ������{���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
     * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
     * <br/>
     * @param building �l��ݒ肷�錚����{���̃o���[�I�u�W�F�N�g
     *
     */
	@Override
    public void copyToBuildingInfo(BuildingInfo buildingInfo, String editUserId) {

		BuildingInfo  building= buildingInfo;
        // �V�X�e������CD��ݒ�
        if (!StringValidateUtil.isEmpty(formAndVo(this.getSysBuildingCd(),buildingInfo.getSysBuildingCd()))) {
            building.setSysBuildingCd(formAndVo(this.getSysBuildingCd(),buildingInfo.getSysBuildingCd()));
        }

        // �����ԍ���ݒ�
        building.setBuildingCd(formAndVo(this.getBuildingCd(),buildingInfo.getBuildingCd()));

        // �������CD��ݒ�
        building.setHousingKindCd(formAndVo(this.getHousingKindCd(),buildingInfo.getHousingKindCd()));

        // ���ݒn�E�X�֔ԍ���ݒ�
        building.setZip(formAndVo(this.getZip(),buildingInfo.getZip()));

        // ���ݒn�E�s���{��CD��ݒ�
        building.setPrefCd(formAndVo(this.getPrefCd(),buildingInfo.getPrefCd()));

        // ���ݒn�E�s�撬��CD��ݒ�
        building.setAddressCd(formAndVo(this.getAddressCd(),buildingInfo.getAddressCd()));

        // ���ݒn�E�s�撬������ݒ�
        building.setAddressName(formAndVo(this.getAddressName(),buildingInfo.getAddressName()));

        // ���ݒn�E�����Ԓn��ݒ�
        building.setAddressOther1(formAndVo(this.getAddressOther1(),buildingInfo.getAddressOther1()));

        // ���ݒn�E���������̑���ݒ�
        building.setAddressOther2(formAndVo(this.getAddressOther2(),buildingInfo.getAddressOther2()));

        // �v�H�N����ݒ�
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (!StringValidateUtil.isEmpty(this.getCompDate())) {
            try {
                building.setCompDate(sdf.parse(this.getCompDate() + "01"));
            } catch (ParseException e) {
                building.setCompDate(null);
            }
        }
        // ���K����ݒ�
        if(this.getTotalFloors() != null || building.getTotalFloors()!=null)
        {
        	building.setTotalFloors(PanaStringUtils.toInteger(formAndVo(this.getTotalFloors(), PanaStringUtils.toString(building.getTotalFloors()))));
        }

        // �X�V���t��ݒ�
        building.setUpdDate(new Date());

        // �X�V�S���҂�ݒ�
        building.setUpdUserId(editUserId);

    }
	/**
     * String�^�̒l��ݒ肷��B<br/>
     * <br/>
     * @return String
     */
	public String formAndVo(String form,String vo){
		String val;
		if(form == null && !StringValidateUtil.isEmpty(vo)){
			val=vo;
		}else{
			val=form;
		}
		return val;
	}

    @Override
    public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        return (startSize == errors.size());
    }
}
