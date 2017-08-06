package com.zcx.zxflowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Andy on 2017/8/6.
 * 自定义 收藏表签，
 * 感谢 ALIOUS 给出的示例
 */

public class ZXFlowLayout extends ViewGroup {
    private static final String TAG = "ZXFlowLayout";

    public ZXFlowLayout(Context context) {
        super(context);
    }

    public ZXFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZXFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            //viewGroup中所有的child
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                widthSize,
                heightSize
        );
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int paddingLeft = getPaddingLeft();
        //允许控件所使用的宽度
        int useLineWidth = right - left;
        int lineX = 0;
        int lineY = 0;
        for (int i = 0; i < getChildCount(); i++) {
            int mLeft = 0;
            int mRight = 0;
            int mTop = 0;
            int mBottom = 0;
            int spaceWidth = 0;
            int spaceHeight = 0;
            View childView = getChildAt(i);
            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();

            ViewGroup.LayoutParams childLP = childView.getLayoutParams();
            //是否包含margin属性
            if (childLP instanceof MarginLayoutParams) {
                MarginLayoutParams m = (MarginLayoutParams) childLP;
                spaceWidth = m.leftMargin + m.rightMargin;
                spaceHeight = m.topMargin + m.bottomMargin;
                mLeft = m.leftMargin + lineX;
                mTop = m.topMargin + lineY;
                mRight = m.leftMargin + lineX + childViewWidth;
                mBottom = m.topMargin + lineY + childViewHeight;
            } else {
                throw new IllegalArgumentException("you must set layoutparams for childview!for example:" +
                        "ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);\n" +
                        "               lp.setMargins(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), 0);\n" +
                        "                TextView tv = new TextView(this);\n" +
                        "                tv.setPadding(dip2px(this, 15), 0, dip2px(this, 15), 0);" +
                        " flowLayout.addView(tv, lp);");
            }

            //计算当前所有组件的宽度是否大于手机屏幕的宽度
            if (lineX + childViewWidth + spaceWidth > useLineWidth) {
                lineX = paddingLeft;
                lineY += (spaceHeight + childViewHeight);
                //是否包含margin属性
                if (childLP instanceof MarginLayoutParams) {
                    MarginLayoutParams m = (MarginLayoutParams) childLP;
                    mLeft = m.leftMargin + lineX;
                    mTop = m.topMargin + lineY;
                    mRight = m.leftMargin + lineX + childViewWidth;
                    mBottom = m.topMargin + lineY + childViewHeight;
                } else {
                    throw new IllegalArgumentException("you must set layoutparams for childview!for example:" +
                            "ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);\n" +
                            "               lp.setMargins(dip2px(this, 10), dip2px(this, 10), dip2px(this, 10), 0);\n" +
                            "                TextView tv = new TextView(this);\n" +
                            "                tv.setPadding(dip2px(this, 15), 0, dip2px(this, 15), 0);" +
                            " flowLayout.addView(tv, lp);");
                }
            }
            childView.layout(mLeft, mTop, mRight, mBottom);
            childViewWidth += spaceWidth;
            lineX += childViewWidth;
        }
    }
}
