package com.example.androidarquitectures.mvp;

import com.example.androidarquitectures.model.CountriesService;
import com.example.androidarquitectures.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesPresenter {
    private View view;
    private CountriesService service;

    public CountriesPresenter(View view) {
        this.view = view;
        service = new CountriesService();
        fetchCountries();
    }

    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> values) {
                        List<String> countryNames = new ArrayList<>();

                        for(Country country: values) {
                            countryNames.add(country.countryName);
                        }
                        view.setValues(countryNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });
    }


    public void onReflesh() {
        fetchCountries();
    }

    public interface View {
        void setValues(List<String> values);
        void onError();
    }
}
