package com.licheng.android.expressview.expressview;

/**
 * 物流控件数据接口
 * Created by licheng on 19/3/17.
 */

public class ExpressViewData {
    private String content; //内容
    private String time; //时间
    private String leftBtnText; //左按钮文字
    private String rightBtnText; //右按钮文字



    @Override
    public String toString() {
        return "ExpressViewData{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", leftBtnText='" + leftBtnText + '\'' +
                ", rightBtnText='" + rightBtnText + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeftBtnText() {
        return leftBtnText;
    }

    public void setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
    }

    public String getRightBtnText() {
        return rightBtnText;
    }

    public void setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
