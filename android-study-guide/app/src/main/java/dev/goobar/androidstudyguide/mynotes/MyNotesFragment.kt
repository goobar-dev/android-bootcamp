package dev.goobar.androidstudyguide.mynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.goobar.androidstudyguide.R
import dev.goobar.androidstudyguide.databinding.FragmentMyNotesBinding
import dev.goobar.androidstudyguide.databinding.FragmentNoteDetailBinding
import kotlinx.coroutines.launch

/**
 * Displays a list of saved notes
 */
class MyNotesFragment : Fragment() {

  private val viewModel: MyNotesViewModel by viewModels()

  private var _binding: FragmentMyNotesBinding? = null
  private val binding: FragmentMyNotesBinding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMyNotesBinding.inflate(inflater, container, false)

    binding.createNoteButton.setOnClickListener {
      findNavController().navigate(R.id.action_myNotesFragment_to_createNoteFragment)
    }

    binding.showDetailsButton.setOnClickListener {
      findNavController().navigate(R.id.action_myNotesFragment_to_noteDetailFragment)
    }

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.state.collect { uiState ->
          binding.textView.text = uiState.title
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}