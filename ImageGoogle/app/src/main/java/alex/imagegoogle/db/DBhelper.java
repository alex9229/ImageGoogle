package alex.imagegoogle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Сreate by alex
 */

public class DBhelper extends SQLiteOpenHelper implements IDBStrings {

    public DBhelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, DataBaseName, factory, version);


        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_IMAGES);


    }

    /* (non-Javadoc)
     * @see if exist then drop dp
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //delete table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS \'" + DataBaseName + "\'");
        onCreate(sqLiteDatabase);
    }


}
