package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DetailLaporanLainnya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_lainnya);

        ImageView imgHalamanUtama = findViewById(R.id.btnHalamanUtama);
        imgHalamanUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailLaporanLainnya.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
