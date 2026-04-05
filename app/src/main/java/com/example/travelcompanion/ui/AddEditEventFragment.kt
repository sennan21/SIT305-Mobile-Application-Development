package com.example.travelcompanion.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.travelcompanion.R
import com.example.travelcompanion.data.EventDatabase
import com.example.travelcompanion.data.EventEntity
import com.example.travelcompanion.data.EventRepository
import com.example.travelcompanion.databinding.FragmentAddEditEventBinding
import com.example.travelcompanion.util.EventFormatter
import com.example.travelcompanion.util.EventValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar

class AddEditEventFragment : Fragment() {

    private var _binding: FragmentAddEditEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: EventRepository
    private var selectedDateTimeMillis: Long? = null
    private var currentEventId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = EventRepository(EventDatabase.getInstance(requireContext()).eventDao())
        currentEventId = arguments?.getInt("eventId", -1) ?: -1

        val categories = resources.getStringArray(R.array.event_categories)
        binding.categorySpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categories.toList()
        )

        binding.dateButton.setOnClickListener { showDatePicker() }
        binding.saveButton.setOnClickListener { saveEvent() }
        binding.deleteButton.setOnClickListener { deleteCurrentEvent() }

        if (currentEventId != -1) {
            binding.screenTitle.text = getString(R.string.edit_event)
            binding.saveButton.text = getString(R.string.update_event)
            binding.deleteButton.visibility = View.VISIBLE
            loadEvent()
        } else {
            binding.screenTitle.text = getString(R.string.add_event)
        }
    }

    private fun loadEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            val event = repository.getEventById(currentEventId) ?: return@launch
            selectedDateTimeMillis = event.eventTimeMillis

            binding.titleInput.setText(event.title)
            binding.locationInput.setText(event.location)
            binding.dateButton.text = EventFormatter.formatDateTime(event.eventTimeMillis)

            val categories = resources.getStringArray(R.array.event_categories)
            val index = categories.indexOf(event.category).coerceAtLeast(0)
            binding.categorySpinner.setSelection(index)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance().apply {
            // Reuse the current selection so edit mode opens on the saved event time.
            selectedDateTimeMillis?.let { timeInMillis = it }
        }

        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                showTimePicker(year, month, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            selectedDateTimeMillis?.let { timeInMillis = it }
        }

        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                selectedDateTimeMillis = selectedCalendar.timeInMillis
                binding.dateButton.text = EventFormatter.formatDateTime(selectedCalendar.timeInMillis)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    private fun saveEvent() {
        val title = binding.titleInput.text.toString().trim()
        val location = binding.locationInput.text.toString().trim()
        val category = binding.categorySpinner.selectedItem?.toString().orEmpty()

        val error = EventValidator.validate(
            title = title,
            eventTimeMillis = selectedDateTimeMillis,
            isEditing = currentEventId != -1
        )

        if (error != null) {
            Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
            return
        }

        val event = EventEntity(
            id = if (currentEventId == -1) 0 else currentEventId,
            title = title,
            category = category,
            location = location,
            eventTimeMillis = selectedDateTimeMillis!!
        )

        viewLifecycleOwner.lifecycleScope.launch {
            if (currentEventId == -1) {
                repository.insert(event)
                Snackbar.make(binding.root, "Event saved", Snackbar.LENGTH_SHORT).show()
            } else {
                repository.update(event)
                Snackbar.make(binding.root, "Event updated", Snackbar.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.eventListFragment)
        }
    }

    private fun deleteCurrentEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            val event = repository.getEventById(currentEventId) ?: return@launch
            repository.delete(event)
            Snackbar.make(binding.root, "Event deleted", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.eventListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
