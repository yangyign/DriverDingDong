package net.onest.driverdingdong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {
    private TextView age;
    private TextView dAge;
    private TextView sex;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private TextView resgi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getViews();
        if(ConfigUtil.isRegister){
            //已经注册过
            resgi.setText("");
            rb1.setChecked(true);
            rb2.setChecked(true);
            rb3.setChecked(true);
            rb4.setChecked(true);
            ConfigUtil.check[0] = 1;
            try {
                JSONObject object = new JSONObject(ConfigUtil.jsonStr);
                age.setText(object.getInt("age")+"");
                sex.setText(object.getString("sex"));
                dAge.setText(object.getString("driverYear"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            resgi.setText("请您先注册");
        }
    }

    private void getViews() {
        resgi = findViewById(R.id.tv_regi);
        age = findViewById(R.id.tv_age);
        dAge = findViewById(R.id.tv_driver_age);
        sex = findViewById(R.id.tv_driver_sex);
        rb1 = findViewById(R.id.rb_user_iden);
        rb2=findViewById(R.id.rb_user_drive);
        rb3=findViewById(R.id.rb_car_check);
        rb4=findViewById(R.id.rb_user_trust);
    }
}