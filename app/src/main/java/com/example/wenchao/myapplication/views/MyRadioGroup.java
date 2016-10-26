package com.example.wenchao.myapplication.views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/9/8.
 *
 * @author wenchao
 * @since 1.0
 */
public class MyRadioGroup extends LinearLayout {

    private Context context;
    private TextView tv_title;
    private List<RadioButton> allRadioButton;

    public MyRadioGroup(Context context, String title, List<String> items) {
        super(context);
        this.context = context;
        allRadioButton = new ArrayList<>();
        initViews(title, items);
    }

    private void initViews(String title, List<String> items) {
        setOrientation(LinearLayout.VERTICAL);
        tv_title = new TextView(context);
        tv_title.setText(title);
//        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(20, 10, 10, 10);
        addView(tv_title);

        RadioGroup group = new RadioGroup(context);
        for(int i=0; i<items.size(); i++){
            String str = items.get(i);
            RadioButton rButton = new RadioButton(context);
            rButton.setText(str);
            allRadioButton.add(rButton);
//            if(i==0)rButton.setChecked(true);
            group.addView(rButton);
        }
        addView(group);
    }

    public String getCheckedResult(){
        for(RadioButton radio : allRadioButton){
            if(radio.isChecked()){
                return radio.getText().toString();
            }
        }
        return "";
    }
}
