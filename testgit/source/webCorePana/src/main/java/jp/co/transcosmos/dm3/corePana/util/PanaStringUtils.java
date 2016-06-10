package jp.co.transcosmos.dm3.corePana.util;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

/**
 * <pre>
 * �����񑀍�N���X
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 * TRANS        2015/03/31  �V�K�쐬
 *
 * </pre>
 */
public class PanaStringUtils {

	/**
	 * �������HTML�G���R�[�h��������s�́�br���ɁB <br>
	 *
	 * @param target
	 *            �G���R�[�h����ΏہB
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
	 * String�^�ɕϊ��B��O���͋󕶎���Ԃ��B<br>
	 * �ΏہFint�AInteger�ADouble�ALong�ABigDecimal <br>
	 *
	 * @param target
	 *            �G���R�[�h����ΏہB
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
	 * String�^��Integer�^�ɕϊ��B���l��O����null��ԋp�B
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
	 * String�^��Long�^�ɕϊ��B���l��O����null��ԋp�B
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
	 * String�^����Double�^�ɕϊ��B��O��������Null��Ԃ��B
	 *
	 * @param target
	 *            Double�^�ւ̕ϊ������҂��镶����
	 * @return result Double�^���l
	 */
	public static Double toDouble(String target) {
		try {
			return Double.parseDouble(target);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * CSV�o�͂̂��߁A�u"�v��������ɂȂ邱�ƁB
	 *
	 * @param input
	 *            ���͒l(��abcd"efg)
	 * @return ���������l(��abcd""efg)
	 */
	public static String encodeDoubleQuotation(Object input) {
		if (null == input) {
			return null;
		}

		return String.valueOf(input).replaceAll("\"", "\"\"");
	}

	/**
	 * ���ꕄ���̂��߁A�u\�v��������ɂȂ邱�ƁB
	 *
	 * @param input
	 *            ���͒l(��abcd\efg)
	 * @return ���������l(��abcd\\efg)
	 */
	public static String encodeBackSlash(Object input) {
		if (null == input) {
			return null;
		}

		return String.valueOf(input).replaceAll("\\\\", "\\\\\\\\");
	}

	/**
	 * CSV�o�͂̂��߁Anull���u""�v�ɂȂ��āA�u"�v��������ɂȂ邱�ƁB
	 *
	 * @param input ���͒l(��abcd"efg)
	 * @return ���������l(��abcd""efg)
	 */
	public static String encodeCSVItem(Object input) {
		input = encodeNull(input);

		return encodeDoubleQuotation(input);
	}

	/**
	 * null���u""�v�ɂȂ邱�ƁB
	 *
	 * @param input ���͒l(��null)
	 * @return ���������l("")
	 */
	public static String encodeNull(Object input) {
		return encodeNull(input, "");
	}

	/**
	 * null��z��l�ɂȂ邱�ƁB
	 *
	 * @param input ���͒l(��null)
	 * @param change �z��l(��abc)
	 * @return ���������l(abc)
	 */
	public static String encodeNull(Object input, String change) {
		if (null == input) {
			return change;
		}

		return String.valueOf(input);
	}

	/**
	 * �Ώە�����̒��ɁA�S�p�������甼�p�����֕ϊ����A�o�͂���<br/>
	 * <br/>
	 * @param num�@
	 * @return �]�������Ώە�����
	 */
	public static String changeToHankakuNumber(String num) {
		String tempStr = "";
		StringBuffer newNum = new StringBuffer();

		if (StringUtils.isEmpty(num)) {
			return null;
		}

		for (int i = 0; i < num.length(); i++) {
			tempStr = num.substring(i, i+1);
			if ("�P".equals(tempStr)) {
				newNum.append("1");
			} else if ("�Q".equals(tempStr)) {
				newNum.append("2");
			} else if ("�R".equals(tempStr)) {
				newNum.append("3");
			} else if ("�S".equals(tempStr)) {
				newNum.append("4");
			} else if ("�T".equals(tempStr)) {
				newNum.append("5");
			} else if ("�U".equals(tempStr)) {
				newNum.append("6");
			} else if ("�V".equals(tempStr)) {
				newNum.append("7");
			} else if ("�W".equals(tempStr)) {
				newNum.append("8");
			} else if ("�X".equals(tempStr)) {
				newNum.append("9");
			} else if ("�O".equals(tempStr)) {
				newNum.append("0");
			} else {
				newNum.append(tempStr);
			}
		}

		return newNum.toString();

	}
}
