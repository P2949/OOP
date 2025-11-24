/* Person.java
 *
 * This class represents a person with basic attributes such as name and age.
 */
package models;

/**
 * The type Person.
 */
public abstract class Person {

    private String name;
    private int age;

    /**
     * Instantiates a new Person.
     *
     * @param name the name
     * @param age  the age
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

}
