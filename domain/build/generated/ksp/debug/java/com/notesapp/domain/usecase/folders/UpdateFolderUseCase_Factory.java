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
public final class UpdateFolderUseCase_Factory implements Factory<UpdateFolderUseCase> {
  private final Provider<FolderRepository> repositoryProvider;

  public UpdateFolderUseCase_Factory(Provider<FolderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateFolderUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateFolderUseCase_Factory create(Provider<FolderRepository> repositoryProvider) {
    return new UpdateFolderUseCase_Factory(repositoryProvider);
  }

  public static UpdateFolderUseCase newInstance(FolderRepository repository) {
    return new UpdateFolderUseCase(repository);
  }
}
