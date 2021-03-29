package org.test.news.widget.temptyview;

import android.content.Context;
import android.util.AttributeSet;

import org.test.news.R;


public class TEmptyView extends BaseTEmptyView {

    public TEmptyView(Context context) {
        this(context, null);
    }

    public TEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_simple_empty_view;
    }
}

