package apollo.app.wofang.data.dao.sqldal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import apollo.data.model.User;

/**
 * Created by Texel on 2016/5/20.
 */
public class UserDao {

    private static UserDao inst = null;

    private UserDao() {}

    public static UserDao getInstance() {
        if (inst == null)
            inst = new UserDao();
        return inst;
    }

    public User getUser(int userId, String name) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String[] columns = null;
        String[] selectionArgs = null;
        String selection = null;
        User user = null;

        db = DatabaseHelper.getReadDatabase();
        columns = new String[] {User.Columns.ID, User.Columns.NAME, User.Columns.PASSWORD, User.Columns.TICKET, User.Columns.ACTIVE};
        selectionArgs = new String[]{name};

        if (userId == -1 && TextUtils.isEmpty(name)) {
            selection = User.Columns.ACTIVE + "=1";
        } else if (userId != -1) {
            selection = User.Columns.ID + "=" + userId;
        } else {
            selection = User.Columns.NAME + "=?";
        }

        cursor = db.query(DatabaseHelper.APOLLO_WF_DATA_TABLE_USER, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setTicket(cursor.getString(3));
                user.setActive(cursor.getInt(4) == 1);
            }
            cursor.close();
        }
        db.close();
        return user;
    }

    public int add(User user) {
        SQLiteDatabase db = null;
        ContentValues values = null;
        int result = -1;

        values = new ContentValues(14);
        values.put(User.Columns.ID, user.getId());
        values.put(User.Columns.NAME, user.getName());
        values.put(User.Columns.PASSWORD, user.getPassword());
        values.put(User.Columns.TICKET, user.getTicket());
        values.put(User.Columns.ACTIVE, 0);

        db = DatabaseHelper.getWriteDatabase();
        result = (int)db.insert(DatabaseHelper.APOLLO_WF_DATA_TABLE_USER, null, values);
        db.close();
        return result;
    }

    public int update(int id, User user) {
        SQLiteDatabase db = null;
        ContentValues values = null;
        String whereClause = null;
        String[] whereArgs = null;
        int result = -1;

        values = new ContentValues(4);
        values.put(User.Columns.NAME, user.getName());
        values.put(User.Columns.PASSWORD, user.getPassword());
        values.put(User.Columns.TICKET, user.getTicket());
        values.put(User.Columns.ACTIVE, user.isActive());

        whereClause = User.Columns.ID + "=?";
        whereArgs = new String[]{Integer.toString(id)};

        db = DatabaseHelper.getWriteDatabase();
        result = db.update(DatabaseHelper.APOLLO_WF_DATA_TABLE_USER, values, whereClause, whereArgs);
        db.close();
        return result;
    }
}
