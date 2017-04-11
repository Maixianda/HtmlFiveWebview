package com.szchangliang.htmlfivewebview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.szchangliang.htmlfivewebview.ui.activity.BaseWebViewAcrivity;
import com.szchangliang.htmlfivewebview.ui.activity.CustomerWebViewActivity;

public class MainActivity extends AppCompatActivity {

    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(TextView)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, CustomerWebViewActivity.class);
                intent2.putExtra(BaseWebViewAcrivity.WEB_URL, "http://www.baidu.com");
                startActivity(intent2);
            }
        });
    }
}
