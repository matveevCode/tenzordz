package com.example.myexample.ui.animationprofile.view

import android.animation.ArgbEvaluator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myexample.R
import com.example.myexample.databinding.FragmentAnimationBinding

class AnimationFragment : Fragment() {

    private lateinit var binding: FragmentAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAnimationBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() = binding.run {
        animateButton.setOnClickListener {
            createValueAnimator().start()
        }
    }

    private fun createValueAnimator(): ValueAnimator = binding.run {
        val squareSizeHolder = getSquareSizeHolder()
        val squareColorHolder = getSquareColorHolder()
        val rectangleHeightHolder = getRectangleHeightHolder()

        ValueAnimator.ofPropertyValuesHolder(
            squareSizeHolder,
            squareColorHolder,
            rectangleHeightHolder
        ).apply {
            duration = ANIMATION_DURATION_MS
            setupListener()
        }
    }

    private fun getSquareSizeHolder(): PropertyValuesHolder {
        val sizeFrom = binding.square.height
        val sizeTo = resources.getDimensionPixelSize(R.dimen.square_to_size)

        return PropertyValuesHolder.ofInt(PROPERTY_NAME_SQUARE_SIZE, sizeFrom, sizeTo)
    }

    private fun getSquareColorHolder(): PropertyValuesHolder {
        val colorFrom = (binding.square.background as ColorDrawable).color
        val colorTo = ContextCompat.getColor(requireContext(), R.color.square_end_color)

        return PropertyValuesHolder.ofInt(PROPERTY_NAME_SQUARE_COLOR, colorFrom, colorTo).apply {
            setEvaluator(ArgbEvaluator())
        }
    }

    private fun getRectangleHeightHolder(): PropertyValuesHolder {
        val heightFrom = binding.rectangle.height
        val heightTo = resources.getDimensionPixelSize(R.dimen.rectangle_to_height)

        return PropertyValuesHolder.ofInt(
            PROPERTY_NAME_RECTANGLE_HEIGHT,
            heightFrom,
            heightTo
        )
    }

    private fun ValueAnimator.setupListener() = binding.run {
        addUpdateListener { valueAnimator ->
            val currentSize = valueAnimator.getAnimatedValue(PROPERTY_NAME_SQUARE_SIZE) as Int
            square.layoutParams.height = currentSize
            square.layoutParams.width = currentSize
            square.requestLayout()

            val currentColor = valueAnimator.getAnimatedValue(PROPERTY_NAME_SQUARE_COLOR) as Int
            square.setBackgroundColor(currentColor)

            val currentHeight =
                valueAnimator.getAnimatedValue(PROPERTY_NAME_RECTANGLE_HEIGHT) as Int
            rectangle.layoutParams.height = currentHeight
            rectangle.requestLayout()
        }
    }

    companion object {
        private const val PROPERTY_NAME_SQUARE_SIZE = "SQUARE_SIZE"
        private const val PROPERTY_NAME_SQUARE_COLOR = "SQUARE_COLOR"
        private const val PROPERTY_NAME_RECTANGLE_HEIGHT = "RECTANGLE_HEIGHT"

        private const val ANIMATION_DURATION_MS = 400L
    }
}
