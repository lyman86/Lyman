package com.ly.luoyan.mylibrary.http;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Retofit网络请求工具类
 * Created by HDL on 2016/7/29.
 */
public class RetrofitUtils {
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒

    private static Retrofit mRetrofit;
    public static String url = "https://live.myyll.com/api/";
    private RetrofitUtils() {

    }

//    public static Retrofit newInstence() {
//        mRetrofit = null;
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(300, TimeUnit.SECONDS)
//                .writeTimeout(600, TimeUnit.SECONDS)
//                .readTimeout(300, TimeUnit.SECONDS)
//                .build();
//        Converter.Factory fastJsonConverterFactory = FastJsonConverterFactory.create();
//        CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
//        mRetrofit = new Retrofit.Builder()
//                .client(okHttpClient)//添加一个client,不然retrofit会自己默认添加一个
//                .baseUrl(url)
//                .addConverterFactory(fastJsonConverterFactory)
//                .addCallAdapterFactory(rxJavaCallAdapterFactory)
//                .build();
//        return mRetrofit;
//    }



}
