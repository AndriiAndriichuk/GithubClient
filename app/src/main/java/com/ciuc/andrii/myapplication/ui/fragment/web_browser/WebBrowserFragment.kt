package com.ciuc.andrii.myapplication.ui.fragment.web_browser

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ciuc.andrii.myapplication.R
import com.ciuc.andrii.myapplication.databinding.FragmentWebBrowserBinding
import com.ciuc.andrii.myapplication.ui.fragment.base.BaseFragment
import com.ciuc.andrii.myapplication.utils.setupUIForHideKeyboard

class WebBrowserFragment : BaseFragment() {
    private val args by navArgs<WebBrowserFragmentArgs>()
    private lateinit var layout: FragmentWebBrowserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = DataBindingUtil.inflate(inflater, R.layout.fragment_web_browser, container, false)
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setupUIForHideKeyboard(requireContext())

        setUpWebViewConfig()
        setWebViewClients()
        setOnClickListeners()

        layout.webViewFAQ.loadUrl(args.webUrl)

    }

    private fun setUpWebViewConfig() {
        layout.webViewFAQ.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = false
            allowFileAccess = false
            blockNetworkLoads = false
            domStorageEnabled = true

            domStorageEnabled = true
            setAppCacheEnabled(true)
            loadsImagesAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }


    }

    private fun setWebViewClients() {
        layout.webViewFAQ.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                }

            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }

                override fun onReceivedTitle(view: WebView, title: String) {
                    super.onReceivedTitle(view, title)
                }
            }
        }
    }

    private fun setOnClickListeners(){
        layout.toolbarWebBrowser.onBackClickListener = {
            findNavController().popBackStack()
        }
    }
}