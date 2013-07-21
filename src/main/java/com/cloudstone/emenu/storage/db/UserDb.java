/**
 * @(#)UserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.DeleteSqlBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;
import com.cloudstone.emenu.util.RsaUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class UserDb extends SQLiteDb implements IUserDb {
    
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
    
    @Value("${admin.password.default}")
    private String DEFAULT_ADMIN_PASSWORD;
    
    private static final String TABLE_NAME = "user";
    
    private static enum Column {
        ID("id"), NAME("name"), PASSWORD("password"),
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
    
    private static final String COL_DEF = new ColumnDefBuilder()
            .append(Column.ID.toString(), DataType.INTEGER, "NOT NULL PRIMARY KEY")
            .append(Column.NAME.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.PASSWORD.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.TYPE.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.REAL_NAME.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.COMMENT.toString(), DataType.TEXT, "DEFAULT ''")
            .build();
    
    private static final String SQL_DELETE = new DeleteSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_SELECT_BY_NAME = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.NAME.toString()).build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.ID.toString()).build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 6).build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME.toString()).appendSetValue(Column.TYPE.toString())
        .appendSetValue(Column.REAL_NAME.toString()).appendSetValue(Column.COMMENT.toString())
        .appendWhereId()
        .build();
    private static final String SQL_MODIFY_PASSWORD = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.PASSWORD.toString()).appendWhereId().build();
    
    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    @Override
    public User getByName(String userName) throws SQLiteException {
        GetByNameBinder binder = new GetByNameBinder(userName);
        User user = queryOne(SQL_SELECT_BY_NAME, binder, rowMapper);
        if (user==null && userName.equals("admin")) {
            //create a default admin user
            user = User.newSuperUser();
            user.setName("admin");
            user.setComment("默认密码admin,请及时修改");
            user.setPassword(RsaUtils.encrypt(DEFAULT_ADMIN_PASSWORD));
            user.setRealName("超级用户");
            user = add(user);
        }
        return user;
    }
    
    @Override
    public User update(User user) throws SQLiteException {
        String sql = SQL_UPDATE;
        executeSQL(sql, new UpdateBinder(user));
        return get(user.getId());
    }
    
    @Override
    public void delete(long userId) throws SQLiteException {
        executeSQL(SQL_DELETE, new IdStatementBinder(userId));
    }
    
    @Override
    public boolean modifyPassword(long userId, String password)
            throws SQLiteException {
        executeSQL(SQL_MODIFY_PASSWORD, new ModifyPasswordBinder(userId, password));
        return true;
    }
    
    @Override
    public User get(long userId) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(userId);
        User user = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return user;
    }
    
    @Override
    public List<User> getAll() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public User add(User user) throws SQLiteException {
        UserBinder binder = new UserBinder(user);
        executeSQL(SQL_INSERT, binder);
        return get(user.getId());
    }
    
    private RowMapper<User> rowMapper = new RowMapper<User>() {
        
        @Override
        public User map(SQLiteStatement stmt) throws SQLiteException {
            User user = new User();
            user.setId(stmt.columnLong(0));
            user.setName(stmt.columnString(1));
            user.setPassword(stmt.columnString(2));
            user.setType(stmt.columnInt(3));
            user.setRealName(stmt.columnString(4));
            user.setComment(stmt.columnString(5));
            return user;
        }
    };
    
    private class GetByNameBinder implements StatementBinder {
        private final String name;

        public GetByNameBinder(String name) {
            super();
            this.name = name;
        }


        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, name);
        }
    }
    
    private class UserBinder implements StatementBinder{
        private final User user;

        public UserBinder(User user) {
            super();
            this.user = user;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, user.getId());
            stmt.bind(2, user.getName());
            stmt.bind(3, user.getPassword());
            stmt.bind(4, user.getType());
            stmt.bind(5, user.getRealName());
            stmt.bind(6, user.getComment());
        }
    }
    
    private class ModifyPasswordBinder implements StatementBinder {
        private final long userId;
        private final String password;

        public ModifyPasswordBinder(long userId, String password) {
            super();
            this.userId = userId;
            this.password = password;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, password);
            stmt.bind(2, userId);
        }
    }
    
    private class UpdateBinder implements StatementBinder{
        private final User user;

        public UpdateBinder(User user) {
            super();
            this.user = user;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, user.getName());
            stmt.bind(2, user.getType());
            stmt.bind(3, user.getRealName());
            stmt.bind(4, user.getComment());
            stmt.bind(5, user.getId());
        }
    }
}
