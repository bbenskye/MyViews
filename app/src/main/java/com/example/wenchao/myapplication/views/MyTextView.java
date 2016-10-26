package com.example.wenchao.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wenchao.myapplication.R;

/**
 * Created on 2016/9/7.
 *
 * @author wenchao
 * @since 1.0
 */
public class MyTextView extends RelativeLayout {
    private Context context;
    private TextView tv;
    private EditText edt;
    public static final int TYPE_NUMBER = 0;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_PHONE = 2;

    public MyTextView(Context context, String title, int type) {
        super(context);
        this.context = context;
        initView(title, type);
    }

//    public MyTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        context = context;
//    }

    private void initView(String title, int type) {
        View.inflate(context, R.layout.view_text, this);
        tv = (TextView) findViewById(R.id.tv_title);
        edt = (EditText) findViewById(R.id.tv_content);
        tv.setText(title);
        switch (type) {
            case TYPE_NUMBER:
                edt.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case TYPE_PHONE:
                edt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                break;
            case TYPE_TEXT:
                edt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                break;
        }


    }

    public String getTypedContent() {
        return edt.getText().toString();
    }
}
