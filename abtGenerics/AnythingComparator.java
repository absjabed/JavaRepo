public class AnythingComparator implements ComparatorTst<String> {

  @Override
  public int compareTo(String obj1, String obj2){
    return obj1.length() - obj2.length();
  }
}