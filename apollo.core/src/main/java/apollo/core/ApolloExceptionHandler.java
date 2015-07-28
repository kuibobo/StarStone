package apollo.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import apollo.app.CollapseActivity;
import apollo.util.FileUtil;

/**
 * Created by Texel on 2015/7/28.
 */
public class ApolloExceptionHandler implements UncaughtExceptionHandler  {

    private UncaughtExceptionHandler mHandler = null;

    ApolloExceptionHandler() {
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    protected void writeErrorLog(Throwable throwable) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] data = null;

        try {
            file = FileUtil.createFile("fatal_error.log");
        } catch (Exception e) {
            return;
        }

        try {
            baos = new ByteArrayOutputStream();
            ps = new PrintStream(baos);
            throwable.printStackTrace(ps);

            data = baos.toByteArray();

            fos = new FileOutputStream(file, true);
            fos.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (baos != null) {
                    baos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        writeErrorLog(throwable);
        CollapseActivity.startActivity(ApolloApplication.app());
        mHandler.uncaughtException(thread, throwable);
    }
}