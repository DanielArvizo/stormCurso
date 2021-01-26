package com.pidelectronics.storm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static com.pidelectronics.storm.gloables.ciudadConsultada;


public class ClimaFragment extends Fragment {

    TextView txtLocalidad, txtCondiciones, txtTemperatura, txtMaxima, txtMinima, txtHumedad;

    public ClimaFragment() {
        // Required empty public constructor
    }


    public static ClimaFragment newInstance(String param1, String param2) {
        return new ClimaFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_clima, container, false);

        txtLocalidad = vista.findViewById(R.id.txtNombreCiudad);
        txtCondiciones = vista.findViewById(R.id.txtCondiciones);
        txtTemperatura = vista.findViewById(R.id.txtTemperaturaActual);
        txtMaxima = vista.findViewById(R.id.txtTemperaturaMaxima);
        txtMinima = vista.findViewById(R.id.txtTemperaturaMinima);
        txtHumedad = vista.findViewById(R.id.txtHumedad);

        String localidad = "Clima en " + ciudadConsultada.getLocalidad();
        String temperatura = ciudadConsultada.getTemperatura() + " C";
        String maxima = ciudadConsultada.getMaxima() + " C";
        String minima = ciudadConsultada.getMinima() + " C";
        String humedad = ciudadConsultada.getHumedad() + " C";

        txtLocalidad.setText(localidad);
        txtCondiciones.setText(ciudadConsultada.getCondiciones());
        txtTemperatura.setText(temperatura);
        txtMaxima.setText(maxima);
        txtMinima.setText(minima);
        txtHumedad.setText(humedad);


        return vista;
    }
}