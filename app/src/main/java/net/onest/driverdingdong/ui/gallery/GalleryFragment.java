package net.onest.driverdingdong.ui.gallery;

import android.content.Intent;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.onest.driverdingdong.CalendarActivity;
import net.onest.driverdingdong.ConfigUtil;
import net.onest.driverdingdong.DayTrip;
import net.onest.driverdingdong.MyTripAdapter;
import net.onest.driverdingdong.Order;
import net.onest.driverdingdong.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private ImageView calendar;
    private View none;
    private ListView listView;
    private MyTripAdapter adapter;
    private StringBuffer stringBuffer;
    private List<Order> trips = new ArrayList<>();

    private GalleryViewModel galleryViewModel;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    if(trips!=null&&trips.size()!=0){
                        none.setVisibility(View.GONE);
                    }else {
                        none.setVisibility(View.VISIBLE);
                    }
                    adapter = new MyTripAdapter(getContext(),trips,R.layout.my_trip_item);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
        calendar = root.findViewById(R.id.calendar);
        none = root.findViewById(R.id.trip_none);
        listView = root.findViewById(R.id.my_trip_view);
//        trips = ConfigUtil.initTrips(trips);
        //获取司机行程
        getDriverRoad();
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getContext(), CalendarActivity.class);
                getContext().startActivity(intent);
            }
        });
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }

    private void getDriverRoad() {
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
                    Log.e("获取司机行程的结果",stringBuffer.toString());
                    JSONArray jsonArray = new JSONArray(stringBuffer.toString());
//                        wallets.clear();
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject object = new JSONObject(jsonArray.getJSONObject(i).toString());
                        Order order = new Order();
                        order.setTime(object.getString("time"));
                        order.setDate(object.getString("date"));
                        order.setOrderName(object.getString("from")+"-"+object.getString("to"));
                        trips.add(order);
                    }
                    Log.e("orders",trips.toString());
                    ConfigUtil.trips0 = trips;
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
}