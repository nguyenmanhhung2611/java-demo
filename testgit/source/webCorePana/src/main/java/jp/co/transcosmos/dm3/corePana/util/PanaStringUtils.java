package jp.co.transcosmos.dm3.corePana.util;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

/**
 * <pre>
 * 文字列操作クラス
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * TRANS        2015/03/31  新規作成
 *
 * </pre>
 */
public class PanaStringUtils {

	/**
	 * 文字列をHTMLエンコードした後改行は＜br＞に。 <br>
	 *
	 * @param target
	 *            エンコードする対象。
	 */
	public static String encodeHtml(String target) {
		String result = "";
		HtmlEncode htmlEncode = new HtmlEncode();

		result = htmlEncode.encode(target);
		if (result != null) {
			result = result.replaceAll("\r\n", "<br>");
		} else {
			result = "";
		}

		return result;
	}

	/**
	 * String型に変換。例外時は空文字を返す。<br>
	 * 対象：int、Integer、Double、Long、BigDecimal <br>
	 *
	 * @param target
	 *            エンコードする対象。
	 */
	public static String toString(String target) {

		if (target == null) {
			return "";
		}
		try {
			return target.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String toString(int target) {
		try {
			return String.valueOf(target);
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static String toString(Integer target) {
		if (target == null) {
			return "";
		}
		try {
			return target.toString();
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static String toString(Double target) {
		if (target == null) {
			return "";
		}
		try {
			return target.toString();
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static String toString(Long target) {
		if (target == null) {
			return "";
		}
		try {
			return target.toString();
		} catch (NumberFormatException e) {
			return "";
		}
	}

	public static String toString(BigDecimal target) {
		if (target == null) {
			return "";
		}
		try {
			return target.toString();
		} catch (NumberFormatException e) {
			return "";
		}
	}


	/**
	 * String型をInteger型に変換。数値例外時はnullを返却。
	 *
	 * @param String
	 *            target
	 * @return Integer result
	 */
	public static Integer toInteger(String target) {
		try {
			return Integer.decode(target);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * String型をLong型に変換。数値例外時はnullを返却。
	 *
	 * @param String
	 *            target
	 * @return Long result
	 */
	public static Long toLong(String target) {
		try {
			return Long.parseLong(target);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * String型からDouble型に変換。例外発生時はNullを返す。
	 *
	 * @param target
	 *            Double型への変換を期待する文字列
	 * @return result Double型数値
	 */
	public static Double toDouble(String target) {
		try {
			return Double.parseDouble(target);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * CSV出力のため、「"」が一つから二つになること。
	 *
	 * @param input
	 *            入力値(例abcd"efg)
	 * @return 処理した値(例abcd""efg)
	 */
	public static String encodeDoubleQuotation(Object input) {
		if (null == input) {
			return null;
		}

		return String.valueOf(input).replaceAll("\"", "\"\"");
	}

	/**
	 * 特殊符号のため、「\」が一つから二つになること。
	 *
	 * @param input
	 *            入力値(例abcd\efg)
	 * @return 処理した値(例abcd\\efg)
	 */
	public static String encodeBackSlash(Object input) {
		if (null == input) {
			return null;
		}

		return String.valueOf(input).replaceAll("\\\\", "\\\\\\\\");
	}

	/**
	 * CSV出力のため、nullを「""」になって、「"」が一つから二つになること。
	 *
	 * @param input 入力値(例abcd"efg)
	 * @return 処理した値(例abcd""efg)
	 */
	public static String encodeCSVItem(Object input) {
		input = encodeNull(input);

		return encodeDoubleQuotation(input);
	}

	/**
	 * nullを「""」になること。
	 *
	 * @param input 入力値(例null)
	 * @return 処理した値("")
	 */
	public static String encodeNull(Object input) {
		return encodeNull(input, "");
	}

	/**
	 * nullを想定値になること。
	 *
	 * @param input 入力値(例null)
	 * @param change 想定値(例abc)
	 * @return 処理した値(abc)
	 */
	public static String encodeNull(Object input, String change) {
		if (null == input) {
			return change;
		}

		return String.valueOf(input);
	}

	/**
	 * 対象文字列の中に、全角数字から半角数字へ変換し、出力する<br/>
	 * <br/>
	 * @param num　
	 * @return 転換した対象文字列
	 */
	public static String changeToHankakuNumber(String num) {
		String tempStr = "";
		StringBuffer newNum = new StringBuffer();

		if (StringUtils.isEmpty(num)) {
			return null;
		}

		for (int i = 0; i < num.length(); i++) {
			tempStr = num.substring(i, i+1);
			if ("１".equals(tempStr)) {
				newNum.append("1");
			} else if ("２".equals(tempStr)) {
				newNum.append("2");
			} else if ("３".equals(tempStr)) {
				newNum.append("3");
			} else if ("４".equals(tempStr)) {
				newNum.append("4");
			} else if ("５".equals(tempStr)) {
				newNum.append("5");
			} else if ("６".equals(tempStr)) {
				newNum.append("6");
			} else if ("７".equals(tempStr)) {
				newNum.append("7");
			} else if ("８".equals(tempStr)) {
				newNum.append("8");
			} else if ("９".equals(tempStr)) {
				newNum.append("9");
			} else if ("０".equals(tempStr)) {
				newNum.append("0");
			} else {
				newNum.append(tempStr);
			}
		}

		return newNum.toString();

	}
}
