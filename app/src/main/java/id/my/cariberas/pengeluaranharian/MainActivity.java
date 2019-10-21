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
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // inisialisasi fab
    private FloatingActionButton fab;

    DatabaseHelper dbcenter;
    public static MainActivity ma;
    protected Cursor cursor;

    String[] daftar;
    ListView listPengeluaran;

    private RecyclerView rvPengeluaran;
    private GroceryRecyclerViewAdapter groceryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPengeluaran = findViewById(R.id.rvPengeluaran);
        rvPengeluaran.setLayoutManager(new LinearLayoutManager(this));

        groceryRecyclerViewAdapter = new GroceryRecyclerViewAdapter();
        rvPengeluaran.setAdapter(groceryRecyclerViewAdapter);
    }

//    private void setData() {
//        List<Grocery> dummyData = DummyGroceryData.groceryList();
//        groceryRecyclerViewAdapter.updateData(dummyData);
//    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_pengeluaran where category = 'pengeluaran'  order by id_pengeluaran DESC", null);
        daftar = new String[cursor.getCount()];

        cursor.moveToFirst();
        final String sfaf[] = new String[cursor.getCount()];



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

            }
        }

        List<Grocery> dummyData = DummyGroceryData.groceryList();
        groceryRecyclerViewAdapter.updateData(dummyData);


//        ListAdapter adapter = new SimpleAdapter(
//                MainActivity.this, list, R.layout.row_list,
//                new String[]{"title", "date", "fee"},
//                new int[]{R.id.tPengeluaran, R.id.tTanggal, R.id.tHarga});
//
//        listPengeluaran.setAdapter(adapter);
    }



}
