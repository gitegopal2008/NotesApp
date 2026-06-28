package com.notesapp.sync;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SyncManager_Factory implements Factory<SyncManager> {
  @Override
  public SyncManager get() {
    return newInstance();
  }

  public static SyncManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SyncManager newInstance() {
    return new SyncManager();
  }

  private static final class InstanceHolder {
    private static final SyncManager_Factory INSTANCE = new SyncManager_Factory();
  }
}
