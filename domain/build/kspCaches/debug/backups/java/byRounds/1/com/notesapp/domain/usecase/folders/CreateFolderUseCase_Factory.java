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
public final class CreateFolderUseCase_Factory implements Factory<CreateFolderUseCase> {
  private final Provider<FolderRepository> repositoryProvider;

  public CreateFolderUseCase_Factory(Provider<FolderRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CreateFolderUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CreateFolderUseCase_Factory create(Provider<FolderRepository> repositoryProvider) {
    return new CreateFolderUseCase_Factory(repositoryProvider);
  }

  public static CreateFolderUseCase newInstance(FolderRepository repository) {
    return new CreateFolderUseCase(repository);
  }
}
