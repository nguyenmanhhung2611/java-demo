package jp.co.transcosmos.dm3.corePana.model.housing.form;

import jp.co.transcosmos.dm3.core.model.feature.form.FeatureSearchForm;
import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.form.annotation.UsePagingParam;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;

import org.springframework.util.StringUtils;
/**
 * ���W�e���v���[�g���̌����p�����[�^�A����сA��ʃR���g���[���p�����[�^����p�t�H�[��.
 * <p>
 * ���������ƂȂ郊�N�G�X�g�p�����[�^�̎擾��A�c�a�����I�u�W�F�N�g�̐������s���B
 * <p>
 * <pre>
 * �S����		�C����		�C�����e
 * ------------ ----------- -----------------------------------------------------
 * guo.zhonglei		2015.04.09	�V�K�쐬
 * </pre>
 * <p>
 * ���ӎ���<br/>
 *
 */
public class PanaFeatureSearchForm extends FeatureSearchForm{

	/** �f�B�t�H���g���i�\�[�g�����j */
	public static final String SORT_DEFAULT = "4";

	/** ���i(�\�[�g����) */
	@UsePagingParam
	private String sortPriceValue;

	/** �����o�^���i�V�����j�i�\�[�g�����j */
	@UsePagingParam
	private String sortUpdDateValue;

	/** �z�N���Â����i�\�[�g�����j */
	@UsePagingParam
	private String sortBuildDateValue;

	/** �w����̋����i�\�[�g�����j */
	@UsePagingParam
	private String sortWalkTimeValue;

	/** �\�[�g���� */
	@UsePagingParam
	private String keyOrderType;

	/** ���ʃR�[�h�ϊ����� */
    private CodeLookupManager codeLookupManager;
    /** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
    protected LengthValidationUtils lengthUtils;

    /**
     * �f�t�H���g�R���X�g���N�^�[<br/>
     * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
     * <br/>
     */
    PanaFeatureSearchForm(CodeLookupManager codeLookupManager, LengthValidationUtils lengthUtils) {
        super();
        this.codeLookupManager = codeLookupManager;
        this.lengthUtils = lengthUtils;
    }

	/**
     * ���i(�\�[�g����)���擾����B<br/>
     * <br/>
     * @return sortPriceValue ���i(�\�[�g����)
     */
	public String getSortPriceValue() {
		return sortPriceValue;
	}

	/**
     * ���i(�\�[�g����)��ݒ肷��B<br/>
     * <br/>
     * @param sortPriceValue ���i(�\�[�g����)
     */
	public void setSortPriceValue(String sortPriceValue) {
		this.sortPriceValue = sortPriceValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortUpdDateValue �z�N���Â����i�\�[�g�����j
     */
	public String getSortUpdDateValue() {
		return sortUpdDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortUpdDateValue �z�N���Â����i�\�[�g�����j
     */
	public void setSortUpdDateValue(String sortUpdDateValue) {
		this.sortUpdDateValue = sortUpdDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortBuildDateValue �z�N���Â����i�\�[�g�����j
     */
	public String getSortBuildDateValue() {
		return sortBuildDateValue;
	}

	/**
     * �z�N���Â����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortBuildDateValue �z�N���Â����i�\�[�g�����j
     */
	public void setSortBuildDateValue(String sortBuildDateValue) {
		this.sortBuildDateValue = sortBuildDateValue;
	}

	/**
     * �w����̋����i�\�[�g�����j���擾����B<br/>
     * <br/>
     * @return sortWalkTimeValue �w����̋����i�\�[�g�����j
     */
	public String getSortWalkTimeValue() {
		return sortWalkTimeValue;
	}

	/**
     * �w����̋����i�\�[�g�����j��ݒ肷��B<br/>
     * <br/>
     * @param sortWalkTimeValue �w����̋����i�\�[�g�����j
     */
	public void setSortWalkTimeValue(String sortWalkTimeValue) {
		this.sortWalkTimeValue = sortWalkTimeValue;
	}

	/**
	 * �\�[�g�������擾����B<br/>
	 * <br/>
	 *
	 * @return �\�[�g����
	 */
	public String getKeyOrderType() {
		return keyOrderType;
	}

	/**
	 * �\�[�g������ݒ肷��B<br/>
	 * <br/>
	 *
	 * @param keyOrder �\�[�g����
	 */
	public void setKeyOrderType(String keyOrderType) {
		this.keyOrderType = keyOrderType;
	}

	/**
     * ���ʃR�[�h�ϊ��������擾����B<br/>
     * <br/>
     * @return codeLookupManager ���ʃR�[�h�ϊ�����
     */
	public CodeLookupManager getCodeLookupManager() {
		return codeLookupManager;
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
     * �n���ꂽ�o���[�I�u�W�F�N�g���� Form �֏����l��ݒ肷��B<br/>
     * <br/>
     * @param prefMstList List<PrefMst>�@�����������A�s���{���}�X�^�Ǘ��p�o���[�I�u�W�F�N�g
     * @param full false �̏ꍇ�A�s���{���}�X�^���X�g���̂ݐݒ肷��B�@true �̏ꍇ�͂��ׂĐݒ肵�܂��B
     *
     */
    public void setDefaultData() {

    	// �f�B�t�H���g�̃\�[�g����ݒ肷��B
		if (StringUtils.isEmpty(this.getKeyOrderType())) {
			this.setSortUpdDateValue("3");
			this.setSortPriceValue("1");
			this.setSortBuildDateValue("6");
			this.setSortWalkTimeValue("7");

			this.setKeyOrderType(SORT_DEFAULT);

		}
    }
}