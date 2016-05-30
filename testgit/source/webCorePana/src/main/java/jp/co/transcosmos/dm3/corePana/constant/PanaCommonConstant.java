package jp.co.transcosmos.dm3.corePana.constant;

/**
 * <pre>
 * 共通定数
 *
 * 担当者       修正日      修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.04.02  新規作成
 * Duong.Nguyen        2015.08.21  Add function codes of Csv Logging
 *
 * </pre>
*/
public class PanaCommonConstant {

    /** 画像閲覧権限が全員*/
    public static final String ROLE_ID_PUBLIC = "1";
    /** 画像閲覧権限が会員のみ*/
    public static final String ROLE_ID_PRIVATE = "2";

    /** メール送信フラグ 0：未送信、1：送信 */
    public static final String SEND_FLG_0 = "0";
    /** メール送信フラグ 0：未送信、1：送信 */
    public static final String SEND_FLG_1 = "1";

    /** お問合せ区分:物件問い合わせ */
    public static final String INQUIRY_TYPE_HOUSING = "00";
    /** お問合せ区分:汎用問い合わせ */
    public static final String INQUIRY_TYPE_GENERAL = "01";
    /** お問合せ区分:物件査定問い合わせ */
    public static final String INQUIRY_TYPE_ASSESSMENT = "02";

    /** 物件種別ＣＤ:マンション */
    public static final String HOUSING_KIND_CD_MANSION = "01";
    /** 物件種別ＣＤ:戸建 */
    public static final String HOUSING_KIND_CD_HOUSE = "02";
    /** 物件種別ＣＤ:土地 */
    public static final String HOUSING_KIND_CD_GROUND = "03";

    /** 入居可能時期フラグ：即時 */
    public static final String MOVEIN_TIMING = "01";

    /** お知らせ情報の公開対象区分：サイト */
    public static final String DSP_FLG_SITE = "0";
    /** お知らせ情報の公開対象区分：全本会員 */
    public static final String DSP_FLG_PUBLIC = "1";
    /** お知らせ情報の公開対象区分：個人 */
    public static final String DSP_FLG_PRIVATE = "2";

    /** 買い替えの有無:0なし*/
    public static final String REPLACEMENT_FLG_NO = "0";
    /** 買い替えの有無：1あり*/
    public static final String REPLACEMENT_FLG_YES = "1";

    /** 物件一覧の検索条件:指定無し*/
    public static final String HOUSING_LIST_WHERE_01 = "01";
    /** 物件一覧の検索条件:7日以内*/
    public static final String HOUSING_LIST_WHERE_02 = "02";
    /** 物件一覧の検索条件:7日以上14日以内*/
    public static final String HOUSING_LIST_WHERE_03 = "03";
    /** 物件一覧の検索条件:15日以上*/
    public static final String HOUSING_LIST_WHERE_04 = "04";

    /** 住宅診断実施有無:有*/
    public static final String HOUSING_INSPECTION_EXIST = "01";

    /** 物件非公開フラグ0 : 公開、1 : 非公開*/
    public static final String HIDDEN_FLG_PUBLIC ="0";
    /** 物件非公開フラグ0 : 公開、1 : 非公開*/
    public static final String HIDDEN_FLG_PRIVATE ="1";

    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_00 ="00";
    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_01 ="01";
    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_02 ="02";
    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_03 ="03";
    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_04 ="04";
    /** 物件画像タイプ'00:間取図／01:外観／02:動画／03:内観／04周辺／99:その他*/
    public static final String IMAGE_TYPE_99 ="99";

    /** 損保有無*1:加入可／2:リフォーム後加入可*/
    public static final String INSUR_EXIST_1 = "1";
     /** 損保有無*1:加入可／2:リフォーム後加入可*/
    public static final String INSUR_EXIST_2 = "2";

    /** お問合せ内容種別CD*004:セミナー・イベントに関して*/
    public static final String INQUIRY_DTL_TYPE_SEMINAR = "004";

    /** 共通アンケート番号:001*/
    public static final String COMMON_CATEGORY_NO = "001";

    /** ロックフラグ:0 : 通常、1 : ロック中*/
    public static final String LOCK_FLG_0 = "0";

    /** ロックフラグ:0 : 通常、1 : ロック中*/
    public static final String LOCK_FLG_1 = "1";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_000 = "000";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_001 = "001";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_002 = "002";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_003 = "003";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_004 = "004";

    /** ロックフラグ:000:全て、001:WEB、002:クラブパナソニック、003:共済会、004:パナソニックショップ、005:セミナー・イベント*/
    public static final String ENTRY_ROUTE_005 = "005";

    /** 画像サイズ **/
    public static final int maxFileSize = 2*1024*1024;
    /** 画像サイズ **/
    public static final int updFileSize = 10*1024*1024;
    
    /** 01 = 問合せ一覧CSV出力, 02 = 物件一覧CSV出力, 03 = 会員一覧CSV出力, 04 = お知らせ一覧CSV出力, 05 = 管理ユーザー一覧CSV出力 **/ 
    public static final String ADMIN_LOG_FC_INQUIRY_LIST = "01";
    /** 01 = 問合せ一覧CSV出力, 02 = 物件一覧CSV出力, 03 = 会員一覧CSV出力, 04 = お知らせ一覧CSV出力, 05 = 管理ユーザー一覧CSV出力 **/
    public static final String ADMIN_LOG_FC_HOUSING_LIST = "02";
    /** 01 = 問合せ一覧CSV出力, 02 = 物件一覧CSV出力, 03 = 会員一覧CSV出力, 04 = お知らせ一覧CSV出力, 05 = 管理ユーザー一覧CSV出力 **/
    public static final String ADMIN_LOG_FC_MEMBER_LIST = "03";
    /** 01 = 問合せ一覧CSV出力, 02 = 物件一覧CSV出力, 03 = 会員一覧CSV出力, 04 = お知らせ一覧CSV出力, 05 = 管理ユーザー一覧CSV出力 **/
    public static final String ADMIN_LOG_FC_INFO_LIST = "04";
    /** 01 = 問合せ一覧CSV出力, 02 = 物件一覧CSV出力, 03 = 会員一覧CSV出力, 04 = お知らせ一覧CSV出力, 05 = 管理ユーザー一覧CSV出力 **/
    public static final String ADMIN_LOG_FC_ADMIN_USER_LIST = "05";
}
