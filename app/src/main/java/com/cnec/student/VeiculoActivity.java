package com.cnec.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VeiculoActivity extends AppCompatActivity {

    private static final int RETCODE = 100 ;
    private static VeiculoHelperDB veiculoDB;
    private ListView listVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);

        this.veiculoDB = new VeiculoHelperDB(this);
        this.veiculoDB.open();


        this.showStudents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Incluir");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case 0:
                Intent i = new Intent(this, VeiculoEntryActivity.class);
                startActivityForResult(i, RETCODE);
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.showStudents();

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showStudents() {
        listVeiculo = (ListView) findViewById(R.id.studentView);

        String[] values = this.veiculoDB.getListVeiculos();

        ArrayAdapter<String> studentData =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, values);


        listVeiculo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Integer idStudent = VeiculoHelperDB.getIdVeiculo(position);

                Intent i = new Intent(view.getContext(),
                        VeiculoEntryActivity.class);

                i.putExtra("ID", idStudent);

                startActivityForResult(i, RETCODE);
            }
        });

        listVeiculo.setAdapter(studentData);

    }

    public static VeiculoHelperDB getVeiculoDB(){
        return veiculoDB;
    }
}
