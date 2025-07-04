package ar.edu.uade.deremate.di;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import ar.edu.uade.deremate.data.api.AuthService;
import ar.edu.uade.deremate.data.api.EntregaService;
import ar.edu.uade.deremate.data.api.RouteService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        
        return new OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(chain -> chain.proceed(chain.request())
                .newBuilder()
                .build())
            .build();
    }
    
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
    
    @Provides
    @Singleton
    AuthService provideAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    @Singleton
    RouteService provideRouteService(Retrofit retrofit) {
        return retrofit.create(RouteService.class);
    }

    @Provides
    @Singleton
    EntregaService provideEntregaService(Retrofit retrofit) {
        return retrofit.create(EntregaService.class);
    }
}
