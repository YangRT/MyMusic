package com.example.mymusic.customview.search;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import com.example.mymusic.R;

public class SearchEditText extends AppCompatEditText {

    private Drawable drawableClear;
    private Drawable drawableBack;
    private BackListener mListener;

    interface BackListener{
        void back();
    }

    public void setBackListener(BackListener listener){
        mListener = listener;
    }

    public SearchEditText(Context context) {
        super(context);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackground(null);
        drawableBack = ResourcesCompat.getDrawable(getResources(), R.drawable.arrow_left,null);
        drawableClear = ResourcesCompat.getDrawable(getResources(),R.drawable.clear,null);
        setCompoundDrawablesWithIntrinsicBounds(drawableBack, null,
                null, null);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearDrawable(text.length()>0&&hasFocus());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearDrawable(focused && length()>0);
    }



    private void setClearDrawable(Boolean visible){
        setCompoundDrawablesWithIntrinsicBounds(drawableBack, null,
                visible ? drawableClear : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Drawable drawable = drawableClear;
                Drawable drawable1 = drawableBack;
                if(drawable!=null && x<= (getWidth()-getPaddingRight()) && x >=(getWidth()-getPaddingRight()-drawable.getBounds().width())){
                    setText("");
                }else if(drawableBack != null && x >= getPaddingStart() && x <= getPaddingStart()+drawable1.getBounds().width()){
                    if(mListener != null){
                        mListener.back();
                    }
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
