package hu.tb.githubsearch.presentation.gitRepoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.githubsearch.R
import hu.tb.githubsearch.data.remote.dto.Item
import hu.tb.githubsearch.databinding.FragmentGitRepoListBinding
import hu.tb.githubsearch.domain.listener.GitRepoItemClickListener
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GitRepoListFragment : Fragment(R.layout.fragment_git_repo_list), GitRepoItemClickListener{

    private var _binding: FragmentGitRepoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GitRepoListViewModel by viewModels()
    private lateinit var gitRepoListAdapter: GitRepoListAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGitRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)

        subscribeToFlow()
        setupRecyclerView()
        setupBtnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun itemClick(repoItem: Item) {
        val bundle = bundleOf("repo" to repoItem)
        navController.navigate(R.id.action_gitRepoListFragment_to_gitRepoDetailsFragment, bundle)
    }

    private fun subscribeToFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest {
                when(it){
                    is GitRepoListState.Success -> {
                        gitRepoListAdapter.submitList(it.data)
                        binding.progressCircular.visibility = View.GONE
                        binding.emptyList.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                    is GitRepoListState.Loading -> {
                        binding.error.visibility = View.GONE
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.emptyList.visibility = View.GONE
                    }
                    is GitRepoListState.Empty -> {
                        binding.error.visibility = View.GONE
                        binding.progressCircular.visibility = View.GONE
                        binding.emptyList.visibility = View.VISIBLE
                    }
                    is GitRepoListState.Error -> {
                        binding.error.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        binding.emptyList.visibility = View.GONE
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.gitRepoRv.apply {
        gitRepoListAdapter = GitRepoListAdapter(this@GitRepoListFragment)
        adapter = gitRepoListAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupBtnClick() {
        binding.searchButton.setOnClickListener {
            val text: String = binding.searchBar.editText!!.text.toString()
            viewModel.getCountries(text)
        }
    }

}