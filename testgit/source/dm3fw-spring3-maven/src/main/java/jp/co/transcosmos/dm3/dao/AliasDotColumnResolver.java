package jp.co.transcosmos.dm3.dao;

public interface AliasDotColumnResolver {
    public String lookupAliasDotColumnName(String fieldName, String fieldAlias, String thisAlias);
}
