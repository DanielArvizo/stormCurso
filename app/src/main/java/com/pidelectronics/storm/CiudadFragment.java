package com.pidelectronics.storm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.pidelectronics.storm.gloables.ciudadConsultada;
import static com.pidelectronics.storm.gloables.mostrarToast;


public class CiudadFragment extends Fragment {

    EditText edtCiudad, edtPais;
    Button btnOk;

    ciudadFragmentCallbacks callbacks;

    public CiudadFragment() {
        // Required empty public constructor
    }


    public static CiudadFragment newInstance() {
        return new CiudadFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ciudad, container, false);
        edtCiudad = vista.findViewById(R.id.edtCiudad);
        edtPais = vista.findViewById(R.id.edtCodigoPaisCiudad);
        btnOk = vista.findViewById(R.id.btnOkCiudad);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localidad = edtCiudad.getText().toString();
                String codigoPais = edtPais.getText().toString();

                if (localidad.isEmpty()){
                    mostrarToast(requireContext(),"Ingrese el nombre de la ciudad para continuar");
                }else if (codigoPais.isEmpty()){
                    mostrarToast(requireContext(),"Ingrese el codigo de pais para continuar");
                }else{
                    ciudadConsultada = new ciudad();
                    ciudadConsultada.setLocalidad(localidad);
                    ciudadConsultada.setCodigoPais(codigoPais);
                    callbacks.onCiudadFragmentBotonOk();
                }
            }
        });
        return vista;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (ciudadFragmentCallbacks) context;
    }

    public interface ciudadFragmentCallbacks{
        void onCiudadFragmentBotonOk();
    }
}