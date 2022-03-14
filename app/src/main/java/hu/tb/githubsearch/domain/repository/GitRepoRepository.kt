package hu.tb.githubsearch.domain.repository

import hu.tb.githubsearch.data.remote.dto.GitRepoDto
import retrofit2.Response

interface GitRepoRepository {

    suspend fun getRepositories(q: String) : Response<GitRepoDto>
}