package mx.lpalma.photomap.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import mx.lpalma.photomap.R;

/**
 * Created by lpalma on 28/08/2016.
 */
public class PhotoLocationDialog extends DialogFragment {

    private String path;
    public Callback callback;

    private LatLng location;

    public interface Callback{
        public void SetLocation(String file, LatLng points);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.titleLocation);
        builder.setMessage(R.string.messageLocation);
        builder.setPositiveButton(R.string.setLocation, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                callback.SetLocation(path, location);
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

    public void setFile(String file, LatLng location){
        this.path = file;
        this.location = location;
    }
}
