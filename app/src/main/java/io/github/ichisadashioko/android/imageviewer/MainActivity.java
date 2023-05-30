package io.github.ichisadashioko.android.imageviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open_file_browser_view(View view) {
        Intent intent = new Intent(this, FileBrowser.class);
        startActivity(intent);
    }
}
