package com.example.travelcompanion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelcompanion.R
import com.example.travelcompanion.data.EventDatabase
import com.example.travelcompanion.data.EventEntity
import com.example.travelcompanion.data.EventRepository
import com.example.travelcompanion.databinding.FragmentEventListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventListFragment : Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: EventRepository
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = EventRepository(EventDatabase.getInstance(requireContext()).eventDao())

        eventAdapter = EventAdapter(
            onEdit = { event -> openEditor(event.id) },
            onDelete = { event -> deleteEvent(event) }
        )

        binding.eventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }

        binding.addEventButton.setOnClickListener {
            findNavController().navigate(R.id.addEditEventFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            // Room already returns the list sorted by date, so the screen just reflects that flow.
            repository.getAllEvents().collectLatest { events ->
                eventAdapter.submitList(events)
                binding.emptyStateText.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun openEditor(eventId: Int) {
        findNavController().navigate(
            R.id.addEditEventFragment,
            bundleOf("eventId" to eventId)
        )
    }

    private fun deleteEvent(event: EventEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            repository.delete(event)
            Snackbar.make(binding.root, "Event deleted", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
