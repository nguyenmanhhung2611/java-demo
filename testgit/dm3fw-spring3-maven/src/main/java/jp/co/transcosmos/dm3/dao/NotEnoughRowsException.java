/*
 * Copyright (C) 2006 Transcosmos INC.
 * All Rights Reserved.
 */
package jp.co.transcosmos.dm3.dao;

/**
 * Thrown when the fragment result set extractor doesn't have enough rows to 
 * support the fragment requirements. Command classes should catch this exception
 * and then re-issue the query after adjusting the offsets requested to match the 
 * maxRowCount available.
 *
 * @author <a href="mailto:rick@knowleses.org">Rick Knowles</a>
 * @version $Id: NotEnoughRowsException.java,v 1.3 2007/06/13 06:32:03 rick Exp $
 */
public class NotEnoughRowsException extends RuntimeException {
	private static final long serialVersionUID = -4752310395847504688L;

	private int maxRowCount;

	public NotEnoughRowsException(String message, int maxRowCount) {
		super(message);
		this.maxRowCount = maxRowCount;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}
}
