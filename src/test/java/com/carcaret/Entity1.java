package com.carcaret;

public class Entity1 {

    @Id
    private final Long id;
    private final String field;

    public Entity1(Long id, String field) {
        this.id = id;
        this.field = field;
    }

    public Long getId() {
        return id;
    }

    public String getField() {
        return field;
    }
}
