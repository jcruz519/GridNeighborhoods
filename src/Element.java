// Characters on grid enumerated
public enum Element {
   SOURCE('X'),
   NEIGHBOR('O'),
   VACANCY(' ');

   private final char ELEMENT;

   Element(char element) {
      this.ELEMENT = element;
   }

   public char get() {
      return this.ELEMENT;
   }
}
