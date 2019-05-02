import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;
import semantic.*;

public class TypeCheckingVisitor extends GJDepthFirst<String,ArrayList<String>>{

  public static Map<String, String> ClassExtend;
  public static Map<ArrayList <String>, String> ClassFields;
  public static Map<ArrayList <String>, String> FunctionFields;
  public static Map<ArrayList <String>, ArrayList<String>> FunctionTypes;

  public TypeCheckingVisitor(){   //same reference as SymbolTableVisitor class fields
    this.ClassExtend = SymbolTableVisitor.ClassExtend;
    this.ClassFields = SymbolTableVisitor.ClassFields;
    this.FunctionFields = SymbolTableVisitor.FunctionFields;
    this.FunctionTypes = SymbolTableVisitor.FunctionTypes;
  }

  // public ArrayList<String> getType(String Identifier){  //[Identifier MethodName ClassName Type] null is something misses
  //   ArrayList<String> Type = new ArrayList<String>();
  //   for ( ArrayList <String> key : FunctionFields.keySet() ){ //first check if declared in function scope may shadow classe's field
  //     if ( key.get(0)==Identifier ){
  //       Type.add(Identifier);
  //       Type.add(key.get(1));
  //       Type.add(key.get(2));
  //       Type.add(FunctionFields.get(key));  //Type
  //       return Type;
  //     }
  //   }
  //   for ( ArrayList <String> key : FunctionTypes.keySet() ){  //after check at parameter list
  //
  //   }
  //   for ( ArrayList <String> key : ClassFields.keySet() ){  //check in the class fields
  //     if ( key.get(0)==Identifier ){
  //       Type.add(Identifier);
  //       Type.add(null); //method doesn't exist
  //       Type.add(key.get(1));
  //       Type.add(ClassFields.get(key));
  //       return Type;
  //     }
  //   }
  //   return null;  //nowhere found
  // }

  public String checkScope(String Identifier,ArrayList <String> argu) throws Exception{ //returns Type of the Identifier in the same scope of argu else null
    String MethodName = argu.get(0);
    String ClassName = argu.get(1);
    ArrayList <String> tmp = new ArrayList <String>();
    tmp.add(Identifier);
    tmp.add(MethodName);
    tmp.add(ClassName);
    String Type = FunctionFields.get(tmp);  //first check if declared in function scope may shadow classe's field
    if ( Type!=null ){
      return Type;
    }
    tmp.clear();
    tmp.add(MethodName);
    tmp.add(ClassName);
    ArrayList <String> AllArguments = new ArrayList <String>();
    AllArguments = FunctionTypes.get(tmp);  //after check at parameter list
    if ( AllArguments!=null ){
      int index=AllArguments.indexOf(Identifier);
      if ( index!=-1 ){
        return AllArguments.get(index+1);
      }
    }
    tmp.clear();
    tmp.add(Identifier);
    tmp.add(ClassName);
    Type = ClassFields.get(tmp);
    if ( Type!=null ){  //check then in the class fields
      return Type;
    }
    String ClassParent = ClassExtend.get(ClassName);
    if ( ClassParent==null ){
      return null;
    }
    while ( ClassParent!=null ){  //at the end check in the class parents
      tmp.clear();
      tmp.add(Identifier);
      tmp.add(ClassParent);
      Type = ClassFields.get(tmp);
      if ( Type!=null ){
        return Type;
      }
      ClassParent = ClassExtend.get(ClassParent);
    }

    // for ( ArrayList <String> key : FunctionFields.keySet() ){ //first check if declared in function scope may shadow classe's field
    //   if ( key.get(0)==Identifier && key.get(1)==MethodName && key.get(2)==ClassName ){
    //     return FunctionFields.get(key);
    //   }
    // }
    // for ( ArrayList <String> key : FunctionTypes.keySet() ){  //after check at parameter list
    //   if ( key.get(0)==MethodName && key.get(1)==ClassName ){
    //     ArrayList <String> AllArguments = FunctionTypes.get(key);
    //     int index=AllArguments.indexOf(Identifier);
    //     if ( index!=-1 ){
    //       return AllArguments.get(index+1);
    //     }
    //   }
    // }
    // for ( ArrayList <String> key : ClassFields.keySet() ){  //check in the class fields
    //   if ( key.get(0)==Identifier && key.get(1)==ClassName ){
    //     return ClassFields.get(key);
    //   }
    // }
    // String ClassParent = ClassExtend.get();
    // for ( ArrayList <String> key : ClassExtend.keySet() ){  //at the end check in the class parents
    //   if ( key==Identifier ){
    //     if ( ClassExtend.get(key)==null ){  //class hasn't parent
    //       return null;
    //     }
    //     else{
    //       ClassParent = ClassExtend.get(key); //parent
    //     }
    //   }
    // }
    // while ( ClassParent!=null ){
    //   for ( ArrayList <String> key : ClassFields.keySet() ){  //check in the class fields
    //     if ( key.get(0)==Identifier && key.get(1)==ClassParent ){
    //       return ClassFields.get(key);
    //     }
    //   }
    //   ClassParent
    // }
    return null;
  }

  // public boolean checkScope(ArrayList <String> Type,ArrayList <String> argu) throws Exception{  //if in the same scope
  //   if ( Type==null ){
  //     throw new DoesNotExistIdentifier(Type.get(0));
  //   }
  //   if ( Type.get(3)!=argu.get(2) ){  //in different class means not in samme scope
  //     throw new NotInScope
  //   }
  //   return true;
  // }

   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public String visit(Goal n, ArrayList<String> argu) throws Exception {
      String _ret=null;
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
   public String visit(MainClass n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String ClassName = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      String MethodName = "main";
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(MethodName);
      Scope.add(ClassName);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      if ( n.f15.present() ){
        n.f15.accept(this, Scope);
      }
      n.f16.accept(this, argu);
      n.f17.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public String visit(TypeDeclaration n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public String visit(ClassDeclaration n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String ClassName = n.f1.accept(this, argu); //argument to MethodDeclaration
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(ClassName);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, Scope); //for scope knowledge
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
   public String visit(ClassExtendsDeclaration n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String ClassName = n.f1.accept(this, argu);
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(ClassName);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, Scope);
      n.f7.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public String visit(VarDeclaration n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
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
   public String visit(MethodDeclaration n, ArrayList<String> argu) throws Exception {
      String ClassName = argu.get(0);
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String MethodName = n.f2.accept(this, argu);
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(MethodName);
      Scope.add(ClassName);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      if ( n.f8.present() ){
        n.f8.accept(this, Scope);
      }
      n.f9.accept(this, argu);
      n.f10.accept(this, Scope);  //also needs here for type errors
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> FormalParameterTail()
    */
   public String visit(FormalParameterList n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public String visit(FormalParameter n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( FormalParameterTerm() )*
    */
   public String visit(FormalParameterTail n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public String visit(FormalParameterTerm n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public String visit(Type n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public String visit(ArrayType n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public String visit(BooleanType n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "int"
    */
   public String visit(IntegerType n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public String visit(Statement n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public String visit(Block n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public String visit(AssignmentStatement n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public String visit(ArrayAssignmentStatement n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public String visit(IfStatement n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public String visit(WhileStatement n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public String visit(PrintStatement n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | Clause()
    */
   public String visit(Expression n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> Clause()
    * f1 -> "&&"
    * f2 -> Clause()
    */
   public String visit(AndExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String TypeLeft = n.f0.accept(this, argu);
      if ( TypeLeft!="BooleanType" ){
        throw new InvalidAndPart("left");
      }
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      if ( TypeRight!="BooleanType" ){
        throw new InvalidAndPart("right");
      }
      return "BooleanType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public String visit(CompareExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String Type;
      String TypeLeft = n.f0.accept(this, argu);
      if ( TypeLeft!="IntegerType" ){
        Type = checkScope(TypeLeft,argu);
        System.out.println(Type);
        // if ( Type.get(3)!="IntegerType" ){
        //   throw new InvalidComparePart("left",Type.get(0),Type.get(1),Type.get(2),Type.get(3));
        // }
      }
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      if ( TypeRight!="IntegerType" ){
        // Type = getType(TypeRight);
      }
      return "BooleanType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public String visit(PlusExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public String visit(MinusExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public String visit(TimesExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public String visit(MessageSend n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
   public String visit(ExpressionList n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ( ExpressionTerm() )*
    */
   public String visit(ExpressionTail n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public String visit(ExpressionTerm n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> NotExpression()
    *       | PrimaryExpression()
    */
   public String visit(Clause n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | BracketExpression()
    */
   public String visit(PrimaryExpression n, ArrayList<String> argu) throws Exception {
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, ArrayList<String> argu) throws Exception {
      return "IntegerType";
   }

   /**
    * f0 -> "true"
    */
   public String visit(TrueLiteral n, ArrayList<String> argu) throws Exception {
      return "BooleanType";
   }

   /**
    * f0 -> "false"
    */
   public String visit(FalseLiteral n, ArrayList<String> argu) throws Exception {
      return "BooleanType";
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public String visit(Identifier n, ArrayList<String> argu) throws Exception {
      return n.f0.toString();
   }

   /**
    * f0 -> "this"
    */
   public String visit(ThisExpression n, ArrayList<String> argu) throws Exception {
      return "this";
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public String visit(ArrayAllocationExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String Type = n.f3.accept(this, argu);
      // if ( Type!="IntegerType" ){  //---
      //
      // }
      n.f4.accept(this, argu);
      return "ArrayType";
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public String visit(AllocationExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String Identifier = n.f1.accept(this, argu);
      if ( !ClassExtend.containsKey(Identifier) ){
        throw new UnknownNewClass(Identifier);
      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return Identifier;
   }

   /**
    * f0 -> "!"
    * f1 -> Clause()
    */
   public String visit(NotExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String Type = n.f1.accept(this, argu);
      if ( Type!="BooleanType" ){
        throw new InvalidNotPart();
      }
      return "BooleanType";
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public String visit(BracketExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String Type = n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return Type;
   }

}
