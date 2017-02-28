package com.jimei.xiaolumeimei.module;

import com.jimei.xiaolumeimei.service.RetrofitClient;
import com.jimei.xiaolumeimei.service.api.ActivityService;
import com.jimei.xiaolumeimei.service.api.AddressService;
import com.jimei.xiaolumeimei.service.api.MainService;
import com.jimei.xiaolumeimei.service.api.ProductService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by wisdom on 17/2/22.
 */

@Module
public class InteractorModule {

    @Singleton
    @Provides
    public Retrofit provideRestAdapter() {
        return RetrofitClient.createAdapter();
    }

    @Singleton
    @Provides
    public ActivityService provideActivityApi(Retrofit retrofit) {
        return retrofit.create(ActivityService.class);
    }

    @Singleton
    @Provides
    public ActivityInteractor provideActivityInteractor(ActivityService service) {
        return new ActivityInteractorImpl(service);
    }

    @Singleton
    @Provides
    public MainService provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainService.class);
    }

    @Singleton
    @Provides
    public MainInteractor provideMainInteractor(MainService service) {
        return new MainInteractorImpl(service);
    }

    @Singleton
    @Provides
    public ProductService provideProductApi(Retrofit retrofit) {
        return retrofit.create(ProductService.class);
    }

    @Singleton
    @Provides
    public ProductInteractor provideProductInteractor(ProductService service) {
        return new ProductInteractorImpl(service);
    }

    @Singleton
    @Provides
    public AddressService provideAddressApi(Retrofit retrofit) {
        return retrofit.create(AddressService.class);
    }

    @Singleton
    @Provides
    public AddressInteractor provideAddressInteractor(AddressService service) {
        return new AddressInteractorImpl(service);
    }
}
