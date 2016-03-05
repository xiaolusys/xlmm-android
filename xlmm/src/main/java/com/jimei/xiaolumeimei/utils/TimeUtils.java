package com.jimei.xiaolumeimei.utils;

import android.util.Log;

/**
 * ***********************
 * author:raochen
 * time:2015/12/1.
 * name:时间计算工具类
 */
public final class TimeUtils {

    /**
     * 获得两个秒值之间天数
     * @param start（起始毫秒值）
     * @param end（结束毫秒值）
     * @return
     */
    public static int getDayCount(long start,long end){
        return (int) ((end-start)/60/60/24);
    }

    /**
     * 获取剩余秒数对应的字符串
     * @param time 秒数
     * @return
     */
    public static String getTimeLeft(int time){
        int day=time/86400;
        int hour=time%86400/3600;
        String hours=hour+"";
        if(hour<10&&hour>=0){
            hours="0"+hour;
        }
        int min=time%86400%3600/60;
        String mins=min+"";
        if(min<10&&min>=0){
            mins="0"+min;
        }
        int second=time%86400%3600%60;
        String seconds=second+"";
        if(second<10&&second>=0){
            seconds="0"+second;
        }
        Log.i("oye","剩"+day+"天"+ hour+":"+min+":"+second);
        return "剩"+day+"天"+ hours+":"+mins+":"+seconds;
    }
}
