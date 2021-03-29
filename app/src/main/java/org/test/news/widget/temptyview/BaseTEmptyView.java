package org.test.news.widget.temptyview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.test.news.R;


public abstract class BaseTEmptyView extends FrameLayout {

    private static TViewUtil.EmptyViewBuilder mConfig = null;

    public static void init(TViewUtil.EmptyViewBuilder defaultConfig) {
        BaseTEmptyView.mConfig = defaultConfig;
    }

    public static boolean hasDefaultConfig() {
        return BaseTEmptyView.mConfig != null;
    }

    public static TViewUtil.EmptyViewBuilder getConfig() {
        return mConfig;
    }

    private ImageView mImageView;
    private TextView mTextView;
    protected Button mButton;

    public BaseTEmptyView(Context context) {
        this(context, null);
    }

    public BaseTEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, getLayout(), this);
        init();
    }

    protected abstract int getLayout();

    private void init() {
        mTextView = findViewById(R.id.textview_empty);
        mImageView = findViewById(R.id.image_View_empty);
        mButton = findViewById(R.id.button_empty);
        mButton.setVisibility(GONE);
    }

    public void setEmptyResponse() {
        setEmptyResponse(getResources().getString(R.string.msg_err_no_data));
    }

    public void setEmptyResponse(String message) {
        setEmptyResponse(new SpannableString(message));
    }

    public void setEmptyResponse(SpannableString message) {
        setEmptyResponse(null, message);
    }

    public void setEmptyResponse(Drawable icon, SpannableString message) {
        setEmptyOrErrorResponse(icon, message, null, null);
    }

    public void setEmptyOrErrorResponse(Drawable icon, SpannableString message, String buttonText, OnClickListener onClickListener) {
        setVisibility(VISIBLE);
        mTextView.setText(TextUtils.isEmpty(message) ? getResources().getString(R.string.msg_err_no_data) : message);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_primary));
        if (icon != null) {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageDrawable(icon);
        } else {
            mImageView.setVisibility(GONE);
        }
        if (onClickListener != null) {
            mButton.setVisibility(VISIBLE);
            mButton.setText(buttonText);
            mButton.setEnabled(true);
            mButton.setOnClickListener(onClickListener);
            return;
        }
        mButton.setVisibility(GONE);
    }

    public void setEmptyOrErrorResponse(SpannableString message, String buttonText, OnClickListener onClickListener) {
        setEmptyOrErrorResponse(null, message, buttonText, onClickListener);
    }

    public void setErrorResponse(SpannableString message, OnClickListener onClickListener) {
        setEmptyOrErrorResponse(null, message, getResources().getString(R.string.lbl_alert_retry), onClickListener);
    }

    public void setErrorResponse(String message, OnClickListener onClickListener) {
        if (message == null) {
            message = getResources().getString(R.string.msg_err_no_data);
        }
        setErrorResponse(new SpannableString(message), onClickListener);
    }


    public void setShowIcon(boolean mShowIcon) {
        mImageView.setVisibility(mShowIcon ? VISIBLE : GONE);
    }

    public void setShowText(boolean showText) {
        mTextView.setVisibility(showText ? VISIBLE : GONE);
    }

    public void setShowButton(boolean showButton) {
        mButton.setVisibility(showButton ? VISIBLE : GONE);
    }

    public void setTextSize(float mTextSize) {
        mTextView.setTextSize(mTextSize);
    }

    public void setTextColor(int mTextColor) {
        mTextView.setTextColor(mTextColor);
    }

    public void setIcon(int mIconSrc) {
        mImageView.setImageResource(mIconSrc);
    }

    public void setIcon(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
    }

    public void setAction(OnClickListener onClickListener) {
        mButton.setOnClickListener(onClickListener);
    }

    public void setActionText(String actionText) {
        mButton.setText(actionText);
    }

    public void setEmptyText(String emptyText) {
        mTextView.setText(emptyText);
        setVisibility(View.VISIBLE);
    }
}
