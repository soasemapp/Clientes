package com.almacen.keplerclientesapp.SetterandGetter;

public class BANKSANDG {
    String code;
    String NBanco;

    public BANKSANDG(String code, String NBanco) {
        this.code = code;
        this.NBanco = NBanco;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNBanco() {
        return NBanco;
    }

    public void setNBanco(String NBanco) {
        this.NBanco = NBanco;
    }
}
