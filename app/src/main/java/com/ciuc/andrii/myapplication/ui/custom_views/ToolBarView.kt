package com.ciuc.andrii.myapplication.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ciuc.andrii.myapplication.R
import kotlinx.android.synthetic.main.toolbar_view.view.*

class ToolBarView  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_TITLE_TEXT = ""
    }

    var titleText = DEFAULT_TITLE_TEXT

    var onBackClickListener: (() -> Unit)? = null

    //field for general feedback
    var currentScore : Int = 0

    init {
        inflate(context, R.layout.toolbar_view, this)
        setupAttributes(attrs)
        setUpView()
        setOnClickListeners()
    }
    private fun setupAttributes(attrs: AttributeSet?) {
        // 6
        //todo Obtain a typed array of attributes
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ToolBarView, 0, 0)

        // 7
        //todo Extract custom attributes into member variables
        titleText =
            typedArray.getString(R.styleable.ToolBarView_titleText) ?: DEFAULT_TITLE_TEXT
    }

    private fun setUpView() {
        toolbarTitleText.text = titleText
    }


    private fun setOnClickListeners() {
        imageBack.setOnClickListener { onBackClickListener?.invoke() }
    }


}