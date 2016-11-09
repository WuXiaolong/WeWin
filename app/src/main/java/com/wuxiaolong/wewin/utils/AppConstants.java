package com.wuxiaolong.wewin.utils;

import android.os.Environment;
import android.support.v4.app.Fragment;

import com.wuxiaolong.wewin.ui.juzimi.MainFragment;

public class AppConstants {
    public static final String COLOR = "color";
    public static final String ID = "id";
    public static final String TNGOU_GIRL_LIST = "TngouGirlList";
    public static String API_SERVER_IMAGE_URL = "http://tnfs.tngou.net/img";
    public static String IMAGE_URL = "imageUrl";
    public static final String TRANSIT_PIC = "picture";
    public static final String INDEX = "index";
    public static int USER_NICKNAME_LAYOUT = 1;
    public static int USER_EMAIL_LAYOUT = 2;
    public static int USER_INTRODUCTION_LAYOUT = 3;
    public static int USER_HEAD = 4;

    public static int PHOTO_CUTTING = 5;
    public static int REQUEST_CODE_USER_ALBUM = 10;// 验证码登录
    public static int BTN_PHOTOGRAPH = 13;// 验证码登录
    public static String SAVED_IMAGE_DIR_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/wewin/camera/";// 拍照路径

    public static int REQUEST_CODE_SETTINGS = 11;
    public static String APPKEY56 = "3000004972";
    public static String SECRET56 = "526f5ec9519c7205";
    public static int REQUEST_CODE_ADD = 14;

    public static String KEY_AD_MOGO = "84849cb4db40477eae961541fbde48b4";
    public static Fragment[] mFragments = {AppUtils.newInstance(new MainFragment())
    };
    public static String POSITION = "position";
    public static String WEBVIEW_TITLE = "webview_title";
    public static String WEBVIEW_URL = "webview_url";
    public static String WEBVIEW_URL_DATA = "webviewUrlData";
    public static String MY_Blog_URL = "http://wuxiaolong.me";
}
