package com.shao.app.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * p>Describe:计时器
 * p>Company:Dyne
 * p>@Author:zsp
 * p>Data:2019/1/11.
 */
public class TimeCountTool {
    private static TimeCountTool mInstance = new TimeCountTool();
    private long time;
    private Timer timer;
    private TimerTask timerTask;

    private TimeCountTool() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                }
            };
        }
    }

    public static TimeCountTool getInstance() {
        if (mInstance == null) {
            mInstance = new TimeCountTool();
        }
        return mInstance;
    }

    /**
     * 开始计时,可以重复调用，之前的时间不会被清零
     * 登录不成功不会统计
     */
    public void startCount() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    time++;
                }
            };
        }
        try {
            timer.schedule(timerTask, 100, 1000);
        } catch (Exception e) {
            Logger.d("MusicPlayTimeCountTool startCount Exception  " + e.getMessage());
        }
    }

    /**
     * 计时停止并返回当前总时间
     */
    public long stopCount() {
        try {
            if (timer != null) {
                timer.cancel();
                timer.purge();
                timer = null;
            }
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
        } catch (Exception e) {
            Logger.d("MusicPlayTimeCountTool stopCount Exception  " + e.getMessage());
        }

        return time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
