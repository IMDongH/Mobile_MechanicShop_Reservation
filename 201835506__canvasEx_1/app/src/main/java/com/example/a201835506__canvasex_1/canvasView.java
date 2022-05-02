package com.example.a201835506__canvasex_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class canvasView extends View {
    private Paint paint;

    public canvasView(Context context){
        super(context);

        init(context); // 초기화
    }

    public canvasView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        init(context); // 초기화
    }

    private void init(Context context){
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(100,100,200,200, paint);
    }

}