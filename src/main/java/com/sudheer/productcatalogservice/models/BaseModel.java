package com.sudheer.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseModel {
    private String id;
    private Date createdOn;
    private Date updatedOn;
    private Status status;
}
