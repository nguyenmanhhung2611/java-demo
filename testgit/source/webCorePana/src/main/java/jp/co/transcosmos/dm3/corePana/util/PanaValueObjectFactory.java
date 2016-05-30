package jp.co.transcosmos.dm3.corePana.util;

import jp.co.transcosmos.dm3.core.util.ValueObjectFactory;
import jp.co.transcosmos.dm3.core.vo.AdminLoginInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.corePana.vo.AdminLog;
import jp.co.transcosmos.dm3.corePana.vo.HousingImageInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingRequestInfo;
import jp.co.transcosmos.dm3.corePana.vo.HousingStatusInfo;
import jp.co.transcosmos.dm3.corePana.vo.Information;
import jp.co.transcosmos.dm3.corePana.vo.InquiryAssessment;
import jp.co.transcosmos.dm3.corePana.vo.InquiryGeneral;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHeader;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousing;
import jp.co.transcosmos.dm3.corePana.vo.InquiryHousingQuestion;
import jp.co.transcosmos.dm3.corePana.vo.MemberInfo;
import jp.co.transcosmos.dm3.corePana.vo.RecentlyInfo;


/**
 * バリーオブジェクトのインスタンスを取得する Factory クラス.
 * <p>
 * getInstance() か、Spring からインスタンスを取得して使用する事。<br/>
 * ※core 側とは Value オブジェクトのパスが異なるので注意する事。<br/>
 * <p>
 * <pre>
 * 担当者		修正日		修正内容
 * ------------ ----------- -----------------------------------------------------
 * H.Mizuno		2015.01.28	新規作成
 * H.Mizuno		2015.03.12	リフレクション対応
 * </pre>
 * <p>
 * 注意事項<br/>
 * Factory のインスタンスを直接生成しない事。　必ずgetInstance() で取得する事。
 * ValueObjectFactory　でインスタンス生成を行っており、Panasonic で継承しているクラスの場合、
 * 必ずこのクラスでオーバーライドする事。
 *
 */
public class PanaValueObjectFactory extends ValueObjectFactory {



	/**
	 * 指定されたクラス名（シンプル名）に該当するバリーオブジェクトのインスタンスを復帰する。<br/>
	 * <br/>
	 * バリーオブジェクトを拡張した場合、このメソッドをオーバーライドして拡張したクラスを復帰する様にカスタマイズする事。<br/>
	 * <br/>
	 * @param shortClassName　 クラス名（シンプル名）
	 *
	 * @return　バリーオブジェクトのインスタンス
	 *
	 */
	@Override
	protected Object buildValueObject(String shortClassName) {

		Object vo = null;

		if ("HousingInfo".equals(shortClassName)){
			// 物件基本情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new HousingInfo();

		} else if ("HousingStatusInfo".equals(shortClassName)) {
			// 物件ステータス情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new HousingStatusInfo();

		} else if ("HousingImageInfo".equals(shortClassName)) {
			// 物件画像情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new HousingImageInfo();

		} else if ("MemberInfo".equals(shortClassName)) {
			// 会員情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new MemberInfo();

		} else if ("PrefMst".equals(shortClassName)) {
			// 都道府県マスタ情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new PrefMst();

		} else if ("Information".equals(shortClassName)){
			// お知らせ情報の場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new Information();

		} else if ("HousingRequestInfo".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new HousingRequestInfo();

		} else if ("RecentlyInfo".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new RecentlyInfo();

		} else if ("InquiryAssessment".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new InquiryAssessment();

		} else if ("InquiryHeader".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new InquiryHeader();

		} else if ("InquiryHousing".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new InquiryHousing();

		} else if ("InquiryHousingQuestion".equals(shortClassName)){
			//物件リクエストの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new InquiryHousingQuestion();

		} else if ("InquiryGeneral".equals(shortClassName)){
			//汎用問合せの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new InquiryGeneral();

		} else if ("AdminLoginInfo".equals(shortClassName)){
			//汎用問合せの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new AdminLoginInfo();
			
		} else if ("AdminLog".equals(shortClassName)){
			//管理サイトログの場合、 Panasonic 用のオブジェクトを復帰する。
			vo = new AdminLog();

		} else {
			// 拡張されていない場合、オリジナルのオブジェクトを復帰
			return super.buildValueObject(shortClassName);

		}

		// Default 値を設定
		setDefaultValue(vo);
		return vo;

	}

}
