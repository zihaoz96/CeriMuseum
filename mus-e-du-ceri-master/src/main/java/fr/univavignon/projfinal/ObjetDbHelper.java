package fr.univavignon.projfinal;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE;


public class ObjetDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "obj.db";

    public static final String TABLE_NAME = "obj";

    public static final String _ID = "_id";
    public static final String COLUMN_OBJ_ID = "idObj";
    public static final String COLUMN_OBJ_NAME = "nameObj";
    public static final String COLUMN_OBJ_BRAND = "brandObj";
    public static final String COLUMN_OBJ_CATEGORY = "categorieObj";
    public static final String COLUMN_OBJ_PICTURE = "pictureObj";
    public static final String COLUMN_OBJ_TECHNICAL = "technicalObj";
    public static final String COLUMN_OBJ_TIMEFRAME = "timeFrameObj";
    public static final String COLUMN_OBJ_YEAR = "yearObj";
    public static final String COLUMN_OBJ_WORKING = "workingObj";
    public static final String COLUMN_OBJ_DESCRIPTION = "descriptionObj";

    public ObjetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_OBJ_ID + " TEXT NOT NULL UNIQUE, " +
                COLUMN_OBJ_NAME + " TEXT, " +
                COLUMN_OBJ_BRAND + " TEXT, " +
                COLUMN_OBJ_CATEGORY + " TEXT, " +
                COLUMN_OBJ_PICTURE + " TEXT, " +
                COLUMN_OBJ_TECHNICAL + " TEXT, " +
                COLUMN_OBJ_TIMEFRAME + " TEXT, " +
                COLUMN_OBJ_YEAR + " TEXT, " +
                COLUMN_OBJ_WORKING + " TEXT, " +
                COLUMN_OBJ_DESCRIPTION + " TEXT " +
                " );";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues fill(ObjetMusee obj) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_OBJ_ID, obj.getIdObjet());
        values.put(COLUMN_OBJ_NAME, obj.getName());
        values.put(COLUMN_OBJ_BRAND, obj.getBrand());
        values.put(COLUMN_OBJ_CATEGORY, obj.getCategory());
        values.put(COLUMN_OBJ_PICTURE, obj.getPictures());
        values.put(COLUMN_OBJ_TECHNICAL, obj.getTechnicalDetails());
        values.put(COLUMN_OBJ_TIMEFRAME, obj.getTimeFrame());
        values.put(COLUMN_OBJ_YEAR, obj.getYear());
        values.put(COLUMN_OBJ_WORKING, obj.getWorking());
        values.put(COLUMN_OBJ_DESCRIPTION, obj.getDescription());


        return values;
    }

    public boolean addObj(ObjetMusee obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = fill(obj);

        long rowID = db.insertWithOnConflict(TABLE_NAME, null, values, CONFLICT_IGNORE);
        db.close();

        return (rowID != -1);
    }

    public int updateObj(ObjetMusee obj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = fill(obj);
        // updating row
        return db.updateWithOnConflict(TABLE_NAME, values, _ID + " = ?",
                new String[] { String.valueOf(obj.getId()) }, CONFLICT_IGNORE);
    }

    public Cursor fetchAllObj() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                null, null, null, null, COLUMN_OBJ_NAME +" ASC", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchObjInCategory(String category){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * From "+TABLE_NAME+ " where "+COLUMN_OBJ_CATEGORY+" like '%"+category+"%' order by "+COLUMN_OBJ_NAME+ " ASC", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchObjInCatalog(String keyWord){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * From "+
                TABLE_NAME + " where "+
                COLUMN_OBJ_CATEGORY +" like '%"+keyWord+"%' or " +
                COLUMN_OBJ_NAME +" like '%"+keyWord+"%' or " +
                COLUMN_OBJ_BRAND +" like '%"+keyWord+"%' or " +
                COLUMN_OBJ_TECHNICAL +" like '%"+keyWord+"%' or " +
                COLUMN_OBJ_DESCRIPTION +" like '%"+keyWord+"%'" +

                " order by "+COLUMN_OBJ_NAME+ " ASC", null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ObjetMusee cursorToObj(Cursor cursor) {
        ObjetMusee objetMusee = new ObjetMusee(
                cursor.getLong(cursor.getColumnIndex(_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_BRAND)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_CATEGORY)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_PICTURE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_TECHNICAL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_TIMEFRAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_YEAR)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OBJ_WORKING))
        );
        return  objetMusee;
    }

    public List<ObjetMusee> getAllObjs() {
        List<ObjetMusee> res = new ArrayList<>();
        Cursor cursor = fetchAllObj();
        if (cursor != null){
            res.add(this.cursorToObj(cursor));
            while(cursor.moveToNext()){
                res.add(this.cursorToObj(cursor));
            }
        }
        return res;
    }

    public ObjetMusee getObjet(String idObj){
        ObjetMusee obj = null;
        List<ObjetMusee> objs = getAllObjs();
        for(int i=0;i<objs.size();i++){
            if (objs.get(i).getIdObjet().equals(idObj)){
                obj = objs.get(i);
            }
        }

        return  obj;
    }
}
