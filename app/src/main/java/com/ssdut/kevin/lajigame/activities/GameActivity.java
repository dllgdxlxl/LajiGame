package com.ssdut.kevin.lajigame.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssdut.kevin.lajigame.R;
import com.ssdut.kevin.lajigame.game_control.Garbage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kevin on 2018/3/1.
 */

public class GameActivity extends BasicActivity implements View.OnClickListener, View.OnTouchListener {

    private ImageView home;
    private ImageView[] lifes = new ImageView[3];
    private TextView score;
    private RelativeLayout game_linear;
    private List<Garbage> garbages;
    private int speed = 4;
    private ImageView content;
    private Garbage garbage;
    private int all_score;
    private int life = 3;
    private boolean isStart = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus&&isStart){
            content.setMaxWidth((int)game_linear.getWidth()/8);
            startGame();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        home = (ImageView)findViewById(R.id.game_home);
        home.setOnClickListener(this);
        score = (TextView)findViewById(R.id.game_score);
        lifes[0] = (ImageView)findViewById(R.id.life_image1);
        lifes[1] = (ImageView)findViewById(R.id.life_image2);
        lifes[2] = (ImageView)findViewById(R.id.life_image3);
        content = new ImageView(this);
        content.setScaleType(ImageView.ScaleType.FIT_CENTER);
        game_linear = (RelativeLayout)findViewById(R.id.game_linear);
        game_linear.setOnTouchListener(this);
        game_linear.addView(content);
        all_score=0;
        score.setText(all_score+"");
       Typeface mtypeface= Typeface.createFromAsset(getAssets(), "orange.TTF");
       score.setTypeface(mtypeface);
       can[0]=(ImageView)findViewById(R.id.kehuishou2);
       can[1]=(ImageView)findViewById(R.id.qita2);
       can[2]=(ImageView)findViewById(R.id.youhai2);
       can[3]=(ImageView)findViewById(R.id.chuyu2);
        initGarbage();
    }

    private final static int[] garbage_scores = new int[]{1,1,1,1,1,
                                                          2,2,2,
                                                          3,3,
                                                          1,1,1,1,1,1,
                                                          2,2,2,
                                                         1,2,3,
                                                         1,1,1,1,1,
                                                         2,3};
    private final static int[] garbage_paths = new int []{3,3,3,3,3,
                                                          3,3,3,
                                                          3,3,
                                                         0,0,0,0,0,0,
                                                         0,0,0,
                                                         1,1,1,
                                                         2,2,2,2,2,
                                                          2,2};
    private final static int[] garbage_resource=new int[]{R.drawable.a41_1,R.drawable.a41_2, R.drawable.a41_3,R.drawable.a41_4,R.drawable.a41_5,
                                                         R.drawable.a42_1,R.drawable.a42_2,R.drawable.a42_3,
                                                         R.drawable.a43_1,R.drawable.a43_2,
                                                         R.drawable.a11_1,R.drawable.a11_2,R.drawable.a11_3,R.drawable.a11_4,R.drawable.a11_5,R.drawable.a11_6,
                                                         R.drawable.a12_1,R.drawable.a12_2,R.drawable.a12_3,
                                                         R.drawable.a21_1,R.drawable.a22_1,R.drawable.a23_1,
                                                         R.drawable.a31_1,R.drawable.a31_2,R.drawable.a31_3,R.drawable.a31_4,R.drawable.a31_5,
                                                         R.drawable.a32_1,R.drawable.a33_1};

    private void initGarbage(){
        garbages = new ArrayList<Garbage>();
        for(int i=0;i<garbage_paths.length;i++){
            Garbage paper = new Garbage();
            paper.setImage_resource(garbage_resource[i]);
            paper.setBelong_path(garbage_paths[i]);
            paper.setScore(garbage_scores[i]);
            garbages.add(paper);
        }
    }
    private Garbage generateGarbage(){
        Random random = new Random();
        int index = random.nextInt(garbages.size());
        Garbage garbage = garbages.get(index);
        garbage.setPath(random.nextInt(4));
        return garbages.get(index);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private Thread gamethread;
    private void startGame(){
        gamethread = new Thread(new Runnable() {
            @Override
            public void run() {
                float height = game_linear.getHeight()-((ImageView)findViewById(R.id.lajitong)).getHeight();
                height-=(content.getHeight()+20);
                float h = ((ImageView)findViewById(R.id.qita2)).getWidth();
                while(true){
                    garbage = generateGarbage();
                    float startY = 0;
                    float startX = game_linear.getWidth()/4*garbage.getPath()
                             + 23;
                    garbage.setX(startX);
                    garbage.setY(startY);
                    speed = speed>1?speed-1:speed;
                    boolean isFailed = false;
                    long last_time = System.currentTimeMillis();
                    while(true){
                        long time = System.currentTimeMillis();
                        if(time-last_time<speed)
                            continue;
                        last_time=time;
                        if(isFinish)
                            break;
                        startY=startY+0.5f;
                        if(startY>height-h-5){
                            index = garbage.getPath();
                            handler.sendEmptyMessage(0x05);
                        }
                        if(startY>height-20) {
                            if(garbage.getPath() == garbage.getBelong_path()){
                                all_score+=garbage.getScore();
                                handler.sendEmptyMessage(0x02);
                            }else{
                                isFailed=true;
                            }
                           break;
                        }
                        garbage.setY(startY);
                        handler.sendEmptyMessage(0x01);
                }
                if(isFailed){
                        life--;
                        handler.sendEmptyMessage(0x04);
                        if(life<=0){
                            handler.sendEmptyMessage(0x03);
                            break;
                        }
                }
                if(isFinish)
                    break;
                }
            }
        });
        gamethread.start();
    }

    private boolean isFinish = false;
    @Override
    public void finish() {
        super.finish();
        isFinish = true;
    }

    private void setImageView(){
        content.setImageResource(garbage.getImage_resource());
        content.setScaleType(ImageView.ScaleType.FIT_CENTER);
        content.setMaxWidth(game_linear.getWidth()/20);
        content.setX(garbage.getX());
        content.setY(garbage.getY());
        content.invalidate();
        //Log.e("position",garbage.getY()+"");

    }

    private int index=0;

    private boolean[] isOpen = new boolean[4];

    private void changeLajiTong(int angle){
        for(int i=0;i<4;i++)
            if(isOpen[i]) {
                can[i].setPivotX(can[i].getWidth() - 15);
                can[i].setPivotY(can[i].getHeight() - 10);
                can[i].setRotation(angle);
                isOpen[i]=false;
            }
    }
    private ImageView[] can = new ImageView[4];
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 0x01){
                setImageView();
            }else if(msg.what == 0x02){
                score.setText(""+all_score);
                // 图片中心作为旋转的支点
                changeLajiTong(0);
            }else if(msg.what == 0x03){
                content.setVisibility(View.GONE);
                isStart=false;
                //Toast.makeText(GameActivity.this,"You Failed",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GameActivity.this,EndActivity.class);
                intent.putExtra("score",all_score+"");
                //Log.e("startEndActivity","");
                startActivity(intent);
                GameActivity.this.finish();
            }else if(msg.what == 0x04){
                lifes[life].setImageResource(R.drawable.heart_gray);
                changeLajiTong(0);
            }else if(msg.what == 0x05){
                isOpen[index]=true;
                can[index].setPivotX(can[index].getWidth()-15);
                can[index].setPivotY(can[index].getHeight()-10);
                can[index].setRotation(90);
            }
        };
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.game_home:
                finish();
                break;
            default:
                break;
        }
    }


    private void moveGarbage(float x, boolean is){
            float now_x = (x-touchX)/0.8f+startX;
            now_x = now_x<=0?1:now_x;
            now_x = now_x>=game_linear.getWidth()?game_linear.getWidth()-1:now_x;
            int path = (int)now_x/(game_linear.getWidth()/4);
            garbage.setPath(path);
            if(is)
              garbage.setX(game_linear.getWidth()/4*garbage.getPath()
                    +23);
            else
              garbage.setX(now_x);
            setImageView();
    }

    private float touchX=-1;
    private float startX= -1;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.game_linear && motionEvent.getX()>=view.getX()&&motionEvent.getY()>=view.getY()){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                  touchX=motionEvent.getX();
                  startX = garbage.getX();
                  content.setAlpha(0.5f);
                  //Toast.makeText(this,"Start Moving",Toast.LENGTH_SHORT).show();
            }if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                if(touchX>0){
                    content.setAlpha(1f);
                    moveGarbage(motionEvent.getX(),true);
                    touchX=-1;
                }
            }if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                moveGarbage(motionEvent.getX(),true);
            }
        }
        return true;
    }
}
