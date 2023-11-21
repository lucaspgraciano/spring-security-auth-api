package com.br.authentication.security.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@DynamoDBTable(tableName = "resetPasswordCode")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResetPasswordCode {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String owner;

    @DynamoDBAttribute
    private String code;

    @DynamoDBAttribute
    private Integer attempts;

    @DynamoDBAttribute
    private Boolean verified;

    @DynamoDBAttribute
    private Date createdAt;

    @DynamoDBAttribute
    private Date updatedAt;

    public ResetPasswordCode setId(String id) {
        this.id = id;
        return this;
    }
}