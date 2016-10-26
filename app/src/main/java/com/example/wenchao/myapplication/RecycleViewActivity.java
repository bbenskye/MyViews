package com.example.wenchao.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/9/22.
 *
 * @author wenchao
 * @since 1.0
 */
public class RecycleViewActivity extends Activity {


    private RecyclerView mRv;
    private List<String> mDatas;
    private MyRecyclerAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        initData();
        initView();
    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.recyclerview);
        recycleAdapter = new MyRecyclerAdapter(this, mDatas);
/**
 * 垂直listview布局
 */
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //设置为垂直布局，这也是默认的
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //添加分隔线
//        mRv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

/**
 * 网格gridlayout布局
 */
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
//        mRv.addItemDecoration(new DividerGridItemDecoration(this));

/**
 * 流布局（可以横向滚动）
 */
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL);
        mRv.addItemDecoration(new DividerGridItemDecoration(this));

        //设置布局管理器
        mRv.setLayoutManager(layoutManager);
        //设置Adapter
        mRv.setAdapter(recycleAdapter);
        //设置增加或删除条目的动画
        mRv.setItemAnimator(new DefaultItemAnimator());

        recycleAdapter.setClickedListener(new MyRecyclerAdapter.OnMyItemClickedListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(RecycleViewActivity.this, "click!-" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(RecycleViewActivity.this, "long click!-" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 80; i++) {
            mDatas.add("item" + i);
        }
    }
}
