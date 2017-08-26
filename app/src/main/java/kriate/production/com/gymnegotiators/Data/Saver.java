package kriate.production.com.gymnegotiators.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.fit.Phrases;

/**
 * Created by dima on 25.08.2017.
 * Класс предназначен для взаимодействия с базой данных. В базе кешируются данные полученные с сервера, где расположены темы фразы и пр.
 */

public class Saver {

    //region Constructor

    private static SQLiteDatabase mDataBase;
    private static Saver mSaver;
    private Context mContext;
    private Saver(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
        mContext = context;
    }

    // Сниглетон, возвращается или создается экземпляр класса Saver
    public static Saver Item(Context c){
        if (mSaver == null) {
            mSaver = new Saver(c);
        }
        return mSaver;
    }

    //endregion

    // Сохраняет в базе данных переданную тему. Фразы не сохраняются (поэтому light)
    public void SaveThemeLight(Theme theme){
        //Сохраняет в базе данных тему, если версия переданной темы не совпадает с сохраненной или темы не существует в базе.
        int currentVersion = getThemeVersion(theme.getId());
        if (theme.getVersion() == currentVersion)
            return;

        if (currentVersion == -1 /* Записи в базе данных не существует */){
            ContentValues newValues = new ContentValues();
            newValues.put("id",theme.getId());
            newValues.put("name",theme.getName());
            newValues.put("photo",theme.getPhoto());
            newValues.put("version", theme.getVersion());

            mDataBase.insert("theme", null, newValues);
        }
    }

    // Сохраняет в базе данных фразы для переданной темы, если версия переданной фразы не совпадает с сохраненной или фразы не существует в базе.
    public void SavePhrases(String themeId, List<Phrases> phrases)
    {
        return;
    }

    // Возвращает выбранную тему, вместе с ее фразами
    public Theme GetTheme(String id)
    {
        return new Theme();
    }

    // Возвращает выбранную фразу
    public Phrases GetPhrase(int id)
    {
        return new Phrases();
    }

    //region Helper

    private int getThemeVersion(String id)
    {
        Cursor cursor = null;
        int verId = -1;
        try {
            cursor = mDataBase.rawQuery("select version from theme where id = ?", new String [] {id + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                verId = cursor.getInt(cursor.getColumnIndex("version"));
            }
            return verId;
        } finally {
            cursor.close();
        }
    }

    //endregion

    //region OpenHelper

    private class OpenHelper extends SQLiteOpenHelper {

        // Данные базы данных и таблиц
        private static final String DATABASE_NAME = "gymnegotiators.db";
        private static final int DATABASE_VERSION = 1;
        private ArrayList<String> mQueries = new ArrayList<>();

        private OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

            mQueries.add("CREATE TABLE theme (id TEXT NOT NULL PRIMARY KEY, name TEXT, desk TEXT, Photo TEXT, version INTEGER NOT NULL); ");
//            mQueries.add("CREATE TABLE category (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL); ");
//            mQueries.add("CREATE TABLE sms_category (id_sms INTEGER REFERENCES sms (id), id_category INTEGER REFERENCES category (id), PRIMARY KEY (id_sms ASC, id_category ASC)); ");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                for (String s : mQueries) {
                    db.execSQL(s);
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.beginTransaction();
            try {
                for (int i = oldVersion; i <= mQueries.size(); i++) {
                    db.execSQL(mQueries.get(i));
                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    //endregion
}
