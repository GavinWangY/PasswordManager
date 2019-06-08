package com.wy.passwordmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountAddActivity extends AppCompatActivity {
    String TAG = "AccountAddActivity: ";
    DBOpenHlper dbOpenHlper;
    private EditText editTextAccountGroup, editTextAccountType, editTextAccount, editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);

        final AccountDesc accountDesc = new AccountDesc();

        dbOpenHlper = new DBOpenHlper(this, "tb_account", null, 1);

        editTextAccountGroup = findViewById(R.id.account_add_group);
        editTextAccountType = findViewById(R.id.account_add_type);
        editTextAccount = findViewById(R.id.account_add_account);
        editTextPassword = findViewById(R.id.account_add_password);

        Button buttonSvae = findViewById(R.id.account_add_save);
        buttonSvae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "save button is clicked.");
                //todo, 此处需要对输入数据进行判断。以确保保存的数据是有效的。
                if (editTextAccountType.getText().length() != 0 && editTextAccount.getText().length() != 0 && editTextPassword.getText().length() != 0) {
                    accountDesc.setAccountType(editTextAccountType.getText().toString());
                    accountDesc.setAccount(editTextAccount.getText().toString());
                    accountDesc.setPassword(editTextPassword.getText().toString());
                    if(editTextAccountGroup.getText().toString() != null){
                        accountDesc.setAccountGroup(editTextAccountGroup.getText().toString());
                    }
                    insertData(dbOpenHlper.getReadableDatabase(), accountDesc);
                    //返回到recent 页面
                    Intent intent = new Intent(AccountAddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.i(TAG, "请完整输入账户类型，账号和密码");
                    Toast.makeText(AccountAddActivity.this, "请完整输入账户类型，账号和密码", Toast.LENGTH_SHORT).show();
                }

            }
        });

        CheckBox checkBoxGeneratePassword = findViewById(R.id.account_add_auto_generate);
        checkBoxGeneratePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "checkBox changed.");
                if (buttonView.isChecked()) {
                    Log.i(TAG, "checkBox is selected.");
                    editTextPassword.setText(getRandomData(16));
                } else {
                    Log.i(TAG, "checkBox is unselected.");
                }
            }
        });

    }

    private static String getRandomData(int length) {
// String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890";
        String base = "abcdefghijklmnopqrstuvwxyz01234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < length; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
    }

    private void insertData(SQLiteDatabase sqLiteDatabase, AccountDesc accountDesc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_group", accountDesc.getAccountGroup());
        contentValues.put("account_type", accountDesc.getAccountType());
        contentValues.put("account", accountDesc.getAccount());
        contentValues.put("password", accountDesc.getPassword());
        sqLiteDatabase.insert("tb_account", null, contentValues);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "back button is pressed.");
//        onDestroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "AccountAddActivity onDestroy is called.");
        if (dbOpenHlper != null) {
            dbOpenHlper.close();//关闭数据库的连接
        }
    }
}
