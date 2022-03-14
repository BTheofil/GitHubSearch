package hu.tb.githubsearch.domain.listener

import hu.tb.githubsearch.data.remote.dto.Item

interface GitRepoItemClickListener {
    fun itemClick(repoItem: Item)
}