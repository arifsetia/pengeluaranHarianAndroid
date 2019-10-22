package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahPengeluaranActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText eText, ePengeluaran, eHarga;

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengeluaran);

        dbHelper = new DatabaseHelper(this);

        //edit text
        eText=(EditText) findViewById(R.id.btnTanggalTambah);
        ePengeluaran =(EditText) findViewById(R.id.btnPengeluaranTambah);
        eHarga = (EditText)findViewById(R.id.btnHargaTambah);

        //button
        btnSimpan = (Button)findViewById(R.id.btnSimpan);

        //google calendar
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(TambahPengeluaranActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                eText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (ePengeluaran.getText().toString().equals("") || eText.getText().toString().equals("") || eHarga.getText().toString().equals("")){
                    Toast.makeText(TambahPengeluaranActivity.this,"Semua Data Harus Diisi!", Toast.LENGTH_SHORT ).show();
                }else {
                    // TODO Auto-generated method stub
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("insert into tb_pengeluaran(title, date, fee, category) values('" +
                            ePengeluaran.getText().toString() + "','" +
                            eText.getText().toString() + "','" +
                            eHarga.getText().toString() + "','pengeluaran')");
                    Toast.makeText(getApplicationContext(), "Berhasil Disimpan", Toast.LENGTH_LONG).show();
                    MainActivity.ma.RefreshList();
                    MainActivity.ma.GetPengeluaranBulanIni();
                    finish();
                }
            }
        });


    }






}
