package jp.co.transcosmos.dm3.core.model.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.model.InformationManage;
import jp.co.transcosmos.dm3.core.model.exception.NotFoundException;
import jp.co.transcosmos.dm3.core.model.information.form.InformationForm;
import jp.co.transcosmos.dm3.core.model.information.form.InformationSearchForm;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.Information;
import jp.co.transcosmos.dm3.core.vo.InformationTarget;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.dao.NotEnoughRowsException;
import jp.co.transcosmos.dm3.dao.OrCriteria;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * ���m�点�����e�i���X�p Model �N���X
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.05	�V�K�쐬
 * H.Mizuno		2015.06.17	���m�点���擾���̏����������I�ɃI�[�o�[���C�h�o����悤�Ƀ��t�@�N�^�����O
 * 
 * ���ӎ���
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B
 * 
 * </pre>
 */
public class InformationManageImpl implements InformationManage {

	private static final Log log = LogFactory.getLog(InformationManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;
	
	/** ���m�点���擾�p DAO */
	protected DAO<JoinResult> informationListDAO;

	/** ���m�点���X�V�p DAO */
	protected DAO<Information> informationDAO;

	/** ���m�点���J����X�V�p DAO */
	protected DAO<InformationTarget> informationTargetDAO;

	/** ���m�点���e�[�u���̕ʖ� */
	public static final String IMFORMATION_ALIA = "information";

	/** ���m�点���J���񃍁[���e�[�u���̕ʖ� */
	public static final String IMFORMATION_TARGET_ALIA = "informationTarget";

	
	
	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}

	/**
	 * ���m�点���擾�p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���m�点���擾�p DAO
	 */
	public void setInformationListDAO(DAO<JoinResult> informationListDAO) {
		this.informationListDAO = informationListDAO;
	}

	/**
	 * ���m�点���X�V�p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���m�点���X�V�p DAO
	 */
	public void setInformationDAO(DAO<Information> informationDAO) {
		this.informationDAO = informationDAO;
	}

	/**
	 * ���m�点���J����X�V�p DAO���擾����B<br/>
	 * <br/>
	 * 
	 * @return ���m�点���J����X�V�p DAO
	 */
	public void setInformationTargetDAO(
			DAO<InformationTarget> informationTargetDAO) {
		this.informationTargetDAO = informationTargetDAO;
	}


	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点����V�K�ǉ�����B<br/>
	 * ���m�点�ԍ� �͎����̔Ԃ����̂ŁAInformationForm �� informationNo �v���p�e�B�ɂ͒l��ݒ肵�Ȃ����B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̓��͒l���i�[���� Form �I�u�W�F�N�g
 	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
 	 * 
	 * @return ���m�点�ԍ�
	 */
	@Override
	public String addInformation(InformationForm inputForm, String editUserId){

    	// �V�K�o�^�����̏ꍇ�A���̓t�H�[���̒l��ݒ肷��o���[�I�u�W�F�N�g�𐶐�����B
		// �o���[�I�u�W�F�N�g�́A�t�@�N�g���[���\�b�h�ȊO�ł͐������Ȃ����B
		// �i�p�����ꂽ�o���[�I�u�W�F�N�g���g�p����Ȃ��Ȃ�ׁB�j
		Information information = (Information) this.valueObjectFactory.getValueObject("Information");
		InformationTarget[] informationTargets
				= new InformationTarget[] {(InformationTarget) this.valueObjectFactory.getValueObject("InformationTarget")};


    	// �t�H�[���̓��͒l���o���[�I�u�W�F�N�g�ɐݒ肷��B
    	inputForm.copyToInformation(information, editUserId);
		
    	// �V�K�o�p�̃^�C���X�^���v����ݒ肷��B �i�X�V���̐ݒ����]�L�j
    	information.setInsDate(information.getUpdDate());
    	information.setInsUserId(editUserId);


		// �擾������L�[�l�ŊǗ����[�U�[����o�^
		this.informationDAO.insert(new Information[] { information });


		// ���J�Ώۋ敪���l�̏ꍇ�A���m�点���J����̃��R�[�h��ǉ�����B
		if ("2".equals(information.getDspFlg())) {
		
			// ���͒l��ݒ�
			inputForm.copyToInformationTarget(informationTargets);

			// ���m�点���J����̎�L�[�l���擾������L�[�l�ɐݒ肷��B
			for (InformationTarget informationTarget : informationTargets) {
				informationTarget.setInformationNo(information.getInformationNo());
			}
			this.informationTargetDAO.insert(informationTargets);
		}

		return information.getInformationNo();
	}



	/**
	 * ���m�点�̍X�V���s��<br/>
	 * <br/>
	 * @param inputForm ���m�点�̓��͒l���i�[���� Form �I�u�W�F�N�g
	 * 
	 * @exception NotFoundException �X�V�Ώۂ����݂��Ȃ��ꍇ
	 */
	@Override
	public void updateInformation(InformationForm inputForm, String editUserId)
			throws NotFoundException {

    	// �X�V�����̏ꍇ�A�X�V�Ώۃf�[�^���擾����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", inputForm.getInformationNo());

		List<JoinResult> informations = this.informationListDAO.selectByFilter(criteria);

        // �Y������f�[�^�����݂��Ȃ��ꍇ�́A��O���X���[����B
		if (informations == null || informations.size() == 0) {
			throw new NotFoundException();
		}    	

		
        // ���m�点�����擾���A���͂����l�ŏ㏑������B
		Information information
			= (Information) informations.get(0).getItems().get(IMFORMATION_ALIA);

    	inputForm.copyToInformation(information, editUserId);


		// ���m�点���J������X�V�E�폜
		this.informationTargetDAO.deleteByFilter(inputForm.buildPkCriteria());


		// ���J�Ώۋ敪���l�̏ꍇ
		if ("2".equals(information.getDspFlg())) {

			InformationTarget[] informationTargets
				= new InformationTarget[] {(InformationTarget) this.valueObjectFactory.getValueObject("InformationTarget")};

			inputForm.copyToInformationTarget(informationTargets);
			
			// ���m�点���J�����ǉ�
			this.informationTargetDAO.insert(informationTargets);
		}
    	
		// ���m�点���̍X�V
		this.informationDAO.update(new Information[]{information});

	}

	
	
	/**
	 * �p�����[�^�œn���ꂽ Form �̏��ł��m�点�����폜����B<br/>
	 * �o���f�[�V�������̃`�F�b�N�����͌Ăяo�����ŗ\�ߎ��{���Ă������B<br/>
	 * InformationSearchForm �� informationNo �v���p�e�B�ɐݒ肳�ꂽ�l����L�[�l�Ƃ��č폜����B
	 * �܂��A�폜�Ώۃ��R�[�h�����݂��Ȃ��ꍇ�ł�����I���Ƃ��Ĉ������B<br/>
	 * <br/>
	 * @param inputForm ���m�点���̌����l�i�폜�ΏۂƂȂ� informationNo�j���i�[���� Form �I�u�W�F�N�g
	 */
	@Override
	public void delInformation(InformationForm inputForm) {

		// ���m�点���J������X�V�E�폜
		this.informationTargetDAO.deleteByFilter(inputForm.buildPkCriteria());

		// ���m�点���̍X�V
		this.informationDAO.deleteByFilter(inputForm.buildPkCriteria());

	}


	
	/**
	 * �T�C�g TOP �ɕ\�����邨�m�点�����擾����B<br/>
	 * <br/>
	 * @see InformationManageImpl#buildTopInformationCriteria()
	 * @return �T�C�g TOP �ɕ\������A���m�点���̃��X�g
	 */
	@Override
	public List<Information> searchTopInformation() {
		// ���m�点�����擾
		return this.informationDAO.selectByFilter(buildTopInformationCriteria());

	}


	
	/**
	 * �T�C�g TOP �ɕ\�����邨�m�点�����擾���錟�������𐶐�����B<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * <br/>
	 * @return �T�C�g TOP �ɕ\������A���m�点���擾�p�����I�u�W�F�N�g
	 */
	protected DAOCriteria buildTopInformationCriteria(){
		
		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();

		// ���J�Ώۋ敪 = �u�����܂ޑS����v
		criteria.addWhereClause("dspFlg", "0");

		// �V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= �V�X�e�����t
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sdf.format(sysDate),
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= �V�X�e�����t
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sdf.format(sysDate),
				DAOCriteria.GREATER_THAN_EQUALS);
		
		criteria.addSubCriteria(startOrCriteria);
		criteria.addSubCriteria(endOrCriteria);
		
		return criteria;
	}
	
	
	
	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点�����擾����B<br/>
	 * <br/>
	 * @see InformationManageImpl#buildMyPageInformationCriteria(String userId)
	 * @return �}�C�y�[�W TOP �ɕ\������A���m�点���̃��X�g
	 */
	@Override
	public List<Information> searchMyPageInformation(String userId) {
		// ���m�点�����擾
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(buildMyPageInformationCriteria(userId));

		List<Information> result = new ArrayList<>();

		if (information == null || information.size() == 0) {
			return result;
		}

		for (JoinResult a : information){
			result.add((Information) a.getItems().get(IMFORMATION_ALIA));
		}

		return result;
	}



	/**
	 * �}�C�y�[�W TOP �ɕ\�����邨�m�点���擾�Ɏg�p���錟�������𐶐�����B<br/>
	 * ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����
	 * �̃��[�U�[ID �������ƈ�v����K�v������B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * <br/>
	 * @return �}�C�y�[�W TOP �ɕ\������A���m�点���̃��X�g
	 */
	protected DAOCriteria buildMyPageInformationCriteria(String userId) {

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria mainCriteria = new DAOCriteria();
		// ���J�Ώۋ敪 =�u�S�{����v�A�܂��́A�u�l�v
		OrCriteria dspCriteria = new OrCriteria();
		// ���J�Ώۋ敪 = �u�l�v
		DAOCriteria dspCriteria2 = new DAOCriteria();
		
		// ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v
		// �u�l�v�̏ꍇ�A���m�点���J����̃��[�U�[ID �������ƈ�v����
		dspCriteria2.addWhereClause("dspFlg", "2");
		dspCriteria2.addWhereClause("userId", userId);
		dspCriteria.addSubCriteria(dspCriteria2);
		dspCriteria.addWhereClause("dspFlg", "1");
		mainCriteria.addSubCriteria(dspCriteria);
		
		// �V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= �V�X�e�����t
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sdf.format(sysDate),
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= �V�X�e�����t
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sdf.format(sysDate),
				DAOCriteria.GREATER_THAN_EQUALS);

		mainCriteria.addSubCriteria(startOrCriteria);
		mainCriteria.addSubCriteria(endOrCriteria);

		return mainCriteria;
	}
	
	
	
	/**
	 * ���m�点�����������A���ʃ��X�g�𕜋A����B�i�Ǘ���ʗp�j<br/>
	 * �����œn���ꂽ Form �p�����[�^�̒l�Ō��������𐶐����A���m�点������������B<br/>
	 * �������ʂ� Form �I�u�W�F�N�g�Ɋi�[����A�擾�����Y��������߂�l�Ƃ��ĕ��A����B<br/>
	 * <br/>
	 * @param searchForm ���������A����сA�������ʂ̊i�[�I�u�W�F�N�g
	 * @return �Y������
	 */
	@Override
	public int searchAdminInformation(InformationSearchForm searchForm) {

		// ���m�点����������������𐶐�����B
		DAOCriteria criteria = searchForm.buildCriteria();

		// ���m�点�̌���
		List<JoinResult> informationList;
		try {
			informationList = this.informationListDAO.selectByFilter(criteria);

		} catch (NotEnoughRowsException err) {

			int pageNo = (err.getMaxRowCount() - 1)
					/ searchForm.getRowsPerPage() + 1;
			log.warn("resetting page to " + pageNo);
			searchForm.setSelectedPage(pageNo);
			criteria = searchForm.buildCriteria();
			informationList = this.informationListDAO.selectByFilter(criteria);
		}

		searchForm.setRows(informationList);

		return informationList.size();
	}



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�T�C�g TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�����܂ޑS����v���擾�ΏۂɂȂ�B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * @return�@���m�点���
	 */
	@Override
	public Information searchTopInformationPk(String informationNo) {

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationNo);

		// ���J�Ώۋ敪 = �u�����܂ޑS����v
		criteria.addWhereClause("dspFlg", "0");
		
		// �V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= �V�X�e�����t
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);
		startOrCriteria.addWhereClause("startDate", sysDate,
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= �V�X�e�����t
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sysDate,
				DAOCriteria.GREATER_THAN_EQUALS);
		
		criteria.addSubCriteria(startOrCriteria);
		criteria.addSubCriteria(endOrCriteria);

		// ���m�点�����擾
		List<Information> information = this.informationDAO
				.selectByFilter(criteria);

		if (information == null || information.size() == 0) {
			return null;
		}

		return information.get(0);
	}



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ�u���m�点�ԍ��v �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�}�C�y�[�W TOP �p�j<br/>
	 * ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v���擾�ΏۂɂȂ邪�A�u�l�v�̏ꍇ�A���m�点���J����
	 * �̃��[�U�[ID �������ƈ�v����K�v������B<br/>
	 * �܂��A�V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�B<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * @param userId �}�C�y�[�W���[�U�[ID
	 * 
	 * @return�@���m�点���
	 */
	@Override
	public Information searchMyPageInformationPk(String informationNo, String userId){

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria mainCriteria = new DAOCriteria();
		mainCriteria.addWhereClause("informationNo", informationNo);
		
		// ���J�Ώۋ敪 =�u�S�{����v�A�܂��́A�u�l�v
		OrCriteria dspCriteria = new OrCriteria();
		// ���J�Ώۋ敪 = �u�l�v
		DAOCriteria dspCriteria2 = new DAOCriteria();

		// ���J�Ώۋ敪 = �u�S�{����v�A�܂��́A�u�l�v
		// �u�l�v�̏ꍇ�A���m�点���J����̃��[�U�[ID �������ƈ�v����
		dspCriteria2.addWhereClause("dspFlg", "2");
		dspCriteria2.addWhereClause("userId", userId);
		dspCriteria.addSubCriteria(dspCriteria2);
		dspCriteria.addWhereClause("dspFlg", "1");
		mainCriteria.addSubCriteria(dspCriteria);

		// �V�X�e�����t�����J���Ԓ��ł��邨�m�点��񂪎擾�ΏۂƂȂ�
		Date sysDate = new Date();
		// startDate IS NUll OR startDate <= �V�X�e�����t
		OrCriteria startOrCriteria = new OrCriteria();
		startOrCriteria.addWhereClause("startDate", null, DAOCriteria.IS_NULL);	
		startOrCriteria.addWhereClause("startDate", sysDate,
				DAOCriteria.LESS_THAN_EQUALS);
		// endDate IS NUll OR endDate >= �V�X�e�����t
		OrCriteria endOrCriteria = new OrCriteria();
		endOrCriteria.addWhereClause("endDate", null, DAOCriteria.IS_NULL);
		endOrCriteria.addWhereClause("endDate", sysDate,
				DAOCriteria.GREATER_THAN_EQUALS);
		
		mainCriteria.addSubCriteria(startOrCriteria);
		mainCriteria.addSubCriteria(endOrCriteria);

		// ���m�点�����擾
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(mainCriteria);

		if (information == null || information.size() == 0) {
			return null;
		}
		
		return (Information) information.get(0).getItems().get(IMFORMATION_ALIA);
	}



	/**
	 * ���N�G�X�g�p�����[�^�œn���ꂽ���[�U�[ID �i��L�[�l�j�ɊY�����邨�m�点���𕜋A����B�i�Ǘ��y�[�W�p�j<br/>
	 * �����Y���f�[�^��������Ȃ��ꍇ�Anull �𕜋A����B<br/>
	 * <br/>
	 * @param informationNo �擾�ΏۂƂȂ邨�m�点�ԍ�
	 * @return�@���m�点���A���m�点���J����
	 */
	@Override
	public JoinResult searchAdminInformationPk(String informationNo) {

		// ���m�点�����擾����ׂ̎�L�[��ΏۂƂ������������𐶐�����B
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("informationNo", informationNo);

		// ���m�点�����擾
		List<JoinResult> information = this.informationListDAO
				.selectByFilter(criteria);

		if (information == null || information.size() == 0) {
			return null;
		}

		return information.get(0);
	}

}
