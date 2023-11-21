package com.br.authentication.security.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.br.authentication.security.entities.ResetPasswordCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ResetPasswordCodeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(ResetPasswordCode entity) {
        dynamoDBMapper.save(entity);
    }
}
