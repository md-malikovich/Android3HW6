package com.e.android3hw6;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.e.android3hw6.base.BaseActivity;
import com.e.android3hw6.data.RetrofitBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.e.android3hw6.BuildConfig.ACCESS_KEY;

public class MainActivity extends BaseActivity {

    Spinner spinnerOne, spinnerTwo;
    private ArrayList<String> ratesValues;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchCurrencies();

        spinnerOne = findViewById(R.id.spinnerOne);
        //spinnerTwo = findViewById(R.id.spinnerTwo);
        ratesValues = new ArrayList<>();
    }

    private void fetchCurrencies() {
        RetrofitBuilder.getService().currencies(ACCESS_KEY)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful() && response.body() != null) {

                            JsonObject rates = response.body().getAsJsonObject("rates");
                            Object[] ratesTitles = rates.keySet().toArray();

                            for (Object ratesTitle : ratesTitles) {
                                ratesValues.add(String.valueOf(rates.getAsJsonPrimitive(ratesTitle.toString())));
                            }
                            Log.d("ololo", "response");

                            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, ratesTitles);
                            spinnerOne.setAdapter(adapter);
                            //spinnerTwo.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        toast("Error");
                    }
                });
    }
}
//1. все что мы делали на уроке это был запрос без использования ретрофит,
// Вам нужно выпилить с проекта asynctask и с помощью ретрофита получить JsomObject.
//2. В самом приложении по дизайну создать 1 еdittext 2 spinner и поле текстовое для вывода результата.
//3. Когда мы вводим сумму в поле результата выводим сумму по курсу валют.
