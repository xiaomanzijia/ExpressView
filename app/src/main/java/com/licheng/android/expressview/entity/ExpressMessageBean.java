package com.licheng.android.expressview.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 订单物流状态
 * Created by licheng on 22/2/17.
 */

public class ExpressMessageBean {


    /**
     * id : 493876
     * orderId : MOTZH170216000000013_1375
     * goodsId : MZH170215000000004
     * opContent : 您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 18827065959
     * buyerId : 502
     * isRead : false
     * createTime : 1487259871184
     * msgType : 2
     * flowState : 1
     * isFlowMsg : true
     * dataType : 1
     */

    @SerializedName("id")
    private int id;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("goodsId")
    private String goodsId;
    @SerializedName("opContent")
    private String opContent;
    @SerializedName("buyerId")
    private int buyerId;
    @SerializedName("isRead")
    private boolean isRead;
    @SerializedName("createTime")
    private long createTime;
    @SerializedName("msgType")
    private int msgType;
    @SerializedName("flowState")
    private int flowState;
    @SerializedName("isFlowMsg")
    private boolean isFlowMsg;
    @SerializedName("dataType")
    private int dataType;

    private String createTimeFormat;
    private String flowStateBtLeft;//左边按钮文字
    private String flowStateBtRight;//右边按钮文字

    public String getFlowStateBtLeft() {
        return flowStateBtLeft;
    }

    public void setFlowStateBtLeft(String flowStateBtLeft) {
        this.flowStateBtLeft = flowStateBtLeft;
    }

    public String getFlowStateBtRight() {
        return flowStateBtRight;
    }

    public void setFlowStateBtRight(String flowStateBtRight) {
        this.flowStateBtRight = flowStateBtRight;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getFlowState() {
        return flowState;
    }

    public void setFlowState(int flowState) {
        this.flowState = flowState;
    }

    public boolean isIsFlowMsg() {
        return isFlowMsg;
    }

    public void setIsFlowMsg(boolean isFlowMsg) {
        this.isFlowMsg = isFlowMsg;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
