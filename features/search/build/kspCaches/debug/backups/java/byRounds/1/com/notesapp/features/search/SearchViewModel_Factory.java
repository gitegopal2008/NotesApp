package com.notesapp.features.search;

import com.notesapp.domain.usecase.notes.SearchNotesUseCase;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<SearchNotesUseCase> searchNotesUseCaseProvider;

  public SearchViewModel_Factory(Provider<SearchNotesUseCase> searchNotesUseCaseProvider) {
    this.searchNotesUseCaseProvider = searchNotesUseCaseProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(searchNotesUseCaseProvider.get());
  }

  public static SearchViewModel_Factory create(
      Provider<SearchNotesUseCase> searchNotesUseCaseProvider) {
    return new SearchViewModel_Factory(searchNotesUseCaseProvider);
  }

  public static SearchViewModel newInstance(SearchNotesUseCase searchNotesUseCase) {
    return new SearchViewModel(searchNotesUseCase);
  }
}
