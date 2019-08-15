package com.example.registroincuba;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    String IP = "https://www.zafiraconsulting.com.mx/PHPEventosIncubadora/";
    final Activity activity = this;

    Button botonScan,botonRegistro;
    String scannedData="";
    String resultado="";
    Spinner spinner;
    String spinnerSe = "";
    EditText idText;

    String Radio="nada";
    String RadioId="nada";
    RadioButton rQr, rId;

    ArrayList<String> nombreEventos = new ArrayList<String>();
    ArrayList<Integer> idEventos = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonScan = findViewById(R.id.botonScan);
        botonRegistro = findViewById(R.id.botonRegistro);
        spinner = findViewById(R.id.spinnerEventos);

        idText = findViewById(R.id.idText);
        rQr = findViewById(R.id.rQr);
        rId = findViewById(R.id.rId);

        Connection connection = new Connection();

        try {

            String response = connection.execute(IP+"obtenerDatosEventos.php").get();

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                nombreEventos.add(jsonObject.getString("nombre_evento"));
                idEventos.add(jsonObject.getInt("id_evento"));

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nombreEventos);
        spinner.setAdapter(a);

        botonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator Integrator = new IntentIntegrator(activity);
                Integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                Integrator.setPrompt("");
                Integrator.setBeepEnabled(false);
                Integrator.setCameraId(0);
                Integrator.setBarcodeImageEnabled(false);
                Integrator.initiateScan();
            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = idText.getText().toString();

                if(id.isEmpty()){

                    Toast.makeText(MainActivity.this,"Llena todos los datos",Toast.LENGTH_SHORT).show();

                }else{

                    Connection connection = new Connection();

                    try {

                        String response =  connection.execute(IP+"asistenciaUsuarioId.php?id=" + id + "&evento=1").get();
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() > 0){

                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String respuesta = jsonObject.getString("respuesta");
                        }else {
                            Toast.makeText(MainActivity.this,"Error extraño", Toast.LENGTH_SHORT).show();
                        }


                } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null) {

            scannedData = result.getContents();
            if (scannedData != null) {

                Log.d("HOLA",scannedData);

                Connection connection = new Connection();

                try {

                    String response =  connection.execute(IP+"asistenciaUsuario.php?hash=" + scannedData + "&evento=1").get();

                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0){

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String respuesta = jsonObject.getString("respuesta");

                        if (respuesta != null){
                            Toast.makeText(MainActivity.this,"Respuesta "+respuesta, Toast.LENGTH_SHORT).show();
                            if (respuesta == ""){
                                Toast.makeText(MainActivity.this,"registro exitoso", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            String error = jsonObject.getString("error");
                            Toast.makeText(MainActivity.this,"Error "+error, Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Toast.makeText(MainActivity.this,"Error extraño", Toast.LENGTH_SHORT).show();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void Cambio (View view){

        if (rId.isChecked() == true)
        {
            botonScan.setVisibility(View.INVISIBLE);
            botonRegistro.setVisibility(View.VISIBLE);
            idText.setVisibility(View.VISIBLE);
        }
        else if(rQr.isChecked() == true)
        {
            botonRegistro.setVisibility(View.INVISIBLE);
            botonScan.setVisibility(View.VISIBLE);
            idText.setVisibility(View.INVISIBLE);
        }
    }
}
