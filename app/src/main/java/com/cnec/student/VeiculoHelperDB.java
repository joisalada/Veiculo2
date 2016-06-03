package com.cnec.student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VeiculoHelperDB {

    private final VeiculoActivity context;
    private final static String NAMEDATABASE = "Veiculo.db";
    private SQLiteDatabase db;
    private static Integer[] ids;

    public VeiculoHelperDB(VeiculoActivity context) {
        this.context = context;
    }

    public void open() {
        this.db = context.getBaseContext().openOrCreateDatabase(
                NAMEDATABASE, Context.MODE_WORLD_WRITEABLE, null);

        this.createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE if not exists Veiculo ( "+
                "id integer primary key autoincrement, "+
                "placa text not null, "+
                "marca text not null,"+
                "proprietario text not null,"+
                "cpf text not null,"+
                "endereco text not null,"+
                "vistoria text not null);" ;

        this.db.execSQL(sql);
    }


    public void insertVeiculo(String placa, String marca, String proprietario, String cpf, String endereco, String vistoria) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("placa", placa);
        initialValues.put("marca", marca);
        initialValues.put("proprietario", proprietario);
        initialValues.put("cpf", cpf);
        initialValues.put("endereco", endereco);
        initialValues.put("vistoria", vistoria);

        this.db.insert("Veiculo", null, initialValues);

    }

    public String[] getListVeiculos() {
        String[] fields = {"placa", "id"};
        Cursor ret =  db.query("Veiculo", fields, null, null, null, null, null);

        String[] values = new String[ret.getCount()];
        ids = new Integer[ret.getCount()];

        boolean hasRecord = ret.moveToFirst();
        int position = 0;

        while ( hasRecord  ){
            ids[position] = ret.getInt(ret.getColumnIndex("id"));
            values[position++] = ret.getString(ret.getColumnIndex("placa"));
            hasRecord  = ret.moveToNext();
        }

        return values;
    }

    public static Integer getIdVeiculo(int position) {
        return ids[position];
    }

    public Cursor getStudent(int id) {
        String[] fields = {"id", "placa", "marca", "proprietario", "cpf", "endereco", "vistoria"};
        Cursor ret =  db.query("Veiculo",
                fields, "id = " + id,
                null, null, null, null);

        ret.moveToFirst();

        return ret;
    }


    public void updateVeiculo(String id, String placa, String marca, String proprietario, String cpf, String endereco, String vistoria) {
        ContentValues values = new ContentValues();
        values.put("placa", placa);
        values.put("marca", marca);
        values.put("proprietario", proprietario);
        values.put("cpf", cpf);
        values.put("endereco", endereco);
        values.put("vistoria", vistoria);

        db.update("Veiculo", values, "id = ?", new String[]{id});
    }

    public void deleteVeiculo(String id){
        db.delete("Veiculo", "id = " + id, null);
    }

}
