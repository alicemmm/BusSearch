package com.shouyi.xue.bussearch.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shouyi.xue.bussearch.R;
import com.shouyi.xue.bussearch.RequestManager;
import com.shouyi.xue.bussearch.Utils;

/**
 * Created by asia on 2018/3/4.
 */

public class BusLineFragment extends Fragment {

    public static BusLineFragment getInstance(Bundle bundle) {

        BusLineFragment busLineFragment = new BusLineFragment();
        if (bundle != null)
            busLineFragment.setArguments(bundle);

        return busLineFragment;
    }

    protected Handler handler = new Handler();

    private AppCompatEditText cityEt;
    private AppCompatEditText busEt;

    private Button searchBtn;
    private TextView resTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_line,container,false);
        cityEt = view.findViewById(R.id.search_city_et);
        busEt = view.findViewById(R.id.search_bus_et);
        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
            }
        });

        resTv = view.findViewById(R.id.search_res);
        return view;
    }

    private void clickSearch(){
        final String city = cityEt.getText().toString().trim();
        final String bus = busEt.getText().toString().trim();

        if(TextUtils.isEmpty(city) || TextUtils.isEmpty(bus)){
            Toast.makeText(getActivity(),"请输入正确的查询信息",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String res = RequestManager.requestForBusLineId(city, bus);



//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Utils.e("tag","res="+res);
//                        doClickSearchBus(res);
//                    }
//                });
            }
        }).start();
    }
}
