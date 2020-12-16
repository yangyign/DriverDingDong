package net.onest.driverdingdong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyTripAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order> trips = new ArrayList<>();
    private int itemLayoutRes;

    public MyTripAdapter(Context mContext, List<Order> trips, int itemLayoutRes) {
        this.mContext = mContext;
        this.trips = trips;
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getCount() {
        if (null!=trips){
            return trips.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (null!=trips){
            return trips.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String[] str = trips.get(i).getOrderName().split("-");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(itemLayoutRes, null);
        final TextView tv_date = view.findViewById(R.id.my_trip_date);
        final TextView tv_state = view.findViewById(R.id.my_trip_place_end);
        final TextView tv_begin = view.findViewById(R.id.my_trip_place_begin);
        final TextView tv_Status = view.findViewById(R.id.my_trip_state);
        tv_date.setText(trips.get(i).getDate()+" "+trips.get(i).getTime());
        tv_state.setText(str[1]);
        tv_begin.setText(str[0]);
        tv_Status.setText(trips.get(i).getStatus());
        return view;
    }
}
