package com.lilosoft.outsidescreen.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lilosoft.outsidescreen.R;
import com.lilosoft.outsidescreen.base.BaseActivity;
import com.lilosoft.outsidescreen.bean.Userinfo;
import com.lilosoft.outsidescreen.web.WCFService;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_work_no)
    EditText etWorkNo;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        final String work_no = etWorkNo.getText().toString();
        new AsyncTask<String, Integer, Userinfo>() {

            @Override
            protected Userinfo doInBackground(String... strings) {
                Userinfo user = null;
                try {
                    user = WCFService.gettabpage5and6(work_no);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return user;
            }

            @Override
            protected void onPostExecute(Userinfo s) {
                super.onPostExecute(s);
                if (s.getWorkno() != null) {
                    Intent intent = getIntent();
                    int flag = intent.getIntExtra("flag", 1);
                    if (flag == 1) {
                        nextActivity(MainActivity.class);
                    }
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "无效登录信息", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @OnClick(R.id.et_work_no)
    public void onClick() {
    }
}
