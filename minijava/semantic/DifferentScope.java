package semantic;

public class DifferentScope extends Exception{
  private String Identifier, MethodName, ClassName;

  public DifferentScope(String Identifier,String MethodName,String ClassName){
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier: "+this.Identifier+" not in same scope for method: "+this.MethodName+" in class: "+
            this.ClassName+". Means not in method's VarDeclaration or method's parameter list or classe's VarDeclaration or parent classe's VarDeclaration";
  }
}
