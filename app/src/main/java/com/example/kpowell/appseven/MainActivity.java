package com.example.kpowell.appseven;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText description;
    private EditText endDate;
    private Button button;
    private SQLHelper sql;
    ArrayAdapter<Item> arr;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        sql = new SQLHelper(this);

        title = (EditText) findViewById(R.id.editText);
        description = (EditText) findViewById(R.id.editText2);
        endDate = (EditText) findViewById(R.id.editText3);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sql.addItem(new Item(title.getText().toString(),description.getText().toString(),
                        endDate.getText().toString()));

                Toast.makeText(getBaseContext(),"Thank you for your response.",
                        Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings2){

            final Context context = this;

            items = sql.getAllItems();

            setContentView(R.layout.list_main);
            ListView lv = (ListView) findViewById(R.id.listview);

            arr = new ArrayAdapter<Item>(this,
                    android.R.layout.simple_list_item_1, items);
            lv.setAdapter(arr);

            lv.setTextFilterEnabled(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                    String str = ((TextView) view).getText().toString();
                    System.out.println("THIS " + position + "OR" + id);
                    items.remove(position);
                    arr.notifyDataSetChanged();

                    showInputDialog(str, position);
                }

            });

        }

        if (id == R.id.action_settings) {

            setContentView(R.layout.activity_main);
            addListenerOnButton();

        }

        return super.onOptionsItemSelected(item);
    }

    public void timeToUpdate(Item item){
        arr.add(item);
        arr.notifyDataSetChanged();
    }

    protected void showInputDialog(String str, int pos) {

        final String[] ary = str.split("\n");
        final int position = pos;

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        editText.setText(ary[1]);
        final EditText editText2 = (EditText) promptView.findViewById(R.id.edittext2);
        editText2.setText(ary[2]);
        final EditText editText3 = (EditText) promptView.findViewById(R.id.edittext3);
        editText3.setText(ary[3]);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Item item = new Item(Integer.parseInt(ary[0]), editText.getText().toString(),
                                editText2.getText().toString(),editText3.getText().toString());
                        sql.editItem(item);
                        timeToUpdate(item);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Item item = new Item(Integer.parseInt(ary[0]), editText.getText().toString(),
                                        editText2.getText().toString(),editText3.getText().toString());
                                timeToUpdate(item);
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
