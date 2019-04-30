package semantic;

public class UnknownIdentifierClass extends Exception{
  private String Identifier, ClassName, Type;

  public UnknownIdentifierClass(String Identifier,String ClassName,String Type){
    this.Identifier = Identifier;
    this.ClassName = ClassName;
    this.Type = Type;
  }

  public String getMessage() {
    return "Unknown class type: "+this.Type+" for field: "+this.Identifier+" in class: "+this.ClassName;
  }
}
