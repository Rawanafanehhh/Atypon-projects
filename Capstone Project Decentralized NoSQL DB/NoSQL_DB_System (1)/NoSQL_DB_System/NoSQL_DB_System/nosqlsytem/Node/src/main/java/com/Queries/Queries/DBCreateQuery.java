package com.Queries.Queries;
import com.Action.CreateCollectionAction;
import com.Action.CreateConfigFileAction;
import com.Entities.Configuration;
import com.Entities.Schema;

public class DBCreateQuery extends Query{
    private String collectionName;
    private Schema schema;
    public DBCreateQuery(String collectionName , Schema schema ){
        this.schema=schema;
        this.collectionName=collectionName;
        addActionToQueue(new CreateCollectionAction(collectionName));
        addActionToQueue(new CreateConfigFileAction(collectionName , new Configuration(schema)));

    }

}