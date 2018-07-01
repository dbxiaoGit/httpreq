package cn.x1.httpreq;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HandlerActivity01 extends Activity {
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.mybutton);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //开始执行AsyncTask，并传入某些数据
                new LongTimeTask().execute("New Text");
            }
        });
    }

    private class LongTimeTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

    }
}
