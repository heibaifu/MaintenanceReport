package com.lxl.valvedemo.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxl.valvedemo.R;
import com.lxl.valvedemo.util.IOHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangleiliu on 2017/8/24.
 * 账户注册
 */
public class StockRegisterActivity extends Activity implements View.OnClickListener {

    TextView mNextStep;
    EditText mPhoneEdit;

    List<String> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_register_layout);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        try {
            InputStream user = getAssets().open("user.txt");
            List<String> strings = IOHelper.readListStrByCode(user, "utf-8");
            mUserList.clear();
            mUserList.addAll(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mNextStep = (TextView) findViewById(R.id.stock_register_next_step);
        mPhoneEdit = (EditText) findViewById(R.id.stock_register_phone_edit);
    }

    private void initListener() {
        mNextStep.setOnClickListener(this);
        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mNextStep.setEnabled(true);
                } else {
                    mNextStep.setEnabled(false);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.stock_register_next_step) {
            String user = mPhoneEdit.getText().toString();
            handleGoToNext(user);
        }
    }

    private void handleGoToNext(String user) {
        if (!(mUserList.size() == 0 || mUserList.contains(user))) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, SelectActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(Activity.RESULT_OK);
        finish();
    }
}
