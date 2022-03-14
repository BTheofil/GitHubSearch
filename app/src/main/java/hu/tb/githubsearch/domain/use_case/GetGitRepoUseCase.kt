package hu.tb.githubsearch.domain.use_case

import hu.tb.githubsearch.commons.Resource
import hu.tb.githubsearch.data.remote.dto.GitRepoDto
import hu.tb.githubsearch.data.remote.dto.Item
import hu.tb.githubsearch.domain.repository.GitRepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGitRepoUseCase @Inject constructor(private val repository: GitRepoRepository){

    operator fun invoke(q: String): Flow<Resource<List<Item>>> = flow {
        try {
            emit(Resource.Loading<List<Item>>())
            val repos = repository.getRepositories(q)
            emit(Resource.Success<List<Item>>(repos.body()!!.items))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Item>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<Item>>("Couldn't reach server. Check your internet connection."))
        }
    }
}