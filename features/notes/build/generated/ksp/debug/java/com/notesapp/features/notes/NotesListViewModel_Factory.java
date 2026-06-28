package com.notesapp.features.notes;

import com.notesapp.domain.usecase.folders.GetFoldersUseCase;
import com.notesapp.domain.usecase.notes.DeleteNoteUseCase;
import com.notesapp.domain.usecase.notes.GetNotesUseCase;
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
public final class NotesListViewModel_Factory implements Factory<NotesListViewModel> {
  private final Provider<GetNotesUseCase> getNotesUseCaseProvider;

  private final Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider;

  private final Provider<GetFoldersUseCase> getFoldersUseCaseProvider;

  public NotesListViewModel_Factory(Provider<GetNotesUseCase> getNotesUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider,
      Provider<GetFoldersUseCase> getFoldersUseCaseProvider) {
    this.getNotesUseCaseProvider = getNotesUseCaseProvider;
    this.deleteNoteUseCaseProvider = deleteNoteUseCaseProvider;
    this.getFoldersUseCaseProvider = getFoldersUseCaseProvider;
  }

  @Override
  public NotesListViewModel get() {
    return newInstance(getNotesUseCaseProvider.get(), deleteNoteUseCaseProvider.get(), getFoldersUseCaseProvider.get());
  }

  public static NotesListViewModel_Factory create(Provider<GetNotesUseCase> getNotesUseCaseProvider,
      Provider<DeleteNoteUseCase> deleteNoteUseCaseProvider,
      Provider<GetFoldersUseCase> getFoldersUseCaseProvider) {
    return new NotesListViewModel_Factory(getNotesUseCaseProvider, deleteNoteUseCaseProvider, getFoldersUseCaseProvider);
  }

  public static NotesListViewModel newInstance(GetNotesUseCase getNotesUseCase,
      DeleteNoteUseCase deleteNoteUseCase, GetFoldersUseCase getFoldersUseCase) {
    return new NotesListViewModel(getNotesUseCase, deleteNoteUseCase, getFoldersUseCase);
  }
}
