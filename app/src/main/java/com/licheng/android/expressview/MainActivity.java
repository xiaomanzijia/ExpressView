package com.licheng.android.expressview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.licheng.android.expressview.entity.ExpressMessageBean;
import com.licheng.android.expressview.expressview.ExpressView;
import com.licheng.android.expressview.expressview.ExpressViewAdapter;
import com.licheng.android.expressview.expressview.ExpressViewData;
import com.licheng.android.expressview.utils.TimeUtils;
import com.licheng.android.expressview.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpressViewAdapter<ExpressMessageBean> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpressView expressView = (ExpressView) findViewById(R.id.expressview);


        final List<ExpressMessageBean> list = new ArrayList<>();
        ExpressMessageBean bean = new ExpressMessageBean();
        bean.setFlowState(1);
        bean.setFlowStateBtRight("购买流程");
        bean.setCreateTime(1487259871184l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259871184l));
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 18827065959");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(2);
        bean.setCreateTime(1487259991260l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
        bean.setOpContent("天空套 0.1200 1个-申请退款");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(3);
        bean.setCreateTime(1487259871184l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259871184l));
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 18827065959");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(4);
        bean.setFlowStateBtLeft("同意退款"); //设置左右按钮文字
        bean.setFlowStateBtRight("拒绝退款");
        bean.setCreateTime(1487259991260l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
        bean.setOpContent("天空套 0.1200 1个-申请退款");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(5);
        bean.setCreateTime(1487259871184l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259871184l));
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 18827065959");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(6);
        bean.setCreateTime(1487259991260l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
        bean.setOpContent("天空套 0.1200 1个-申请退款");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(7);
        bean.setCreateTime(1487259871184l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259871184l));
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 18827065959");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(1);
        bean.setFlowStateBtRight("购买流程"); //设置右按钮文字
        bean.setCreateTime(1487259991260l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
        bean.setOpContent("天空套 0.1200 1个-申请退款");
        list.add(bean);

        adapter = new ExpressViewAdapter<ExpressMessageBean>(list) {
            @Override
            public ExpressViewData bindData(ExpressView expressView, int position, ExpressMessageBean expressMessageBean) {
                ExpressViewData data = new ExpressViewData();
                data.setContent(expressMessageBean.getOpContent());
                data.setTime(expressMessageBean.getCreateTimeFormat());
                data.setLeftBtnText(expressMessageBean.getFlowStateBtLeft());
                data.setRightBtnText(expressMessageBean.getFlowStateBtRight());
                return data;
            }
        };

        expressView.setAdapter(adapter);
        adapter.notifyDataChanged();

        //延迟4秒添加2条数据
        expressView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ExpressMessageBean bean = new ExpressMessageBean();
                bean.setFlowState(1);
                bean.setFlowStateBtRight("购买流程"); //设置右按钮文字
                bean.setCreateTime(1487259991260l);
                bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
                bean.setOpContent("天空套 0.1200 1个-申请退款");
                list.add(bean);
                bean = new ExpressMessageBean();
                bean.setFlowState(1);
                bean.setFlowStateBtRight("购买流程"); //设置右按钮文字
                bean.setCreateTime(1487259991260l);
                bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
                bean.setOpContent("天空套 0.1200 1个-申请退款");
                list.add(bean);
                adapter.notifyDataChanged();
            }
        }, 4000);

        //处理点击事件
        expressView.setOnExpressItemButtonClickListener(new ExpressView.OnExpressItemButtonClickListener() {
            @Override
            public void onExpressItemButtonClick(int position, int status) {
                switch (list.get(position).getFlowState()){
                    case 1:
                        if(status == 1){ //购买流程
                            ToastUtil.ToastBottow(MainActivity.this, list.get(position).getFlowStateBtRight());
                        }
                        break;
                    case 4:
                        if(status == 0) { //同意退款
                            ToastUtil.ToastBottow(MainActivity.this, list.get(position).getFlowStateBtLeft());
                        } else if(status == 1){ //拒绝退款
                            ToastUtil.ToastBottow(MainActivity.this, list.get(position).getFlowStateBtRight());
                        }
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
