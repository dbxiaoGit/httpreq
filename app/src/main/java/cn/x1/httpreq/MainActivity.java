package cn.x1.httpreq;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = findViewById(R.id.mybutton);
        this.textView = findViewById(R.id.textview);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                for(int i=0;i<80;i++){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true) {
                                sendGet();
                            }
                        }
                    }).start();
                }
                Log.d("click","aaa");
            }
        });
    }

    public String sendGet() {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = "https://h5.qzone.qq.com/ugc/share/D99417FD032C65AE9B3FBF8A11505D09?uw=1421270531&subtype=0&sid=&from=singlemessage&blog_photo=0&appid=2&ciphertext=D99417FD032C65AE9B3FBF8A11505D09&_wv=1";
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            // 建立实际的连接
            connection.connect();
            /*
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            */
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //System.out.println(result);
        Pattern pa = Pattern.compile(">浏览(.+?)次<");
        Matcher matcher = pa.matcher(result);
        String totalCount = "";
        while(matcher.find()){
            totalCount = matcher.group(0);
        }
        //textView.setText(totalCount);
        Message message = new Message();
        message.obj = totalCount;
        handler.sendMessage(message);
        return result;
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            textView.setText(msg.obj.toString());
        }
    };
}
