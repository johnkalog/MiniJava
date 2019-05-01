package semantic;

public class UnknownNewClass extends Exception{
  private String Identifier;

  public UnknownNewClass(String Identifier){
    this.Identifier = Identifier;
  }

  public String getMessage() {
    return "Unknown class type: "+this.Identifier+" for expression: new "+this.Identifier+"();";
  }
}
