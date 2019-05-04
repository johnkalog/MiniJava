package semantic;

public class NoClassAccepted extends Exception{
  private String Statement, Class, MethodName, ClassName;

  public NoClassAccepted(String Statement,String Class,String MethodName,String ClassName){
    this.Statement = Statement;
    this.Class = Class;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Identifier is ClassName: "+this.Class+" at "+this.Statement+" in method: "+this.MethodName+" in class: "+
           this.ClassName;
  }
}
