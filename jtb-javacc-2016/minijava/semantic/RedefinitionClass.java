package semantic;

public class RedefinitionClass extends Exception{
  private String ClassName;

  public RedefinitionClass(String ClassName){
    this.ClassName = ClassName;
  }

  public String getMessage() {
    return "Redefinition of class "+this.ClassName;
  }
}
