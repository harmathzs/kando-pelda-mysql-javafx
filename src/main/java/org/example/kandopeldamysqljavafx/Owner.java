package org.example.kandopeldamysqljavafx;

public class Owner {
    private Integer id;
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }



    public Owner(Integer id, String name) {
        setId(id);
        setName(name);
    }
}

