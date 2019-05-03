package semantic;

public class UnsupportedInheritance extends Exception{
  private String Type, Identifier, MethodName, ClassName;

  public UnsupportedInheritance(String Type,String Identifier,String MethodName,String ClassName){
    this.Type = Type;
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Assignment: "+this.Type+" ... = new "+this.Identifier+"() can't be supported because "+this.Type+
            " is not predecessor. Assignment found in method: "+this.MethodName+" in class: "+this.ClassName;
  }
}
