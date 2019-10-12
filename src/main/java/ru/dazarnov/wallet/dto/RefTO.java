package ru.dazarnov.wallet.dto;

import java.util.Objects;

public class RefTO {

    private final Long id;
    private final String name;

    public RefTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefTO refTO = (RefTO) o;
        return Objects.equals(id, refTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RefTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
