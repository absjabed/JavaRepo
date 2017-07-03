import java.util.ArrayList;
import java.util.List;
public class GenericMethod{
  
  public static <T> boolean findElementInList(T ob, List<T> elements){
  
    for(T element : elements){
      
      if(element.equals(ob)){
        return true;
      }
    }
    return false;
  }
}