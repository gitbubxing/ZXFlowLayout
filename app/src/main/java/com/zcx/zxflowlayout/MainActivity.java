package com.zcx.zxflowlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zcx.zxflowlayout.view.ZXFlowLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ZXFlowLayout flowLayout;

    String[] texts = new String[]{
            "书包", "零食", "哇哈哈", "可口可乐",
            "冰红茶", "风扇", "士力架", "保温杯", "插座",
            "音箱", "空调", "iphone7", "ipad"
    };

    int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        length = texts.length;
        flowLayout = (ZXFlowLayout) findViewById(R.id.flowlayout);
        findViewById(R.id.btn_add_random).setOnClickListener(this);
        findViewById(R.id.btn_remove_all).setOnClickListener(this);
        for (int i = 0; i <10 ; i++) {
            addChildView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_random:
               addChildView();
                break;
            case R.id.btn_remove_all:
                flowLayout.removeAllViews();
                break;
            default:
                break;
        }
    }

    public void addChildView(){
        int ranHeight = dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(dip2px(this, 15), 0, dip2px(this, 15), 0);
        tv.setTextColor(Color.parseColor("#FF3030"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        int index = (int)(Math.random() * length);
        tv.setText(texts[index]);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(2);
        tv.setBackgroundResource(R.drawable.bg_tag);
        flowLayout.addView(tv, lp);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
