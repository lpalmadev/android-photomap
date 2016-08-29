package mx.lpalma.photomap.ui.adapters;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.File;

import mx.lpalma.photomap.R;
import mx.lpalma.photomap.helper.PhotoMarkerManager;
import mx.lpalma.photomap.models.Photo;

/**
 * Created by lpalma on 28/08/2016.
 */
public class PhotoMapInfoAdapter implements GoogleMap.InfoWindowAdapter {

    LayoutInflater inflater = null;

    public PhotoMapInfoAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Photo file = PhotoMarkerManager.markers.get(marker);
        View infowindowView = inflater.inflate(R.layout.info_windom_item, null);

        ImageView picture = (ImageView)infowindowView.findViewById(R.id.picThumbnail);
        TextView title = (TextView)infowindowView.findViewById(R.id.markerTitle);
        TextView desc = (TextView)infowindowView.findViewById(R.id.markerDescr);
        if((new File(file.getPath()).exists())){
            picture.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getPath()),100,100));
        }else{
            Resources res = infowindowView.getResources();
            picture.setImageBitmap(BitmapFactory.decodeResource(res, R.drawable.ic_add_a_photo_white_24dp));
        }
        //picture.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getPath()),100,100));
        title.setText(file.getName());
        desc.setText(file.getDate());
        return infowindowView;
    }
}
