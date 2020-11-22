package com.hanna.modebanking.testapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hanna.modebanking.testapplication.OpenForTesting
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.databinding.FragmentJokeListBinding
import com.hanna.modebanking.testapplication.datasource.network.Status
import com.hanna.modebanking.testapplication.di.DaggerInjectHelper
import com.hanna.modebanking.testapplication.extensions.provideViewModel
import com.hanna.modebanking.testapplication.extensions.viewBinding
import com.hanna.modebanking.testapplication.util.ViewStates
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModel
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModelFactory
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

//Prototypes - V
//Tests - V
@OpenForTesting
class JokesListFragment : Fragment(R.layout.fragment_joke_list) {

    @Inject
    lateinit var factory: JokesViewModelFactory
    val viewModel: JokesViewModel by provideViewModel { factory }
    private val binding: FragmentJokeListBinding by viewBinding(FragmentJokeListBinding::bind)
    var jokesListAdapter = JokesListAdapter(::onJokeClicked)
    val viewStates: ViewStates by lazy {
        ViewStates(requireView())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerInjectHelper.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.jokesList.adapter = jokesListAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.jokesList().collect { resource ->
                Log.d("TAG", "onViewCreated: ${resource.data == null}")
                resource.data?.let { jokeList ->
                    jokesListAdapter.submitList(jokeList)
                    viewStates.setState(ViewStates.State.MAIN)
                } ?: run {
                    val state = when (resource.status) {
                        Status.LOADING -> ViewStates.State.LOADING.takeIf { resource.data == null }
                            ?: ViewStates.State.MAIN
                        Status.SUCCESS -> ViewStates.State.MAIN
                        Status.ERROR -> ViewStates.State.ERROR
                    }
                    viewStates.setState(state)
                }
            }
        }
    }

    private fun onJokeClicked(id: Int) {
        JokeDialog.newInstance(id).show(parentFragmentManager, JokeDialog::class.java.name)
    }
}