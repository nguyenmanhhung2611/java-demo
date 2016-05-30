package jp.co.transcosmos.dm3.corePana.util;

import java.util.Comparator;

import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;

/**
 * <pre>
 * 物件画像情報の Comparator クラス
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Y.Zhang		2015.05.08	新規作成
 *
 *
 * </pre>
 */
public class HousingImageComparator implements Comparator<HousingImageInfo> {

	/** 処理モード (true = 枝番設定用ソート、false = 画面表示用ソート) */
	private boolean sortForDivNo = false;

	/**
	 * コンストラクター<br/>
	 * Factory 以外からのインスタンス生成を制限する為、コンストラクターを隠蔽する。<br/>
	 * <br/>
	 */
	public HousingImageComparator(boolean sortForDivNo) {
		this.sortForDivNo = sortForDivNo;
	}


	/**
	 * 物件画像情報の内容を比較する。<br/>
	 * <br/>
	 * @param image1 物件画像情報１
	 * @param image2 物件画像情報２
	 * @return -1：第一引数が小さい、0：第一引数と第二引数が等しい、1：第一引数が大きい
	 */
	@Override
	public int compare(HousingImageInfo image1, HousingImageInfo image2) {

		// 枝番設定用ソートの場合、画像タイプ、表示順、枝番の昇順でソートされる
		if (this.sortForDivNo) {
			if (Integer.parseInt(image1.getImageType()) > Integer.parseInt(image2.getImageType())) {
				return 1;

			} else if (Integer.parseInt(image1.getImageType()) == Integer.parseInt(image2.getImageType())) {

				if (image1.getSortOrder() > image2.getSortOrder()) {
					return 1;

				} else if (image1.getSortOrder() == image2.getSortOrder()) {

					if (image1.getDivNo() > image2.getDivNo()) {
						return 1;

					} else if (image1.getDivNo() == image2.getDivNo()) {
						return 0;
					} else {
						return -1;
					}

				} else {
					return -1;
				}

			} else {
				return -1;
			}
		}

		// 表示順、画像タイプ、枝番の昇順でソートされる
		if (image1.getSortOrder() > image2.getSortOrder()) {
			return 1;

		} else if (image1.getSortOrder() == image2.getSortOrder()) {

			if (Integer.parseInt(image1.getImageType()) > Integer.parseInt(image2.getImageType())) {
				return 1;

			} else if (Integer.parseInt(image1.getImageType()) == Integer.parseInt(image2.getImageType())) {

				if (image1.getDivNo() > image2.getDivNo()) {
					return 1;

				} else if (image1.getDivNo() == image2.getDivNo()) {
					return 0;
				} else {
					return -1;
				}

			} else {
				return -1;
			}

		} else {
			return -1;
		}

	}

}
