package jp.co.transcosmos.dm3.corePana.vo;

/**
 * お問合せヘッダ.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10  新規作成
 * H.Mizuno		2015.03.11	住所等、連絡先フィールド追加
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class InquiryHeader extends jp.co.transcosmos.dm3.core.vo.InquiryHeader {

    // 2015.03.11 H.Mizuno 項目追加 start
    /** 問合者住所・郵便番号 */
    private String zip;
    /** 問合者住所・都道府県CD */
    private String prefCd;
    /** 問合者住所・市区町村番地 */
    private String address;
    /** 問合者住所・建物名 */
    private String addressOther;
    // 2015.03.11 H.Mizuno 項目追加 end

    /** FAX */
    private String fax;
    /** 流入元CD */
    private String refCd;

    // 2015.03.11 H.Mizuno 項目追加 start
    /**
     * 問合者住所・郵便番号を取得する。<br/>
     * <br/>
     * @return 問合者住所・郵便番号
     */
    public String getZip() {
        return zip;
    }

    /**
     * 問合者住所・郵便番号 を設定する。<br/>
     * <br/>
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 問合者住所・都道府県CD を取得する。<br/>
     * <br/>
     * @return 問合者住所・都道府県CD
     */
    public String getPrefCd() {
        return prefCd;
    }

    /**
     * 問合者住所・都道府県CD を設定する。<br/>
     * <br/>
     * @param prefCd
     */
    public void setPrefCd(String prefCd) {
        this.prefCd = prefCd;
    }

    /**
     * 問合者住所・市区町村番地 を取得する。<br/>
     * <br/>
     * @return 問合者住所・市区町村番地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 問合者住所・市区町村番地 を設定する。<br/>
     * <br/>
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 問合者住所・建物名 を取得する。<br/>
     * <br/>
     * @return 問合者住所・建物名
     */
    public String getAddressOther() {
        return addressOther;
    }

    /**
     * 問合者住所・建物名 を設定する。<br/>
     * <br/>
     * @param addressOther
     */
    public void setAddressOther(String addressOther) {
        this.addressOther = addressOther;
    }

    // 2015.03.11 H.Mizuno 項目追加 end

    /**
     * FAX を取得する。<br/>
     * <br/>
     * @return FAX
     */
    public String getFax() {
        return fax;
    }

    /**
     * FAX を設定する。<br/>
     * <br/>
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 流入元CD を取得する。<br/>
     * <br/>
     * @return 流入元CD
     */
    public String getRefCd() {
        return refCd;
    }

    /**
     * 流入元CD を設定する。<br/>
     * <br/>
     * @param refCd
     */
    public void setRefCd(String refCd) {
        this.refCd = refCd;
    }
}
