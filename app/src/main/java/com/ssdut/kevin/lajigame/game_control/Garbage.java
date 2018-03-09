package com.ssdut.kevin.lajigame.game_control;

/**
 * Created by Kevin on 2018/3/2.
 */

public class Garbage {
    public final static int RECOVERABLE=1;
    public final static int KITCHEN=2;
    public final static int HARMFUL=3;
    public final static int OTHER=4;
    public Garbage(){

    }
    private int score;
    private int image_resource;

    public void setPath(int path) {
        this.path = path;
    }

    public int getPath() {
        return path;
    }

    private int type;
    private int belong_path;
    private int path;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float x;
    private float y;

    public int getBelong_path() {
        return belong_path;
    }

    public void setBelong_path(int belong_path) {
        this.belong_path = belong_path;
    }

    public int getScore() {
        return score;
    }

    public int getImage_resource() {
        return image_resource;
    }

    public int getType() {
        return type;
    }


    public Garbage(int score, int image_resource, int type, int belong_path, int path) {
        this.score = score;
        this.image_resource = image_resource;
        this.type = type;
        this.belong_path = belong_path;
        this.path = path;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public void setImage_resource(int image_resource) {
        this.image_resource = image_resource;
    }

    public void setType(int type) {
        this.type = type;
    }

}
