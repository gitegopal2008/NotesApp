package com.notesapp.security;

import com.notesapp.domain.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AppLockManager_Factory implements Factory<AppLockManager> {
  private final Provider<UserPreferencesRepository> preferencesRepositoryProvider;

  public AppLockManager_Factory(Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public AppLockManager get() {
    return newInstance(preferencesRepositoryProvider.get());
  }

  public static AppLockManager_Factory create(
      Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    return new AppLockManager_Factory(preferencesRepositoryProvider);
  }

  public static AppLockManager newInstance(UserPreferencesRepository preferencesRepository) {
    return new AppLockManager(preferencesRepository);
  }
}
