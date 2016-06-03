package com.cnec.student;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VeiculoEntryActivity extends AppCompatActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo_entry);

        if ( this.getIntent().hasExtra("ID") ) {
            this.id = this.getIntent().
                    getIntExtra("ID", -1);

            this.showFields( id );
        }

        Button btnSave = (Button) findViewById(R.id.save);
        Button btnDelete = (Button) findViewById(R.id.delete);

        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idField = (EditText) findViewById(R.id.idField);
                EditText placa = (EditText) findViewById(R.id.placa);
                EditText marca = (EditText) findViewById(R.id.marca);
                EditText proprietario = (EditText) findViewById(R.id.proprietario);
                EditText cpf = (EditText) findViewById(R.id.cpf);
                EditText endereco = (EditText) findViewById(R.id.endereco);
                EditText vistoria = (EditText) findViewById(R.id.vistoria);

                if (! isFieldEmpty(placa, marca, proprietario, cpf, endereco, vistoria)){
                    saveRecord(placa, marca, proprietario, cpf, endereco, vistoria, idField);
                    finish();
                }
            }

            private void saveRecord(EditText placa, EditText marca,
                                    EditText proprietario, EditText cpf, EditText endereco, EditText vistoria, EditText idField) {
                if (TextUtils.isEmpty(idField.getText())){
                    VeiculoActivity.getVeiculoDB().
                            insertVeiculo(placa.getText().toString(),
                                    marca.getText().toString(),
                                    proprietario.getText().toString(),
                                    cpf.getText().toString(),
                                    endereco.getText().toString(),
                                    vistoria.getText().toString());
                } else {
                    VeiculoActivity.getVeiculoDB().
                            updateVeiculo(
                                    idField.getText().toString(),
                                    placa.getText().toString(),
                                    marca.getText().toString(),
                                    proprietario.getText().toString(),
                                    cpf.getText().toString(),
                                    endereco.getText().toString(),
                                    vistoria.getText().toString());
                }
            }

            private boolean isFieldEmpty(EditText ...fields) {
                boolean empty = false;

                for (EditText field: fields){
                    if (TextUtils.isEmpty(field.getText().toString())){
                        empty = true;
                        field.setError("Campo deve ser preenchido!");
                    }
                }

                return empty;
            }
        });

        btnDelete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText idField = (EditText) findViewById(R.id.idField);
                EditText placa = (EditText) findViewById(R.id.placa);
                EditText marca = (EditText) findViewById(R.id.marca);
                EditText proprietario = (EditText) findViewById(R.id.proprietario);
                EditText cpf = (EditText) findViewById(R.id.cpf);
                EditText endereco = (EditText) findViewById(R.id.endereco);
                EditText vistoria = (EditText) findViewById(R.id.vistoria);


                    deleteRecord(idField);
                    finish();

            }
            private void deleteRecord(EditText idField) {
                    VeiculoActivity.getVeiculoDB().deleteVeiculo(idField.getText().toString());

            }
        });

    }

    private void showFields(int id) {
        Cursor student = VeiculoActivity.getVeiculoDB().getStudent(id);

        EditText idField = (EditText) findViewById(R.id.idField);
        EditText placa = (EditText) findViewById(R.id.placa);
        EditText marca = (EditText) findViewById(R.id.marca);
        EditText proprietario = (EditText) findViewById(R.id.proprietario);
        EditText cpf = (EditText) findViewById(R.id.cpf);
        EditText endereco = (EditText) findViewById(R.id.endereco);
        EditText vistoria = (EditText) findViewById(R.id.vistoria);

        idField.setText(new Integer(id).toString());
        placa.setText(student.getString(student.getColumnIndex("placa")));
        marca.setText(student.getString(student.getColumnIndex("marca")));
        proprietario.setText(student.getString(student.getColumnIndex("proprietario")));
        cpf.setText(student.getString(student.getColumnIndex("cpf")));
        endereco.setText(student.getString(student.getColumnIndex("endereco")));
        vistoria.setText(student.getString(student.getColumnIndex("vistoria")));
    }
}
