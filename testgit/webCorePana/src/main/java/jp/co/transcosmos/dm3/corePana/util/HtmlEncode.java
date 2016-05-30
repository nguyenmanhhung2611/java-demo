package jp.co.transcosmos.dm3.corePana.util;

/**
 * Panasonic�pHTML�G���R�[�h���ʃN���X.
 *
 * <pre>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   Trans    2015.03.31    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class HtmlEncode {

    /** HTML�G���R�[�h���K�v�ȕ��� **/
    static char[] htmlChar = { '&', '"', '<', '>' };

    /** HTML�G���R�[�h���������� **/
    static String[] htmlEncStr = { "&amp;", "&quot;", "&lt;", "&gt;" };

    /**
    * HTML�G���R�[�h�����B
    *   &,",<,>�̒u��
    **/
    public String encode(String pStr) {
        if (pStr == null) {
            return (null);
        }

        // HTML�G���R�[�h����
        StringBuffer strOut = new StringBuffer(pStr);

        // �G���R�[�h���K�v�ȕ��������Ԃɏ���
        for (int i = 0; i < htmlChar.length; i++) {
            // �G���R�[�h���K�v�ȕ����̌���
            int idx = strOut.toString().indexOf(htmlChar[i]);

            while (idx != -1) {
                // �G���R�[�h���K�v�ȕ����̒u��
                strOut.setCharAt(idx, htmlEncStr[i].charAt(0));
                strOut.insert(idx + 1, htmlEncStr[i].substring(1));

                // ���̃G���R�[�h���K�v�ȕ����̌���
                idx = idx + htmlEncStr[i].length();
                idx = strOut.toString().indexOf(htmlChar[i], idx);
            }
        }
        return (strOut.toString());
    }

}
