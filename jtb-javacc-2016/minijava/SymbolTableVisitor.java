import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;

public class SymbolTableVisitor extends GJDepthFirst<Map<String, String>, Map<String, String>>{

  private Map<String, String> ClassExtend;  //ClassName,ClassParent
  private Map<ArrayList <String>, String> ClassFields;  //[Identifier ClassName] Type
  private Map<ArrayList <String>, String> FunctionFields; //[Identifier MethodName ClassName] Type
  private Map<ArrayList <String>, ArrayList<String>> FunctionTypes; //[MethodName ClassName] [ReturnType ArgumentType1 ... ArgumentType2]

  public SymbolTableVisitor(){
    ClassExtend = new HashMap<String, String>();
    ClassFields = new HashMap<ArrayList <String>, String>();
    FunctionFields = new HashMap<ArrayList <String>, String>();
    FunctionTypes = new HashMap<ArrayList <String>, ArrayList<String>>();

  }

  public void printSymbolTable(){
    System.out.println("ClassExtend\n"+ClassExtend+"\n-----------------------");
    System.out.println("ClassFields\n"+ClassFields+"\n-----------------------");
    System.out.println("FunctionFields\n"+FunctionFields+"\n-----------------------");
    System.out.println("FunctionTypes\n"+FunctionTypes);
  }

  /**
   * f0 -> MainClass()
   * f1 -> ( TypeDeclaration() )*
   * f2 -> <EOF>
   */
  public Map<String, String> visit(Goal n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "{"
   * f3 -> "public"
   * f4 -> "static"
   * f5 -> "void"
   * f6 -> "main"
   * f7 -> "("
   * f8 -> "String"
   * f9 -> "["
   * f10 -> "]"
   * f11 -> Identifier()
   * f12 -> ")"
   * f13 -> "{"
   * f14 -> ( VarDeclaration() )*
   * f15 -> ( Statement() )*
   * f16 -> "}"
   * f17 -> "}"
   */
  public Map<String, String> visit(MainClass n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     n.f7.accept(this, argu);
     n.f8.accept(this, argu);
     n.f9.accept(this, argu);
     n.f10.accept(this, argu);
     n.f11.accept(this, argu);
     n.f12.accept(this, argu);
     n.f13.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>(); //Identifier key with value it's type
     if ( n.f14.present() ){  //if there is VarDeclaration
       n.f14.accept(this, IdentifierType);  //passed as argument to f14 to put values
       IdentifierType.forEach((key, value) -> { //lamda function to add Map contents
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
     }
     else{
       //System.out.println("No VarDeclaration for MainClass");
     }
     n.f15.accept(this, argu);
     n.f16.accept(this, argu);
     n.f17.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "{"
   * f3 -> ( VarDeclaration() )*
   * f4 -> ( MethodDeclaration() )*
   * f5 -> "}"
   */
  public Map<String, String> visit(ClassDeclaration n, Map<String, String> argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,null);  //no ClassParent
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f3.present() ){
       n.f3.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
     }
     else{
       //System.out.println("No VarDeclaration for class "+ClassName);
     }
     Map <String, String> ClassForMethod = new HashMap<String, String>();
     if ( n.f4.present() ){
       ClassForMethod.put(ClassName,null);  //argiment to f4 to add values
       n.f4.accept(this, ClassForMethod);
     }
     else{
       //System.out.println("No MethodDeclaration for class "+ClassName);
     }
     n.f5.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> "class"
   * f1 -> Identifier()
   * f2 -> "extends"
   * f3 -> Identifier()
   * f4 -> "{"
   * f5 -> ( VarDeclaration() )*
   * f6 -> ( MethodDeclaration() )*
   * f7 -> "}"
   */
  public Map<String, String> visit(ClassExtendsDeclaration n, Map<String, String> argu) {
    String ClassName = n.f1.accept(this, argu).keySet().toArray()[0].toString();
    String ClassParent = n.f3.accept(this, argu).keySet().toArray()[0].toString();
    ClassExtend.put(ClassName,ClassParent);
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     n.f3.accept(this, argu);
     n.f4.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f5.present() ){
       n.f5.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierClass = new ArrayList<String>();
         IdentifierClass.add(key);
         IdentifierClass.add(ClassName);
         ClassFields.put(IdentifierClass,value);
       });
     }
     else{
       //System.out.println("No VarDeclaration for class "+ClassName);
     }
     Map <String, String> ClassForMethod = new HashMap<String, String>();
     if ( n.f6.present() ){
       ClassForMethod.put(ClassName,null);
       n.f6.accept(this, ClassForMethod);
     }
     else{
       //System.out.println("noo1");
     }
     n.f7.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   * f2 -> ";"
   */
  public Map<String, String> visit(VarDeclaration n, Map<String, String> argu) {
    String Type = n.f0.accept(this, argu).keySet().toArray()[0].toString();
    String Identifier =n.f1.accept(this, argu).keySet().toArray()[0].toString();
    argu.put(Identifier,Type);
    Map<String, String> _ret=null;
    n.f2.accept(this, argu);
    return _ret;
  }

  /**
   * f0 -> "public"
   * f1 -> Type()
   * f2 -> Identifier()
   * f3 -> "("
   * f4 -> ( FormalParameterList() )?
   * f5 -> ")"
   * f6 -> "{"
   * f7 -> ( VarDeclaration() )*
   * f8 -> ( Statement() )*
   * f9 -> "return"
   * f10 -> Expression()
   * f11 -> ";"
   * f12 -> "}"
   */
  public Map<String, String> visit(MethodDeclaration n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     String ReturnType = n.f1.accept(this, argu).keySet().toArray()[0].toString();
     String MethodName = n.f2.accept(this, argu).keySet().toArray()[0].toString();
     String ClassName = argu.keySet().toArray()[0].toString();
     ArrayList<String> FunctionInfo = new ArrayList<String>();
     FunctionInfo.add(MethodName);
     FunctionInfo.add(ClassName);
     n.f3.accept(this, argu);
     ArrayList<String> ReturnArguments;
     if ( n.f4.present() ){
       Map<String, String> Arguments = n.f4.accept(this, argu);
       ReturnArguments = new ArrayList<String>(Arguments.values()); //convert HashMap values to ArrayList

     }
     else{
       ReturnArguments = new ArrayList<String>();
       //System.out.println("No arguments for function "+MethodName+" in class "+ClassName);
     }
     ReturnArguments.add(0,ReturnType); //first is ReturnType
     FunctionTypes.put(FunctionInfo,ReturnArguments);
     n.f5.accept(this, argu);
     n.f6.accept(this, argu);
     Map <String, String> IdentifierType = new HashMap<String, String>();
     if ( n.f7.present() ){
       n.f7.accept(this, IdentifierType);
       IdentifierType.forEach((key, value) -> {
         ArrayList<String> IdentifierFunctionClass = new ArrayList<String>();
         IdentifierFunctionClass.add(key);
         IdentifierFunctionClass.add(MethodName);
         IdentifierFunctionClass.add(ClassName);
         FunctionFields.put(IdentifierFunctionClass,value);
       });
     }
     else{
       //System.out.println("No VarDeclaration for class "+ClassName);
     }
     n.f8.accept(this, argu);
     n.f9.accept(this, argu);
     n.f10.accept(this, argu);
     n.f11.accept(this, argu);
     n.f12.accept(this, argu);
     return _ret;
  }

  /**
   * f0 -> FormalParameter()
   * f1 -> FormalParameterTail()
   */
  public Map<String, String> visit(FormalParameterList n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     Map <String, String> Arguments = new LinkedHashMap<String, String>();  //to keep insertions in order because argument types
     n.f0.accept(this, Arguments);
     n.f1.accept(this, Arguments);
     return Arguments;
  }

  /**
   * f0 -> Type()
   * f1 -> Identifier()
   */
  public Map<String, String> visit(FormalParameter n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     String Type = n.f0.accept(this, argu).keySet().toArray()[0].toString();
     String Identifier = n.f1.accept(this, argu).keySet().toArray()[0].toString();
     argu.put(Identifier,Type);
     return _ret;
  }

  /**
   * f0 -> "int"
   * f1 -> "["
   * f2 -> "]"
   */
  public Map<String, String> visit(ArrayType n, Map<String, String> argu) {
     Map<String, String> _ret=null;
     n.f0.accept(this, argu);
     n.f1.accept(this, argu);
     n.f2.accept(this, argu);
     Map<String, String> ArrayType = new HashMap<String, String>(); //return String added to a HashMap as key
     ArrayType.put("ArrayType",null);                               //because that is the ReturnType
     return ArrayType;                                              //extracted from it at parent class
  }

  /**
   * f0 -> "boolean"
   */
  public Map<String, String> visit(BooleanType n, Map<String, String> argu) {
     n.f0.accept(this, argu);
     Map<String, String> BooleanType = new HashMap<String, String>();
     BooleanType.put("BooleanType",null);
     return BooleanType;
  }

  /**
   * f0 -> "int"
   */
  public Map<String, String> visit(IntegerType n, Map<String, String> argu) {
     n.f0.accept(this, argu);
     Map<String, String> IntegerType = new HashMap<String, String>();
     IntegerType.put("IntegerType",null);
     return IntegerType;
  }

  /**
   * f0 -> <IDENTIFIER>
   */
  public Map<String, String> visit(Identifier n, Map<String, String> argu) {
     //return n.f0.accept(this, argu);
     Map<String, String> Identifier = new HashMap<String, String>();
     Identifier.put(n.f0.toString(),null);
     return Identifier;
  }

}
