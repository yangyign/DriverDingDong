package net.onest.driverdingdong;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.onest.driverdingdong.ui.order.OrderFragment;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderListAdapter extends BaseAdapter {
    private List<Order> dayTripList=new ArrayList<>();
    private Context context;
    private int resId;
    private String str;

    public OrderListAdapter(List<Order> dayTripList, Context context, int resId) {
        this.dayTripList = dayTripList;
        this.context = context;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        if(dayTripList!=null){
            return dayTripList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(dayTripList!=null){
            return dayTripList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Myholder viewHolder = null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resId,null);
            viewHolder = new Myholder();
            viewHolder.goOrCome = convertView.findViewById(R.id.day_trip_go_or_come);
            viewHolder.date = convertView.findViewById(R.id.day_trip_date);
            viewHolder.timeBegin = convertView.findViewById(R.id.day_trip_time_begin);
            viewHolder.tripState = convertView.findViewById(R.id.day_trip_state);
            viewHolder.placeBegin = convertView.findViewById(R.id.day_trip_place_begin);
            viewHolder.placeEnd = convertView.findViewById(R.id.day_trip_place_end);
            viewHolder.tripInfo=convertView.findViewById(R.id.item_day_trip_btn_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (Myholder) convertView.getTag();
        }
        String[] strs = dayTripList.get(position).getOrderName().split("-");
        viewHolder.goOrCome.setText(dayTripList.get(position).getName());
        viewHolder.date.setText(dayTripList.get(position).getDate());
        viewHolder.timeBegin.setText(dayTripList.get(position).getTime());
        viewHolder.placeBegin.setText(strs[0]);
        viewHolder.placeEnd.setText(strs[1]);
        viewHolder.tripState.setText(dayTripList.get(position).getStatus());
        viewHolder.tripInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfigUtil.isRegister){
                    //修改司机状态为 忙碌
                    updateDriverStatus();
                    ConfigUtil.order.setId(dayTripList.get(position).getId());
                    ConfigUtil.order.setSpend(dayTripList.get(position).getSpend());
                    ConfigUtil.order.setOrderName(dayTripList.get(position).getOrderName());
                    ConfigUtil.isHaveOrder = true;
                    dayTripList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    private void updateDriverStatus() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(ConfigUtil.xt+"ChangeDriverStatesServlet?id="+ConfigUtil.id+"&status="+"忙碌");
                    InputStream is = url.openStream();
                    int len = 0;
                    byte[] b = new byte[512];
                    if((len = is.read(b))!=-1){
                        str = new String(b,0,len);
                    }
                    Log.e("修改司机状态为忙碌的结果",str);
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class Myholder{
        private TextView goOrCome;
        private TextView date;
        private TextView timeBegin;
        private TextView placeBegin;
        private TextView placeEnd;
        private TextView tripState;
        private Button tripInfo;
    }
}
