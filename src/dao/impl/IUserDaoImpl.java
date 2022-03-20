package dao.impl;

import dao.IUserDao;
import domain.User;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName IUserDaoImpl
 * @Description TODO
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/17 20:42
 * @Version 1.0
 */

public class IUserDaoImpl implements IUserDao {

    private DBUtils utils = new DBUtils();
    private ResultSet rs = null;
    private User user = new User();

    @Override
    public User queryByName(String name) {
        return _name(utils.query("SELECT name,password  FROM user WHERE name = ?", name));
    }

    @Override
    public int insertUser(String name, String password) {
        return utils.update("INSERT name,password FROM user WHERE name = ?,password = ?", name, password);
    }

    public User _name(ResultSet rs) {
        try {
            while (rs.next()) {
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.closeAll();
        }
        return user;
    }

}