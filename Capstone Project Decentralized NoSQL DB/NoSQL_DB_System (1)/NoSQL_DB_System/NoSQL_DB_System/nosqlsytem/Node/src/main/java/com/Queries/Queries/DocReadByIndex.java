package com.Queries.Queries;

import com.Action.ReadByIndexAction;

public class DocReadByIndex extends Query{
    public DocReadByIndex(String databaseName, String indexingName, String value){

        addActionToQueue(new ReadByIndexAction( databaseName,  indexingName, value) );
    }
}
