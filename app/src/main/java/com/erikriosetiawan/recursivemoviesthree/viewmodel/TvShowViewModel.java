package com.erikriosetiawan.recursivemoviesthree.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.erikriosetiawan.recursivemoviesthree.BuildConfig;
import com.erikriosetiawan.recursivemoviesthree.models.TvShowResult;
import com.erikriosetiawan.recursivemoviesthree.apiservices.TvShowRepository;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<TvShowResult> mutableLiveDataTvShow;
    private MutableLiveData<Boolean> isFetching = new MutableLiveData<>();
    private static final String API_KEY = BuildConfig.API_KEY;

    public LiveData<TvShowResult> getTvShows() {
        return mutableLiveDataTvShow;
    }

    public LiveData<Boolean> getIsFetching() {
        return isFetching;
    }

    public void setIsFetching(boolean isFetching) {
        this.isFetching.postValue(isFetching);
    }

    public void open() {
        setIsFetching(true);
        if (mutableLiveDataTvShow != null) {
            return;
        }
        TvShowRepository tvShowRepository = TvShowRepository.getInstance();
        mutableLiveDataTvShow = tvShowRepository.getTvShow(API_KEY, "en-US");
    }

    public void close() {
        if (mutableLiveDataTvShow.getValue() != null) {
            setIsFetching(false);
        }
    }
}