import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class WildCards{

  public static void printCollection(List<?> objs){
    for(Object obj : objs){
      System.out.println(obj);
  }
  }
}