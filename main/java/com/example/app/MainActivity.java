package com.example.app;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.app.Main.Tab1Fragment;
import com.example.app.Main.Tab2Fragment;
import com.example.app.Main.Tab3Fragment;
import com.example.app.Utils.BottomAdapter;

/* 主函数 */
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBv;
    private ViewPager mVp;
    public MediaPlayer mp;
    public boolean sound = true;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 播放背景音乐
        mp = MediaPlayer.create(this, R.raw.bg);
        // 循环播放
        mp.setLooping(true);
        sp = this.getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.start();
        }
        if(!sp.getBoolean("sound",true)) {
            sound = false;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sp = this.getSharedPreferences("config", MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sp = this.getSharedPreferences("config", MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.start();
        }
    }
    private void initView() {
        mBv = findViewById(R.id.bv);
        mVp = findViewById(R.id.vp);
        mVp.setOffscreenPageLimit(2);
        mBv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //点击可以进入到下一界面
                    case R.id.tab1:
                        mVp.setCurrentItem(0);
                        break;
                    case R.id.tab2:
                        mVp.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

        //数据填充
        setupViewPager(mVp);
        //ViewPager监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());//添加界面
        adapter.addFragment(new Tab3Fragment());//添加界面
        viewPager.setAdapter(adapter);
    }
}
