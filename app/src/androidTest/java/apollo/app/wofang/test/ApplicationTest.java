package apollo.app.wofang.test;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import apollo.data.model.Section;
import apollo.util.ResUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testRecommJson() {
        List<Section> ss = null;
        Section s = null;
        Gson gson = new Gson();

        ss = new ArrayList<Section>();

        s = new Section();
        s.setName("主页");
        s.setLocked(true);
        s.setUrl("http://m.wofang.com");
        s.setGuid("MainContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("新房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/");
        s.setGuid("SearchContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("二手房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/sale/");
        s.setGuid("SearchContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("租房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/hire/");
        s.setGuid("SearchContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("资讯");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/news/");
        s.setGuid("ListContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("问答");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/asks/");
        s.setGuid("AskContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("看房团");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/tuan/");
        s.setGuid("ContactContent");
        s.setType(0);
        ss.add(s);

        s = new Section();
        s.setName("海口");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_38/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("三亚");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_39/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("琼海");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_318/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("儋州");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_322/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("博鳌");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_3241/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("陵水");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_330/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("澄迈");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_317/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("万宁");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_319/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("临高");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_323/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("定安");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_325/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("保亭");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_331/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("五指山");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_320/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("乐东");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_332/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("屯昌");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_326/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("东方");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_321/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("琼中");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_329/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("五指山");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_320/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("昌江");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_327/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        s = new Section();
        s.setName("白沙");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/ct_328/");
        s.setGuid("SearchContent");
        s.setType(1);
        ss.add(s);

        String str = gson.toJson(ss);
        Log.i("Test", str);

        Context context = getContext();
        str = ResUtil.readAsset(context.getAssets(), "sections.json");

        Type listType = new TypeToken<ArrayList<Section>>(){}.getType();
        ss = null;
        ss = gson.fromJson(str, listType);
    }
}