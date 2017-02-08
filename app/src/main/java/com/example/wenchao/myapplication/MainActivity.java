package com.example.wenchao.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.wenchao.myapplication.utils.ListDataSave;
import com.example.wenchao.myapplication.views.MyCheckBox;
import com.example.wenchao.myapplication.views.MyRadioGroup;
import com.example.wenchao.myapplication.views.MyTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;
    String pack = "com.example.wenchao.myapplication4";
    private int i = 3;
    private MyTextView myTextView;
    private MyRadioGroup myRadioGroup;
    private MyCheckBox myCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        final ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        Button btn1 = new Button(this);
        Button btn2 = new Button(this);
        Button btn3 = new Button(this);
        Button btn4 = new Button(this);
        Button btn5 = new Button(this);

        //创建输入框
        myTextView = new MyTextView(MainActivity.this, "说出一个三国人物：", MyTextView.TYPE_TEXT);

        String[] checkItems = {"刘备", "关羽", "张飞", "赵云", "马超", "黄忠",
                "曹孟德", "张辽", "张郃", "徐晃", "乐进", "于禁",
                "孙权", "太史子义", "甘宁", "周泰", "蒋钦", "凌统"};
        List<String> itemsList = new ArrayList<>();
        for (String str : checkItems) {
            itemsList.add(str);
        }

        //创建多选框
        myCheckBox = new MyCheckBox(this, "属同一势力的有：", itemsList);
        myCheckBox.requestFocus();

        List<String> radioItems = new ArrayList<>();
        radioItems.add("魏");
        radioItems.add("蜀");
        radioItems.add("吴");
        radioItems.add("群雄");

        //创建单选框
        myRadioGroup = new MyRadioGroup(this, "所属势力:", radioItems);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 10, 10);
//        lp.leftMargin = 10;
        myCheckBox.setLayoutParams(lp);
//        setContentView(layout2);
        btn1.setText("添加view");
        btn2.setText("提交");
        btn3.setText("拍照");
        btn4.setText("小视频");
        btn5.setText("sharepreference");

        layout2.addView(myTextView);
        layout2.addView(myRadioGroup, lp);
        layout2.addView(myCheckBox);
        layout2.addView(btn2, lp);
        layout2.addView(btn3, lp);
        layout2.addView(btn4, lp);
        layout2.addView(btn1, lp);
        layout2.addView(btn5, lp);

        scrollView.addView(layout2);
        setContentView(scrollView);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "btn1 clicked!", Toast.LENGTH_SHORT).show();
                RelativeLayout relativeLayout = new RelativeLayout(v.getContext());
                RelativeLayout.LayoutParams params = new RelativeLayout
                        .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i % 2 == 0) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                Button btn_x = new Button(v.getContext());
                btn_x.setText("Button" + i);
                relativeLayout.addView(btn_x, params);
                layout2.addView(relativeLayout);

                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                i++;
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                String edtContent = myTextView.getTypedContent();

                sb.append(edtContent + " \n\n");

                String radio = myRadioGroup.getCheckedResult();

                sb.append(radio + ": \n");

                List<String> result = myCheckBox.getCheckedResult();
                for (String str : result) {
                    sb.append(str);
                    sb.append(", ");
                }
                if (!TextUtils.isEmpty(edtContent) || !TextUtils.isEmpty(radio) || result.size() > 0) {
                    String res = sb.toString().substring(0, sb.length() - 1);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    System.out.println("Checked : " + res);
                } else {
                    Toast.makeText(MainActivity.this, "请做出一个选择", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VideoRecorderActivity.class);
                startActivity(i);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TakePictureActivity.class);
                startActivity(i);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> datas = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    datas.add("string-" + i);
                }
                ListDataSave datasave = new ListDataSave(MainActivity.this, "myview");
                datasave.setDataList("tag", datas);

                List<String> newMyList = datasave.getDataList("tag");
                for (String str : newMyList) {
                    Log.i("myView", "str = " + str);
                }
            }
        });
    }
}
