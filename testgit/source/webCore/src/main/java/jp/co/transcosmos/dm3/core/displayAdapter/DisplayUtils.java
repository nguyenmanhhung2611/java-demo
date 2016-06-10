package jp.co.transcosmos.dm3.core.displayAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * �\�������p���ʏ���.
 * �V���O���g���Ŏg�p���鎖��O��Ƃ��Ă���̂ŁA�c�h �R���e�i�ȊO����v���p�e�B�l��ύX���Ȃ����B<br>
 * <br>
 * @author H.Mizuno
 *
 */
public class DisplayUtils {

	/**
	 * ���i�𖜉~�P�ʂɕϊ�����ۂ̐؎̂ă��[�h<br>
	 * �f�t�H���g�ł͒[���͐؏グ����B<br>
	 */
	private RoundingMode priceRoundMode = RoundingMode.CEILING;

	/**
	 * ���i�𖜉~�P�ʂɕϊ�����ۂ̏����_�ȉ��̌���<br>
	 * �f�t�H���g�ł͏����_�ȉ��͕\�����Ȃ��B<br>
	 */
	private int priceRoundScale = 0;

	/**
	 * priceRoundScale �ɂ��킹���A�o�̓t�H�[�}�b�g<br>
	 * priceRoundScale �̕ύX�ɍ��킹�Đݒ肪�ύX�����B<br>
	 * <br>
	 */
	private String priceRoundFormat = ",###";
	
	/** �������[�g�����A�ؐ��ϊ����̊����萔 */
    protected static final BigDecimal tuboRatio = new BigDecimal("0.3025");
	
	
	
	/**
	 * ���i�𖜉~�P�ʂɕϊ�����ۂ̐؎̂ă��[�h��ݒ肷��B<br>
	 * <br>
	 * @param priceRoundMode �؎̂ă��[�h�i�f�t�H���g CEILING = �؏グ�j
	 */
	public void setPriceRoundMode(RoundingMode priceRoundMode) {
		this.priceRoundMode = priceRoundMode;
	}

	/**
	 * ���i�𖜉~�P�ʂɕϊ�����ۂ̏����_�ȉ��̌�����ݒ肷��B<br>
	 * <br>
	 * @param priceRoundScale �����_�ȉ��̌����B�@�f�t�H���g 0
	 */
	public void setPriceRoundScale(int priceRoundScale) {
		this.priceRoundScale = priceRoundScale;
		
		if (this.priceRoundScale <= 0){
			this.priceRoundFormat = ",###";
			return;
		}

		// �[���ȏ�̏����_�ȉ��̌������w�肳�ꂽ�ꍇ�A���̌����ɍ��킹�ăt�H�[�}�b�g��ύX
		StringBuffer buff = new StringBuffer();
		buff.append(",###.");
		for (int i=0; i<this.priceRoundScale; ++i){
			buff.append("#");
		}
		this.priceRoundFormat = buff.toString();
	}



	/**
	 * ���i�𖜉~�P�ʂɉ��H����B<br>
	 * <br>
	 * @param price ���i
	 * @return ���~�P�ʂ̉��i������
	 */
	public String toTenThousandUnits(Long price){
		
		// �ϊ����� null �̏ꍇ�A�󕶎���𕜋A����B
		if (price == null) return "";

		// �ϊ����� 0 �̏ꍇ�A�u0�~�v�𕜋A����B
		if (price.equals(0L)) return "0�~";

		
		// 10000 �Ŋ���A���~�P�ʂɕϊ�����B
		BigDecimal bd = new BigDecimal(price);
		bd = bd.divide(new BigDecimal(10000), this.priceRoundScale, this.priceRoundMode);

		return new DecimalFormat(this.priceRoundFormat).format(bd) + "���~";
	}



	/**
	 * �������[�g�����A�ؐ��ɕϊ�����B<br>
	 * �ؐ� = �������[�g�� * 0.3025 �i�����_�Q���ڂŎl�̌ܓ��j �ŎZ�o<br>
	 * <br>
	 * @param squareMeter �������[�g��
	 * @return �ؐ�������
	 */
	public String toTubo(BigDecimal squareMeter){
		if (squareMeter == null) return "";
        return "��" + squareMeter.multiply(tuboRatio).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "��";
	}

	
	
	/**
	 * �X�֔ԍ���\���p�Ƀn�C�t����؂�ɕϊ�����B<br>
	 * <br>
	 * @param zip �X�֔ԍ�
	 * @return �n�C�t����؂�̗X�֔ԍ�������
	 */
	public String toDspZip(String zip){
		if (StringValidateUtil.isEmpty(zip)) return "";
		if (zip.length() != 7) return zip;
		return zip.substring(0,3) + "-" + zip.substring(3);
	}
	
}
