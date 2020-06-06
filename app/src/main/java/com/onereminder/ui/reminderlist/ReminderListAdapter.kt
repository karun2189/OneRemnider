package com.onereminder.ui.reminderlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.onereminder.R
import com.onereminder.db.core.Utils
import com.onereminder.db.helper.ReminderListHelper
import kotlinx.android.synthetic.main.item_categories.view.*

class ReminderListAdapter(private val mItemList: List<ReminderListHelper>/*,
                        private val mListener: RecyclerViewItemClickListener*/) :
    RecyclerView.Adapter<ReminderListAdapter.PreparationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreparationViewHolder {

        val widthAndHeight = Utils.getCategoriesItemWidth()

        val holder = PreparationViewHolder(LayoutInflater.from(parent.context), parent)
        val params = holder.itemView.layoutParams
        params.width = widthAndHeight
        params.height = widthAndHeight


        val bgWidthHeight = (widthAndHeight * 80) / 100
        val bgParams = holder.categoriesBg.layoutParams
        bgParams.width = bgWidthHeight
        bgParams.height = bgWidthHeight

        return holder
    }

    override fun onBindViewHolder(holder: PreparationViewHolder, position: Int) {
        val category = mItemList[position]
        holder.title.text = category.mCallList!![0].name
        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    inner class PreparationViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_reminder_list, parent, false)) {
        val title: TextView = itemView.categories_name
        val categoriesBg: CardView = itemView.categories_bg
    }
}