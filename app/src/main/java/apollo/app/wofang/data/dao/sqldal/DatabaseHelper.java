package apollo.app.wofang.data.dao.sqldal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import apollo.core.ApolloApplication;
import apollo.data.model.Section;

/**
 * Created by Texel on 2015/8/19.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String APOLLO_WF_DATABASE_NAME= "apollo.db";
    public static final String APOLLO_WF_DATA_TABLE_USER = "users";
    public static final String APOLLO_WF_DATA_TABLE_SECTION = "sections";

    public DatabaseHelper(Context context) {
        super(context, APOLLO_WF_DATABASE_NAME, null, 1);
    }

    public static SQLiteDatabase getWriteDatabase() {
        SQLiteDatabase db =  null;

        db = (new DatabaseHelper(ApolloApplication.app())).getWritableDatabase();
        return db;
    }

    public static SQLiteDatabase getReadDatabase() {
        SQLiteDatabase db =  null;

        db = (new DatabaseHelper(ApolloApplication.app())).getReadableDatabase();
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer buf1 = null;

        // recomm section
        buf1 = new StringBuffer();
        buf1.append("create table if not exists ");
        buf1.append(APOLLO_WF_DATA_TABLE_SECTION);
        buf1.append(" (");
        buf1.append(Section.Columns.ID);
        buf1.append(" integer primary key,");
        buf1.append(Section.Columns.NAME);
        buf1.append(" varchar(16),");
        buf1.append(Section.Columns.URL);
        buf1.append(" varchar(1024),");
        buf1.append(Section.Columns.GUID);
        buf1.append(" varchar(128),");
        buf1.append(Section.Columns.TYPE);
        buf1.append(" integer,");
        buf1.append(Section.Columns.LOCKED);
        buf1.append(" integer)");

        db.execSQL(buf1.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
