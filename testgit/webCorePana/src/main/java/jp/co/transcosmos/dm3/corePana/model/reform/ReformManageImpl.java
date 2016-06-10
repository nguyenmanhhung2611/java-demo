package jp.co.transcosmos.dm3.corePana.model.reform;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.corePana.model.ReformManage;
import jp.co.transcosmos.dm3.corePana.model.housing.PanaHousingPartThumbnailProxy;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformDtlForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformImgForm;
import jp.co.transcosmos.dm3.corePana.model.reform.form.ReformInfoForm;
import jp.co.transcosmos.dm3.corePana.util.PanaStringUtils;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.ReformChart;
import jp.co.transcosmos.dm3.corePana.vo.ReformDtl;
import jp.co.transcosmos.dm3.corePana.vo.ReformImg;
import jp.co.transcosmos.dm3.corePana.vo.ReformPlan;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.FormulaUpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateExpression;
import jp.co.transcosmos.dm3.dao.UpdateValue;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;

/** model �N���X�͂��̃C���^�[�t�F�[�X���������鎖�B<br/>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS		2015.03.10	�V�K�쐬
 * Thi Tran     2015.12.18      Search housing by cd
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 *
 */
public class ReformManageImpl implements ReformManage {
    /** ���t�H�[���v����DAO */
    private DAO<ReformPlan> reformPlanDAO;

    /** ���t�H�[���E���[�_�[�`���[�gDAO */
    private DAO<ReformChart> reformChartDAO;

    /** ���t�H�[���ڍ׏��DAO */
    private DAO<ReformDtl> reformDtlDAO;

    /** ���t�H�[���摜���DAO */
    private DAO<ReformImg> reformImgDAO;

    /** ������񃁃��e�i���X�� model */
    private PanaHousingPartThumbnailProxy panaHousingManager;

    /**
     * ���t�H�[���v����DAO �̐ݒ�<br>
     * <br>
     * @param reformPlanDAO ���t�H�[���v����DAO
     */
    public void setReformPlanDAO(DAO<ReformPlan> reformPlanDAO) {
        this.reformPlanDAO = reformPlanDAO;
    }

    /**
     * ���t�H�[���E���[�_�[�`���[�gDAO �̐ݒ�<br>
     * <br>
     * @param reformChartDAO ���t�H�[���E���[�_�[�`���[�gDAO
     */
    public void setReformChartDAO(DAO<ReformChart> reformChartDAO) {
        this.reformChartDAO = reformChartDAO;
    }

    /**
     * ���t�H�[���ڍ׏��DAO �̐ݒ�<br>
     * <br>
     * @param reformDtlDAO ���t�H�[���ڍ׏��DAO
     */
    public void setReformDtlDAO(DAO<ReformDtl> reformDtlDAO) {
        this.reformDtlDAO = reformDtlDAO;
    }

    /**
     * ���t�H�[���摜���DAO �̐ݒ�<br>
     * <br>
     * @param reformImgDAO ���t�H�[���摜���DAO
     */
    public void setReformImgDAO(DAO<ReformImg> reformImgDAO) {
        this.reformImgDAO = reformImgDAO;
    }

    /**
     * Pana������񃁃��e�i���X�p model ��ݒ肷��B<br/>
     * <br/>
     * @param panaHousingManager Pana������񃁃��e�i���X�p model
     */
    public void setPanaHousingManager(PanaHousingPartThumbnailProxy panaHousingManager) {
        this.panaHousingManager = panaHousingManager;
    }

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���v��������V�K�ǉ�����B<br/>
     * <br/>
     * @param reformPlan ���t�H�[���v�������
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param userId ���O�C�����[�U�[ID �i�X�V���p�j
     *
     * @return �V�X�e�����t�H�[��CD
     */
    public String addReformPlan(ReformPlan reformPlan, ReformInfoForm inputForm, String userId) throws Exception {

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        // �f�[�^��ǉ�
        try {

        	// ���[�_�[�`���[�g�摜�p�X���̐ݒ�
        	reformPlan.setReformChartImagePathName(uploadPath);

            // �擾������L�[�l�Ń��t�H�[���v��������o�^
            this.reformPlanDAO.insert(new ReformPlan[] { reformPlan });

        } catch (DataIntegrityViolationException e) {
            // ���̗�O�́A�o�^���O�Ɉˑ���ƂȂ錚����񂪍폜���ꂽ�ꍇ�ɔ�������B
            e.printStackTrace();
            throw new NotFoundException();
        }

        return reformPlan.getSysReformCd();
    }

    /**
     * ���t�H�[���v�������̍X�V���s��<br/>
     * <br/>
     * @param inputForm ���͒l���i�[���ꂽ Form �I�u�W�F�N�g
     * @param userId ���O�C�����[�U�[ID �i�X�V���p�j
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public void updateReformPlan(ReformInfoForm inputForm, String userId)  throws Exception {

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        ReformPlan reformPlan = this.reformPlanDAO.selectByPK(inputForm.getSysReformCd());

        // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
        if (reformPlan == null) {
            throw new NotFoundException();
        }

        inputForm.copyToReformPlan(reformPlan, userId);

        try {

            // ���[�_�[�`���[�g�摜�̍X�V
            if (!"on".equals(inputForm.getReformImgDel())) {

            	// ���[�_�[�`���[�g�摜�p�X���̍X�V
            	reformPlan.setReformChartImagePathName(uploadPath);

    			// ���[�_�[�`���[�g�摜�̍폜
            } else {

            	//  ���[�_�[�`���[�g�摜�p�X��
            	reformPlan.setReformChartImagePathName("");
            	//  ���[�_�[�`���[�g�摜�t�@�C����
            	reformPlan.setReformChartImageFileName("");
            }

            // ���t�H�[���v�������̍X�V
            this.reformPlanDAO.update(new ReformPlan[] { reformPlan });

        } catch (DataIntegrityViolationException e) {
            // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    /**
     * �p�����[�^�œn���ꂽ sysReformCd���L�[�Ƀ��t�H�[���v���������폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     * @throws Exception
     *
     */
    public void delReformPlan(String sysHousingCd, String sysReformCd, String userId) throws Exception {

        // ��L�[�l�ō폜���������s
        this.reformPlanDAO.deleteByPK(new String[] { sysReformCd });

    	//  �����g���������̍폜�i���[�_�[�`���[�g�摜�p�X���j
    	this.panaHousingManager.delExtInfo(sysHousingCd, "reformChart",
				"reformChartImagePathName", userId);

    	//  �����g���������̍폜�i���[�_�[�`���[�g�摜�t�@�C�����j
    	this.panaHousingManager.delExtInfo(sysHousingCd, "reformChart",
				"reformChartImageFileName", userId);

        // �ˑ��\�͐���������ō폜���鎖��z�肵�Ă���̂ŁA�����I�ȍ폜�͍s��Ȃ��B
        // reformDtl, reformImg, reformChart

        // �摜�t�@�C���� Proxy ���ō폜����̂ł����ł͑Ή����Ȃ��B
    }

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���ڍ׏���V�K�ǉ�����B<br/>
     * �}�� �͎����̔Ԃ����̂ŁAReformDtl �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
     * <br/>
     * @param reformDtl ���t�H�[���ڍ׏��
     *
     * @return �}��
     */
    public List<ReformDtl> addReformDtl(ReformDtlForm inputForm) throws Exception {
        List<ReformDtl> reformDtlList = new ArrayList<ReformDtl>();

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());

        for (int idx = 0; idx < inputForm.getAddHidFileName().length; idx++) {
            if (!StringUtils.isEmpty(inputForm.getAddHidFileName()[idx])) {
                ReformDtl reformDtl = new ReformDtl();
                inputForm.copyToReformDtl(reformDtl, idx);

                reformDtl.setPathName(getUploadPath(buildingInfo, inputForm.getSysHousingCd()));
                // �A�Ԑݒ�
                reformDtl.setDivNo(getReformDtlDivNo(reformDtl.getSysReformCd()));

                try {
                    // ���t�H�[���v�������̍X�V
                    this.reformDtlDAO.insert(new ReformDtl[] { reformDtl });
                } catch (DataIntegrityViolationException e) {
                    // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
                    e.printStackTrace();
                    throw new NotFoundException();
                }
                reformDtlList.add(reformDtl);
            }
        }
        // ���C���摜�t���O�A�}�Ԃ��X�V����B
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformDtlDAO");
        return reformDtlList;
    }

    /**
     * ���t�H�[���ڍ׏��̍X�V���s��<br/>
     * <br/>
     * @param form ���t�H�[���ڍ׏��̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public List<ReformDtl> updateReformDtl(ReformDtlForm inputForm) throws Exception {

        // �߂�l�ƂȂ�A�폜���������������摜���̃��X�g
        List<ReformDtl> reformDtlDelList = new ArrayList<ReformDtl>();
        List<ReformDtl> reformDtlList = new ArrayList<ReformDtl>();

        // �摜�t�@�C������Ɍ��������[�v����B
        for (int idx = 0; idx < inputForm.getDivNo().length; ++idx) {

            // �폜�t���O���ݒ肳��Ă���ꍇ�͍폜������
            if ("1".equals(inputForm.getDelFlg()[idx])) {

                // �����摜�����폜
                ReformDtl reformDtl = delReformDtl(inputForm.getSysReformCd(),
                        PanaStringUtils.toInteger(inputForm.getDivNo()[idx]));
                // ���ۂɍ폜�����񂪖��������ꍇ�A null �����A�����̂ŁA���̏ꍇ�̓��X�g�ɒǉ����Ȃ��B
                if (reformDtl != null) {
                    reformDtlDelList.add(reformDtl);
                }

            } else {

                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause("sysReformCd", inputForm.getSysReformCd());
                criteria.addWhereClause("divNo", inputForm.getDivNo()[idx]);

                List<ReformDtl> reformDtlResult = this.reformDtlDAO.selectByFilter(criteria);

                // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
                if (reformDtlResult == null || reformDtlResult.size() == 0) {
                    throw new NotFoundException();
                }

                ReformDtl reformDtl = reformDtlResult.get(0);
                inputForm.copyToReformDtl(reformDtl, idx);

                reformDtlList.add(reformDtl);
            }
        }
        if (reformDtlList.size() > 0) {
            ReformDtl reformDtls[] = new ReformDtl[reformDtlList.size()];
            reformDtlList.toArray(reformDtls);

            try {
                // ���t�H�[���v�������̍X�V
                reformDtlDAO.update(reformDtls);
            } catch (DataIntegrityViolationException e) {
                // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
                e.printStackTrace();
                throw new NotFoundException();
            }
        }
        // ���C���摜�t���O�A�}�Ԃ��X�V����B
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformDtlDAO");
        return reformDtlDelList;
    }

    /**
     * �p�����[�^�œn���ꂽ Form �̏��Ń��t�H�[���ڍ׏����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param form ���t�H�[���ڍ׏��̍폜�������i�[����
     *
     */
    public ReformDtl delReformDtl(String sysReformCd, int divNo) {

        // �����摜�����폜����������쐬
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        // �폜�O�ɁA�폜�Ώۃf�[�^�̏����擾����B
        // �����擾�o���Ȃ��ꍇ�� null �𕜋A����B
        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(criteria);
        if (reformDtlList.size() == 0)
            return null;

        this.reformDtlDAO.deleteByFilter(criteria);

        // �摜�t�@�C���̕����폜�͊g�������l�����Ă��̃N���X���ł͍s��Ȃ��B
        // �����摜���J�A�T���l�C���쐬�A�����폜���̏����́A���̃N���X�� Proxy �N���X���őΉ�����B

        return reformDtlList.get(0);
    }

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���摜����V�K�ǉ�����B<br/>
     * �}�� �͎����̔Ԃ����̂ŁAReformImg �� divNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
     * <br/>
     * @param reformImg ���t�H�[���摜���
     *
     * @return �}��
     */
    public ReformImg addReformImg(ReformImgForm inputForm) throws Exception {

        // ������{�����擾����B
        BuildingInfo buildingInfo = this.searchBuildingInfo(inputForm.getSysHousingCd());

        ReformImg reformImg = inputForm.newToReformImg();
        String uploadPath = getUploadPath(buildingInfo, inputForm.getSysHousingCd());

        reformImg.setAfterPathName(uploadPath);
        reformImg.setBeforePathName(uploadPath);
        // �A�Ԑݒ�
        reformImg.setDivNo(getReformImgDivNo(reformImg.getSysReformCd()));

        try {
            // ���t�H�[���v�������̍X�V
            this.reformImgDAO.insert(new ReformImg[] { reformImg });
        } catch (DataIntegrityViolationException e) {
            // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
            e.printStackTrace();
            throw new NotFoundException();
        }
        // ���C���摜�t���O�A�}�Ԃ��X�V����B
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformImgDAO");
        return reformImg;
    }

    /**
     * ���t�H�[���摜���̍X�V���s��<br/>
     * <br/>
     * @param form ���t�H�[���摜���̓��͒l���i�[���� Form �I�u�W�F�N�g
     *
     * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
     */
    public List<ReformImg> updateReformImg(ReformImgForm inputForm) throws Exception {
        List<ReformImg> reformImgList = new ArrayList<ReformImg>();
        List<ReformImg> reformImgDelList = new ArrayList<ReformImg>();

        // �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
        for (int idx = 0; idx < inputForm.getDivNo().length; idx++) {
            // �폜�����łȂ��ꍇ
            if ("1".equals(inputForm.getDelFlg()[idx])) {

                // �����摜�����폜
                ReformImg reformImg = delReformImg(inputForm.getSysReformCd(),
                        PanaStringUtils.toInteger(inputForm.getDivNo()[idx]));
                // ���ۂɍ폜�����񂪖��������ꍇ�A null �����A�����̂ŁA���̏ꍇ�̓��X�g�ɒǉ����Ȃ��B
                if (reformImg != null) {
                    reformImgDelList.add(reformImg);
                }
            } else {
                DAOCriteria criteria = new DAOCriteria();
                criteria.addWhereClause("sysReformCd", inputForm.getSysReformCd());
                criteria.addWhereClause("divNo", inputForm.getDivNo()[idx]);

                List<ReformImg> reformImgResult = this.reformImgDAO.selectByFilter(criteria);

                // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
                if (reformImgResult == null || reformImgResult.size() == 0) {
                    throw new NotFoundException();
                }

                ReformImg reformImg = reformImgResult.get(0);

                // �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
                inputForm.copyToReformImg(reformImg, idx);

                reformImgList.add(reformImg);
            }
        }

        if (reformImgList.size() > 0) {
            ReformImg reformImgs[] = new ReformImg[reformImgList.size()];
            reformImgList.toArray(reformImgs);

            try {
                // ���t�H�[���v�������̍X�V
                reformImgDAO.update(reformImgs);
            } catch (DataIntegrityViolationException e) {
                // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
                e.printStackTrace();
                throw new NotFoundException();
            }

        }
        // ���C���摜�t���O�A�}�Ԃ��X�V����B
     	updateMainFlgAndDivNo(inputForm.getSysReformCd(),"reformImgDAO");
        return reformImgDelList;
    }

    /**
     * �p�����[�^�œn���ꂽ Form �̏��Ń��t�H�[���摜�����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param form ���t�H�[���摜���̍폜�������i�[����
     *
     */
    public ReformImg delReformImg(String sysReformCd, int divNo) {

        // �����摜�����폜����������쐬
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        // �폜�O�ɁA�폜�Ώۃf�[�^�̏����擾����B
        // �����擾�o���Ȃ��ꍇ�� null �𕜋A����B
        List<ReformImg> reformImgList = this.reformImgDAO.selectByFilter(criteria);
        if (reformImgList.size() == 0)
            return null;

        this.reformImgDAO.deleteByFilter(criteria);

        return reformImgList.get(0);
    }


    /**
	 * �����摜���̃��C���摜�t���O�A����ю}�Ԃ̍X�V���s��<br/>
	 * �摜��ʖ��Ɉ�ԍŏ��ɕ\�����镨���摜���ɑ΂��ă��C���摜�t���O��ݒ肷��B<br/>
	 * �܂��A�摜�^�C�v���ɕ\�����Ŏ}�Ԃ��X�V����B<br/>
	 * <br/>
	 * @param getSysHousingCd �V�X�e������CD
	 */
	protected void updateMainFlgAndDivNo(String sysReformCd,String daoType){
		DAOCriteria tagetCri = new DAOCriteria();
		tagetCri.addWhereClause("sysReformCd", sysReformCd);


		// �\�����݂̂�ύX�����ꍇ�A�}�Ԃ��d�����ăG���[����������B
		// �����ŁA�g���b�L�[�ȕ��@�����A��x�A�}�Ԃ��}�C�i�X�̒l�� UPDATE ������Ɏ}�Ԃ̍X�V�������s���B
		UpdateExpression[] initExpression
			= new UpdateExpression[] {new FormulaUpdateExpression("div_no = div_no * -1")};

		if("reformImgDAO".equals(daoType)){

			this.reformImgDAO.updateByCriteria(tagetCri, initExpression);

			// �\�[�g������ǉ�
			// �}�Ԃ̓}�C�i�X�̒l�ɍX�V����Ă���̂ŁADESC �Ń\�[�g����B
			tagetCri.addOrderByClause("sortOrder");
			tagetCri.addOrderByClause("divNo", false);

			// �V�X�e������CD ���̕����摜�����擾
			List<ReformImg> imgList = this.reformImgDAO.selectByFilter(tagetCri);

			int divNo = 0;							// �}��
			for (ReformImg imgInfo : imgList){
				++divNo;

				// �X�V�Ώۂ̃L�[�����쐬
				DAOCriteria updCri = new DAOCriteria();
				updCri.addWhereClause("sysReformCd", sysReformCd);
				updCri.addWhereClause("divNo", imgInfo.getDivNo());

				UpdateExpression[] expression;
				// �摜�^�C�v���O��̍s�Ɠ����ꍇ�A���C���摜���I�t�Ƃ��Ď}�Ԃ��X�V����B

				expression = new UpdateExpression[] {new UpdateValue("divNo", divNo)};

				this.reformImgDAO.updateByCriteria(updCri, expression);
			}
		}
		if("reformDtlDAO".equals(daoType)){
			this.reformDtlDAO.updateByCriteria(tagetCri, initExpression);

			// �\�[�g������ǉ�
			// �}�Ԃ̓}�C�i�X�̒l�ɍX�V����Ă���̂ŁADESC �Ń\�[�g����B
			tagetCri.addOrderByClause("sortOrder");
			tagetCri.addOrderByClause("divNo", false);

			// �V�X�e������CD ���̕����摜�����擾
			List<ReformDtl> imgList = this.reformDtlDAO.selectByFilter(tagetCri);

			int divNo = 0;							// �}��
			for (ReformDtl imgInfo : imgList){
				++divNo;

				// �X�V�Ώۂ̃L�[�����쐬
				DAOCriteria updCri = new DAOCriteria();
				updCri.addWhereClause("sysReformCd", sysReformCd);
				updCri.addWhereClause("divNo", imgInfo.getDivNo());

				UpdateExpression[] expression;
				// �摜�^�C�v���O��̍s�Ɠ����ꍇ�A���C���摜���I�t�Ƃ��Ď}�Ԃ��X�V����B

				expression = new UpdateExpression[] {new UpdateValue("divNo", divNo)};

				this.reformDtlDAO.updateByCriteria(updCri, expression);
			}
		}
	}

    /**
     * ���t�H�[���֘A���iReformPlan, ReformChart, ReformDtl, ReformImg)���������A����Map�𕜋A����B<br/>
     * �����œn���ꂽ sysReformCd �p�����[�^�̒l�Ō��������𐶐����A���t�H�[��������������B<br/>
     * <br/>
     * ���������Ƃ��āA�ȉ��̃f�[�^�������ΏۂƂ���B<br/>
     *
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     * @return ���t�H�[���v��������Map
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public Map<String, Object> searchReform(String sysReformCd) {
        // ���t�H�[���v�������
        ReformPlan reformPlanResult =
                this.reformPlanDAO.selectByPK(sysReformCd);

        // ��ʍ��ڌ���
        // ���t�H�[���E���[�_�[�`���[�g���
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        List<ReformChart> chartResults = this.reformChartDAO.selectByFilter(criteria);

        // ���t�H�[���ڍ׏��擾
        criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addOrderByClause("sortOrder", true);
        criteria.addOrderByClause("divNo", true);
        List<ReformDtl> dtlResults = this.reformDtlDAO.selectByFilter(criteria);

        // ���t�H�[���摜���擾
        criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addOrderByClause("sortOrder", true);
        criteria.addOrderByClause("divNo", true);
        List<ReformImg> imgResults = this.reformImgDAO.selectByFilter(criteria);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("reformPlan", reformPlanResult);
        // ���t�H�[���E���[�_�[�`���[�g�����i�[
        resultMap.put("chartList", chartResults);
        // ���t�H�[���ڍ׏����i�[
        resultMap.put("dtlList", dtlResults);
        // ���t�H�[���摜�����i�[
        resultMap.put("imgList", imgResults);
        // �V�X�e�����t�H�[��Cd���i�[
        resultMap.put("sysReformCd", sysReformCd);

        return resultMap;
    }

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏����X�V�iupdate��insert)����B<br/>
     * <br/>
     * @param reformChart ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
     *
     */
    public void updReformChart(ReformInfoForm form, int count) {
        delReformChart(form.getSysReformCd());
        addReformChart(form, count);
    }

    /**
     * �p�����[�^�œn���ꂽ ���t�H�[���E���[�_�[�`���[�g�ڍ׏���V�K�ǉ�����B<br/>
     * <br/>
     * @param reformChart ���t�H�[���E���[�_�[�`���[�g�ڍ׏��
     *
     */
    public void addReformChart(ReformInfoForm form, int count) {
        ReformChart reformCharts[] = new ReformChart[count];
        for (int i = 0; i < count; i++) {
            if (!StringValidateUtil.isEmpty(form.getChartValue()[i])) {
                ReformChart reformChart = new ReformChart();

                // �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
                form.copyToReformChart(reformChart, String.valueOf(i));
                reformCharts[i] = reformChart;
            }
        }
        try {
            // ���t�H�[���v�������̍X�V
            reformChartDAO.insert(reformCharts);
        } catch (DataIntegrityViolationException e) {
            // ���̗�O�́A�o�^���O�ɕύX��ƂȂ郊�t�H�[����񂪍폜���ꂽ�ꍇ�ɔ�������B
            e.printStackTrace();
            throw new NotFoundException();
        }
    }

    /**
     * �p�����[�^�œn���ꂽ �V�X�e�����t�H�[��CD�Ń��t�H�[���E���[�_�[�`���[�g�����폜����B<br/>
     * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public void delReformChart(String sysReformCd) {
        DAOCriteria criteria = new DAOCriteria();

        // �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
        criteria.addWhereClause("sysReformCd", sysReformCd);

        reformChartDAO.deleteByFilter(criteria);
    }

    /**
     * ���t�H�[���摜���̎}�Ԃ��̔Ԃ��鏈���B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public int getReformImgDivNo(String sysReformCd) {
        // ���̏����́A�r��������s���Ă��Ȃ��B
        // ���t�H�[���ڍׂ̌���
        DAOCriteria daoCriteria = new DAOCriteria();

        if (StringUtils.isEmpty(sysReformCd)) {
            daoCriteria.addWhereClause("sysReformCd", sysReformCd);
        }

        daoCriteria.addOrderByClause("divNo", false);

        // ����V�X�e�����t�H�[��CD�Ŏ}�Ԃ��~���Ƀ\�[�g����B
        // ���̌��ʂ̍ő�l + 1 ���}�ԂƂ��ĕ��A����B
        // �Y���f�[�^�������ꍇ�� 1 �𕜋A����B
        List<ReformImg> reformImgList = this.reformImgDAO.selectByFilter(daoCriteria);

        if (reformImgList.size() == 0)
            return 1;
        return reformImgList.get(0).getDivNo() + 1;
    }

    /**
     * ���t�H�[���ڍ׏��̎}�Ԃ��̔Ԃ��鏈���B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     *
     */
    public int getReformDtlDivNo(String sysReformCd) {
        // ���̏����́A�r��������s���Ă��Ȃ��B
        // ���t�H�[���ڍׂ̌���
        DAOCriteria daoCriteria = new DAOCriteria();

        if (StringUtils.isEmpty(sysReformCd)) {
            daoCriteria.addWhereClause("sysReformCd", sysReformCd);
        }

        daoCriteria.addOrderByClause("divNo", false);

        // ����V�X�e�����t�H�[��CD�Ŏ}�Ԃ��~���Ƀ\�[�g����B
        // ���̌��ʂ̍ő�l + 1 ���}�ԂƂ��ĕ��A����B
        // �Y���f�[�^�������ꍇ�� 1 �𕜋A����B
        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(daoCriteria);

        if (reformDtlList.size() == 0)
            return 1;
        return reformDtlList.get(0).getDivNo() + 1;
    }

    /**
     * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ������{���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public HousingInfo searchHousingInfo(String sysHousingCd) throws Exception {
        // ����������������
        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

        HousingInfo housingInfo = new HousingInfo();
        if (housing != null && housing.getHousingInfo() != null) {
            housingInfo = (HousingInfo) housing.getHousingInfo().getItems().get("housingInfo");
        }

        return housingInfo;
    }

    /**
     * �p�����[�^ �V�X�e������CD ���L�[�ɁA������{������������B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ������{���
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public BuildingInfo searchBuildingInfo(String sysHousingCd) throws Exception {
        // ����������������
        Housing housing = this.panaHousingManager.searchHousingPk(sysHousingCd, true);

        return (BuildingInfo) housing.getBuilding().getBuildingInfo().getItems().get("buildingInfo");
    }

    /**
     * ���t�H�[���ڍ׏����������A���ʂ𕜋A����B<br/>
     * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���t�H�[���ڍ׏�����������B<br/>
     * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y�����R�[�h��߂�l�Ƃ��ĕ��A����B<br/>
     * <br/>
     * @param sysReformCd �V�X�e�����t�H�[��CD
     * @param divNo �}��
     *
     * @return ���������ɊY�����郊�t�H�[���ڍ׏��
     *
     */
    public ReformDtl searchReformDtlByPk(String sysReformCd, String divNo) {
        // ������{����������������𐶐�����B
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);
        criteria.addWhereClause("divNo", divNo);

        List<ReformDtl> reformDtlList = this.reformDtlDAO.selectByFilter(criteria);

        if (reformDtlList == null || reformDtlList.size() == 0) {
            return null;
        }

        return reformDtlList.get(0);
    }

    /**
     * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * ���̃��\�b�h���g�p�����ꍇ�A�Öق̒��o�����i�Ⴆ�΁A����J�����̏��O�Ȃǁj���K�p�����B<br/>
	 * ����āA�t�����g���͊�{�I�ɂ��̃��\�b�h���g�p���鎖�B<br/>
     *
     * @return ���������ɊY�����郊�t�H�[���v�������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public List<ReformPlan> searchReformPlan(String sysHousingCd)
            throws Exception {
        return searchReformPlan(sysHousingCd, false);
    }

    /**
     * ���t�H�[���v���������������A���ʂ𕜋A����B<br/>
     * <br/>
     * @param sysHousingCd �V�X�e������CD
     *
     * @return ���������ɊY�����郊�t�H�[���v�������
     *
     * @exception Exception �����N���X�ɂ��X���[�����C�ӂ̗�O
     */
    public List<ReformPlan> searchReformPlan(String sysHousingCd, boolean full)
            throws Exception {

        // ���t�H�[���v��������������������𐶐�����B
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysHousingCd", sysHousingCd);

        if (!full) {
            criteria.addWhereClause("hiddenFlg", 0);
        }

        List<ReformPlan> reformPlanList = this.reformPlanDAO.selectByFilter(criteria);

        return reformPlanList;
    }

    /**
     * ���J�p�X�𕜋A����B<br/>
     * <br/>
     * @param buildingInfo ������{���
     * @param sysHousingCd �V�X�e�������ԍ�
     * @return ���J�p�X
     *         ��j[�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e�������ԍ�/
     */
    protected static String getUploadPath(BuildingInfo buildingInfo, String sysHousingCd) {
        StringBuffer transPath = new StringBuffer("");

        // �������CD�t�H���_�[���p�X�ɐݒ�
        // �������CD�����ݒ�̏ꍇ�A�u999�v�t�H���_�[�ɒu��
        // [�������CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getHousingKindCd()) ?
                "999" : buildingInfo.getHousingKindCd());
        transPath.append("/");

        // �s���{���R�[�h�t�H���_�[���p�X�ɐݒ�
        // �s���{���R�[�h�����ݒ�̏ꍇ�A�u99�v�t�H���_�[�ɒu��
        // [�������CD]/[�s���{��CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getPrefCd()) ?
                "99" : buildingInfo.getPrefCd());
        transPath.append("/");

        // �s�����R�[�h�t�H���_�[���p�X�ɐݒ�
        // �s�����R�[�h�����ݒ�̏ꍇ�A�u99999�v�t�H���_�[�ɒu��
        // [�������CD]/[�s���{��CD]/[�s�撬��CD]/
        transPath.append(StringUtils.isEmpty(buildingInfo.getAddressCd()) ?
                "99999" : buildingInfo.getAddressCd());
        transPath.append("/");

        // �V�X�e������CD
        // �V�X�e������CD�t�H���_�[���p�X�ɐݒ�
        // [�������CD]/[�s���{��CD]/[�s�撬��CD]/�V�X�e������CD/
        transPath.append(sysHousingCd);
        transPath.append("/");

        return transPath.toString();
    }

    /**
     * ������{���̃^�C���X�^���v�����X�V����B<br/>
     * <br/>
     * @param sysHousingCd �X�V�ΏۃV�X�e������CD
     * @param editUserId �X�V��ID
     * @throws Exception
     */
    public void updateEditTimestamp(String sysHousingCd, String sysReformCd, String editUserId) throws Exception {
        this.panaHousingManager.updateEditTimestamp(sysHousingCd, editUserId);

        // �X�V�����𐶐�
        DAOCriteria criteria = new DAOCriteria();
        criteria.addWhereClause("sysReformCd", sysReformCd);

        // �X�V�I�u�W�F�N�g���쐬
        UpdateExpression[] updateExpression = new UpdateExpression[] { new UpdateValue("updUserId", editUserId),
                new UpdateValue("updDate", new Date()) };

        this.reformPlanDAO.updateByCriteria(criteria, updateExpression);
    }

    /**
     * Search all housing by the given housing cd
     * @param sysHousingCd the housing cd
     * @param full Return public housing if false. Else, return all
     * @return Housing
     * @exception Exception is thrown while implementing
     */
    public Housing searchHousingByPk(String sysHousingCd, boolean full)
            throws Exception {
        return this.panaHousingManager.searchHousingPk(sysHousingCd, full);
    }
}
