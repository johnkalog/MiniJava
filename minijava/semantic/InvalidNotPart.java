package semantic;

public class InvalidNotPart extends Exception{

  public InvalidNotPart(){
  }

  public String getMessage() {
    return "Invalid part for logical ! operator,must be boolean";
  }
}
