package com.pidelectronics.storm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.pidelectronics.storm.gloables.ciudadConsultada;
import static com.pidelectronics.storm.gloables.mostrarToast;

public class MainActivity extends AppCompatActivity implements CiudadFragment.ciudadFragmentCallbacks,
        PostalFragment.postalFragmentCallbacks {

    Button btnCiudad, btnCodigoPostal, btnGps;
    Context context;

    NavController navController;
    boolean permisoGps = false;

    //constantes:
    final String urlBase = "http://api.openweathermap.org/";
    final String API_KEY = "94a019a5991ffe071001d0b4c162c6d7";
    String UNIDADES = "units=metric";
    String IDIOMA = "lang=es";
    String URL = "data/2.5/weather?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        btnCiudad = findViewById(R.id.btnCiudad);
        btnCodigoPostal = findViewById(R.id.btnCodigoPostal);
        btnGps = findViewById(R.id.btnGps);
        navController = Navigation.findNavController(MainActivity.this,R.id.navHost);

        btnCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.ciudadFragment);

            }
        });

        btnCodigoPostal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.postalFragment);
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (revisarInternet()) {
                    Location location = obtenerUbicacion();
                    if (location != null) {
                        ciudadConsultada = new ciudad();
                        ciudadConsultada.setUbicacionGps(location);
                        descargarClima(ciudadConsultada);
                    }
                }else{
                    mostrarToast(MainActivity.this,"Sin conexion a internet!");
                }
            }
        });
    }


    private Location obtenerUbicacion(){
        Location ubicacion = null;
        permisoGps = checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (permisoGps){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean gpsActivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gpsActivo) {
                ubicacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (ubicacion != null){
                    mostrarToast(context,"Ubicaicon obtenida por gps");
                    return ubicacion;
                }else{
                    ubicacion = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (ubicacion != null){
                        mostrarToast(context,"Ubicaicon obtenida por red telefonica");
                        return ubicacion;
                    }else{
                        mostrarToast(context,"No es posible obtener ubicacion en este momento!");
                    }
                }
            }else{
                mostrarToast(context,"GPS Desactivado, active el gps para continuar!");
            }
        }else{
            requestPermissions(new String[]{ACCESS_FINE_LOCATION},14);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14){
            obtenerUbicacion();
        }
    }

    private boolean revisarInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void descargarClima(ciudad ciudad){
        ciudadConsultada = new ciudad();
        String peticionUrl = "";

        if (ciudad.getLocalidad() != null){
            peticionUrl = URL + "q=" + ciudad.getLocalidad() + "," + ciudad.codigoPais + "&appid=" + API_KEY + "&" + UNIDADES + "&" + IDIOMA;
        }else if(ciudad.getCodigoPostal() != null){
            peticionUrl = URL + "zip=" + ciudad.getCodigoPostal() + "," + ciudad.codigoPais + "&appid=" + API_KEY + "&" + UNIDADES + "&" + IDIOMA;
        }else if(ciudad.getUbicacionGps() != null){
            peticionUrl = URL + "lat=" + ciudad.getUbicacionGps().getLatitude() + "&lon=" + ciudad.getUbicacionGps().getLongitude() +
                    "&appid=" + API_KEY + "&" + UNIDADES + "&" + IDIOMA;
        }
        Log.i("Arvizo","Peticion URL: " + peticionUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clientAPI cliente = retrofit.create(clientAPI.class);
        Call<JsonObject> call = cliente.obtenerClimaApi(peticionUrl);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JSONObject jsonResponse;
                    try {
                        assert response.body() != null;
                        jsonResponse = new JSONObject(response.body().toString());
                        Log.i("Arvizo","json: " + jsonResponse.toString());
                        parsearJson(jsonResponse);
                    }catch (JSONException e){
                        mostrarToast(context,"Error al convertir json: " + e);
                    }
                }else{
                    mostrarToast(context,"Error al obtener el clima: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mostrarToast(context,"Error al conectar con API: " + t.toString());
            }
        });
    }

    private void parsearJson(JSONObject jsonObject){
        try {
            ciudadConsultada.setLocalidad(jsonObject.getString("name"));
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            ciudadConsultada.setCondiciones(weatherArray.getJSONObject(0).getString("description"));
            JSONObject jsonMain = jsonObject.getJSONObject("main");
            ciudadConsultada.setTemperatura(jsonMain.getDouble("temp"));
            ciudadConsultada.setMaxima(jsonMain.getDouble("temp_max"));
            ciudadConsultada.setMinima(jsonMain.getDouble("temp_min"));
            ciudadConsultada.setHumedad(jsonMain.getInt("humidity"));
            navController.navigate(R.id.climaFragment);
        } catch (JSONException e) {
            e.printStackTrace();
            mostrarToast(context,"Error al parsear json: " + e);
            Log.e("Arvizo","Error al parsear json: " + e);
        }
    }

    @Override
    public void onCiudadFragmentBotonOk() {
        if (revisarInternet()) {
            descargarClima(ciudadConsultada);
        }else{
            mostrarToast(MainActivity.this,"Sin conexion a internet!");
        }
    }

    @Override
    public void onPostalFragmentBotonOk() {
        if (revisarInternet()) {
            descargarClima(ciudadConsultada);
        }else{
            mostrarToast(MainActivity.this,"Sin conexion a internet!");
        }
    }
}