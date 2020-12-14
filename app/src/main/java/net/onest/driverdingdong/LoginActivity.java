package net.onest.driverdingdong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

public class LoginActivity extends AppCompatActivity {
    private EditText phone;
    private EditText pwd;
    private Button login;
    private String str;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(Integer.parseInt(str)!=0){
                        //登录成功
                        ConfigUtil.isLogin = true;
                        ConfigUtil.id = Integer.parseInt(str);
                        ConfigUtil.phone = phone.getText().toString().trim();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                      //根据获取手机号获取id
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone = findViewById(R.id.edt_tel);
        pwd = findViewById(R.id.edt_pwd);
        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText()!=null&&phone.getText().length()!=0&&phone.getText().length()==11){
                    if(phone.getText()!=null&&phone.getText().length()!=0){
                        //向服务端发起请求
                        getLoginInfo();
                    }
                }
            }
        });
    }
    private String toStr(){
        JSONObject object = new JSONObject();
        try {
            object.put("phone",phone.getText());
            object.put("password",pwd.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
    private void getLoginInfo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"DriverLoginServlet");
                    Log.e("urll",ConfigUtil.xt+"DriverLoginServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    Log.e("sttttt",toStr());
                    OutputStream os = connection.getOutputStream();
                    os.write(toStr().getBytes());
                    InputStream is = connection.getInputStream();
                    int len = 0;
                    byte[] b = new byte[512];
                    if((len=is.read(b))!=-1){
                        str = new String(b,0,len);
                    }
                    Log.e("登录的结果",str);
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}