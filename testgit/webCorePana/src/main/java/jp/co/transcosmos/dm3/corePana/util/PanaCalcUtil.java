package jp.co.transcosmos.dm3.corePana.util;

import java.math.BigDecimal;

/**
 * Panasonic���ʌv�ZUtil.
 *
 * <pre>
 *
 * �S����       �C����      �C�����e
 * ------------ ----------- -----------------------------------------------------
 *   Trans	  2015.03.31    �V�K�쐬
 *
 * ���ӎ���
 *
 * </pre>
 */
public class PanaCalcUtil {
    /**
     * �؂��v�Z���郁�\�b�h.<br>
     * �ؐ����ʐρi�y�n�ʐρA��L�ʐρA�����ʐρj* 0.3025�������_�Q���ڂŎl�̌ܓ�<br>
     * <br>
     * @param area
     *            �ʐ�
     * @return �v�Z�����ؐ�
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
     * �؂��v�Z���郁�\�b�h.<br>
     * �ؐ����ʐρi�y�n�ʐρA��L�ʐρA�����ʐρj���W���������_�Q���ڂŎl�̌ܓ�<br>
     * <br>
     * @param area
     *            �ʐ�
     * @param ratio
     *            �W��
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio) {
        int roundCalcTyp = BigDecimal.ROUND_HALF_UP;
        int bt = 2;

        BigDecimal tsuboNew = calcTsubo(area, ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * �؂��v�Z���郁�\�b�h.<br>
     * �ؐ����ʐρi�y�n�ʐρA��L�ʐρA�����ʐρj���W�������� �p�����[�^�Ŏl�̌ܓ�<br>
     * <br>
     * @param area
     *            �ʐ�
     * @param ratio
     *            �W��
     * @param bt
     *            ����
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio, int bt) {
        int roundCalcTyp = BigDecimal.ROUND_HALF_UP;

        BigDecimal tsuboNew = calcTsubo(area, ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * �؂��v�Z���郁�\�b�h.<br>
     * �ؐ����ʐρi�y�n�ʐρA��L�ʐρA�����ʐρj���W��<br>
     * ���� �p�����[�^�Ŏɓ��敪�p�����[�^�̎w��ɂ��ۂ�<br>
     * <br>
     * @param area
     *            �ʐ�
     * @param ratio
     *            �W��
     * @param bt
     *            ����
     * @param roundCalcTyp
     *            �ɓ��敪
     * @return
     */
    public static BigDecimal calcTsubo(BigDecimal area, BigDecimal ratio,
            int bt, int roundCalcTyp) {

        BigDecimal tsuboNew = area.divide(ratio, bt, roundCalcTyp);

        return tsuboNew;
    }

    /**
     * �؂��v�Z���郁�\�b�h.<br>
     * �ؐ����ʐρi�y�n�ʐρA��L�ʐρA�����ʐρj* �W��<br>
     * ���� �p�����[�^�Ŏɓ��敪�p�����[�^�̎w��ɂ��ۂ�<br>
     * <br>
     * @param area
     *            �ʐ�
     * @param ratio
     *            �W��
     * @param bt
     *            ����
     * @param roundCalcTyp
     *            �ɓ��敪
     * @return
     */
    public static BigDecimal calcTsubo2(BigDecimal area, BigDecimal ratio,
            int bt, int roundCalcTyp) {

        BigDecimal tsuboNew = area.multiply(ratio).setScale(bt,roundCalcTyp);

        return tsuboNew;
    }

    /**
     * �k�������v�Z���郁�\�b�h.<br>
     * �k������������80�i�����_�ȉ��؂�グ�j<br>
     *
     * @param distance
     *            ����
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
     * �k�������v�Z���郁�\�b�h.<br>
     * �k�������������W���i�����_�ȉ��؂�グ�j<br>
     *
     * @param distance
     *            ����
     * @param ratio
     *            �W��
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
     * �k�������v�Z���郁�\�b�h.<br>
     * �k�������������W���i���� �p�����[�^�Ő؂�グ�j<br>
     *
     * @param distance
     *            ����
     * @param ratio
     *            �W��
     * @param bt
     *            ����
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance,
            BigDecimal ratio, int bt) {
        int roundCalcTyp = BigDecimal.ROUND_UP;

        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }

    /**
     * �k�������v�Z���郁�\�b�h.<br>
     * �k�������������W���i���� �p�����[�^�Ŏɓ��敪 �p�����[�^�ɂ��ۂ߁j<br>
     *
     * @param distance
     *            ����
     * @param ratio
     *            �W��
     * @param bt
     *            ����
     * @param roundCalcTyp
     *            �ɓ��敪
     * @return
     */
    public static BigDecimal calcLandMarkTime(BigDecimal distance,
            BigDecimal ratio, int bt, int roundCalcTyp) {
        BigDecimal time = distance.divide(ratio, bt, roundCalcTyp);

        return time;
    }
}
