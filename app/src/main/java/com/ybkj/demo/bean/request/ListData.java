package com.ybkj.demo.bean.request;

/**
 * author : ywh
 * date : 2019/1/25 15:55
 * description :
 */
public class ListData {
    private float x;
    private float y;

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

    public ListData(int x, int y) {

        this.x = x;
        this.y = y;
    }
}
