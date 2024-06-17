package com.mindkerchief.localloan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EstablishmentMarkersInfo.db";
    private static final int DATABASE_VERSION = 1;
    private final String MARKERS_TABLE = "LocalLoanMarkers";
    private final String LAST_LOCATION_TABLE = "LastLocation";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void copyDatabaseFromAssets() throws IOException {
        String outFileName = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        File file = new File(outFileName);

        if (!file.exists()) {
            InputStream myInput = context.getAssets().open(DATABASE_NAME);
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }

    public MapObjectWrapper getMapMarkers() {
        MapObjectWrapper mapObjectWrapper = new MapObjectWrapper();
        int index = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MARKERS_TABLE, null);

        while (cursor.moveToNext()) {
            MapMarkerInfoWrapper markerInfoWrapper = new MapMarkerInfoWrapper();
            String name = cursor.getString(cursor.getColumnIndexOrThrow("establishment_name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("business_day"));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));

            markerInfoWrapper.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            markerInfoWrapper.establishmentName = name;
            markerInfoWrapper.businessHour = cursor.getString(cursor.getColumnIndexOrThrow("business_hour"));
            markerInfoWrapper.businessDay = description;
            markerInfoWrapper.minimumInitialDeposit = cursor.getInt(cursor.getColumnIndexOrThrow("minimum_initial_deposit"));
            markerInfoWrapper.savingsInterestRate = cursor.getFloat(cursor.getColumnIndexOrThrow("savings_interest_rate"));
            markerInfoWrapper.minimumLoanAmount = cursor.getInt(cursor.getColumnIndexOrThrow("minimum_loan_amount"));
            markerInfoWrapper.monthlyAddonInterestRate = cursor.getFloat(cursor.getColumnIndexOrThrow("monthly_addon_interest_rate"));

            mapObjectWrapper.mapMarkerOverlay.add(new OverlayItem(name, description, new GeoPoint(latitude, longitude)));
            mapObjectWrapper.mapMarkerInfoWrapper.add(index++, markerInfoWrapper);
        }
        cursor.close();
        db.close();
        return mapObjectWrapper;
    }

    public void setLastLocation(GeoPoint currentLocation) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("latitude", currentLocation.getLatitude());
        values.put("longitude", currentLocation.getLongitude());

        db.update(LAST_LOCATION_TABLE, values, "id = ?", new String[]{"1"});
        db.close();
    }

    public GeoPoint getLastLocation() {
        GeoPoint currentLocation = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LAST_LOCATION_TABLE, null);
        while (cursor.moveToNext()) {
            currentLocation = new GeoPoint(cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")));
        }
        cursor.close();
        db.close();
        return currentLocation;
    }
}
