package io.github.ichisadashioko.android.imageviewer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;

public class FileBrowser extends Activity {
    public EditText filepath_edit_text;
    public Button show_filepath_button;
    public LinearLayout children_file_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_browser);

        filepath_edit_text = (EditText) findViewById(R.id.filepath_edit_text);
        show_filepath_button = (Button) findViewById(R.id.show_filepath_button);
        children_file_container = (LinearLayout) findViewById(R.id.children_file_container);
    }

    public void on_show_button_clicked(View view) {
        try {
            String input_filepath = filepath_edit_text.getText().toString();
            File file_obj = new File(input_filepath);
            if (file_obj.exists()) {
                if (file_obj.isFile()) {
                    // TODO
                } else if (file_obj.isDirectory()) {
                    File[] child_file_array = file_obj.listFiles();
                    if (child_file_array != null) {

                        if (children_file_container.getChildCount() > 0) {
                            children_file_container.removeAllViews();
                        }

                        for (int i = 0; i < child_file_array.length; i++) {
                            File child_file_obj = child_file_array[i];
                            Button button_view = new Button(this);
                            button_view.setText(child_file_obj.getName());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            button_view.setLayoutParams(lp);

                            children_file_container.addView(button_view);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
