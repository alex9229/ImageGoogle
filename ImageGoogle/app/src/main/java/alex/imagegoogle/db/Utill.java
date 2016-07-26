package alex.imagegoogle.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Сreate by alex
 */

public class Utill {

    /**
     * Sets the image
     *
     * @param cont     the cont
     * @param thumbUri the URI of image
     */
    public static void setimages(Context cont, String thumbUri) {

        DBhelper dbHelper = new DBhelper(cont, "", null, 1);
        SQLiteDatabase sdb;
        sdb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("thumbUri", thumbUri);
        sdb.insert("IMAGES", null, values);

        dbHelper.close();
        sdb.close();

    }


    /**
     * Gets the allimage.
     *
     * @param cont the cont
     * @return the images HashMap<title,thumbUrl>
     */
    public static ArrayList<String> getallimages(Context cont) {
        DBhelper dbHelper = new DBhelper(cont, "", null, 1);
        SQLiteDatabase sdb;

        sdb = dbHelper.getWritableDatabase();

        ArrayList<String> imag = new ArrayList();
        try {
            Cursor c = sdb.query("IMAGES", null, null, null, null, null, null);
            c.moveToFirst();
            do {
                imag.add(c.getString(c.getColumnIndex("thumbUrl")+2));

            } while (c.moveToNext());
        } catch (CursorIndexOutOfBoundsException e) {
            imag = null;
        }
        System.out.println(imag);
        dbHelper.close();
        sdb.close();
        return imag;
    }


}
