package com.hanna.modebanking.testapplication.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.databinding.DialogBaseBinding
import com.hanna.modebanking.testapplication.di.DaggerInjectHelper
import com.hanna.modebanking.testapplication.extensions.extraNotNull
import com.hanna.modebanking.testapplication.extensions.provideViewModel
import com.hanna.modebanking.testapplication.extensions.throttledClickListener
import com.hanna.modebanking.testapplication.extensions.viewBinding
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModel
import com.hanna.modebanking.testapplication.viewmodel.JokesViewModelFactory
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import javax.inject.Inject

//Prototypes - V
//Tests - V
class JokeDialog : BottomSheetDialogFragment() {

    private lateinit var primaryButtonObserver: Disposable

    private val jokeId: Int by extraNotNull(JOKE_ID)

    override fun getTheme(): Int = R.style.BaseBottomSheetDialog

    private val binding: DialogBaseBinding by viewBinding(DialogBaseBinding::bind)

    @Inject
    lateinit var factory: JokesViewModelFactory
    val viewModel: JokesViewModel by provideViewModel { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerInjectHelper.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val joke = viewModel.getJokeById(jokeId)
            binding.title.text = getString(R.string.joke_number_format, jokeId)
            binding.body.text = joke.joke
        }

        primaryButtonObserver =
            binding.primaryButton.throttledClickListener { primaryButtonClicked() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    private fun primaryButtonClicked() {
        dismiss()
    }

    override fun onDestroyView() {
        primaryButtonObserver.dispose()
        super.onDestroyView()
    }

    companion object {
        const val JOKE_ID = "joke_id"
        fun newInstance(id: Int): JokeDialog {
            return JokeDialog().apply {
                arguments = bundleOf(JOKE_ID to id)
            }
        }
    }
}