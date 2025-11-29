package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramTest {

  private Program program;

  @BeforeEach
  void setup() {
    program = new Program("BSc Hobby horsing", 5);
  }

  @Test
  void constructor_initializesFields() {
    assertEquals("BSc Hobby horsing", program.getProgramName());
    assertEquals(5, program.getTotalModules());
    assertTrue(program.getModulesTaught().isEmpty());
  }

  @Test
  void settersUpdateFieldsCorrectly() {
    program.setProgramName("BSc Curling");
    program.setTotalModules(10);

    assertEquals("BSc Curling", program.getProgramName());
    assertEquals(10, program.getTotalModules());
  }

  @Test
  void getModulesTaught_returnsDefensiveCopy() {
    Module m = mock(Module.class);
    program.addModuleTaught(m);

    List<Module> list1 = program.getModulesTaught();
    List<Module> list2 = program.getModulesTaught();

    assertNotSame(list1, list2);
    assertEquals(1, list1.size());
  }

  @Test
  void addModuleTaught_addsModule() {
    Module m1 = mock(Module.class);
    Module m2 = mock(Module.class);

    program.addModuleTaught(m1);
    program.addModuleTaught(m2);

    List<Module> result = program.getModulesTaught();

    assertEquals(2, result.size());
    assertTrue(result.contains(m1));
    assertTrue(result.contains(m2));
  }

  @Test
  void setModulesTaught_replacesList() {
    Module m1 = mock(Module.class);
    Module m2 = mock(Module.class);

    program.setModulesTaught(List.of(m1));
    assertEquals(1, program.getModulesTaught().size());

    program.setModulesTaught(List.of(m2));
    assertEquals(1, program.getModulesTaught().size());
    assertTrue(program.getModulesTaught().contains(m2));
    assertFalse(program.getModulesTaught().contains(m1));
  }

  @Test
  void toCSVRow_outputsCorrectData() {
    Module m1 = mock(Module.class);
    Module m2 = mock(Module.class);

    when(m1.getModuleCode()).thenReturn("CS101");
    when(m2.getModuleCode()).thenReturn("CS102");

    program.addModuleTaught(m1);
    program.addModuleTaught(m2);

    String[] csv = program.toCSVRow();

    assertArrayEquals(
        new String[]{
            "BSc Hobby horsing",
            "5",
            "SP123, SP456"
        },
        csv
    );
  }

  @Test
  void toCSVRow_emptyModulesProducesEmptyField() {
    String[] csv = program.toCSVRow();
    assertEquals("", csv[2]);
  }

  @Test
  void getCSVHeader_isCorrect() {
    assertArrayEquals(
        new String[]{"programName", "totalModules", "moduleCodes"},
        program.getCSVHeader()
    );
  }
}
