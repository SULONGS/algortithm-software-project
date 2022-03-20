package service.impl;

import dao.IUserDao;
import dao.impl.IUserDaoImpl;
import domain.User;
import service.UserService;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Yuan Fen, Yiyou long
 * @Date 2022/3/18 9:35
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {

    IUserDao iUserDao = new IUserDaoImpl();
    User user = new User();

    @Override
    public int login(String name, String password) {
        int loginSuccess = 1;
        int passWrong = 2;
        int nameInvalid = 3;
        user = iUserDao.queryByName(name);
        if (user.getName() != null) {
            if (user.getPassword().equals(password)) {
                return loginSuccess;
            } else {
                return passWrong;
            }
        } else {
            return nameInvalid;
        }
    }


    @Override
    public Boolean register(String name, String password) {
        if (iUserDao.insertUser(name, password) > 0) {
            return true;
        } else {
            return false;
        }
    }

}
