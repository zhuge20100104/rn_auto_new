package base.utils.sqllite.dao;

import base.utils.sqllite.RowMapper;
import config.utils.ConfigUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MsgIdDao extends BaseDao {

    String tableName = "msgid";
    public MsgIdDao() throws Exception{
        super("msgid",
                "create table msgid(" +
                        "desc text primary key," +
                        "id text not null);");
    }





    public BigDecimal getCurrentMsgId() throws Exception {
        List<BigDecimal> msgIds = sqliteHelper.executeQuery("select id from " + tableName + " where desc='msgid'", new RowMapper<BigDecimal>() {
            @Override
            public BigDecimal mapRow(ResultSet rs, int index) throws SQLException {

                return rs.getBigDecimal(1);
            }
        });


        BigDecimal newMsgId;
        if(msgIds.size()>0) {
            newMsgId =  msgIds.get(0).add(new BigDecimal(1));
            updateMsgId(newMsgId.toString());
            return newMsgId;
        }else {
            String firstMessageId = ConfigUtil.getFirstWechatMessageId();
            newMsgId = new BigDecimal(firstMessageId);
            setFirstMsgId(newMsgId.toString());
            return newMsgId;
        }
    }


    public void updateMsgId(String msgId) throws Exception {
        sqliteHelper.executeUpdate("update msgid set id='"+msgId+"' where desc='msgid'");
    }

    public void setFirstMsgId(String msgId) throws Exception {
        sqliteHelper.executeUpdate("insert into msgid(id,desc) values('"+msgId+"','msgid')");
    }
    public static void main(String[] args) throws Exception{
        MsgIdDao msgIdDao = new MsgIdDao();
        System.out.println(msgIdDao.getCurrentMsgId());
    }


}
