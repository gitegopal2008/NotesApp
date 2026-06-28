package com.notesapp.features.backup;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
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
public final class BackupWorker_Factory {
  private final Provider<BackupManager> backupManagerProvider;

  public BackupWorker_Factory(Provider<BackupManager> backupManagerProvider) {
    this.backupManagerProvider = backupManagerProvider;
  }

  public BackupWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, backupManagerProvider.get());
  }

  public static BackupWorker_Factory create(Provider<BackupManager> backupManagerProvider) {
    return new BackupWorker_Factory(backupManagerProvider);
  }

  public static BackupWorker newInstance(Context context, WorkerParameters workerParams,
      BackupManager backupManager) {
    return new BackupWorker(context, workerParams, backupManager);
  }
}
