package jp.co.transcosmos.dm3.core.util;

import java.util.Random;

import jp.co.transcosmos.dm3.utils.StringValidateUtil;

/**
 * <pre>
 * ランダムパスワード生成処理
 * 
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * I.Shu		2015.02.04	新規作成
 * H.Mizuno		2015.03.27	DI コンテナから設定を変えられるように変更
 * 
 * 注意事項
 * パスワード生成処理は同期化しているので、このクラスの bean 定義は Singleton にする事。
 *
 * </pre>
 */
public class CreatePassword {

	/** 生成するパスワードの桁数　 */
	protected int length = 8;

	/**
	 * パスワードに含める記号文字　（例  #!;:）<br/>
	 * このプロパティが未設定の場合、ランダム生成するパスワードに記号文字は含めない。<br/>
	 */
	protected String signList = "";

	
	/** 文字種別:数字　 */
	private static final int NUM = 2;
	/** 文字種別:記号　 */
	private static final int SIGN = 3;
	/** ランダム数字範囲　 */
	private static final int NUM_TO = 10;
	/** ランダム英字範囲　 */
	private static final int CHAR_TO = 52;
	/** 小文字と大文字区分　 */
	private static final int SL = 26;
	/** 小文字from　 */
	private static final int S_FROM = 97;
	/** 大文字from　 */
	private static final int L_FROM = 65;
	/** 最大リトライ回数 */
	private static final int MAX_RETRY = 100;
	
	/** 乱数生成クラス  */
	protected static Random r = new Random();


	
	/**
	 * 生成するパスワードの桁数を設定する。<br/>
	 * 省略時は 8 桁で生成する。<br\>
	 * <br/>
	 * @param length パスワードの桁数
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * パスワードに含める記号文字を設定する。　（例  #!;:）<br/>
	 * このプロパティが未設定の場合、ランダム生成するパスワードに記号文字は含めない。 （デフォルト未設定）<br/>
	 * <br/>
	 * @param signList パスワードに含める記号文字
	 */
	public void setSignList(String signList) {
		this.signList = signList;
	}



	/**
	 * ランダムでパスワード生成する。<br/>
	 * <br/>
	 * 
	 */
	public synchronized String getPassword() {

		// もし、記号文字列を含めない場合、乱数の生成範囲を 0 ～ 2 に変更する。
		int maxRndCnt = 4;
		if (StringValidateUtil.isEmpty(this.signList)){
			maxRndCnt = 3;
		}


		// 生成したパスワードが要求事項を満たさない場合、再起コールしてリトライを行っていた。
		// 実質的には問題ないと思えるが、スタックにも影響を与えるのでループに変更した。
		// また、万が一、無限ループになるとまずいので、最大リトライ回数の閾値を設定した。
		// 閾値に達しても要件を満たすパスワードが生成できない場合は例外をスローする。
		for (int retry=0; retry < MAX_RETRY; ++retry){

			StringBuilder password = new StringBuilder(this.length);

			// 半角英字小文字ありフラグ
			boolean ｓCharFlg = false;
			// 半角英字大文字ありフラグ
			boolean lCharFlg = false;
			// 数字ありフラグ
			boolean numFlg = false;
			// 記号ありフラグ （記号文字を含めない場合は、記号ありフラグを true 固定にする。）
			boolean signFlg = false;
			if (maxRndCnt == 3) signFlg = true;


			// パスワードの桁数より、ループで生成する
			for (int i = 0; i < this.length; ++i) {

				// 0 ～ 3 （又は、2）の範囲で乱数を生成する。
				// この値によって、0、1 の場合は大小英字、2 の場合は数字、3 の場合は記号文字の
				// 生成へと振り分ける。
				int numCharSignFlg = r.nextInt(maxRndCnt);

				if (numCharSignFlg == NUM) {

					// 2: 半角数字の生成処理
					// 0 ～ 9 の乱数を生成し、その値をパスワード文字として使用する。
					password.append(r.nextInt(NUM_TO));
					// 半角数字あり
					numFlg = true;

				} else if (numCharSignFlg == SIGN) {
					
					// 3: 記号文字の生成処理
					// signList が未設定の場合、ここに流れる事はない。
					// signList の文字数範囲で乱数を生成し、その位置の文字をパスワード文字として使用する。
					password.append(signList.charAt(r.nextInt(signList.length())));
					signFlg = true;

				} else {

					// 0、1: 半角英字小文字と半角英字大文字
					// 0 ～ 51 までの乱数を生成し、26 未満の場合は英小文字として生成、それ以上の場合は
					// 英大文字として生成する。

					int temp = r.nextInt(CHAR_TO);
					// 半角英字小文字や半角英字大文字を生成する
					char x;
					// 半角英字小文字の場合
					if (temp < SL) {
						x = (char) (temp + S_FROM);
						// 半角英字小文字あり
						ｓCharFlg = true;
					} else {
						// 半角英字大文字の場合
						x = (char) ((temp % SL) + L_FROM);
						// 半角英字大文字あり
						lCharFlg = true;
					}
					password.append(x);
				}
			}
			
			// 生成したパスワードが要求事項を満たしていれば、その値を復帰する。
			if ((numFlg && ｓCharFlg && lCharFlg && signFlg)) {
				return password.toString();
			}
		}

		// 閾値に達しても要件を満たすパスワードが生成できない場合は例外をスローする。
		throw new RuntimeException("random password generate failed.");
	}

}
