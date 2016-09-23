package com.imooc.android_webview;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;




public class MainActivity extends Activity implements OnClickListener {

	private String url = null;
	private WebView webView;
	private EditText et_url;
	private Button btn_login;
	private Button btn_back;
	private Button btn_exit;
	private Button btn_forward;
	private Button btn_menu;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		       setContentView(R.layout.web); //加载进度条
				setContentView(R.layout.activity_main);
		        setProgressBarIndeterminate(true);
		        webView = (WebView)findViewById(R.id.webView) ;
                et_url = (EditText)findViewById(R.id.et_url);
		        btn_login = (Button)findViewById(R.id.btn_login);
		        btn_back = (Button)findViewById(R.id.btn_back);
		        btn_exit = (Button)findViewById(R.id.btn_exit);
		        btn_forward = (Button)findViewById(R.id.btn_forward);
		        btn_menu = (Button)findViewById(R.id.btn_menu);

		        btn_login.setOnClickListener(this);
		        btn_back.setOnClickListener(this);
		        btn_exit.setOnClickListener(this);
		        btn_forward.setOnClickListener(this);
		        btn_menu.setOnClickListener(this);


	}

	private void startReadUrl(String url) {
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {


									   public boolean shouldOverrideUrlLoading(WebView view, String url) {
										   view.loadUrl(url);
										   return true;
									   }
								   });

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		webView.setWebChromeClient(new WebChromeClient(){

			public void onProgressChange(WebView view,int newProgress){
			setTitle("本页已加载"+ newProgress+"%");
			if (newProgress == 100){
			closeProgressBar();}
				else{
				openProgressBar(newProgress);
			}
				super.onProgressChanged(view,newProgress);
			}

		});
	}
	    protected void openProgressBar(int x){
			setProgressBarIndeterminateVisibility(true);
			setProgress(x);
		}    //打开进度条

	    protected void closeProgressBar(){
			setProgressBarIndeterminateVisibility(false);
		}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(webView.canGoBack())
			{
				webView.goBack();//返回上一页面
				return true;
			}
			else
			{
				System.exit(0);//退出程序
			}
		}
		return super.onKeyDown(keyCode, event);
	}


	public void onClick(View v){
		switch (v.getId()){
			case R.id.btn_login:
				url= "http://" + et_url.getText().toString();
				url= url.replace(" ","");
				startReadUrl(url);
				break;
			case R.id.btn_back:
				if(webView.canGoBack()){
					webView.goBack();
				}else{
					finish();
				}
				break;
			case R.id.btn_forward:
				if (webView.canGoForward()){
					webView.goForward();
				}
				break;
			case R.id.btn_exit:
				finish();
				break;
			case R.id.btn_menu:
				startReadUrl("http://m.baidu.com");
				break;
		}

	}
	
}


