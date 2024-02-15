package com.Queries.QueriesManegers.Validators.syntax;

import com.Queries.Queries.DocReadByIndex;
import com.Queries.Queries.QueriesTypes;

public class ValidatorsFactory {
    public QueryValidator getInstance(QueriesTypes  type, String query) throws Exception {
        switch (type){
            case CREATE_DATABASE: return new DBCreateQueryValidator(query);
            case DELETE_DATABASE: return new DBDeleteQueryValidator(query);
            case CREATE_DOCUMENT: return new DocCreateQueryValidator(query);
            case DELETE_DOCUMENT: return new DocDeleteQueryValidator(query);
            case READ_DOCUMENT:  return new DocReadQueryValidator(query);
            case READ_PROPERTY: return new DocReadPropertyQueryValidator(query);
            case READ_BY_INDEX:return new DocReadByIndexValidator(query);
            case UPDATE_DOCUMENT: return new DocUpdateQueryValidator(query);
            case CREATE_INDEX:    return new IndexCreateQueryValidator(query);

        }
        throw new Exception("Invalid Query type");
    }
}