package com.jeluchu.roomlivedata

import android.content.Context
import com.jeluchu.roomlivedata.di.AppComponent
import com.jeluchu.roomlivedata.di.AppModule
import com.jeluchu.roomlivedata.di.DaggerAppComponent
import com.jeluchu.roomlivedata.di.UtilsModule
import com.jeluchu.roomlivedata.managers.TTSManager

import com.jeluchu.roomlivedata.utils.Constants


class Application : android.app.Application() {
    private val TAG = Application::class.java.simpleName
    lateinit var appComponent: AppComponent
    override fun onCreate() {

        super.onCreate()
        context = applicationContext

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).utilsModule(
            UtilsModule()
        ).build()



        Constants.Appcontext = context.toString()



    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex.install(this);
    }

    companion object {

        //    private static FirebaseAnalytics mFirebaseAnalytics;
        //
        //    public static FirebaseAnalytics getmFirebaseAnalytics() {
        //        return mFirebaseAnalytics = FirebaseAnalytics.getInstance(AppConstant.CONTEXT);
        //    }

        var context: Context? = null
            private set
    }


}
