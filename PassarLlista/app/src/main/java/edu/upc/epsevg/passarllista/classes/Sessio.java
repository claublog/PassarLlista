package edu.upc.epsevg.passarllista.classes;

import java.util.Date;

public class Sessio {

    private int id;
    private Date data;

    public Sessio(int id, Date data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
