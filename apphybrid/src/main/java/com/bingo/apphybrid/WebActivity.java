package com.bingo.apphybrid;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bingo.apphybrid.databinding.ActivityWebBinding;
import com.bingo.demo.approuterpath.AppHybrid;
import com.bingo.router.Router;
import com.bingo.router.annotations.Parameter;
import com.bingo.router.annotations.Route;


@Route(pathClass = AppHybrid.Web.class)
public class WebActivity extends AppCompatActivity {
    @Parameter
    String url;

    private ActivityWebBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.bind(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        mBinding.web.loadUrl(url);
        mBinding.web.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return aaa(request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return aaa(url);
            }

            private boolean aaa(String url) {
                Uri uri = Uri.parse(url);
                if ("router".equals(uri.getScheme()) && "filter.com".equals(uri.getHost())) {
                    Router.newRequest(uri).putParameter().build().startBy(WebActivity.this);
                    return true;
                } else {
                    return false;
                }
            }

        });
    }
}
