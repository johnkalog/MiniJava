package semantic;

public class DoubleDeclareParameter extends Exception{
  private String Identifier, MethodName, ClassName;

  public DoubleDeclareParameter(String Identifier,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" declared twice in parameter list in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
