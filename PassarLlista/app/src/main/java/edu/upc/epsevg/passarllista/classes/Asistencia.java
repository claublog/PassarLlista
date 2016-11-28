package edu.upc.epsevg.passarllista.classes;

public class Asistencia {
    private int id;
    private boolean ha_asistit;
    private boolean retard;
    private String observacions;

    public Asistencia(int id, boolean ha_asistit, boolean retard, String observacions) {
        this.id = id;
        this.ha_asistit = ha_asistit;
        this.retard = retard;
        this.observacions = observacions;
    }

    public int getId() {
        return id;
    }

    public boolean isHa_asistit() {
        return ha_asistit;
    }

    public boolean isRetard() {
        return retard;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHa_asistit(boolean ha_asistit) {
        this.ha_asistit = ha_asistit;
    }

    public void setRetard(boolean retard) {
        this.retard = retard;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }
}
