package com.notesapp.analytics;

import com.notesapp.core.logging.Logger;
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
public final class NoOpAnalyticsTracker_Factory implements Factory<NoOpAnalyticsTracker> {
  private final Provider<Logger> loggerProvider;

  public NoOpAnalyticsTracker_Factory(Provider<Logger> loggerProvider) {
    this.loggerProvider = loggerProvider;
  }

  @Override
  public NoOpAnalyticsTracker get() {
    return newInstance(loggerProvider.get());
  }

  public static NoOpAnalyticsTracker_Factory create(Provider<Logger> loggerProvider) {
    return new NoOpAnalyticsTracker_Factory(loggerProvider);
  }

  public static NoOpAnalyticsTracker newInstance(Logger logger) {
    return new NoOpAnalyticsTracker(logger);
  }
}
