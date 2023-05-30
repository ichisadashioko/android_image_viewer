package io.github.ichisadashioko.android.imageviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        children_file_container = findViewById(R.id.children_file_container);
    }

    public static String[] SUPPORTED_FILE_EXTENSIONS = {
        ".JPG", ".JPEG", ".PNG", ".ZIP", ".RAR", ".CRB", ".WEBP"
    };

    public void open_filepath(String filepath) {
        try {
            File file_info = new File(filepath);
            if (!file_info.exists()) {
                throw new Exception(filepath + " does not exist!");
            }

            if (file_info.isDirectory()) {
                File[] child_file_info = file_info.listFiles();
                if (child_file_info == null) {
                    throw new Exception("child_file_info is null");
                }

                update_current_filepath(filepath);
                show_file_array(child_file_info);
                // TODO
            } else if (file_info.isFile()) {
                String filename = file_info.getName();
                int ext_idx = filename.lastIndexOf('.');
                if (ext_idx < 0) {
                    // TODO unsupported file type
                    // TODO log to UI
                    System.out.println("unsupported file type ext_idx < 0");
                    return;
                }

                String ext = filename.substring(ext_idx);
                // ext = ext.toUpperCase();

                boolean is_supported_ext = false;
                for (int i = 0; i < SUPPORTED_FILE_EXTENSIONS.length; i++) {
                    if (ext.equalsIgnoreCase(SUPPORTED_FILE_EXTENSIONS[i])) {
                        is_supported_ext = true;
                        break;
                    }
                }

                if (!is_supported_ext) {
                    // TODO unsupported file type
                    // TODO log to UI
                    System.out.println("unsupported file type !is_supported_ext");
                    return;
                }

                // TODO load and show image
            }
        } catch (Exception ex) {
            // TODO log to UI
            ex.printStackTrace(System.err);
        }
    }

    public HorizontalScrollView create_file_listing_entry(String filepath, String render_filename) {
        HorizontalScrollView container = new HorizontalScrollView(this);
        container.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView text_view = new TextView(this);
        text_view.setText(render_filename);
        text_view.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        container.addView(text_view);

        text_view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        open_filepath(filepath);
                    }
                });

        return container;
    }

    public void show_file_array(File[] file_array) {
        try {
            if (file_array == null) {
                System.err.println("file_array is null");
                return;
            }

            children_file_container.removeAllViews();

            if (file_array.length == 0) {
                return;
            }

            for (int i = 0; i < file_array.length; i++) {
                File child_file_obj = file_array[i];
                Button button_view = new Button(this);
                button_view.setText(child_file_obj.getName());
                LinearLayout.LayoutParams lp =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                button_view.setLayoutParams(lp);

                FileBrowserFileEntryView file_entry = new FileBrowserFileEntryView(this, null);
                file_entry.filepath = child_file_obj.getAbsolutePath();
                file_entry.render_filename = child_file_obj.getName();

                children_file_container.addView(button_view);

                button_view.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                open_filepath(file_entry.filepath);
                            }
                        });
            }

        } catch (Exception ex) {
            // TODO log to UI
            ex.printStackTrace(System.err);
        }
    }

    public void update_current_filepath(String filepath) {
        filepath_edit_text.setText(filepath);
    }

    public void on_show_button_clicked(View view) {
        try {
            String input_filepath = filepath_edit_text.getText().toString();
            open_filepath(input_filepath);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
