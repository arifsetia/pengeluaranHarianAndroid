package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailLaporanLainnya extends AppCompatActivity {
    DatabaseHelper dbcenter;
    public static DetailLaporanLainnya dll;
    protected Cursor cursor;

    Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_lainnya);

        dll = this;
        dbcenter = new DatabaseHelper(this);

        Intent intent = getIntent();


        String tanggalAwal = getIntent().getStringExtra("tanggalAwal");
        String tanggalAkhir = getIntent().getStringExtra("tanggalAkhir");
//        Toast.makeText(DetailLaporanLainnya.this, tanggalAkhir, Toast.LENGTH_LONG).show();
        GetPengeluaranBulanIni(tanggalAwal, tanggalAkhir);

        ImageView imgHalamanUtama = findViewById(R.id.btnHalamanUtama);
        imgHalamanUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailLaporanLainnya.this,MainActivity.class);
                startActivity(i);
            }
        });


    }

    public void GetPengeluaranBulanIni(String firstDate, String secondDate){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int pengeluaranBulanIni;
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        String q = "SELECT SUM(fee) as total FROM tb_pengeluaran WHERE category = 'pengeluaran' and date BETWEEN '"+firstDate+"' AND '"+secondDate+"';";
        Log.d("keluarin",q);
        cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst())
            pengeluaranBulanIni = cursor.getInt(0);
        else
            pengeluaranBulanIni = 0;
        cursor.close();
        TextView tTotalPengeluaran = findViewById(R.id.totalPengeluaranLihat);
        TextView tPeriodeLaporan = findViewById(R.id.periode);
//        String period = formateDateFromstring("YYYY-mm-dd","dd/mm/YYYY",firstDate).toString() +" s/d "+formateDateFromstring("YYYY-mm-dd","dd/mm/YYYY",secondDate).toString();
        String period = firstDate+" s/d "+secondDate;
        tPeriodeLaporan.setText(String.valueOf(period));
        tTotalPengeluaran.setText(String.valueOf(formatRupiah.format(pengeluaranBulanIni)));
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            Log.d("Error : ", "ParseException - dateFormat");
        }

        return outputDate;

    }
}
