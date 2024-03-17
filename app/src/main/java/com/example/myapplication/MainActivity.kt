package com.example.myapplication

import android.content.Context
import android.net.http.SslError
import android.os.Bundle
import android.webkit.GeolocationPermissions
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContent {
//            MyApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
//                }
//            }
//        }

        setContentView(R.layout.web_view)
        var webview = findViewById<WebView>(R.id.webView)
        webview.webViewClient = SSLTolerentWebViewClient()
        webview.settings.javaScriptEnabled = true
        webview.settings.setGeolocationEnabled(true)
        webview.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                callback.invoke(origin, true, false)
            }
        }

        webview.addJavascriptInterface(WebAppInterface(this), "Android")

        webview.loadUrl("https://192.168.1.112:3333/test")
//        webview.loadUrl("https://malay-mobile-navigation.vercel.app")


        webview.addJavascriptInterface(WebAppInterface(this), "Android")
    }
}

// SSL Error Tolerant Web View Client
private class SSLTolerentWebViewClient : WebViewClient() {
    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed() // Ignore SSL certificate errors
    }
}

class WebAppInterface
/** Instantiate the interface and set the context.  */ internal constructor(var mContext: Context) {
    /** Show a toast from the web page.  */
    @JavascriptInterface
    fun onFinished() {
        Toast.makeText(mContext, "FINISHED", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}