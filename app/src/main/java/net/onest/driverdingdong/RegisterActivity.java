package net.onest.driverdingdong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private int colors;
    private String str0;
    private TextView status;
    private OkHttpClient okHttpClient;
    private String str;
    private EditText name;
    private EditText age;
    private EditText no;
    private EditText sex;
    private EditText carNo;
    private EditText carType;
    private EditText driverYear;
    private ImageView userId;
    private ImageView ivCarNo;
    private ImageView carId;
    private ImageView m1;
    private ImageView m2;
    private ImageView m3;
    private String color;
    private Spinner spinner;
    private Driver driver;
    private Button submit;
    private String ip1;
    private String ip2;
    private String ip3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    status.setText("待审核");
                    finish();
                    break;
                case 2:
                    if(str0.equals("认证通过")){
                        ConfigUtil.isRegister=true;
                        submit.setVisibility(View.INVISIBLE);
                        //显示三张图片
                        ConfigUtil.isRegister=true;
                        status.setText(str0);
                        RequestOptions options = new RequestOptions().centerCrop();
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=1")
                                .apply(options)
                                .into(userId);
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=2")
                                .apply(options)
                                .into(ivCarNo);
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=3")
                                .apply(options)
                                .into(carId);
                        Glide.with(RegisterActivity.this)
                                .load(R.drawable.mi)
                                .apply(options)
                                .into(m1);
                        Glide.with(RegisterActivity.this)
                                .load(R.drawable.mi)
                                .apply(options)
                                .into(m2);
                        Glide.with(RegisterActivity.this)
                                .load(R.drawable.mi)
                                .apply(options)
                                .into(m3);
                        //将数据填入该界面
                        try {
                            JSONObject object = new JSONObject(ConfigUtil.jsonStr);
                            name.setText(object.get("name")+"");
                            age.setText(object.getInt("age")+"");
                            sex.setText(object.getString("sex"));
                            no.setText(object.getString("userId"));
                            carNo.setText(object.getString("carId"));
                            carType.setText(object.getString("carType"));
                            driverYear.setText(object.getString("driverYear"));
                            switch (object.getString("color")){
                                case "黑色":
                                    colors=0;
                                    break;
                                case "白色":
                                    colors=1;
                                    break;
                                case "银色":
                                    colors=2;
                                    break;
                                case "灰色":
                                    colors=3;
                                    break;
                                case "红色":
                                    colors=4;
                                    break;
                                case "蓝色":
                                    colors=5;
                                    break;
                                case "金色":
                                    colors=6;
                                    break;
                            }
                            spinner.setSelection(colors);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(!ConfigUtil.isAuthen && str0.equals("未认证")){
                        submit.setVisibility(View.VISIBLE);
                        status.setText(str0);
                    }else if(ConfigUtil.isAuthen && str0.equals("未认证")){
                        status.setText("待审核");
                        //显示三张图片
                        RequestOptions options = new RequestOptions().centerCrop();
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=1")
                                .apply(options)
                                .into(userId);
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=2")
                                .apply(options)
                                .into(ivCarNo);
                        Glide.with(RegisterActivity.this)
                                .load(ConfigUtil.xt+"ReturnDriverZhengjianImageServlet?id="+ConfigUtil.id+"&imgId=3")
                                .apply(options)
                                .into(carId);
                        //将数据填入该界面
                        try {
                            JSONObject object = new JSONObject(ConfigUtil.jsonStr);
                            name.setText(object.get("name")+"");
                            age.setText(object.getInt("age")+"");
                            sex.setText(object.getString("sex"));
                            no.setText(object.getString("userId"));
                            carNo.setText(object.getString("carId"));
                            carType.setText(object.getString("carType"));
                            driverYear.setText(object.getString("driverYear"));
                            switch (object.getString("color")){
                                case "黑色":
                                    colors=0;
                                    break;
                                case "白色":
                                    colors=1;
                                    break;
                                case "银色":
                                    colors=2;
                                    break;
                                case "灰色":
                                    colors=3;
                                    break;
                                case "红色":
                                    colors=4;
                                    break;
                                case "蓝色":
                                    colors=5;
                                    break;
                                case "金色":
                                    colors=6;
                                    break;
                            }
                            spinner.setSelection(colors);
                            submit.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_register);
        okHttpClient = new OkHttpClient();
        getViews();
        getDriverStatu();
        userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);
            }
        });
        ivCarNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        200);
            }
        });
        carId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        300);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color = getResources().getStringArray(R.array.color)[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("提交认证申请",initStr());
                identiDriver();
            }
        });

    }

    private void getDriverStatu() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ShowDriverStateServlet?id="+ConfigUtil.id);
                    InputStream inputStream = url.openStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    if((len = inputStream.read(b))!=-1){
                        str0 = new String(b,0,len);
                    }
                    Log.e("结果",str0);
                    Message message = new Message();
                    message.what=2;
                    handler.sendMessage(message);
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void identiDriver() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"CheckDriverServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    os.write(initStr().getBytes());
                    InputStream inputStream = connection.getInputStream();
                    int len = 0;
                    byte[] bytes = new byte[512];
                    if((len = inputStream.read(bytes))!=-1){
                        str = new String(bytes,0,len);
                    }
                    ConfigUtil.isAuthen=true;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    os.close();
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String initStr() {
        JSONObject object = new JSONObject();
        try {
            object.put("id",ConfigUtil.id);
            object.put("name",name.getText());
            object.put("age",Integer.parseInt(age.getText()+""));
            object.put("sex",sex.getText());
            object.put("userId",no.getText());
            object.put("carId",carNo.getText());
            object.put("carType",carType.getText());
            object.put("color",color);
            object.put("driverYear",driverYear.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ConfigUtil.jsonStr = object.toString();
        return object.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,10);
        }else if(requestCode==200){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,20);
        }else if(requestCode==300){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,30);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10&&resultCode==RESULT_OK){
            ContentResolver contentResolver = getContentResolver();
            Uri uri = data.getData();
            Cursor cursor = contentResolver.query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                ip1 = cursor.getString(cursor.getColumnIndex("_data"));
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(RegisterActivity.this)
                        .load(ip1)
                        .apply(requestOptions)
                        .into(userId);
                uploadFile(ip1,1);
            }
        }else if(requestCode==20&&resultCode==RESULT_OK){
            ContentResolver contentResolver = getContentResolver();
            Uri uri = data.getData();
            Cursor cursor = contentResolver.query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                ip2 = cursor.getString(cursor.getColumnIndex("_data"));
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(RegisterActivity.this)
                        .load(ip2)
                        .apply(requestOptions)
                        .into(ivCarNo);
                uploadFile(ip2,2);
            }
        }else if(requestCode==30&&resultCode==RESULT_OK){
            ContentResolver contentResolver = getContentResolver();
            Uri uri = data.getData();
            Cursor cursor = contentResolver.query(uri,null,null,null,null);
            if(cursor.moveToFirst()){
                ip3 = cursor.getString(cursor.getColumnIndex("_data"));
                RequestOptions requestOptions = new RequestOptions().centerCrop();
                Glide.with(RegisterActivity.this)
                        .load(ip3)
                        .apply(requestOptions)
                        .into(carId);
                uploadFile(ip3,3);
            }
        }
    }

    private void getViews() {
        m1 = findViewById(R.id.iv_mi);
        m2 = findViewById(R.id.iv_mi1);
        m3 = findViewById(R.id.iv_mi2);
        submit = findViewById(R.id.btn_submit);
        status = findViewById(R.id.rz_status);
        sex = findViewById(R.id.edt_sex);
        name = findViewById(R.id.edt_name);
        age = findViewById(R.id.edt_age);
        no = findViewById(R.id.edt_id_no);
        carNo = findViewById(R.id.edt_car_no);
        carType = findViewById(R.id.edt_car_type);
        driverYear=findViewById(R.id.edt_driver_year);
        userId = findViewById(R.id.iv_user_no);
        ivCarNo=findViewById(R.id.iv_driver_no);
        carId=findViewById(R.id.iv_car_id);
        spinner = findViewById(R.id.color);
    }
    private void uploadFile(String imagePath,int imgId) {

        File file = new File(imagePath);
        //2.创建请求体对象
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        //3.创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)  //使用post请求方法
                .url(ConfigUtil.xt+"ShowCheckDriverServlet?id="+ConfigUtil.id+"&imgId="+imgId)
                .build();
        Log.e("图片请求servlet",ConfigUtil.xt+"ShowCheckDriverServlet?id="+ConfigUtil.id+"&imgId="+imgId);
        //4.创建Call对象
        Call call = okHttpClient.newCall(request);
        //异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("上传文件结果",response.body().string());
            }
        });
    }
}