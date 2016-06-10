package jp.co.transcosmos.dm3.corePana.model.comment.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.transcosmos.dm3.core.util.LengthValidationUtils;
import jp.co.transcosmos.dm3.core.vo.UserComment;
import jp.co.transcosmos.dm3.dao.DAOCriteria;
import jp.co.transcosmos.dm3.form.PagingListForm;
import jp.co.transcosmos.dm3.lookup.CodeLookupManager;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;
import jp.co.transcosmos.dm3.validation.AlphanumericOnlyValidation;
import jp.co.transcosmos.dm3.validation.MaxLengthValidation;
import jp.co.transcosmos.dm3.validation.ValidDateValidation;
import jp.co.transcosmos.dm3.validation.Validateable;
import jp.co.transcosmos.dm3.validation.ValidationChain;
import jp.co.transcosmos.dm3.validation.ValidationFailure;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author thoph
 *
 */
public class CommentSearchForm extends PagingListForm<UserComment> implements Validateable {
	private static final Log log = LogFactory.getLog(CommentSearchForm.class);

	
    private CodeLookupManager codeLookupManager;
    private LengthValidationUtils lengthUtils;
    
    CommentSearchForm() {
        super();
    }

	protected CommentSearchForm(LengthValidationUtils lengthUtils, CodeLookupManager codeLookupManager) {
		super();
		this.lengthUtils = lengthUtils;
		this.codeLookupManager = codeLookupManager;
	}
	
	/**
	 * news id
	 */
	private String keyNewId; 
	/**
	 * comment id
	 */
	private String keyCommentId;
	/**
	 * comment Content (search)
	 */
	private String keyCommentContent;
	/**
	 * create date from (search)
	 */
	private String keyInsDateFrom;
	/**
	 * crate date to (search)
	 */
	private String keyInsDateTo;
	/**
	 * 
	 */
	private String searchCommand;
	
	/**
	 * userid comment
	 */
	private String keyUserId;
	
	
	
	/**
	 * 
	 * @return keyUserId
	 */
	public String getKeyUserId() {
		return keyUserId;
	}
	
	/**
	 * 
	 * @param keyUserId
	 */
	public void setKeyUserId(String keyUserId) {
		this.keyUserId = keyUserId;
	}

	/**
	 * 
	 * @return keyNewsId
	 */
	public String getKeyNewId() {
		return keyNewId;
	}

	/**
	 * 
	 * @param keyNewsId
	 */
	public void setKeyNewId(String keyNewId) {
		this.keyNewId = keyNewId;
	}
	
	/**
	 * 
	 * @return keyCommentId
	 */
	public String getKeyCommentId() {
		return keyCommentId;
	}
	
	/**
	 * 
	 * @param keyCommentId
	 */
	public void setKeyCommentId(String keyCommentId) {
		this.keyCommentId = keyCommentId;
	}
	/**
	 * 
	 * @return searchCommand
	 */
	public String getSearchCommand() {
		return searchCommand;
	}
	
	/**
	 * 
	 * @param searchCommand
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}

	/**
	 * 
	 * @return keyInsDateTo
	 */
	
	public String getKeyInsDateTo() {
		return keyInsDateTo;
	}

	/**
	 * 
	 * @param keyInsDateTo
	 */
	public void setKeyInsDateTo(String keyInsDateTo) {
		this.keyInsDateTo = keyInsDateTo;
	}

	/**
	 * 
	 * @return keyInsDate
	 */
	public String getKeyInsDateFrom() {
		return keyInsDateFrom;
	}

	/**
	 * 
	 * @param keyInsDate
	 */
	public void setKeyInsDateFrom(String keyInsDateFrom) {
		this.keyInsDateFrom = keyInsDateFrom;
	}

	/**
	 * 
	 * @return keyCommentContent
	 */
	public String getKeyCommentContent() {
		return keyCommentContent;
	}

	/**
	 * 
	 * @param keyCommentContent
	 */
	public void setKeyCommentContent(String keyCommentContent) {
		this.keyCommentContent = keyCommentContent;
	}

	/**
	 * <br/>
	 * @param errors 
	 * @return have error is true orÅAno error is false
	 */
	@Override
	public boolean validate(List<ValidationFailure> errors) {
        int startSize = errors.size();
        //call valikeycontentcomment function
        valiKeyContentComment(errors);
        //all valikeyinsdate fucntion
        valiKeyInsDate(errors);
        return (startSize == errors.size());
	}
	
	/**
	 * validate content commnet when admin search
	 * @param errors
	 */
	protected void valiKeyContentComment(List<ValidationFailure> errors) {
        ValidationChain valMemberLname = new ValidationChain("comment.search.KeyContentComment",this.getKeyCommentContent());
        // 0-9 a-z input
        //valMemberLname.addValidation(new AlphanumericOnlyValidation());
        valMemberLname.validate(errors);
	}
	
	/**
	 * 
	 * @param errors
	 * validate from date to date
	 */
	protected void valiKeyInsDate(List<ValidationFailure> errors) {
		SimpleDateFormat formatterstartenddate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (!StringValidateUtil.isEmpty(this.getKeyInsDateFrom())) {
				// From Date
				ValidationChain valdateFromDate = new ValidationChain("comment.search.keyInsDateFrom",this.getKeyInsDateFrom());
				valdateFromDate.addValidation(new ValidDateValidation("yyyy-MM-dd"));
				valdateFromDate.addValidation(new MaxLengthValidation(10));
				valdateFromDate.addValidation(new jp.co.transcosmos.dm3.validation.MinLengthValidation(10));
				valdateFromDate.validate(errors);
			}
			if (!StringValidateUtil.isEmpty(this.getKeyInsDateTo())) {
				// To Date
				ValidationChain valdateToDate = new ValidationChain("comment.search.keyInsDateTo", this.getKeyInsDateTo());
				valdateToDate.addValidation(new ValidDateValidation("yyyy-MM-dd"));
				valdateToDate.addValidation(new MaxLengthValidation(10));
				valdateToDate.validate(errors);
			}
			if ((!StringValidateUtil.isEmpty(this.getKeyInsDateFrom())) && (!StringValidateUtil.isEmpty(this.getKeyInsDateTo()))) {
				Date dateInputFrom = formatterstartenddate.parse(this.getKeyInsDateFrom());
				Date dateInputTo = formatterstartenddate.parse(this.getKeyInsDateTo());
				if (CollectionUtils.isEmpty(errors)) {
					// check condition
					if (dateInputTo.compareTo(dateInputFrom) < 0) {
						ValidationFailure dateStartEnd = new ValidationFailure("dateFromToDate", "comment.search.FormToDate",null, null);
						errors.add(dateStartEnd);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To create a search condition object
	 * to use where clause in database
	 * @return criteria
	 */
	public DAOCriteria buildPkCriteria(){
		DAOCriteria criteria = new DAOCriteria();
		criteria.addWhereClause("commentId", this.keyCommentId);
		return criteria;
	}
	
	/**
	 * Date function help convert date from String type to Date type
	 * @param dateSearch
	 * @return dateconvert
	 */
	public Date convertDate(String dateSearch) {
		Date dateconvert = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = dateSearch;
			try {
				dateconvert = formatter.parse(dateInString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return dateconvert;
	}
	

	/**
	* To create a search condition object. <br/>
	* For the search object that was received by the argument, to generate the search criteria from the request parameters received
	* Within this method, there is a code that specifies the physical field name of the DB. <br/>
	* <br/>
	* @return Search condition object
	*/
	public DAOCriteria buildCriteria(){

		DAOCriteria criteria = new DAOCriteria();
		
		//if keynewsId has value
		//select table with where clause is newsid
		if (!StringValidateUtil.isEmpty(this.getKeyNewId())) {
			criteria.addWhereClause("newsId", this.getKeyNewId(), DAOCriteria.EQUALS);
		}
		
		//if keycommentcontent has value
		//select table with where clause is commetnContent
		if (!StringValidateUtil.isEmpty(this.getKeyCommentContent())) {
			criteria.addWhereClause("commentContent", "%" + this.getKeyCommentContent() + "%", DAOCriteria.LIKE);
		}
		
		//if keyInsDateform has value
		//select table with where clause is keyindateFrom
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateFrom())) {
			criteria.addWhereClause("insDate", convertDate(this.getKeyInsDateFrom()), DAOCriteria.GREATER_THAN_EQUALS);
		}
		
		//if keyIndateTo has value
		//select table with where clause is keyindateTo
		if (!StringValidateUtil.isEmpty(this.getKeyInsDateTo())) {
			// cong them 1 ngay
			Calendar cal = Calendar.getInstance();
			Date insDate = convertDate(this.getKeyInsDateTo());
			cal.setTime(insDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			criteria.addWhereClause("insDate", cal.getTime(), DAOCriteria.LESS_THAN);
		}
		
		//
		if(!StringValidateUtil.isEmpty(this.getKeyUserId())){
			criteria.addWhereClause("insUserId", "%" + this.getKeyUserId() + "%", DAOCriteria.LIKE);
		}
		return criteria;
	}

	
}