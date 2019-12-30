package com.example.notescamera_daggermvvm

import android.util.Log
import com.example.notescamera_daggermvvm.dagger.ApplicationComponent
import com.example.notescamera_daggermvvm.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Log.d("BaseApplication -> applicationInjector","hello")
        val appComponent:ApplicationComponent = DaggerApplicationComponent.factory().create(this)
        appComponent.inject(this)
        return appComponent
    }
}