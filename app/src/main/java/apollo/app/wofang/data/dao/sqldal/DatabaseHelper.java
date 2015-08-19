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
    public static final String APOLLO_WF_DATA_TABLE_RECOMMSECTION = "recomm_sections";
    public static final String APOLLO_WF_DATA_TABLE_SUBSECTION = "sub_sections";

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

        // section
        buf1 = new StringBuffer();
        buf1.append("create table if not exists ");
        buf1.append(APOLLO_WF_DATA_TABLE_SECTION);
        buf1.append(" (");
        buf1.append(Section.Columns.ID);
        buf1.append(" varchar(8) primary key,");
        buf1.append(Section.Columns.NAME);
        buf1.append(" varchar(16))");

        db.execSQL(buf1.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
