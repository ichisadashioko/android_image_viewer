package io.github.ichisadashioko.android.imageviewer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ImageViewActivity extends Activity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_layout);

        String input_filepath = getIntent().getExtras().getString("filepath");
        try {
            File file_info = new File(input_filepath);
            String filename = file_info.getName();

            if (Utils.is_supported_file_extension(filename)) {
                Bitmap bm = BitmapFactory.decodeFile(input_filepath);
                iv = findViewById(R.id.image_view);
                iv.setImageBitmap(bm);
            } else {
                throw new Exception("unsupported file type");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            finish();
        }
    }
}
