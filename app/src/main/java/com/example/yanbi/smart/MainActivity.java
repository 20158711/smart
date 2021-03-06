package com.example.yanbi.smart;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yanbi.smart.fragment.ButleFragment;
import com.example.yanbi.smart.fragment.GirlFragment;
import com.example.yanbi.smart.fragment.UserFragment;
import com.example.yanbi.smart.fragment.WechatFragment;
import com.example.yanbi.smart.ui.SettingActivity;
import com.example.yanbi.smart.utils.L;
import com.example.yanbi.smart.utils.ShareUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private List<String> mTitle;

    private List<Fragment> mFragment;

    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        initData();
        initView();

    }

    private void initView() {
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setVisibility(View.GONE);
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        fab_setting.setOnClickListener(this);

        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        mTitle=new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        mFragment=new ArrayList<>();
        mFragment.add(new ButleFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }
}
