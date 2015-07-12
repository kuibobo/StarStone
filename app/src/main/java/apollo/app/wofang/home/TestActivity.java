package apollo.app.wofang.home;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;

import apollo.app.wofang.R;
import apollo.net.AsyncHttpClient;
import apollo.util.Regex;


public class TestActivity extends ActionBarActivity {

    private class TestTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {
            AsyncHttpClient client = AsyncHttpClient.getInstance();

            String content = client.getContent("http://m.wofang.com");

            // fix css
            content = Regex.replace(content,
                    "<link\\s.*?href=\"([^\"]+)\"[^>]*/>",
                    "<link href=\"http://m.wofang.com$1\" rel=\"stylesheet\" type=\"text/css\" />");

            // fix js
            content = Regex.replace(content,
                    "<script\\s.*?src=\"([^\"]+)\"[^>]*></script>",
                    "<script src=\"http://m.wofang.com$1\"></script>");

            // remove element
            content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
            content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
            content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
            content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");

            //content += "<style>.header,.footer,.footer_from{display:none;}</style>";
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            view.loadData(result, "text/html; charset=UTF-8", null);
        }
    }

    private WebView view = null;
    private TestTask task = null;

    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);

        view = (WebView) super.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient() {
            /* 这个事件，将在用户点击链接时触发。
             * 通过判断url，可确定如何操作，
             * 如果返回true，表示我们已经处理了这个request，
             * 如果返回false，表 示没有处理，
             * 那么浏览器将会根据url获取网页*/
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String page="<html><body><a href=\"clock\">" +
                        new Date().toString()  + "</a></body></html>";
	        /* 这里我们使用了loadDataWithBaseUrl的方式。
	         * 浏览器可以通过"file:///android_asset/"来获得映用中asset/的资源。
	         * 但是如果我们没有设置baseUrl，或者为空，这无法获取，
	         * 想获取这些资源，baseUrl不能设置为http(s)/ftp(s) /about/javascript
	         * 这些已有专属的定义。
	         * 在例子中，我们baseUrl设置为"wei://base".
	         * 对于上面的那个链接地址为wei://base/clock */
                view.loadDataWithBaseURL ("wei://base", page, "text/html", "UTF-8", null);

                return true; //表 示已经处理了这次URL的请求
            }
        });

        task = new TestTask();
        task.execute();
    }
}
