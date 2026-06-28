package com.notesapp.features.reminders;

import com.notesapp.domain.usecase.reminders.CancelReminderUseCase;
import com.notesapp.domain.usecase.reminders.GetUpcomingRemindersUseCase;
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
public final class RemindersViewModel_Factory implements Factory<RemindersViewModel> {
  private final Provider<GetUpcomingRemindersUseCase> getUpcomingRemindersUseCaseProvider;

  private final Provider<CancelReminderUseCase> cancelReminderUseCaseProvider;

  public RemindersViewModel_Factory(
      Provider<GetUpcomingRemindersUseCase> getUpcomingRemindersUseCaseProvider,
      Provider<CancelReminderUseCase> cancelReminderUseCaseProvider) {
    this.getUpcomingRemindersUseCaseProvider = getUpcomingRemindersUseCaseProvider;
    this.cancelReminderUseCaseProvider = cancelReminderUseCaseProvider;
  }

  @Override
  public RemindersViewModel get() {
    return newInstance(getUpcomingRemindersUseCaseProvider.get(), cancelReminderUseCaseProvider.get());
  }

  public static RemindersViewModel_Factory create(
      Provider<GetUpcomingRemindersUseCase> getUpcomingRemindersUseCaseProvider,
      Provider<CancelReminderUseCase> cancelReminderUseCaseProvider) {
    return new RemindersViewModel_Factory(getUpcomingRemindersUseCaseProvider, cancelReminderUseCaseProvider);
  }

  public static RemindersViewModel newInstance(
      GetUpcomingRemindersUseCase getUpcomingRemindersUseCase,
      CancelReminderUseCase cancelReminderUseCase) {
    return new RemindersViewModel(getUpcomingRemindersUseCase, cancelReminderUseCase);
  }
}
