package net.onest.driverdingdong;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView view;
    private View view_none;
    private ListView listView;
    private MyTripAdapter adapter;
    private List<Order> trips = new ArrayList<>();
    private List<Order> trr = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        trr = ConfigUtil.trips0;
        Log.e("trips",trips.toString());
        LocalDate date = LocalDate.now();
        String date_now = date.toString();
        Log.e("date_now",date_now);
        for (Order trip : trr){
            if (trip.getDate().equals(date_now)){
                trips.add(trip);
            }
        }
        Log.e("trr0",trips.toString());
        adapter = new MyTripAdapter(this,trips,R.layout.my_trip_item);
        view = findViewById(R.id.calendar_view);
        view_none = findViewById(R.id.calendar_none);
        listView = findViewById(R.id.lv_calendar_trip);
        if (trips!=null&&trips.size()!=0){
            view_none.setVisibility(View.GONE);
        }else {
            view_none.setVisibility(View.VISIBLE);
        }
        if (getActionBar() != null){
            getActionBar().hide();
        }
        listView.setAdapter(adapter);
        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                trips.clear();
                String date1 = i+"-"+(i1+1)+"-"+i2;
                for (Order trip : trr){
                    if (trip.getDate().equals(date1)){
                        trips.add(trip);
                    }
                }
                Log.e("trr1",trips.toString());
                if (trips!=null&&trips.size()!=0){
                    view_none.setVisibility(View.GONE);
                    adapter = new MyTripAdapter(getApplicationContext(),trips,R.layout.my_trip_item);
                    listView.setAdapter(adapter);
                }else {
                    view_none.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
