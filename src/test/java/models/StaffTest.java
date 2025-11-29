package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {

  static class TestStaff extends Staff {
    public TestStaff(String name, int age, String staffID, String department) {
      super(name, age, staffID, department);
    }
  }

  @Test
  void constructor_initializesAllFields() {
    Staff s = new TestStaff("John", 40, "SID1232", "Science & Engineering");

    assertEquals("John", s.getName());
    assertEquals(40, s.getAge());
    assertEquals("SID123", s.getStaffID());
    assertEquals("Science & Engineering", s.getDepartment());
  }

  @Test
  void setStaffID_updatesCorrectly() {
    Staff s = new TestStaff("Bob", 50, "SID456", "Business");

    s.setStaffID("S999");

    assertEquals("S999", s.getStaffID());
  }

  @Test
  void setDepartment_updatesCorrectly() {
    Staff s = new TestStaff("Bob", 50, "SID456", "Business");

    s.setDepartment("Maths");

    assertEquals("Maths", s.getDepartment());
  }

  @Test
  void inheritedSettersWork() {
    Staff s = new TestStaff("Bob", 50, "SID456", "Business");

    s.setName("Charlie");
    s.setAge(60);

    assertEquals("Charlie", s.getName());
    assertEquals(60, s.getAge());
  }
}
