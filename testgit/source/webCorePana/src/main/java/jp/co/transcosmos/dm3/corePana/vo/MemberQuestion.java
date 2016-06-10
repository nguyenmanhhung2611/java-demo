package jp.co.transcosmos.dm3.corePana.vo;

/**
 * マイページアンケート情報.
 * <p>
 * クラス名、および、フィールド名はＤＢテーブル名、列名にマッピングされる。
 * <p>
 * <pre>
 * 担当者        修正日        修正内容
 * ------------ ----------- -----------------------------------------------------
 * Trans        2015.03.10     新規作成
 * </pre>
 * <p>
 * 注意事項<br/>
 * 個別カスタマイズで継承される可能性があるので、直接インスタンスを生成しない事。<br/>
 * インスタンスを取得する場合は、ValueObjectFactory から取得する事。<br/>
 *
 */
public class MemberQuestion {

    /** ユーザーID */
    private String userId;
    /** アンケート番号 */
    private String categoryNo;
    /** 選択質問CD */
    private String questionId;
    /** その他回答入力 */
    private String etcAnswer;

    /**
     * ユーザーID を取得する。<br/>
     * <br/>
     * @return ユーザーID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザーID を設定する。<br/>
     * <br/>
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * アンケート番号 を取得する。<br/>
     * <br/>
     * @return アンケート番号
     */
    public String getCategoryNo() {
        return categoryNo;
    }
    
    /**
     * アンケート番号 を設定する。<br/>
     * <br/>
     * @param categoryNo
     */
    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }
    
    /**
     * 選択質問CD を取得する。<br/>
     * <br/>
     * @return 選択質問CD
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 選択質問CD を設定する。<br/>
     * <br/>
     * @param questionId
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * その他回答入力 を取得する。<br/>
     * <br/>
     * @return その他回答入力
     */
    public String getEtcAnswer() {
        return etcAnswer;
    }

    /**
     * その他回答入力 を設定する。<br/>
     * <br/>
     * @param etcAnswer
     */
    public void setEtcAnswer(String etcAnswer) {
        this.etcAnswer = etcAnswer;
    }
}
