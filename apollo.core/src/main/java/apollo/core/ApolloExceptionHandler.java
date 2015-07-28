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
    private ArrayList<Activity> list = new ArrayList<Activity>();

    ApolloExceptionHandler() {
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    protected void writeErrorLog(Throwable ex) {
        String info = null;
        ByteArrayOutputStream baos = null;
        PrintStream printStream = null;
        File file = null;

        try {
            baos = new ByteArrayOutputStream();
            printStream = new PrintStream(baos);
            ex.printStackTrace(printStream);
            byte[] data = baos.toByteArray();
            info = new String(data);
            data = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printStream != null) {
                    printStream.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.d(ApolloExceptionHandler.class.getName(), info);
        try {
            file = FileUtil.createFile("fatal_error.log");
        } catch (Exception e) {
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        writeErrorLog(ex);
        CollapseActivity.startActivity(ApolloApplication.app());
        exit();
    }

    private void exit() {
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}