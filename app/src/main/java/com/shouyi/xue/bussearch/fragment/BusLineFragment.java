package com.shouyi.xue.bussearch.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shouyi.xue.bussearch.R;
import com.shouyi.xue.bussearch.RequestManager;
import com.shouyi.xue.bussearch.Utils;
import com.shouyi.xue.bussearch.model.SouData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    private String city;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_line, container, false);
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

    private void clickSearch() {
        city = cityEt.getText().toString().trim();
        final String bus = busEt.getText().toString().trim();

        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(bus)) {
            Toast.makeText(getActivity(), "请输入正确的查询信息", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String res = RequestManager.requestForBusLineId(city, bus);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.e("tag", "res=" + res);
                        doClickSearchBusId(res);
                    }
                });
            }
        }).start();
    }

    private void doClickSearchBusId(String res) {
        try {
            JSONObject jsonObject = new JSONObject(res);
            int code = jsonObject.getInt("showapi_res_code");
            if (code == 0) {
                JSONObject body = jsonObject.getJSONObject("showapi_res_body");
                int retCode = body.getInt("ret_code");
                if (retCode == 0) {
                    JSONArray jsonArray = body.getJSONArray("soulist");
                    if (jsonArray.length() > 0) {
                        List<SouData> souDatas = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            String lineTypeName = object.getString("lineTypeName");
                            String _id = object.getString("_id");
                            String lineName = object.getString("lineName");

                            SouData data = new SouData();
                            data.setId(_id);
                            data.setLineTypeName(lineTypeName);
                            data.setLineName(lineName);

                            souDatas.add(data);
                        }

                        showSimpleListDialog(souDatas);

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


    private void showSimpleListDialog(final List<SouData> souDatas) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        final String[] items = new String[souDatas.size()];
        for (int i = 0; i < souDatas.size(); i++) {
            items[i] = souDatas.get(i).getLineName();
        }

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("tag", "onClick: " + souDatas.get(which).getLineName());
                dialog.dismiss();
                clickBusInfo(souDatas.get(which).getId());
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clickBusInfo(final String id) {
        Log.e("tag", "clickBusInfo: " + id);

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(city)) {
            Toast.makeText(getActivity(), "查询错误！", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String res = RequestManager.requestBusLine(city, id);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Utils.e("tag", "res=" + res);
                        doclickShowInfo(res);
                    }
                });
            }
        }).start();
    }

    private void doclickShowInfo(String res) {
        try {
            JSONObject jsonObject = new JSONObject(res);
            int code = jsonObject.getInt("showapi_res_code");
            if (code == 0) {
                JSONObject body = jsonObject.getJSONObject("showapi_res_body");
                int retCode = body.getInt("ret_code");
                if (retCode == 0) {
                    StringBuffer buffer = new StringBuffer();

                    JSONObject busSite = body.getJSONObject("busSite");
                    buffer.append(busSite.getString("lineName")).append("\n");
                    buffer.append(busSite.getString("runtime")).append("\n");
                    buffer.append(busSite.getString("price")).append("\n");
                    buffer.append(busSite.getString("company")).append("\n");
                    buffer.append(busSite.getString("siteTitleName")).append("\n");

                    JSONArray sitesLineList = busSite.getJSONArray("sitesLineList");
                    for (int i = 0; i < sitesLineList.length(); i++) {
                        JSONObject sitesLine = (JSONObject) sitesLineList.get(i);
                        buffer.append(sitesLine.getString("lineSiteCount")).append("\n");
                        buffer.append(sitesLine.getString("lineTitle")).append("\n");

                        JSONArray lineSiteList = sitesLine.getJSONArray("lineSiteList");
                        if (lineSiteList.length() > 0) {
                            buffer.append("经过的站点为：\n");
                            for (int j = 0; j < lineSiteList.length(); j++) {
                                JSONObject lineSite = (JSONObject) lineSiteList.get(j);
                                buffer.append(lineSite.getString("lineSiteName")).append("-");
                            }
                            buffer.append("\n");
                        }

                    }

                    resTv.setText(buffer);

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
