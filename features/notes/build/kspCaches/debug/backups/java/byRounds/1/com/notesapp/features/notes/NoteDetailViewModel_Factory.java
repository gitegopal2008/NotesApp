package com.notesapp.features.notes;

import com.notesapp.domain.usecase.notes.GetNoteByIdUseCase;
import com.notesapp.domain.usecase.notes.SaveNoteUseCase;
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
public final class NoteDetailViewModel_Factory implements Factory<NoteDetailViewModel> {
  private final Provider<GetNoteByIdUseCase> getNoteByIdUseCaseProvider;

  private final Provider<SaveNoteUseCase> saveNoteUseCaseProvider;

  public NoteDetailViewModel_Factory(Provider<GetNoteByIdUseCase> getNoteByIdUseCaseProvider,
      Provider<SaveNoteUseCase> saveNoteUseCaseProvider) {
    this.getNoteByIdUseCaseProvider = getNoteByIdUseCaseProvider;
    this.saveNoteUseCaseProvider = saveNoteUseCaseProvider;
  }

  @Override
  public NoteDetailViewModel get() {
    return newInstance(getNoteByIdUseCaseProvider.get(), saveNoteUseCaseProvider.get());
  }

  public static NoteDetailViewModel_Factory create(
      Provider<GetNoteByIdUseCase> getNoteByIdUseCaseProvider,
      Provider<SaveNoteUseCase> saveNoteUseCaseProvider) {
    return new NoteDetailViewModel_Factory(getNoteByIdUseCaseProvider, saveNoteUseCaseProvider);
  }

  public static NoteDetailViewModel newInstance(GetNoteByIdUseCase getNoteByIdUseCase,
      SaveNoteUseCase saveNoteUseCase) {
    return new NoteDetailViewModel(getNoteByIdUseCase, saveNoteUseCase);
  }
}
