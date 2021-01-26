package com.pidelectronics.storm;

import android.location.Location;

public class ciudad {

    String localidad;
    int id;
    String condiciones;
    double temperatura;
    double maxima;
    double minima;
    int humedad;
    String codigoPostal;
    String codigoPais;
    Location ubicacionGps;

    public ciudad() {
        this.localidad = null;
        this.id = 0;
        this.condiciones = null;
        this.temperatura = 0;
        this.codigoPostal = null;
        this.codigoPais = null;
        this.maxima = 0;
        this.minima = 0;
        this.humedad = 0;
        this.ubicacionGps = null;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public double getMinima() {
        return minima;
    }

    public void setMinima(double minima) {
        this.minima = minima;
    }

    public double getMaxima() {
        return maxima;
    }

    public void setMaxima(double maxima) {
        this.maxima = maxima;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public Location getUbicacionGps() {
        return ubicacionGps;
    }

    public void setUbicacionGps(Location ubicacionGps) {
        this.ubicacionGps = ubicacionGps;
    }
}
