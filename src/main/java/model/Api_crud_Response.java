package model;

import lombok.Data;

import java.util.List;

@Data
public class Api_crud_Response {
    private List<Api_crud_element> crudElementList;
}
