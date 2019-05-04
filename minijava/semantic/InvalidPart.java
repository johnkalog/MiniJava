package semantic;

public class InvalidPart extends Exception{
  private String IntOrBoolean, Operator, Part, Identifier, MethodName, ClassName, Type;

  public InvalidPart(String IntOrBoolean,String Operator,String Part,String Identifier,String MethodName,String ClassName,String Type){
    this.IntOrBoolean = IntOrBoolean;
    this.Operator = Operator;
    this.Part = Part;
    this.Identifier = Identifier;
    this.MethodName = MethodName;
    this.ClassName = ClassName;
    this.Type = Type;
  }

  public String getMessage() {
    if ( this.MethodName==null ){ //this ocassion may be not necessary
      return "Invalid "+this.Part+" part for "+this.Operator+" operator. Identifier: "
              +this.Identifier+" is "+this.Type+" instead of "+this.IntOrBoolean+" in class: "+this.ClassName;
    }
    else{
      return "Invalid "+this.Part+" part for "+this.Operator+" operator. Identifier: "
            +this.Identifier+" is "+this.Type+" instead of "+this.IntOrBoolean+" in method: "+this.MethodName+" in class: "+this.ClassName;
    }
  }
}
