package semantic;

public class OnlyBoolean extends Exception{
  private String Statement, Type, MethodName, ClassName;

  public OnlyBoolean(String Statement,String Type,String MethodName,String ClassName){
    this.Statement = Statement;
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Expression is: "+this.Type+" instead of BooleanType at "+this.Statement+" in method: "+this.MethodName+
           "in class: "+this.ClassName;
  }
}
