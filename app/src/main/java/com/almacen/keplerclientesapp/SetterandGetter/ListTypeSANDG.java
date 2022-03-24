package com.almacen.keplerclientesapp.SetterandGetter;

public class ListTypeSANDG {
    String codeType;
    String Type;

    public ListTypeSANDG(String codeType, String type) {
        this.codeType = codeType;
        Type = type;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
