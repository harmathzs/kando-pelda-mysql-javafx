package org.example.kandopeldamysqljavafx;

public class Dog {
    private Integer id;
    private String name;
    private float age;
    private boolean male;
    private Owner owner;

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

    public void setAge(float age) {
        this.age = age;
    }

    public float getAge() {
        return age;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isMale() {
        return male;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }



    public Dog(
            Integer id,
            String name,
            float age,
            boolean male,
            Owner owner
    ) {
        setId(id);
        setName(name);
        setAge(age);
        setMale(male);
        setOwner(owner);
    }
}

