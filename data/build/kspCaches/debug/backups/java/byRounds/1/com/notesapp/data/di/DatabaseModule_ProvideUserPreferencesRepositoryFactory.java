package com.notesapp.data.di;

import com.notesapp.data.repository.UserPreferencesRepositoryImpl;
import com.notesapp.domain.repository.UserPreferencesRepository;
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
public final class DatabaseModule_ProvideUserPreferencesRepositoryFactory implements Factory<UserPreferencesRepository> {
  private final Provider<UserPreferencesRepositoryImpl> implProvider;

  public DatabaseModule_ProvideUserPreferencesRepositoryFactory(
      Provider<UserPreferencesRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public UserPreferencesRepository get() {
    return provideUserPreferencesRepository(implProvider.get());
  }

  public static DatabaseModule_ProvideUserPreferencesRepositoryFactory create(
      Provider<UserPreferencesRepositoryImpl> implProvider) {
    return new DatabaseModule_ProvideUserPreferencesRepositoryFactory(implProvider);
  }

  public static UserPreferencesRepository provideUserPreferencesRepository(
      UserPreferencesRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserPreferencesRepository(impl));
  }
}
