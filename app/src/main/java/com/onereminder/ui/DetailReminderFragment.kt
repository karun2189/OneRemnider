package com.onereminder.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import com.onereminder.R
import com.onereminder.core.BaseFragment
import com.onereminder.core.OneReminderApplication
import com.onereminder.db.core.DbHelper
import com.onereminder.db.entity.Call
import com.onereminder.db.entity.Reminder
import kotlinx.android.synthetic.main.add_reminder_fragment.*
import java.util.*


class DetailReminderFragment : BaseFragment(R.layout.add_reminder_fragment) {

    private val mSelectedCalendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeDateTime?.setOnClickListener {
            showDatePicker()
        }

        addReminder?.setOnClickListener {
            Call().apply {
                this.name = "Arun"
                this.phoneNumber = "9876543210"
                this.mReminder = Reminder().apply {
                    this.reminderTime = mSelectedCalendar.time
                    this.title = "Call to"
                    this.description = "Important"
                }
                DbHelper(requireContext()).insertOrUpdateReminder(this)
            }
        }
    }

    private fun showDatePicker() {
        val c: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                mSelectedCalendar.set(Calendar.YEAR, year)
                mSelectedCalendar.set(Calendar.MONTH, monthOfYear)
                mSelectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePicker()
            },
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val c = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(), OnTimeSetListener { view, hourOfDay, minute ->
                mSelectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                mSelectedCalendar.set(Calendar.MINUTE, minute)
                mSelectedCalendar.set(Calendar.SECOND, 0)
                mSelectedCalendar.set(Calendar.MILLISECOND, 0)
                dateTimeCaption.text = mSelectedCalendar.time.toString()
            },
            c[Calendar.HOUR_OF_DAY],
            c[Calendar.MINUTE],
            false
        )
        timePickerDialog.show()
    }
}