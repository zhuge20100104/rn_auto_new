package base.utils.sqllite;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The sql lite helper class to operate database
 */
public class SqliteHelper {

    /** The log4j log used to print log*/
    final static Logger logger = Logger.getLogger(SqliteHelper.class);

    /** The sql lite connection instance*/
    private Connection connection;

    /** The jdbc statement instance*/
    private Statement statement;

    /** The jdbc result set instance*/
    private ResultSet resultSet;

    /** Database file path string*/
    private String dbFilePath;


    /**
     * Construct of sqlite helper
     * @throws ClassNotFoundException  Throws this exception when jdbc jar is not ready
     * @throws SQLException    Throws this exception when jdbc jar is not ready
     */
    public SqliteHelper() throws ClassNotFoundException, SQLException {
       this("test.db");
    }

    /**
     * Constructor of sqlite helper
     * @param dbFilePath Sqlite db file path
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     * @throws SQLException Throws this exception when jdbc jar is not ready
     */
    public SqliteHelper(String dbFilePath) throws ClassNotFoundException, SQLException {
        this.dbFilePath = dbFilePath;
        connection = getConnection(dbFilePath);
    }

    /**
     * Get sqllite database connection
     * @param dbFilePath Database file path
     * @return Database connection
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     * @throws SQLException Throws this exception when jdbc jar is not ready
     */
    public Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        return conn;
    }

    /**
     * Execute sql query
     * @param sql Sql query statement
     * @param rse Result set handle class
     * @param <T>  Type parameter, for what type of object you want to return
     * @return Execution query result
     * @throws SQLException  Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    public <T> T executeQuery(String sql, ResultSetExtractor<T> rse) throws SQLException, ClassNotFoundException {
        try {
            resultSet = getStatement().executeQuery(sql);
            T rs = rse.extractData(resultSet);
            return rs;
        } finally {
            destroyed();
        }
    }


    /**
     * Execute sql query, get result list
     * @param sql Sql select statement
     * @param rm Row mapper class to handle a line of result set
     * @param <T>  Type parameter, for what type of object you want to return.
     * @return  List of results
     * @throws SQLException Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     *
     * <br> <b> See a demo as follow,</b>
     * <br>  &nbsp;   &nbsp; List&lt;String&gt; accessTokens = sqliteHelper.executeQuery("select accesstoken from " + tableName + " where desc='accesstoken'", new RowMapper&lt;String&gt;() {
     * <br>  &nbsp;   &nbsp;         @Override
     * <br> &nbsp; &nbsp;              public String mapRow(ResultSet rs, int index) throws SQLException {
     * <br>
     * <br>  &nbsp; &nbsp; &nbsp; &nbsp;                 return rs.getString(1);
     * <br>  &nbsp; &nbsp;             }
     * <br>   &nbsp; &nbsp;        });
     *
     * <br>   &nbsp; &nbsp;        if(accessTokens.size()&gt;0) {
     * <br>  &nbsp; &nbsp; &nbsp; &nbsp;             return accessTokens.get(0);
     * <br>  &nbsp; &nbsp;         }
     * <br>   &nbsp; &nbsp;        return null;
     */
    public <T> List<T> executeQuery(String sql, RowMapper<T> rm) throws SQLException, ClassNotFoundException {
        List<T> rsList = new ArrayList<T>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                rsList.add(rm.mapRow(resultSet, resultSet.getRow()));
            }
        } finally {
            destroyed();
        }
        return rsList;
    }



    /**
     * Execute sql query, return list of results, <b>ignore null result</b>
     * @param sql The sql query statement
     * @param rm Row mapper class to handle a line of result set
     * @param <T> Type parameter, for what type of object you want to return
     * @return  List of results
     * @throws SQLException Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    public <T> List<T> executeQueryIgnoreNull(String sql, RowMapper<T> rm) throws SQLException, ClassNotFoundException {
        List<T> rsList = new ArrayList<T>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                T row = rm.mapRow(resultSet, resultSet.getRow());
                if(row!=null) {
                    rsList.add(row);
                }
            }
        } finally {
            destroyed();
        }
        return rsList;
    }

    /**
     * Execute update statement.
     * @param sql  The sql statement to execute
     * @return How many lines you have updated
     * @throws SQLException Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        try {
            int c = getStatement().executeUpdate(sql);
            return c;
        } finally {
            destroyed();
        }

    }

    /**
     * Execute multiple sql statements
     * @param sqls  Sql statements you want to execute
     * @throws SQLException Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    public void executeUpdate(String...sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }

    /**
     * Execute multiple sql statements
     * @param sqls Sql statements list
     * @throws SQLException Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }

    /**
     *  Get sqllite database connection
     * @return The sqllite database connection.
     * @throws ClassNotFoundException  Throws this exception when jdbc jar is not ready
     * @throws SQLException Throws this exception when jdbc jar is not ready
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (null == connection) connection = getConnection(dbFilePath);
        return connection;
    }


    /**
     * Get jdbc statement instance
     * @return  The jdbc statement
     * @throws SQLException  Throws this exception when jdbc jar is not ready
     * @throws ClassNotFoundException Throws this exception when jdbc jar is not ready
     */
    private Statement getStatement() throws SQLException, ClassNotFoundException {
        if (null == statement) statement = getConnection().createStatement();
        return statement;
    }

    /**
     * Close and release database resources
     */
    public void destroyed() {
        try {
            if (null != resultSet) {
                resultSet.close();
                resultSet = null;
            }

            if (null != statement) {
                statement.close();
                statement = null;
            }

            if (null != connection) {
                connection.close();
                connection = null;
            }


        } catch (SQLException e) {
            logger.error("Sqlite数据库关闭时异常", e);
        }
    }
}
