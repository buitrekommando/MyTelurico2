package cl.aiep.mytelurico2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView resultadoTextview;
    private Spinner listaCismos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultadoTextview = findViewById(R.id.teluricoTextView);
        listaCismos = findViewById(R.id.spinner_cismos);

        new HttGetRequest().execute();
    }
     private class HttGetRequest extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids){
            String apiURL = "https://api.gael.cloud/general/public/sismos";
            try {
                // Conexión
                URL url = new URL(apiURL);
                HttpsURLConnection connexion = (HttpsURLConnection) url.openConnection();
                connexion.setRequestMethod("GET");

                // Leer respuesta del server
                InputStream inputStream = connexion.getInputStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
                StringBuilder resultado = new StringBuilder();
                String linea;
                while ((linea = reader.readLine())!= null) {
                    resultado.append(linea);

                }

                // Cerrar las conexiones
                //Retrofit esa es la que hay que usar.
                reader.close();
                inputStream.close();
                connexion.disconnect();

                return resultado.toString();


            }catch (IOException e){
                e.printStackTrace();
                return "Error al realizar la solicitud";
            }

        }

        @Override
         protected void onPostExecute(String resultado){
            super.onPostExecute(resultado);
            resultadoTextview.setText(resultado);

        }
     }

}