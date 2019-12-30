package com.example.notescamera_daggermvvm.dagger

import android.app.Application
import com.example.notescamera_daggermvvm.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component (modules = [
    AndroidInjectionModule::class,
    ActivityBuilderModule::class,
    AppModule::class,
    ViewModelModule::class])
interface ApplicationComponent :AndroidInjector<BaseApplication>{

    @Component.Factory
    interface Factory{
        fun create( @BindsInstance application: Application ):ApplicationComponent
    }

    override fun inject(instance: BaseApplication?)
}