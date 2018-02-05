package com.tika_test2;

public class EmbeddedToken {
    private String _name;
    private String _value;

    public String getName(){
        return this._name;
    }

    public String getValue(){
        return this._value;
    }

    public EmbeddedToken(String name, String value){
        this._name = name;
        this._value = value;
    }
}
