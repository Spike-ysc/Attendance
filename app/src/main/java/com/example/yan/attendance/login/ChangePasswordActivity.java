package com.example.yan.attendance.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yan.attendance.ActivityFunction.BaseActivity;
import com.example.yan.attendance.R;

public class ChangePasswordActivity extends BaseActivity {

    private Button sureBtn;
    private EditText passwordEdit;
    private String password;
    private CheckBox showPaswordBox;
    private ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        passwordEdit = (EditText) findViewById(R.id.change_password_edit);
        sureBtn = (Button) findViewById(R.id.sure_button);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = passwordEdit.getText().toString();
                if (password.length()>=6){
                    SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
                    editor.putString("password", password);
                    editor.commit();
                    Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                    sendBroadcast(intent);
                }else {
                    Toast.makeText(ChangePasswordActivity.this,"密码不够六位",Toast.LENGTH_SHORT).show();
                }

            }
        });
        showPaswordBox = (CheckBox) findViewById(R.id.change_checkbox);
        showPaswordBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEdit.setSelection(passwordEdit.length());
                }else {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEdit.setSelection(passwordEdit.length());
                }
            }
        });
        backImg = (ImageView) findViewById(R.id.back_icon);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
