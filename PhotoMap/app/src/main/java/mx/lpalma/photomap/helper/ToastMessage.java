package mx.lpalma.photomap.helper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by lpalma on 28/08/2016.
 */
public class ToastMessage {

    public static Snackbar snackbar;

    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void message(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
