package com.shao.app.views;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.shao.app.utils.DensityUtils;
import com.shao.app.utils.ImageUtils;
import com.shao.app.utils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:自动轮播的view
 * initView（初始化界面）
 * setAutoChangePage（是否开启轮播，默认开启）
 * setAutoChangePageTime（轮播的间隔时间，默认5000毫秒）
 * setOnImageClickListener（点击事件）
 * Company:
 * Author:Zhangshaopeng
 * Email :1377785991@qq.com
 * Data:2018/6/27
 */
public class BannerViewPager extends RelativeLayout {

    /**
     * 图片链接集合
     */
    private List<String> urlList;
    /**
     * 图片标题集合
     */
    private List<String> picTitleList;

    /**
     * 图片加载到控件时的显示类型
     */
    private ScaleType scaleType = ScaleType.CENTER_CROP;

    /**
     * 指定适配器的count，当图片数量大于1时为Integer的最大值。这样就可以在一定程度上无限的往后切换
     */
    private int picMaxCount;

    /**
     * 图片实际的数量
     */
    private int picRealCount;

    private ImageAdapter adapter;

    private ViewPager mViewPager;
    private LinearLayout ll_point;
    private TextView tv_picTitle;// 图片的说明

    /**
     * 是否自动切换
     */
    private boolean isAutoChangePage = true;

    /**
     * 自动切换的时间
     */
    private long autoChangePageTime = 5000;

    private Timer timer;
    private TimerTask task;

    private OnClickListener l;

    private Context context;

    private RelativeLayout layout;


    private static final String TAG = BannerViewPager.class.getName();

    /**
     * 当前图片的真实下标
     */
    private int currentRealIndex;


    public BannerViewPager(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUI();

    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initUI();

    }

    private void initUI() {

        layout = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.banner_view_pager, this, true);
        mViewPager = (ViewPager) layout.findViewById(R.id.viewPager);
        ll_point = (LinearLayout) layout.findViewById(R.id.ll_point);
        tv_picTitle = (TextView) layout.findViewById(R.id.tv_picTitle);

        urlList = new ArrayList<>();

    }

    public void initView(List<String> urlList) {
        initView(urlList, null, ScaleType.CENTER_CROP);
    }

    /**
     * 初始化界面
     *
     * @param urlList      要显示的图片链接集合
     * @param picTitleList 要显示的图片的标题集合，可以为null
     * @param scaleType    图片的缩放模式，为null时，会默认为FIT_XY
     */
    private void initView(List<String> urlList, List<String> picTitleList,
                          ScaleType scaleType) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        this.urlList.clear();
        this.ll_point.removeAllViews();// 清空下小圆点
        picMaxCount = 0;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        if (urlList == null || urlList.size() == 0) {
            Log.e(TAG, "传入的图片url集合对象为null或者size=0！");
            return;
        }
        this.urlList = urlList;
        this.picTitleList = picTitleList;
        if (scaleType != null) {
            this.scaleType = scaleType;
        }

        picRealCount = this.urlList.size();// 图片的数量等于链接的数量
        if (picRealCount == 1) {
            picMaxCount = 1;
        } else {
            picMaxCount = Integer.MAX_VALUE;
        }

        for (int i = 0; i < picRealCount; i++) {// 根据url的数量，动态的增加圆点,
            if (picRealCount > 1) {// 如果不止一张图片，才显示圆点
                ImageView point = new ImageView(context);// 小圆点
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        DensityUtils.dip2px(6), DensityUtils.dip2px(6));//小圆点直径为6dp
                point.setScaleType(ScaleType.CENTER_CROP);
                point.setImageResource(R.drawable.select_point_bg);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    // 每个圆点间隔5dp
                    params.leftMargin = DensityUtils.dip2px(5);
                    point.setEnabled(false);
                }
                ll_point.addView(point, params);
            }
        }
        adapter = new ImageAdapter();
        mViewPager.setAdapter(adapter);
        // 这样设置可以保证在刚开始的时候 用户从左往右滑动的时候 也能顺利滑动(而且显示的是第一张)
        mViewPager.setCurrentItem(picRealCount * 100);
        if (picTitleList != null && picTitleList.size() == picRealCount) {
            tv_picTitle.setVisibility(View.VISIBLE);
            tv_picTitle.setText(picTitleList.get(mViewPager.getCurrentItem()
                    % picRealCount));
        } else {
            tv_picTitle.setVisibility(View.GONE);
        }

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (null != BannerViewPager.this.picTitleList
                        && BannerViewPager.this.picTitleList.size() == picRealCount) {
                    tv_picTitle.setVisibility(View.VISIBLE);
                    tv_picTitle.setText(BannerViewPager.this.picTitleList
                            .get(arg0 % picRealCount));
                } else {
                    tv_picTitle.setVisibility(View.GONE);
                }
                currentRealIndex = arg0 % picRealCount;
                refreshPoint(currentRealIndex);// 图片切换后切换点
                if (changeListener != null) {
                    changeListener.onChange(currentRealIndex);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case 1:// 手势滑动，空闲中
                        isAutoChangePage = false;
                        break;
                    case 2:// 界面切换中
                        isAutoChangePage = false;
                        break;
                    case 0:// 滑动结束，即切换完毕或者加载完毕
                        isAutoChangePage = true;
                        break;
                }
            }
        });

		/*
         * 自动循环的几种实现方式： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、
		 * 用handler 发送延时信息，实现循环
		 */
        if (isAutoChangePage)
            autoChangePage();

    }

    /**
     * 自动切换图片 timer实现
     */
    private void autoChangePage() {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (picRealCount < 2)
            return;// 如果图片数量小于2张 ，就不轮播
        if (isAutoChangePage) {
            timer = new Timer();
            task = new TimerTask() {

                @Override
                public void run() {
                    if (isAutoChangePage) {
                        handler.sendEmptyMessage(mViewPager.getCurrentItem());
                    }
                }
            };

            // 执行一个任务, delay 延迟时间 ,period间隔时间
            timer.schedule(task, autoChangePageTime, autoChangePageTime);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(msg.what + 1);
        }
    };


    /**
     * 刷新圆点
     *
     * @param index
     */
    private void refreshPoint(int index) {

        if (picRealCount < 2)
            return; // 如果少于两张图，就直接return

        for (int i = 0; i < picRealCount; i++) {
            ll_point.getChildAt(i)
                    .setEnabled(false);
        }
        ll_point.getChildAt(index)
                .setEnabled(true);
    }


    /**
     * 图片的适配器
     */
    class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return picMaxCount;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int realPosition = position % picRealCount;// 图片真实的position
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(scaleType);
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (l != null) {
                        l.onClick(v, realPosition);
                    }
                }

            });

            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            ImageUtils.load(urlList.get(realPosition), iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }


    /**
     * 点击事件的回调接口
     *
     * @author Administrator
     */
    public interface OnClickListener {
        /**
         * @param v
         * @param position 图片的下标
         */
        void onClick(View v, int position);

    }

    public interface OnChangeListener {
        /**
         * @param position 图片的下标
         */
        void onChange(int position);

    }

    public OnChangeListener changeListener;

    /**
     * 设置页面切换监听
     *
     * @param changeListener
     */
    public void setOnImageChangeListener(OnChangeListener changeListener) {
        this.changeListener = changeListener;
    }


    /**
     * 设置点击事件
     *
     * @param l
     */
    public void setOnImageClickListener(OnClickListener l) {
        this.l = l;
    }

    /**
     * 设置是否自动切换图片 默认开启
     *
     * @param isAutoChangePage
     */
    public void setAutoChangePage(boolean isAutoChangePage) {
        this.isAutoChangePage = isAutoChangePage;
        if (isAutoChangePage)
            autoChangePage();
        else {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    /**
     * 设置切换图片的间隔时间，默认3000毫秒
     *
     * @param autoChangePageTime 间隔时间 毫秒单位
     */
    public void setAutoChangePageTime(long autoChangePageTime) {

        this.autoChangePageTime = autoChangePageTime;

        if (isAutoChangePage)
            autoChangePage();

    }

    /**
     * view销毁时调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        destroyBitmaps();
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }

    }

}
