package jp.co.transcosmos.dm3.core.vo;

import jp.co.transcosmos.dm3.dao.annotation.DaoAlias;
import jp.co.transcosmos.dm3.dao.annotation.Function;

/**
 * object newscomment to join 2 table news and comment
 * 
 * @author nhatlv
 *
 */
public class NewsComment extends News{
	
	/** count number comment of news */
	@DaoAlias("user_comment")
	@Function(columnName="newsId", functionName=Function.FunctionName.COUNT)
	private int count;

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}


}
