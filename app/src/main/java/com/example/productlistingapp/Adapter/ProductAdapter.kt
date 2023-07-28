package com.example.productlistingapp.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.productlistingapp.R
import com.example.productlistingapp.databinding.ListItemBinding
import com.example.productlistingapp.models.ProductDetail.product_detail_Model
import com.example.productlistingapp.models.ProductDetail.product_detail_ModelItem
import java.util.*

class ProductAdapter(private val context:Context,private var itemList: product_detail_Model):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var newData: List<product_detail_ModelItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(newDataList: product_detail_Model) {
        itemList= newDataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {


        /**
         * This method is used to set properties to the views
         *
         * @param view                  is the view of the widget
         * @param CornerRadiusOfTheView is used to set the corners of the view
         * @param Shape                 is used to set the shape of the view
         * Shape ==0  means no shape
         * Shape == 1 means OVAL
         */
        fun setConstraintLayoutProperties(
            view: View?,
            BackgroundColorOfTheView: Int,
            CornerRadiusOfTheView: Float,
            Shape: Int,
            strokeWidth: Int,
            i_top_or_bottom: Int,
            vararg StrokeColor: Int,
        ) {
            try {
                if (view != null && Shape == 0) {
                    val gradientDrawable = GradientDrawable()
                    // i_top_or_bottom ==1 means show corner on the top only
                    if (i_top_or_bottom == 0) {
                        gradientDrawable.cornerRadius = CornerRadiusOfTheView
                    } else if (i_top_or_bottom == 1) {
                        gradientDrawable.cornerRadii = floatArrayOf(
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView,
                            0f,
                            0f,
                            0f,
                            0f
                        )
                    } else if (i_top_or_bottom == 2) {
                        gradientDrawable.cornerRadii = floatArrayOf(
                            0f,
                            0f,
                            0f,
                            0f,
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView,
                            CornerRadiusOfTheView
                        )
                    }
                    for (Color in StrokeColor) {
                        gradientDrawable.setStroke(strokeWidth, Color)
                    }
                    gradientDrawable.setColor(BackgroundColorOfTheView)
                    //                view.setBackgroundDrawable(new RippleDrawable(Objects.requireNonNull(getPressedColorSelector(BackgroundColorOfTheView, context.getResources().getColor(R.color.ripple_color))), gradientDrawable, null));
                    view.setBackgroundDrawable(gradientDrawable)
                } else if (view != null) {
                    val gradientDrawable = GradientDrawable()
                    // SHape == 1 means Oval Shape
                    if (Shape == 1) {
                        gradientDrawable.shape = GradientDrawable.OVAL
                    }
                    for (Color in StrokeColor) {
                        gradientDrawable.setStroke(strokeWidth, Color)
                    }
                    gradientDrawable.setColor(BackgroundColorOfTheView)
                    view.background = RippleDrawable(
                        Objects.requireNonNull<ColorStateList>(
                          getPressedColorSelector(
                                BackgroundColorOfTheView,
                                context.resources.getColor(R.color.ripple_color)
                            )
                        ), gradientDrawable, null
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        /**
         * Set the color or the states defined
         * state_pressed,state_focused and state_activated - given Pressed color
         *
         * @param normalColor
         * @param pressedColor
         * @return - ColorStateList
         */
        fun getPressedColorSelector(normalColor: Int, pressedColor: Int): ColorStateList? {
            return try {
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_focused),
                        intArrayOf(android.R.attr.state_activated),
                        intArrayOf()
                    ), intArrayOf(
                        pressedColor,
                        pressedColor,
                        pressedColor,
                        normalColor
                    )
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                null
            }
        }

        fun bind(item: product_detail_ModelItem) {
           binding.apply {

               if(item.image.isNotEmpty()) {
                   Glide.with(context)
                       .load(item.image)
                       .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Set the disk caching strategy
                       .into(itemImage)
               }
               else{
                   Glide.with(context)
                       .load(R.drawable.product_default)
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .into(itemImage)
               }

               setConstraintLayoutProperties(itemImage, Color.TRANSPARENT,50F,
                   0,0,0,0)
               itemImage.visibility= View.VISIBLE
               itemName.text= "Name :"
               itemNameValue.text= item.product_name
               itemPrice.text= "Price (INR) :"
               itemPriceValue.text= ""+item.price
               itemTax.text= "Tax (%) :"
               itemTaxValue.text= ""+item.tax
               itemType.text= "Type :"
               itemTypeValue.text= item.product_type



           }
        }
    }
}