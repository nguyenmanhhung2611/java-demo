package jp.co.transcosmos.dm3.dao;

import java.util.List;

/**
 * Allows formulas to be used in update expression. e.g.
 * formula: "###abc.def### = ###abc.def### + 1" will be translated into a lookup for the
 * field def in the alias abc, and replaced to make "def = def + 1"
 */
public class FormulaUpdateExpression implements UpdateExpression {

    private String formula;
    private List<Object> params;
        
    public FormulaUpdateExpression(String formula) {
        this.formula = formula;
    }
        
    public FormulaUpdateExpression(String formula, List<Object> params) {
        this.formula = formula;
        this.params = params;
    }
    
    public String buildSQL(String thisAlias, AliasDotColumnResolver resolver,
            List<Object> params) {
        StringBuilder out = new StringBuilder();
        
        // Check formula for ### syntax
        int start = this.formula.indexOf("###");
        int lastEnd = 0;
        while (start != -1) {
            out.append(this.formula.subSequence(lastEnd, start));
            int end = this.formula.indexOf("###", start + 3);
            if (end == -1) {
                start = -1;
            } else {
                lastEnd = end + 3;
                String mix = this.formula.substring(start + 3, end);
                int dotPos = mix.indexOf('.');
                if (dotPos == -1) {
                    out.append(resolver.lookupAliasDotColumnName(mix, null, thisAlias));
                } else {
                    out.append(resolver.lookupAliasDotColumnName(mix.substring(0, dotPos), 
                            mix.substring(dotPos + 1), thisAlias));
                }
                start = this.formula.indexOf("###", end + 3);
            }
        }
        out.append(this.formula.substring(lastEnd));
        if (this.params != null) {
            params.addAll(this.params);
        }
        return out.toString();
    }

}
