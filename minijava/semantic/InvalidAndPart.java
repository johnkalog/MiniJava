package semantic;

public class InvalidAndPart extends Exception{
  private String Part;

  public InvalidAndPart(String Part){
    this.Part = Part;
  }

  public String getMessage() {
    return "Invalid "+this.Part+" part for logical && operator, must be boolean";
  }
}
