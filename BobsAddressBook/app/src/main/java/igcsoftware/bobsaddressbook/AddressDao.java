package igcsoftware.bobsaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BobSSD on 6/9/2016.
 */
public class AddressDao {
    public static String TABLE_NAME = "Addresses";

    public static String COLUMN_NAME_CONTACTNAME = "ContactName";
    public static String COLUMN_NAME_ADDRESS = "Address";
    public static String COLUMN_NAME_ADDRESS2 = "Address2";
    public static String COLUMN_NAME_CITY = "City";
    public static String COLUMN_NAME_STATE = "State";
    public static String COLUMN_NAME_ZIP = "Zip";

    public static int COLUMN_NUM_CONTACTNAME = 0;
    public static int COLUMN_NUM_ADDRESS = 1;
    public static int COLUMN_NUM_ADDRESS2 = 2;
    public static int COLUMN_NUM_CITY = 3;
    public static int COLUMN_NUM_STATE = 4;
    public static int COLUMN_NUM_ZIP = 5;
    
    public static String[] ALL_COLUMNS = {COLUMN_NAME_CONTACTNAME, COLUMN_NAME_ADDRESS,
            COLUMN_NAME_ADDRESS2, COLUMN_NAME_CITY, COLUMN_NAME_STATE, COLUMN_NAME_ZIP};


    public static List<AddressCard> getCards(Context context) {
        SQLiteDatabase db = new AddressDBHelper(context).getReadableDatabase();
        List<AddressCard> cards = new ArrayList<>();

        //Select all cards from the DB
        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, null);
        while (cursor.moveToNext()) {

            String contactName = cursor.getString(COLUMN_NUM_CONTACTNAME);
            String address = cursor.getString(COLUMN_NUM_ADDRESS);
            String address2 = cursor.getString(COLUMN_NUM_ADDRESS2);
            String city = cursor.getString(COLUMN_NUM_CITY);
            String state = cursor.getString(COLUMN_NUM_STATE);
            String zip = cursor.getString(COLUMN_NUM_ZIP);

            AddressCard card = new AddressCard(contactName, address, address2, city, state, zip);
            cards.add(card);
        }
        return cards;
    }

    public static void insertCard (Context context, AddressCard card) {
        SQLiteDatabase db = new AddressDBHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_CONTACTNAME, card.getContactName());
        cv.put(COLUMN_NAME_ADDRESS, card.getAddress());
        cv.put(COLUMN_NAME_ADDRESS2, card.getAddress2());
        cv.put(COLUMN_NAME_CITY, card.getCity());
        cv.put(COLUMN_NAME_STATE, card.getState());
        cv.put(COLUMN_NAME_ZIP, card.getZip());

        db.insert(TABLE_NAME, null, cv);
    }

    public static void updateCard (Context context, AddressCard newCard, AddressCard oldCard) {
        SQLiteDatabase db = new AddressDBHelper(context).getWritableDatabase();
        ContentValues cv = new ContentValues();

        String where = String.format("ContactName = '%s' AND Address = '%s'",
                oldCard.getContactName(), oldCard.getAddress());

        cv.put(COLUMN_NAME_CONTACTNAME, newCard.getContactName());
        cv.put(COLUMN_NAME_ADDRESS, newCard.getAddress());
        cv.put(COLUMN_NAME_ADDRESS2, newCard.getAddress2());
        cv.put(COLUMN_NAME_CITY, newCard.getCity());
        cv.put(COLUMN_NAME_STATE, newCard.getState());
        cv.put(COLUMN_NAME_ZIP, newCard.getZip());

        db.update(TABLE_NAME, cv, where, null);
    }

    public static void deleteCard(Context context, String contactName, String address) {
        SQLiteDatabase db = new AddressDBHelper(context).getWritableDatabase();

        String where = String.format("ContactName = '%s' AND Address = '%s'", contactName, address);
        db.delete(TABLE_NAME, where, null);
    }
}
