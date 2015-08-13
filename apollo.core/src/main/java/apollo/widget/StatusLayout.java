package apollo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apollo.core.R;
import apollo.util.CompatibleUtil;

/**
 * Created by Texel on 2015/8/13.
 */
public class StatusLayout extends LinearLayout implements View.OnClickListener {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;

    private ProgressBar animProgress;
    private ImageView img;
    private OnClickListener listener;
    private RelativeLayout mLayout;
    private TextView mTv;
    private Animation mAnim;

    private int mErrorState;
    private boolean clickEnable = true;
    private String strNoDataContent = "";

    public StatusLayout(Context context) {
        super(context);
        init();
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * init the view.
     */
    private void init() {
        View view = View.inflate(super.getContext(), R.layout.layout_status, null);
        img = (ImageView) view.findViewById(R.id.error_layout_img);
        mTv = (TextView) view.findViewById(R.id.error_layout_tv);
        mLayout = (RelativeLayout) view.findViewById(R.id.error_layout_main);
        animProgress = (ProgressBar) view.findViewById(R.id.error_layout_animProgress);
        mAnim = AnimationUtils.loadAnimation(super.getContext(), R.anim.progressbar_loading);
        setBackgroundColor(-1);
        setOnClickListener(this);
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    //setErrorType(NETWORK_LOADING);
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });

        addView(view);
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            //setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setErrorMessage(String msg) {
        mTv.setText(msg);
    }

    public void setErrorMessage(int msg) {
        if (msg != 0) mTv.setText(msg);
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                if (CompatibleUtil.hasInternet(super.getContext())) {
                    mTv.setText(R.string.click_to_refresh);
                    img.setBackgroundResource(R.drawable.pagefailed_bg);
                } else {
                    mTv.setText(R.string.network_error);
                    img.setBackgroundResource(R.drawable.page_icon_network);
                }
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                animProgress.clearAnimation();
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                animProgress.setVisibility(View.VISIBLE);
                animProgress.clearAnimation();
                animProgress.startAnimation(mAnim);
                img.setVisibility(View.GONE);
                mTv.setText(R.string.loading);
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                animProgress.clearAnimation();
                setTvNoDataContent();
                clickEnable = false;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                animProgress.clearAnimation();
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                img.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                animProgress.clearAnimation();
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            mTv.setText(strNoDataContent);
        else
            mTv.setText(R.string.no_data);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }

    public String getMessage() {
        if (mTv != null) {
            return mTv.getText().toString();
        }
        return "";
    }
}
