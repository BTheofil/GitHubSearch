package hu.tb.githubsearch.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.githubsearch.commons.Constants
import hu.tb.githubsearch.data.GitSearchApi
import hu.tb.githubsearch.data.remote.repository.GitRepoImpl
import hu.tb.githubsearch.domain.repository.GitRepoRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGitSearchApi(): GitSearchApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitSearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitRepoRepository(api: GitSearchApi) : GitRepoRepository {
        return GitRepoImpl(api)
    }
}