package com.pidelectronics.storm;

import android.content.Context;
import android.widget.Toast;

public class gloables {

    public static ciudad ciudadConsultada = null;

    //metodos
    public static void mostrarToast(Context context, String mensaje){
        Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show();
    }
}
