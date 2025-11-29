package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

  static class TestPerson extends Person {
    public TestPerson(String name, int age) {
      super(name, age);
    }
  }

  @Test
  void constructor_initializesFields() {
    Person p = new TestPerson("John", 30);

    assertEquals("John", p.getName());
    assertEquals(30, p.getAge());
  }

  @Test
  void setName_updatesName() {
    Person p = new TestPerson("John", 30);

    p.setName("Bob");

    assertEquals("Bob", p.getName());
  }

  @Test
  void setAge_updatesAge() {
    Person p = new TestPerson("John", 30);

    p.setAge(40);

    assertEquals(40, p.getAge());
  }
}
