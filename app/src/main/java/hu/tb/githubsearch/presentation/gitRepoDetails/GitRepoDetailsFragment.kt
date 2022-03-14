package hu.tb.githubsearch.presentation.gitRepoDetails

import android.net.Uri
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.githubsearch.R
import hu.tb.githubsearch.data.remote.dto.Item
import hu.tb.githubsearch.databinding.FragmentGitRepoDetailsBinding

@AndroidEntryPoint
class GitRepoDetailsFragment : Fragment(R.layout.fragment_git_repo_details) {

    private var _binding : FragmentGitRepoDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGitRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedRepo = arguments?.get("repo") as Item

        selectedRepo.apply {
            Glide.with(requireContext()).load(owner.avatar_url).into(binding.avatarIV)
            binding.nameTV.text = name
            binding.linkProfile.text = owner.html_url
            binding.linkProfile.setTextColor(Color.BLUE)
            binding.linkProfile.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(owner.html_url))
                startActivity(browserIntent)
            }
            binding.description.text = description
            binding.createDate.text = created_at
            binding.forksCount.text = forks_count.toString()
            binding.startsCount.text = score.toInt().toString()
            binding.lastUpdate.text = updated_at
            binding.linkRepo.text = html_url
            binding.linkRepo.setTextColor(Color.BLUE)
            binding.linkRepo.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(html_url))
                startActivity(browserIntent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}