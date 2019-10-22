package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // inisialisasi fab
    private FloatingActionButton fab;

    DatabaseHelper dbcenter;
    public static MainActivity ma;
    protected Cursor cursor;

    private RecyclerView recyclerView;
    private GroceryRecyclerViewAdapter adapter;
    private ArrayList<Grocery> groceryArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;

    Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ma = this;
        dbcenter = new DatabaseHelper(this);
        GetPengeluaranBulanIni();
        RefreshList();
        //add
        FloatingActionButton floatingActionButton=findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Floating Action Button Berhasil dibuat", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,TambahPengeluaranActivity.class);
                startActivity(i);
            }
        });

        //laporan lainnya
        ImageView imageview1 = findViewById(R.id.btnLaporanLainnya);
        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LaporanLainnyaActivity.class);
                startActivity(i);
            }
        });

        //tombol hapus
//        ImageView tombolHapus = findViewById(R.id.btnHapus);
//        tombolHapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshList();
                GetPengeluaranBulanIni();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pengeluaran where category = 'pengeluaran'  order by id_pengeluaran DESC LIMIT 20", null);
        cursor.moveToFirst();
        groceryArrayList = new ArrayList<>();

        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            try {
                groceryArrayList.add(new Grocery(cursor.getString(0), cursor.getString(1), formatRupiah.format(Integer.parseInt(cursor.getString(3))), cursor.getString(2)));
            } catch (Exception e) {
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.rvPengeluaran);
        adapter = new GroceryRecyclerViewAdapter(groceryArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void GetPengeluaranBulanIni(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int pengeluaranBulanIni;
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        String q = "SELECT strftime('%m', date) as valMonth, \n" +
                "SUM(fee) as valTotalMonth \n" +
                "FROM tb_pengeluaran \n" +
                "WHERE strftime('%Y', date)='"+year+"' GROUP BY valMonth;";
        Log.d("keluarin",q);
        cursor = db.rawQuery(q, null);
        if(cursor.moveToFirst())
            pengeluaranBulanIni = cursor.getInt(1);
        else
            pengeluaranBulanIni = 0;
        cursor.close();
        TextView tTotalPengeluaran = findViewById(R.id.totalPengeluaran);
        tTotalPengeluaran.setText(String.valueOf(formatRupiah.format(pengeluaranBulanIni)));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
