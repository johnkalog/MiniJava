package semantic;

public class InvalidAllocationIndex extends Exception{
  private String MethodName, ClassName;

  public InvalidAllocationIndex(String MethodName,String ClassName){
    this.MethodName = MethodName;
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Invalid expression inside ArrayAllocationExpression in method: "+this.MethodName+" in class: "
           +this.ClassName+". It should be integer";
  }
}
