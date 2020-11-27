package com.example.mymusic.customview.layoutmanager

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.mymusic.BuildConfig
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import kotlin.math.abs


class StackLayoutManager(config: LayoutConfig) : RecyclerView.LayoutManager() {

    private val TAG = "StackLayoutManager"

    private var mSpace = 60
    private var mUnit = 0
    private var mTotalOffset = 0

    //record the total offset without parallex
    private var mRealOffset = 0
    private var animator: ObjectAnimator? = null
    private var animateValue = 0
    private var duration = 300
    private var lastAnimateValue = 0

    //the max stacked item count;
    private var maxStackCount = 4

    //initial stacked item
    private var initialStackCount = 4
    private var secondaryScale = 0.8f
    private var scaleRatio = 0.4f
    private var parallex = 1f
    private var initialOffset = 0
    private var initial = false
    private var mMinVelocityX = 0
    private var pointerId = 0

    private val mVelocityTracker: VelocityTracker = VelocityTracker.obtain()

    private lateinit var mRV: RecyclerView
    private lateinit var recycler: RecyclerView.Recycler

    private var direction: Align = Align.LEFT

    private var mItemWidth: Int = 0
    private var mItemHeight: Int = 0
    private var sSetScrollState: Method? = null
    private var mPendingScrollPosition: Int = NO_POSITION

    init {
        this.maxStackCount = config.maxStackCount
        this.mSpace = config.space
        this.initialStackCount = config.initialStackCount
        this.secondaryScale = config.secondaryScale
        this.scaleRatio = config.scaleRatio
        this.direction = config.align
        this.parallex = config.parallex
    }


    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        view?.let {
            mRV = it
            it.setOnTouchListener(mTouchListener)

            it.onFlingListener = mOnFlingListener
        }
    }

    override fun onAdapterChanged(oldAdapter: Adapter<*>?, newAdapter: Adapter<*>?) {
        super.onAdapterChanged(oldAdapter, newAdapter)
        initial = false
        mTotalOffset = 0
        mRealOffset = 0
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: State?) {
        if (itemCount <= 0) return
        this.recycler = recycler!!
        recycler.let {
            detachAndScrapAttachedViews(recycler)
            //got the mUnit basing on the first child,of course we assume that  all the item has the same size
            //got the mUnit basing on the first child,of course we assume that  all the item has the same size
            val anchorView = recycler.getViewForPosition(0)
            measureChildWithMargins(anchorView, 0, 0)
            mItemWidth = anchorView.measuredWidth
            mItemHeight = anchorView.measuredHeight
            mUnit = if (canScrollHorizontally()) mItemWidth + mSpace else mItemHeight + mSpace
            //because this method will be called twice
            //because this method will be called twice
            initialOffset = resolveInitialOffset()
            mMinVelocityX =
                ViewConfiguration.get(anchorView.context).scaledMinimumFlingVelocity
            fill(recycler, 0)
        }
    }

    override fun onLayoutCompleted(state: State?) {
        super.onLayoutCompleted(state)
        if (itemCount <= 0)
            return
        if (!initial) {
            fill(recycler, initialOffset, false)
            initial = true
        }
    }

    private fun resolveInitialOffset(): Int {
        var offset = initialStackCount * mUnit
        if (mPendingScrollPosition != NO_POSITION) {
            offset = mPendingScrollPosition * mUnit
            mPendingScrollPosition = NO_POSITION
        }
        if (direction === Align.LEFT) return offset
        if (direction === Align.RIGHT) return -offset
        return if (direction === Align.TOP) offset else offset
    }

    private fun fill(recycler: Recycler, dy: Int, apply: Boolean): Int {
        var delta: Int = direction.sign * dy
        if (apply) delta = (delta * parallex).toInt()
        if (direction === Align.LEFT) return fillFromLeft(recycler, delta)
        if (direction === Align.RIGHT) return fillFromRight(recycler, delta)
        return if (direction === Align.TOP) fillFromTop(
            recycler,
            delta
        ) else dy //bottom alignment is not necessary,we don't support that
    }

    private fun fill(recycler: Recycler?, dy: Int): Int {
        return fill(recycler!!, dy, true)
    }

    private fun fillFromTop(recycler: Recycler, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        detachAndScrapAttachedViews(recycler)
        mTotalOffset += direction.sign * dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child: View? = getChildAt(i)
            if (recycleVertically(child, dy)) removeAndRecycleView(child!!, recycler)
        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace: Int = height - (left(currPos) + mUnit)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount >= 0) currPos - maxStackCount else 0
        val end = if (e >= itemCount) itemCount - 1 else e
        val left = width / 2 - mItemWidth / 2
        //layout views
        for (i in start..end) {
            val view: View = recycler.getViewForPosition(i)
            val scale: Float = scale(i)
            val alpha: Float = alpha(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val top = (left(i) - (1 - scale) * view.measuredHeight / 2).toInt()
            val right: Int = view.measuredWidth + left
            val bottom: Int = view.measuredHeight + top
            layoutDecoratedWithMargins(view, left, top, right, bottom)
            view.alpha = alpha
            view.scaleY = scale
            view.scaleX = scale
        }
        return dy
    }

    private fun fillFromRight(recycler: Recycler, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        detachAndScrapAttachedViews(recycler)
        mTotalOffset += dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child: View? = getChildAt(i)
            if (recycleHorizontally(child, dy)) removeAndRecycleView(child!!, recycler)
        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace: Int = left(currPos)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount <= 0) 0 else currPos - maxStackCount
        val end = if (e >= itemCount) itemCount - 1 else e

        //layout view
        for (i in start..end) {
            val view: View = recycler.getViewForPosition(i)
            val scale: Float = scale(i)
            val alpha: Float = alpha(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val left = (left(i) - (1 - scale) * view.measuredWidth / 2).toInt()
            val top = 0
            val right: Int = left + view.measuredWidth
            val bottom: Int = view.measuredHeight
            layoutDecoratedWithMargins(view, left, top, right, bottom)
            view.alpha = alpha
            view.scaleY = scale
            view.scaleX = scale
        }
        return dy
    }

    private fun fillFromLeft(recycler: Recycler, dy: Int): Int {
        if (mTotalOffset + dy < 0 || (mTotalOffset + dy + 0f) / mUnit > itemCount - 1) return 0
        detachAndScrapAttachedViews(recycler)
        mTotalOffset += direction.sign * dy
        val count = childCount
        //removeAndRecycle  views
        for (i in 0 until count) {
            val child: View? = getChildAt(i)
            if (recycleHorizontally(child, dy)) removeAndRecycleView(child!!, recycler)
        }
        val currPos = mTotalOffset / mUnit
        val leavingSpace: Int = width - (left(currPos) + mUnit)
        val itemCountAfterBaseItem = leavingSpace / mUnit + 2
        val e = currPos + itemCountAfterBaseItem
        val start = if (currPos - maxStackCount >= 0) currPos - maxStackCount else 0
        val end = if (e >= itemCount) itemCount - 1 else e

        //layout view
        for (i in start..end) {
            val view: View = recycler.getViewForPosition(i)
            val scale: Float = scale(i)
            val alpha: Float = alpha(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val left = (left(i) - (1 - scale) * view.measuredWidth / 2).toInt()
            val top = 0
            val right: Int = left + view.measuredWidth
            val bottom: Int = top + view.measuredHeight
            layoutDecoratedWithMargins(view, left, top, right, bottom)
            view.alpha = alpha
            view.scaleY = scale
            view.scaleX = scale
        }
        return dy
    }

    fun setAnimateValue(animateValue: Int) {
        this.animateValue = animateValue
        val dy = this.animateValue - lastAnimateValue
        fill(recycler, direction.sign * dy, false)
        lastAnimateValue = animateValue
    }

    fun getAnimateValue(): Int {
        return animateValue
    }

    /**
     * should recycle view with the given dy or say check if the
     * view is out of the bound after the dy is applied
     *
     * @param view ..
     * @param dy   ..
     * @return ..
     */
    private fun recycleHorizontally(
        view: View? /*int position*/,
        dy: Int
    ): Boolean {
        return view != null && (view.left - dy < 0 || view.right - dy > width)
    }

    private fun recycleVertically(view: View?, dy: Int): Boolean {
        return view != null && (view.top - dy < 0 || view.bottom - dy > height)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: Recycler?,
        state: State?
    ): Int {
        return fill(recycler, dx)
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: State?): Int {
        return fill(recycler, dy)
    }

    override fun canScrollHorizontally(): Boolean {
        return direction === Align.LEFT || direction === Align.RIGHT
    }

    override fun canScrollVertically(): Boolean {
        return direction === Align.TOP || direction === Align.BOTTOM
    }

    private val mTouchListener = OnTouchListener { v, event ->
        mVelocityTracker.addMovement(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            animator?.let {
                if (it.isRunning) {
                    it.cancel()
                }
            }
            pointerId = event.getPointerId(0)
        }
        if (event.action == MotionEvent.ACTION_UP) {
            if (v.isPressed) v.performClick()
            mVelocityTracker.computeCurrentVelocity(1000, 14000f)
            val xVelocity = mVelocityTracker.getXVelocity(pointerId)
            val o = mTotalOffset % mUnit
            val scrollX: Int
            if (abs(xVelocity) < mMinVelocityX && o != 0) {
                scrollX = if (o >= mUnit / 2) mUnit - o else -o
                val dur = (abs((scrollX + 0f) / mUnit) * duration).toInt()
                Log.i(TAG, "onTouch: ======BREW===")
                brewAndStartAnimator(dur, scrollX)
            }
        }
        false
    }

    private val mOnFlingListener: OnFlingListener = object : OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            val o = mTotalOffset % mUnit
            val s = mUnit - o
            val scrollX: Int
            val vel = absMax(velocityX, velocityY)
            scrollX = if (vel * direction.sign > 0) {
                s
            } else -o
            val dur =
                computeSettleDuration(abs(scrollX), abs(vel).toFloat())
            brewAndStartAnimator(dur, scrollX)
            setScrollStateIdle()
            return true
        }
    }

    /**
     * we need to set scrollstate to [RecyclerView.SCROLL_STATE_IDLE] idle
     * stop RV from intercepting the touch event which block the item click
     */
    private fun setScrollStateIdle() {
        try {
            if (sSetScrollState == null){
                sSetScrollState = RecyclerView::class.java.getDeclaredMethod(
                    "setScrollState",
                    Int::class.javaPrimitiveType
                )

            }
            sSetScrollState?.let {
                it.isAccessible = true
                it.invoke(mRV, SCROLL_STATE_IDLE)
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    override fun scrollToPosition(position: Int) {
        if (position > itemCount - 1) {
            Log.i(
                TAG,
                "position is $position but itemCount is $itemCount"
            )
            return
        }
        val currPosition = mTotalOffset / mUnit
        val distance = (position - currPosition) * mUnit
        val dur: Int = computeSettleDuration(abs(distance), 0f)
        brewAndStartAnimator(dur, distance)
    }

    override fun requestLayout() {
        super.requestLayout()
        initial = false
    }

    private fun computeSettleDuration(distance: Int, xvel: Float): Int {
        val sWeight = 0.5f * distance / mUnit
        val velWeight: Float = if (xvel > 0) 0.5f * mMinVelocityX / xvel else 0f
        return ((sWeight + velWeight) * duration).toInt()
    }

    private fun brewAndStartAnimator(dur: Int, finalXorY: Int) {
        animator = ObjectAnimator.ofInt(this@StackLayoutManager, "animateValue", 0, finalXorY)
        animator?.let {
            it.duration = dur.toLong()
            it.start()
            it.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    lastAnimateValue = 0
                }

                override fun onAnimationCancel(animation: Animator?) {
                    lastAnimateValue = 0
                }
            })
        }
    }

    interface CallBack {
        fun scale(totalOffset: Int, position: Int): Float
        fun alpha(totalOffset: Int, position: Int): Float
        fun left(totalOffset: Int, position: Int): Float
    }

    private fun alpha(position: Int): Float {
        val alpha: Float
        val currPos = mTotalOffset / mUnit
        val n = (mTotalOffset + .0f) / mUnit
        alpha = if (position > currPos) 1.0f else {
            //temporary linear map,barely ok
            1 - (n - position) / maxStackCount
        }
        //for precise checking,oh may be kind of dummy
        return if (alpha <= 0.001f) 0f else alpha
    }

    private fun scale(position: Int): Float {
        return when (direction) {
            Align.LEFT, Align.RIGHT -> scaleDefault(
                position
            )
            else -> scaleDefault(position)
        }
    }

    private fun scaleDefault(position: Int): Float {
        val scale: Float
        val currPos = mTotalOffset / mUnit
        val n = (mTotalOffset + .0f) / mUnit
        val x = n - currPos
        // position >= currPos+1;
        scale = if (position >= currPos) {
            when (position) {
                currPos -> 1 - scaleRatio * (n - currPos) / maxStackCount
                currPos + 1 //let the item's (index:position+1) scale be 1 when the item slide 1/2 mUnit,
                    // this have better visual effect
                -> {
                    //                scale = 0.8f + (0.4f * x >= 0.2f ? 0.2f : 0.4f * x);
                    secondaryScale + if (x > 0.5f) 1 - secondaryScale else 2 * (1 - secondaryScale) * x
                }
                else -> secondaryScale
            }
        } else { //position <= currPos
            if (position < currPos - maxStackCount) 0f else {
                1f - scaleRatio * (n - currPos + currPos - position) / maxStackCount
            }
        }
        return scale
    }

    /**
     * @param position the index of the item in the adapter
     * @return the accurate left position for the given item
     */
    private fun left(position: Int): Int {
        val currPos = mTotalOffset / mUnit
        val tail = mTotalOffset % mUnit
        val n = (mTotalOffset + .0f) / mUnit
        val x = n - currPos
        return when (direction) {
            Align.LEFT, Align.TOP ->                 //from left to right or top to bottom
                //these two scenario are actually same
                ltr(position, currPos, tail, x)
            Align.RIGHT -> rtl(
                position,
                currPos,
                tail,
                x
            )
            else -> ltr(position, currPos, tail, x)
        }
    }

    /**
     * @param position ..
     * @param currPos  ..
     * @param tail     .. change
     * @param x        ..
     * @return the left position for given item
     */
    private fun rtl(position: Int, currPos: Int, tail: Int, x: Float): Int {
        //虽然是做对称变换，但是必须考虑到scale给 对称变换带来的影响
        val scale = scale(position)
        val ltr = ltr(position, currPos, tail, x)
        return (width - ltr - mItemWidth * scale).toInt()
    }

    private fun ltr(position: Int, currPos: Int, tail: Int, x: Float): Int {
        var left: Int
        if (position <= currPos) {
            left = if (position == currPos) {
                (mSpace * (maxStackCount - x)).toInt()
            } else {
                (mSpace * (maxStackCount - x - (currPos - position))).toInt()
            }
        } else {
            if (position == currPos + 1) left = mSpace * maxStackCount + mUnit - tail else {
                val closestBaseItemScale = scale(currPos + 1)

                //调整因为scale导致的left误差
//                left = (int) (mSpace * maxStackCount + (position - currPos) * mUnit - tail
//                        -(position - currPos)*(mItemWidth) * (1 - closestBaseItemScale));
                val baseStart =
                    (mSpace * maxStackCount + mUnit - tail + closestBaseItemScale * (mUnit - mSpace) + mSpace).toInt()
                left =
                    (baseStart + (position - currPos - 2) * mUnit - (position - currPos - 2) * (1 - secondaryScale) * (mUnit - mSpace)).toInt()
                if (BuildConfig.DEBUG) Log.i(
                    TAG, "ltr: currPos " + currPos
                            + "  pos:" + position
                            + "  left:" + left
                            + "   baseStart" + baseStart
                            + " currPos+1:" + left(currPos + 1)
                )
            }
            left = if (left <= 0) 0 else left
        }
        return left
    }

    private fun absMax(a: Int, b: Int): Int {
        return if (abs(a) > abs(b)) a else b
    }
}