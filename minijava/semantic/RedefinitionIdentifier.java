package semantic;

public class RedefinitionIdentifier extends Exception{
  private String Identifier, Type;

  public RedefinitionIdentifier(String Identifier,String Type){
    this.Identifier = Identifier;
    this.Type = Type;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" already defined as: "+this.Type;
  }
}
