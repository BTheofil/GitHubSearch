package hu.tb.githubsearch.presentation.gitRepoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.githubsearch.commons.Resource
import hu.tb.githubsearch.domain.use_case.GetGitRepoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GitRepoListViewModel @Inject constructor(
    private val getGitRepoUseCase: GetGitRepoUseCase,
    ) : ViewModel(){

    private val _state = MutableStateFlow<GitRepoListState>(GitRepoListState.Empty)
    val state: StateFlow<GitRepoListState> = _state

    fun getCountries(q: String) {
        getGitRepoUseCase(q).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GitRepoListState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _state.value = GitRepoListState.Error(result.message!!)
                }
                is Resource.Loading -> {
                    _state.value = GitRepoListState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}