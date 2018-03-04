package com.shouyi.xue.bussearch;

import android.text.TextUtils;
import android.widget.TextView;

import com.show.api.ShowApiRequest;

/**
 * Created by asia on 2018/3/4.
 */

public class RequestManager {

    /**
     * 公交站点信息查询
     * 城市名称
     * 站点名称
     */
    public static String requestForBusPoint(String cityName, String lineSiteName) {
        if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(lineSiteName)) {
            return "";
        }

        String res = new ShowApiRequest("http://route.showapi.com/1463-7", RequestConfig.APP_ID, RequestConfig.APP_SECRET)
                .addTextPara("cityName", cityName)
                .addTextPara("lineSiteName", lineSiteName)
                .post();

        return res;
    }


    /**
     * 公交线路信息查询
     * <p>
     * 1、根据公交线路名称查询线路id
     * <p>
     * <p>
     * 得到公交线路以及线路id
     */


    public static String requestForBusLineId(String cityName, String lineName) {
        if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(lineName)) {
            return "";
        }

        String lineRes = new ShowApiRequest("http://route.showapi.com/1463-3", RequestConfig.APP_ID, RequestConfig.APP_SECRET)
                .addTextPara("cityName", cityName)
                .addTextPara("lineName", lineName)
                .addTextPara("curPage", "1")//页数，默认为1
                .post();

        return lineRes;
    }

    /**
     * 根据公交id查询公交详情信息
     */
    public static String requestBusLine(String cityName, String lineId) {
        if (TextUtils.isEmpty(lineId) || TextUtils.isEmpty(cityName)) {
            return "";
        }

        String res = new ShowApiRequest("http://route.showapi.com/1463-4", RequestConfig.APP_ID, RequestConfig.APP_SECRET)
                .addTextPara("cityName", cityName)
                .addTextPara("lineId", lineId)
                .post();

        return res;
    }


    /**
     * 公交换乘信息查询
     */

    public static String requestBusChangeInfo(String cityName, String beginSite, String endSite) {
        if (TextUtils.isEmpty(beginSite) || TextUtils.isEmpty(endSite) || TextUtils.isEmpty(cityName)) {
            return "";
        }

        String res = new ShowApiRequest("http://route.showapi.com/1463-5", RequestConfig.APP_ID, RequestConfig.APP_SECRET)
                .addTextPara("cityName", cityName)
                .addTextPara("beginSite", beginSite)
                .addTextPara("endSite", endSite)
                .addTextPara("isLineId", "0")
                .addTextPara("isSiteId", "0")
                .post();

        return res;

    }


}
