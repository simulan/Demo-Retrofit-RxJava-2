package be.sanderdebleecker.uselections.core.di.modules;

import android.content.Context;

import java.io.IOException;

import javax.inject.Singleton;

import be.sanderdebleecker.uselections.BuildConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author simulan
 * @version 1.0.0
 * @since 20/05/2017
 * ""
 */

@Module
public class ApplicationModule {
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String URL = "https://www.googleapis.com/civicinfo/v2/";
    private Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }
    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        //Configure to deserialize returned JSON
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //Interceptor to add API key to every request
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", API_KEY )
                        .build();
                Request.Builder requestBuilder = original.newBuilder().url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return clientBuilder.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client,GsonConverterFactory converterFactory,RxJava2CallAdapterFactory adapterFactory) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
