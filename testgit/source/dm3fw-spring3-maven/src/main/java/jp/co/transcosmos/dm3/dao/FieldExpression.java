package jp.co.transcosmos.dm3.dao;


/**
 * �t�B�[���h���I�u�W�F�N�g<br/>
 * DAOCriteria ���g�p���Č��������𐶐����鎞�ɁA�o�C���h�ϐ��l�̕ς��ɂ��̃C���X�^���X��ݒ肷��ƁA
 * �Œ�t�B�[���h�ւ̏����𐶐����鎖���ł���B<br/>
 * <br/>
 * ��j<br/>
 * subCriteria.addWhereClause("column1", 20);<br/>
 * �̏ꍇ�A�ucolumn1 = ?�v�@�� SQL �Ƃ��Đ��������B<br/>
 * subCriteria.addWhereClause("column1", new FieldExpression("s", "column2"));<br/>
 * �̏ꍇ�A�ucolumn1 = s.column2�v�@�� SQL �Ƃ��Đ��������B<br/>
 * <br/>
 * @author H.Mizuno
 *
 */
public class FieldExpression {

	/** 
	 * �e�[�u���̕ʖ�<br/>
	 * �ʖ��ł͂Ȃ��e�[�u�������w�肷��ꍇ�A�L�������X�^�C���ł͂Ȃ��A���e�[�u�������w�肷�鎖�B<br/>
	 */
	private String alias;

	/** �t�B�[���h�� */
	private String fieldName;



	/**
	 * �R���X�g���N�^<br/>
	 * <br/>
	 * @param alias �e�[�u���̕ʖ�
	 * @param fieldName �t�B�[���h��
	 */
	public FieldExpression(String alias, String fieldName){
		this.alias = alias;
		this.fieldName = fieldName;
	}


	
	/**
	 * �e�[�u���̕ʖ����擾����B<br/>
	 * <br/>
	 * @return �e�[�u���̕ʖ�
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * �t�B�[���h�����擾����B<br/>
	 * <br/>
	 * @return �t�B�[���h��
	 */
	public String getFieldName() {
		return fieldName;
	}

}
