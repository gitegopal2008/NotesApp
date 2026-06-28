package com.notesapp.domain.usecase.reminders;

import com.notesapp.domain.repository.ReminderRepository;
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
public final class CancelReminderUseCase_Factory implements Factory<CancelReminderUseCase> {
  private final Provider<ReminderRepository> repositoryProvider;

  public CancelReminderUseCase_Factory(Provider<ReminderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CancelReminderUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CancelReminderUseCase_Factory create(
      Provider<ReminderRepository> repositoryProvider) {
    return new CancelReminderUseCase_Factory(repositoryProvider);
  }

  public static CancelReminderUseCase newInstance(ReminderRepository repository) {
    return new CancelReminderUseCase(repository);
  }
}
