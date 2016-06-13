package igcsoftware.bobsaddressbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BobSSD on 6/8/2016.
 */
public class AddressDBHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Address.db";

    public AddressDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Addresses ( " +
                "ContactName TEXT NOT NULL," +
                "Address TEXT NOT NULL," +
                "Address2 TEXT," +
                "City TEXT NOT NULL," +
                "State TEXT NOT NULL," +
                "Zip TEXT NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
