/**
 * @(#)CreateIndexBuilder.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class CreateIndexBuilder extends SQLBuilder {

    public CreateIndexBuilder(String indexName, String tableName, Object[] columns) {
        super();
        append("CREATE UNIQUE INDEX IF NOT EXISTS " + indexName + " ON " + tableName);
        append(" (");
        boolean first = true;
        for (Object c:columns) {
            if (!first) {
                appendComma();
            }
            first = false;
            append(c);
        }
        append(")");
    }
}
