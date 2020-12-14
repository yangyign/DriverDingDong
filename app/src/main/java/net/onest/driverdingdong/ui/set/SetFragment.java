package net.onest.driverdingdong.ui.set;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.onest.driverdingdong.ActivityAboutDingDong;
import net.onest.driverdingdong.PolicyActivity;
import net.onest.driverdingdong.R;
import net.onest.driverdingdong.ServiceAgreementActivity;
import net.onest.driverdingdong.UserAgreementActivity;

public class SetFragment extends Fragment implements View.OnClickListener {
    private SetViewModel setViewModel;
    LinearLayout lGoodReputation;
    LinearLayout lUserAgreement;
    LinearLayout lServiceAgreement;
    LinearLayout lPersonMsg;
    LinearLayout lAboutDingDong;
    Button btnLogOff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setViewModel = new ViewModelProvider(this).get(SetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_set,container,false);
        btnLogOff = root.findViewById(R.id.btn_log_off);
        lGoodReputation = root.findViewById(R.id.ll_good_reputation);
        lUserAgreement = root.findViewById(R.id.ll_user_agreement);
        lServiceAgreement = root.findViewById(R.id.ll_service_agreement);
        lPersonMsg = root.findViewById(R.id.ll_person_msg);
        lAboutDingDong = root.findViewById(R.id.ll_about_dingdong);

        btnLogOff.setOnClickListener(this::onClick);
        lGoodReputation.setOnClickListener(this::onClick);
        lUserAgreement.setOnClickListener(this::onClick);
        lServiceAgreement.setOnClickListener(this::onClick);
        lPersonMsg.setOnClickListener(this::onClick);
        lAboutDingDong.setOnClickListener(this::onClick);
        setViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_off://退出登录
                Log.e("ActivitySetting", "btnLogOff onClicked");
//                if(ConfigUtil.isLogin){
//                    ConfigUtil.isLogin = false;
//                    //跳转到登录页面
//                    Intent intent = new Intent(AcitivySetting.this, ActivityLoginPage.class);
//                    startActivity(intent);
////                    finish();
//                }else {
//                    Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT);
//                }
                break;
            case R.id.ll_good_reputation://给叮咚好评
                Log.e("ActivitySetting", "lGoodReputation onClicked");
                break;
            case R.id.ll_user_agreement:
                Log.e("ActivitySetting", "lUserAgreement onClicked");
                Intent userIntent = new Intent(getContext(), UserAgreementActivity.class);
                getContext().startActivity(userIntent);
                break;
            case R.id.ll_service_agreement:
                Log.e("ActivitySetting", "lServiceAgreement onClicked");
                Intent serviceAgreementIntent = new Intent(getContext(), ServiceAgreementActivity.class);
                startActivity(serviceAgreementIntent);
                break;
            case R.id.ll_person_msg:
                Log.e("ActivitySetting", "lPersonMsg onClicked");
                Intent msgIntent = new Intent(getContext(), PolicyActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.ll_about_dingdong:
                Log.e("ActivitySetting", "lAboutDingDong onClicked");
                Intent aboutIntent = new Intent(getContext(), ActivityAboutDingDong.class);
                startActivity(aboutIntent);
                break;
        }
    }
}
