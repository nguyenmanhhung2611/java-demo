package jp.co.transcosmos.dm3.csv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.ReflectionUtils;
import jp.co.transcosmos.dm3.view.SimpleSpreadsheetModel;


/**
 * <pre>
 * �w�b�_�s�̏o�͂��J�X�^�}�C�Y���� CSV �o�͏���
 * URL �}�b�s���O���A�ucsvh:CSV�ݒ���v�Ŏg�p����ƁA�w�肳�ꂽ CSV�ݒ���̓��e�� CSV
 * �o�͂���B�@�܂��A�t�@�C�����ɓ��t�������g�p���鎖���\�B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.22  �V�K�쐬
 * H.Mizuno  2013.12.20  �����R�[�h��ݒ�\�ɕύX
 * H.Mizuno  2015.03.04  JoinResult �̃��X�|���X�ɑΉ��ACSV �o�͍��ڐݒ�ɑΉ�
 *
 * </pre>
*/
public class SimpleCSVModel extends SimpleSpreadsheetModel {

	// CSV �o�͂̃v���p�e�B���
	private CsvConfig csvConfig;
	

	/**
     * �R���X�g���N�^�[<br/>
     * <br/>
     * @param fileName CSV �o�̓t�@�C����
     */
	public SimpleCSVModel(CsvConfig pConfig) {
		super(pConfig.getFileName());
		this.csvConfig = pConfig;
    }

    

	/**
     * CSV �w�b�_���o�͏���<br/>
     * �W���̏����́A���f���̃v���p�e�B������ CSV �̏o�͂��s���Ă���B<br/>
     * ���̃N���X�ł́A�w�肳�ꂽ CSV ��񂩂�w�b�_�s�̏����擾���ďo�͂���B<br/>
     * �܂��A�w�b�_�s�̐ݒ肪�����ꍇ�A�w�b�_�s���o�͂��Ȃ��B<br/>
     * <br/>
     * @param rows �o�̓f�[�^
     * @param model �}�b�v��� �i���g�p�j
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
     */
	@Override
	public Object[] getHeaders(Object[] rows, Map<?, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// �Y���f�[�^�����݂��Ȃ��ꍇ�A�w�b�_���o�͂��Ȃ��B
        if ((rows == null) || (rows.length == 0)) {
        	return null;
        }

       	// CSV �̐ݒ��񂪖����ꍇ�A�w�b�_���o�͂��Ȃ��B
       	// �A���AModelAndViewRewriteSupport ���o�R����ꍇ�A��O����������̂ŁA
       	// ���̃p�^�[���͒ʏ�A���݂��Ȃ��B
       	if (this.csvConfig == null){
       		return null;
       	}

   		// �w�b�_�p�̃��X�g���ݒ肳��Ă��Ȃ��ꍇ�A�w�b�_���o�͂��Ȃ��B
       	if (this.csvConfig.getHeaderColumns() == null) {
       		return null;
       	}

  		// �w�b�_�p�̃��X�g���ݒ肳��Ă���ꍇ�A���̏����g�p���� Header ���o�͂���B
       	return this.csvConfig.headerColumns.toArray(new String[this.csvConfig.headerColumns.size()]);
	}



// 2015.03.04 H.Mizuno JoinResult �ł��o�͂ł���l�ɋ@�\���g�� start
	/**
	 * CSV �P�s���̃f�[�^��ҏW���Ď擾����B<br/>
	 * csvConfig�@�� dbColumns �v���p�e�B���ݒ肳��Ă���ꍇ�A�����Ɏw�肳�ꂽ�t�B�[���h���̒l�� CSV �o��
	 * ���ڂƂ���B<br/>
	 * �܂��AcsvConfig�@�� csvValueConverter �v���p�e�B���ݒ肳��Ă���ꍇ�A���̃N���X���g�p���� CSV �o
	 * �͒l�̕ϊ��������s���B<br/>
	 * <br/>
	 */
	@Override
    public Object[] getOneRow(Object rows[], int index, Map<?,?> model, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {

    	Object thisOne = rows[index];
        if (thisOne == null) {
            return null;
        }


    	// �o�̓J�����̐ݒ肪�ݒ肳��Ă��Ȃ��ꍇ�A���̏��������s���ĕ��A����B
        if (this.csvConfig == null ||
        		this.csvConfig.getDbColumns() == null || this.csvConfig.getDbColumns().size() == 0){
        	return super.getOneRow(rows, index, model, request, response);
        }


        // �ȉ��A�t�B�[���h���w�肵�� CSV �o�͏���


    	// �߂�l�p�̃��X�g�I�u�W�F�N�g���쐬
    	List<Object> result = new ArrayList<>();

        
    	// DB �o�͍��ڂ̐ݒ�l���擾����B
    	for (String columnName : this.csvConfig.getDbColumns()){

    		// �t�B�[���h���́AaliasName.fieldName �܂��́AfieldName �Őݒ肳���B
    		// �����A�s���I�h�ŋ�؂��Ă����ꍇ�AJoinResult �Ɣ��f���ď�������B
    		String colInfo[] = columnName.split("\\.");

    		Object value = null;
    		if (colInfo.length == 2) {
    			// alias �̎w�肪����ꍇ�AJoinResult ���� item ���擾���Ă��珈������B
    			Object valueObject = ((JoinResult)thisOne).getItems().get(colInfo[0]);

                try {
                	value = ReflectionUtils.getFieldValueByGetter(valueObject, colInfo[1]);
                } catch (Throwable err) {
                    throw new RuntimeException("Error getting field: " + columnName);
                }

    		} else {
    			// alias �̎w�肪�����ꍇ�A���ڒl���擾����B
                try {
                	value = ReflectionUtils.getFieldValueByGetter(thisOne, columnName);
                } catch (Throwable err) {
                    throw new RuntimeException("Error getting field: " + columnName);
                }
    		}

    		result.add(convertValue(columnName, value, thisOne));
    	}
        
    	return result.toArray();
	}


    
	/**
	 * CSV �o�͒l�̕ϊ�����<br/>
	 * csvConfig �� csvValueConverter �v���p�e�B�ɕϊ��������ݒ肳��Ă���ꍇ�ACSV �o�͒l
	 * �̕ϊ��������s���B<br/>
	 * <br/>
	 * @param columnName �o�̓t�B�[���h��
	 * @param value ���̃J�����̏o�͒l
	 * @param thisOne CSV �s�f�[�^ �iValue �I�u�W�F�N�g�A�܂��́AJoinResult�j
	 * @return
	 */
    private Object convertValue(String columnName, Object value, Object thisOne){

        if (this.csvConfig == null) return value;
        
        CsvValueConverter csvConv = this.csvConfig.getCsvValueConverter(); 
        if (csvConv == null) return value;

        return csvConv.convert(columnName, value, thisOne);
    }
// 2015.03.04 H.Mizuno JoinResult �ł��o�͂ł���l�ɋ@�\���g�� end



	/**
     * CSV �t�@�C�����擾����<br/>
     * �t�@�C�����ɁA###yyyyMMdd### �̗l�ɁA���t�t�H�[�}�b�g�������܂܂��ꍇ�A�w�肳�ꂽ<br/>
     * �����Œu��������������t�@�C�����Ƃ���B<br/>
     * <br/>
     * @param rows �o�̓f�[�^
     * @param model �}�b�v��� �i���g�p�j
     * @param request HTTP ���N�G�X�g
     * @param response HTTP ���X�|���X
     */
	@Override
    public String getFilename(Map<?,?> model, HttpServletRequest request, HttpServletResponse response)
    		throws Exception {

		// �t�@�C�����w�肳��Ă��Ȃ��ꍇ�Anull �𕜋A
		if (this.filename == null || this.filename.length() == 0) return null;

		// �t�H�[�}�b�g�w��̊J�n�������ꍇ�A���̂܂܃t�@�C�����𕜋A
		int start = this.filename.indexOf("###");
		if (start == -1) return this.filename;

		// �t�H�[�}�b�g�w��̊J�n�����t�������ꍇ�A�I���ʒu���擾
		// �I���ʒu�����t����Ȃ��ꍇ�A���̂܂܃t�@�C�����Ƃ��ĕ��A
		int end = this.filename.indexOf("###", start + 3);
		if (end == -1) return this.filename;
		
		// �w�肳�ꂽ�����œ��t������𐶐�����B
		String fmtStr = this.filename.substring(start + 3, end);
		SimpleDateFormat dateFmt = new SimpleDateFormat(fmtStr);
		String fmtDate = dateFmt.format(new Date());

		// �����������t�Œu���������ʂ��t�@�C�����Ƃ��ĕԂ��B
		return this.filename.replaceAll("###.*###", fmtDate);
    }

// 2013.12.20 H.Mizuno CSV �o�͕����R�[�h�̐ݒ���\�ɕύX start
	@Override
	public String getEncoding() {
		return csvConfig.getEncode();
	}
// 2013.12.20 H.Mizuno CSV �o�͕����R�[�h�̐ݒ���\�ɕύX end


// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� start
	public byte[] getBOM(String encoding) {

		if (this.csvConfig.isUseBOM()) {
			if ("UTF-8".equals(encoding.toUpperCase())){
				return new byte[]{(byte)0xef, (byte)0xbb, (byte)0xbf};
			}
		}

		return null;
	}
// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� end

}
