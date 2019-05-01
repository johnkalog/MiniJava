package semantic;

public class InvalidComparePart extends Exception{
  private String Part;

  public InvalidComparePart(String Part){
    this.Part = Part;
  }

  public String getMessage() {
    return "Invalid "+this.Part+" part for compare < operator,must be integer";
  }
}
