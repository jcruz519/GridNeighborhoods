// Unit tests for Grid.java
public class GridTest {
   // I literally just thought it would be cool to type like this
   private static void threadedType(String message) {
      char[] messageStream = message.toCharArray();

      try {
         for(char character : messageStream) {
            Thread.sleep(10);
            System.out.print(character);
         }

         Thread.sleep(10);
         System.out.print('\n');
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }   
   }   

   //--------------------------Assertion Helpers--------------------------------
   private static void assertTrue(boolean condition, String message) {
      if (condition) {
         threadedType("✅ PASS: " + message);
      } else {
         threadedType("❌ FAIL: " + message);
      }
   }

   private static <Type> void assertEquals(Type expected, Type actual, 
      String message) {
      if (expected.equals(actual)) {
         threadedType("✅ PASS: " + message + " (Expected: " + expected + 
            ", Actual: " + actual + ")");
      } else {
         threadedType("❌ FAIL: " + message + " (Expected: " + expected + 
            ", Actual: " + actual + ")");
      }
   }

   public static void assertThrows(Runnable codeBlock, 
      Class<? extends Exception> expectedType, String message) {
      boolean thrown = false;
      
      try {
         codeBlock.run();
      } catch (Exception e) {
         if (expectedType.isInstance(e)) {
            thrown = true;
         }
      }

      if (thrown) {
         System.out.println("✅ PASS: " + message + " (Expected exception " + 
            expectedType.getSimpleName() + " thrown)");
      } else {
         System.err.println("❌ FAIL: " + message + " (Did not throw " + 
            "expected exception " + expectedType.getSimpleName() + ")");
      }
   }
   //--------------------------Assertion Helpers--------------------------------

   public void testConstructor_shouldCreateCharMatrixOfInputDimensions(
      int rows, int columns) {
      Grid grid = new Grid(rows, columns);

      boolean condition = grid.getCells().length == rows &&
         grid.getCells()[0].length == columns;

      assertTrue(condition, "Constructor initialization test");
   }

   public void testConstructorFailure_shouldThrowIllegalArgumentException() {
      assertThrows(
         () -> new Grid(0, 0),
         IllegalArgumentException.class,
         "Constructor failure test for input under 1"
      );
      
      assertThrows(
         () -> new Grid(9999999, 9999999),
         IllegalArgumentException.class,
         "Constructor failure test for input that will cause memory error"
      );
   }

   public void testSourceAdded_shouldAddSource(int rows, int columns, int x, 
      int y) {
      Grid grid = new Grid(rows, columns);

      boolean condition = grid.addSource(x, y);

      assertTrue(condition, "Add source test return value");
      assertEquals(1, grid.getSources().size(), "Add source test execution");
   }

   public void testSourceAddFailure_shouldReturnFalse() {
      Grid grid = new Grid(5, 5);
      
      grid.addSource(2, 2);

      boolean condition1 = grid.addSource(2, 2);

      assertTrue(!condition1, "Duplicate source shouldn't be added");
      assertEquals(1, grid.getSources().size(), "Duplicate source not added");
      
      boolean condition2 = grid.addSource(6, 6);

      assertTrue(!condition2, "Out of bounds source shouldn't be added");
      assertEquals(1, grid.getSources().size(), "Out of bounds source " + 
         "not added");
   }

   public void 
      testCalculateNeighbors_shouldReturnAccurateNonZeroPositiveResult
      (int rows, int columns, int[] x, int[] y, int size, int expected) {
      Grid grid = new Grid(rows, columns);
         
      for(int i = 0; i < x.length; i++) {
         grid.addSource(x[i], y[i]);
      }

      assertEquals(expected, grid.calculateNeighbors(size), "Calculate " + 
         "neighbors test");
   }

   public void testCalculateNeighborsFailure_shouldReturnNegativeOne() {
      Grid grid = new Grid(5, 5);

      assertEquals(-1, grid.calculateNeighbors(-1), "Calculate neighbors " + 
         "should return -1 on input under 0" );
      assertEquals(-1, grid.calculateNeighbors(Integer.MAX_VALUE + 1), 
         "Calculate neighbors should return -1 on input above int max" );
   }
}
