package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LecturerTest {

  private Lecturer lecturer;

  @BeforeEach
  void setup() {
    lecturer = new Lecturer("John", 40, "12345", "Science & Engineering");
  }

  @Test
  void constructor_initializesFieldsCorrectly() {
    assertEquals("John", lecturer.getName());
    assertEquals(40, lecturer.getAge());
    assertEquals("12345", lecturer.getStaffID());
    assertEquals("Science & Engineering", lecturer.getDepartment());
  }

  @Test
  void getModulesTaught_returnsDefensiveCopy() {
    Module m = mock(Module.class);
    lecturer.addModuleTaught(m);

    var list1 = lecturer.getModulesTaught();
    var list2 = lecturer.getModulesTaught();

    assertEquals(1, list1.size());
    assertNotSame(list1, list2);
  }

  @Test
  void addModuleTaught_addsModule() {
    Module m = mock(Module.class);

    lecturer.addModuleTaught(m);

    assertTrue(lecturer.getModulesTaught().contains(m));
  }

  @Test
  void removeModuleTaught_removesModule() {
    Module m = mock(Module.class);

    lecturer.addModuleTaught(m);
    lecturer.removeModuleTaught(m);

    assertFalse(lecturer.getModulesTaught().contains(m));
  }

  @Test
  void toCSVRow_containsCorrectFields() {
    Module m1 = mock(Module.class);
    Module m2 = mock(Module.class);

    when(m1.getModuleCode()).thenReturn("MA123");
    when(m2.getModuleCode()).thenReturn("MA456");

    lecturer.addModuleTaught(m1);
    lecturer.addModuleTaught(m2);

    String[] csv = lecturer.toCSVRow();

    assertArrayEquals(
        new String[]{
            "12345",
            "John",
            "40",
            "Science & Engineering",
            "MA123, MA456"
        },
        csv
    );
  }

  @Test
  void toCSVRow_emptyModulesListProducesEmptyString() {
    String[] csv = lecturer.toCSVRow();

    assertEquals("", csv[4]);
  }

  @Test
  void getCSVHeader_returnsCorrectHeader() {
    assertArrayEquals(
        new String[]{"staffID", "name", "age", "department", "modulesTaught"},
        lecturer.getCSVHeader()
    );
  }
}
