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
public final class GetUpcomingRemindersUseCase_Factory implements Factory<GetUpcomingRemindersUseCase> {
  private final Provider<ReminderRepository> repositoryProvider;

  public GetUpcomingRemindersUseCase_Factory(Provider<ReminderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetUpcomingRemindersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetUpcomingRemindersUseCase_Factory create(
      Provider<ReminderRepository> repositoryProvider) {
    return new GetUpcomingRemindersUseCase_Factory(repositoryProvider);
  }

  public static GetUpcomingRemindersUseCase newInstance(ReminderRepository repository) {
    return new GetUpcomingRemindersUseCase(repository);
  }
}
