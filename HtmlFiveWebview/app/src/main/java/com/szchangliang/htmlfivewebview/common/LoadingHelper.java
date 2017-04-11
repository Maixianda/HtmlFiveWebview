package com.szchangliang.htmlfivewebview.common;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by 麦显达 on 17/4/10.
 * webview的自定义加载进度框,MaterialDialog实现
 */
public class LoadingHelper {

    //region 成员变量
    protected static MaterialDialog mMaterialDialog = null;
    //endregion 成员变量

    //region 显示加载的方法
    /**
     * 显示加载
     *
     * @param context 上下文
     * @param message 加载框显示的提示
     * @return
     */
    public static MaterialDialog showMaterLoading(Context context, String message) {
        return showMaterLoading(context, message, null);
    }

    /**
     * 显示加载
     *
     * @param context  上下文
     * @param message  加载框显示的提示
     * @param listener 取消对话框的回调
     * @return MaterialDialog
     */
    public static MaterialDialog showMaterLoading(final Context context, final String message, final DialogInterface.OnCancelListener listener) {
        if (mMaterialDialog != null) {
            hideMaterLoading();
        }
        mMaterialDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content(message)
                .cancelable(listener != null)
                .cancelListener(listener)
                .show();

        return mMaterialDialog;
    }
    //endregion 显示加载的方法

    //region 获取对话框的方法
    /**
     * 生成加载对话框,但是不显示
     *
     * @param context  上下文
     * @param message  加载框显示的提示
     * @param listener 取消对话框的回调
     * @return MaterialDialog
     */
    public static MaterialDialog getMaterLoading(final Context context, final String message, final DialogInterface.OnCancelListener listener) {
        if (mMaterialDialog != null) {
            hideMaterLoading();
        }
        mMaterialDialog = new MaterialDialog.Builder(context)
                .progress(true, 0)
                .content(message)
                .cancelable(listener != null)
                .cancelListener(listener).build();

        return mMaterialDialog;
    }
    //endregion 获取对话框的方法

    //region 隐藏对话框的方法
    /**
     * hide loading
     */
    public static void hideMaterLoading() {
        if (mMaterialDialog != null) {
            if (mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = null;
        }
    }
    //endregion 隐藏对话框的方法
}
