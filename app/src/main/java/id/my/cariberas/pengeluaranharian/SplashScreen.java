package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private int waktu_loading=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView teks = findViewById(R.id.txtNmApk);
        Typeface customfont=Typeface.createFromAsset(getAssets(),"font/NeoSansStd Bold TR.otf");
        teks.setTypeface(customfont);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent home=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);
    }
}
