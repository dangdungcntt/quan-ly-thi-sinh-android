package com.nddcoder.quanlythisinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ThiSinhDB extends SQLiteOpenHelper {

    private String tableName = "tbl_thisinh";
    private String col_soBD = "soBD";
    private String col_tenTS = "tenTS";
    private String col_diemToan = "diemToan";
    private String col_diemLy = "diemLy";
    private String col_diemHoa = "diemHoa";

    public ThiSinhDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = String.format("Create table if not exists %s (%s Text Primary key, %s Text,%s Double,%s Double, %s Double);", tableName, col_soBD, col_tenTS, col_diemToan, col_diemLy, col_diemHoa);

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tableName);
        //Tạo lại
        onCreate(db);
    }

    public void themThiSinh(ThiSinh thiSinh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_soBD, thiSinh.getSoBaoDanh());
        contentValues.put(col_tenTS, thiSinh.getHoTen());
        contentValues.put(col_diemToan, thiSinh.getDiemToan());
        contentValues.put(col_diemLy, thiSinh.getDiemLy());
        contentValues.put(col_diemHoa, thiSinh.getDiemHoa());

        db.insert(tableName, null, contentValues);
        db.close();
    }

    public boolean xoaThiSinh(String soBD) {
        String sql =  "delete from " + tableName + " where " + col_soBD + " = '" + soBD + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }

        db.close();
        return true;
    }

    public List<ThiSinh> danhSachThiSinh() {

        List<ThiSinh> dsThiSinh = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                dsThiSinh.add(new ThiSinh(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4)
                ));
            }
        }

        return dsThiSinh;
    }
}
