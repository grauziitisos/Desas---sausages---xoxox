package com.aal.bb
import android.content.Context
import android.graphics.*
import android.view.View
import android.app.Activity
import android.content.DialogInterface
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import android.graphics.RectF
import android.view.MotionEvent
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.math.roundToInt

// lietoju: https://github.com/googlecodelabs/android-kotlin-drawing-canvas/blob/master/app/src/main/java/com/example/android/minipaint/MyCanvasView.kt




class cnvsview (context: Context) : View(context)  {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val STROKE_WIDTH = 12f // has to be float
    private val STROKE_WIDTH_2 = 18f // has to be float

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
        if(MainActivity.Companion.vards==""){
            waitForMode()
        }
        // the author changed her/his code??? ok will add reference to the new place
        // However, this is a memory leak, leaving the old bitmaps around. To fix this,
        // recycle extraBitmap before creating the next one by adding this code right after
        // the call to super
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(Color.parseColor("#FFAAFF"))

    }
    fun waitForMode(){
        val bld = AlertDialog.Builder(this.context)
        val infl = (this.context as Activity).layoutInflater
        bld.setTitle("Izvēlies režīmu:")
        val dlgl = infl.inflate(R.layout.rezims_ievade, null)
        val edt = dlgl.findViewById<RadioGroup>(R.id.radio_mode_choice)
        bld.setView(dlgl)
        bld.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
            MainActivity.Companion.rezims= if (edt.checkedRadioButtonId == R.id.radio_two_play) playmode.Two else  playmode.Single
            waitForName()
        })
        bld.setOnCancelListener(fun(di: DialogInterface){
            MainActivity.Companion.rezims= if (edt.checkedRadioButtonId == R.id.radio_two_play) playmode.Two else  playmode.Single
            waitForName()
        })
        bld.create()
        bld.show()
    }
    fun waitForName(){
        val bld = AlertDialog.Builder(this.context)
        val infl = (this.context as Activity).layoutInflater
        bld.setTitle("Ievadi savu vārdu:")
        val dlgl = infl.inflate(R.layout.varda_ievade, null)
        val edt = dlgl.findViewById<EditText>(R.id.editTextTextPersonName)
        bld.setView(dlgl)
        bld.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
            MainActivity.Companion.vards = edt.text.toString()
            this.reset()
        })
        bld.setOnCancelListener(fun(di: DialogInterface){
            MainActivity.Companion.vards = edt.text.toString()
            if(MainActivity.Companion.vards=="") waitForName()
        })
        bld.create()
        bld.show()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        MainActivity.Companion.state.isHitterCross = MainActivity.Companion.gaja
        val hasHit: Boolean  = MainActivity.Companion.state.hit(event?.x!!?.roundToInt(), event.y.roundToInt())
//        val hasHit: Boolean? =
//            event?.rawX?.let { MainActivity.Companion.state.hit(it?.roundToInt(), event?.rawY.roundToInt()) }
        if (hasHit) this.invalidate()
        else return super.onTouchEvent(event)
        var uzvrtajs: desuZime? = null
        if (MainActivity.Companion.state.isVictory.also { uzvrtajs = it } != null) {
            if (uzvrtajs === desuZime.krusts) {
                val alertDialogBuilder = AlertDialog.Builder(this.context)
                alertDialogBuilder.setMessage("${MainActivity.Companion.vards} zaude!")

                alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                    this.reset()
                })
                alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                    this.reset()
                })
                alertDialogBuilder.create()
                alertDialogBuilder.show()
                return super.onTouchEvent(event)
            } else {
//                draw(extraCanvas)
                val alertDialogBuilder = AlertDialog.Builder(this.context)
                alertDialogBuilder.setMessage("${MainActivity.Companion.vards} uzvara!")
                alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                    this.reset()
                })
                alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                    this.reset()
                })
                alertDialogBuilder.create()
                alertDialogBuilder.show()
                return super.onTouchEvent(event)
            }
        }
        if ((MainActivity.Companion.state.db or MainActivity.Companion.state.n0) === 0b1111111111) {
            val alertDialogBuilder = AlertDialog.Builder(this.context)
            alertDialogBuilder.setMessage("neizskirts!")
            alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                this.reset()
            })
            alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                this.reset()
            })
            alertDialogBuilder.create()
            alertDialogBuilder.show()
            return super.onTouchEvent(event)
        }
        MainActivity.Companion.gaja = !MainActivity.Companion.gaja;
        if(MainActivity.Companion.rezims == playmode.Single) { doMove(); MainActivity.Companion.gaja = false}
        this.invalidate()
        if ((MainActivity.Companion.state.db or MainActivity.Companion.state.n0) === 0b1111111111) {
            val alertDialogBuilder = AlertDialog.Builder(this.context)
            alertDialogBuilder.setMessage("neizskirts!")
            alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                this.reset()
            })
            alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                this.reset()
            })
            alertDialogBuilder.create()
            alertDialogBuilder.show()
            return super.onTouchEvent(event)
        }
        if (MainActivity.Companion.state.isVictory.also { uzvrtajs = it } != null) {
            if (uzvrtajs === desuZime.krusts) {
                val alertDialogBuilder = AlertDialog.Builder(this.context)
                alertDialogBuilder.setMessage("${MainActivity.Companion.vards} zaude!")

                alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                    this.reset()
                })
                alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                    this.reset()
                })
                alertDialogBuilder.create()
                alertDialogBuilder.show()
                return super.onTouchEvent(event)
            } else {
//                draw(extraCanvas)
                val alertDialogBuilder = AlertDialog.Builder(this.context)
                alertDialogBuilder.setMessage("${MainActivity.Companion.vards} uzvara!")
                alertDialogBuilder.setPositiveButton("ok" ,  fun(_: DialogInterface, _: Int) {
                    this.reset()
                })
                alertDialogBuilder.setOnCancelListener(fun(_: DialogInterface){
                    this.reset()
                })
                alertDialogBuilder.create()
                alertDialogBuilder.show()
                return super.onTouchEvent(event)
            }
        }
        return super.onTouchEvent(event)
    }
    var rnd: Random = Random()
    private fun doMove() {
        var c = rnd.nextInt(3)
        var r = rnd.nextInt(3)
        while (MainActivity.Companion.state.db and (1 shl r * 3 + c) === 1 shl r * 3 + c || MainActivity.Companion.state.n0 and (1 shl r * 3 + c) === 1 shl r * 3 + c) {
            c = rnd.nextInt(3)
            r = rnd.nextInt(3)
        }
        MainActivity.Companion.state.s(desuZime.krusts, r, c)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        (this.context as Activity).setTitle("veiksmi ${MainActivity.Companion.vards}!" )
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
//        var windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(context as Activity)
        val OffsetV: Int = 0//this.top// windowMetrics.bounds.top
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
//        MainActivity.Companion.state.s(desuZime.krusts, 0, 0)
//        MainActivity.Companion.state.s(desuZime.nulle, 0, 1)
//        MainActivity.Companion.state.s(desuZime.krusts, 0, 2)
//        MainActivity.Companion.state.s(desuZime.nulle, 1, 0)
//        MainActivity.Companion.state.s(desuZime.krusts, 1, 1)
//        MainActivity.Companion.state.s(desuZime.nulle, 1, 2)
//        MainActivity.Companion.state.s(desuZime.krusts, 2, 0)
//        MainActivity.Companion.state.s(desuZime.nulle, 2, 1)
//        MainActivity.Companion.state.s(desuZime.krusts, 2, 2)
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


    fun reset() : Boolean{
        MainActivity.Companion.state.clear()
        return redraw()
    }
    fun redraw() : Boolean{
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(Color.parseColor("#FFAAFF"))
        this.invalidate()
//        this.requestLayout()
        return true
    }
}