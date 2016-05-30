package jp.co.transcosmos.dm3.core.displayAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * 表示処理用共通処理.
 * シングルトンで使用する事を前提としているので、ＤＩ コンテナ以外からプロパティ値を変更しない事。<br>
 * <br>
 * @author H.Mizuno
 *
 */
public class DisplayUtils {

	/**
	 * 価格を万円単位に変換する際の切捨てモード<br>
	 * デフォルトでは端数は切上げられる。<br>
	 */
	private RoundingMode priceRoundMode = RoundingMode.CEILING;

	/**
	 * 価格を万円単位に変換する際の小数点以下の桁数<br>
	 * デフォルトでは小数点以下は表示しない。<br>
	 */
	private int priceRoundScale = 0;

	/**
	 * priceRoundScale にあわせた、出力フォーマット<br>
	 * priceRoundScale の変更に合わせて設定が変更される。<br>
	 * <br>
	 */
	private String priceRoundFormat = ",###";
	
	/** 平方メートルを、坪数変換時の割合定数 */
    protected static final BigDecimal tuboRatio = new BigDecimal("0.3025");
	
	
	
	/**
	 * 価格を万円単位に変換する際の切捨てモードを設定する。<br>
	 * <br>
	 * @param priceRoundMode 切捨てモード（デフォルト CEILING = 切上げ）
	 */
	public void setPriceRoundMode(RoundingMode priceRoundMode) {
		this.priceRoundMode = priceRoundMode;
	}

	/**
	 * 価格を万円単位に変換する際の小数点以下の桁数を設定する。<br>
	 * <br>
	 * @param priceRoundScale 小数点以下の桁数。　デフォルト 0
	 */
	public void setPriceRoundScale(int priceRoundScale) {
		this.priceRoundScale = priceRoundScale;
		
		if (this.priceRoundScale <= 0){
			this.priceRoundFormat = ",###";
			return;
		}

		// ゼロ以上の小数点以下の桁数が指定された場合、その桁数に合わせてフォーマットを変更
		StringBuffer buff = new StringBuffer();
		buff.append(",###.");
		for (int i=0; i<this.priceRoundScale; ++i){
			buff.append("#");
		}
		this.priceRoundFormat = buff.toString();
	}



	/**
	 * 価格を万円単位に加工する。<br>
	 * <br>
	 * @param price 価格
	 * @return 万円単位の価格文字列
	 */
	public String toTenThousandUnits(Long price){
		
		// 変換元が null の場合、空文字列を復帰する。
		if (price == null) return "";

		// 変換元が 0 の場合、「0円」を復帰する。
		if (price.equals(0L)) return "0円";

		
		// 10000 で割り、万円単位に変換する。
		BigDecimal bd = new BigDecimal(price);
		bd = bd.divide(new BigDecimal(10000), this.priceRoundScale, this.priceRoundMode);

		return new DecimalFormat(this.priceRoundFormat).format(bd) + "万円";
	}



	/**
	 * 平方メートルを、坪数に変換する。<br>
	 * 坪数 = 平方メートル * 0.3025 （小数点２桁目で四捨五入） で算出<br>
	 * <br>
	 * @param squareMeter 平方メートル
	 * @return 坪数文字列
	 */
	public String toTubo(BigDecimal squareMeter){
		if (squareMeter == null) return "";
        return "約" + squareMeter.multiply(tuboRatio).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "坪";
	}

	
	
	/**
	 * 郵便番号を表示用にハイフン区切りに変換する。<br>
	 * <br>
	 * @param zip 郵便番号
	 * @return ハイフン区切りの郵便番号文字列
	 */
	public String toDspZip(String zip){
		if (StringValidateUtil.isEmpty(zip)) return "";
		if (zip.length() != 7) return zip;
		return zip.substring(0,3) + "-" + zip.substring(3);
	}
	
}
