package base.utils.sqllite.dao;

import base.utils.sqllite.RowMapper;
import base.utils.sqllite.SqliteHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BaseDao {
    protected SqliteHelper sqliteHelper = null;

    public BaseDao(String tableName,String createTableSql) throws Exception{
        sqliteHelper = new SqliteHelper();
        List<String> result =  sqliteHelper.executeQuery("SELECT name FROM sqlite_master WHERE type='table';", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int index) throws SQLException {
                return  rs.getString(1);
            }
        });

        if(!result.contains(tableName)) {
            sqliteHelper.executeUpdate(createTableSql);
        }
    }
}
