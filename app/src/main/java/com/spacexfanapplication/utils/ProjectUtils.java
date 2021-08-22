package com.spacexfanapplication.utils;

import android.app.ProgressDialog;
import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class ProjectUtils {
    private static AlertDialog dialog;
    private static ProgressDialog mProgressDialog;

    public static void showToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void showLog(String tag, String text) {
        Log.e(tag, text);
    }

    //Here Show ProgressDialog
    public static ProgressDialog showProgressDialog(Context context, String message, boolean isCancelable) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.show();
        return mProgressDialog;
    }

    // Static method to pause the progress dialog.
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}
