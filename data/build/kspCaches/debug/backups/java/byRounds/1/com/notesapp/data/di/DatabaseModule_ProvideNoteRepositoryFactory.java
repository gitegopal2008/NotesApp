package com.notesapp.data.di;

import com.notesapp.data.repository.NoteRepositoryImpl;
import com.notesapp.domain.repository.NoteRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideNoteRepositoryFactory implements Factory<NoteRepository> {
  private final Provider<NoteRepositoryImpl> implProvider;

  public DatabaseModule_ProvideNoteRepositoryFactory(Provider<NoteRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public NoteRepository get() {
    return provideNoteRepository(implProvider.get());
  }

  public static DatabaseModule_ProvideNoteRepositoryFactory create(
      Provider<NoteRepositoryImpl> implProvider) {
    return new DatabaseModule_ProvideNoteRepositoryFactory(implProvider);
  }

  public static NoteRepository provideNoteRepository(NoteRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNoteRepository(impl));
  }
}
