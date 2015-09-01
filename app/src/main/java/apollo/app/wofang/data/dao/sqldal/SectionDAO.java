package apollo.app.wofang.data.dao.sqldal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import apollo.data.model.Section;
import apollo.data.model.User;
import apollo.util.DataSet;

/**
 * Created by Texel on 2015/8/19.
 */
public class SectionDao {
    private static SectionDao inst = null;

    private SectionDao() {}

    public static SectionDao getInstance() {
        if (inst == null)
            inst = new SectionDao();
        return inst;
    }

    public int delete(int id) {
        SQLiteDatabase db = null;
        String whereClause = null;
        String[] whereArgs = null;
        int r = -1;

        whereClause = Section.Columns.ID + "=?";
        whereArgs = new String[]{Integer.toString(id)};
        db = DatabaseHelper.getWriteDatabase();
        r = db.delete(DatabaseHelper.APOLLO_WF_DATA_TABLE_SECTION, whereClause, whereArgs);
        db.close();

        return r;
    }

    public int[] update(List<Section> sections) {
        SQLiteDatabase db = null;
        ContentValues values = null;
        String whereClause = null;
        String[] whereArgs = null;
        int[] ids = null;
        int i = 0;

        db = DatabaseHelper.getWriteDatabase();
        ids = new int[sections.size()];
        for(Section s:sections) {
            values = new ContentValues(4);
            values.put(Section.Columns.NAME, s.getName());
            values.put(Section.Columns.URL, s.getUrl());
            values.put(Section.Columns.LOCKED, s.isLocked());
            values.put(Section.Columns.TYPE, s.getType());
            values.put(Section.Columns.GUID, s.getGuid());

            whereClause = Section.Columns.ID + "=?";
            whereArgs = new String[]{Integer.toString(s.getId())};
            ids[i++] = db.update(DatabaseHelper.APOLLO_WF_DATA_TABLE_SECTION, values, whereClause, whereArgs);
        }
        db.close();

        return ids;
    }

    public long[] add(List<Section> sections) {
        SQLiteDatabase db = null;
        ContentValues values = null;
        long[] ids = null;
        int i = 0;

        ids = new long[sections.size()];
        db = DatabaseHelper.getWriteDatabase();
        for(Section s:sections) {
            values = new ContentValues(4);
            values.put(Section.Columns.NAME, s.getName());
            values.put(Section.Columns.URL, s.getUrl());
            values.put(Section.Columns.LOCKED, s.isLocked());
            values.put(Section.Columns.TYPE, s.getType());
            values.put(Section.Columns.GUID, s.getGuid());

            ids[i++] = db.insert(DatabaseHelper.APOLLO_WF_DATA_TABLE_SECTION, null, values);
        }
        db.close();
        return ids;
    }

    public DataSet<Section> getSections(int type, int pageIndex, int pageSize) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        DataSet<Section> datas = null;
        List<Section> sections = null;
        Section s = null;
        String[] columns = null;
        String[] selectionArgs = null;
        String selection = null;
        String orderBy = null;
        String limit = null;

        sections = new ArrayList<Section>();
        datas = new DataSet<Section>();
        datas.setObjects(sections);

        db = DatabaseHelper.getReadDatabase();

        if (type != -1) {
            selection = Section.Columns.TYPE + "=?";
            selectionArgs = new String[]{Integer.toString(type)};
        }

        columns = new String[] {Section.Columns.ID, Section.Columns.NAME, Section.Columns.URL, Section.Columns.LOCKED, Section.Columns.TYPE, Section.Columns.GUID};
        //orderBy = Section.Columns.ID + " asc";
        limit = (pageIndex - 1) * pageSize + "," + pageSize;

        cursor = db.query(DatabaseHelper.APOLLO_WF_DATA_TABLE_SECTION, columns, selection, selectionArgs, null, null, orderBy, limit);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                s = new Section();

                s.setId(cursor.getInt(0));
                s.setName(cursor.getString(1));
                s.setUrl(cursor.getString(2));
                s.setLocked(cursor.getInt(3) == 1);
                s.setType(cursor.getInt(4));
                s.setGuid(cursor.getString(5));
                sections.add(s);
            }
            cursor.close();
        }

        columns = new String[] {"count(0)"};
        cursor = db.query(DatabaseHelper.APOLLO_WF_DATA_TABLE_SECTION, columns, null, null, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                datas.setTotalRecords(cursor.getInt(0));
            }
            cursor.close();
        }
        db.close();
        return datas;
    }

}
