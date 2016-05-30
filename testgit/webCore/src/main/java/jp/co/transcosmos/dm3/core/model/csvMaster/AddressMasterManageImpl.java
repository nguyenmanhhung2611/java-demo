package jp.co.transcosmos.dm3.core.model.csvMaster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.transcosmos.dm3.core.constant.CommonParameters;
import jp.co.transcosmos.dm3.core.model.CsvMasterManage;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.AddressCsvForm;
import jp.co.transcosmos.dm3.core.model.csvMaster.form.CsvUploadFormFactory;
import jp.co.transcosmos.dm3.core.util.CsvStreamReader;
import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.core.vo.ZipMst;
import jp.co.transcosmos.dm3.dao.DAO;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.transaction.RequestScopeDataSource;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.NotExistsInCriteriaValidation;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

public class AddressMasterManageImpl implements CsvMasterManage {

	private static final Log log = LogFactory.getLog(AddressMasterManageImpl.class);

	/** VO �̃C���X�^���X�𐶐�����ꍇ�̃t�@�N�g���[ */
	protected ValueObjectFactory valueObjectFactory;

	/** �s���{���}�X�^ DAO */
	protected DAO<PrefMst> prefMstDAO;

	/** �s�撬���}�X�^ DAO */
	protected DAO<AddressMst> addressMstDAO;
	
	/** �X�֔ԍ��}�X�^ DAO */
	protected DAO<ZipMst> zipMstDAO;

	/** �X�֔ԍ�CSV �̉��H�����l���i�[���� Form �𐶐����� Factory */
	protected CsvUploadFormFactory formFactory;

	/** CSV �G���R�[�h������ */
	protected String csvEncode = "ms932";

	/** ���ʃp�����[�^�[�I�u�W�F�N�g */
	protected CommonParameters commonParameters;

	/** ���ʃR�[�h�ϊ����� */
	protected CodeLookupManager codeLookupManager;
	
	/**
	 * RequestScopeDataSource �� Bean ID ��<br/>
	 * �蓮�Ńg�����U�N�V�����𐧌䂷��ꍇ�A���̃v���p�e�B�ɐݒ肳��Ă��� Bean ID �ŏ�������B<br/>
	 * �ʏ�́ACommonsParameter �ɐݒ肳��Ă���l���g�p���邪�A���̃v���p�e�B�ɒl���ݒ�
	 * ����Ă���ꍇ�A������̒l��D�悷��B<br/>
	 */
	protected String requestScopeDataSourceId = null;

	

	/**
	 * �o���[�I�u�W�F�N�g�̃C���X�^���X�𐶐�����t�@�N�g���[��ݒ肷��B<br/>
	 * <br/>
	 * @param valueObjectFactory �o���[�I�u�W�F�N�g�̃t�@�N�g���[
	 */
	public void setValueObjectFactory(ValueObjectFactory valueObjectFactory) {
		this.valueObjectFactory = valueObjectFactory;
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
	 * �s�撬���}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param addressMstDAO �s�撬���}�X�^ DAO
	 */
	public void setAddressMstDAO(DAO<AddressMst> addressMstDAO) {
		this.addressMstDAO = addressMstDAO;
	}

	/**
	 * �X�֔ԍ��}�X�^ DAO ��ݒ肷��B<br/>
	 * <br/>
	 * @param zipMstDAO �X�֔ԍ��}�X�^ DAO
	 */
	public void setZipMstDAO(DAO<ZipMst> zipMstDAO) {
		this.zipMstDAO = zipMstDAO;
	}

	/**
	 * �X�֔ԍ�CSV �̉��H�����l���i�[���� Form �𐶐����� Factory ��ݒ肷��B<br/>
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
	 * ���ʃp�����[�^�[�I�u�W�F�N�g��ݒ肷��B<br/>
	 * <br/>
	 * @param commonParameters ���ʃp�����[�^�[�I�u�W�F�N�g
	 */
	public void setCommonParameters(CommonParameters commonParameters) {
		this.commonParameters = commonParameters;
	}

	/**
	 * ���ʃR�[�h�ϊ�������ݒ肷��B<br/>
	 * <br/>
	 * @param codeLookupManager�@���ʃR�[�h�ϊ�����
	 */
	public void setCodeLookupManager(CodeLookupManager codeLookupManager) {
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * RequestScopeDataSource �� Bean ID ����ݒ肷��B<br/>
	 * �ʏ�AcommonParameters �Őݒ肳��Ă���l���g�p����̂ŁA���̃v���p�e�B���g�p���鎖�͂Ȃ��B<br/>
	 * <br/>
	 * @param requestScopeDataSourceId Bean ID
	 */
	public void setRequestScopeDataSourceId(String requestScopeDataSourceId) {
		this.requestScopeDataSourceId = requestScopeDataSourceId;
	}



	@Override
	public boolean csvLoad(InputStream inputStream, String editUserId, List<ValidationFailure> errors)
			throws IOException {

		// �ǉ����ꂽ�s�撬��CD �� Set �I�u�W�F�N�g
		Set<String> addAddressCdSet = new HashSet<>();
		
		//�@�Z���}�X�^��S���폜����B �@�i�X�֔ԍ��}�X�^�̍폜�́A����������Ɉς˂�B�j
		DAOCriteria criteria = new DAOCriteria();
		this.addressMstDAO.deleteByFilter(criteria);		


		// ���݂̃V�X�e�����t���擾�i�c�a�X�V���̃^�C���X�^���v�Ƃ��Ďg�p�B�j
		Date currentDate = new Date();


		// �A�b�v���[�h���ꂽ CSV �t�@�C����Ǎ���
		InputStreamReader stream = new InputStreamReader(inputStream, this.csvEncode);

		try (CsvStreamReader reader = new CsvStreamReader(stream)){

			int listCnt = 0;				// �s�ԍ�
			String oldPrefCd = "";			// �P�s�O�̓s���{��CD


			reader.open();

			Map<Integer, String> csvData;	// �擾���� CSV DATA
			while ((csvData = reader.getRowData()) != null){

				++listCnt;

				// ���ڐ����������Ȃ��ꍇ
				if(csvData.size() != 15){
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.add(new ValidationFailure("csvFormat", null, null,null));
					manualRollback();
					return false;
				}


				// CSV �̒l��ݒ肵�� Form �𐶐�����B
				AddressCsvForm addressCsvForm = createAddressForm(csvData);

				// �t�H�[�}�b�g�`�F�b�N
				List<ValidationFailure> subErrors = new ArrayList<ValidationFailure>();
				addressCsvForm.validate(subErrors);
				
				// PrefCd ���擾�ł��āA���A�s���{��CD ���ς�����ꍇ�A�s���{��CD �̃o���f�[�V�������s���B
				// �s�撬���}�X�^�̃f�[�^�ʂ������̂ŁA���ׂ��y������ׁA�Œ���̃`�F�b�N�ɂ���B
				String prefCd = addressCsvForm.getPrefCd();
				if (!StringValidateUtil.isEmpty(prefCd) && !oldPrefCd.equals(prefCd)){

					oldPrefCd = prefCd;

					// �s���{��CD �����݂��邩�̃o���f�[�V���������s
					DAOCriteria prefCri = new DAOCriteria();
					prefCri.addWhereClause("prefCd", prefCd, DAOCriteria.EQUALS, false);

					ValidationChain valPrefCd = new ValidationChain("stationCsv.prefCd", prefCd);
					valPrefCd.addValidation(new NotExistsInCriteriaValidation(this.prefMstDAO, prefCri));
					valPrefCd.validate(subErrors);

				}

				// �G���[���������ꍇ�A�����𒆒f����B
				if (subErrors.size() > 0) {
					errors.add(new ValidationFailure("csvListCnt", String.valueOf(listCnt), null,null));
					errors.addAll(subErrors);
					manualRollback();
					return false;
				}			


				// CSV �́u�X�V�̕\���v�i���{�X�ւ��񋟂��� CSV ��14�Ԗڂ̃t�B�[���h�̒l�j���Q�̏ꍇ�A
				// �u�p�~�f�[�^�v���Ӗ�����B
				// �����ȊO�ł��̗l�ȃf�[�^���܂܂��Ƃ͎v���Ȃ����A�ꉞ�`�F�b�N���Ă����B
				if ("2".equals(addressCsvForm.getUpdFlg())){
					log.warn("zip csv 12 column value is 2. skipped.");
					continue;
				}


				// ���o�^�̎s�撬��CD �̏ꍇ�A�V�K�ǉ�����B
				if (!addAddressCdSet.contains(addressCsvForm.getAddressCd())){

					// �G���A�����ł̏��O�ݒ肪����Ă���s�撬��CD �����`�F�b�N����B
					// �������O�ݒ肳��Ă���ꍇ�́A�s�撬���}�X�^�� area_not_dsp �� 1 ��ݒ肷��B
					// ���̃t���O���Z�b�g���ꂽ�s�撬�����́A�G���A�����ł͕\���ΏۊO�ƂȂ�B
					isException(addressCsvForm);

					// ��x�o�^�����s�撬��CD �́ASet �I�u�W�F�N�g�ɑޔ�����B
					addAddressCdSet.add(addressCsvForm.getAddressCd());

					AddressMst addressMst = createAddressMst(addressCsvForm, editUserId, currentDate);
					this.addressMstDAO.insert(new AddressMst[]{addressMst});
				}

				// �X�֔ԍ��}�X�^��V�K�ǉ�����B
				ZipMst zipMst = createZipMst(addressCsvForm, editUserId, currentDate);
				try {
					this.zipMstDAO.insert(new ZipMst[]{zipMst});

				} catch (DuplicateKeyException e){
					// �X�֔ԍ� CSV �̗X�֔ԍ��́A�ꕔ�̓���Ȕԍ��̏ꍇ�A�d���������R�[�h�����݂���B
					// ���ׁ̈A��L�[�̏d���f�[�^�͂Q�ڂ𖳎�����B
					log.warn("The zip code of zip code CSV overlapped. (" + addressCsvForm.getZip() + ")");
				}


			}
		}

		// �o�^�f�[�^��1�����Ȃ��ꍇ
		if(addAddressCdSet.size()==0){
			errors.add(new ValidationFailure("csvNoData", null, null,null));
			manualRollback();
			return false;
		}

		return true;
	}


	
	/**
	 * �X�֔ԍ�CSV �̒l��ݒ肵�� AddressCsvForm �I�u�W�F�N�g��������B<br/>
	 * <br/>
	 * @param csvData CSV �f�[�^�[
	 * 
	 * @return�@Form �I�u�W�F�N�g
	 */
	protected AddressCsvForm createAddressForm(Map<Integer, String> csvData){

		// �s�撬��CD ���擾����B
		String addressCd = csvData.get(1);

		AddressCsvForm form = this.formFactory.createAddressCsvForm();

		form.setAddressCd(addressCd);						// �s�撬��CD�@�i�S���n�������c�̃R�[�h�j
		form.setAddressName(csvData.get(8));				// �s�撬����
		form.setZip(csvData.get(3));						// �X�֔ԍ�
		form.setUpdFlg(csvData.get(14));					// �X�V�̕\��

		// �s�撬��CD �Ƃ��āA�T�����̕����񂪎擾�ł����ꍇ�A�擪�Q����s���{��CD �Ƃ��Đݒ肷��B
		if (!StringValidateUtil.isEmpty(addressCd) && addressCd.length() == 5){
			form.setPrefCd(addressCd.substring(0, 2));
		}

		return form;

	}



	/**
	 * CSV �̒l��ݒ肵���s�撬���}�X�^�� Value �I�u�W�F�N�g�̃C���X�^���X���쐬����B
	 * <br/>
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 * 
	 * @return addressMst CSV �̒l���i�[���ꂽ addressMst �̃C���X�^���X
	 */
	protected AddressMst createAddressMst(AddressCsvForm csvForm, String editUserId, Date currentDate){

		AddressMst addressMst = (AddressMst) this.valueObjectFactory.getValueObject("AddressMst");
			
		addressMst.setAddressCd(csvForm.getAddressCd());			// �s�撬��CD
		addressMst.setPrefCd(csvForm.getPrefCd());					// �s���{��CD
		addressMst.setAddressName(csvForm.getAddressName());		// �s�撬����
		addressMst.setAreaNotDsp(csvForm.getAreaNotDsp());			// �G���A������\���t���O
		addressMst.setInsDate(currentDate);							// �o�^��
		addressMst.setInsUserId(editUserId);						// �o�^��
		addressMst.setUpdDate(currentDate);							// �X�V��
		addressMst.setUpdUserId(editUserId);						// �X�V��

		return addressMst;
	}

	
	
	/**
	 * CSV �̒l��ݒ肵���X�֔ԍ��}�X�^�� Value �I�u�W�F�N�g�̃C���X�^���X���쐬����B
	 * <br/>
	 * @param csvForm�@���H���ꂽ CSV �̏��
	 * @param editUserId ���O�C�����[�U�[ID�i�X�V���p�j
	 * @param currentDate ���݂̎���
	 * 
	 * @return zipMst CSV �̒l���i�[���ꂽ zipMst �̃C���X�^���X
	 */
	protected ZipMst createZipMst(AddressCsvForm csvForm, String editUserId, Date currentDate){

		ZipMst zipMst = (ZipMst) this.valueObjectFactory.getValueObject("ZipMst");

		zipMst.setZip(csvForm.getZip());
		zipMst.setPrefCd(csvForm.getPrefCd());
		zipMst.setAddressCd(csvForm.getAddressCd());

		return zipMst;
	}



	/**
	 * �G���A�������ɔ�\���ɂ���s�撬��CD �����`�F�b�N����B<br/>
	 * ������\���ݒ肳��Ă���ꍇ�ACSV �t�H�[���̃G���A������\���t���O�� 1 �i��\���j��ݒ肷��B<br/>
	 * ��\���ݒ肳��Ă��Ȃ��ꍇ�͉������Ȃ��B�@�i�v���p�e�B�̏����l�� 0 �ȈׁB�j<br/>
	 * <br/>
	 * @param csvForm ���H���ꂽ CSV �̏��
	 * @return
	 */
	public void isException(AddressCsvForm csvForm) {
		
		// �s�撬��CD �� Key �Ƃ��āA���O�ݒ肳��Ă���s�撬�������擾����B
		String addressName = this.codeLookupManager.lookupValue("addressMstException", csvForm.getAddressCd());
		
		if (!StringValidateUtil.isEmpty(addressName)){
			csvForm.setAreaNotDsp("1");
		}

	}



	/**
	 * �蓮�Ń��[���o�b�N�������s���B<br/>
	 * <br/>
	 */
	protected void manualRollback(){

		// ���ʃp�����[�^�I�u�W�F�N�g����l���擾����B
		String beanId = this.commonParameters.getRequestScopeDataSourceId();
		// �����A���̃N���X�̃v���p�e�B�ɒl���ݒ肳��Ă����ꍇ�A������̒l��D�悷��B
		if (!StringValidateUtil.isEmpty(this.requestScopeDataSourceId)){
			beanId = this.requestScopeDataSourceId;
		}

		// ���[���o�b�N����
		RequestScopeDataSource.closeCurrentTransaction(beanId, true);
	}

}
