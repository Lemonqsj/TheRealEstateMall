package com.lemon.the_real_estate_mall.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
/**
 * @Auther: Lemon
 * @Date: 2018/4/18 16:39 42
 * @Describe: the infor of the class
 */
public class BaseApplication extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // crach 收集
//        initCrach();

        //CrashHandler.getInstance().init(mContext);

    }

//    private void initCrach() {
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
//        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
//            public Map<String, String> onCrashHandleStart(int crashType, String errorType,
//                                                          String errorMessage, String errorStack) {
//
//                restartApp();
//                // 退出程序
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
//                return null;
//            }
//
//            @Override
//            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
//                                                           String errorMessage, String errorStack) {
//                try {
//                    return "Extra data.".getBytes("UTF-8");
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//
//        });
//
//        CrashReport.initCrashReport(getApplicationContext(), "f10c51a701", false);
//    }

    //解决 荣耀6 java.lang.VerifyError: io/netty/util/concurrent/MultithreadEventExecutorGroup异常
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return mContext;
    }


    // 重启应用
    public  void restartApp() {
        ComponentName componentName = new ComponentName("com.kc.kchelmetupdateservice", "com.kc.kchelmetupdateservice.MainActivity");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        Log.e("APP--", "----CrashHandler--------- : ");
    }
}
