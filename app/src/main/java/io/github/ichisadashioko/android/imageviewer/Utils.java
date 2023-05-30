package io.github.ichisadashioko.android.imageviewer;

public class Utils {
    public static String get_file_extension(String filename) {
        int ext_idx = filename.lastIndexOf('.');
        if (ext_idx < 0) {
            return null;
        }

        return filename.substring(ext_idx);
    }

    public static String SINGLE_IMAGE_EXTENSIONS[] = {".JPG", ".JPEG", ".PNG", ".WEBP"};

    public static boolean is_supported_file_extension(String filename) {
        String ext = get_file_extension(filename);
        if (ext == null) {
            return false;
        }

        for (String supported_ext : SINGLE_IMAGE_EXTENSIONS) {
            if (supported_ext.equalsIgnoreCase(ext)) {
                return true;
            }
        }

        return false;
    }
}
