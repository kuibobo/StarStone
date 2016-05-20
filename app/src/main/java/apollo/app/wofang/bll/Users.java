package apollo.app.wofang.bll;

import apollo.app.wofang.data.dao.sqldal.UserDao;
import apollo.data.model.User;

/**
 * Created by Texel on 2016/5/20.
 */
public class Users {

    public static User getUser() {
        UserDao ud = UserDao.getInstance();

        return ud.getUser(-1, null);
    }

    public static User getUser(int userId) {
        UserDao ud = UserDao.getInstance();

        return ud.getUser(userId, null);
    }

    public static User getUser(String name) {
        UserDao ud = UserDao.getInstance();

        return ud.getUser(-1, name);
    }

    public static void update(int id, User user) {
        UserDao ud = UserDao.getInstance();

        ud.update(id, user);
    }


}
