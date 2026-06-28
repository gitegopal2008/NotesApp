package com.notesapp.data.di;

import com.notesapp.data.repository.FolderRepositoryImpl;
import com.notesapp.domain.repository.FolderRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class DatabaseModule_ProvideFolderRepositoryFactory implements Factory<FolderRepository> {
  private final Provider<FolderRepositoryImpl> implProvider;

  public DatabaseModule_ProvideFolderRepositoryFactory(
      Provider<FolderRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public FolderRepository get() {
    return provideFolderRepository(implProvider.get());
  }

  public static DatabaseModule_ProvideFolderRepositoryFactory create(
      Provider<FolderRepositoryImpl> implProvider) {
    return new DatabaseModule_ProvideFolderRepositoryFactory(implProvider);
  }

  public static FolderRepository provideFolderRepository(FolderRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFolderRepository(impl));
  }
}
