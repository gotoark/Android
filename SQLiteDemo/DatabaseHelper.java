package arkthepro.com.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by rajesharumugam on 23-11-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //Static Variables
    // If you change the database schema, you must increment the database version.
    //DB version
    private static final int DB_VERSION = 1;

    //DB Name
    public static final String DB_NAME = "contactsDB";

    //Table Name
    public static final String TABLE_NAME = "contacts";

    //Table Columns Names

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "phone_number";

    //Public Constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*Query to Create TABLE
        CREATE TABLE <TableName> (ColumnName1 TYPE,ColumnName2 TYPE,ColumnNameN Type);*/
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_NUMBER + " TEXT" + ")";
        Log.d(TAG, "Query Creating Table---------------"+CREATE_CONTACT_TABLE);

        //Execute Query
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        //Query TO drop TAble
        String DROPE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROPE_TABLE);
        //Create Table Again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /*Methods in CRUD Operation (Create,Read,Update,Delete)
            1.addContact() - to Add a new Contact
            2.getContact() - to Get a Single Contact
            3.getAllContacts() - to Get All Contacts stored in DB
            4.getContactCount() - to Get Total Number of Contacts Stored in DB
            5.updateContact() - to Update a Contacts Stored in DB
            5.deleteContact() - to Remove a Contacts Stored in DB*/
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); //Contact Name
        values.put(KEY_NUMBER, contact.getPhone_number()); //Contact Number
        //Inserting a Row
        db.insert(TABLE_NAME,null,values);
        db.close();
        /*Open the connection to the database as late as possible and
        KEEP it OPEN until the last SQL Query has been retreieved, then close it.

        Opening a database is expensive in terms of both time and resources.
        So the fewer times you have to open it the better performace your program will achieve.*/

    }

    public Contact getContact(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        /*
        Example From Dev Documetnation
        Filter results WHERE "title" = 'My Title'
        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };

     How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor c = db.query(
                FeedEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );*/



        Cursor cursor=db.query(
                TABLE_NAME,
                new String[]{KEY_ID,KEY_NAME,KEY_NUMBER},
                KEY_ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
            if(cursor!=null){
                cursor.moveToFirst();
            }
        Contact contact=new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1)
        ,cursor.getString(2));
        return contact;

    }

    public List<Contact> getAllContacts(){
        List<Contact>  contactList=new ArrayList<Contact>();

        //Select All Query
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
//Looping through all rows and add them in List
        if(cursor.moveToFirst()){
            do{
                Contact contact=new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone_number(cursor.getString(2));
                //Addding contact to list
                contactList.add(contact);
            }while (cursor.moveToNext());
        }


        return contactList;
    }

    public int getContactsCount(){

        String countQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        cursor.close();
        db.close();
        return cursor.getCount();
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_NUMBER,contact.getPhone_number());
        return db.update(TABLE_NAME,values,KEY_ID+" =?",new String[]{String.valueOf(contact.getID())});
    }
    public void deleteContact(Contact contact){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+" =?",new String[]{String.valueOf(contact.getID())});
        db.close();

    }

}
