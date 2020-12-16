package net.onest.driverdingdong.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.onest.driverdingdong.ConfigUtil;
import net.onest.driverdingdong.DayTrip;
import net.onest.driverdingdong.Order;
import net.onest.driverdingdong.OrderListAdapter;
import net.onest.driverdingdong.R;
import net.onest.driverdingdong.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SNIHostName;

public class HomeFragment extends Fragment {
    private SmartRefreshLayout refreshLayout;
    private List<Order> trips = new ArrayList<>();
    private OrderListAdapter adapter;
    private HomeViewModel homeViewModel;
    private ImageView imageView;
    private Button button;
    private ListView listView;
    private StringBuffer stringBuffer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //重新从客户端请求数据
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    break;
                case 2:
                    adapter=new OrderListAdapter(trips,getContext(),R.layout.item_recycleview_day_trip);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listView = root.findViewById(R.id.listview);
        refreshLayout = root.findViewById(R.id.first_refreshLayout);
        imageView = root.findViewById(R.id.iv);
        button = root.findViewById(R.id.btn_register);
        //获取orders。从服务端获取 订单状态是待接单、司机id是ConfigUtil.id的订单显示
        //点击接单，跳转到一个新的activity。
        if(ConfigUtil.isRegister){
            getMyOrders();
        }
//        trips = ConfigUtil.initTrips(trips);
        //设置刷新头和加载更多的样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //上拉刷新下拉加载更多的事件处理
        setListener();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    private void getMyOrders() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    stringBuffer = new StringBuffer();
                    URL url = new URL(ConfigUtil.xt+"WaitRunServlet?id="+ConfigUtil.id);
                    InputStream inputStream = url.openStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b))!=-1){
                        String str = new String(b,0,len);
                        stringBuffer.append(str);
                    }
                    Log.e("获取司机待接单信息的结果",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
//                        wallets.clear();
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                        Order order = new Order();
                        order.setTime(object.getString("time"));
                        order.setDate(object.getString("date"));
                        order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                        order.setName(object.getString("address"));
                        order.setSpend(object.getString("price"));
                        order.setId(object.getInt("order_id"));
                        trips.add(order);
                    }
                    Log.e("orders",trips.toString());
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

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新  请求服务端
                getAddOrders();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }
    private void getAddOrders() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    stringBuffer = new StringBuffer();
                    URL url = new URL(ConfigUtil.xt+"WaitRunServlet?id="+ConfigUtil.id);
                    InputStream inputStream = url.openStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = inputStream.read(b))!=-1){
                        String str = new String(b,0,len);
                        stringBuffer.append(str);
                    }
                    Log.e("获取司机待接单信息的结果",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
//                        wallets.clear();
                    trips.clear();
                    Log.e("长度",trips.size()+"");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                        Order order = new Order();
                        order.setTime(object.getString("time"));
                        order.setDate(object.getString("date"));
                        order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                        order.setName(object.getString("address"));
                        order.setSpend(object.getString("price"));
                        order.setId(object.getInt("order_id"));
                        trips.add(order);
                    }
                    Log.e("orders",trips.toString());
                    Message message = new Message();
                    message.what=1;
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
}