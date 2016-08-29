package mx.lpalma.photomap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mx.lpalma.photomap.R;
import mx.lpalma.photomap.helper.ToastMessage;
import mx.lpalma.photomap.models.Photo;

/**
 * Created by lpalma on 28/08/2016.
 */
public class PhotoData {

    PhotoMapDBHelper db;

    public PhotoData(){

    }

    public boolean insert(Context context, Photo photo){
        boolean result = false;
        try{
            PhotoMapDBHelper helper = new PhotoMapDBHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PhotoMapDBHelper.PATH, photo.getPath());
            values.put(PhotoMapDBHelper.LAT, photo.getLatitude());
            values.put(PhotoMapDBHelper.LNG, photo.getLongitude());
            values.put(PhotoMapDBHelper.NAME, photo.getName());
            values.put(PhotoMapDBHelper.DATE, photo.getDate());
            values.put(PhotoMapDBHelper.TYPE, photo.getType());
            long id = db.insert(PhotoMapDBHelper.TABLE_NAME, null, values);
            if (id == -1) {
                result = true;
                ToastMessage.message(context, R.string.exist);
            } else {
                result = true;
                ToastMessage.message(context, R.string.inserted);
            }
            db.close();
        }catch (Exception e){
            result = false;
            Log.e("ERROR_INSERT",e.getMessage());
        }
        return result;
    }

    public boolean update(Context context, Photo photo) {
        boolean result = false;
        try{
            PhotoMapDBHelper helper = new PhotoMapDBHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PhotoMapDBHelper.PATH, photo.getPath());
            values.put(PhotoMapDBHelper.LAT, photo.getLatitude());
            values.put(PhotoMapDBHelper.LNG, photo.getLongitude());
            values.put(PhotoMapDBHelper.NAME, photo.getName());
            values.put(PhotoMapDBHelper.DATE, photo.getDate());
            values.put(PhotoMapDBHelper.TYPE, photo.getType());
            String whereClause = PhotoMapDBHelper.PID + " = ?";
            String[] whereArgs = {photo.getId() + ""};
            long id = db.update(PhotoMapDBHelper.TABLE_NAME, values, whereClause , whereArgs);
            result = id == -1;
            db.close();
        }catch (Exception e){
            result = false;
            Log.e("ERROR_UPDATE",e.getMessage());
        }
        return result;
    }

    public boolean delete(Context context, Photo photo){
        boolean result = false;
        try {
            PhotoMapDBHelper helper = new PhotoMapDBHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            String whereClause = PhotoMapDBHelper.PID + " = ?";
            String[] whereArgs = { photo.getId() + "" };
            long id = db.delete(PhotoMapDBHelper.TABLE_NAME, whereClause, whereArgs);
            db.close();
            if (id > 0) {
                result = true;
                ToastMessage.message(context, R.string.deleted);
            } else {
                result = false;
                ToastMessage.message(context, R.string.error_deleted);
            }
        } catch (Exception e) {
            result = false;
            Log.e("ERROR_DELETE",e.getMessage());
            ToastMessage.message(context, R.string.error_deleted);
        }
        return result;
    }

    public boolean deleteAll(Context context){
        boolean result = false;
        try {
            PhotoMapDBHelper helper = new PhotoMapDBHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            long id = db.delete(PhotoMapDBHelper.TABLE_NAME, null, null);
            db.close();
            if (id > 0) {
                result = true;
                ToastMessage.message(context, R.string.deletedAll);
            } else {
                result = false;
                ToastMessage.message(context, R.string.error_deleted);
            }
        } catch (Exception e) {
            result = false;
            Log.e("ERROR_DELETE",e.getMessage());
        }
        return result;
    }

    public List<Photo> getAll(Context context)
    {
        List<Photo> photos = null;
        try{
            photos = new ArrayList<Photo>();
            PhotoMapDBHelper helper = new PhotoMapDBHelper(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            String[] columns = { PhotoMapDBHelper.PID, PhotoMapDBHelper.PATH,
                    PhotoMapDBHelper.LAT, PhotoMapDBHelper.LNG, PhotoMapDBHelper.NAME,
                    PhotoMapDBHelper.DATE,PhotoMapDBHelper.TYPE };
            Cursor cursor = db.query(PhotoMapDBHelper.TABLE_NAME, columns, null,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                Photo i = new Photo();
                i.setId(cursor.getInt(0));
                i.setPath(cursor.getString(1));
                i.setLatitude(cursor.getDouble(2));
                i.setLongitude(cursor.getDouble(3));
                i.setName(cursor.getString(4));
                i.setDate(cursor.getString(5));
                i.setType(cursor.getInt(6));
                photos.add(i);
            }
        }catch (Exception e){
            Log.e("ERROR_GETALL",e.getMessage());
        }
        return photos;
    }
}
