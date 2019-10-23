package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  implements GroceryRecyclerViewAdapter.RecyclerImageAdapter{
    // inisialisasi fab
    private FloatingActionButton fab;

    DatabaseHelper dbcenter;
    public static MainActivity ma;
    protected Cursor cursor;

    private RecyclerView recyclerView;
    private GroceryRecyclerViewAdapter adapter;
    private ArrayList<Grocery> groceryArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    DatePickerDialog picker;

    private AdView mAdView;

    Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    private NestedScrollView nested_scrool_view;



    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //admob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        /*for test*/
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("323C4BAC43608B9E353C1E674F09F67C").build();
//        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        nested_scrool_view = findViewById(R.id.nested_scroll_view);

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
        cursor = db.rawQuery("SELECT * FROM tb_pengeluaran where category = 'pengeluaran'  order by id_pengeluaran DESC LIMIT 50", null);
        cursor.moveToFirst();
        //groceryArrayList = new ArrayList<>();

        /*for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            try {
                groceryArrayList.add(new Grocery(cursor.getString(0), cursor.getString(1), formatRupiah.format(Integer.parseInt(cursor.getString(3))), cursor.getString(2)));
            } catch (Exception e) {
            }
        }*/

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            try {
                HashMap<String, String> showData = new HashMap<String, String>();
                showData.put("id_pengeluaran", cursor.getString(0).toString());
                showData.put("title", cursor.getString(1).toString());
                showData.put("date", cursor.getString(2).toString());
                showData.put("fee", "Rp. "+cursor.getString(3).toString());
                list.add(showData);
            } catch (Exception e) {
                Log.d("Exception : ",e.getMessage());
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.rvPengeluaran);

        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new GroceryRecyclerViewAdapter(new ArrayList<HashMap>(list), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setAutoMeasureEnabled(true);
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
    public void onRecyclerImageSelected(HashMap groceryList) {
        String xImg = groceryList.get("fee").toString();
        final String idPengeluaran = groceryList.get("id_pengeluaran").toString();
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Hapus Data");
        alert.setMessage("Apakah anda yakin akan menghapus data ini?");
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = dbcenter.getReadableDatabase();
                db.delete("tb_pengeluaran","id_pengeluaran=? ",new String[]{idPengeluaran});
                cursor.moveToFirst();
                Toast.makeText(MainActivity.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                GetPengeluaranBulanIni();
                RefreshList();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public void onRecyclerLongClick(HashMap groceryList){

//        dialog = new AlertDialog.Builder(MainActivity.this);
//        inflater = getLayoutInflater();
//        dialogView = inflater.inflate(R.layout.activity_edit_data, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_edit_data,null);

        builder.setView(dialogView);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();

        final String id_pengeluaran = groceryList.get("id_pengeluaran").toString();
        final String title = groceryList.get("title").toString();
        final String date = groceryList.get("date").toString();
        final String fee = groceryList.get("fee").toString();

        String currentString = fee;
        String[] separated = currentString.split(". ");
        final String feeBaru = separated[1].trim();

        //        EditText id_pengeluaran_edit = dialogView.findViewById(R.id.tPengeluaranEdit);
        final EditText titleEdit = dialogView.findViewById(R.id.tPengeluaranEdit);
        final EditText feeEdit = dialogView.findViewById(R.id.tHargaEdit);
        final EditText tanggalEdit = dialogView.findViewById(R.id.tTanggalEdit);
        Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);


        //google calendar
        tanggalEdit.setInputType(InputType.TYPE_NULL);
        tanggalEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                tanggalEdit.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        titleEdit.setText(title);
        feeEdit.setText(feeBaru);
        tanggalEdit.setText(date);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbcenter.getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("title", titleEdit.getText().toString());
                cv.put("date", tanggalEdit.getText().toString());
                cv.put("fee", feeEdit.getText().toString());
                db.update("tb_pengeluaran", cv, "id_pengeluaran="+id_pengeluaran, null);
                cursor.moveToFirst();
                Toast.makeText(MainActivity.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                GetPengeluaranBulanIni();
                RefreshList();
                dialog.dismiss();
            }
        });
        dialog.show();
    }



}
