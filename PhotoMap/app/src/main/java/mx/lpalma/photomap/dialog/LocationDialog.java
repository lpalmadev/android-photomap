package mx.lpalma.photomap.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import mx.lpalma.photomap.R;

/**
 * Created by lpalma on 28/08/2016.
 */
public class LocationDialog extends DialogFragment {

    private String path;
    public Callback callback;

    public interface Callback{
        public void SetLocation(String file);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleDialog);
        builder.setMessage(R.string.messageDialog);
        builder.setPositiveButton(R.string.setLocation, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                callback.SetLocation(path);
            }
        });
        builder.setNegativeButton(R.string.currentLocation, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback)activity;
    }

    public void setFile(String file){
        this.path = file;
    }
}
