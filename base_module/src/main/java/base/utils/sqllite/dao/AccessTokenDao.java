package base.utils.sqllite.dao;

import base.utils.sqllite.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccessTokenDao extends BaseDao {
    String tableName = "accesstoken";
    public AccessTokenDao() throws Exception{
        super("accesstoken",
                "create table accesstoken(" +
                        "desc text primary key," +
                        "accesstoken text not null);");
    }



    public String getCurrentAccessToken() throws Exception {
        List<String> accessTokens = sqliteHelper.executeQuery("select accesstoken from " + tableName + " where desc='accesstoken'", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int index) throws SQLException {

                return rs.getString(1);
            }
        });

        if(accessTokens.size()>0) {
            return accessTokens.get(0);
        }
        return null;
    }


    public void setFirstAccessToken(String accessToken) throws Exception {
        sqliteHelper.executeUpdate("insert into accesstoken(accesstoken,desc) values('"+accessToken+"','accesstoken')");
    }


    public void updateAccessToken(String accessToken) throws Exception {
        sqliteHelper.executeUpdate("update accesstoken set accesstoken='"+accessToken+"' where desc='accesstoken'");
    }

    public void deleteAccessToken() throws Exception {
        sqliteHelper.executeUpdate("delete from accesstoken where desc='accesstoken'");
    }

    public static void main(String[] args)  throws Exception{
        AccessTokenDao accessTokenDao = new AccessTokenDao();
        String s = accessTokenDao.getCurrentAccessToken();
        System.out.println(s);
    }
}
