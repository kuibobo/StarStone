package apollo.app.wofang.data.dao.sqldal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import apollo.data.model.Section;

/**
 * Created by Texel on 2015/8/19.
 */
public class SectionDAO {
    private static SectionDAO inst = null;

    public static SectionDAO getInstance() {
        return inst;
    }

    public void saveRecommendSections(List<Section> sections) {
        saveSections(DatabaseHelper.APOLLO_WF_DATA_TABLE_RECOMMSECTION, sections);
    }

    public void saveSubSections(List<Section> sections) {
        saveSections(DatabaseHelper.APOLLO_WF_DATA_TABLE_SUBSECTION, sections);
    }

    public void saveSections(String tab, List<Section> sections) {
        SQLiteDatabase db = null;
        ContentValues values = null;

        db = DatabaseHelper.getWriteDatabase();
        for(Section s:sections) {
            values = new ContentValues(2);
            values.put(Section.Columns.ID, s.getId());
            values.put(Section.Columns.NAME, s.getName());
            values.put(Section.Columns.NAME, s.getName());
            values.put(Section.Columns.URL, s.getUrl());
            values.put(Section.Columns.LOCKED, s.isLocked());

            db.insert(tab, null, values);
        }
        db.close();
    }
}
