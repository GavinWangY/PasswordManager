package com.wy.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class AccountDescActivity extends AppCompatActivity {
    String TAG = "AccountDescActivity: ";
    AccountDesc accountDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_desc);
        accountDesc = (AccountDesc)getIntent().getSerializableExtra("account");
        showAccount(accountDesc);

        Button buttonCopyAccount = findViewById(R.id.button_account_desc_copy_account);
        Button buttonCopyPassword = findViewById(R.id.button_account_desc_copy_password);

        buttonCopyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "copy account button is clicked.");
                if (setClipboard(accountDesc.getAccount())) {
                    Toast.makeText(AccountDescActivity.this, "复制账户成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "copy password button is clicked.");
                if (setClipboard(accountDesc.getPassword())) {
                    Toast.makeText(AccountDescActivity.this, "复制密码成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAccount(AccountDesc accountDesc){
        Log.i(TAG, accountDesc.getAccountGroup() + accountDesc.getAccountType() + accountDesc.getAccount() + accountDesc.getPassword());
        TextView textViewAccountGroup = findViewById(R.id.account_desc_group);
        textViewAccountGroup.setText(accountDesc.getAccountGroup());
        TextView textViewAccountType = findViewById(R.id.account_desc_type);
        textViewAccountType.setText(accountDesc.getAccountType());
        TextView textViewAccount = findViewById(R.id.account_desc_account);
        textViewAccount.setText(accountDesc.getAccount());
        TextView textViewPassword = findViewById(R.id.account_desc_password);
        textViewPassword.setText(accountDesc.getPassword());
    }

    private Boolean setClipboard(String str){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(MainActivity.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", str);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Log.i(TAG, "copy account " + str + " successful.");
        return true;
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
        Log.i(TAG, "AccountDescActivity onDestroy is called.");
    }
}
