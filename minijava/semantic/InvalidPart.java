package semantic;

public class InvalidPart extends Exception{
  private String Operator, Part, Identifier, MethodName, ClassName, Type;

  public InvalidPart(String Operator,String Part,String Identifier,String MethodName,String ClassName,String Type){
    this.Operator = Operator;
    this.Part = Part;
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
    this.Type = Type;
  }

  public String getMessage() {
    if ( this.MethodName==null ){
      return "Invalid "+this.Part+" part for "+this.Operator+" operator. Identifier: "
              +this.Identifier+" is "+this.Type+" instead of integer in class: "+this.ClassName;
    }
    else{
      return "Invalid "+this.Part+" part for "+this.Operator+" operator. Identifier: "
            +this.Identifier+" is "+this.Type+" instead of integer in method: "+this.MethodName+" in class: "+this.ClassName;
    }
  }
}
