package apollo.app.wofang.test;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import apollo.data.model.Section;

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
        s.setName("ึ๗าณ");
        s.setLocked(true);
        s.setUrl("http://m.wofang.com");
        ss.add(s);

        String str = gson.toJson(ss);
        Log.i("Test", str);
    }

}