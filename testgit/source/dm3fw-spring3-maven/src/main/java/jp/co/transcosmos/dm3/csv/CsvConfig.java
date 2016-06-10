package jp.co.transcosmos.dm3.csv;

import java.util.List;


/**
 * <pre>
 * CSV �o�͐ݒ���
 * CSV �o�͎��̃w�b�_���A�t�@�C�����A��؂蕶���Ȃǂ��w�肷��B
 *
 * �S����            �C����               �C�����e
 * ----------------------------------------------------------------------------
 * H.Mizuno  2013.04.22  �V�K�쐬
 * H.Mizuno  2013.12.20  �����R�[�h��ݒ�\�ɕύX
 * H.Mizuno  2015.03.04  �o�̓t�B�[���h��ݒ�\�ɕύX
 *
 * </pre>
*/
public class CsvConfig {

	// �o�̓t�@�C�����i�g���q�����Ŏw��j
	protected String fileName;

	// CSV �w�b�_�s���
	// �w�b�_�s���o�͂��Ȃ��ꍇ�A�Ȃɂ��ݒ肵�Ȃ�
	protected List<String> headerColumns;

// 2015.03.04 H.Mizuno �o�̓t�B�[���h��ݒ肷��@�\��ǉ� start
	// �o�͑ΏۂƂȂ� DB �t�B�[���h��
	// JoinResult �̏ꍇ�Aalias.fieldName �̏����Őݒ肷��B
	// �S�t�B�[���h���o�͂���ꍇ�A�����ݒ肵�Ȃ��B
	protected List<String> dbColumns;
	
	// CSV �̒l��ϊ�����ꍇ�A�ϊ�������ݒ肷��B
	// ���ɉ����������Ȃ��ꍇ�͉����ݒ肵�Ȃ��B
	protected CsvValueConverter csvValueConverter;
// 2015.03.04 H.Mizuno �o�̓t�B�[���h��ݒ肷��@�\��ǉ� end

	// ��؂蕶���B�@�ȗ����́A�J���}�L�����g�p�����B
	protected String delimiter;

// 2013.12.20 H.Mizuno CSV �����R�[�h��ݒ�\�ɂ���ׂɐݒ��ǉ� start
	// CSV �o�͎��̕����R�[�h
	protected String encode;
// 2013.12.20 H.Mizuno CSV �����R�[�h��ݒ�\�ɂ���ׂɐݒ��ǉ� end

// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� start
	// CSV �o�͎��� BOM �o�� �iUTF-8 �� BOM ���o�͂���ꍇ�Atrue ��ݒ肷��B�j
	protected boolean useBOM = false;
// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� end


	// setter�Agetter
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getHeaderColumns() {
		return headerColumns;
	}

// 2015.03.04 H.Mizuno �o�̓t�B�[���h��ݒ肷��@�\��ǉ� start
	public List<String> getDbColumns() {
		return dbColumns;
	}
	public void setDbColumns(List<String> dbColumns) {
		this.dbColumns = dbColumns;
	}
	public CsvValueConverter getCsvValueConverter() {
		return csvValueConverter;
	}
	public void setCsvValueConverter(CsvValueConverter csvValueConverter) {
		this.csvValueConverter = csvValueConverter;
	}
// 2015.03.04 H.Mizuno �o�̓t�B�[���h��ݒ肷��@�\��ǉ� end

	public void setHeaderColumns(List<String> headerColumns) {
		this.headerColumns = headerColumns;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

// 2013.12.20 H.Mizuno CSV �����R�[�h��ݒ�\�ɂ���ׂɐݒ��ǉ� start
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
// 2013.12.20 H.Mizuno CSV �����R�[�h��ݒ�\�ɂ���ׂɐݒ��ǉ� end

// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� start
	public boolean isUseBOM() {
		return useBOM;
	}
	public void setUseBOM(boolean useBOM) {
		this.useBOM = useBOM;
	}
// 2015.05.19 H.Mizuno CSV �o�͎��� BOM �Ή� end

}
