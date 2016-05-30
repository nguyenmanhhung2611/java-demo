package jp.co.transcosmos.dm3.core.model.csvMaster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.csvMaster.dao.StationSortOrderUpdateDAO;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.StationCsvForm;
import jp.co.transcosmos.dm3.core.util.CsvStreamReader;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.RouteMst;
import jp.co.transcosmos.dm3.core.vo.RrMst;
import jp.co.transcosmos.dm3.core.vo.StationMst;
import jp.co.transcosmos.dm3.core.vo.StationRouteInfo;
import jp.co.transcosmos.dm3.core.vo.UnregistRouteMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.validation.NotExistsInCriteriaValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * �w�}�X�^�����e�i���X�p Model �N���X.
 * <p>
 * ���@�������񋟂���w���CSV ����荞�݁A�S����Ѓ}�X�^�A�H���}�X�^�A�w�}�X�^�A
 * �w�E�H���Ή��\���X�V����B<br/>
 * <ul>
 * <li>�o�^���O�H���}�X�^�ɐݒ肳�ꂽ�H��CD �́A�w�}�X�^CSV �̎�荞�ݑΏۊO�Ƃ���B</li>
 * <li>�w���CSV �̉�Ж��A�H�����A�w���́AstationAndRouteReplace.xml�@�ɒu�����[����ݒ�
 *     ���鎖�ɂ��A�Œ�l�ɕϊ����Ď捞�ގ����ł���B<br/>
 *     �i�ڍׂ́A�e�u�����\�b�h�̐������Q�ƁB�j</li>
 * <li>CSV �ɑ��݂��Ȃ��S����ЁA�H���A�w�̏��́A�S����Ѓ}�X�^�A�H���}�X�^�A�w�}�X�^�A�w�E�H���Ή��\
 *     �Ɋ��ɑ��݂���ꍇ�͍폜���鎖�B</li>
 * <li>���݂��Ȃ��S����Ж��� CSV �ɑ��݂���ꍇ�A�S�����CD ���̔Ԃ��ēS����Ѓ}�X�^�ɓo�^����B</li>
 * <li>CSV �̃f�[�^�ɊY������H���}�X�^�A�w�}�X�^�A�w�E�H���Ή��\�̃f�[�^�����ɑ��݂���ꍇ�͍X�V���A
 *     ���݂��Ȃ��ꍇ�͒ǉ�����B</li>
 * <li>CSV ����擾�ł��Ȃ��S����Ѓ}�X�^�A�H���}�X�^�̕\�����f�[�^�A�����f�[�^�̕\���� + ��L�[�l��
 *     �ēx�\�[�g���Ă���l��A�ԂōĐݒ肷��B</li>
 * </ul>
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.02.05	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 * ���̃N���X�̓V���O���g���� DI �R���e�i�ɒ�`�����̂ŁA�X���b�h�Z�[�t�ł��鎖�B<br/>
 * 
 */
public class StationMasterManageImpl implements CsvMasterManage {

	private static final Log log = LogFactory.getLog(StationMasterManageImpl.class);
	
	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** �o�^���O�H���}�X�^ DAO */
	protected DAO<UnregistRouteMst> unregistRouteMstDAO;

	/** �S����Ѓ}�X�^ DAO */
	protected DAO<RrMst> rrMstDAO;
	
	/** �H���}�X�^ DAO */
	protected DAO<RouteMst> routeMstDAO;

	/** �w���}�X�^ DAO */
	protected DAO<StationMst> stationMstDAO;
	
	/** �w�E�H���Ή��\ DAO */
	protected DAO<StationRouteInfo> stationRouteInfoDAO;
	
	/** �s���{���}�X�^ DAO */
	protected DAO<PrefMst> prefMstDAO;

	/** �H���}�X�^�A�S����Ѓ}�X�^�̕\�����X�V�p DAO */
	protected StationSortOrderUpdateDAO stationSortOrderUpdateDAO;
	
	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;

	/** �w���CSV �̉��H�����l���i�[���� Form �𐶐����� Factory */
	protected CsvUploadFormFactory formFactory;
	
	/** CSV �G���R�[�h������ */
	protected String csvEncode = "ms932";

	/** �^�C�g���s���X�L�b�v���邩 �itrue �̏ꍇ�A�P�s�ڂ��X�L�b�v�j */
	private boolean titleSkip = true;



	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
	}
	
	/**
	 * �o�^���O�H���}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param unregistRouteMstDAO �o�^���O�H���}�X�^ DAO
	 */
	public void setUnregistRouteMstDAO(DAO<UnregistRouteMst> unregistRouteMstDAO) {
		this.unregistRouteMstDAO = unregistRouteMstDAO;
	}

	/**
	 * �S����Ѓ}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param rrMstDAO �S����Ѓ}�X�^ DAO
	 */
	public void setRrMstDAO(DAO<RrMst> rrMstDAO) {
		this.rrMstDAO = rrMstDAO;
	}

	/**
	 * �H���}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param routeMstDAO�@�H���}�X�^�@DAO
	 */
	public void setRouteMstDAO(DAO<RouteMst> routeMstDAO) {
		this.routeMstDAO = routeMstDAO;
	}

	/**
	 * �w���}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationMstDAO �w���}�X�^ DAO
	 */
	public void setStationMstDAO(DAO<StationMst> stationMstDAO) {
		this.stationMstDAO = stationMstDAO;
	}
	
	/**
	 * �w�E�H���Ή��\ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationRouteInfo�@�w�E�H���Ή��\�@DAO
	 */
	public void setStationRouteInfoDAO(DAO<StationRouteInfo> stationRouteInfo) {
		this.stationRouteInfoDAO = stationRouteInfo;
	}
	
	/**
	 * �s���{���}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param prefMstDAO�@�s���{���}�X�^�@DAO
	 */
	public void setPrefMstDAO(DAO<PrefMst> prefMstDAO) {
		this.prefMstDAO = prefMstDAO;
	}

	/**
	 * �S����Ѓ}�X�^�A�H���}�X�^�̕\�����X�V�p DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param stationSortOrderUpdateDAO �S����Ѓ}�X�^�A�H���}�X�^�̕\�����X�V�p DAO
	 */
	public void setStationSortOrderUpdateDAO(StationSortOrderUpdateDAO stationSortOrderUpdateDAO) {
		this.stationSortOrderUpdateDAO = stationSortOrderUpdateDAO;
	}

	/**
	 * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager ���ʃR�[�h�ϊ�����
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �w���CSV �̉��H�����l���i�[���� Form �𐶐����� Factory ��ݒ肷��B<br/>
	 * <br/>
	 * @param formFactory Form �� Factory
	 */
	public void setFormFactory(CsvUploadFormFactory formFactory) {
		this.formFactory = formFactory;
	}

	/**
	 * CSV �̕����R�[�h��ύX����ꍇ�A���̃v���p�e�B�ɐݒ肷��B<br/>
	 * ���ݒ�̏ꍇ�A�����l�Ƃ��� ms932 ���g�p�����B<br/>
	 * <br/>
	 * @param csvEncode CSV �t�@�C���̕����R�[�h
	 */
	public void setCsvEncode(String csvEncode) {
		this.csvEncode = csvEncode;
	}

	/**
	 * �f�t�H���g�ł́A1�s�ڂ��^�C�g���s�Ƃ��ăX�L�b�v����B<br/>
	 * �P�s�ڂ���f�[�^���n�܂�ꍇ�A���̃v���p�e�B�� false �ɐݒ肷��B<br/>
	 * <br/>
	 * @param titleSkip true �̏ꍇ�A�P�s�ڂ��X�L�b�v�i�f�t�H���g�j�Afalse �̏ꍇ�A�P�s�ڂ��烍�[�h
	 */
	public void setTitleSkip(boolean titleSkip) {
		this.titleSkip = titleSkip;
	}

	
	
	/**
	 * �w�}�X�^CSV �̎�荞�ݏ���<br/>
	 * ����I�������ꍇ�A�߂�l�̃G���[�I�u�W�F�N�g�̃��X�g������ 0 ���ɂȂ�B<br/>
	 * <br/>
	 * @param inputStream ���N�G�X�g�ő��M���ꂽ File �� InputStream �I�u�W�F�N�g
	 * @param editUserId ���O�C�����[�U�[�h�c�i�X�V���p�j
	 * @param errors �G���[�I�u�W�F�N�g�̃��X�g
	 * 
	 * @return ���펞 true�ACSV �̃o���f�[�V�����ŃG���[������ꍇ false
	 * 
	 * @throws IOException
	 */
	@Override
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws IOException {
		
		// �f�[�^�o�^�p Map �I�u�W�F�N�g�̏�����

		// �S����Џ��ɒǉ���������i�[���� Map �I�u�W�F�N�g�𐶐�����B
		// �iKey = �S����Ж��AValue = �S����ЃI�u�W�F�N�g�j
		Map<String, RrMst> addRrMstMap = new HashMap<>();

		// �H���}�X�^�ɒǉ���������i�[���� Map �I�u�W�F�N�g�𐶐�����B
		// �iKey = �H��CD�AValue = �H���}�X�^�I�u�W�F�N�g�j
		Map<String, RouteMst> addRouteMstMap = new HashMap<>();

		// �w�}�X�^�ɒǉ���������i�[���� Map �I�u�W�F�N�g�𐶐�����B
		// �iKey = �wCD�AValue = �w�}�X�^�I�u�W�F�N�g�j
		Map<String, StationMst> addStationMstMap = new HashMap<>();

		// �w�E�H���Ή��\�ɒǉ���������i�[���� Map �I�u�W�F�N�g�𐶐�����B
		// �iKey = �H��CD + �wCD�AValue = �w�E�H���Ή��\�I�u�W�F�N�g�j
		Map<String, StationRouteInfo> addStationRouteInfoMap = new HashMap<>();



		// �S����Ђ̊��������擾
		
		// �����̓S����Ѓ}�X�^���� Map �ƁA�ő�̓S�����CD �̍ő�l���擾����B
		// �S�����CD �̍ő�l�́A�V���ȓS����Ђ�o�^����ۂ̃L�[�l�����Ɏg�p����B
		// �iKey = �S����Ж��A Value = �S����Ѓ}�X�^�I�u�W�F�N�g�j
		Map<String, RrMst> rrMstMap = new HashMap<>();
		String maxRrCd = getRrMst(rrMstMap);

		// �����̓S����Џ�񂩂�A�S�����CD �� Set �I�u�W�F�N�g���쐬����B
		// ���̒l�́A�V���ȓS�����CD ���̔Ԃ��鎞�ɁA��ӂȒl���`�F�b�N����̂Ɏg�p����B
		Set<String> allRrCdSet = new HashSet<>();
		for (Entry<String, RrMst> e : rrMstMap.entrySet()){
			allRrCdSet.add(e.getValue().getRrCd());
		}


		// �H�����̊��������擾

		// �����̘H���}�X�^����A�H��CD �̕\�������i�[���� Map �I�u�W�F�N�g���擾����B
		// �iKey = �H��CD�A Value = �\�����j
		Map<String, Integer> routeOrderMap = getRouteOrder();

		// ��荞�ݑΏۊO�Ƃ���H��CD �� Set ���擾����B
		Set<String> unregistRouteCdSet = getUnregistRouteCd();
		


		// ���݂̃V�X�e�����t���擾�i�c�a�X�V���̃^�C���X�^���v�Ƃ��Ďg�p�B�j
		Date currentDate = new Date();



		// �A�b�v���[�h���ꂽ CSV �t�@�C����Ǎ���
		InputStreamReader stream = new InputStreamReader(inputStream, this.csvEncode);

		try (CsvStreamReader reader = new CsvStreamReader(stream)){

			int listCnt = 0;				// �s�ԍ�

			// 1�s�ڂ̓^�C�g���Ȃ̂œǂݔ�΂��ݒ�����ēǍ��݂��J�n����B
			if (this.titleSkip){
				reader.setFirstSkip(true);
				listCnt = 1;				// �^�C�g���s��ǂݔ�΂��ꍇ�A�P����X�^�[�g����B
			}
			reader.open();


			Map<Integer, String> csvData;	// �擾���� CSV DATA
			while ((csvData = reader.getRowData()) != null){

				++listCnt;

				// ���ڐ����������Ȃ��ꍇ
				if(csvData.size() != 17){
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.add(new ValidationFailure("csvFormat", null, null,null));
					return false;
				}


				// CSV �̒l��ݒ肵�� Form �𐶐�����B
				StationCsvForm csvForm = createForm(csvData);

				// �S����Ж������̃��[���ŕϊ���������B
				replaceRrName(csvForm);

				// �H���������̃��[���ŕϊ���������B
				replaceRouteName(csvForm);

				// �u�H�����J�b�R�t�v�����̃��[���ŕϊ���������B
				replaceRouteNameFull(csvForm);
				
				// �u�H�����S����Еt���v�����̃��[���ŕϊ���������B
				replaceRouteNameRr(csvForm);


				// �����f�[�^�A�܂��͒ǉ��\��f�[�^�ɊY������S����Џ�񂪑��݂���ꍇ�A�S�����CD�A
				// �S����Ђ̕\������ Form �ɐݒ肷��B
				// �܂��A�����f�[�^�����݂��Ȃ��ꍇ�͓S�����CD ��V���ɍ̔Ԃ��� Form �ɐݒ肷��B
				// �i���̏ꍇ�A�\������ null ���ݒ肳���B�j
				maxRrCd = confRrCdAndRrSortOrder(csvForm, rrMstMap, addRrMstMap, allRrCdSet, maxRrCd);

				// �ő�l�ƂȂ�S�����CD �����A�����B
				// �����̔Ԃł��Ȃ��ꍇ�� null �����A�����B
				if (maxRrCd == null){
					// �S����ЃR�[�h �I�[�o�[�t���[
					errors.add(new ValidationFailure("csvListCnt", null, null,null));
					return false;
				}


				// �����H���}�X�^�����݂���ꍇ�A�H���}�X�^�̕\������ Form �֐ݒ肷��B
				confRouteSortOrder(routeOrderMap, csvForm);
				
				
				// �t�H�[�}�b�g�`�F�b�N
				List<ValidationFailure> subErrors = new ArrayList<ValidationFailure>();
				csvForm.validate(subErrors);


				// �s���{��CD �����݂��邩�̃o���f�[�V���������s
				DAOCriteria prefCri = new DAOCriteria();
				prefCri.addWhereClause("prefCd", csvForm.getPrefCd(), DAOCriteria.EQUALS, false);

				ValidationChain valPrefCd = new ValidationChain("stationCsv.prefCd", csvForm.getPrefCd());
				valPrefCd.addValidation(new NotExistsInCriteriaValidation(this.prefMstDAO, prefCri));
				valPrefCd.validate(subErrors);
				
				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					return false;
				}			


				// �o�^���O�H���}�X�^�e�[�u���ɑ��݂���ꍇ�͏��O����
				if (!unregistRouteCdSet.contains(csvForm.getRouteCd())) {

					// �S����Ѓ}�X�^
					// CSV �ɂ͓S�����CD �����݂��Ȃ��̂ŁA�S����Ж��ő��݂��邩���`�F�b�N����B
					// �ǉ��\�� Map �ɓS����Ж������݂��Ȃ��ꍇ�A�S����Џ���ǉ�����B
					addRrMstData(addRrMstMap, csvForm, editUserId, currentDate);

					// �w�E�H���Ή��\
					// �H��CD + �wCD ���L�[�Ƃ��āA�w�E�H���Ή��\�̒ǉ��Ώ� Map �ɑ��݂��邩���`�F�b�N����B
					// ���݂��Ȃ��ꍇ�� Map �ɒǉ�����B
					addStationRouteData(addStationRouteInfoMap, csvForm, editUserId, currentDate);

					// �H���}�X�^
					// �H��CD ���L�[�Ƃ��āA�H���}�X�^�̒ǉ� Map �ɑ��݂��邩���`�F�b�N����B
					// ���݂��Ȃ��ꍇ�� Map �ɒǉ�����B
					addRouteMstData(addRouteMstMap, csvForm, editUserId, currentDate);

					
					// �w���}�X�^
					// �wCD ���L�[�Ƃ��āA�w�}�X�^�̒ǉ� Map �ɑ��݂��邩���`�F�b�N����B
					// ���݂��Ȃ��ꍇ�� Map �ɒǉ�����B
					addStationMstData(addStationMstMap, csvForm, editUserId, currentDate);

				}

				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					return false;
				}			
			}
		}

		// �o�^�f�[�^��1�����Ȃ��ꍇ
		if(addStationMstMap.size()==0){
			errors.add(new ValidationFailure("csvNoData", null, null,null));
			return false;
		}


		// �S�f�[�^�폜 ���q�e�[�u������폜!
		DAOCriteria dummyCri = new DAOCriteria();
		this.stationRouteInfoDAO.deleteByFilter(dummyCri);
		this.stationMstDAO.deleteByFilter(dummyCri);
		this.routeMstDAO.deleteByFilter(dummyCri);
		this.rrMstDAO.deleteByFilter(dummyCri);

		// �o�^�@���e�e�[�u������o�^!
		this.rrMstDAO.insert((RrMst[])addRrMstMap.values().toArray(new RrMst[addRrMstMap.size()]));
		this.routeMstDAO.insert((RouteMst[])addRouteMstMap.values().toArray(new RouteMst[addRouteMstMap.size()]));
		this.stationMstDAO.insert((StationMst[])addStationMstMap.values().toArray(new StationMst[addStationMstMap.size()]));
		this.stationRouteInfoDAO.insert((StationRouteInfo[])addStationRouteInfoMap.values().toArray(new StationRouteInfo[addStationRouteInfoMap.size()]));

		// ���я����X�V(�����̕��я��{�R�[�h�Ń\�[�g���ĐU��Ȃ���)
		Object[] updData = new Object[]{currentDate, editUserId};
		this.stationSortOrderUpdateDAO.updateRrMstSortOrder(updData);
		this.stationSortOrderUpdateDAO.updateRouteMstSortOrder(updData);

		return true;
	}



	/**
	 * ��荞�ݑΏۊO�Ƃ���H��CD ��ݒ肵�� Set �I�u�W�F�N�g���擾����B<br/>
	 * �o�^���O�H���}�X�^�ɓo�^����Ă���H��CD ���擾���ASet �I�u�W�F�N�g�Ɋi�[���ĕ��A����B<br/>
	 * <br/>
	 * @return�@��荞�ݑΏۊO�Ƃ���H��CD ���i�[���ꂽ Map �I�u�W�F�N�g
	 */
	protected Set<String> getUnregistRouteCd(){

		// �o�^���O�H���}�X�^����S���擾
		List<UnregistRouteMst> unregistRouteMstList = this.unregistRouteMstDAO.selectByFilter(null);

		Set<String> unregistRouteCdSet = new HashSet<>();

		// �擾�����H��CD �� Set �Ɋi�[���ĕ��A�B
		for (UnregistRouteMst unregistRoute : unregistRouteMstList){
			unregistRouteCdSet.add(unregistRoute.getRouteCd());
		}

		return unregistRouteCdSet;
	}



	/**
	 * ���ɓo�^����Ă���S����Џ��� Map �I�u�W�F�N�g���擾����B<br/>
	 * <br/>
	 * Key = �S����Ж��A Value = �S����Ѓ}�X�^�I�u�W�F�N�g
	 * <br/>
	 * @param rrMstMap �S����Џ��� Map �I�u�W�F�N�g
	 * @return �ő�S�����CD
	 */
	protected String getRrMst(Map<String, RrMst> rrMstMap) {

		// �ő�S�����CD
		String maxRrCd = "000";

		// �S����Ѓ}�X�^��ǂݍ��݁AMap�ɔz�u
		// �V���ȓS����Ђ�o�^����ۂ̓S�����CD �́A�o�^�ϓS�����CD �̍ő�l + 1 ��
		// �Z�o����B
		// ���ׁ̈A�����̓S����Ѓ}�X�^����f�[�^���擾����ۂɂ͓S�����CD �Ń\�[�g��������
		// ���擾����B�@�i
		DAOCriteria criteria = new DAOCriteria();
		criteria.addOrderByClause("rrCd");
		List<RrMst> rrMstList = this.rrMstDAO.selectByFilter(null);

		for (RrMst rrMst : rrMstList){
			maxRrCd = rrMst.getRrCd();
			rrMstMap.put(rrMst.getRrName(), rrMst);
		}
		
		return maxRrCd;
	}
	
	
	
	/**
	 * �����H��CD �i�H���}�X�^�j�̕\������ Map ���擾����B<br/>
	 * <br/>
	 * Key = �H��CD�A Value = �\����
	 * <br/>
	 * @return �H��CD �̕\�������i�[���� Map �I�u�W�F�N�g
	 */
	protected Map<String, Integer> getRouteOrder(){

		Map<String, Integer> routeOrderMap = new HashMap<String, Integer>();

		// �H���}�X�^���擾����B
		List<RouteMst> routeMstList = this.routeMstDAO.selectByFilter(null);

		for (RouteMst routeMst : routeMstList){
			
			// note
			// �����R�[�h�ł́A�H��CD �ł͂Ȃ��A�S�����CD �� Key �Ƃ��Đݒ肵�Ă����B
			// ���� Map �����ۂɎg�p���Ă���ӏ��ł͘H��CD �ŎQ�Ƃ��Ă����̂ŁA�����o�O�Ɏv����B
			routeOrderMap.put(routeMst.getRouteCd(), routeMst.getSortOrder());
		}

		return routeOrderMap;
	}
	
	
	
	/**
	 * �w���CSV �̒l��ݒ肵�� Form �I�u�W�F�N�g��������B<br/>
	 * <br/>
	 * @param csvData CSV �f�[�^�[
	 * @return�@Form �I�u�W�F�N�g
	 */
	protected StationCsvForm createForm(Map<Integer, String> csvData){
		
		StationCsvForm form = this.formFactory.createStationCsvForm();

		form.setRouteCd(csvData.get(1));						// �H���R�[�h
		form.setRrName(csvData.get(2));							// �S����Ж�
		form.setRouteNameFull(csvData.get(4));					// �H�������ʕt��
		form.setRouteNameRr(csvData.get(7));					// �H�������ʕt���A�S����Еt��
		form.setStationCd(csvData.get(10));						// �w�R�[�h
		form.setStationNameFull(csvData.get(11));				// �w�����ʂ���
		form.setPrefCd(csvData.get(15));						// �s���{���R�[�h
		form.setStationRouteDispOrder(csvData.get(13));			// ��ԏ�1
		form.setRouteName(splitKakko(form.getRouteNameFull()));	// �H����

		return form;
	}



	/**
	 * ����̏����ɊY������S����Ж���u������B<br/>
	 * �E�H��CD �� "J" ����n�܂�ꍇ�͓S����Ж����u�i�q�v�ɂ���B<br/>
	 * �E�S����Ж����ϊ��\�istationAndRouteReplace.xml�j�ɐݒ肳��Ă���ꍇ�A
	 *  �擾�����l�Œu������B<br/>
	 * <br/>
	 * @param csvForm ���H���� CSV �̒l��ݒ肷�� Form
	 */
	protected void replaceRrName(StationCsvForm csvForm){

		// �H��CD�uJ�v����͂��܂���͓̂S����Ж����u�i�q�v�Ƃ���
		if (csvForm.getRouteCd().startsWith("J")) {
			csvForm.setRrName("�i�q");
		}

		// �ϊ��\�ɑ��݂���ꍇ�� �S����Ж���ϊ�����B			
		String repRrName = this.codeLookupManager.lookupValue("rrName", csvForm.getRrName());
		if(repRrName != null){
			csvForm.setRrName(repRrName);
		}
	}


	
	/**
	 * ����̏����ɊY������H������u������B<br/>
	 * �H��CD ���ϊ��\�istationAndRouteReplace.xml�j�ɐݒ肳��Ă���ꍇ�A
	 * �擾�����l�Œu������B<br/>
	 * <br/>
	 * @param csvForm ���H���� CSV �̒l��ݒ肷�� Form
	 */
	protected void replaceRouteName(StationCsvForm csvForm) {

		// �ϊ��\�ɑ��݂���ꍇ�� �H������ϊ�����B			
		String repRouteName = this.codeLookupManager.lookupValue("routeName", csvForm.getRouteCd());
		if(repRouteName != null){
			csvForm.setRouteName(repRouteName);
		}
	}


	
	/**
	 * ����̏����ɊY������u�H�����J�b�R�t�v��u������B<br/>
	 * �H��CD ���ϊ��\�istationAndRouteReplace.xml�j�ɐݒ肳��Ă���ꍇ�A
	 * �擾�����l�Œu������B<br/>
	 * <br/>
	 * @param csvForm ���H���� CSV �̒l��ݒ肷�� Form
	 */
	protected void replaceRouteNameFull(StationCsvForm csvForm){

		// �ϊ��\�ɑ��݂���ꍇ�� �u�H�����J�b�R�t�v��ϊ�����B			
		String repRouteNameFull = this.codeLookupManager.lookupValue("routeNameFull", csvForm.getRouteCd());
		if(repRouteNameFull != null){
			csvForm.setRouteNameFull(repRouteNameFull);
		}
	}

	

	/**
	 * ����̏����ɊY������u�H�����S����Еt���v��u������B<br/>
	 * �E�u�H�����S����Еt���v�̒l�ɁA�J�b�R�ň͂܂ꂽ�l������ꍇ�A���̕�������菜���B
	 * �H��CD ���ϊ��\�istationAndRouteReplace.xml�j�ɐݒ肳��Ă���ꍇ�A
	 * �擾�����l�Œu������B<br/>
	 * <br/>
	 * @param csvForm ���H���� CSV �̒l��ݒ肷�� Form
	 */
	protected void replaceRouteNameRr(StationCsvForm csvForm){

		// �f�[�^�ɃJ�b�R���܂܂�Ă���ꍇ�A���̕�������������B
		csvForm.setRouteNameRr(splitKakko(csvForm.getRouteNameRr()));

		// �ϊ��\�ɑ��݂���ꍇ�� �u�H�����S����Еt���v��ϊ�����B			
		String repRouteNameRr = this.codeLookupManager.lookupValue("routeNameRr", csvForm.getRouteCd());
		if(repRouteNameRr != null){
			csvForm.setRouteNameRr(repRouteNameRr);
		}
	}

	
	
	/**
	 * �S�����CD�A�S����Ђ̕\������ݒ肷��B<br/>
	 * �S����Ж��ɊY������f�[�^���S����Ѓ}�X�^�ɑ��݂���ꍇ�A���̃}�X�^�[�ɐݒ肳��Ă���S�����CD�A
	 * �S����Е\������ csvForm �֐ݒ肷��B<br/>
	 * <br/>
	 * ������Ȃ��ꍇ�A�V���ɒǉ�����S����Џ��� Map �ɊY������S����Џ�� �����݂���̂����`�F�b
	 * �N����B�@���݂���ꍇ�A���̓S�����CD csvForm �֐ݒ肷��B<br/>
	 * <br/>
	 * ����ł�������Ȃ��ꍇ�A�V���ȓS�����CD ���̔Ԃ��� csvForm �֐ݒ肷��B
	 * <br/>
	 * @param csvForm ���H���� CSV �̒l��ݒ肷�� Form
	 * @param rrMstMap �����̓S����Џ��� Map
	 * @param addRrMstMap �V���ɒǉ�����S����Џ��� Map
	 * @param allRrCdSet �S�S�����CD �i���ꂩ��ǉ�����\��̃R�[�h���܂ށj
	 * @param maxRrCd �ő�S�����CD
	 * @param �ő�S�����CD
	 */
	protected String confRrCdAndRrSortOrder(StationCsvForm csvForm,
										  Map<String, RrMst> rrMstMap,
										  Map<String, RrMst> addRrMstMap,
										  Set<String> allRrCdSet,
										  String maxRrCd){

		// �S����Џ��̓S�����CD�A�\������ݒ�
		if (rrMstMap.containsKey(csvForm.getRrName())) {
			// �����̓S����Ѓ}�X�^�ɓ��ꖼ�̂�����΁A�����̒l���g�p����B
			RrMst rrMst = rrMstMap.get(csvForm.getRrName());
			csvForm.setRrCd(rrMst.getRrCd());
			csvForm.setRrSortOrder(rrMst.getSortOrder());
			return maxRrCd;
		}

		if (addRrMstMap.containsKey(csvForm.getRrName())) {
			// �ǉ��\��f�[�^�ɒǉ��ς݂ł���΂������D�悷��
			RrMst rrMst = addRrMstMap.get(csvForm.getRrName());
			csvForm.setRrCd(rrMst.getRrCd());
			csvForm.setRrSortOrder(rrMst.getSortOrder());
			return maxRrCd;
		}

		// �V�K�̏ꍇ
		String rrCd = "";								// �S�����CD
		Integer intMaxrrCd = Integer.valueOf(maxRrCd);	// ���l�������ő�S�����CD


		// �d�����Ȃ��R�[�h���쐬
		intMaxrrCd++;
		if(intMaxrrCd <= 9999){
			// �S�����CD �̍ő�l�ɂP���Z�����l���ő�l�𒴂��Ă��Ȃ���΁A���̒l���[���l
			// �ҏW���ēS�����CD �Ƃ���B
			rrCd = makeDigit(intMaxrrCd, 4, '0');		// ��0�l��4��
			if(allRrCdSet.contains(rrCd)){				// �������݂��Ă����烊�Z�b�g
				rrCd = "";
			}
		}

		// �̔Ԃł��Ȃ������ꍇ�i�ő�l�ɒB���Ă����ꍇ�A�܂��́A���Ɏg�p����Ă����ꍇ�B�j
		if(rrCd.equals("")){

			// 0001�`9999�܂ŋ󂢂Ă���CD��T��
			for(intMaxrrCd=1; intMaxrrCd<=9999; intMaxrrCd++){
				rrCd = makeDigit(intMaxrrCd, 4, '0');	// ��0�l��4��
				if(!allRrCdSet.contains(rrCd)){
					break;
				}
				rrCd = "";
			}
		}

		// ����ł��̔Ԃł��Ȃ��ꍇ�A�ő�S�����CD �Ƃ��� null �𕜋A����B
		// �i�S����ЃR�[�h �I�[�o�[�t���[�j
		if(rrCd.equals("")) return null;

		// �̔Ԃ����R�[�h�� Form �ɐݒ肷��B
		// �V�K�o�^���̓\�[�g���� null
		csvForm.setRrCd(rrCd);
		csvForm.setRrSortOrder(null);
		
		// �S�S�����CD �� set �I�u�W�F�N�g�ɁA�ǉ��ΏۂƂȂ�S�����CD ��ǉ�����B
		allRrCdSet.add(rrCd);

		return rrCd;

	}
	

	
	/**
	 * �����H���}�X�^�����݂���ꍇ�A�����̕\������ Form �֐ݒ肷��B<br/>
	 * <br/>
	 * @param routeOrderMap �����H���}�X�^�ɓo�^����Ă���\����
	 * @param csvForm CSV �f�[�^���i�[���� Form 
	 */
	protected void confRouteSortOrder(Map<String, Integer> routeOrderMap, StationCsvForm csvForm){

		// �����H���}�X�^�����݂���ꍇ�A�H���}�X�^�̕\������ Form �֐ݒ肷��B
		if(routeOrderMap.containsKey(csvForm.getRouteCd())){
			csvForm.setRouteSortOrder(routeOrderMap.get(csvForm.getRouteCd()));
		}

	}

	
	
	/**
	 * �S����Џ��ɒǉ����ׂ��f�[�^�����`�F�b�N���A�ǉ��Ώۂ̏ꍇ�� addRrMstMap ��
	 * Form �̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param addRrMstMap �ǉ��ΏۂƂȂ�S����Џ���ݒ肷�� Map
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 */
	protected void addRrMstData(Map<String, RrMst> addRrMstMap,	StationCsvForm csvForm,	String editUserId, Date currentDate){

		// CSV �ɂ͓S�����CD �����݂��Ȃ��̂ŁA�S����Ж��ő��݂��邩���`�F�b�N����B
		// �ǉ��\�� Map �ɓS����Ж������݂��Ȃ��ꍇ�A�S����Џ���ǉ�����B
		if (!addRrMstMap.containsKey(csvForm.getRrName())) {
		
			RrMst rrMst = (RrMst) this.valueObjectFactory.getValueObject("RrMst");
			
			rrMst.setRrCd(csvForm.getRrCd());
			rrMst.setRrName(csvForm.getRrName());
			rrMst.setInsDate(currentDate);
			rrMst.setInsUserId(editUserId);
			rrMst.setUpdDate(currentDate);
			rrMst.setUpdUserId(editUserId);
			rrMst.setSortOrder(csvForm.getRrSortOrder());
			addRrMstMap.put(csvForm.getRrName(), rrMst);
		}
	}
	
	
	
	/**
	 * �w�E�H���Ή��\�ɒǉ����ׂ��f�[�^�����`�F�b�N���A�ǉ��Ώۂ̏ꍇ�� addStationRouteInfoMap ��
	 * Form �̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param addStationRouteInfoMap �ǉ��ΏۂƂȂ�w�E�H���Ή��\��ݒ肷�� Map
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 */
	protected void addStationRouteData(Map<String, StationRouteInfo> addStationRouteInfoMap,
									   StationCsvForm csvForm,
									   String editUserId,
									   Date currentDate){

		// �w�E�H���Ή��\
		// �H��CD + �wCD ���L�[�Ƃ��āA�w�E�H���Ή��\�̒ǉ��Ώ� Map �ɑ��݂��邩���`�F�b�N����B
		// ���݂��Ȃ��ꍇ�� Map �ɒǉ�����B
		if(!addStationRouteInfoMap.containsKey(csvForm.getRouteCd() + csvForm.getStationCd())){
			StationRouteInfo stationRouteInfo = (StationRouteInfo) this.valueObjectFactory.getValueObject("StationRouteInfo");
			stationRouteInfo.setRouteCd(csvForm.getRouteCd());
			stationRouteInfo.setStationCd(csvForm.getStationCd());
			try {
				stationRouteInfo.setSortOrder(new Integer(csvForm.getStationRouteDispOrder()));
			} catch (Exception e) {
				// �t�H�[�}�b�g�`�F�b�N�ς݂Ȃ̂ł�����ʂ邱�Ƃ͂Ȃ��͂�
				log.warn("station route order is not numeric.");
			}

			// �w�E�H���Ή��\�̒ǉ��Ώ� Map �ɒǉ�����B
			addStationRouteInfoMap.put(csvForm.getRouteCd() + csvForm.getStationCd(), stationRouteInfo);
		}
	}



	/**
	 * �H���}�X�^�ɒǉ����ׂ��f�[�^�����`�F�b�N���A�ǉ��Ώۂ̏ꍇ�� addRouteMstMap ��
	 * Form �̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param addRouteMstMap �ǉ��ΏۂƂȂ�H���}�X�^��ݒ肷�� Map
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 */
	protected void addRouteMstData(Map<String, RouteMst> addRouteMstMap,
			   					   StationCsvForm csvForm,
			   					   String editUserId,
			   					   Date currentDate){

		// �H���}�X�^
		// ���łɓo�^�\��ƂȂ��Ă���R�[�h�ȊO��ǉ�����(�揟��)
		if (!addRouteMstMap.containsKey(csvForm.getRouteCd())) {

			RouteMst routeMst = (RouteMst) this.valueObjectFactory.getValueObject("RouteMst");
			routeMst.setRouteCd(csvForm.getRouteCd());
			routeMst.setRouteName(csvForm.getRouteName());
			routeMst.setRouteNameFull(csvForm.getRouteNameFull());
			routeMst.setRouteNameRr(csvForm.getRouteNameRr());
			routeMst.setRrCd(csvForm.getRrCd());
			routeMst.setInsDate(currentDate);
			routeMst.setInsUserId(editUserId);
			routeMst.setUpdDate(currentDate);
			routeMst.setUpdUserId(editUserId);
			routeMst.setSortOrder(csvForm.getRrSortOrder());
			addRouteMstMap.put(csvForm.getRouteCd(), routeMst);
		}
	}

	

	/**
	 * �w�}�X�^�ɒǉ����ׂ��f�[�^�����`�F�b�N���A�ǉ��Ώۂ̏ꍇ�� addStationMstMap ��
	 * Form �̒l��ݒ肷��B<br/>
	 * <br/>
	 * @param addStationMstMap �ǉ��ΏۂƂȂ�w�}�X�^��ݒ肷�� Map
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 */
	protected void addStationMstData(Map<String, StationMst> addStationMstMap,
			   						 StationCsvForm csvForm,
			   						 String editUserId,
			   						 Date currentDate){

		// �w���}�X�^
		// ���łɓo�^�\��ƂȂ��Ă���R�[�h�ȊO��ǉ�����(�揟��)
		if (!addStationMstMap.containsKey(csvForm.getStationCd())) {
			StationMst stationMst = (StationMst) this.valueObjectFactory.getValueObject("StationMst");
			stationMst.setStationCd(csvForm.getStationCd());
			stationMst.setStationName(splitKakko(csvForm.getStationNameFull()));
			stationMst.setStationNameFull(csvForm.getStationNameFull());
			stationMst.setPrefCd(csvForm.getPrefCd());
			stationMst.setInsDate(currentDate);
			stationMst.setInsUserId(editUserId);
			stationMst.setUpdDate(currentDate);
			stationMst.setUpdUserId(editUserId);
			addStationMstMap.put(csvForm.getStationCd(), stationMst);
		}
	}
	

	
	/**
	 * �����������A�J�b�R�ň͂܂ꂽ��������菜�������ʂ𕜋A����B<br/>
	 * <br/>
	 * @param str �ҏW�Ώە����� 
	 * @return �ҏW���ꂽ������
	 */
	private String splitKakko(String str) {
		int startIndex, endIndex;
		
		startIndex = str.indexOf("(");
		endIndex = str.indexOf(")");
		if (startIndex == -1) {
			return str;
		}
		
		if (endIndex == -1) {
			return str.substring(0, startIndex);
		}
		
		StringBuffer sb = new StringBuffer(str);
		sb.delete(startIndex, endIndex + 1);
		str = new String(sb);
		sb.setLength(0);
		return str;
	}


	
	/**
	 * �w�肳�ꂽ���l���Adigit �̌����ɂȂ�l�ɁAappendix�@�Ɏw�肳�ꂽ�l�ō��l�����l�𕜋A����B<br/>
	 * <br/>
	 * @param number �ҏW�Ώۂ̐��l
	 * @param digit �ҏW��̌���
	 * @param appendix ���l���镶��
	 * @return ���H���ꂽ������
	 */
	private String makeDigit(int number, int digit, char appendix) {
		String result = Integer.toString(number);
		
		for (int i = result.length(); i < digit; i++) {
			result = appendix + result;
		}
		return result;
	}
	
}
