package com.madchan.migratedatademo.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Assets工具类
 */
public class AssetsUtil {

    /**
     * 拷贝Assets文件夹里的指定文件到sd卡
     * @param context      上下文
     * @param fileName     源文件名
     * @param destFile     目标文件
     */
    public static void copyFileFromAssets(Context context, String fileName, File destFile) {
        try {
            InputStream is = context.getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {     //循环从输入流读取 buffer字节
                fos.write(buffer, 0, len);      //将读取的输入流写入到输出流
            }
            fos.flush();    //刷新缓冲区
            is.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
