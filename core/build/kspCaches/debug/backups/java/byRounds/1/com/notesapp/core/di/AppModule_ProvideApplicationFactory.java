package com.notesapp.core.di;

import android.app.Application;
import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideApplicationFactory implements Factory<Application> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideApplicationFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public Application get() {
    return provideApplication(contextProvider.get());
  }

  public static AppModule_ProvideApplicationFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideApplicationFactory(contextProvider);
  }

  public static Application provideApplication(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideApplication(context));
  }
}
