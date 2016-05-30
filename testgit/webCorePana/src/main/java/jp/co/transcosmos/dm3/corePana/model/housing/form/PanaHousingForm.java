package jp.co.transcosmos.dm3.corePana.model.housing.form;

import java.math.BigDecimal;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.housing.form.HousingForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
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
 * fan	       2015.03.11  �V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */

public class PanaHousingForm extends HousingForm implements Validateable {
    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaHousingForm() {
        super();
    }

    /**
     * �R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     * @param lengthUtils �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
     * @param�@codeLookupManager�@���ʃR�[�h�ϊ�����
     */
    PanaHousingForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
        super();
        this.lengthUtils = lengthUtils;
        this.codeLookupManager = codeLookupManager;
    }

    /** �ŏ��k������ */
    private String minWalkingTime;
    /** ���t�H�[�������i */
    private String priceFullMin;
    /** ���t�H�[�������i */
    private String priceFullMax;

    /** ���t�H�[���������R�����g */
    private String reformComment;
	/** �������N�G�X�g�����Ώۃt���O */
	private String requestFlg;

    /**
     * ���t�H�[�������i �p�����[�^���擾����B<br/>
     * ���t�H�[�������i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�������i �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �ŏ��k������ �p�����[�^
     */
    public String getPriceFullMin() {
		return priceFullMin;
	}

    /**
     * ���t�H�[�������i �p�����[�^��ݒ肷��B<BR/>
     * ���t�H�[�������i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�������i �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM priceFullMin ���t�H�[�������i �p�����[�^
     */
	public void setPriceFullMin(String priceFullMin) {
		this.priceFullMin = priceFullMin;
	}

	/**
     * ���t�H�[�������i �p�����[�^���擾����B<br/>
     * ���t�H�[�������i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�������i �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �ŏ��k������ �p�����[�^
     */
	public String getPriceFullMax() {
		return priceFullMax;
	}

	 /**
     * ���t�H�[�������i �p�����[�^��ݒ肷��B<BR/>
     * ���t�H�[�������i �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� ���t�H�[�������i �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM priceFullMax ���t�H�[�������i �p�����[�^
     */
	public void setPriceFullMax(String priceFullMax) {
		this.priceFullMax = priceFullMax;
	}
	 /**
     * �ŏ��k������ �p�����[�^���擾����B<br/>
     * �ŏ��k������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �ŏ��k������ �N���X�̐؂�ւ��ɂ��g�p�����B<br/>
     * <br/>
     *
     * @return �ŏ��k������ �p�����[�^
     */
	public String getMinWalkingTime() {
        return minWalkingTime;
    }
	 /**
     * �ŏ��k������ �p�����[�^��ݒ肷��B<BR/>
     * �ŏ��k������ �p�����[�^�̒l�́A�t���[�����[�N�� URL �}�b�s���O�Ŏg�p���� �ŏ��k������ �N���X�̐؂�ւ��ɂ��g�p�����B<BR/>
     * <BR/>
     *
     * @PARAM minWalkingTime �ŏ��k������ �p�����[�^
     */
    public void setMinWalkingTime(String minWalkingTime) {
        this.minWalkingTime = minWalkingTime;
    }

    /**
	 * @return reformComment
	 */
	public String getReformComment() {
		return reformComment;
	}

	/**
	 * @param reformComment �Z�b�g���� reformComment
	 */
	public void setReformComment(String reformComment) {
		this.reformComment = reformComment;
	}

	/**
	 * �������N�G�X�g�����Ώۃt���O���擾����B<br/>
	 * <br/>
	 * @return �������N�G�X�g�����Ώۃt���O
	 */
	public String getRequestFlg() {
		return requestFlg;
	}

	/**
	 * �������N�G�X�g�����Ώۃt���O��ݒ肷��B<br/>
	 * <br/>
	 * @param basicComment�@�������N�G�X�g�����Ώۃt���O
	 */
	public void setRequestFlg(String requestFlg) {
		this.requestFlg = requestFlg;
	}


	/**
     * �����œn���ꂽ������{���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
     * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
     * <br/>
     * @return housingInfo ������{���
     */
	@Override
    public void copyToHousingInfo(HousingInfo housingInfo) {
//        super.copyToHousingInfo(housingInfo);

        // �V�X�e������CD �́A�����̔Ԃ��邩�AhousingInfo ���ŗ\�ߐݒ肳��Ă���l���g�p����̂�
 		// Form �̒l�͐ݒ肵�Ȃ��B

 		housingInfo.setHousingCd(formAndVo(super.getHousingCd(),housingInfo.getHousingCd()));								// �����ԍ�
 		housingInfo.setDisplayHousingName(formAndVo(super.getDisplayHousingName(),housingInfo.getDisplayHousingName()));				// �\���p������
 		housingInfo.setDisplayHousingNameKana(formAndVo(super.getDisplayHousingNameKana(),housingInfo.getDisplayHousingNameKana()));		// �\���p�������ӂ肪��
 		housingInfo.setRoomNo(formAndVo(super.getRoomNo(),housingInfo.getRoomNo()));										// �����ԍ�
 		housingInfo.setSysBuildingCd(formAndVo(super.getSysBuildingCd(),housingInfo.getSysBuildingCd()));						// �V�X�e������CD

 		if(super.getPrice() !=null){
 			if (!StringValidateUtil.isEmpty(super.getPrice())) {							// ����/���i
 	 			housingInfo.setPrice(Long.valueOf(super.getPrice()));
 	 		} else {
 	 			housingInfo.setPrice(null);
 	 		}
 		}

 		if(super.getUpkeep() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getUpkeep())) {							// �Ǘ���
	 			housingInfo.setUpkeep(Long.valueOf(super.getUpkeep()));
	 		} else {
	 			housingInfo.setUpkeep(null);
	 		}
 		}

 		if(super.getCommonAreaFee() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getCommonAreaFee())) {					// ���v��
	 			housingInfo.setCommonAreaFee(Long.valueOf(super.getCommonAreaFee()));
	 		} else {
	 			housingInfo.setCommonAreaFee(null);
	 		}
 		}

 		if(super.getMenteFee() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getMenteFee())) {						// �C�U�ϗ���
	 			housingInfo.setMenteFee(Long.valueOf(super.getMenteFee()));
	 		} else {
	 			housingInfo.setMenteFee(null);
	 		}
 		}

 		if(super.getSecDeposit() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getSecDeposit())) {						// �~��
	 			housingInfo.setSecDeposit(new BigDecimal(super.getSecDeposit()));
	 		} else {
	 			housingInfo.setSecDeposit(null);
	 		}
 		}

 		housingInfo.setSecDepositCrs(formAndVo(super.getSecDepositCrs(),housingInfo.getSecDepositCrs()));		// �~���P��

 		if(super.getBondChrg() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getBondChrg())) {						// �ۏ؋�
	 			housingInfo.setBondChrg(new BigDecimal(super.getBondChrg()));
	 		} else {
	 			housingInfo.setBondChrg(null);
	 		}
 		}

 		housingInfo.setBondChrgCrs(formAndVo(super.getBondChrgCrs(),housingInfo.getBondChrgCrs()));		// �ۏ؋��P��
 		housingInfo.setDepositDiv(formAndVo(super.getDepositDiv(),housingInfo.getDepositDiv()));		// �~������敪

 		if(super.getDeposit() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getDeposit())) {						// �~������z
	 			housingInfo.setDeposit(new BigDecimal(super.getDeposit()));
	 		} else {
	 			housingInfo.setDeposit(null);
	 		}
 		}

 		housingInfo.setDepositCrs(formAndVo(super.getDepositCrs(),housingInfo.getDepositCrs()));			// �~������P��
 		housingInfo.setLayoutCd(formAndVo(super.getLayoutCd(),housingInfo.getLayoutCd()));					// �Ԏ�CD
 		housingInfo.setLayoutComment(formAndVo(super.getLayoutComment(),housingInfo.getLayoutComment()));	// �Ԏ�ڍ׃R�����g

 		if(super.getFloorNo() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getFloorNo())) {						// �����̊K��
	 			housingInfo.setFloorNo(Integer.valueOf(super.getFloorNo()));
	 		} else {
	 			housingInfo.setFloorNo(null);
	 		}
 		}

 		housingInfo.setFloorNoNote(formAndVo(super.getFloorNoNote(),housingInfo.getFloorNoNote()));			// �����̊K���R�����g

 		if(super.getLandArea() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getLandArea())) {						// �y�n�ʐ�
	 			housingInfo.setLandArea(new BigDecimal(super.getLandArea()));
	 		} else {
	 			housingInfo.setLandArea(null);
	 		}
 		}

 		housingInfo.setLandAreaMemo(formAndVo(super.getLandAreaMemo(),housingInfo.getLandAreaMemo()));		// �y�n�ʐ�_�⑫

 		if(super.getPersonalArea() !=null){
	 		if (!StringValidateUtil.isEmpty(super.getPersonalArea())) {					// ��L�ʐ�
	 			housingInfo.setPersonalArea(new BigDecimal(super.getPersonalArea()));
	 		} else {
	 			housingInfo.setPersonalArea(null);
	 		}
 		}

 		housingInfo.setPersonalAreaMemo(formAndVo(super.getPersonalAreaMemo(),housingInfo.getPersonalAreaMemo()));			// ��L�ʐ�_�⑫
 		housingInfo.setMoveinFlg(formAndVo(super.getMoveinFlg(),housingInfo.getMoveinFlg()));								// ������ԃt���O
 		housingInfo.setParkingSituation(formAndVo(super.getParkingSituation(),housingInfo.getParkingSituation()));			// ���ԏ�̏�
 		housingInfo.setParkingEmpExist(formAndVo(super.getParkingEmpExist(),housingInfo.getParkingEmpExist()));				// ���ԏ��̗L��
 		housingInfo.setDisplayParkingInfo(formAndVo(super.getDisplayParkingInfo(),housingInfo.getDisplayParkingInfo()));	// �\���p���ԏ���
 		housingInfo.setWindowDirection(formAndVo(super.getWindowDirection(),housingInfo.getWindowDirection()));				// ���̌���
 		//�������N�G�X�g�t���O
 		((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setRequestFlg(formAndVo(this.getRequestFlg(),((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).getRequestFlg()));

 		// �A�C�R�����́A�ݔ���񑤂��玩���X�V����̂œ��͍��ڂł͂Ȃ��B

 		housingInfo.setBasicComment(formAndVo(super.getBasicComment(),housingInfo.getBasicComment()));						// ��{���R�����g

        if (!StringValidateUtil.isEmpty(this.minWalkingTime)) {
            int minWalkingTime = Integer.valueOf(this.minWalkingTime);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setMinWalkingTime(minWalkingTime);
        }
        if(!StringValidateUtil.isEmpty(this.priceFullMin)){
        	Long priceFullMin = Long.valueOf(this.priceFullMin);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setPriceFullMin(priceFullMin);
        }
        if(!StringValidateUtil.isEmpty(this.priceFullMax)){
            Long priceFullMax = Long.valueOf(this.priceFullMax);
            ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setPriceFullMax(priceFullMax);
        }

        ((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setReformComment(formAndVo(this.getReformComment(),((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).getReformComment()));

        if(!StringValidateUtil.isEmpty(this.getFloorNo())){
        	int floorNo = Integer.valueOf(this.getFloorNo());
        	((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setFloorNo(floorNo);
        }
        if(!StringValidateUtil.isEmpty(this.getUpkeep())){
        	housingInfo.setUpkeep(PanaStringUtils.toLong(this.getUpkeep()));
        }
        if(!StringValidateUtil.isEmpty(this.getMenteFee())){
        	housingInfo.setMenteFee(PanaStringUtils.toLong(this.getMenteFee()));
        }

        //((jp.co.transcosmos.dm3.corePana.vo.HousingInfo) housingInfo).setRequestFlg(this.requestFlg);
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
