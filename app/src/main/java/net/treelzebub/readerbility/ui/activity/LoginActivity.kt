package net.treelzebub.readerbility.ui.activity

import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import butterknife.bindView
import net.treelzebub.readerbility.Constants
import net.treelzebub.readerbility.R
import net.treelzebub.readerbility.async.async
import net.treelzebub.readerbility.auth.AuthService
import net.treelzebub.readerbility.util.AuthUtils

/**
 * Created by Tre Murillo on 1/1/16
 */
class LoginActivity : BaseAuthActivity() {

    private val webView: WebView by bindView(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val wvSettings = webView.settings
        wvSettings.builtInZoomControls = true
        wvSettings.javaScriptEnabled = true
        webView.setWebViewClient(RequestTokenCallback())
        loadAuthUrl()
    }

    private fun loadAuthUrl() {
        var authUrl: String = ""
        async({
            val sAuth = AuthService.service
            val rt = sAuth.requestToken
            authUrl = sAuth.getAuthorizationUrl(rt)
        }, {
            webView.loadUrl(authUrl)
        })
    }

    private inner class RequestTokenCallback : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (!url.isNullOrBlank() && url.startsWith(Constants.CALLBACK_URL)) {
                AuthUtils.requestAccessToken(this@LoginActivity, Uri.parse(url))
                return true
            }
            return false
        }
    }
}
