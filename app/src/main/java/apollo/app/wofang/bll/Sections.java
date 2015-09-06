package apollo.app.wofang.bll;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.data.dao.sqldal.SectionDao;
import apollo.data.model.Section;
import apollo.util.ResUtil;

/**
 * Created by Texel on 2015/8/20.
 */
public class Sections {

    public static void removeAll() {
        List<Section> sections = null;

        sections = getSections(-1);
        remove(sections);
    }

    private static void remove(List<Section> sections) {
        for(Section s:sections) {
            SectionDao.getInstance().delete(s.getId());
        }
    }


    private static void init() {
        List<Section> sections = null;
        Type t = null;

        t = new TypeToken<ArrayList<Section>>(){}.getType();
        sections = ResUtil.readAsset("sections.json", t);

        SectionDao.getInstance().add(sections);
    }


    public static void updateRecommendSections(List<Section> sections) {
        List<Section> ss = null;

        ss = getRecommendSections();
        for(Section s:ss) {
            SectionDao.getInstance().delete(s.getId());
        }
        SectionDao.getInstance().add(sections);
    }

    public static void updateSubSections(List<Section> sections) {
        List<Section> ss = null;

        ss = getSubSections();
        for(Section s:ss) {
            SectionDao.getInstance().delete(s.getId());
        }
        SectionDao.getInstance().add(sections);
    }

    public static List<Section> getRecommendSections() {
        return getSections(0);
    }

    public static List<Section> getSubSections() {
        return getSections(1);
    }

    private static List<Section> getSections(int type) {
        List<Section> ss = null;

        ss = SectionDao.getInstance().getSections(type, 1, Integer.MAX_VALUE).getObjects();
        if (ss.size() == 0) {
            init();
            ss = SectionDao.getInstance().getSections(type, 1, Integer.MAX_VALUE).getObjects();
        }
        return ss;
    }

}
