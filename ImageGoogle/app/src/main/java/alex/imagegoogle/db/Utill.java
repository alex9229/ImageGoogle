package alex.imagegoogle.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Сreate by alex
 */

public class Utill {

    /**
     * Sets the image
     *
     * @param cont     the cont
     * @param thumbUrl the URL of image
     * @param title    the Title of image
     */
    public static void setimages(Context cont, String thumbUrl, String title) {

        DBhelper dbHelper = new DBhelper(cont, "", null, 1);
        SQLiteDatabase sdb;
        sdb = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        System.out.println(title);
        values.put("thumbUrl", thumbUrl);
        values.put("title", title);
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
    public static HashMap<String, String> getallimages(Context cont) {
        DBhelper dbHelper = new DBhelper(cont, "", null, 1);
        SQLiteDatabase sdb;

        sdb = dbHelper.getWritableDatabase();

        HashMap<String, String> imag = new HashMap();
        try {
            Cursor c = sdb.query("IMAGES", null, null, null, null, null, null);
            c.moveToFirst();
            do {
                imag.put(c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("thumbUrl")));
            } while (c.moveToNext());
        } catch (CursorIndexOutOfBoundsException e) {
            imag = null;
        }
        dbHelper.close();
        sdb.close();
        return imag;
    }


}
