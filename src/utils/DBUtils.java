package utils;

import java.sql.*;
import java.util.Objects;

public class DBUtils {

    private String DBDriver = "com.mysql.jdbc.Driver";
    private String DBURL = "jdbc:mysql://localhost:3306/algorithm_test?autoRec";
    private String DBUser = "root";
    private String DBPass = "root";
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public DBUtils() {
        try {
            Class.forName(DBDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        try {
            conn = DriverManager.getConnection(DBURL, DBUser, DBPass);//取得连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * @Author Yuan Fen,Yiyou Long
     * @Description close all resources
     * @Date 19:35 2022/3/17
     * @Param
     * @return
     **/
    public void closeAll() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int update(String sql, Object... obj) {
        int rst = 0;
        conn = getConn();
        try {
            ps = conn.prepareStatement(sql);
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            rst = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return rst;
    }

    public ResultSet query(String sql, Object... obj) {
        conn = getConn();
        try {
            ps = conn.prepareStatement(sql);
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

}  