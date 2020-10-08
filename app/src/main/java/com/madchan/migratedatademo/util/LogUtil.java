package com.madchan.migratedatademo.util;

import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * 日志工具类
 */
public class LogUtil {

    public static void init(final boolean isDebugMode) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // （可选）是否显示线程信息。 默认为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认值2
                .methodOffset(7)        // （可选）隐藏内部方法调用，直到offset。 默认5
//                .logStrategy(customLog) // （可选）更改日志策略以打印出来。 默认LogCat
//                .tag("My custom tag")   // （可选）每个日志的全局标记。 默认值PRETTY_LOGGER
                .build();

        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isDebugMode;
            }
        });
    }


    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void i(String msg) {
        Logger.i(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(String msg,
                         Throwable e) {
        Logger.e(e, msg);
    }

}
