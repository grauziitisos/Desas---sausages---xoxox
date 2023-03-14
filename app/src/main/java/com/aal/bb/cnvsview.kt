package com.aal.bb
import android.content.Context
import android.graphics.*
import android.view.View
import android.app.Activity
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;


class cnvsview (context: Context) : View(context)  {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val STROKE_WIDTH = 12f // has to be float
    private val STROKE_WIDTH_2 = 18f // has to be float

    var windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context as Activity)
    val d_height: Int = windowMetrics.bounds.height()+windowMetrics.bounds.top
    val d_width: Int = windowMetrics.bounds.width()/*.width()+windowMetrics.bounds.left*/

    // Set up the paint with which to draw.
    private val paint1 = Paint().apply {
        color = Color.parseColor("#00FAFA")
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }

    // Set up the paint with which to draw.
    private val paint2 = Paint().apply {
        color = Color.parseColor("#A0A0A0")
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH_2 // default: Hairline-width (really thin)
    }
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        // However, this is a memory leak, leaving the old bitmaps around. To fix this,
        // recycle extraBitmap before creating the next one by adding this code right after
        // the call to super
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(Color.parseColor("#FFAAFF"))

    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        var pp = Path()
        var windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context as Activity)
        println(d_width)
        pp.quadTo(25f, 30f,  windowMetrics.bounds.right+0.0f, d_height+0.0f)
        extraCanvas.drawPath(pp, paint1)

    }
}