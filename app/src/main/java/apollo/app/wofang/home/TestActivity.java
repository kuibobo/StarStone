package apollo.app.wofang.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import org.miscwidgets.widget.Panel;

import apollo.app.wofang.R;


public class TestActivity extends ActionBarActivity {
//
//    private class TestTask extends AsyncTask<Void, Void, String> {
//
//
//        @Override
//        protected String doInBackground(Void... params) {
//            AsyncHttpClient client = AsyncHttpClient.getInstance();
//
//            String content = client.getContent("http://m.wofang.com");
//
//            // fix css
//            content = Regex.replace(content,
//                    "<link\\s.*?href=\"([^\"]+)\"[^>]*/>",
//                    "<link href=\"http://m.wofang.com$1\" rel=\"stylesheet\" type=\"text/css\" />");
//
//            // fix js
//            content = Regex.replace(content,
//                    "<script\\s.*?src=\"([^\"]+)\"[^>]*></script>",
//                    "<script src=\"http://m.wofang.com$1\"></script>");
//
//            // remove element
//            content = Regex.replace(content, "(?s)<div class=\"header\">.*</form></div></div>", "");
//            content = Regex.replace(content, "<div class=\"(bot_menu|menu|wraper telbg)\"[^>]*?>.*?</div>", "");
//            content = Regex.replace(content, "<div class=\"footer_from\">.*?</form></div>", "");
//            content = Regex.replace(content, "(?s)<div class=\"footer\">.*</span></div>", "");
//
//            //content += "<style>.header,.footer,.footer_from{display:none;}</style>";
//            return content;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            view.loadData(result, "text/html; charset=UTF-8", null);
//        }
//    }
//
//    private WebView view = null;
//    private TestTask task = null;
//
    public static void startActivity(Context context) {
        Intent intent = null;

        intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.activity_test);
//
//        view = (WebView) super.findViewById(R.id.webView);
//        view.getSettings().setJavaScriptEnabled(true);
//        view.setWebViewClient(new WebViewClient() {
//            /* ����¼��������û��������ʱ������
//             * ͨ���ж�url����ȷ����β�����
//             * �������true����ʾ�����Ѿ����������request��
//             * �������false���� ʾû�д���
//             * ��ô������������url��ȡ��ҳ*/
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                String page="<html><body><a href=\"clock\">" +
//                        new Date().toString()  + "</a></body></html>";
//	        /* ��������ʹ����loadDataWithBaseUrl�ķ�ʽ��
//	         * ���������ͨ��"file:///android_asset/"�����ӳ����asset/����Դ��
//	         * �����������û������baseUrl������Ϊ�գ����޷���ȡ��
//	         * ���ȡ��Щ��Դ��baseUrl��������Ϊhttp(s)/ftp(s) /about/javascript
//	         * ��Щ����ר���Ķ��塣
//	         * �������У�����baseUrl����Ϊ"wei://base".
//	         * ����������Ǹ����ӵ�ַΪwei://base/clock */
//                view.loadDataWithBaseURL ("wei://base", page, "text/html", "UTF-8", null);
//
//                return true; //�� ʾ�Ѿ����������URL������
//            }
//        });
//
//        task = new TestTask();
//        task.execute();
//    }

    private Panel panel = null;
    //    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_test);

        panel = (Panel) super.findViewById(R.id.layout_main_sections);

        Button btn = (Button) super.findViewById(R.id.btn_drop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (panel.isOpen()) {
                    panel.setOpen(false, true);
                } else {
                    panel.setOpen(true, true);
                }
            }
        });
    }
}
