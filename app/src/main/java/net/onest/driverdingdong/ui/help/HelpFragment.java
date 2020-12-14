package net.onest.driverdingdong.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.onest.driverdingdong.R;
import net.onest.driverdingdong.ui.set.SetViewModel;

public class HelpFragment extends Fragment {
    private HelpViewModel helpViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        helpViewModel = new ViewModelProvider(this).get(HelpViewModel.class);
        View root = inflater.inflate(R.layout.changjianwenti,container,false);
//        final TextView textView = root.findViewById(R.id.text_help);
        helpViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}
