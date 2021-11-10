package com.example.geometry

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.View
import android.graphics.BitmapFactory

class Draw2D(context: Context?) : View(context) {

private val paint: Paint = Paint()
private val rect: Rect = Rect()
        val res: Resources = this.resources
private var bitmap: Bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_cursor)

        override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.apply {
        style = Paint.Style.FILL // стиль Заливка
        color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(paint)

        // Солнце
        paint.apply {
        isAntiAlias = true
        color = Color.YELLOW
        }
        canvas?.drawCircle(width - 30F, 30F, 25F, paint)

        // Лужайка
        paint.color = Color.GREEN
        canvas?.drawRect(0F, height - 30F, width.toFloat(), height.toFloat(), paint)

        // Текст над лужайкой
        paint.apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
        textSize = 32F
        }
        canvas?.drawText("Лужайка только для котов", 30F, height - 32F, paint)

        // Лучик солнца
        val x = width - 170F
        val y = 190F

        paint.apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        textSize = 27F
        }

        val beam = "Лучик солнца!"

        canvas?.save()
        canvas?.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas?.drawText(beam, x, y, paint)

        canvas?.restore()

        canvas?.drawBitmap(
        bitmap, (width - bitmap.width).toFloat(), (height - bitmap.height
        - 10).toFloat(), paint
        )
        }
        }