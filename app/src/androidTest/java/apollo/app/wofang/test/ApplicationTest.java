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

    public void testJson() {
        List<Section> ss = null;
        Section s = null;
        Gson gson = new Gson();

        ss = new ArrayList<Section>();

        s = new Section();
        s.setName("主页");
        s.setLocked(true);
        s.setUrl("http://m.wofang.com");
        ss.add(s);

        s = new Section();
        s.setName("新房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/building/");
        ss.add(s);

        s = new Section();
        s.setName("二手房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/sale/");
        ss.add(s);

        s = new Section();
        s.setName("租房");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/hire/");
        ss.add(s);

        s = new Section();
        s.setName("资讯");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/news/");
        ss.add(s);

        s = new Section();
        s.setName("问答");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/asks/");
        ss.add(s);

        s = new Section();
        s.setName("看房团");
        s.setLocked(false);
        s.setUrl("http://m.wofang.com/tuan/");
        ss.add(s);

        String str = gson.toJson(ss);
        Log.i("Test", str);

        Context context = getContext();
        str = ResUtil.read(context.getAssets(), "recomm_sections.json");

        Type listType = new TypeToken<ArrayList<Section>>(){}.getType();
        ss = null;
        ss = gson.fromJson(str, listType);
    }
}