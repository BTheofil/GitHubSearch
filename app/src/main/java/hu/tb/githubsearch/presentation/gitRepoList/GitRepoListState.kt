package hu.tb.githubsearch.presentation.gitRepoList

import hu.tb.githubsearch.data.remote.dto.Item

sealed class GitRepoListState {
    object Empty: GitRepoListState()
    object Loading: GitRepoListState()
    data class Success(val data: List<Item>) : GitRepoListState()
    data class Error(val message: String) : GitRepoListState()
}