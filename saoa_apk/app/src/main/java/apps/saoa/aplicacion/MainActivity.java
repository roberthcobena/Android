package apps.saoa.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView browser;
    String url = "https://www.kimsacorp.com.ec/prueba/app/";
    String url_error = "file:///android_asset/error.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"Necesita conexión a Internet",Toast.LENGTH_LONG).show();
            finish(); //Calling this method to close this activity when internet is not available.
        }

        //setea title
        MainActivity.this.setTitle("Artes y Servicios SAOA");

        //define navegador interno
        browser = (WebView)findViewById(R.id.Idvisorweb);
        WebSettings webSettings = browser.getSettings();
        //browser.setVisibility(View.GONE);

        //habilitamos javascript
        webSettings.setJavaScriptEnabled(true);

        browser.loadUrl(url);

        browser.setWebViewClient(new WebViewClient()
        {
            // evita que los enlaces se abran fuera nuestra app en el navegador de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if(!isappOnline()) {
                    browser.loadUrl(url_error);
                        finish(); //Calling this method to close this activity when internet is not available.
                }
            }

        });
    }


    private boolean isNetworkAvailable(MainActivity mainActivity) {
        ConnectivityManager conMan = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    private boolean isappOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}