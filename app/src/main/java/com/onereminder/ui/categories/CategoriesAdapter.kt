package com.onereminder.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.onereminder.R
import com.onereminder.db.core.Utils
import com.onereminder.db.core.listen
import com.onereminder.db.entity.Categories
import com.onereminder.listeners.ItemClickListeners
import kotlinx.android.synthetic.main.item_categories.view.*


class CategoriesAdapter(private val mItemList: List<Categories>,
                        private val mListener: ItemClickListeners?) :
    RecyclerView.Adapter<CategoriesAdapter.PreparationViewHolder>() {

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

        return holder.listen { position, _ ->
            mListener?.onSelect(mItemList[position].type)
        }
    }

    override fun onBindViewHolder(holder: PreparationViewHolder, position: Int) {
        val category = mItemList[position]
        holder.title.text = category.name
        holder.itemView.tag = position
//        Utils.setGradientColorToView(holder.remainingCountTv, category.color)
        holder.categoriesBg.setCardBackgroundColor(category.color)
        holder.icon.setImageResource(category.icon)
//        holder.itemView.onClick {
//            mListener.onItemClick(holder.itemView, position)
//        }
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    inner class PreparationViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_categories, parent, false)) {
        val title: TextView = itemView.categories_name
        val icon: ImageView = itemView.id_remaining_count_tv
        val categoriesBg: CardView = itemView.categories_bg
    }
}