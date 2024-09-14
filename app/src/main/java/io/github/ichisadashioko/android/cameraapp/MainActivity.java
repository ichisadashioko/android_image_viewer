package io.github.ichisadashioko.android.cameraapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import MediaStore.Files.FileColumns;
import android.hardware.Camera;

public class MainActivity extends Activity {
    public Camera camera_obj;
    public CameraPreview camera_preview_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera_obj = get_camera_instance();

        if (camera_obj == null) {
            System.err.println("cannot get camera");
        } else {
            camera_preview_obj = new CameraPreview(this, camera_obj);
            FrameLayout preview_container = (FrameLayout) findViewById(R.id.camera_preview);
            preview_container.addView(camera_preview_obj);
        }
    }

    public void button_todo_onclick(View view) {
        return;
    }

    public boolean check_camera_hardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public static Camera get_camera_instance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
        }

        return c;
    }
}

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    public SurfaceHolder surface_holder;
    public Camera camera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        surface_holder = getHolder();
        surface_holder.addCallback(this);

        surface_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {}

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (surface_holder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
        }

        // set preview size and make any resize, rotate or reformatting changes here
        try {
            camera.setPreviewDisplay(surface_holder);
            camera.startPreview();
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
        }
    }
}
