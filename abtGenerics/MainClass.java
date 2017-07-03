import java.util.ArrayList;
import java.util.List;
public class MainClass {

  public static void main(String[] args){
    //ComparatorTst<String> comparator = new AnythingComparator();
   
    //System.out.println(comparator.compareTo("Jbdctg","jbd2"));
  
    List<String> names = new ArrayList<String>();
    
    names.add("abc");
    names.add("pqr");
    names.add("str");
    
   
    //System.out.println(GenericMethod.findElementInList("yyy",names));
    
    //Generic WildCard ?
    //WildCards.printCollection(names);
    
    // bounded wild card -> Upper Bounded Wild Card --- super <- lower
  /*  Rectangle rec = new Rectangle();
    Circle cir = new Circle();
    
    draw(rec);
    draw(cir); */
  }
  
  // bounded wild card -> Upper Bounded Wild Card --- super <- lower 
  public static<T extends Shape> void draw(T shape){
    shape.draw();
  }
}