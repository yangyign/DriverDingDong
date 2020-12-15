package net.onest.driverdingdong.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.onest.driverdingdong.ConfigUtil;
import net.onest.driverdingdong.R;
import net.onest.driverdingdong.VedioActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class OrderFragment extends Fragment {
    private int id;
    private OrderViewModel orderViewModel;
    private TextView tvStatus;
    private TextView tvPrice;
    private TextView tvFrom;
    private TextView tvTo;
    private Button receive;
    private Button end;
    private String str;
    private String str0;
    private String strs;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(str.equals("true")){
                        tvStatus.setText("运行中");
                    }
                    break;
                case 2:
                    if(strs.equals("true")){
                        //
                        ConfigUtil.isHaveOrder = false;
                        tvStatus.setText("当前没有订单");
                        tvPrice.setText("");
                        tvFrom.setText("");
                        tvTo.setText("");
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        orderViewModel=new ViewModelProvider(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        tvFrom = root.findViewById(R.id.day_trip_time_begin_xia);
        tvTo = root.findViewById(R.id.day_trip_time_end_xia);
        tvStatus = root.findViewById(R.id.day_trip_state);
        tvPrice = root.findViewById(R.id.tv_price);
        receive = root.findViewById(R.id.btn_redio);
        end = root.findViewById(R.id.btn_end_order);
        if(ConfigUtil.isHaveOrder){
            String[] strs = ConfigUtil.order.getOrderName().split("-");
            id = ConfigUtil.order.getId();
            tvPrice.setText(ConfigUtil.order.getSpend());
            tvFrom.setText(strs[0]);
            tvTo.setText(strs[1]);
            updateStatus();
            //修改司机余额
            updateDriverMoney();
        }else {
            tvStatus.setText("当前没有订单");
        }
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VedioActivity.class);
                getContext().startActivity(intent);
            }
        });
        orderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置当前订单状态为已完成，并传递结束时间
                if(ConfigUtil.isHaveOrder){
                    endOrder();
                }
            }
        });
        return root;
    }

    private void endOrder() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                Log.e("结束时间",endTime);
                try {
                    URL url = new URL(ConfigUtil.xt+"EndOrderServlet?id="+ConfigUtil.id+"&endTime="+endTime);
                    InputStream is = url.openStream();
                    int len = 0;
                    byte[] b=new byte[512];
                    if((len=is.read(b))!=-1){
                        strs = new String(b,0,len);
                    }
                    Log.e("结束该订单的结果",strs);
                    Message message = new Message();
                    message.what = 2;
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

    private void updateDriverMoney() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"UpdateDriverMoneyServlet?id="+ConfigUtil.id+"&price="+ConfigUtil.order.getSpend());
                    InputStream is = url.openStream();
                    int len =0;
                    byte[] b = new byte[512];
                    if((len = is.read(b))!=-1){
                        str0 = new String(b,0,len);
                    }
                    Log.e("修改司机余额返回的结果",str0);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void updateStatus() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ChangeOrderStatusServlet?id="+id+"&status="+"运行中");
                    InputStream inputStream = url.openStream();
                    int len = 0;
                    byte[] b = new byte[512];
                    if((len = inputStream.read(b))!=-1){
                        str = new String(b,0,len);
                    }
                    Log.e("修改订单状态的结果",str);
                    Message message = new Message();
                    message.what=1;
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
}
