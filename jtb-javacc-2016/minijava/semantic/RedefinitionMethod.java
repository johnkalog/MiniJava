package semantic;

public class RedefinitionMethod extends Exception{
  private String Identifier, Type;

  public RedefinitionMethod(String Identifier,String Type){
    this.Identifier = Identifier;
    this.Type = Type;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" already defined as:"+this.Type+" in method declaration parameteres";
  }
}
