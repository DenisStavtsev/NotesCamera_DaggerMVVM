package com.example.notescamera_daggermvvm.dagger

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.notescamera_daggermvvm.R
import com.example.notescamera_daggermvvm.db.NoteDao
import com.example.notescamera_daggermvvm.db.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun someString():String{
        return "this is a test di /w dagger"
    }
    @Provides
    fun provideRequestOptions():RequestOptions{
        return RequestOptions.placeholderOf(R.drawable.white_background)
    }

    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions):RequestManager{
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Provides
    fun provideApplicationDrawable(application: Application):Drawable{
        return ContextCompat.getDrawable(application,R.drawable.logo_)!!
    }

    @Singleton
    @Provides
    fun provideNoteDataBase(application:Application): NoteDatabase {
        return NoteDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase) : NoteDao {
        return noteDatabase.noteDao()
    }

}