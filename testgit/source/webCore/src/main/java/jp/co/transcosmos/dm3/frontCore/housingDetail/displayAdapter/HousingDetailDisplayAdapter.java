package jp.co.transcosmos.dm3.frontCore.housingDetail.displayAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.transcosmos.dm3.core.displayAdapter.DisplayAdapter;
import jp.co.transcosmos.dm3.core.displayAdapter.DisplayUtils;
import jp.co.transcosmos.dm3.core.model.housing.Housing;
import jp.co.transcosmos.dm3.core.vo.AddressMst;
import jp.co.transcosmos.dm3.core.vo.BuildingDtlInfo;
import jp.co.transcosmos.dm3.core.vo.BuildingInfo;
import jp.co.transcosmos.dm3.core.vo.HousingInfo;
import jp.co.transcosmos.dm3.core.vo.PrefMst;
import jp.co.transcosmos.dm3.dao.JoinResult;
import jp.co.transcosmos.dm3.utils.StringValidateUtil;


/**
 * 物件詳細画面用 DisplayAdapter.
 * バリーオブジェクトの値を画面表示用に変換する。　ただし、エスケープ等は JSP 側で必要に応じて行う事。<br>
 * <br>
 * このクラスのメソッドは、継承元クラスの getDisplayValue() からリフレクションされて実行される。<br>
 * メソッド実行時のフィールド名に該当するメソッドが、このクラスに存在する場合は、そのメソッドが使用される。<br>
 * もし、該当するメソッドが存在しない場合は引数で渡されたバリーオブジェクトのメソッドを実行する。<br>
 * <br>
 * また、クラスに実装するメソッドは、getXXXX(T vo)、isXXXX(T vo) の書式の public メソッドであれば、
 * 委譲先バリーオブジェクトに存在しなくても実装可能である。<br>
 * <br>
 * JSP から使用する場合は、tld で呼び出しが定義されているので、webcore:display() で実行する。<br>
 * <br>
 * @author H.Mizuno
 *
 */
public class HousingDetailDisplayAdapter extends DisplayAdapter {

	/** 共通表示加工処理クラス */
	private DisplayUtils displayUtils;

	/**
	 * 共通表示加工処理クラスを設定する。<br>
	 * <br>
	 * @param displayUtils 共通表示加工処理クラス
	 */
	public void setDisplayUtils(DisplayUtils displayUtils) {
		this.displayUtils = displayUtils;
	}



	/**
	 * 新着マークの表示有無を取得する<br>
	 * 物件情報の登録日をシステム日付と比較し、７日以内なら true を復帰する。<br>
	 * <br>
	 * @param housingInfo　物件情報
	 * @return　新着の場合、true を復帰する
	 */
	public boolean isNew(HousingInfo housingInfo){
		// 物件情報のシステム日付を取得し、もし空だった場合は false を復帰する。
		Date insDate = housingInfo.getInsDate();
		if (insDate == null) return false;
		
		if ((new Date().getTime() - insDate.getTime()) <= 7 * (24 * 60 * 60 * 1000)) {
			return true;
		}
		return false;
	}

	
	
	/**
	 * 表示用の賃料/価格を取得する<br>
	 * 表示単位を万円に変換する。<br>
	 * <br>
	 * @param housingInfo 物件基本情報
	 * @return 表示用に変換された文字列
	 */
	public String getPrice(HousingInfo housingInfo){
		return this.displayUtils.toTenThousandUnits(housingInfo.getPrice());
	}
	

	
	/**
	 * 表示用に郵便番号をハイフン区切りに変換して取得する。<br>
	 * <br>
	 * @param 建物基本情報
	 * @return 表示用に変換された文字列
	 */
	public String getZip(BuildingInfo buildingInfo){
		return this.displayUtils.toDspZip(buildingInfo.getZip());
	}
	
	

	/**
	 * 表示用住所を取得する。<br>
	 * 都道府県名　＋　市区町村名　＋　町名番地　＋　建物名その他　を連結する。<br>
	 * <br>
	 * @param building 建物情報
	 * @return 表示用に変換された文字列
	 */
	public String getDisplayAddress(Housing housing) {

		StringBuilder sb = new StringBuilder(256);

		// 建物情報の検索結果となる、JoinResult を取得する。
		JoinResult buildingResult = housing.getBuilding().getBuildingInfo();

		// note
		// JoinResult は、Outer 結合で該当レコードが存在しない場合、null ではなく空のバリーオブジェクトを復帰するので、
		// 判定方法には注意する事。

		// 検索結果から、該当する都道府県マスタを取得する。
		// core の実装としては、都道府県CD は必須だが、存在しない場合も考慮しておく。
		// 都道府県マスタが取得できた場合は都道府県名を設定する。
		PrefMst prefMst = (PrefMst) buildingResult.getItems().get("prefMst");
		if (!StringValidateUtil.isEmpty(prefMst.getPrefName())){
			sb.append(prefMst.getPrefName() + " ");
		}


		// 建物基本情報を取得
		BuildingInfo buildingInfo = (BuildingInfo) buildingResult.getItems().get("buildingInfo");


		// 検索結果から、該当する市区町村マスタを取得する。
		// もし、市区町村マスタが取得できた場合、そのオブジェクトの市区町村名を使用する。
		// 市区町村合併等が発生した場合、市区町村CD に該当するデータが存在しない場合がある。
		// その場合は、建物基本情報の市区町村名を使用する。
		AddressMst addressMst = (AddressMst) buildingResult.getItems().get("addressMst");
		if (!StringValidateUtil.isEmpty(addressMst.getAddressName())){
			sb.append(addressMst.getAddressName() + " ");
		} else if (!StringValidateUtil.isEmpty(buildingInfo.getAddressName()))  {
			sb.append(buildingInfo.getAddressName() + " ");
		}

		// 建物基本情報から、町名・番地を取得する。
		if (!StringValidateUtil.isEmpty(buildingInfo.getAddressOther1())){
			sb.append(buildingInfo.getAddressOther1() + " ");
		}

		// 建物基本情報から、建物名その他を取得する。
		if (!StringValidateUtil.isEmpty(buildingInfo.getAddressOther2())){
			sb.append(buildingInfo.getAddressOther2() + " ");
		}

		return sb.toString();
	}
	
	
	
	/**
	 * 表示用の築年月を取得する。<br>
	 * 竣工年月の、年、月部分＋竣工旬＋「築」を復帰する。<br>
	 * <br>
	 * @param buildingInfo 建物基本情報
	 * @return 表示用に変換された文字列
	 */
	public String getCompDate(BuildingInfo buildingInfo){
		if (buildingInfo.getCompDate() == null) return "";
		String compDate = new SimpleDateFormat("yyyy年M月").format(buildingInfo.getCompDate());
		if (!StringValidateUtil.isEmpty(buildingInfo.getCompTenDays())){
			return compDate + buildingInfo.getCompTenDays() + "築";
		} else {
			return compDate + "築";
		}
	}



	/**
	 * 表示用に、建物面積を平方メートルから坪数に変換して取得する。<br>
	 * <br>
	 * @param buildingDtlInfo 建物詳細情報
	 * @return 表示用に変換された文字列
	 */
	public String getBuildingArea(BuildingDtlInfo buildingDtlInfo){
		return this.displayUtils.toTubo(buildingDtlInfo.getBuildingArea());
	}


	
	/**
	 * カンマ区切りで格納されている、表示用アイコンCD を List オブジェクトに変換して復帰する。<br>
	 * 物件 Model の標準実装では、物件の設備情報として登録した設備CD が表示用アイコンCD として
	 * 登録されている。<br>
	 * <br>
	 * @param housingInfo 物件情報
	 * @return アイコンCD の配列
	 */
	public String[] getIconCd(HousingInfo housingInfo){
		if (StringValidateUtil.isEmpty(housingInfo.getIconCd())) return new String[0];
		return housingInfo.getIconCd().split(",");
	}

}
