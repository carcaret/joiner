package com.carcaret;

public class Entity2 {

    @Id
    private final Long id;
    private final String field2;

    public Entity2(Long id, String field2) {
        this.id = id;
        this.field2 = field2;
    }

    public Long getId() {
        return id;
    }

    public String getField2() {
        return field2;
    }
}
