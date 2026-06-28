package com.notesapp.features.folders;

import com.notesapp.domain.usecase.folders.CreateFolderUseCase;
import com.notesapp.domain.usecase.folders.DeleteFolderUseCase;
import com.notesapp.domain.usecase.folders.GetFoldersUseCase;
import com.notesapp.domain.usecase.folders.UpdateFolderUseCase;
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
public final class FoldersViewModel_Factory implements Factory<FoldersViewModel> {
  private final Provider<GetFoldersUseCase> getFoldersUseCaseProvider;

  private final Provider<CreateFolderUseCase> createFolderUseCaseProvider;

  private final Provider<DeleteFolderUseCase> deleteFolderUseCaseProvider;

  private final Provider<UpdateFolderUseCase> updateFolderUseCaseProvider;

  public FoldersViewModel_Factory(Provider<GetFoldersUseCase> getFoldersUseCaseProvider,
      Provider<CreateFolderUseCase> createFolderUseCaseProvider,
      Provider<DeleteFolderUseCase> deleteFolderUseCaseProvider,
      Provider<UpdateFolderUseCase> updateFolderUseCaseProvider) {
    this.getFoldersUseCaseProvider = getFoldersUseCaseProvider;
    this.createFolderUseCaseProvider = createFolderUseCaseProvider;
    this.deleteFolderUseCaseProvider = deleteFolderUseCaseProvider;
    this.updateFolderUseCaseProvider = updateFolderUseCaseProvider;
  }

  @Override
  public FoldersViewModel get() {
    return newInstance(getFoldersUseCaseProvider.get(), createFolderUseCaseProvider.get(), deleteFolderUseCaseProvider.get(), updateFolderUseCaseProvider.get());
  }

  public static FoldersViewModel_Factory create(
      Provider<GetFoldersUseCase> getFoldersUseCaseProvider,
      Provider<CreateFolderUseCase> createFolderUseCaseProvider,
      Provider<DeleteFolderUseCase> deleteFolderUseCaseProvider,
      Provider<UpdateFolderUseCase> updateFolderUseCaseProvider) {
    return new FoldersViewModel_Factory(getFoldersUseCaseProvider, createFolderUseCaseProvider, deleteFolderUseCaseProvider, updateFolderUseCaseProvider);
  }

  public static FoldersViewModel newInstance(GetFoldersUseCase getFoldersUseCase,
      CreateFolderUseCase createFolderUseCase, DeleteFolderUseCase deleteFolderUseCase,
      UpdateFolderUseCase updateFolderUseCase) {
    return new FoldersViewModel(getFoldersUseCase, createFolderUseCase, deleteFolderUseCase, updateFolderUseCase);
  }
}
