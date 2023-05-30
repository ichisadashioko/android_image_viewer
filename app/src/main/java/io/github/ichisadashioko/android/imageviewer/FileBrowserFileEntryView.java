package io.github.ichisadashioko.android.imageviewer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class FileBrowserFileEntryView extends View {
    public static float DEFAULT_TEXT_SIZE = 16f;
    // public static int
    public String filepath;
    public String render_filename;

    public TextPaint text_paint_obj;

    public float text_size = DEFAULT_TEXT_SIZE;
    public float text_padding_left = 5f;
    public float text_padding_bottom = 5f;
    public boolean auto_padding = true;

    public int background_color = Color.rgb(0, 0, 0);
    public int text_color = Color.rgb(255, 255, 255);

    public FileBrowserFileEntryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        text_paint_obj = new TextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        text_paint_obj.setColor(text_color);
        text_paint_obj.setTextSize(text_size);
        canvas.drawText(render_filename, text_padding_left, text_padding_bottom, text_paint_obj);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        if (auto_padding) {}
    }
}
