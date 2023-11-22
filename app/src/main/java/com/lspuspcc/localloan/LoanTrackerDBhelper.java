package com.lspuspcc.localloan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoanTrackerDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "loans.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LOANS = "loans";

    public static final String COUNTER = "count";
    public static final String COLUMN_ID = "id";

    private static final String COLUMN_INTEREST_RATE = "interest_rate";
    public static final String COLUMN_LOAN_AMOUNT = "loan_amount";
    public static final String COLUMN_MONTHLY_PAYMENT = "monthly_payment";
    public static final String COLUMN_NUMBER_OF_PAYMENTS = "number_of_payments";

    public static LoanTrackerDBhelper LoanTrackerDBhelper;


    private static final String CREATE_TABLE_LOANS =
            "CREATE TABLE " + TABLE_LOANS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_INTEREST_RATE + " REAL, " +
                    COLUMN_LOAN_AMOUNT + " REAL, " +
                    COLUMN_MONTHLY_PAYMENT + " REAL, " +
                    COLUMN_NUMBER_OF_PAYMENTS + " INTEGER" +
                    ")";

    public LoanTrackerDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static LoanTrackerDBhelper instanceofdatabase(Context context)
    {
        if (LoanTrackerDBhelper == null)
        {
            LoanTrackerDBhelper = new LoanTrackerDBhelper(context);
        }

        return LoanTrackerDBhelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_LOANS)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(COLUMN_ID)
                .append(" INT, ")
                .append(COLUMN_INTEREST_RATE)
                .append(" TEXT, ")
                .append(COLUMN_LOAN_AMOUNT)
                .append(" TEXT, ")
                .append(COLUMN_MONTHLY_PAYMENT)
                .append(" TEXT")
                .append(COLUMN_NUMBER_OF_PAYMENTS)
                .append(" TEXT)");

        db.execSQL(sql.toString());

        db.execSQL(CREATE_TABLE_LOANS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOANS);
        onCreate(db);
    }

    public void addLoantoDatabase(AddLoanClass loanDetails)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, AddLoanClass.getId());
        contentValues.put(COLUMN_INTEREST_RATE, AddLoanClass.getInterest());
        contentValues.put(COLUMN_LOAN_AMOUNT, AddLoanClass.getLoanAmount());
        contentValues.put(COLUMN_MONTHLY_PAYMENT, AddLoanClass.getMonthlyPayment());
        contentValues.put(COLUMN_NUMBER_OF_PAYMENTS, AddLoanClass.getnumberOfPayments());

        sqLiteDatabase.insert(TABLE_LOANS, null, contentValues);
    }

    public void PopulateArrayList()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_LOANS, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String interest_rate = result.getString(2);
                    String loanAmount = result.getString(3);
                    String monthlyPayment = result.getString(4);
                    String numberOfPayments = result.getString(5);

                    AddLoanClass newLoanDetails = new AddLoanClass(id, loanAmount, interest_rate, numberOfPayments, monthlyPayment);
                    AddLoanClass.loanArrayList.add(newLoanDetails);
                }
            }
        }
    }

    public void updateLoantoDatabase(AddLoanClass loanDetails)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, AddLoanClass.getId());
        contentValues.put(COLUMN_INTEREST_RATE, AddLoanClass.getInterest());
        contentValues.put(COLUMN_LOAN_AMOUNT, AddLoanClass.getLoanAmount());
        contentValues.put(COLUMN_MONTHLY_PAYMENT, AddLoanClass.getMonthlyPayment());
        contentValues.put(COLUMN_NUMBER_OF_PAYMENTS, AddLoanClass.getnumberOfPayments());

        sqLiteDatabase.update(TABLE_LOANS, contentValues, COLUMN_ID + " =? ", new String[]{String.valueOf(loanDetails.getId())});
    }
}
