package com.Entities;

public class Schema {
    private String[] schemaAttributes;

    public Schema(String ... list){
        schemaAttributes=new String[list.length];
        for (int i = 0 ; i < list.length ; i++ )
            schemaAttributes[i]=list[i];
    }

    public String[] getAttributes(){
        return schemaAttributes;
    }

}