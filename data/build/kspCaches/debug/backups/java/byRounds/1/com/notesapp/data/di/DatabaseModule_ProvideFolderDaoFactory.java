package com.notesapp.data.di;

import com.notesapp.data.local.NotesDatabase;
import com.notesapp.data.local.dao.FolderDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideFolderDaoFactory implements Factory<FolderDao> {
  private final Provider<NotesDatabase> dbProvider;

  public DatabaseModule_ProvideFolderDaoFactory(Provider<NotesDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FolderDao get() {
    return provideFolderDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideFolderDaoFactory create(Provider<NotesDatabase> dbProvider) {
    return new DatabaseModule_ProvideFolderDaoFactory(dbProvider);
  }

  public static FolderDao provideFolderDao(NotesDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFolderDao(db));
  }
}
