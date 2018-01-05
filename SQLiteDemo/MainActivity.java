package arkthepro.com.sqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="------LOG-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db=new DatabaseHelper(this);
       /*CRUD OPERATIONS
    */
         //Inserting Contact
        Log.d(TAG, "Inserting---------------------");
        db.addContact(new Contact("Rajesh","9876543210"));
        db.addContact(new Contact("Shangeeth","91234567890"));
        db.addContact(new Contact("SivaBarathy","9988776655"));
        db.addContact(new Contact("Ragavan","9113344500"));

//Reading Contacts
        Log.d(TAG, "ReadingContacts---------------------");
        List<Contact> contacts=db.getAllContacts();
        for(Contact cn:contacts){
            String log="ID :"+cn.getID()+" Name :"+cn.getName()+" Number :"+cn.getPhone_number();
            Log.d(TAG, "User Information: "+log);
        }



    }
}
