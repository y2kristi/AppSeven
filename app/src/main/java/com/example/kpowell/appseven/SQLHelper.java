package com.example.kpowell.appseven;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kpowell on 4/4/15.
 */
public class SQLHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ToDo";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String item_table = "CREATE TABLE todos ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "date TEXT, "+
                "description TEXT )";

        db.execSQL(item_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS todos");

        this.onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("date", item.getDate());
        values.put("description", item.getDescription());

        long id = db.insert("todos",null,values);

        System.out.println("This is the ID: " + id);
        db.close();

    }

    public void editItem(Item item){

        SQLiteDatabase db = this.getWritableDatabase();

        System.out.println("HERE: " + item.toString());
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("description", item.getDescription());
        values.put("date", item.getDate());

        int i = db.update("todos",
                values,
                "id" + " = ?",
                new String[] { String.valueOf(item.getId()) });

        db.close();

        System.out.println("Value updated" + i);
    }

    public List<Item> getAllItems() {
        List<Item> items = new LinkedList<Item>();

        String query = "SELECT  * FROM " + "todos";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setDescription(cursor.getString(3));
                item.setDate(cursor.getString(2));

                items.add(item);
            } while (cursor.moveToNext());
        }

        Log.d("getAllItems()", items.toString());

        return items;
    }

}
