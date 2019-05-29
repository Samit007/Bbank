package Helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;

import Model.User;

import java.util.ArrayList;
import java.util.List;

public class MyHelper extends SQLiteOpenHelper {
    private static final String databaseNAme = "BloodBankDB";
    private static final int dbVersion = 1;
    private static final String tblUser = "tblUser";
    private static final String UserID = "UserID";
    private static final String Fullname = "Fullname";
    private static final String Username = "Username";
    private static final String Password = "Password";
    private static final String Email = "Email";
    private static final String PhoneNo = "Phoneno";
    private static final String Address = "Address";
    private static final String Gender = "Gender";
    private static final String Blood = "Blood";

    public MyHelper(FragmentActivity context) {
        super(context, databaseNAme, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tblUser + "(" + UserID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Fullname + " TEXT," + Username + " TEXT," + Password + " TEXT," + Email + " TEXT," + PhoneNo + " TEXT," + Address + " TEXT," + Gender + " TEXT," + Blood + " TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long InsertData(String fullname, String username, String password,
                           String email, String phoneno, String address,
                           String gender, String blood, SQLiteDatabase db) {
        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Fullname, fullname);
        contentValues.put(Username, username);
        contentValues.put(Password, password);
        contentValues.put(Email, email);
        contentValues.put(PhoneNo, phoneno);
        contentValues.put(Address, address);
        contentValues.put(Gender, gender);
        contentValues.put(Blood, blood);
        id = db.insert(tblUser, null, contentValues);
        return id;
    }


    public List<User> GetAllUsers(SQLiteDatabase db) {
        List<User> userList = new ArrayList<>();
        String[] columns = {UserID, Fullname, Username, Password, Email, PhoneNo, Address, Gender, Blood};
        Cursor cursor = db.query(tblUser, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                userList.add(new User(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7),
                        cursor.getString(8)));
            }
        }
        return userList;
    }

    public List<User> GetWordsByBlood(String blood, SQLiteDatabase db) {
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from tblUser where Blood=?", new String[]{blood});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                userList.add(new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7),
                    cursor.getString(8)));
            }
        }
        return userList;
    }
}