package com.example.rxrtmvp.rxrtfz;

public class RetrofitHelper {
    private static RetrofitHelper mHelper;
    private String BASE_URL = "";
    private RetrofitHelper() {
        //BASE_URL = constans.baseUrl;
    }
    public static RetrofitHelper getService() {
        if (mHelper == null) mHelper = new RetrofitHelper();
        return mHelper;
    }

    public RetrofitService getApi() {
        return RetrofitApi.getApiService(RetrofitService.class, BASE_URL);
    }
}