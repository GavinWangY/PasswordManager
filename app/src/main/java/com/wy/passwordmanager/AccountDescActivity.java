package com.wy.passwordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class AccountDescActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_desc);
        AccountDesc accountDesc = (AccountDesc)getIntent().getSerializableExtra("account");
        showAccount(accountDesc);
    }

    private void showAccount(AccountDesc accountDesc){
        Log.i("TAG", accountDesc.getAccountGroup() + accountDesc.getAccountType() + accountDesc.getAccount() + accountDesc.getPassword());
        TextView textViewAccountGroup = findViewById(R.id.account_desc_group);
        textViewAccountGroup.setText(accountDesc.getAccountGroup());
        TextView textViewAccountType = findViewById(R.id.account_desc_type);
        textViewAccountType.setText(accountDesc.getAccountType());
        TextView textViewAccount = findViewById(R.id.account_desc_account);
        textViewAccount.setText(accountDesc.getAccount());
        TextView textViewPassword = findViewById(R.id.account_desc_password);
        textViewPassword.setText(accountDesc.getPassword());
    }
}
