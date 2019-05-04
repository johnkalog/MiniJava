package semantic;

public class NoClassAccepted extends Exception{
  private String Class, MethodName, ClassName;

  public NoClassAccepted(String Class,String MethodName,String ClassName){
    this.Class = Class;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier is ClassName: "+this.Class+" at AssignmentStatement in method: "+this.MethodName+" in class: "+
           this.ClassName;
  }
}
