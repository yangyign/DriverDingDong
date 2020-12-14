package net.onest.driverdingdong;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {
    public static int id = 0;
    //判断当前用户是否登录
    public static boolean isLogin = false;
    //判断用户是否注册司机
    public static boolean isRegister = false;
    //是否提交认证
    public static boolean isAuthen = false;
    //当前是否有订单
    public static boolean isHaveOrder = false;
    //获取当前接单的信息
    public static Order order = new Order();
    //当前手机号
    public static String phone="";
    public static String jsonStr = "";
    public static int[] check = {0,0,0,0};
    //徐婷连接到服务器
    public static final String xt = "http://10.7.90.141:8080/Dingdongg/";
    public static List<Order> trips0 = new ArrayList<>();
    public static List<DayTrip> initTrips(List<DayTrip> trips){
        for(int i = 0; i < 5 ; i++){
            DayTrip dayTrip = new DayTrip();
            dayTrip.setGoOrCome("放学"+i);
            dayTrip.setDate("2020-12-11");
            dayTrip.setTimeBegin("16:40");
            dayTrip.setTripState("运行中");
            dayTrip.setTimeEnd("17:00");
            dayTrip.setPlaceBegin("徐汇区实验小学");
            dayTrip.setPlaceEnd("望春园西门");
            trips.add(dayTrip);
        }
        return trips;
    }
}
