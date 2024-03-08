package com.base.hilt.bind

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.base.hilt.R

/**
 * Generic Adapter for set recycler view
 * @param T Response model
 * @param D Item Layout binding class
 * @property context Context object
 * @property mArrayList ArrayList<T>? model type of list
 * @property layoutResId Int layout name
 * @constructor
 */
abstract class GenericRecyclerViewAdapter<T, D>(
    val context: Context,
    private var mArrayList: ArrayList<T?>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    abstract val layoutResId: Int

    abstract fun onBindData(model: T, position: Int, dataBinding: D)

    abstract fun onItemClick(model: T, position: Int, dataBinding: D)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_loading,
                parent,
                false
            )
            ProgressViewHolder(dataBinding)
        } else {
            val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutResId,
                parent,
                false
            )
            ItemViewHolder(dataBinding)
        }
    }

    internal inner class ProgressViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mArrayList!![position]?.let {a->
            onBindData(
                a, holder.adapterPosition,
                dataBinding = (holder as GenericRecyclerViewAdapter<*, *>.ItemViewHolder).mDataBinding as D
            )

            (holder.mDataBinding as ViewDataBinding).root.setOnClickListener {
                onItemClick(
                    a,
                    position,
                    dataBinding = (holder as GenericRecyclerViewAdapter<*, *>.ItemViewHolder).mDataBinding as D
                )
            }
        }


    }

    override fun getItemCount(): Int {
        return mArrayList!!.size
    }

    fun addItems(arrayList: ArrayList<T?>) {
        val startSize = mArrayList!!.size
        mArrayList = arrayList
        this.notifyDataSetChanged()
//        this.notifyItemRangeChanged(startSize, arrayList.size)
    }

    fun updateItem(model: T, position: Int) {
        mArrayList?.set(position, model)
        this.notifyItemChanged(position)
    }

    fun addItemsRange(arrayList: ArrayList<T?>, startSize: Int) {
        this.mArrayList = arrayList
        this.notifyItemRangeChanged(startSize, arrayList.size)
    }

    fun getItem(position: Int): T? {
        return mArrayList!![position]
    }

    internal inner class ItemViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mDataBinding: D = binding as D
    }

    private var isShowingProgress = false
    fun showLoader(flag: Boolean) {
//        Log.i("madmadgg", "showLoader: ${flag} ")
        isShowingProgress = flag
        if (flag) {
            if (mArrayList?.last() != null) {
                mArrayList?.add(null)
                notifyItemInserted(itemCount)
            }
        } else {
            if (mArrayList?.last() == null) {
                mArrayList?.removeLast()
                notifyItemRemoved(itemCount)
            }
        }
    }

    private fun isLastItemIsLoader(): Boolean {
        return isShowingProgress && mArrayList?.get(mArrayList?.size?.minus(1) ?: 0) == null
    }

}
