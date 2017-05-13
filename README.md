#物流状态控件

------

## 介绍
一款简单的物流状态进度展示自定义View，仅供参考学习

## **效果图**
![image](https://github.com/xiaomanzijia/ExpressView/blob/master/screen/express.gif)

## **使用方法**

**布局文件**
```java
<ExpressView
        android:id="@+id/expressview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        express:circleToTextMargin="12dp"
        express:expressCircleOuterRadius="8dp"
        express:expressCircleRadius="6dp"
        express:expressTextMargin="12dp"
        express:expressTextSize="14sp"
        express:expressTextVecPadding="5dp"
        express:expressTimeTextSize="10sp"
        express:firstExpressCircleMarginLeft="16dp"
        express:firstExpressCircleMarginTop="16dp"
        express:isTimeButtonVisible="true" />
```

**控件属性介绍**

```java
firstExpressCircleMarginLeft 第一个物流状态点距离父控件坐边的间距
firstExpressCircleMarginTop 第一个物流状态点距离父控件上边的间距
expressCircleRadius 物流状态点内圈半径
expressCircleOuterRadius 物流状态点外圈半径
circleToTextMargin 物流状态提示圈到文字背景的距离
expressTextMargin 文字距离背景边距
expressTextVecPadding 每个物流信息竖直方向的间距
expressTextSize 文字大小
expressTimeTextSize 时间文字大小
isTimeButtonVisible 是否显示时间和文字按钮
```

**客户端**
```java
        //数据源
        final List<ExpressMessageBean> list = new ArrayList<>();
        ExpressMessageBean bean = new ExpressMessageBean();
        bean.setFlowState(1);
        bean.setFlowStateBtRight("购买流程");
        bean.setCreateTime(1487259871184l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259871184l));
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 189****2298");
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
        bean.setOpContent("您已付款0.1200元，购买 地下城与勇士/广东区/广东1区帐号，请联系卖家卡罗特将密保手机绑定您的手机号 189****2298");
        list.add(bean);
        bean = new ExpressMessageBean();
        bean.setFlowState(1);
        bean.setFlowStateBtRight("购买流程"); //设置右按钮文字
        bean.setCreateTime(1487259991260l);
        bean.setCreateTimeFormat(TimeUtils.millis2String(1487259991260l));
        bean.setOpContent("天空套 0.1200 1个-申请退款");
        list.add(bean);
        //数据源适配
        ExpressViewAdapter adapter = new ExpressViewAdapter<ExpressMessageBean>(list) {
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
        //处理点击事件
        expressView.setOnExpressItemButtonClickListener(new ExpressView.OnExpressItemButtonClickListener() {
            @Override
            public void onExpressItemButtonClick(int position, int status) {
                switch (list.get(position).getFlowState()){
                    case 1:
                        if(status == 1){ //购买流程
                            ToastUtil.ToastBottow(TestActivity.this, list.get(position).getFlowStateBtRight());
                        }
                        break;
                    case 4:
                        if(status == 0) { //同意退款
                            ToastUtil.ToastBottow(TestActivity.this, list.get(position).getFlowStateBtLeft());
                        } else if(status == 1){ //拒绝退款
                            ToastUtil.ToastBottow(TestActivity.this, list.get(position).getFlowStateBtRight());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
```

##待完善

```java
1、处理滑动冲突
2、处理滑动到顶部和到底部停止滑动的逻辑
3、实现弹性滑动的效果
```


##博客文章介绍
[http://www.jianshu.com/p/2d87f62d5d27](http://www.jianshu.com/p/2d87f62d5d27)


## License

    Copyright 2015 Cundong

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
