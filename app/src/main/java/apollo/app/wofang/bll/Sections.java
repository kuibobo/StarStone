package apollo.app.wofang.bll;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import apollo.app.wofang.data.dao.sqldal.SectionDao;
import apollo.core.ApolloApplication;
import apollo.data.model.Section;
import apollo.util.ResUtil;

/**
 * Created by Texel on 2015/8/20.
 */
public class Sections {

    public static void updateRecommendSections(List<Section> sections) {
        SectionDao.getInstance().add(sections);
    }

    public static void updateSubSections(List<Section> sections) {
        SectionDao.getInstance().add(sections);
    }

    public static List<Section> getRecommendSections() {
//        Type type = null;
//        List<Section> entities = null;
//
//        type = new TypeToken<ArrayList<Section>>(){}.getType();
//        entities = ResUtil.readAsset("recomm_sections.json", type);
//
//        return entities;

        return getSections(1);
    }

    public static List<Section> getSubSections() {
        return getSections(1);
    }

    private static List<Section> getSections(int type) {
        if (ApolloApplication.app().isFirstUse()) {
            List<Section> entities = null;
            Type t = null;

            t = new TypeToken<ArrayList<Section>>(){}.getType();
            entities = ResUtil.readAsset("sections.json", t);

            SectionDao.getInstance().add(entities);
        }
        return SectionDao.getInstance().getSections(type, 1, Integer.MAX_VALUE).getObjects();
    }

}
