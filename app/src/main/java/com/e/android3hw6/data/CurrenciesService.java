package com.e.android3hw6.data;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import static com.e.android3hw6.data.ApiEndpoints.LATEST;

public interface CurrenciesService {
    @GET(LATEST)
    Call<JsonObject> currencies(@Query("access_key") String access_key);
}