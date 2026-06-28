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
public final class SetReminderUseCase_Factory implements Factory<SetReminderUseCase> {
  private final Provider<ReminderRepository> repositoryProvider;

  public SetReminderUseCase_Factory(Provider<ReminderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SetReminderUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SetReminderUseCase_Factory create(Provider<ReminderRepository> repositoryProvider) {
    return new SetReminderUseCase_Factory(repositoryProvider);
  }

  public static SetReminderUseCase newInstance(ReminderRepository repository) {
    return new SetReminderUseCase(repository);
  }
}
