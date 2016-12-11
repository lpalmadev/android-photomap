package mx.lpalma.photomap.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import java.io.File;
import java.util.List;

import mx.lpalma.photomap.R;
import mx.lpalma.photomap.helper.PhotoMarkerManager;
import mx.lpalma.photomap.models.Photo;

/**
 * Created by lpalma on 11/12/2016.
 */

public class FileDialog extends DialogFragment {

    public static final int DELETE_FILE = 1;
    public static final int DELETE_FILES = 2;

    public Callback callback;
    public int typeDelete;


    public interface Callback{
        public void UpdateFiles ();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       typeDelete = PhotoMarkerManager.fail.size() > 0 ? DELETE_FILE : DELETE_FILES;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle( typeDelete == DELETE_FILE ? R.string.titleFile :R.string.titleFiles);
        builder.setMessage(typeDelete == DELETE_FILE ? R.string.messageFile :R.string.messageFiles);
        builder.setPositiveButton(R.string.setLocation, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                callback.UpdateFiles();
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
}
