package id.my.cariberas.pengeluaranharian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	1;
    private	static final String	DATABASE_NAME = "db_pengeluaran";
    private	static final String TABLE_PENGELUARAN = "tb_pengeluaran";

    private static final String COLUMN_ID = "id_pengeluaran";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_FEE = "fee";
    private static final String COLUMN_TANGGAL = "tanggal";
    private static final String COLUMN_CATEGORY= "category";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tb_pengeluaran (\n" +
                "    id_pengeluaran INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    title          VARCHAR (255),\n" +
                "    date           DATE,\n" +
                "    fee            INT,\n" +
                "    category       VARCHAR (255) \n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PENGELUARAN);
        onCreate(db);
    }

    public ArrayList<Contacts> listContacts(){
        String sql = "select * from " + TABLE_PENGELUARAN ;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contacts> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String fee = cursor.getString(2);
                String tanggal = cursor.getString(3);
                storeContacts.add(new Contacts(id, title, fee, tanggal));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addContacts(Contacts contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, contacts.getTitle());
        values.put(COLUMN_FEE, contacts.getFee());
        values.put(COLUMN_TANGGAL, contacts.getTanggal());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PENGELUARAN, null, values);
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("insert into tb_pengeluaran(COLUMN_TITLE, COLUMN_FEE, COLUMN_TANGGAL, COLUMN_CATEGORY) values('" +
//                contacts.getTitle() + "','" +
//                contacts.getFee() + "','" +
//                contacts.getTanggal() + "','pengeluaran')");
    }

    public void updateContacts(Contacts contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, contacts.getTitle());
        values.put(COLUMN_FEE, contacts.getFee());
        values.put(COLUMN_TANGGAL, contacts.getTanggal());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PENGELUARAN, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(contacts.getId_pengeluaran())});
    }

    public Contacts findContacts(String name){
        String query = "Select * FROM "	+ TABLE_PENGELUARAN + " WHERE " + COLUMN_TITLE + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Contacts contacts = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String title = cursor.getString(1);
            String fee = cursor.getString(2);
            String tanggal = cursor.getString(2);
            contacts = new Contacts(id, title, fee, tanggal);
        }
        cursor.close();
        return contacts;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PENGELUARAN, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
