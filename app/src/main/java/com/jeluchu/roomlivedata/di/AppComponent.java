package com.jeluchu.roomlivedata.di;


import com.jeluchu.roomlivedata.activities.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjectionLogin(LoginActivity loginActivity);





}
