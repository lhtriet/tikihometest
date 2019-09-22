package com.example.videoaudioplayer.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.tikihometestapplication.R


class MyAdapter(private val myDataset: Array<String>?) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myDataset?.let {
            val keyword = it[position]
            if (!keyword.contains(" ")) {
                holder.view.tvline1.text = keyword
                holder.view.tvline2.visibility = GONE
            } else {
                holder.view.tvline2.visibility = VISIBLE
                val numberOfChar = keyword.count()
                val midPosition = numberOfChar / 2
                var numberOfFirstLine = midPosition
                var numberOfSecondLine = midPosition

                var n = 0
                while (n < midPosition) {
                    val preMidChar = keyword[midPosition - n]
                    val nextMidChar = keyword[midPosition + n]
                    if (preMidChar.isWhitespace() && nextMidChar.isWhitespace()) {
                        numberOfFirstLine = numberOfChar - (midPosition + n)
                        numberOfSecondLine = (midPosition + n) + 1
                        break
                    } else if (preMidChar.isWhitespace()) {
                        numberOfFirstLine = numberOfChar - (midPosition - n)
                        numberOfSecondLine = midPosition - n + 1
                        break
                    } else if (nextMidChar.isWhitespace()) {
                        numberOfSecondLine = (midPosition + n) + 1
                        numberOfFirstLine = numberOfChar - (midPosition + n)
                        break
                    }
                    n++
                }

                holder.view.tvline1.text = keyword.dropLast(numberOfFirstLine)
                holder.view.tvline2.text = keyword.drop(numberOfSecondLine)
            }
        }
    }

    override fun getItemCount() = myDataset?.let {it.size} ?:run { 0 }
}