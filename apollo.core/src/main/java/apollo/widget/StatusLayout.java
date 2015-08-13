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

    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int HIDE_LAYOUT = 4;
    public static final int NODATA_ENABLE_CLICK = 5;

    private ProgressBar mProgress;
    private ImageView mImgView;
    private OnClickListener mClickListener;
    private RelativeLayout mLayout;
    private TextView mTextView;
    private Animation mAnim;

    private int mErrorState;
    private boolean mClickEnable = true;
    private String mNoDataContent = "";

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
        mImgView = (ImageView) view.findViewById(R.id.error_layout_img);
        mTextView = (TextView) view.findViewById(R.id.error_layout_tv);
        mLayout = (RelativeLayout) view.findViewById(R.id.error_layout_main);
        mProgress = (ProgressBar) view.findViewById(R.id.error_layout_animProgress);
        mAnim = AnimationUtils.loadAnimation(super.getContext(), R.anim.progressbar_loading);
        setBackgroundColor(-1);
        setOnClickListener(this);
        mImgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mClickEnable) {
                    //setErrorType(NETWORK_LOADING);
                    if (mClickListener != null)
                        mClickListener.onClick(v);
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
        if (mClickEnable) {
            //setErrorType(NETWORK_LOADING);
            if (mClickListener != null)
                mClickListener.onClick(v);
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
        mTextView.setText(msg);
    }

    public void setErrorMessage(int msg) {
        if (msg != 0) mTextView.setText(msg);
    }

    public void setStatus(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                if (CompatibleUtil.hasInternet(super.getContext())) {
                    mTextView.setText(R.string.click_to_refresh);
                    mImgView.setBackgroundResource(R.drawable.pagefailed_bg);
                } else {
                    mTextView.setText(R.string.network_error);
                    mImgView.setBackgroundResource(R.drawable.page_icon_network);
                }
                mImgView.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mProgress.clearAnimation();
                mClickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                mProgress.setVisibility(View.VISIBLE);
                mProgress.clearAnimation();
                mProgress.startAnimation(mAnim);
                mImgView.setVisibility(View.GONE);
                mTextView.setText(R.string.loading);
                mClickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                mImgView.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mProgress.clearAnimation();
                setNoDataContent();
                mClickEnable = false;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                mProgress.clearAnimation();
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                mImgView.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                mProgress.clearAnimation();
                setNoDataContent();
                mClickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        mNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    public void setNoDataContent() {
        if (!mNoDataContent.equals(""))
            mTextView.setText(mNoDataContent);
        else
            mTextView.setText(R.string.no_data);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }

    public String getMessage() {
        if (mTextView != null) {
            return mTextView.getText().toString();
        }
        return "";
    }
}
