package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaporanLainnyaActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText,eText2;
    Button btnLihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_lainnya);

        eText = findViewById(R.id.tanggalDari);
        eText2 = findViewById(R.id.tanggalSampai);

        //tanggal dari
        eText=(EditText) findViewById(R.id.tanggalDari);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LaporanLainnyaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;
                                if(month < 10){
                                    formattedMonth = "0" + month;
                                }
                                if(dayOfMonth < 10){

                                    formattedDayOfMonth  = "0" + dayOfMonth ;
                                }
                                eText.setText(  year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        eText2=(EditText) findViewById(R.id.tanggalSampai);
        eText2.setInputType(InputType.TYPE_NULL);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LaporanLainnyaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String formattedMonth = "" + month;
                                String formattedDayOfMonth = "" + dayOfMonth;
                                if(month < 10){
                                    formattedMonth = "0" + month;
                                }
                                if(dayOfMonth < 10){
                                    formattedDayOfMonth  = "0" + dayOfMonth ;
                                }
                                eText2.setText(  year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

            btnLihat = findViewById(R.id.btnLihatLaporanLainnya);
            btnLihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eText.getText().toString().equals("") || eText2.getText().toString().equals("")){
                        Toast.makeText(LaporanLainnyaActivity.this,"Data tidak boleh kosong!!", Toast.LENGTH_SHORT ).show();
                    }else {
                        if(!isValidDate(eText.getText().toString())){
                            Toast.makeText(LaporanLainnyaActivity.this,"Format tanggal salah!!", Toast.LENGTH_SHORT ).show();
                        }else if(!isValidDate(eText2.getText().toString())) {
                            Toast.makeText(LaporanLainnyaActivity.this,"Format tanggal salah!!", Toast.LENGTH_SHORT ).show();
                        } else{
                            String tanggalAwal, tanggalAkhir;
                            tanggalAwal = eText.getText().toString();
                            tanggalAkhir = eText2.getText().toString();
                            Intent i = new Intent(getApplicationContext(),DetailLaporanLainnya.class);
                            i.putExtra("tanggalAwal", tanggalAwal);
                            i.putExtra("tanggalAkhir", tanggalAkhir);
                            startActivity(i);
                        }

                    }
                }
            });
    }

    public boolean isValidDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date testDate = null;
        String errorMessage;
        try {
            testDate = sdf.parse(date);
        }
        catch (ParseException e) {
            return false;
        }
        if (!sdf.format(testDate).equals(date))
        {
            return false;
        }
        return true;
    }
}
