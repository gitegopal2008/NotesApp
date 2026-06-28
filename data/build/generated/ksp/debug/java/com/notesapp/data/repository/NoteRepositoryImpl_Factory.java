package com.notesapp.data.repository;

import com.notesapp.data.local.dao.NoteDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.notesapp.core.di.IoDispatcher")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class NoteRepositoryImpl_Factory implements Factory<NoteRepositoryImpl> {
  private final Provider<NoteDao> noteDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public NoteRepositoryImpl_Factory(Provider<NoteDao> noteDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.noteDaoProvider = noteDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public NoteRepositoryImpl get() {
    return newInstance(noteDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static NoteRepositoryImpl_Factory create(Provider<NoteDao> noteDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new NoteRepositoryImpl_Factory(noteDaoProvider, ioDispatcherProvider);
  }

  public static NoteRepositoryImpl newInstance(NoteDao noteDao, CoroutineDispatcher ioDispatcher) {
    return new NoteRepositoryImpl(noteDao, ioDispatcher);
  }
}
