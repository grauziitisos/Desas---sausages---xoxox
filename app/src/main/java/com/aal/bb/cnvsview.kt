package com.aal.bb
import android.content.Context
import android.graphics.*
import android.view.View
import android.app.Activity
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import android.graphics.RectF





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
        val OffsetV: Int = windowMetrics.bounds.top
        val crd:
                inTupleTsorrynotypeparamThere = MainActivity.Companion.state.setRutis(
            inTupleTsorrynotypeparamThere(
                inCetruple(
                    OffsetV, OffsetV + (this.height - OffsetV) / 3,
                    OffsetV + (this.height - OffsetV) * 2 / 3, OffsetV + (this.height - OffsetV)
                ), inCetruple(0, this.width / 3, this.width * 2 / 3, this.width)
            )
        )
        var pp1 = Path()
        pp1.moveTo(            crd.vertik.a+0.0f,
            crd.horiz.b+0.0f)
        pp1.lineTo(
            crd.vertik.d+0.0f,
            crd.horiz.b+0.0f)

        pp1.moveTo(
            crd.vertik.a+0.0f,
            crd.horiz.c+0.0f)
        pp1.lineTo(
            crd.vertik.d+0.0f,
            crd.horiz.c+0.0f)
        // ||||||||
        pp1.moveTo(
            crd.vertik.b+0.0f,
            crd.horiz.a+0.0f)
        pp1.lineTo(
            crd.vertik.b+0.0f,
            crd.horiz.d+0.0f)
        pp1.moveTo(
            crd.vertik.c+0.0f,
            crd.horiz.a+0.0f)
        pp1.lineTo(
            crd.vertik.c+0.0f,
            crd.horiz.d+0.0f)
//        pp.quadTo(25f, 30f,  windowMetrics.bounds.right+0.0f, d_height+0.0f)
        extraCanvas.drawPath(pp1, paint1)
        val saurakais = Math.min(this.width, this.height - OffsetV)
        val zimesPlatums = saurakais / 3 - 4 * 12
        val left = this.width / 3 / 2 - zimesPlatums / 2 - 0
        val hSolis = this.width / 3
        val top = OffsetV + this.height / 3 / 2 - zimesPlatums / 2 - 0
        val vSolis = (this.height - OffsetV) / 3
        MainActivity.Companion.state.s(desuZime.krusts, 0, 0)
        MainActivity.Companion.state.s(desuZime.nulle, 0, 1)
        MainActivity.Companion.state.s(desuZime.krusts, 0, 2)
        MainActivity.Companion.state.s(desuZime.nulle, 1, 0)
        MainActivity.Companion.state.s(desuZime.krusts, 1, 1)
        MainActivity.Companion.state.s(desuZime.nulle, 1, 2)
        MainActivity.Companion.state.s(desuZime.krusts, 2, 0)
        MainActivity.Companion.state.s(desuZime.nulle, 2, 1)
        MainActivity.Companion.state.s(desuZime.krusts, 2, 2)
        val drws:
                Array<Array<desuZime?>> = MainActivity.Companion.state.g()
        for (i in drws.indices) {
            for (j in drws[0].indices) {
                if (drws[i][j] === desuZime.nulle) {
                    val rect = RectF((left + i * hSolis)+0.0f, (top + j * vSolis)+0.0f, (left + i * hSolis)+zimesPlatums+0.0f, (top + j * vSolis)+zimesPlatums+0.0f)
                    extraCanvas.drawOval(rect, paint2)
                }
                if (drws[i][j] === desuZime.krusts) {
                    pp1.moveTo(
                        (left + i * hSolis)+0.0f, (top + j * vSolis)+0.0f)
                                pp1.lineTo( (left + i * hSolis + zimesPlatums)+0.0f,
                        (top + j * vSolis + zimesPlatums)+0.0f
                    )
                    pp1.moveTo(
                        (left + i * hSolis)+0.0f, (top + j * vSolis + zimesPlatums)+0.0f)
                                pp1.lineTo(
                        (left + i * hSolis + zimesPlatums)+0.0f, (top + j * vSolis)+0.0f
                    )
                    extraCanvas.drawPath(pp1, paint1)
                }
            }
        }


    }
}