package com.notesapp.domain.usecase.folders;

import com.notesapp.domain.repository.FolderRepository;
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
public final class DeleteFolderUseCase_Factory implements Factory<DeleteFolderUseCase> {
  private final Provider<FolderRepository> repositoryProvider;

  public DeleteFolderUseCase_Factory(Provider<FolderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteFolderUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteFolderUseCase_Factory create(Provider<FolderRepository> repositoryProvider) {
    return new DeleteFolderUseCase_Factory(repositoryProvider);
  }

  public static DeleteFolderUseCase newInstance(FolderRepository repository) {
    return new DeleteFolderUseCase(repository);
  }
}
