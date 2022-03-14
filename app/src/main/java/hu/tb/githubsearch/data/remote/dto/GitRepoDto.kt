package hu.tb.githubsearch.data.remote.dto

import java.io.Serializable

data class GitRepoDto(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
) : Serializable