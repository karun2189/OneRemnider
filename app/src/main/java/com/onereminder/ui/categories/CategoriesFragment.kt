package com.onereminder.ui.categories

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onereminder.R
import com.onereminder.core.BaseFragment
import com.onereminder.db.core.DbHelper
import com.onereminder.db.core.Utils
import com.onereminder.listeners.ItemClickListeners
import kotlinx.android.synthetic.main.layout_recyclerview.*

class CategoriesFragment : BaseFragment(R.layout.layout_recyclerview), ItemClickListeners {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categories_recyclerview?.apply {
            layoutManager = GridLayoutManager(requireContext(), Utils.getColumnCount())
            adapter = CategoriesAdapter(DbHelper(requireContext()).getAllCategories(), this@CategoriesFragment)
        }
    }

    override fun onSelect(type: Int) {
        view?.findNavController()?.navigate(R.id.action_categoriesFragment_to_reminderListFragment)
    }
}