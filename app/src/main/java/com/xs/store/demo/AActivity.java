package com.xs.store.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xs.store.Call;
import com.xs.store.StoreCallback;
import com.xs.store.test.AAction;
import com.xs.store.test.BAction;
import com.xs.store.test.In;
import com.xs.store.test.Out;
import com.xs.store.test.AStore;

import com.xs.store.demo.R;


/**
 * Created by wangzhengtao767 on 2019/5/9.
 */

public class AActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "BActivity";

    private TextView tvName;
    private TextView tvAge;
    private TextView tvMessage;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        assignViews();

        storeObserver.refresh(new Call<AStore>() {

            @Override
            public void handle(AStore testStore) {

                tvName.setText(testStore.getName());
                tvAge.setText(testStore.getAge() + "");
                if (testStore.getIdea() != null) {
                    tvMessage.setText(testStore.getIdea().getMessage());
                }
            }
        });
    }

    private void assignViews() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        btnA = (Button) findViewById(R.id.btn_a);
        btnB = (Button) findViewById(R.id.btn_b);
        btnC = (Button) findViewById(R.id.btn_c);
        btnD = (Button) findViewById(R.id.btn_d);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a:
                storeObserver.handAciton(AAction.class, new In("AA"), new StoreCallback<In, Out>() {
                    @Override
                    public void success(In in, Out o) {
                        logTime(TAG + "==" + "AA");
                    }

                    @Override
                    public void error(int code, String message, Throwable e) {

                    }
                });
                break;
            case R.id.btn_b:
                storeObserver.handAciton(BAction.class, new In("AB"), new StoreCallback<In, Out>() {
                    @Override
                    public void success(In in, Out o) {
                        logTime(TAG + "==" + "AB");
                    }

                    @Override
                    public void error(int code, String message, Throwable e) {

                    }
                });
                break;
            case R.id.btn_c:
                startActivity(new Intent(this,BActivity.class));
                break;
            case R.id.btn_d:
                startActivity(new Intent(this,CActivity.class));
                break;
        }
    }
}
