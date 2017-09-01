package com.jzp.rotate3d.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jzp.rotate3d.Rotate3D;

public class MainActivity extends AppCompatActivity {

    Rotate3D anim;
    private LinearLayout account_login_ll;
    private LinearLayout account_phone_ll;
    private LinearLayout parent_ll;
    private TextView no_pass_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account_phone_ll = (LinearLayout) findViewById(R.id.account_phone_ll);
        account_login_ll = (LinearLayout) findViewById(R.id.account_login_ll);
        parent_ll = (LinearLayout) findViewById(R.id.parent_ll);
        no_pass_login = (TextView) findViewById(R.id.no_pass_login);

        anim = new Rotate3D.Builder(this)
                .bindParentView(parent_ll)
                .bindPositiveView(account_login_ll)
                .bindNegativeView(account_phone_ll)
                .create();

        no_pass_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim.transform();
                if (anim.isOpen()) {
                    no_pass_login.setText("使用免密登录");
                } else {
                    no_pass_login.setText("使用账户登录");
                }
            }
        });
    }
}
