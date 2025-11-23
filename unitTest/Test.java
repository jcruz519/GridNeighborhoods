// Unit test runner for GridTest.java
public class Test {
   public static void main(String[] args) {
      System.out.println("--- Starting Grid Manual Tests ---");
        
      GridTest gridTest = new GridTest();
      gridTest.testConstructor_shouldCreateCharMatrixOfInputDimensions(5, 5);
      gridTest.testConstructorFailure_shouldThrowIllegalArgumentException();
      gridTest.testSourceAdded_shouldAddSource(5, 5, 1, 1);
      gridTest.testSourceAddFailure_shouldReturnFalse();
      
      int x1[] = {2};
      int y1[] = {2};
      int x2[] = {7, 6};
      int y2[] = {1, 3};
      int empty[] = {};

      gridTest.
         testCalculateNeighbors_shouldReturnAccurateNonZeroPositiveResult
         (5, 5, x1, y1, 1, 5);
      gridTest.
         testCalculateNeighbors_shouldReturnAccurateNonZeroPositiveResult
         (9, 5, x2, y2, 2, 19);
      gridTest.
         testCalculateNeighbors_shouldReturnAccurateNonZeroPositiveResult
         (5, 5, empty, empty, 100, 0);
      gridTest.
         testCalculateNeighbors_shouldReturnAccurateNonZeroPositiveResult
         (3, 3, x1, y1, 1, 3);
      gridTest.testCalculateNeighborsFailure_shouldReturnNegativeOne();
      gridTest.
         testCalculateNeighborsWrap_shouldReturnAccurateNonZeroPositiveResult
         (5, 5, x1, y1, 1, 5);
      gridTest.
         testCalculateNeighborsWrap_shouldReturnAccurateNonZeroPositiveResult
         (9, 5, x2, y2, 2, 19);
      gridTest.
         testCalculateNeighborsWrap_shouldReturnAccurateNonZeroPositiveResult
         (5, 5, empty, empty, 100, 0);
      gridTest.
         testCalculateNeighborsWrap_shouldReturnAccurateNonZeroPositiveResult
         (3, 3, x1, y1, 1, 4);
      gridTest.testCalculateNeighborsWrapFailure_shouldReturnNegativeOne();

      System.out.println("--- Testing Complete ---");
   }
}
