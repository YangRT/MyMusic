package com.example.mymusic.customview

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mymusic.R

class IdentityDialog@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr:Int = 0): Dialog(context) {

    public class Builder(context: Context){
        private var mLayout: View
        private var tvSearch: TextView
        private var tvRetry: TextView
        private var tvMessage: TextView
        private var dialog: IdentityDialog = IdentityDialog(context)
        private var searchOnClickListener: View.OnClickListener? = null
        private var retryOnClickerListener: View.OnClickListener? = null

        init {
            val inflater =  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mLayout = inflater.inflate(R.layout.dialog_identity, null, false)
            dialog.addContentView(mLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            tvSearch = mLayout.findViewById(R.id.tv_search)
            tvRetry = mLayout.findViewById(R.id.tv_retry)
            tvMessage = mLayout.findViewById(R.id.dialog_message)
        }

        fun setMessage(message: String) : Builder {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvMessage.text = Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvMessage.text = Html.fromHtml(message)
            }
            return this
        }

        fun setSearchOnClickListener(listener: View.OnClickListener): Builder {
            searchOnClickListener = listener
            return this
        }

        fun setRetryOnClickerListener(listener: View.OnClickListener): Builder {
            retryOnClickerListener = listener
            return this
        }

        fun create(): IdentityDialog {
            tvRetry.setOnClickListener {
                dialog.dismiss()
                retryOnClickerListener?.onClick(it)
            }
            tvSearch.setOnClickListener {
                dialog.dismiss()
                searchOnClickListener?.onClick(it)
            }
            dialog.setContentView(mLayout)
            dialog.setCancelable(true)                //用户可以点击后退键关闭 Dialog
            dialog.setCanceledOnTouchOutside(false)
            return dialog
        }
    }
}