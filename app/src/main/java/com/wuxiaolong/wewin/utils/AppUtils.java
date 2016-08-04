package com.wuxiaolong.wewin.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 常量配置/公用方法
 *
 * @author wxl
 * @since 2014.5.4.14：01
 */
public class AppUtils {
    public static int getPaletteColor(Bitmap bitmap) {
        int color = -12417291;
        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch vibrant = palette.getVibrantSwatch();
        Palette.Swatch vibrantdark = palette.getDarkVibrantSwatch();
        Palette.Swatch vibrantlight = palette.getLightVibrantSwatch();
        Palette.Swatch Muted = palette.getMutedSwatch();
        Palette.Swatch Muteddark = palette.getDarkMutedSwatch();
        Palette.Swatch Mutedlight = palette.getLightMutedSwatch();

        if (vibrant != null) {
            color = vibrant.getRgb();
        } else if (vibrantdark != null) {
            color = vibrantdark.getRgb();
        } else if (vibrantlight != null) {
            color = vibrantlight.getRgb();
        } else if (Muted != null) {
            color = Muted.getRgb();
        } else if (Muteddark != null) {
            color = Muteddark.getRgb();
        } else if (Mutedlight != null) {
            color = Mutedlight.getRgb();
        }
        return color;
    }

    /**
     * 关闭键盘事件
     *
     * @author shimiso
     * @update 2012-7-4 下午2:34:34
     */
    public static void closeInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 检查3G网络 0 无网络； 1 3G网络；2WiFi
     *
     * @return
     */
    public static int checkNetworkInfo(Activity activity) {
        ConnectivityManager conMan = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // mobile 3G Data Network
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        // wifi
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        // 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接

        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            return 1;
        } else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return 2;
        }

        return 0;

    }

    // 获取图片所在文件夹名称
    public static String getDir(String path) {
        String subString = path.substring(0, path.lastIndexOf('/'));
        return subString.substring(subString.lastIndexOf('/') + 1,
                subString.length());
    }


    /**
     * 版本号
     */
    public static String getVersionName(Activity activity) {
        PackageManager manager = activity.getPackageManager();
        String packageName = activity.getPackageName();
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            return "1.0";
        }
    }

    public static boolean isRunningForeground(Context context) {
        String packageName = getPackageName(context);
        String topActivityClassName = getTopActivityName(context);
        Log.d("wxl", "packageName=" + packageName + ",topActivityClassName="
                + topActivityClassName);
        if (packageName != null && topActivityClassName != null
                && topActivityClassName.startsWith(packageName)) {
            Log.d("wxl", "---> isRunningForeGround");
            return true;
        } else {
            Log.d("wxl", "---> isRunningBackGround");
            return false;
        }
    }

    public static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) (context
                .getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager
                .getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }

    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }


    /**
     * 去往市场下载
     *
     * @param activity
     * @param packageName
     */
    public static void marketDownload(Activity activity, String packageName) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=" + packageName));
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "未找到安卓市场", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 单个分享
     *
     * @param activity
     * @param share_content
     * @param _packageName
     */
    @SuppressLint("DefaultLocale")
    public static void initShareIntent(Activity activity, String share_content,
                                       String _packageName) {
        boolean found = false;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = activity.getPackageManager()
                .queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(
                        _packageName)
                        || info.activityInfo.name.toLowerCase().contains(
                        _packageName)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    share.putExtra(Intent.EXTRA_TEXT, share_content);

                    // share.putExtra(Intent.EXTRA_STREAM,
                    // Uri.fromFile(new File(myPath))); // Optional, just
                    // // if you wanna
                    // // share an
                    // // image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(activity, "未能找到该分享应用", Toast.LENGTH_LONG).show();
                return;
            }
            activity.startActivity(Intent.createChooser(share, "选择"));
        }
    }

    /**
     * 分享
     *
     * @param activity
     * @param share_content
     */
    public static void initShareIntent(Activity activity, String share_content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        List<ResolveInfo> resInfo = activity.getPackageManager()
                .queryIntentActivities(intent, 0);
        if (!resInfo.isEmpty()) {
            try {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    ActivityInfo activityInfo = info.activityInfo;

                    // judgments :activityInfo.packageName, activityInfo.name,
                    // etc.
                    // com.tencent.mm 微信
                    // com.qzone 空间
                    // com.tencent.WBlog 腾讯微博
                    // com.tencent.mobileqq qq
                    // com.renren.mobile.android 人人
                    // com.sina.weibo 人人
                    // im.yixin 易信
                    // jp.naver.line.android LINE
                    // com.xiaomi.channel 米聊
                    if (activityInfo.packageName.equals("com.tencent.WBlog")
                            || activityInfo.packageName
                            .equals("com.xiaomi.channel")
                            || activityInfo.packageName.equals("cn.com.fetion")
                            || activityInfo.packageName
                            .equals("jp.naver.line.android")) {
                        targeted.putExtra(Intent.EXTRA_TEXT, share_content);
                        targeted.setPackage(activityInfo.packageName);
                        targetedShareIntents.add(targeted);
                    }
                }
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0), "选择分享方式");
                if (chooserIntent == null) {
                    return;
                }
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);
            } catch (Exception ex) {
                // Toast.makeText(activity,
                // "Can't find sharecomponent to share",
                // Toast.LENGTH_SHORT).show();
                Toast.makeText(activity, "未能找到分享应用", Toast.LENGTH_LONG).show();
            }
        } else {
            // Toast.makeText(activity, "未找到分享应用", Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "未能找到分享应用", Toast.LENGTH_LONG).show();
        }
    }


    public static ProgressDialog createProgressDialog(Activity activity) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("玩命加载中...");
        progressDialog.show();
        return progressDialog;
    }

    public static Fragment newInstance(Fragment fragment) {
        if (fragment == null) {
            fragment = new Fragment();
        }
        return fragment;
    }

    public static void getWeekAndDay(final Activity activity) {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        // 获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
            week = week - 1;
        } else {
            day = day - 1;
        }
        switch (day) {
            case 2:
                initComment(activity);
                break;
            default:
                break;
        }
    }

    /**
     * 邀请评论
     */
    static SharedPreferences sharedPreferences;

    public static void initComment(final Activity activity) {
        sharedPreferences = activity.getSharedPreferences("commentTime",
                Context.MODE_PRIVATE);
        // getString()第二个参数为缺省值,如果preference中不存在该key,将返回缺省值
        long commentTime = sharedPreferences.getLong("commentTime", 1);
        if (System.currentTimeMillis() - commentTime > 3 * 24 * 60 * 60 * 1000) {

            AlertDialog.Builder builder = new Builder(activity);
            builder.setTitle("邀请");
            builder.setMessage("【剩者为王】邀请您来评论\n您的好评将是我们前进的动力。");
            builder.setPositiveButton("评论",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setComment(activity);
                            try {
                                Intent intent = new Intent(
                                        "android.intent.action.VIEW");
                                intent.setData(Uri
                                        .parse("market://details?id=com.xiaomolongstudio.wewin"));
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(activity, "未找到安卓市场",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            builder.setNegativeButton("下次",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setComment(activity);
                        }
                    });
            builder.create().show();

        }
    }

    public static void setComment(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("commentTime",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();// 获取编辑器
        editor.putLong("commentTime", Calendar.getInstance().getTimeInMillis());
        editor.commit();// 提交修改
    }
}
