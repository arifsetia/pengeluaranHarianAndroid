package id.my.cariberas.pengeluaranharian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listPengeluaran = (ListView)findViewById(R.id.listView);

        ma = this;
        dbcenter = new DatabaseHelper(this);
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
//                Toast.makeText(MainActivity.this, "Here is your Text",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,LaporanLainnyaActivity.class);
                startActivity(i);
            }
        });
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pengeluaran where category = 'pengeluaran'  order by id_pengeluaran DESC", null);

        cursor.moveToFirst();


        groceryArrayList = new ArrayList<>();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);

            try {
                groceryArrayList.add(new Grocery(cursor.getString(0), cursor.getString(1), "Rp. "+cursor.getString(3), cursor.getString(2)));
            } catch (Exception e) {

            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.rvPengeluaran);

        adapter = new GroceryRecyclerViewAdapter(groceryArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

}
