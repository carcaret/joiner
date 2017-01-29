package com.carcaret;

public class Entity1 {

    @Id
    private final Long id;
    private final String field1;

    public Entity1(Long id, String field1) {
        this.id = id;
        this.field1 = field1;
    }

    public Long getId() {
        return id;
    }

    public String getField1() {
        return field1;
    }
}
