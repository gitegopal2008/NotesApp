package com.notesapp.features.backup;

import android.content.Context;
import com.notesapp.data.local.NotesDatabase;
import com.notesapp.domain.repository.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BackupManager_Factory implements Factory<BackupManager> {
  private final Provider<Context> contextProvider;

  private final Provider<NotesDatabase> databaseProvider;

  private final Provider<UserPreferencesRepository> userPreferencesRepositoryProvider;

  public BackupManager_Factory(Provider<Context> contextProvider,
      Provider<NotesDatabase> databaseProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
    this.userPreferencesRepositoryProvider = userPreferencesRepositoryProvider;
  }

  @Override
  public BackupManager get() {
    return newInstance(contextProvider.get(), databaseProvider.get(), userPreferencesRepositoryProvider.get());
  }

  public static BackupManager_Factory create(Provider<Context> contextProvider,
      Provider<NotesDatabase> databaseProvider,
      Provider<UserPreferencesRepository> userPreferencesRepositoryProvider) {
    return new BackupManager_Factory(contextProvider, databaseProvider, userPreferencesRepositoryProvider);
  }

  public static BackupManager newInstance(Context context, NotesDatabase database,
      UserPreferencesRepository userPreferencesRepository) {
    return new BackupManager(context, database, userPreferencesRepository);
  }
}
