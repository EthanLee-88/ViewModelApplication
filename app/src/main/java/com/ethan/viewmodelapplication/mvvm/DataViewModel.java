package com.ethan.viewmodelapplication.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ethan.viewmodelapplication.retrofit.RetrofitInstance;
import com.ethan.viewmodelapplication.retrofit.RetrofitRequestInterface;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewModel extends ViewModel {
    // LiveData
    private MutableLiveData<String> mLiveData;
    // 网络加载引擎
    private RetrofitRequestInterface retrofit;
    public DataViewModel(){
        // 初始化 LiveData
        mLiveData = new MutableLiveData();
        // 初始化网络引擎
        retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitRequestInterface.class);
    }
    public LiveData getLiveData(){
        return mLiveData;
    }
    public void getData(String url){
        // 网络请求
        Call<ResponseBody> call = retrofit.getRequest(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    // 用 LiveData回调
                    mLiveData.postValue(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    mLiveData.postValue(e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mLiveData.postValue(t.getMessage());
            }
        });
    }
}
