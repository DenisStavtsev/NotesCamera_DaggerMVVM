package com.example.notescamera_daggermvvm.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notescamera_daggermvvm.vm.NoteViewModel
import com.example.notescamera_daggermvvm.vm.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("UNUSED")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindNoteViewModel(noteViewModel: NoteViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory:ViewModelFactory ): ViewModelProvider.Factory
}