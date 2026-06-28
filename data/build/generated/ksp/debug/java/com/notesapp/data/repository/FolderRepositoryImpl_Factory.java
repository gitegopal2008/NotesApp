package com.notesapp.data.repository;

import com.notesapp.data.local.dao.FolderDao;
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
public final class FolderRepositoryImpl_Factory implements Factory<FolderRepositoryImpl> {
  private final Provider<FolderDao> folderDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public FolderRepositoryImpl_Factory(Provider<FolderDao> folderDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.folderDaoProvider = folderDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public FolderRepositoryImpl get() {
    return newInstance(folderDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static FolderRepositoryImpl_Factory create(Provider<FolderDao> folderDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new FolderRepositoryImpl_Factory(folderDaoProvider, ioDispatcherProvider);
  }

  public static FolderRepositoryImpl newInstance(FolderDao folderDao,
      CoroutineDispatcher ioDispatcher) {
    return new FolderRepositoryImpl(folderDao, ioDispatcher);
  }
}
