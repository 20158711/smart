package com.example.yanbi.smart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanbi.smart.MainActivity;
import com.example.yanbi.smart.R;
import com.example.yanbi.smart.entity.MyUser;
import com.example.yanbi.smart.utils.ShareUtils;
import com.example.yanbi.smart.utils.StaticClass;
import com.example.yanbi.smart.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by yanbi on 2017/9/11.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private TextView tv_forget;
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_registered= (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);

        et_name= (EditText) findViewById(R.id.et_name);
        et_password= (EditText) findViewById(R.id.et_password);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        keep_password= (CheckBox) findViewById(R.id.keep_password);
        tv_forget= (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        dialog=new CustomDialog(this, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,R.layout.dialog_loding,R.style.pop_anim_style, Gravity.CENTER,0);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //读取之前保存的选中状态
        boolean isChecked = ShareUtils.getBoolean(this, StaticClass.LOGIN_KEEP_PASS, false);
        if(isChecked){
            keep_password.setChecked(true);
            et_name.setText(ShareUtils.getString(this,StaticClass.LOGIN_NAME,""));
            et_password.setText(ShareUtils.getString(this,StaticClass.LOGIN_PASSWORD,""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registered:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.btnLogin:
                String name = et_name.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                if(!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(password)){
                    dialog.show();
                    final MyUser user=new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if(e==null){

                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "邮箱未验证", Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                Toast.makeText(LoginActivity.this, "登录失败"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this, StaticClass.LOGIN_KEEP_PASS,keep_password.isChecked());

        if(keep_password.isChecked()){
            //记住密码
            ShareUtils.putString(this,StaticClass.LOGIN_NAME,et_name.getText().toString().trim());
            ShareUtils.putString(this,StaticClass.LOGIN_PASSWORD,et_password.getText().toString().trim());
        }else {
            ShareUtils.deleShare(this,StaticClass.LOGIN_NAME);
            ShareUtils.deleShare(this,StaticClass.LOGIN_PASSWORD);
        }
    }
}
