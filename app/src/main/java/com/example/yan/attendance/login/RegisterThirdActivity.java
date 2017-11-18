package com.example.yan.attendance.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.yan.attendance.ActivityFunction.BaseActivity;
import com.example.yan.attendance.MainActivity;
import com.example.yan.attendance.R;

public class RegisterThirdActivity extends BaseActivity {

    private Button mOverBtn;
    private EditText realNameEdit, studentIDEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_third);
        initView();
        mOverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("userData",MODE_PRIVATE).edit();
                editor.putString("realName",realNameEdit.getText().toString());
                editor.putString("studentID", studentIDEdit.getText().toString());
                editor.commit();

                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }

    private void initView(){
        mOverBtn =(Button) findViewById(R.id.register_over);
        realNameEdit = (EditText) findViewById(R.id.real_name_edit_text);
        studentIDEdit = (EditText) findViewById(R.id.student_id_edit_text);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            int myColor = getResources().getColor(R.color.registerButton);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(myColor);
        }
    }
}
