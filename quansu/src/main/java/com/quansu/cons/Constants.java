package com.quansu.cons;

import android.graphics.Color;

/**
 * Created by xianguangjin on 15/12/10.
 */
public class Constants {
    /**
     * URL
     */
//    public static final String BASE_URL = "http://192.168.1.10/";
//    public static final String BASE_URL = "http://1688.muyoudaoli.com/";
//    public static final String BASE_URL = "http://192.168.1.168/";
//    public static final String BASE_URL = "http://192.168.1.103/";


      //  public static final String BASE_HOST = "api.mayinongchang.net";
    public static final String BASE_HOST = "need.quansuwangluo.com";
    public static final String BASE_HOST_FANGWEI = "need.quansuwangluo.com";
    public static final String QUANSU_HOST = "https://kefu.quansuwangluo.com";
      //  public static final String BASE_URL = "https://api.mayinongchang.net/";
    public static final String BASE_URL = "https://need.quansuwangluo.com/";
    public static final String BASE_URL_FANGWEI = "https://fangwei.quansuwangluo.com/";

    //沐熙大学-群聊
    public static final String BASE_URL_COLLEGE = "https://devchencollege.quansuwangluo.com/";
   // public static final String BASE_URL_COLLEGE = "https://college.quansuwangluo.com/";


    //团队管理-接口替换
    public static final String BASE_URL_TEAM = "https://need.quansuwangluo.com/";



    //    public static final String BASE_URL = "https://mynctest.quansuwangluo.com/";
      public static final String BASE_ENURL="http://en.mayinongchang.net/ServiceAPI/usercenter/";//蚂蚁农场-英语

    public static final String BASE_URL_MYNC = "http://k.mayinongchang.net/ServiceAPI/usercenter/";

    public static final String BASE_URL_MJD = "http://k.maijiduo.com/";

    public static final String BASE_URL_IMG = "http://www.zhaoyoukeji.com/";

    public static final String BASE_URL_AVATAR = "http://www.zhaoyoukeji.com/Uploads/avatar/";


//    public static final String BASE_URL = "http://192.168.1.103/zhaoyou/index.php/Home/Api/";
//    public static final String BASE_URL_IMG = "http://192.168.1.103/zhaoyou/";


    /**
     * OSS
     */

    public static final String ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com";
    public static final String ACCESSKEYID = "SlHZGvZokALYTUFY";
    public static final String ACCESSKEYSECRET = "RESZB5X36HIT8z5AeJhuAz4SukWcXp";
    public static final String Bucket = "zhaoyoukeji";
    public static final String OSS_PATH = "http://zhaoyoukeji.oss-cn-qingdao.aliyuncs.com/";
    public static final String OSS_IMGS = "http://zhaoyoukeji.oss-cn-qingdao.aliyuncs.com/imgs/";
    public static final String OSS_VIDEOS = "http://zhaoyoukeji.oss-cn-qingdao.aliyuncs.com/videos/";

    /**
     * COLOR
     */
    public final static int COLOR_BG = Color.parseColor("#FFFFFF");
    public final static int COLOR_TEXT = Color.parseColor("#96989b");
    public final static int COLOR_YELLOW = Color.parseColor("#ef9c00");
    public final static int COLOR_PRIMARY = Color.parseColor("#F9AFBD");
    public final static int COLOR_ACCENT = Color.parseColor("#8AF9EE");
    public final static int COLOR_TRANSPARENT_DEEP = Color.parseColor("#9B9B9B");

    /**
     * 拍照*选择照片
     */
    public static final int OK = 1;
    public static final int OK_TWO = 210;
    public static final int NO_RECORD = 202;

    public static final int FAIL = 201;
    public static final int TAKE_PIC = 20001;
    public static final int CHOOSE_PIC = 20002;
    public static final int CROP = 20003;
    public static final int REQUEST_CODE_CROP_IMAGE = 20004;

    /**
     * MOB
     */
    public static final String MOB_KEY = "d5572cbfc8b7";
    public static final String MOB_SECRET = "04e92d2c19317b1ab64cd60d5b571b28";
    public static final String DEFAULT_COUNTRY_ID = "86";
    /**
     * 密码
     */
    public static final String PWD_HEADER = "zhaoyou_pwd";
    public static final String PWD_END = "#-4Du(03.,u";


    /**
     * 选择视频和拍摄视频
     */
    public static final int CHOOSE_VIDEO = 20004;
    public static final int TAKE_VIDEO = 20005;

    /**
     * AdapterViewType
     */
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = -1;
    public static final int VIEW_TYPE_NORMAL = 1;


    /**
     * RequestCode
     */
    public static final int REQUEST_SELECT_CATE = 20006;
    /**
     * 支付
     */
    public static final int REQUEST_CODE_PAYMENT = 20007;
    public static final int REQUEST_GET_LIST = 20008;
    public static final int REQUEST_RED_WALLET = 10085;
    public static final int RESULT_RED_WALLET = 10086;
    public static final int GET_CONTACTS = 10087;

    /**
     * ResultCode
     */
    public static final int RESULT_ONE = 0;
    public static final int RESULT_TWO = 1;
    public static final int RESULT_THREE = 2;


    public static final int PAYWAY_ALIPAY = 1;
    public static final int PAYWAY_WX = 2;
    public static final int PAYWAY_ZYB = 3;
    public static final int PAYWAY_COD = 4;

    /**
     * 银联支付渠道
     */
    public static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    public static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    public static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    public static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    public static final String CHANNEL_JDPAY_WAP = "jdpay_wap";

    /**
     * 标识哪一个
     */
    public static final int WHITCH_DEFAUT = 0;
    public static final int WHITCH_ONE = 1;
    public static final int WHITCH_TWO = 2;
    public static final int WHITCH_THREE = 3;
    public static final int WHITCH_FOUR = 4;
    public static final int WHITCH_FIVE = 5;
    public static final int WHITCH_SIX = 6;
    public static final int WHITCH_SEVEN = 7;
    public static final int WHITCH_EIGHT = 8;


    public static final String WHITCH = "whitch";


    public static final Integer MSG_DEFAUT = 0;
    public static final Integer MSG_ONE = 1;
    public static final Integer MSG_TWO = 2;
    public static final Integer MSG_CLOSE_MAIN_ACTIVITY = 3;
    public static final int MSG_MSGCOUNT_CHANGE = 4;
    public static final int MSG_DELETE_FRIEND_SUCCESS = 5;
    public static final int MSG_REFRESH_CONDITION_LIST = 6;

    /**
     * 性别
     */
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_UN_KNOWN = 0;

    public static final String CACHE_DIR_PATH = "/hewuzhe/cache";
    public static final int MSG_LOC_SUCCESS = 20000;
    public static final int MSG_UPDATE_SORT = 20001;
    public static final int MSG_UPDATE_CAT = 20002;
    public static final int MSG_REFRESH_PLAN_LIST = 20003;

    public static final int MSG_CLOSE_LOGIN = 20004;

    public static final int UPLOAD_TYPE_LOCAL = 0;//本地视频
    public static final int UPLOAD_TYPE_RECORD = 1;//拍摄视频

    public static final String ZHAOZU_IMG = "/Uploads/img/zhaozu.jpg";
    public static final String ZHAOZU_URL = "";
    public static final int SELECT_SHOP = 1;
    public static final int NORMAL_SHOP = 2;
    //订单状态

    /**
     * 订单状态：1：待付款2：待发货3：待收货4：待评价，5：订单完成 6:退款、售后
     */
    public static final int STATUS_TO_PAY = 1;//待付款
    public static final int STATUS_TO_SHIPPING = 2;//待发货
    public static final int STATUS_TO_RECEIVE = 3;//待收货
    public static final int STATUS_TO_COMMENT = 4;//待评价
    public static final int STATUS_TO_COMPLETED = 5;//订单完成
    public static final int STATUS_TO_REFUND = 6;//待退款、售后

    /**
     * 推送消息类型
     */
    public static final int MSG_AD_TO = 1;
    public static final int MSG_AD_FROM = 2;
    public static final int MSG_DATE_TO = 3;
    public static final int MSG_DATE_FROM = 4;
    public static final int MSG_SEND_SUM = 5;
    public static final int MSG_UPDATE_AVATAR = 6;

    //付款的类型
    public static final int PAY_DATE_MYDATE = 2;
    public static final int PAY_DATE_AA = 1;
    public static final int PAY_SHOPPING = 3;
    public static final int PAY_BUY_ZYB = 4;
    public static final int PAY_PHONE_CHARGE = 5;

    public static final int YES = 1;
    public static final int NO = 2;


    public static int textColor = Color.parseColor("#96989b");
    public static int textBlack = Color.parseColor("#bacbd4");//#bacbd4
    public static int primary = Color.parseColor("#ff6977");

    /**
     * 错误
     */

    public static final int E_GET_DATA = 1;

    /**
     * 测试
     */
    public static final String TAG = "测试";
    public static final int IMG_COUNT = 11;
    public static final int STATUS_BAR_COLOR_ALPHA = 1;

    public static String SINGLE_CODE = "";


    public static long HOUR = 3600 * 1000;
    public static long MINUTE = 60 * 1000;
    public static long DAY = 3600 * 1000 * 24;
    public static long WEEK = 3600 * 1000 * 24 * 7;
    public static long MONTH = 3600 * 1000 * 24 * 30;


    /** 说明:
     *RL--->Refresh&LoadMore
     */

    //792 腾讯云  正式：36862
    public static final int ACCOUNT_TYPE = 36862;
    //sdk appid 由腾讯分配  正式： 1400150706
    public static final int SDK_APPID = 1400150706;
    //签名
    public static String USER_SIG = "";
    public static String AUDIO_GROUP_ID = "";//群组名
    public static String AUDIO_USER_ID = "";//用户名
    public static int AUDIO_ROOM_ID = 0;//房间名


}
