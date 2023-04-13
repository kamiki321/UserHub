package com.kamiki.userhub.ui.userview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamiki.userhub.R
import com.kamiki.userhub.adapter.FollowListAdapter
import com.kamiki.userhub.databinding.FragmentFollowBinding
import com.kamiki.userhub.ui.viewmodel.MainViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val followViewModel by viewModels<MainViewModel>()
    private var listFollower = ArrayList<String>()
    private var listFollowing = ArrayList<String>()

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "section_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel.IsLoading.observe(requireActivity()) { showLoading(it) }


        var position = arguments?.getInt(ARG_POSITION, 0)
        var username = arguments?.getString(ARG_USERNAME)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }


        if (position == 1) {
            followViewModel.getFollowers(username.toString())

            followViewModel.followers.observe(viewLifecycleOwner) {
                binding.followList.layoutManager = LinearLayoutManager(requireContext())

                for (user in it) {
                    listFollower.add(
                        """
                                ${user.avatarUrl};${user.login}.
                            """.trimIndent()
                    )
                }
                val adapter = FollowListAdapter(listFollower)
                binding.followList.adapter = adapter
            }
        } else {
            followViewModel.getFollowing(username.toString())
            followViewModel.following.observe(viewLifecycleOwner) {
                binding.followList.layoutManager = LinearLayoutManager(requireActivity())
                for (user in it) {
                    listFollowing.add(
                        """
                            ${user.avatarUrl};${user.login}
                        """.trimIndent()
                    )
                }
                val adapter = FollowListAdapter(listFollowing)
                binding.followList.adapter = adapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}