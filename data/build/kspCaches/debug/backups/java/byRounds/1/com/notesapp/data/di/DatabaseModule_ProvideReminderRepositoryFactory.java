package com.notesapp.data.di;

import com.notesapp.data.repository.ReminderRepositoryImpl;
import com.notesapp.domain.repository.ReminderRepository;
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
public final class DatabaseModule_ProvideReminderRepositoryFactory implements Factory<ReminderRepository> {
  private final Provider<ReminderRepositoryImpl> implProvider;

  public DatabaseModule_ProvideReminderRepositoryFactory(
      Provider<ReminderRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public ReminderRepository get() {
    return provideReminderRepository(implProvider.get());
  }

  public static DatabaseModule_ProvideReminderRepositoryFactory create(
      Provider<ReminderRepositoryImpl> implProvider) {
    return new DatabaseModule_ProvideReminderRepositoryFactory(implProvider);
  }

  public static ReminderRepository provideReminderRepository(ReminderRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideReminderRepository(impl));
  }
}
