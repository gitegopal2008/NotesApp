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
public final class GetFoldersUseCase_Factory implements Factory<GetFoldersUseCase> {
  private final Provider<FolderRepository> repositoryProvider;

  public GetFoldersUseCase_Factory(Provider<FolderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFoldersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFoldersUseCase_Factory create(Provider<FolderRepository> repositoryProvider) {
    return new GetFoldersUseCase_Factory(repositoryProvider);
  }

  public static GetFoldersUseCase newInstance(FolderRepository repository) {
    return new GetFoldersUseCase(repository);
  }
}
