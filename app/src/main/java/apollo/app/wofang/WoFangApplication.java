package apollo.app.wofang;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import apollo.core.ApolloApplication;

public class WoFangApplication extends ApolloApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
    }
}
