package com.example.notescamera_daggermvvm.dagger

import com.example.notescamera_daggermvvm.AuthActivity
import com.example.notescamera_daggermvvm.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("UNUSED")
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}