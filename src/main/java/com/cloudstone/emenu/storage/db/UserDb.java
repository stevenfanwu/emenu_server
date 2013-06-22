/**
 * @(#)UserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class UserDb extends SQLiteDb implements IUserDb {
    private static final String TABLE_USER = "user";
    
    private static enum Column {
        USER_ID("userId"), NAME("name"), PASSWORD("password"),
        TYPE("type"), REAL_NAME("realName"), COMMENT("comment");
        
        private final String str;
        private Column(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }
    
    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateUserTable();
    }
    
    private void checkCreateUserTable() throws SQLiteException {
        String colDef = new ColumnDefBuilder()
            .append(Column.USER_ID.toString(), DataType.INTEGER, "NOT NULL PRIMARY KEY ASC AUTOINCREMENT")
            .append(Column.NAME.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.PASSWORD.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.TYPE.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.REAL_NAME.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.COMMENT.toString(), DataType.TEXT, "DEFAULT ''")
            .build();
        checkCreateTable(TABLE_USER, colDef);
    }
    
    @Override
    public User getUserByName(String userName) {
        //TODO
        User user = User.newUser();
        user.setName(userName);
        user.setUserId(userName.hashCode());
        return user;
    }

    @Override
    public User addUser(User user) {
        //TODO auto incresment user id
        user.setUserId(1);
        return user;
    }
}
