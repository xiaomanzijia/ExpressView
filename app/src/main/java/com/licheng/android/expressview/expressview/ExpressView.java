package com.licheng.android.expressview.expressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.licheng.android.expressview.R;
import com.licheng.android.expressview.utils.DeviceUtils;
import com.licheng.android.expressview.utils.JsonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 物流状态View
 * Created by licheng on 19/2/17.
 */

public class ExpressView extends View implements ExpressViewAdapter.OnDataChangedListener {


    private int screenWidth;
    private int screenHeight;
    private int width, height; //当前View宽高
    private int viewWidth, viewHeight; //当前View宽高
    private int firstExpressCircleMarginLeft = DeviceUtils.dipToPx(getContext(), 16);
    private int firstExpressCircleMarginTop = DeviceUtils.dipToPx(getContext(), 40);

    private int expressCircleRadius = DeviceUtils.dipToPx(getContext(), 6);//物流状态提示圈半径
    private int expressCircleCurrentRadius = DeviceUtils.dipToPx(getContext(), 3);//物流状态提示圈半径
    private int expressCircleOuterRadius = DeviceUtils.dipToPx(getContext(), 8);//物流状态提示圈外半径

    private int circleToTextMargin = DeviceUtils.dipToPx(getContext(), 12);//物流状态提示圈到文字背景的距离

    private int expressTextBackgroundWidth; //文字背景宽
    private int expressTextMargin = DeviceUtils.dipToPx(getContext(), 8); //文字距离背景边距

    private int expressTextVecPadding = DeviceUtils.dipToPx(getContext(), 5); //每个物流信息竖直方向的间距

    private int expressTextToTimeTextPadding = DeviceUtils.dipToPx(getContext(), 6); //物流文字距离时间文字的间距

    private int expressButtonTextHeight; //按钮文字高度

    private boolean isTimeButtonVisible = false; //是否需要显示时间和按钮

    private Paint expressCirclePaint; //物流状态提示圈
    private Paint expressTextBackgroundPaint; //文字背景
    private TextPaint expressTextPaint; //文字
    private TextPaint timeTextPaint; //时间文字
    private TextPaint buttonTextPaint; //按钮文字
    private Paint bgPaint;
    private Paint expressLinePaint;//物流线条
    private int expressTextSize;//文字大小
    private int expressTimeTextSize;//时间文字大小

    private int contentDrawHeight; //根据内容绘制的高度

    private Map<Integer, Point> buttonTopPositionMap; //用于存储点击按钮左上角坐标
    private int firstButtonPositionY; //记录第一个按钮位置坐标

//    private int[] location = new int[2];

    private ExpressViewAdapter mAdapter;

    public ExpressView(Context context) {
        super(context);
        initPaint(context);
    }

    public ExpressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypeArray(context, attrs);
        initPaint(context);
    }

    public ExpressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypeArray(context, attrs);
        initPaint(context);
    }


    private void initTypeArray(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpressView);
        firstExpressCircleMarginLeft = (int) array.getDimension(R.styleable.ExpressView_firstExpressCircleMarginLeft, 16);
        firstExpressCircleMarginTop = (int) array.getDimension(R.styleable.ExpressView_firstExpressCircleMarginTop, 16);
        expressCircleRadius = (int) array.getDimension(R.styleable.ExpressView_expressCircleRadius, 6);
        expressCircleOuterRadius = (int) array.getDimension(R.styleable.ExpressView_expressCircleOuterRadius, 8);
        circleToTextMargin = (int) array.getDimension(R.styleable.ExpressView_circleToTextMargin, 12);
        expressTextMargin = (int) array.getDimension(R.styleable.ExpressView_expressTextMargin, 8);
        expressTextVecPadding = (int) array.getDimension(R.styleable.ExpressView_expressTextVecPadding, 5);
        expressTextSize = (int) array.getDimension(R.styleable.ExpressView_expressTextSize, 16);
        expressTimeTextSize = (int) array.getDimension(R.styleable.ExpressView_expressTimeTextSize, 16);
        isTimeButtonVisible = array.getBoolean(R.styleable.ExpressView_isTimeButtonVisible, false);
        array.recycle();

        buttonTopPositionMap = new HashMap<>();
        buttonTopPositionMap.put(0, new Point(0, 0)); //设置初始值
    }

    private void initPaint(Context context) {

        touchDistance = ViewConfiguration.get(context).getScaledTouchSlop();

        screenHeight = DeviceUtils.getScreenHeight(context);
        screenWidth = DeviceUtils.getScreenWidth(context);

        //物流状态提示圈
        expressCirclePaint = new Paint();
        expressCirclePaint.setColor(Color.parseColor("#969696"));
        expressCirclePaint.setStyle(Paint.Style.FILL);
        expressCirclePaint.setAntiAlias(true);
        expressCirclePaint.setStrokeWidth(DeviceUtils.dipToPx(getContext(), 2));

        expressTextBackgroundPaint = new Paint();
        expressTextBackgroundPaint.setAntiAlias(true);
        expressTextBackgroundPaint.setColor(Color.WHITE);
        expressTextBackgroundPaint.setStyle(Paint.Style.FILL);
        expressCirclePaint.setStrokeWidth(DeviceUtils.dipToPx(getContext(), 2));

        expressTextPaint = new TextPaint();
        expressTextPaint.setAntiAlias(true);
        expressTextPaint.setColor(Color.BLACK);
        expressTextPaint.setTextSize(expressTextSize);
        expressTextPaint.setStyle(Paint.Style.FILL);

        timeTextPaint = new TextPaint();
        timeTextPaint.setAntiAlias(true);
        timeTextPaint.setColor(Color.parseColor("#969696"));
        timeTextPaint.setTextSize(expressTimeTextSize);
        timeTextPaint.setStyle(Paint.Style.FILL);

        buttonTextPaint = new TextPaint();
        buttonTextPaint.setAntiAlias(true);
        buttonTextPaint.setColor(Color.parseColor("#4682B4"));
        buttonTextPaint.setTextSize(expressTextSize);
        buttonTextPaint.setStyle(Paint.Style.FILL);


        expressLinePaint = new Paint();
        expressLinePaint.setAntiAlias(true);
        expressLinePaint.setColor(Color.parseColor("#969696"));
        expressLinePaint.setStyle(Paint.Style.FILL);
        expressLinePaint.setStrokeWidth(DeviceUtils.dipToPx(getContext(), 1));

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setAlpha(30);
        bgPaint.setColor(Color.parseColor("#969696"));
        bgPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        Log.e("ExpressView", "当前View的width " + width + "  height " + height);

//        getLocationOnScreen(location);
//        Log.e("ExpressViewOnScreen", location[0] + "  " + location[1]);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.e("ExpressView", "屏幕宽高 " + screenWidth + "  " + screenHeight);

        if (modeWidth == MeasureSpec.EXACTLY) {
            viewWidth = sizeWidth;
            Log.e("ExpressView", "精确测量宽 " + viewWidth);
        } else {
            viewWidth = width;
            Log.e("ExpressView", "粗略测量宽 " + viewWidth);
        }

        if (modeHeight == MeasureSpec.EXACTLY) {
            viewHeight = sizeHeight;
            Log.e("ExpressView", "精确测量高 " + viewHeight);
        } else {
            viewHeight = height;
            Log.e("ExpressView", "粗略测量高 " + viewHeight);
        }


        viewWidth = viewWidth > screenWidth ? screenWidth : viewWidth;
        viewHeight = viewHeight > screenHeight ? screenHeight : viewHeight;

        setMeasuredDimension(viewWidth, viewHeight);

        expressTextBackgroundWidth = viewWidth - 2 * (firstExpressCircleMarginLeft - expressCircleRadius) - 2 * circleToTextMargin;
        Log.e("ExpressView", "View宽度 " + viewWidth + "绘制的文字背景宽度 " + expressTextBackgroundWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {


        int expressTextBgHeightSum = 0;
        int firstCirclePoint = 0, lastCirclePoint = 0; //记录第一个绘制和最后一个绘制的点位置

        if (isAdapterNull()) {

            for (int i = 0; i < mAdapter.getCount(); i++) {
                //物流文字高度测量

                ExpressViewData expressViewData = mAdapter.bindData(this, i, mAdapter.getItem(i));

                String text = expressViewData.getContent();
                StaticLayout layout = new StaticLayout(text, expressTextPaint, expressTextBackgroundWidth - 2 * expressTextMargin, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                int textHeight = layout.getHeight();//计算文字高度
                int expressTextBgHeight = textHeight + 2 * expressTextMargin;

                //时间文字高度测量
                String timeText = "2017-02-16 23:44:31";
                StaticLayout timeLayout = new StaticLayout(timeText, timeTextPaint, (expressTextBackgroundWidth - 2 * expressTextMargin) / 2, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                int timeTextHeight = timeLayout.getHeight();
                String timeShowText = expressViewData.getTime();

                //按钮文字高度测量
                String buttonLeft = expressViewData.getLeftBtnText();
                String buttonRight = expressViewData.getRightBtnText();
                String buttoTxt = "立即购买";

                StaticLayout buttonLayout = new StaticLayout(buttoTxt, buttonTextPaint, (expressTextBackgroundWidth - 2 * expressTextMargin) / 4, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                int buttonTextHeight = buttonLayout.getHeight();
                int buttonTextWidth = buttonLayout.getWidth();

                expressButtonTextHeight = buttonTextHeight;
                timeTextHeight = Math.max(timeTextHeight, buttonTextHeight);


                if (!isTimeButtonVisible) {
                    timeTextHeight = 0;
                    expressTextToTimeTextPadding = 0;
                }


                //绘制提示圆圈
                canvas.save();
                if (i == 0) {
                    expressCirclePaint.setColor(Color.parseColor("#D2691E"));
                    canvas.drawCircle(firstExpressCircleMarginLeft,
                            firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i,
                            expressCircleOuterRadius,
                            expressCirclePaint);
                    expressCirclePaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawCircle(firstExpressCircleMarginLeft,
                            firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i,
                            expressCircleCurrentRadius,
                            expressCirclePaint);
                } else {
                    expressCirclePaint.setColor(Color.parseColor("#969696"));
                    canvas.drawCircle(firstExpressCircleMarginLeft,
                            firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i,
                            expressCircleRadius,
                            expressCirclePaint);
                }
                canvas.restore();

                //获取第一个提示点和最后一个提示点坐标
                if (i == 0)
                    firstCirclePoint = firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i;
                if (i == mAdapter.getCount() - 1)
                    lastCirclePoint = firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i;

                //绘制文字背景
                canvas.save();
                if (i == 0) {
                    canvas.translate(firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius,
                            firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i);
                    expressTextBackgroundPaint.setColor(Color.parseColor("#D2691E"));
                    canvas.drawRect(0, 0, expressTextBackgroundWidth, expressTextBgHeight + expressTextToTimeTextPadding + timeTextHeight, expressTextBackgroundPaint);
                    expressTextBackgroundPaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawRect(2, 2, expressTextBackgroundWidth - 2, expressTextBgHeight + expressTextToTimeTextPadding + timeTextHeight - 2, expressTextBackgroundPaint);
                } else {
                    canvas.translate(firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius,
                            firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i);
                    expressTextBackgroundPaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawRect(0, 0, expressTextBackgroundWidth, expressTextBgHeight + expressTextToTimeTextPadding + timeTextHeight, expressTextBackgroundPaint);
                }

                if (i == mAdapter.getCount() - 1) { //记录最后一个文字背景的坐标位置
                    contentDrawHeight = firstExpressCircleMarginTop + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + expressTextBgHeight + expressTextToTimeTextPadding + timeTextHeight + expressTextVecPadding;
                    Log.e("ExpressView", "最后一个文字背景坐标 " + contentDrawHeight);
                }

                canvas.restore();

                //绘制物流文字
                drawLeftButton(canvas, layout, firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius + expressTextMargin, firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i);

                if (isTimeButtonVisible) {
                    //绘制时间文字
                    if (!TextUtils.isEmpty(timeShowText)) {
                        StaticLayout tl = new StaticLayout(timeShowText, timeTextPaint, (expressTextBackgroundWidth - 2 * expressTextMargin) / 2, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                        drawLeftButton(canvas, tl, firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius + expressTextMargin, firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + textHeight + expressTextToTimeTextPadding);
                    }

                    //绘制左边按钮
                    if (!TextUtils.isEmpty(buttonLeft)) {
                        StaticLayout sll = new StaticLayout(buttonLeft, buttonTextPaint, (expressTextBackgroundWidth - 2 * expressTextMargin) / 4, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                        drawLeftButton(canvas, sll, firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius + (expressTextBackgroundWidth - 2 * expressTextMargin) * 8 / 15, firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + textHeight + expressTextToTimeTextPadding);
                    }

                    //绘制右边按钮
                    if (!TextUtils.isEmpty(buttonRight)) {
                        StaticLayout sll = new StaticLayout(buttonRight, buttonTextPaint, (expressTextBackgroundWidth - 2 * expressTextMargin) / 4, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                        drawRightButton(canvas, expressTextBgHeightSum, i, textHeight, sll, buttonTextWidth);
                    }
                }

                if (i == 0)
                    firstButtonPositionY = firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + textHeight + expressTextToTimeTextPadding; //记录第一按钮位置坐标

                //存储左边按钮坐标
                Point point = new Point();
                point.set(firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius + (expressTextBackgroundWidth - 2 * expressTextMargin) * 8 / 15, firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + textHeight + expressTextToTimeTextPadding);
                buttonTopPositionMap.put(i, point);

                expressTextBgHeightSum += (expressTextBgHeight + timeTextHeight);

            }

        }


        if (isAdapterNull()) {
            canvas.save();
            canvas.drawLine(firstExpressCircleMarginLeft, firstCirclePoint + expressCircleOuterRadius, firstExpressCircleMarginLeft, lastCirclePoint, expressLinePaint);
            canvas.restore();
        }

        Log.e("ExpressView", "按钮位置信息 " + JsonUtils.objectToString(buttonTopPositionMap, Map.class));
    }

    //绘制左边边按钮
    private void drawLeftButton(Canvas canvas, StaticLayout buttonLayout, int dx, int dy) {
        canvas.save();
        canvas.translate(dx, dy);
        buttonLayout.draw(canvas);
        canvas.restore();
    }

    //绘制右边按钮
    private void drawRightButton(Canvas canvas, int expressTextBgHeightSum, int i, int textHeight, StaticLayout buttonLayout, int buttonTextWidth) {
        drawLeftButton(canvas, buttonLayout, firstExpressCircleMarginLeft + circleToTextMargin + expressCircleRadius + (expressTextBackgroundWidth - 2 * expressTextMargin) * 8 / 15 + buttonTextWidth, firstExpressCircleMarginTop + expressTextMargin + expressTextBgHeightSum + (expressTextVecPadding + expressTextToTimeTextPadding) * i + textHeight + expressTextToTimeTextPadding);
    }

    /**
     * 判断适配器是否为null
     *
     * @return
     */
    private boolean isAdapterNull() {
        return null != mAdapter;
    }

    int downY = 0;
    int lastY = 0;
    int lastMoveY = 0;
    int transDistance; //累计滑动距离
    int touchDistance; //系统判定滑动的最小距离
    boolean isMoving = false; //是否滑动

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                lastY = downY;
                lastMoveY = downY;
                isMoving = false;

                break;
            case MotionEvent.ACTION_MOVE:
                downY = (int) event.getY();
                int transY = downY - lastY;
                int transMoveY = downY - lastMoveY; //每次手指按下到滑动停止的滑动距离
                isMoving = Math.abs(transMoveY) > touchDistance ? true : false; //判定是否滑动
                Log.e("ExpressViewTouch", "当前滑动距离 " + Math.abs(transMoveY) + " 是否滑动 " + isMoving);
                if (isMoving) {
                    transDistance += transY;
                    Log.e("ExpressViewOnScreen", "滑动距离" + transDistance);
                    scrollBy(0, -transY);
//                    if (Math.abs(transDistance) <= contentDrawHeight - screenHeight + location[1] && Math.abs(transDistance) > 0) {
//                        scrollBy(0, -transY);
//                    } else if (Math.abs(transDistance) == 0) {
//                        Log.e("ExpressViewOnScreen", "底部");
//                    } else {
//                        Log.e("ExpressViewOnScreen", "顶部");
//                    }
                }
                lastY = downY;
                break;
            case MotionEvent.ACTION_UP:
                if (isTimeButtonVisible) {
                    Log.e("ExpressViewTouch", "累计滑动总距离 " + transDistance);
                    if (isMoving || (!isMoving && (buttonTopPositionMap.get(0).y == firstButtonPositionY))) {
                        Iterator<Map.Entry<Integer, Point>> it = buttonTopPositionMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, Point> entry = it.next();
                            entry.getValue().y += transDistance;
                        }
                    }
                    Log.e("ExpressViewTouch", "手指离开屏幕位置信息 " + JsonUtils.objectToString(buttonTopPositionMap, Map.class));
                    onActionUpEvent(isMoving, event, buttonTopPositionMap);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    private void onActionUpEvent(boolean isMoving, MotionEvent event, Map<Integer, Point> maps) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        Iterator<Map.Entry<Integer, Point>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Point> entry = it.next();
            if (!isMoving) { //只有点击事件才检验点击是否有效并响应点击事件
                if (isBooleanXY1(touchX, touchY, entry)) {
                    onExpressItemButtonClickListener.onExpressItemButtonClick(entry.getKey(), 0);
                } else if (isBooleanXY2(touchX, touchY, entry)) {
                    onExpressItemButtonClickListener.onExpressItemButtonClick(entry.getKey(), 1);
                }
            }
        }
    }

    public void setTimeButtonVisible(boolean timeButtonVisible) {
        isTimeButtonVisible = timeButtonVisible;
    }

    private boolean isBooleanXY1(int touchX, int touchY, Map.Entry<Integer, Point> entry) {
        return isBooleanX1(touchX, entry) && isBooleanY(touchY, entry);
    }

    private boolean isBooleanY(int touchY, Map.Entry<Integer, Point> entry) {
        return touchY > entry.getValue().y && touchY < entry.getValue().y + expressButtonTextHeight;
    }

    private boolean isBooleanX1(int touchX, Map.Entry<Integer, Point> entry) {
        return touchX > entry.getValue().x && touchX < entry.getValue().x + (expressTextBackgroundWidth - 2 * expressTextMargin) / 4;
    }

    private boolean isBooleanXY2(int touchX, int touchY, Map.Entry<Integer, Point> entry) {
        return isBooleanX2(touchX, entry) && isBooleanY(touchY, entry);
    }

    private boolean isBooleanX2(int touchX, Map.Entry<Integer, Point> entry) {
        return touchX > entry.getValue().x + (expressTextBackgroundWidth - 2 * expressTextMargin) / 4 && touchX < entry.getValue().x + 2 * ((expressTextBackgroundWidth - 2 * expressTextMargin) / 4);
    }


    @Override
    public void onDataChanged() {
        invalidate();
    }

    public interface OnExpressItemButtonClickListener {
        void onExpressItemButtonClick(int position, int status);
    }

    private OnExpressItemButtonClickListener onExpressItemButtonClickListener;


    public void setOnExpressItemButtonClickListener(OnExpressItemButtonClickListener onExpressItemButtonClickListener) {
        this.onExpressItemButtonClickListener = onExpressItemButtonClickListener;
    }

    public void setAdapter(ExpressViewAdapter adapter){
        mAdapter = adapter;
        mAdapter.setOnDataChangedListener(this);
    }
}
