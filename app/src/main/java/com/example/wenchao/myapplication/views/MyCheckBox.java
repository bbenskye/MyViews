package com.example.wenchao.myapplication.views;

import android.content.Context;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/9/7.
 *
 * @author wenchao
 * @since 1.0
 */
public class MyCheckBox extends LinearLayout {
    private TextView tv_title;
    private Context context;
    private List<CheckBox> allCheckBox;
    public MyCheckBox(Context context, String title, List<String> items) {
        super(context);
        this.context = context;
        allCheckBox = new ArrayList<>();
        initViews(title, items);
    }

    private void initViews(String title, List<String> items){
        setOrientation(VERTICAL);
        tv_title = new TextView(context);
        tv_title.setText(title);

        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setOrientation(GridLayout.HORIZONTAL);
        gridLayout.setColumnCount(3);
        this.addView(tv_title);
        this.addView(gridLayout);
        for(int i = 0; i<items.size(); i++){
            String str = items.get(i);
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(str);
            allCheckBox.add(checkBox);
            gridLayout.addView(checkBox);
        }
    }

    public List<String> getCheckedResult(){
        List<String> result = new ArrayList<>();
        for(CheckBox box : allCheckBox){
            if(box.isChecked()){
                result.add(box.getText().toString());
            }
        }
        return result;
    }
}
