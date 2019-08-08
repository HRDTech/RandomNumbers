package com.hrd.randomnumbers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrd.randomnumbers.adapter.AdapterListNumber;
import com.hrd.randomnumbers.util.DataConfigApk;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> arrayNumber;
    private RecyclerView listDisplay;
    private TextView textMin;
    private TextView textMax;
    private boolean horizontalList;
    private DataConfigApk configApk;
    private boolean orderAsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configApk = new DataConfigApk(this);

        listDisplay = (RecyclerView) findViewById(R.id.listDisplay);

        textMin = (TextView) findViewById(R.id.textMin);
        textMin.setText(configApk.getData_NumMin());
        textMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textMin.getText().toString().isEmpty()){
                    textMin.setText("1");
                    DialogErrorMinMaxNumber().show();
                }else if (Integer.valueOf(textMin.getText().toString()) < 1 ){
                    textMin.setText("1");
                    DialogErrorMinMaxNumber().show();
                }else if (Integer.valueOf(textMin.getText().toString()) > 100 ){
                    textMin.setText("100");
                    DialogErrorMinMaxNumber().show();
                }else {
                    Gen_NumberRandom();
                    AddDate_List();
                }
            }
        });

        textMax = (TextView) findViewById(R.id.textMax);
        textMax.setText(configApk.getData_NumMax());
        textMax.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textMax.getText().toString().isEmpty()){
                    textMax.setText("100");
                    DialogErrorMinMaxNumber().show();
                }else if (Integer.valueOf(textMax.getText().toString()) > 100 ){
                    textMax.setText("100");
                    DialogErrorMinMaxNumber().show();
                }else if (Integer.valueOf(textMax.getText().toString()) < 1 ){
                    textMax.setText("1");
                    DialogErrorMinMaxNumber().show();
                }else {
                    Gen_NumberRandom();
                    AddDate_List();
                }
            }
        });

        horizontalList = configApk.getData_LayoutList();

        Gen_NumberRandom();
        AddDate_List();

    }

    @Override
    protected void onDestroy() {
        configApk.setData_LayoutList(horizontalList);
        configApk.setData_NumMin(textMin.getText().toString());
        configApk.setData_NumMax(textMax.getText().toString());
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.layoutList:
                LinearLayoutManager layoutManager;

                if (horizontalList){
                    layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    horizontalList = false;
                }else {
                    layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    horizontalList = true;
                }

                listDisplay.setLayoutManager(layoutManager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    void Gen_NumberRandom(){
        arrayNumber = new ArrayList<String>();
        Random handlerRandom = new Random();
        int numMax = Integer.valueOf(textMax.getText().toString());
        int numMin = Integer.valueOf(textMin.getText().toString());

        for (int i=0; i<5; i++){
            int numRandom = handlerRandom.nextInt(((numMax - numMin) +1 ) + numMin);
            arrayNumber.add(String.valueOf(numRandom));
        }
    }

    void AddDate_List(){
        AdapterListNumber adapterNumber = new AdapterListNumber(arrayNumber);

        LinearLayoutManager layoutManager;

        if (horizontalList){
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        }else {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }

        listDisplay.setLayoutManager(layoutManager);
        listDisplay.setAdapter(adapterNumber);
    }

    public void onClick_Random(View v){
        Gen_NumberRandom();
        AddDate_List();
    }

    public void onClick_Order(View v){
        DialogSelectOrder().show();
    }

    /*============================================================================================
    =====Funcion de Ordenamiento de modo de Asendiente y Desendiente.
    El ordenamiento por selección (Selection Sort en inglés) es un algoritmo de ordenamiento que requiere O operaciones para ordenar una lista de n elementos.
Su principio de funcionamiento es:
 - Buscar el mínimo elemento entre una posición i y el final de la lista.
 - Intercambiar el mínimo con el elemento de la posición i.
Lo use por las siguientes razones:
 - Simple de impelmentar.
 - Es 40% mas rapidos que el Ordenación de burbuja y mas 8% mas rapido que el ordenamiento por inserción.
 - Lo he usado en anteriores proyecto resultando muy efectivo.
 =============================================================================================*/
    private AlertDialog DialogSelectOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] items = new CharSequence[2];

        items[0] = "Asendente";
        items[1] = "Desendente";

         orderAsc= true;

        builder.setTitle("Como desea ordenar los números ")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Asendente"){
                            orderAsc = true;
                        } else {
                            orderAsc = false;
                        }
                    }
                })
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Orden Asendiente.
                if (orderAsc) {
                    int tmpNumber = 0;
                    int tmpNumber1 = 0;
                    for (int j = 0; j < (arrayNumber.size() - 1) ; j++) {
                        for (int i = j+1; i < arrayNumber.size(); i++) {

                            tmpNumber = Integer.valueOf(arrayNumber.get(i));
                            tmpNumber1 = Integer.valueOf(arrayNumber.get(j));
                            if (tmpNumber > tmpNumber1) {
                                arrayNumber.set(j, String.valueOf(tmpNumber));
                                arrayNumber.set(i, String.valueOf(tmpNumber1));
                            }
                        }
                    }

                    AddDate_List();
                } else {
                    //Orden Decendiente.
                    int tmpNumber = 0;
                    int tmpNumber1 = 0;
                    for (int j = 0; j < (arrayNumber.size() - 1); j++) {
                        for (int i = j+1; i < arrayNumber.size(); i++) {

                            tmpNumber = Integer.valueOf(arrayNumber.get(i));
                            tmpNumber1 = Integer.valueOf(arrayNumber.get(j));
                            if (tmpNumber < tmpNumber1) {
                                arrayNumber.set(j, String.valueOf(tmpNumber));
                                arrayNumber.set(i, String.valueOf(tmpNumber1));
                            }
                        }
                    }

                    AddDate_List();
                }
            }
        });

        return builder.create();
    }

    private AlertDialog DialogErrorMinMaxNumber(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error en el rango de numeros.")
                .setMessage("El numero insertado esta fuera de rango, Min = 1 y Max = 100")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
