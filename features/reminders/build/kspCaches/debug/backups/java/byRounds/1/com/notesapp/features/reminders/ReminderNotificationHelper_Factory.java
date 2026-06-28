package com.notesapp.features.reminders;

import android.content.Context;
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
public final class ReminderNotificationHelper_Factory implements Factory<ReminderNotificationHelper> {
  private final Provider<Context> contextProvider;

  public ReminderNotificationHelper_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ReminderNotificationHelper get() {
    return newInstance(contextProvider.get());
  }

  public static ReminderNotificationHelper_Factory create(Provider<Context> contextProvider) {
    return new ReminderNotificationHelper_Factory(contextProvider);
  }

  public static ReminderNotificationHelper newInstance(Context context) {
    return new ReminderNotificationHelper(context);
  }
}
