package com.e.android3hw6;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    EditText etInput;
    TextView tvShow;
    private ArrayList<String> ratesValues;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        fetchCurrencies();
        addTextChangedListener();
    }

    private void initViews() {
        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        etInput = findViewById(R.id.etInput);
        tvShow = findViewById(R.id.tvShow);
    }

    private void addTextChangedListener() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                tvShow.setText(s);
                math(editable);
            }
        });
    }

    private void fetchCurrencies() {
        RetrofitBuilder.getService().currencies(ACCESS_KEY)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful() && response.body() != null) {

                            JsonObject rates = response.body().getAsJsonObject("rates");
                            Object[] ratesTitles = rates.keySet().toArray();
                            ratesValues = new ArrayList<>();

                            for (Object ratesTitle : ratesTitles) {
                                ratesValues.add(String.valueOf(rates.getAsJsonPrimitive(ratesTitle.toString())));
                            }

                            ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, ratesTitles);
                            spinnerOne.setAdapter(adapter);
                            spinnerTwo.setAdapter(adapter);
                            //tvShow.setText(rates.getAsJsonPrimitive("KGS").toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        toast("Error");
                    }
                });
    }

    private void math(Editable editable) {
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvShow.setText(ratesValues.get(position).toString().trim());
                //parse to Double
                //etInput.getText().toString();
                //String result = etInput * 2;
                //etInput.setText(tvShow);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
    }
}
