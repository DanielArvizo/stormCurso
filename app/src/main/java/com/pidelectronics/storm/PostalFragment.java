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


public class PostalFragment extends Fragment {

    EditText edtCodigoPostal, edtCodigoPais;
    Button btnOk;

    postalFragmentCallbacks callbacks;

    public PostalFragment() {
        // Required empty public constructor
    }


    public static PostalFragment newInstance(String param1, String param2) {
        return new PostalFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_postal, container, false);
        edtCodigoPostal = vista.findViewById(R.id.edtCodigoPostal);
        edtCodigoPais = vista.findViewById(R.id.edtCodigoPaisPostal);
        btnOk = vista.findViewById(R.id.btnOkPostal);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoPostal = edtCodigoPostal.getText().toString();
                String codigoPais = edtCodigoPais.getText().toString();
                if (codigoPostal.isEmpty()){
                    mostrarToast(requireContext(),"Ingrese el codigo postal para continuar");
                }else if(codigoPais.isEmpty()){
                    mostrarToast(requireContext(),"Ingrese el codigo de pais para continuar");
                }else{
                    ciudadConsultada = new ciudad();
                    ciudadConsultada.setCodigoPostal(codigoPostal);
                    ciudadConsultada.setCodigoPais(codigoPais);
                    callbacks.onPostalFragmentBotonOk();
                }
            }
        });
        return vista;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (postalFragmentCallbacks)context;
    }

    public interface postalFragmentCallbacks{
        void onPostalFragmentBotonOk();
    }
}