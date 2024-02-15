package com.Queries.Queries;

import com.Action.ReadDocPropertyAction;

public class DocReadPropertyQuery extends Query{
    public DocReadPropertyQuery(String databaseName , String id , String propertyName){
        addActionToQueue(new ReadDocPropertyAction(databaseName  , id,propertyName));

    }
}
