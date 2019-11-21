package com.t3h.wallccraft.apialbum;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiBuilder {
    private static Api api;

    public static Api getInstance(){
        if (api == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://104.225.218.235:3200")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(Api.class);
        }
        return api;
    }

}
