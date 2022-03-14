package hu.tb.githubsearch.data

import hu.tb.githubsearch.data.remote.dto.GitRepoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitSearchApi {

    @GET("/search/repositories")
    suspend fun getRepositories(@Query("q") q: String) : Response<GitRepoDto>
}