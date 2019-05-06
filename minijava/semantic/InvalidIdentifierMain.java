package semantic;

public class InvalidIdentifierMain extends Exception{
  private String Identifier;

  public InvalidIdentifierMain(String Identifier){
    this.Identifier = Identifier;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" can't be declared because is main's argument";
  }
}
