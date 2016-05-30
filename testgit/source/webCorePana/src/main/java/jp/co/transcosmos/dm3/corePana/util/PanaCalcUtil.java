package jp.co.transcosmos.dm3.corePana.util;

import java.math.BigDecimal;

/**
 * Panasonic共通計算Util.
 *
 * <pre>
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 *   Trans	  2015.03.31    新規作成
 *
 * 注意事項
 *
 * </pre>
 */
public class PanaCalcUtil {
    /**
     * 坪を計算するメソッド.<br>
     * 坪数＝面積（土地面積、専有面積、建物面積）* 0.3025→小数点２桁目で四捨五入<br>
     * <br>
     * @param area
     *            面積
     * @return 計算した坪数
     */
    public static BigDecimal calcTsubo(BigDecimal area) {
        int roundCalcTyp = BigDecimal.ROUND_HALF_UP;
        int bt = 2;

//        BigDecimal ratio = new BigDecimal("3.3025");
//        BigDecimal tsuboNew = calcTsubo(area, ratio, bt, roundCalcTyp);

        BigDecimal ratio = new BigDecimal("0.3025");
        BigDecimal tsuboNew = calcTsubo2(area, ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * 坪を計算するメソッド.<br>
     * 坪数＝面積（土地面積、専有面積、建物面積）÷係数→小数点２桁目で四捨五入<br>
     * <br>
     * @param area
     *            面積
     * @param ratio
     *            係数
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio) {
        int roundCalcTyp = BigDecimal.ROUND_HALF_UP;
        int bt = 2;

        BigDecimal tsuboNew = calcTsubo(area, ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * 坪を計算するメソッド.<br>
     * 坪数＝面積（土地面積、専有面積、建物面積）÷係数→桁数 パラメータで四捨五入<br>
     * <br>
     * @param area
     *            面積
     * @param ratio
     *            係数
     * @param bt
     *            桁数
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio, int bt) {
        int roundCalcTyp = BigDecimal.ROUND_HALF_UP;

        BigDecimal tsuboNew = calcTsubo(area, ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * 坪を計算するメソッド.<br>
     * 坪数＝面積（土地面積、専有面積、建物面積）÷係数<br>
     * 桁数 パラメータで舎入区分パラメータの指定により丸め<br>
     * <br>
     * @param area
     *            面積
     * @param ratio
     *            係数
     * @param bt
     *            桁数
     * @param roundCalcTyp
     *            舎入区分
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio,
            int bt, int roundCalcTyp) {

        BigDecimal tsuboNew = area.divide(ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * 坪を計算するメソッド.<br>
     * 坪数＝面積（土地面積、専有面積、建物面積）* 係数<br>
     * 桁数 パラメータで舎入区分パラメータの指定により丸め<br>
     * <br>
     * @param area
     *            面積
     * @param ratio
     *            係数
     * @param bt
     *            桁数
     * @param roundCalcTyp
     *            舎入区分
     * @return
     */
    public static BigDecimal calcTsubo2(BigDecimal area, BigDecimal ratio,
            int bt, int roundCalcTyp) {

        BigDecimal tsuboNew = area.multiply(ratio).setScale(bt,roundCalcTyp);

        return tsuboNew;
    }

    /**
     * 徒歩分を計算するメソッド.<br>
     * 徒歩分＝距離÷80（小数点以下切り上げ）<br>
     *
     * @param distance
     *            距離
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance) {
        int roundCalcTyp = BigDecimal.ROUND_UP;
        int bt = 0;
        BigDecimal ratio = new BigDecimal("80");

        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }

    /**
     * 徒歩分を計算するメソッド.<br>
     * 徒歩分＝距離÷係数（小数点以下切り上げ）<br>
     *
     * @param distance
     *            距離
     * @param ratio
     *            係数
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance,
            BigDecimal ratio) {
        int roundCalcTyp = BigDecimal.ROUND_UP;
        int bt = 0;

        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }

    /**
     * 徒歩分を計算するメソッド.<br>
     * 徒歩分＝距離÷係数（桁数 パラメータで切り上げ）<br>
     *
     * @param distance
     *            距離
     * @param ratio
     *            係数
     * @param bt
     *            桁数
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance,
            BigDecimal ratio, int bt) {
        int roundCalcTyp = BigDecimal.ROUND_UP;

        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }

    /**
     * 徒歩分を計算するメソッド.<br>
     * 徒歩分＝距離÷係数（桁数 パラメータで舎入区分 パラメータにより丸め）<br>
     *
     * @param distance
     *            距離
     * @param ratio
     *            係数
     * @param bt
     *            桁数
     * @param roundCalcTyp
     *            舎入区分
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance,
            BigDecimal ratio, int bt, int roundCalcTyp) {
        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }
}
