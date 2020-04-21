package com.lemon.the_real_estate_mall.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lemon.the_real_estate_mall.BuildConfig;
import com.lemon.the_real_estate_mall.base.BaseApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Pattern;


public class LogUtils {



    private static final int TYPE_VERBOSE=0;
    private static final int TYPE_DEBUG=1;
    private static final int TYPE_INFO=2;
    private static final int TYPE_WARN=3;
    private static final int TYPE_ERROR=4;
    private static final int TYPE_ASSERT=5;
    private static final String NULL = "null";
    private static final int MAX_LENGTH = 4000;
    private static final String ARGUMENTS = "argument";


    private static final String TRACE_CLASS_LOGGER_FLAG = "LogUtils.java";
    private static final String SUFFIX_JAVA = ".java";
    private static final String ANONYMITY_JAVA_FLAG = "$";


    private static final boolean isDebug=BuildConfig.isDeBug;
    private static final String TRACE_CLASS_END = "com.lemon.the_real_estate_mall.utils.LogUtils";
    static Context context;
    static final String logTag = getAppName() ;

    public static void v(Object arg) {
        if (isDebug)
      printLog(TYPE_VERBOSE,arg);
    }
    public static void d(Object arg) {
        if (isDebug)
        printLog(TYPE_DEBUG,arg);

    }
    public static void e(Object arg) {
        if (isDebug)
        printLog(TYPE_ERROR,arg);

    }

    public static void i(Object arg) {
        if (isDebug)
        printLog(TYPE_INFO,arg);

    }





    private static void printLog(int type, Object... objects) {
        String headString = wrapperContent();
        String   message = headString + ((objects == null) ? NULL : getObjectsString(objects));
       log(type, message);
    }
    private static void log(int type, String msg) {
        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            //超长msg检测 处理
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                log(type, logTag, sub);
                index += MAX_LENGTH;
            }
            //除不尽的 打印剩余长度
            log(type, logTag, msg.substring(index, length));
        } else {
            log(type, logTag, msg);
        }
    }
    private static void log(int type, String tag, String sub) {
        switch (type) {
            case TYPE_VERBOSE:
                Log.v(tag, sub);
                break;
            case TYPE_DEBUG:
                Log.d(tag, sub);
                break;
            case TYPE_INFO:
                Log.i(tag, sub);
                break;
            case TYPE_WARN:
                Log.w(tag, sub);
                break;
            case TYPE_ERROR:
                Log.e(tag, sub);
                break;
            case TYPE_ASSERT:
                Log.wtf(tag, sub);
                break;
            default:
                Log.v(tag, sub);
                break;
        }
    }



    private static String getObjectsString(Object[] objects) {

        if (objects.length > 1) {
            StringBuilder builder = new StringBuilder();
            builder.append("\n");
            for (int i = 0, length = objects.length; i < length; i++) {
                Object object = objects[i];
                builder.append("\t").append(ARGUMENTS).append("[").append(i).append("]").append("=");
                if (object == null) {
                    builder.append(NULL);
                } else {
                    builder.append(checkArray(object) ? getArrayString(object) : object.toString());
                }
                if (i != length - 1) {
                    builder.append("\n");
                }
            }
            return builder.toString();
        } else {
            Object object = objects[0];
            if (object == null) {
                return NULL;
            } else {
                return checkArray(object) ? getArrayString(object) : object.toString();
            }
        }
    }

    private static boolean checkArray(Object object) {
        return object.getClass().isArray();
    }

    private static String getArrayString(Object object) {
        Class<?> componentType = object.getClass().getComponentType();
        if (componentType == null) {
            return object.toString();
        }

        if (int.class == componentType) {
            return Arrays.toString((int[]) object);
        } else if (long.class == componentType) {
            return Arrays.toString((long[]) object);
        } else if (float.class == componentType) {
            return Arrays.toString((float[]) object);
        } else if (double.class == componentType) {
            return Arrays.toString((double[]) object);
        } else if (short.class == componentType) {
            return Arrays.toString((short[]) object);
        } else if (byte.class == componentType) {
            return Arrays.toString((byte[]) object);
        } else if (boolean.class == componentType) {
            return Arrays.toString((boolean[]) object);
        } else if (char.class == componentType) {
            return Arrays.toString((char[]) object);
        } else {
            return Arrays.toString((Object[]) object);
        }
    }

    private static String wrapperContent() {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        int stackTraceIndex = findInvokeStackIndex(stackTraceElements);

        StackTraceElement targetElement = stackTraceElements[stackTraceIndex];
        String classFileName = targetElement.getFileName();
        String className = targetElement.getClassName();

        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX_JAVA;
        }

        String innerClassName = null;
        if (!className.equals(classFileName) && className.contains(ANONYMITY_JAVA_FLAG)) {
            //内部类
            int index = className.indexOf(ANONYMITY_JAVA_FLAG);
            innerClassName = className.substring(index);
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        return "[ ("
                + classFileName
                + ':'
                + lineNumber
                + ')'
                + (innerClassName == null ? "#" : innerClassName + "#")
                + methodName
                + " ] ";
    }




    private static int findInvokeStackIndex(StackTraceElement[] stackTraceElements) {
        //调用栈逆序 遍历
        for (int i = stackTraceElements.length - 1; i >= 0; i--) {
            StackTraceElement traceElement = stackTraceElements[i];
            if (TRACE_CLASS_LOGGER_FLAG.equals(traceElement.getFileName())) {
                return i + 1;
            }
        }
        return 0;
    }


    private static  String getAppName() {
        context = BaseApplication.getAppContext();

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}



