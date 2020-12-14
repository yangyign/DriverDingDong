package net.onest.driverdingdong.ui.wallet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.onest.driverdingdong.CalendarActivity;
import net.onest.driverdingdong.ConfigUtil;
import net.onest.driverdingdong.Order;
import net.onest.driverdingdong.R;
import net.onest.driverdingdong.WalletDetailsAdapter;
import net.onest.driverdingdong.ui.slideshow.SlideshowViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment {
    private WalletViewModel walletViewModel;
    private TextView money;
    private ListView listView;
    private List<Order> orders = new ArrayList<>();
    private WalletDetailsAdapter adapter;
    private String string;
    private StringBuffer stringBuffer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(Double.parseDouble(string)!=-1.0){
                        //获取到的余额
                        money.setText(string);
                    }
                    break;
                case 2:
                    adapter = new WalletDetailsAdapter(R.layout.wallet_item_list,getContext(),orders);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        money = root.findViewById(R.id.tv_money);
        listView = root.findViewById(R.id.list);

        //通过司机id获取用户money
        if(ConfigUtil.id!=0){
            getMoney();
            //根据司机id获取订单信息 from、to、花费、时间、司机余额
            getDriverOrder();
        }
        walletViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void getDriverOrder() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"GetDriverOriderServlet?id="+ConfigUtil.id);
                    InputStream inputStream = url.openStream();
                    stringBuffer = new StringBuffer();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b))!=-1){
                        String str = new String(b,0,len);
                        stringBuffer.append(str);
                    }
                    Log.e("接收司机订单的结果",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
//                        wallets.clear();
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                        Order order = new Order();
                        order.setTime(object.getString("time"));
                        order.setBalance(object.getDouble("price")+"");
                        order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                        order.setSpend("+"+object.getDouble("spend"));
                        orders.add(order);
                    }
                    Log.e("orders",orders.toString());
                    Message message = new Message();
                    message.what=2;
                    handler.sendMessage(message);
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getMoney() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"GetDriverMoneyServlet?id="+ConfigUtil.id);
                    InputStream is = url.openStream();
                    int len = 0;
                    byte[] b = new byte[512];
                    if((len = is.read(b))!=-1){
                        string = new String(b,0,len);
                    }
                    Log.e("获取余额",string);
                    Message message = new Message();
                    message.what = 1;
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
