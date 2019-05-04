package semantic;

public class UnexpectedClassName extends Exception{
  private String Class, Operator, MethodName, ClassName;

  public UnexpectedClassName(String Class,String Operator,String MethodName,String ClassName){
    this.Class = Class;
    this.Operator = Operator;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Invalid use of new "+this.Class+"() or "+this.Class+" for: "+this.Operator+" operator in method: "+
            this.MethodName+" in class: "+this.ClassName;
  }
}
