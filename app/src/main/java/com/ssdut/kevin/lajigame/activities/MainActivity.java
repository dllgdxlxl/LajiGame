package com.ssdut.kevin.lajigame.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssdut.kevin.lajigame.R;

import junit.framework.Test;


public class MainActivity extends BasicActivity implements View.OnClickListener {

    private ImageView about_btn;
    private ImageView learn_btn;
    private ImageView start_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        learn_btn = (ImageView)findViewById(R.id.learn_btn);
        about_btn = (ImageView)findViewById(R.id.about_btn);
        start_btn = (ImageView)findViewById(R.id.start_btn);
        learn_btn.setOnClickListener(this);
        about_btn.setOnClickListener(this);
        start_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.about_btn:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.learn_btn:
                startActivity(new Intent(this, LearnActivity.class));
                break;
            case R.id.start_btn:
                startActivity(new Intent(this,GameActivity.class));
                break;
            default:
                break;
        }
    }
}
