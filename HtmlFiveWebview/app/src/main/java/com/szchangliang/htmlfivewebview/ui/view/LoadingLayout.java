package com.szchangliang.htmlfivewebview.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szchangliang.htmlfivewebview.R;


/**
 * Created by 麦显达 on 2017/10/4.
 */
public class LoadingLayout extends RelativeLayout {

    private Context context;

    private TextView errTv;

    private RelativeLayout loadingLayout;

    private LinearLayout ingLayout;

    private LinearLayout errLayout;

    private ProgressBar progressBar1;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();//
    }

    public LoadingLayout(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    private void initViews() {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_loading, this);
        errTv = (TextView) view.findViewById(R.id.errText);
        loadingLayout = (RelativeLayout) view.findViewById(R.id.loadingLayout);
        ingLayout = (LinearLayout) view.findViewById(R.id.loadLayoutIng);
        errLayout = (LinearLayout) view.findViewById(R.id.loadLayoutErr);
        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
    }

    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     * @param v
     *            正常结束后需要显示的view
     * @param errStr
     *            非正常结束时的错误信息，默认为 网络不给力，请检查您的网络设置!
     * @param topesId
     *           顶部图片!
     */
    public void setLoadStop(boolean bool, View v, CharSequence errStr, int topesId) {
        // animation.cancel();
        if (progressBar1 != null) {
            progressBar1.clearAnimation();
        }
        if (!bool) {
            loadingLayout.setVisibility(View.VISIBLE);
            ingLayout.setVisibility(View.GONE);
            errLayout.setVisibility(View.VISIBLE);
//            if (null != errStr) {
//                errTv.setText(errStr);
//            }

            if(0!=topesId){

                Drawable drawable=context.getResources().getDrawable(topesId);

                errTv.setCompoundDrawablePadding(10);
                errTv.setCompoundDrawables(null,drawable , null, null);
            }else{
                errTv.setCompoundDrawables(null,null , null, null);
            }

            if (null != errStr) {
                errTv.setText(errStr);
            }
        } else {
            loadingLayout.setVisibility(View.GONE);
            if (null != v) {
                v.setVisibility(View.VISIBLE);
            }

        }

    }

    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     * @param v
     *            正常结束后需要显示的view
     * @param errStr
     *            非正常结束时的错误信息，默认为 网络不给力，请检查您的网络设置!
     */
    public void setLoadStop(boolean bool, View v, String errStr){

        setLoadStop(bool,v,errStr,0);
    }


    /**
     * @param v 正常结束后需要显示的view
     */
    public void setLoadSuccess(View v) {
        setLoadStop(true, v, null, 0);
    }


    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     */
    public void setLoadStop(boolean bool){
        setLoadStop(bool,null,"网络不给力，请检查您的网络设置!",0);
    }
    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     */
    public void setLoadStop(boolean bool,int strIsd){
        setLoadStop(bool, context.getResources().getString(strIsd));
    }
    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     */
    public void setLoadStop(boolean bool,CharSequence msg){
        setLoadStop(bool,null,msg,0);
    }

    /**
     *
     * @param bool
     *            是否正常结束 true为正常
     * @param v
     *            正常结束后需要显示的view
     * @param strIsd
     *            非正常结束时的错误信息，默认为 网络不给力，请检查您的网络设置!
     */
    public void setLoadStop(boolean bool, View v, int strIsd, int topesId) {
        // animation.cancel();
        if (progressBar1 != null) {
            progressBar1.clearAnimation();
        }
        if (!bool) {
            loadingLayout.setVisibility(View.VISIBLE);
            ingLayout.setVisibility(View.GONE);
            errLayout.setVisibility(View.VISIBLE);
            if (0 != strIsd) {
                Drawable drawable= context.getResources().getDrawable(topesId);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                errTv.setCompoundDrawablePadding(10);
                errTv.setCompoundDrawables(null,drawable , null, null);
                errTv.setText(getResources().getString(strIsd));
            }
        } else {
            loadingLayout.setVisibility(View.GONE);
            if (null != v) {
                v.setVisibility(View.VISIBLE);
            }

        }

    }

    /**
     * 加载失败后回显
     *
     * @param msg
     */
    public void setLoadFailure(String msg) {
        setLoadStop(false, null, msg, R.drawable.new_icon_expect);
    }

    /**
     * 加载失败后回显
     */
    public void setLoadFailure() {
        setLoadStop(false, null, "网络不给力，请检查您的网络设置!", R.drawable.new_icon_expect);
    }

    public void setLoadStart() {
        // 界面再次载入时显示进度条
        loadingLayout.setVisibility(View.VISIBLE);
        errLayout.setVisibility(View.GONE);
        ingLayout.setVisibility(View.VISIBLE);
    }

    public void setBtnRetry(OnClickListener listener) {
        loadingLayout.setOnClickListener(listener);

    }
}