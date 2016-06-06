package com.guliash.calculator.di.modules;

import com.guliash.calculator.CalculatorApplication;
import com.guliash.calculator.storage.DBHelper;
import com.guliash.calculator.storage.Storage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private CalculatorApplication mApplication;

    public AppModule(CalculatorApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public CalculatorApplication provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public Storage provideStorage() {
        return new DBHelper(mApplication, CalculatorApplication.DATABASE_NAME);
    }
}
