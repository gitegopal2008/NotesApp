package com.notesapp.data.repository;

import com.notesapp.data.local.dao.ReminderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.notesapp.core.di.IoDispatcher")
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
public final class ReminderRepositoryImpl_Factory implements Factory<ReminderRepositoryImpl> {
  private final Provider<ReminderDao> reminderDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public ReminderRepositoryImpl_Factory(Provider<ReminderDao> reminderDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.reminderDaoProvider = reminderDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public ReminderRepositoryImpl get() {
    return newInstance(reminderDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static ReminderRepositoryImpl_Factory create(Provider<ReminderDao> reminderDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new ReminderRepositoryImpl_Factory(reminderDaoProvider, ioDispatcherProvider);
  }

  public static ReminderRepositoryImpl newInstance(ReminderDao reminderDao,
      CoroutineDispatcher ioDispatcher) {
    return new ReminderRepositoryImpl(reminderDao, ioDispatcher);
  }
}
