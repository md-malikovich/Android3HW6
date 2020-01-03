package com.e.android3hw6.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.e.android3hw6.BuildConfig.BASE_URL;

public class RetrofitBuilder {

    private static CurrenciesService currenciesService;

    public static CurrenciesService getService() {
        if(currenciesService == null) {
            currenciesService =  buildRetrofit();
        }
    return currenciesService;
    }

    private static CurrenciesService buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrenciesService.class);
    }
}
