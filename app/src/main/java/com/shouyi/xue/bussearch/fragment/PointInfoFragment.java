package com.shouyi.xue.bussearch.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shouyi.xue.bussearch.R;
import com.shouyi.xue.bussearch.RequestManager;
import com.shouyi.xue.bussearch.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asia on 2018/3/4.
 */

public class PointInfoFragment extends Fragment {

    public static PointInfoFragment getInstance(Bundle bundle) {

        PointInfoFragment pointInfoFragment = new PointInfoFragment();
        if (bundle != null)
            pointInfoFragment.setArguments(bundle);

        return pointInfoFragment;
    }

    protected Handler handler = new Handler();

    private AppCompatEditText cityEt;
    private AppCompatEditText siteEt;

    private Button searchBtn;
    private TextView searchResTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_info,container,false);
        cityEt= view.findViewById(R.id.search_city_et);
        siteEt=view.findViewById(R.id.search_site_et);
        searchBtn=view.findViewById(R.id.search_btn);
        searchResTv = view.findViewById(R.id.search_res);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
            }
        });
        return view;
    }

    private void clickSearch(){
        final String city = cityEt.getText().toString().trim();
        final String site = siteEt.getText().toString().trim();

        if(TextUtils.isEmpty(city) || TextUtils.isEmpty(site)){
            Toast.makeText(getActivity(),"请输入正确的查询信息",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String res = RequestManager.requestForBusPoint(city,site);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Utils.e("tag","res="+res);
                                doClickSearchBus(res);
                            }
                        });
                    }
                }).start();
    }


    private void doClickSearchBus(String res) {
        try {
            JSONObject jsonObject = new JSONObject(res);
            int code = jsonObject.getInt("showapi_res_code");
            if(code == 0){
                JSONObject body = jsonObject.getJSONObject("showapi_res_body");
                int retCode = body.getInt("ret_code");
                if(retCode == 0){
                    JSONArray jsonArray = body.getJSONArray("line");
                    if(jsonArray.length()>0){
                       JSONObject lineJson = (JSONObject) jsonArray.get(0);
                       String siteName = lineJson.getString("siteName");
                       JSONArray linesJson = lineJson.getJSONArray("linkNameList");
                       Log.e("tag", "doClickSearchBus: "+siteName);
                        List<String> lineList = new ArrayList<>();

                        Log.e("tag", "doClickSearchBus length: "+linesJson );
                        for (int i=0;i<linesJson.length();i++){
                            lineList.add((String) linesJson.get(i));
                        }

                        StringBuffer buffer = new StringBuffer();
                        buffer.append("经过车站").append(siteName).append("的车辆有：").append("\n\n");

                        for (String s : lineList){
                            buffer.append(s).append("\n");
                        }

                        searchResTv.setText(buffer);

                    }
                } else {
                    Toast.makeText(getActivity(),"查询错误！",Toast.LENGTH_SHORT).show();
            }
            } else {
                Toast.makeText(getActivity(),jsonObject.getString("showapi_res_error"),Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
