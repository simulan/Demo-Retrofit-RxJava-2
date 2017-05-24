package be.sanderdebleecker.uselections.api;


import java.io.IOException;

import be.sanderdebleecker.uselections.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceFactory {
    static final String ENDPOINT ="https://www.googleapis.com/civicinfo/v2/";
    private static final String API_KEY = BuildConfig.API_KEY;

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined ENDPOINT
     */
    public static <T> T create (final Class<T> clazz) {
        //Configure to deserialize returned JSON
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //Interceptor to add API key to every request
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build();
                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
