package com.wy.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SimpleAdapter adapter;
    private ListView recentList;
    private List<Map<String, Object>> dataList;
    private Map<String, Object> map;
    private List<AccountDesc> accountList;

    private DBOpenHlper dbOpenHlper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOpenHlper = new DBOpenHlper(this, "tb_account", null, 1);

        recentList = findViewById(R.id.recent_list);

        String[] from = {"accountType", "account", "password"};
        int[] to = {R.id.simple_layout_account_type, R.id.simple_layout_account, R.id.simple_layout_password};
        dataList = new ArrayList<Map<String, Object>>();
        accountList = new ArrayList<AccountDesc>();

        SQLiteDatabase db = dbOpenHlper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from tb_account", null);
        if(cursor.moveToFirst()) {
            do {
                map = new HashMap<String, Object>();
                AccountDesc accountDesc = new AccountDesc();
//                String accountGroup = cursor.getString(cursor.getColumnIndex("account_group"));
                accountDesc.setAccountType(cursor.getString(cursor.getColumnIndex("account_type")));
                accountDesc.setAccount(cursor.getString(cursor.getColumnIndex("account")));
                accountDesc.setPassword(cursor.getString(cursor.getColumnIndex("password")));

                accountList.add(accountDesc);
                map.put("accountType", accountDesc.getAccountType());
                map.put("account", accountDesc.getAccount());
                map.put("password", accountDesc.getPassword());
                dataList.add(map);

            } while (cursor.moveToNext());
        }

        adapter = new SimpleAdapter(this, dataList, R.layout.simple_adapter, from, to);


        recentList.setAdapter(adapter);

        recentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", "Item is clicked.");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AccountDescActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("account", accountList.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        recentList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Item "+position+" has been deleted.", Toast.LENGTH_SHORT).show();
                dbOpenHlper.getWritableDatabase().delete("tb_account", "account_type=?", new String[]{accountList.get(position).getAccountType()});
                dataList.remove(position);
                accountList.remove(position);
                adapter.notifyDataSetChanged();

                return true;
            }
        });


        Button button_add = findViewById(R.id.add_account);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "add button is clicked.");
                Intent intent = new Intent(MainActivity.this, AccountAddActivity.class);
                startActivity(intent);
            }
        });

        Button button_query = findViewById(R.id.qurey);
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "query button is clicked.");
                String key = "QQ";
//                Cursor cursor = dbOpenHlper.getReadableDatabase().query("tb_account", null, "account_type=?", new String[]{key}, null, null, null);
                SQLiteDatabase db = dbOpenHlper.getWritableDatabase();
                Cursor cursor = db.rawQuery("Select * from tb_account", null);
                if(cursor.moveToFirst()) {
                    do {
                        String accountGroup = cursor.getString(cursor.getColumnIndex("account_group"));
                        String accountType = cursor.getString(cursor.getColumnIndex("account_type"));
                        String account = cursor.getString(cursor.getColumnIndex("account"));
                        String password = cursor.getString(cursor.getColumnIndex("password"));
                        Log.i("TAG", "accountGroup is " + accountGroup);
                        Log.i("TAG", "accountType is " + accountType);
                        Log.i("TAG", "account is " + account);
                        Log.i("TAG", "password is " + password);

                    } while (cursor.moveToNext());
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("TAG", "back button is pressed.");
//        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "MainActivity onDestroy is called.");
        if (dbOpenHlper != null) {
            dbOpenHlper.close();//关闭数据库的连接
        }
    }

}
