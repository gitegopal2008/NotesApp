package com.notesapp.data.di;

import com.notesapp.data.local.NotesDatabase;
import com.notesapp.data.local.dao.NoteDao;
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
public final class DatabaseModule_ProvideNoteDaoFactory implements Factory<NoteDao> {
  private final Provider<NotesDatabase> dbProvider;

  public DatabaseModule_ProvideNoteDaoFactory(Provider<NotesDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NoteDao get() {
    return provideNoteDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideNoteDaoFactory create(Provider<NotesDatabase> dbProvider) {
    return new DatabaseModule_ProvideNoteDaoFactory(dbProvider);
  }

  public static NoteDao provideNoteDao(NotesDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNoteDao(db));
  }
}
