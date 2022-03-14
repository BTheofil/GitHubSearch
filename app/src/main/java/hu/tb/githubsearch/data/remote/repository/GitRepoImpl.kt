package hu.tb.githubsearch.data.remote.repository

import hu.tb.githubsearch.data.GitSearchApi
import hu.tb.githubsearch.data.remote.dto.GitRepoDto
import hu.tb.githubsearch.domain.repository.GitRepoRepository
import retrofit2.Response
import javax.inject.Inject

class GitRepoImpl @Inject constructor(private val api: GitSearchApi) : GitRepoRepository{

    override suspend fun getRepositories(q: String): Response<GitRepoDto> {
        return api.getRepositories(q)
    }

}