package papublish.plshehe.tf88.presentation.webview

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import papublish.plshehe.tf88.common.model.DbFirebase
import papublish.plshehe.tf88.databinding.ActivityWebviewBinding
import papublish.plshehe.tf88.presentation.dashboard.MainActivity


class WebviewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWebLink()
        init()
    }

    private fun showMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
    }
    private fun getWebLink() {
        DbFirebase().getDatabase().addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged", "SetJavaScriptEnabled")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val pn = ds.child("packageName").getValue(String::class.java)
                    val url = ds.child("url").getValue(String::class.java)
                    val status = ds.child("jumpStatus").getValue(Boolean::class.java)
                    Log.d("TAG", "$pn / $url")

                    val webView: WebView = binding.firstWeb
                    if (packageName == pn) {
                        if (status == true) {
                            webView.loadUrl(url.toString())
                        } else {
                            showMainActivity()
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {

        with(binding.firstWeb) {
            with(settings) {
                defaultTextEncodingName = "UTF-8"
                cacheMode = WebSettings.LOAD_NO_CACHE
                useWideViewPort = true
                pluginState = WebSettings.PluginState.ON
                domStorageEnabled = true
                builtInZoomControls = false
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                loadWithOverviewMode = true
                blockNetworkImage = true
                loadsImagesAutomatically = true
                setSupportZoom(false)
                setSupportMultipleWindows(true)
            }
            requestFocusFromTouch()
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        }

        val webseting: WebSettings = binding.firstWeb.settings
        with(webseting) {
            this@WebviewActivity.getDir(
                "cache", MODE_PRIVATE
            )?.path
            domStorageEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        binding.firstWeb.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                binding.pbLoading.progress = newProgress
                if (newProgress == 100) {
                    binding.firstWeb.settings.blockNetworkImage = false
                }
            }

            override fun onCreateWindow(
                view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = this@WebviewActivity?.let { WebView(it) }
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                newWebView?.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        binding.firstWeb.loadUrl(url)
                        if (url.startsWith("http") || url.startsWith("https")) {
                            return super.shouldOverrideUrlLoading(view, url)
                        } else if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith(WebView.SCHEME_MAILTO)) {
                            val dialIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(dialIntent)
                        } else {
                            try {
                                val `in` = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                this@WebviewActivity?.startActivity(`in`)

                            } catch (ex: ActivityNotFoundException) {
                                val makeShortText = "The Application has not been installed"
                                Toast.makeText(this@WebviewActivity, makeShortText, Toast.LENGTH_SHORT).show()
                            }
                        }
                        return true
                    }
                }
                return true
            }
        }

        val settings: WebSettings = binding.firstWeb.settings
        settings.javaScriptEnabled = true
        binding.firstWeb.setOnLongClickListener { v: View ->
            val result = (v as WebView).hitTestResult
            val type = result.type
            if (type == WebView.HitTestResult.UNKNOWN_TYPE) return@setOnLongClickListener false
            when (type) {
                WebView.HitTestResult.PHONE_TYPE -> {}
                WebView.HitTestResult.EMAIL_TYPE -> {}
                WebView.HitTestResult.GEO_TYPE -> {}
                WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {}

                else -> {}
            }
            true
        }

        binding.firstWeb.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
       g         binding.pbLoading.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                binding.pbLoading.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView, request: WebResourceRequest, error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView, handler: SslErrorHandler, error: SslError
            ) {
                val builder = android.app.AlertDialog.Builder(this@WebviewActivity)
                var message = "SSL Certificate error."
                when (error.primaryError) {
                    SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                    SslError.SSL_EXPIRED -> message = "The certificate has expired."
                    SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                    SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                }
                message += " Do you want to continue anyway?"
                builder.setTitle("SSL Certificate Error")
                builder.setMessage(message)
                builder.setPositiveButton(
                    "Continue"
                ) { _: DialogInterface?, _: Int -> handler.proceed() }
                builder.setNegativeButton(
                    "Cancel"
                ) { _: DialogInterface?, _: Int -> handler.cancel() }
                val dialog = builder.create()
                dialog.show()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldOverrideUrlLoading(view, url)
                } else if (url.startsWith("intent:")) {
                    val urlSplit = url.split("/").toTypedArray()
                    var send = ""
                    if (urlSplit[2] == "user") {
                        send = "https://m.me/" + urlSplit[3]
                    } else if (urlSplit[2] == "ti") {
                        val data = urlSplit[4]
                        val newSplit = data.split("#").toTypedArray()
                        send = "https://line.me/R/" + newSplit[0]
                    }
                    val newInt = Intent(Intent.ACTION_VIEW, Uri.parse(send))
                    this@WebviewActivity?.startActivity(newInt)
                } else {
                    try {
                        val `in` = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        this@WebviewActivity.startActivity(`in`)
                    } catch (ex: ActivityNotFoundException) {
                        val makeShortText = "The Application has not been installed"
                        Toast.makeText(this@WebviewActivity, makeShortText, Toast.LENGTH_SHORT).show()
                    }
                }
                return true

            }
        }

        binding.firstWeb.setOnKeyListener { _: View?, i: Int, keyEvent: KeyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK && binding.firstWeb.canGoBack()) {
                    binding.firstWeb.goBack()
                    return@setOnKeyListener true
                }
            }
            false
        }
    }
    companion object{
        fun getStartIntent (context: Context) = Intent(context, WebviewActivity::class.java)
    }

}