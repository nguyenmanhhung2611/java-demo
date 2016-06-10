package jp.co.transcosmos.dm3.core.model.news.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.News;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.NullOrEmptyCheckValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * ���m�点�����e�i���X�̓��̓p�����[�^����p�t�H�[��
 * 
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * @author hiennt
 */
public class NewsForm implements Validateable {

	private static final Log log = LogFactory.getLog(NewsForm.class);

	/** command �p�����[�^ */
	private String command;
	private String newsId;
	private String newsTitle;
	private String newsContent;
	private String insDate;
	private String insUserId;
	private String updDate;
	private String updUserId;
	private boolean delFlg;

	private String userId;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getInsUserId() {
		return insUserId;
	}

	public void setInsUserId(String insUserId) {
		this.insUserId = insUserId;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public boolean isDelFlg() {
		return delFlg;
	}

	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}

	/** �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B */
	protected LengthValidationUtils lengthUtils;

	/** ���ʃR�[�h�ϊ����� */
	private CodeLookupManager codeLookupManager;

	/**
	 * �f�t�H���g�R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 */
	protected NewsForm() {
		super();
	}

	/**
	 * �R���X�g���N�^�[<br/>
	 * Factory �ȊO����̃C���X�^���X�����𐧌�����ׁA�R���X�g���N�^�[���B������B<br/>
	 * <br/>
	 * 
	 * @param lengthUtils
	 *            �����O�X�o���f�[�V�����Ŏg�p���镶���񒷂��擾���郆�[�e�B���e�B
	 * @param codeLookupManager
	 *            ���ʃR�[�h�ϊ�����
	 */
	protected NewsForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}

	/**
	 * �o���f�[�V��������<br/>
	 * ���N�G�X�g�p�����[�^�̃o���f�[�V�������s��<br/>
	 * <br/>
	 * ���� Form �N���X�̃o���f�[�V�������\�b�h�́AValidateable �C���^�[�t�F�[�X�̎����ł͖����̂ŁA �o���f�[�V�������s���̈������قȂ�̂ŁA���� Form ���܂Ƃ߂ăo���f�[�V��������ꍇ�Ȃǂ͒��� ���鎖�B<br/>
	 * <br/>
	 * 
	 * @param errors
	 *            �G���[�����i�[���郊�X�g�I�u�W�F�N�g
	 * @param mode
	 *            �������[�h ("insert" or "update")
	 * @return ���펞 true�A�G���[�� false
	 */
	public boolean validate(List<ValidationFailure> errors) {

		int startSize = errors.size();

		validKeyNewsTitle(errors);
		validKeyNewsContent(errors);

		return (startSize == errors.size());
	}

	protected void validKeyNewsTitle(List<ValidationFailure> errors) {
		// �^�C�g�����̓`�F�b�N
		ValidationChain valTitle = new ValidationChain("information.search.keyTitle", this.newsTitle);
		// �����`�F�b�N
		valTitle.addValidation(new NullOrEmptyCheckValidation());
		valTitle.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 50)));
		valTitle.validate(errors);
	}

	protected void validKeyNewsContent(List<ValidationFailure> errors) {
		// �^�C�g�����̓`�F�b�N
		ValidationChain valContent = new ValidationChain("information.search.keyTitle", this.newsContent);
		// �����`�F�b�N
		valContent.addValidation(new NullOrEmptyCheckValidation());
		valContent.addValidation(new MaxLengthValidation(this.lengthUtils.getLength("information.search.keyTitle", 200)));
		valContent.validate(errors);
	}

	/**
	 * command �p�����[�^��ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param command
	 *            command �p�����[�^
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * command �p�����[�^���擾����B<br/>
	 * <br/>
	 * 
	 * @return command �p�����[�^
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * �Ώۉ����ݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param userId
	 *            �Ώۉ��
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * �Ώۉ�����擾����B<br/>
	 * <br/>
	 * 
	 * @return �Ώۉ��
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setDefaultData(News news) {

		this.newsId = (String) news.getNewsId();
		this.newsTitle = news.getNewsTitle();
		this.newsContent = news.getNewsContent();

		SimpleDateFormat formatSEDate = new SimpleDateFormat("yyyy/MM/dd");
		if (news.getInsDate() != null) {
			this.insDate = formatSEDate.format(news.getInsDate());
		}

		if (news.getUpdDate() != null) {
			this.updDate = formatSEDate.format(news.getUpdDate());
		}
	}

	/**
	 * �����œn���ꂽ���m�点���̃o���[�I�u�W�F�N�g�Ƀt�H�[���̒l��ݒ肷��B<br/>
	 * �X�V�����ł��g�p���鎖���l�����A�^�C���X�^���v���Ɋւ��ẮA�X�V���A�X�V�҂̂ݐݒ肷��B<br/>
	 * <br/>
	 * 
	 * @param information
	 *            �l��ݒ肷�邨�m�点���̃o���[�I�u�W�F�N�g
	 * 
	 */
	public void copyToNews(News news, String editUserId) {

		if (!StringValidateUtil.isEmpty(this.newsId)) {
			news.setNewsId(this.newsId);
		}
		
		news.setNewsTitle(this.newsTitle);
		news.setNewsContent(this.newsContent);

		Date date = new Date();
		news.setUpdDate(date);

		news.setUpdUserId(editUserId);
		news.setDelFlg(true);
	}

	/**
	 * ���m�点���̎�L�[�l�ƂȂ錟�������𐶐�����B<br/>
	 * <br/>
	 * ���̃��\�b�h���ł́ADB �̕����t�B�[���h�����w�肵�Ă���R�[�h�����݂���B<br/>
	 * ���ׁ̈A�A�Ǘ����[�U�[���̃e�[�u���Ƃ��āAInformation �ȊO���g�p�����ꍇ�A���̃��\�b�h�� �I�[�o�[���C�h����K�v������B<br/>
	 * <br/>
	 * 
	 * @return ��L�[�ƂȂ錟�������I�u�W�F�N�g
	 */
	public DAOCriteria buildPkCriteria() {
		// ���������I�u�W�F�N�g���擾
		DAOCriteria criteria = new DAOCriteria();

		// ���m�点�ԍ������Ă���e�[�u���̎�L�[�̌��������𐶐�����B
		criteria.addWhereClause("newsId", this.newsId);
		return criteria;
	}

}
