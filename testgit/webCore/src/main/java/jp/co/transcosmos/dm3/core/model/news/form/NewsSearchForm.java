package jp.co.transcosmos.dm3.core.model.news.form;

import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

/**
 * 
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 */
public class NewsSearchForm extends PagingListForm<News> implements Validateable {

	private String keyNewsId;
	private String keyNewsTitle;
	private String keyNewsContent;

	public String getKeyNewsId() {
		return keyNewsId;
	}

	public void setKeyNewsId(String keyNewsId) {
		this.keyNewsId = keyNewsId;
	}

	public String getKeyNewsTitle() {
		return keyNewsTitle;
	}

	public void setKeyNewsTitle(String keyNewsTitle) {
		this.keyNewsTitle = keyNewsTitle;
	}

	public String getKeyNewsContent() {
		return keyNewsContent;
	}

	public void setKeyNewsContent(String keyNewsContent) {
		this.keyNewsContent = keyNewsContent;
	}

	private String newsId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	private String searchCommand;
	
	protected LengthValidationUtils lengthUtils;
	

	/**
	 * ������ʂ� command���擾����B<br/>
	 * <br/>
	 * 
	 * @param searchCommand
	 *            ������ʂ� command
	 */
	public String getSearchCommand() {
		return searchCommand;
	}

	/**
	 * ������ʂ� command��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @return ������ʂ� command
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}


	protected NewsSearchForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * 
	 * @param lengthUtils
	 *            �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 */
	protected NewsSearchForm(LengthValidationUtils lengthUtils) {
		super();
		this.lengthUtils = lengthUtils;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @return ���펞 true�A�G���[�� false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
		int startSize = errors.size();

		validKeyNewsId(errors);
		validKeyNewsTitle(errors);
		validKeyNewsContent(errors);

		return (startSize == errors.size());
	}

	protected void validKeyNewsId(List<ValidationFailure> errors) {
		ValidationChain validKeyNewsId = new ValidationChain("information.search.keyInformationNo", this.keyNewsId);
		
		validKeyNewsId.addValidation(new AlphanumericOnlyValidation());
		validKeyNewsId.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyInformationNo", 20)));
		validKeyNewsId.validate(errors);
	}

	protected void validKeyNewsTitle(List<ValidationFailure> errors) {
		ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.keyNewsTitle);
		
		valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 50)));
		valTitle.validate(errors);
	}

	protected void validKeyNewsContent(List<ValidationFailure> errors) {
		ValidationChain valContent = new ValidationChain("information.search.keyTitle", this.keyNewsContent);

		valContent.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
		valContent.validate(errors);
	}

	/**
	 * ���������I�u�W�F�N�g���쐬����B<br/>
	 * PagingListForm �Ɏ�������Ă���A�y�[�W�����p�̌������������������g�����A�󂯎�������N�G�X�g �p�����[�^�ɂ�錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAInformation �ȊO���g�p�����ꍇ�A���̃��\�b�h�� �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * 
	 * @return ���������I�u�W�F�N�g
	 */
	@Override
	public DAOCriteria buildCriteria() {
		DAOCriteria criteria = super.buildCriteria();

		if (!StringValidateUtil.isEmpty(this.keyNewsTitle)) {
			criteria.addWhereClause("newsTitle", "%" + this.keyNewsTitle + "%", DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		if (!StringValidateUtil.isEmpty(this.keyNewsContent)) {
			criteria.addWhereClause("newsContent", "%" + this.keyNewsContent + "%", DAOCriteria.LIKE_CASE_INSENSITIVE);
		}

		return criteria;
	}

}
