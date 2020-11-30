package com.example.mymusic.customview.flowLayout

class ViewLocation {

    private var left = 0
    private var right = 0
    private var top = 0
    private var myLine = 0

    fun getMyLine(): Int {
        return myLine
    }

    fun setMyLine(myLine: Int) {
        this.myLine = myLine
    }


    fun getLeft(): Int {
        return left
    }

    fun setLeft(left: Int) {
        this.left = left
    }

    fun getRight(): Int {
        return right
    }

    fun setRight(right: Int) {
        this.right = right
    }

    fun getTop(): Int {
        return top
    }

    fun setTop(top: Int) {
        this.top = top
    }

    fun getBottom(): Int {
        return bottom
    }

    fun setBottom(bottom: Int) {
        this.bottom = bottom
    }

    private var bottom = 0
}