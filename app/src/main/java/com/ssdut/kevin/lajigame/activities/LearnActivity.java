package com.ssdut.kevin.lajigame.activities;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.flavienlaurent.discrollview.lib.DiscrollView;
import com.ssdut.kevin.lajigame.R;
import com.ssdut.kevin.lajigame.library.ColorAnimationView;

/**
 * Created by Kevin on 2018/3/7.
 */

public class LearnActivity extends FragmentActivity {

    private final static  int[] pages =new int[]{R.layout.page1,R.layout.page2,R.layout.page3,R.layout.page4};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_learn);
        MyFragmentStatePager adapter = new MyFragmentStatePager(getSupportFragmentManager());
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        colorAnimationView.setmViewPager(viewPager,pages.length,0xffb3c792,0xffe0cb6b,0xffa63a52,0xff509a9d);
    }
    public class MyFragmentStatePager
            extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return new MyFragment(position);
        }

        @Override public int getCount() {
            return pages.length;
        }
    }

    @SuppressLint("ValidFragment") public static class MyFragment
            extends Fragment {
        private int position;

        public MyFragment(int position) {
            this.position = position;
        }

        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.e("inflater",position+"");
            ScrollView discrollView = (ScrollView) inflater.inflate(pages[position],null);
            return discrollView;
        }
    }
}
