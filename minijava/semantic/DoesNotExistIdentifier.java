package semantic;

public class DoesNotExistIdentifier extends Exception{
  private String Identifier;

  public DoesNotExistIdentifier(String Identifier){
    this.Identifier = Identifier;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" hasn't been declared";
  }
}
