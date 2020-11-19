package com.example.mymusic.customview.search

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.res.ResourcesCompat
import com.example.mymusic.R

class SearchEditText@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0):
    androidx.appcompat.widget.AppCompatEditText(context,attributeSet,defStyleAttr) {

    private var clearDrawable: Drawable? = null
    private var backDrawable: Drawable? = null
    var backListener:BackListener? = null

    interface BackListener{
        fun back()
    }

    init {
        init()
    }

    private fun init(){
        background = null
        backDrawable = ResourcesCompat.getDrawable(resources, R.drawable.arrow_back,null)
        clearDrawable = ResourcesCompat.getDrawable(resources, R.drawable.clear,null)
        setCompoundDrawablesWithIntrinsicBounds(backDrawable,null,null,null)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearDrawable(text?.length!! >0&&hasFocus());
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setClearDrawable(focused && length()>0);

    }

    private fun setClearDrawable(visible:Boolean){
        setCompoundDrawablesWithIntrinsicBounds(backDrawable, null,if (visible) clearDrawable else null, null);
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        if(event?.action ==  MotionEvent.ACTION_UP){
            val clear = clearDrawable
            val back = backDrawable
            if (x != null) {
                if(clear!=null && x <= (width - paddingRight) && x >=(width-paddingRight-clear.bounds.width())){
                    setText("")
                }else if(back != null && x >= paddingLeft && x <= paddingLeft + back.bounds.width()){
                    backListener?.back()
                }
            }

        }
        return super.onTouchEvent(event)
    }

}