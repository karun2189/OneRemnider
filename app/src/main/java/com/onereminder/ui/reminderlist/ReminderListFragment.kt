package com.onereminder.ui.reminderlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onereminder.R
import com.onereminder.core.BaseFragment
import com.onereminder.db.core.DbHelper
import com.onereminder.db.core.Utils
import kotlinx.android.synthetic.main.layout_recyclerview.*

class ReminderListFragment : BaseFragment(R.layout.layout_recyclerview) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categories_recyclerview?.apply {
            layoutManager = GridLayoutManager(requireContext(), Utils.getColumnCount())
            adapter =
                ReminderListAdapter(DbHelper(requireContext()).getReminderListByType(Utils.R_CALL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.reminder_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionAdd -> {
                view?.findNavController()
                    ?.navigate(R.id.action_reminderListFragment_to_detailReminderFragment)
            }
        }
        return true
    }
}