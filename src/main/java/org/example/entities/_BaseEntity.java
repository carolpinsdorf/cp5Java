package org.example.entities;

public abstract class _BaseEntity {
    // propriedades herdadas em todas as outras entidade
    private Integer id;

    // construtor vazio
    public _BaseEntity() {

    }
    //construtor completo
    public _BaseEntity(int id) {
        this.id = id;
    }

    // getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // toString
    @Override
    public String toString() {
        return "_BaseEntity{" +
                "id=" + id +
                '}';
    }
}
