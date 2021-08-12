package com.example.payphoneapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText Telefono, CI, Importe, Civa, Siva;
    Button btn;
    TextView Resp;
    String url = "https://pay.payphonetodoesposible.com/api/Sale";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Telefono = (EditText) findViewById(R.id.txtTelefono);
        CI = (EditText) findViewById(R.id.txtCI);
        Importe = (EditText) findViewById(R.id.txtImporte);
        Civa = (EditText) findViewById(R.id.txtCiva);
        Siva = (EditText) findViewById(R.id.txtSiva);
        btn = (Button) findViewById(R.id.btnEnviar);
        Resp = (TextView) findViewById(R.id.txtResponse);

        requestQueue = Volley.newRequestQueue(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest();
            }
        });
    }

    public JSONObject DatosJSON() {
        JSONObject datos = new JSONObject();
        try {
            datos.put("phoneNumber", Telefono.getText().toString());
            datos.put("countryCode", "593");
            datos.put("clientUserId", CI.getText().toString());
            datos.put("reference", "none");
            datos.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            datos.put("amount", MontoTotal());
            datos.put("amountWithTax", Civa.getText().toString());
            datos.put("amountWithoutTax", Siva.getText().toString());
            datos.put("tax", Importe.getText().toString());
            datos.put("clientTransactionId", id());
        }catch (Exception e){

        }

        return datos;
    }

    public void JsonObjectRequest(){
        System.out.println(DatosJSON().toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST,
                url,
                DatosJSON(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Resp.setText("Código de transacción :" + response.getString("transactionId"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Resp.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> Header = new HashMap<>();
                Header.put("Content-Type", "application/json");
                Header.put("Accept", "application/json");
                Header.put("Authorization", "Bearer i25JbD0HxCNYAfJ-ny15rmGbAQTZbb7wBG0owiYFeua1WslkrlR2Y-HpvLw7-U9UsYaXiWTUPY3u00TJAeKyECmuQmBEuZs-BxiyHhaNFJvGHzeOSqfPjvy2vlcecTRDiOFXBs4BVgV4FaCov_MHoOupz_fj1MHVZvIe5rytMIc7P51JxpnKCOp4lC2lHAiDXU14y3cl3HYiQ5xC7HDRHPyXx5qrYtjgj1H7CKwJuSudsZv4VAvePCqJOK4jVIsxKnzc8zFVUDUgdYr0loeByhW1SuUsNpVeTTuN_rxjIWArM8KNtgsKFfrIoOCeB6bV_A5QdIUeqvkM8aGoG_N_io3FMQQ");
                return Header;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public int MontoTotal(){
        int iva, siniva, coniva;
        iva = Integer.valueOf(Importe.getText().toString());
        siniva = Integer.valueOf(Siva.getText().toString());
        coniva = Integer.valueOf(Civa.getText().toString());


        return iva+siniva+coniva;
    }

    public String id(){

        String rd = "";

        for (int i = 0; i < 6 ; i++) {
            Random r = new Random();
            char abc = (char) (r.nextInt(90 - 65 + 1)+65);
            rd += String.valueOf(abc);
        }

        return rd;
    }


}