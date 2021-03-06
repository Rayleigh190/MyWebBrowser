package com.example.chlwj.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 웹뷰 기본 설정
        WebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        WebView.loadUrl("https://www.google.com")


        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                WebView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }

        }

        // 컨텍스트 메뉴 등록
        registerForContextMenu(WebView)
    }

    override fun onBackPressed() {
        if (webView().canGoBack()) {
            webView().goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                WebView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                WebView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                WebView.loadUrl("https://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                // 문자보내기
                sendSMS("031-123-456", WebView.url)
                return true
            }
            R.id.action_email -> {
                // 이메일 보내기
                email("test@example.com", "좋은 사이트", WebView.url)
                return true
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                // 페이지 공유
                share(WebView.url)
                return true
            }
            R.id.action_browser -> {
                // 기본 웹 브라우저에서 열기
                browse(WebView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
