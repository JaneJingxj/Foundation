package com.quansu.utils;

import android.text.format.Time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 时间操作类
 */
public class TimeUtil {

    public static long MINUTE = 1000 * 60;
    public static long HOUR = 1000 * 60 * 60;
    public static long DAY = 1000 * 60 * 60 * 24;
    private static String hoursString = "";
    private static String minutesString = "";
    private static String secondsString = "";

    public static int getCurrentYear() {
        Time t = new Time();
        t.setToNow();
        return t.year;
    }

    public static int getCurrentMonth() {
        Time t = new Time();
        t.setToNow();
        return t.month + 1;
    }

    public static int getCurrentDay() {
        Time t = new Time();
        t.setToNow();
        return t.monthDay;
    }

    /**
     * @return 今天的格式化
     */
    public static String getCurrentDayFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        return format.format(currentTime);
    }

    /**
     * @param day
     *
     * @return day天之后的格式化
     */
    public static String getDayAfterFormat(int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        currentTime.setTime(currentTime.getTime() + DAY * day);
        return format.format(currentTime);
    }


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     *
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }


    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }


    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     *
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }


    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    public static Long getLongtime(String data) {//data格式 "2001-03-15 15:37:05";


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");//12小时制
        try {
            long time2 = simpleDateFormat.parse(data).getTime();
            return time2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    public static String timeAgo(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp) / 1000;

        long minutes = Math.abs(seconds / 60);
        long hours = Math.abs(minutes / 60);
        long days = Math.abs(hours / 24);


        if (seconds <= 15) {
            return "刚刚";
        } else if (seconds < 60) {
            return seconds + "秒前";
        } else if (seconds < 120) {
            return "1分钟前";
        } else if (minutes < 60) {
            return minutes + "分钟前";
        } else if (minutes < 120) {
            return "1小时前";
        } else if (hours < 24) {
            return hours + "小时前";
        } else if (hours < 24 * 2) {
            return "1天前";
        } else if (days < 30) {
            return days + "天前";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            return dateString;
        }

    }

    public static String timeLeft(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }


        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total_seconds = (timeStamp - currentTimeStamp) / 1000;

        if (total_seconds <= 0) {
            return "";
        }

        long days = Math.abs(total_seconds / (24 * 60 * 60));

        long hours = Math.abs((total_seconds - days * 24 * 60 * 60) / (60 * 60));
        long minutes = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60) / 60);
        long seconds = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60));
        String leftTime;
        if (days > 0) {
            leftTime = days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
        } else if (hours > 0) {
            leftTime = hours + "小时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0) {
            leftTime = minutes + "分" + seconds + "秒";
        } else if (seconds > 0) {
            leftTime = seconds + "秒";
        } else {
            leftTime = "0秒";
        }

        return leftTime;
    }


    /**
     * @param timeStr 已经过了多长时间
     *
     * @return
     */
    public static String timeHaved(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total = currentTimeStamp - timeStamp;

        if (total <= 0) {
            return "";
        }

        int years = Math.round(total / (DAY * 365));

        return years + "";
    }


    /**
     * @param timeStr 已经过了多长时间
     *
     * @return
     */
    public static String timeHavedDay(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total = currentTimeStamp - timeStamp;

        if (total <= 0) {
            return "";
        }

        int days = Math.round(total / (DAY));

        return days + "";
    }

    public static String timeFormat(long time) {
        Date date = null;


        long timeStamp = time;


        long total_seconds = timeStamp / 1000;

        if (total_seconds <= 0) {
            return "";
        }

        long hours = Math.abs(time / HOUR);
        long minutes = Math.abs((time - hours * HOUR) / MINUTE);
        long seconds = Math.abs((time - (minutes * MINUTE + hours * HOUR)) / 1000);

        if (hours < 10) {
            hoursString = "0" + hours;
        } else {
            hoursString = "" + hours;

        }
        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = "" + minutes;

        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = seconds + "";
        }
        String leftTime = hoursString + ":" + minutesString + ":" + secondsString;

        return leftTime;
    }


    public static String timeFormatTwo(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("yyyy-MM-dd");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String timeFormatThree(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("HH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String timeFormatFour(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("MM-dd HH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String timeFormatFive(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
            SimpleDateFormat formatReverse = new SimpleDateFormat("MM-dHH:mm");
            return formatReverse.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public static String getYMTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }


    public static String getHMTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    /**
     * @param timeStr
     *
     * @return 传入的比当前时间大为True
     */
    public static boolean timeComparedNow(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        return timeStamp > currentTimeStamp;
    }


    /**
     * @param timeStr
     *
     * @return 传入的比当前时间大为True
     */
    public static long timeGap(String timeStr) {
        Date date = null;
        if (StringUtil.isEmpty(timeStr)) {
            return 0;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        return timeStamp - currentTimeStamp;
    }


    /**
     * @param oldTime 时间戳
     * @param day 时间
     *
     * @return 是否过期
     */
    public static boolean isOverDue(long oldTime, int day) {
        return (System.currentTimeMillis() - oldTime) > TimeUnit.DAYS.toMillis(day);
    }

    /**
     * @param oldTime 某个时间
     *
     * @return
     */
    public static boolean isOverDue(long oldTime) {

        return (System.currentTimeMillis() - oldTime) > 0;
    }

    /**
     * @param
     * @return
     */

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


}
