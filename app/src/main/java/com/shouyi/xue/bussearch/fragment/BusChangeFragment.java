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
import com.shouyi.xue.bussearch.model.ConInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asia on 2018/3/4.
 */

public class BusChangeFragment extends Fragment {

    public static BusChangeFragment getInstance(Bundle bundle) {

        BusChangeFragment pointInfoFragment = new BusChangeFragment();
        if (bundle != null)
            pointInfoFragment.setArguments(bundle);

        return pointInfoFragment;
    }

    protected Handler handler = new Handler();

    private AppCompatEditText cityEt;
    private AppCompatEditText startSiteEt;
    private AppCompatEditText endSiteEt;

    private Button searchBtn;

    private TextView searchResTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_change, container, false);
        cityEt = view.findViewById(R.id.search_city_et);
        startSiteEt = view.findViewById(R.id.search_site_start_et);
        endSiteEt = view.findViewById(R.id.search_site_end_et);

        searchBtn = view.findViewById(R.id.search_btn);
        searchResTv = view.findViewById(R.id.search_res);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSearch();
            }
        });
        return view;
    }

    private void clickSearch() {
        final String city = cityEt.getText().toString().trim();
        final String startSite = startSiteEt.getText().toString().trim();
        final String endSite = endSiteEt.getText().toString().trim();

        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(startSite) || TextUtils.isEmpty(endSite)) {
            Toast.makeText(getActivity(), "请输入正确的查询信息", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String res = RequestManager.requestBusChangeInfo(city, startSite, endSite);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
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
            if (code == 0) {
                JSONObject body = jsonObject.getJSONObject("showapi_res_body");
                int retCode = body.getInt("ret_code");
                if (retCode == 0) {
                    JSONArray contentJsonArray = body.getJSONArray("contentlist");
                    if (contentJsonArray.length() > 0) {
                        JSONObject contentItem = (JSONObject) contentJsonArray.get(0);
                        String city = contentItem.getString("cityName");
                        String proName = contentItem.getString("proName");
                        String cityEngName = contentItem.getString("cityEngName");
                        JSONArray conArray = contentItem.getJSONArray("conArray");
                        List<ConInfo> conInfoList = new ArrayList<>();

                        for (int i = 0; i < conArray.length(); i++) {
                            JSONObject conInfoJson = (JSONObject) conArray.get(i);
                            String plan = conInfoJson.getString("plan");
                            String dictance = conInfoJson.getString("distance");
                            JSONArray storkeListJson = conInfoJson.getJSONArray("storkelist");
                            Log.e("tag", "storkeListJson: "+storkeListJson );

                            ConInfo conInfo = new ConInfo();
                            conInfo.setPlan(plan);
                            conInfo.setDistance(dictance);

                            if (storkeListJson != null) {
                                List<ConInfo.Info> infoList = new ArrayList<>();

                                for (int j = 0; j < storkeListJson.length(); j++) {
                                    JSONObject storke = (JSONObject) storkeListJson.get(j);

                                    ConInfo.Info info = new ConInfo.Info();
                                    info.setWorkName(storke.getString("walkName"));
                                    info.setSiteId(storke.getString("siteId"));
                                    info.setLineName(storke.getString("lineName"));
                                    info.setOrderNum(storke.getString("orderNum"));
                                    info.setExplain(storke.getString("explain"));
                                    info.setSiteName(storke.getString("siteName"));
                                    info.setLineId(storke.getString("lineId"));

                                    infoList.add(info);
                                }

                                conInfo.setInfoList(infoList);
                            }

                            conInfoList.add(conInfo);

                            StringBuffer buffer = new StringBuffer();
                            buffer.append("路线为：").append("\n");

                            for (ConInfo info : conInfoList) {
                                buffer.append(info.getPlan()).append(":\n").append(info.getDistance()).append("\n");
                                List<ConInfo.Info> infoList = info.getInfoList();
                                for (ConInfo.Info fo : infoList) {
                                    if (!TextUtils.isEmpty(fo.getWorkName())) {
                                        buffer.append(fo.getWorkName()).append("\n");
                                    }

                                    if (!TextUtils.isEmpty(fo.getLineName())) {
                                        buffer.append(fo.getLineName()).append("\n");
                                    }

                                    if (!TextUtils.isEmpty(fo.getExplain())) {
                                        buffer.append(fo.getExplain()).append("\n");
                                    }

                                    if (!TextUtils.isEmpty(fo.getSiteName())) {
                                        buffer.append(fo.getSiteName()).append("\n");
                                    }
                                }

                                buffer.append("\n");
                            }

                            searchResTv.setText(buffer);


                        }


                    } else {
                        Toast.makeText(getActivity(), "未查到！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "查询错误！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), jsonObject.getString("showapi_res_error"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
