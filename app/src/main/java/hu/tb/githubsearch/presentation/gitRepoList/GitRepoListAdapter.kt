package hu.tb.githubsearch.presentation.gitRepoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hu.tb.githubsearch.data.remote.dto.Item
import hu.tb.githubsearch.databinding.GitRepoItemBinding
import hu.tb.githubsearch.domain.listener.GitRepoItemClickListener

class GitRepoListAdapter(private var listener: GitRepoItemClickListener) : RecyclerView.Adapter<GitRepoListAdapter.GitRepoListViewHolder>() {

    inner class GitRepoListViewHolder(var binding: GitRepoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Item>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoListViewHolder =
        GitRepoListViewHolder(GitRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: GitRepoListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            repoName.text = item.name
            description.text = item.description
            startsCount.text = item.score.toInt().toString()
            lastUpdate.text = item.updated_at
        }
        holder.itemView.setOnClickListener {
            listener.itemClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}