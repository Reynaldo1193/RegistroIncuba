package com.example.registroincuba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private TextView txtid, txtcontraseña;
    private String id,contraseña;
    private Button btnlogin;
    private String IP = "www.zafiraconsulting.com.mx/PHPEventosIncubadora/loginQRScan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtid = (TextView) findViewById(R.id.editTextLID);
        txtcontraseña = (TextView) findViewById(R.id.editTextLContraseña);
        btnlogin = (Button) findViewById(R.id.buttonLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                id = txtid.getText().toString();
                contraseña = txtcontraseña.getText().toString();

                if(id.isEmpty() || contraseña.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Llena todos los datos",Toast.LENGTH_SHORT).show();
                }else{
                    Connection connection = new Connection();

                    try {

                        String response =  connection.execute(IP+"?id="+id+"&password="+contraseña).get();

                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() > 0){

                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String nombre = jsonObject.getString("nombre");

                            Toast.makeText(LoginActivity.this,"Bienvenido "+nombre, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginActivity.this,"No se encontro el usuario", Toast.LENGTH_SHORT).show();
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
}


//Josue, Roberto, Luis, Cesar Fabi, Rodri Reynaldo,