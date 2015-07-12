package apollo.app.wofang.data.networkdal;

import apollo.data.model.Thread;

/**
 * Created by bourne on 2015/7/11.
 */
public class ThreadDataProvider extends DataProvider {

    private static ThreadDataProvider provider = null;
    public static ThreadDataProvider getInstance() {
        if (provider == null) {
            provider = new ThreadDataProvider();
        }
        return provider;
    }


    public Thread getThread(int id) {
        return null;
    }
}
