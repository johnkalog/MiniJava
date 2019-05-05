package semantic;

public class InvalidPrint extends Exception{
  private String Type, MethodName, ClassName;

  public InvalidPrint(String Type,String MethodName,String ClassName){
    this.Type = Type;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return this.Type+" used in PrintStatement instead of IntegerType or BooleanType in method: "+this.MethodName+
           "in class: "+this.ClassName;
  }
}
