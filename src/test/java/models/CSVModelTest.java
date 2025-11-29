package models;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CSVModelTest {

  @Test
  void csvModelInterface_hasRequiredMethods() {
    Method[] methods = CSVModel.class.getDeclaredMethods();

    assertEquals(2, methods.length);

    assertTrue(containsMethod(methods, "toCSVRow"));
    assertTrue(containsMethod(methods, "getCSVHeader"));
  }

  private boolean containsMethod(Method[] methods, String name) {
    for (var m : methods) {
      if (m.getName().equals(name)) return true;
    }
    return false;
  }

  @Test
  void csvModel_canBeMockedAndMethodsCallable() {
    CSVModel model = Mockito.mock(CSVModel.class);

    String[] row = {"a", "b", "c"};
    String[] header = {"H1", "H2", "H3"};

    Mockito.when(model.toCSVRow()).thenReturn(row);
    Mockito.when(model.getCSVHeader()).thenReturn(header);

    assertArrayEquals(row, model.toCSVRow());
    assertArrayEquals(header, model.getCSVHeader());
  }
}
