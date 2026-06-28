package com.notesapp.features.backup;

import android.app.Application;
import com.notesapp.domain.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class BackupViewModel_Factory implements Factory<BackupViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<BackupManager> backupManagerProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public BackupViewModel_Factory(Provider<Application> applicationProvider,
      Provider<BackupManager> backupManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.applicationProvider = applicationProvider;
    this.backupManagerProvider = backupManagerProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public BackupViewModel get() {
    return newInstance(applicationProvider.get(), backupManagerProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static BackupViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<BackupManager> backupManagerProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new BackupViewModel_Factory(applicationProvider, backupManagerProvider, userPreferencesRepositoryProvider);
  }

  public static BackupViewModel newInstance(Application application, BackupManager backupManager,
      UserPreferencesRepository userPreferencesRepository) {
    return new BackupViewModel(application, backupManager, userPreferencesRepository);
  }
}
