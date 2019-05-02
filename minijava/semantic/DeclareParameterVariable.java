package semantic;

public class DeclareParameterVariable extends Exception{
  private String Identifier, MethodName, ClassName;

  public DeclareParameterVariable(String Identifier,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" defined in parameter list and in variable declaration in method: "+
            this.MethodName+" in class: "+this.ClassName;
  }
}
