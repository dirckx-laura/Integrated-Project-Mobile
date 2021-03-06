package com.example.integratedproject

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat

private const val STROKE_WIDTH = 12f

class MyCanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var path = Path()
    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private var coordlist = ArrayList<Float>()
    private var derivativelist=ArrayList<Float>()
    private var coordstring: String = "";

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap

    private var isRedraw:Boolean=false
    private val paint = Paint().apply {
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = STROKE_WIDTH
    }

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        if(!isRedraw){
            if (::extraBitmap.isInitialized) extraBitmap.recycle()
            extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            extraCanvas = Canvas(extraBitmap)
            extraCanvas.drawColor(backgroundColor)
        }
    }
    fun Init(width: Int,height: Int){
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }
    fun Redraw(coordlist: ArrayList<Float>,width: Int,height: Int) {
        isRedraw=true
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        var floatArrayy = coordlist.toFloatArray()
        //extraCanvas.drawLines(floatArrayy, paint)
        Log.d("test","Redrawing...")

        val path = Path()
/*        if(floatArrayy.size>3){
            for(i in 0 until floatArrayy.size step 2){
                if (i === 0) {
                    derivativelist.add((floatArrayy[i+2] - floatArrayy[i]) / 3)
                    derivativelist.add((floatArrayy[i+3]- floatArrayy[i+1]) / 3)
                } else if (i === floatArrayy.size - 2) {

                    derivativelist.add((floatArrayy[i] - floatArrayy[i-2]) / 3)
                    derivativelist.add((floatArrayy[i+1] - floatArrayy[i-1]) / 3)
                } else {

                    derivativelist.add((floatArrayy[i+2] - floatArrayy[i-2]) / 3)
                    derivativelist.add((floatArrayy[i+3] - floatArrayy[i-1]) / 3)
                }
            }
        }*/
        var derivativeArray=derivativelist.toFloatArray()
        var first = true
        for(i in 0 until floatArrayy.size step 4){
            if(first){
                first=false
                path.moveTo(floatArrayy[i],floatArrayy[i+1])
            }
            else if(i<floatArrayy.size-3){
                path.quadTo(floatArrayy[i],floatArrayy[i+1],floatArrayy[i+2],floatArrayy[i+3])
            }
            else{
                //path.cubicTo(floatArrayy[i-2]+derivativeArray[i-2],floatArrayy[i-1]+derivativeArray[i-1],floatArrayy[i]-derivativeArray[i],floatArrayy[i+1]-derivativeArray[i+1],floatArrayy[i],floatArrayy[i+1])
                path.lineTo(floatArrayy[i], floatArrayy[i+1]);
            }
        }
        extraCanvas.drawPath(path, paint)
        //invalidate()
    }

    fun getDrawing(): ArrayList<Float> {
        return coordlist
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
      // Log.d("test","x:${currentX}, y:${currentY}")
        coordlist.add(currentX)
        coordlist.add(currentY)
        Log.d("x:",currentX.toString())
        Log.d("y",currentY.toString())
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(
                currentX,
                currentY,
                (motionTouchEventX + currentX) / 2,
                (motionTouchEventY + currentY) / 2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    fun clearCanavas() {
        coordlist.clear()
        extraCanvas.drawColor(Color.WHITE)
    }

    fun saveCoords() {
        var coordstringTemp: String = ""
        coordlist.forEach { it ->
            coordstringTemp += "${it.toString()},"
        }
        coordstring = coordstringTemp.substring(0, coordstringTemp.length - 1)
    }

    fun getCoords() : String {
        return coordstring

    }

    private fun touchUp() {
        // Reset the path so it doesn't get drawn again.
        path.reset()
    }
}