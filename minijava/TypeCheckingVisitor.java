import syntaxtree.*;
import visitor.GJDepthFirst;
import java.util.*;
import semantic.*;

public class TypeCheckingVisitor extends GJDepthFirst<String,ArrayList<String>>{

  public static Map<String, String> ClassExtend;
  public static Map<ArrayList <String>, String> ClassFields;
  public static Map<ArrayList <String>, String> FunctionFields;
  public static Map<ArrayList <String>, ArrayList<String>> FunctionTypes;
  private Map<String, ArrayList< ArrayList<String>>> ClassRowFields;  //  ClassNames as appeared in input file each with it's info example element A->[[i 0] [j 4]]
  private Map<String, ArrayList< ArrayList<String>>> ClassRowFunctions;  //  A->[[foo 0]]
  private int InClassNow;

  public TypeCheckingVisitor(){   //same reference as SymbolTableVisitor class fields
    this.ClassExtend = SymbolTableVisitor.ClassExtend;
    this.ClassFields = SymbolTableVisitor.ClassFields;
    this.FunctionFields = SymbolTableVisitor.FunctionFields;
    this.FunctionTypes = SymbolTableVisitor.FunctionTypes;
    ClassRowFields = new LinkedHashMap<String, ArrayList< ArrayList<String>>>();
    ClassRowFunctions = new LinkedHashMap<String, ArrayList< ArrayList<String>>>();
    InClassNow = 0;
  }

  public void printOffsets(){
    for ( String ClassName : ClassRowFields.keySet() ){
      System.out.println("-----------Class "+ClassName+"-----------");
      System.out.println("--Variables---");
      ArrayList< ArrayList<String>> ForThisClassFields = ClassRowFields.get(ClassName);
      for ( int i=0; i<ForThisClassFields.size(); i++ ){
        String Identifier = ForThisClassFields.get(i).get(0);
        String Offset = ForThisClassFields.get(i).get(1);
        System.out.println(ClassName+"."+Identifier+" : "+Offset);
      }
      System.out.println("--Methods---");
      ArrayList< ArrayList<String>> ForThisClassFunctions = ClassRowFunctions.get(ClassName);
      for ( int i=0; i<ForThisClassFunctions.size(); i++ ){
        String Identifier = ForThisClassFunctions.get(i).get(0);
        String Offset = ForThisClassFunctions.get(i).get(1);
        System.out.println(ClassName+"."+Identifier+" : "+Offset);
      }
      System.out.println();
    }
  }

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
    ArrayList <String> AllArguments = new ArrayList <String>(FunctionTypes.get(tmp));  //after check at parameter list
    if ( !AllArguments.isEmpty() ){
      AllArguments.remove(0);
      keepVariables(AllArguments);
      int index=AllArguments.indexOf(Identifier);
      if ( index!=-1 ){
        return FunctionTypes.get(tmp).get(index*2+2); //0->2 1->4 2->6 ...
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
    return null;
  }

  public String IdentifierAndCheck(String Identifier,ArrayList<String> argu)  throws Exception{ //returns the type of the Identifier
    String Type = null;
    if ( isIdentifier(Identifier) ){
      Type = checkScope(Identifier,argu);
      if ( Type==null ){
        throw new DifferentScope(Identifier,argu.get(0),argu.get(1));
      }
    }
    else{
      Type = Identifier;
    }
    return Type;
  }

  public void keepVariables(ArrayList<String> myList){  //keeps identifiers, they are at odd indexes
    for ( int i=0; i<myList.size(); i++ ){
      if ( !isIdentifier(myList.get(i)) ){
        myList.remove(i);
      }
    }
  }

  public boolean isIdentifier(String Identifier){
    if ( Identifier.equals("IntegerType") || Identifier.equals("BooleanType") || Identifier.equals("ArrayType") ||ClassExtend.containsKey(Identifier) ){
      return false;
    }
    return true;
  }

  public ArrayList<String> checkMethod(String MethodName,String IfInClass,ArrayList<String> argu) throws Exception{ //checks if MethodName exists in IfInClass class or parent classe's
    ArrayList<String> tmp = new ArrayList<String>();
    String ClassParent = IfInClass; //first class to check
    while ( ClassParent!=null ){
      tmp.add(MethodName);
      tmp.add(ClassParent);
      ArrayList<String> AllArguments = FunctionTypes.get(tmp);
      if ( AllArguments!=null ){  //exists
        return AllArguments;
      }
      ClassParent = ClassExtend.get(ClassParent);
      tmp.clear();
    }
    return null;
  }

  public boolean isPredecessor(String Type,String Identifier){ //if Type is predecessor of Identifier
    String ClassParent = ClassExtend.get(Identifier);
    if ( Type.equals(Identifier) ){
      return true;
    }
    while ( ClassParent!=null ){
      if ( ClassParent.equals(Type) ){
        return true;
      }
      ClassParent = ClassExtend.get(ClassParent);
    }
    return false;
  }

  public void filterPass(String IntOrBoolean,String Operator,String Part,String Identifier,ArrayList<String> argu) throws Exception{  //obstacle challenge pass
    String Type=IdentifierAndCheck(Identifier,argu);
    if ( Type!=IntOrBoolean ){  //for both types
        throw new InvalidPart(IntOrBoolean,Operator,Part,Identifier,argu.get(0),argu.get(1),Type);
    }
  }

  public void KeepTypes(ArrayList <String> myList){ //keep only types
    for ( int i=1; i<myList.size(); i++ ){  //value 0 may be a class
      String value = myList.get(i);
      if ( value!="IntegerType" && value!="BooleanType" && value!="ArrayType" ){
        myList.remove(value);
      }
    }
  }

  public boolean equalsAndParents(ArrayList <String> listParent,ArrayList <String> listToCheck){ //checks if listParent is Parent list for listToCheck
    if ( listParent.size()!=listToCheck.size() ){
      return false;
    }
    for ( int i=0; i<listParent.size(); i++ ){
      String Parent = listParent.get(i);
      String Child = listToCheck.get(i);
      if ( !isPredecessor(Parent,Child) ){
        return false;
      }
    }
    return true;
  }
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
      ArrayList< ArrayList<String>> ForThisClassFields = new ArrayList< ArrayList<String>>(); //reference so can change it later
      ClassRowFields.put(ClassName,ForThisClassFields); //filled at VarDeclaration and Method
      ArrayList< ArrayList<String>> ForThisClassFunctions = new ArrayList< ArrayList<String>>();
      ClassRowFunctions.put(ClassName,ForThisClassFunctions);
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(ClassName);
      n.f2.accept(this, argu);
      InClassNow = 1;
      n.f3.accept(this, Scope);
      InClassNow = 0;
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
      ArrayList< ArrayList<String>> ForThisClassFields = new ArrayList< ArrayList<String>>();
      ClassRowFields.put(ClassName,ForThisClassFields);
      ArrayList< ArrayList<String>> ForThisClassFunctions = new ArrayList< ArrayList<String>>();
      ClassRowFunctions.put(ClassName,ForThisClassFunctions);
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(ClassName);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      InClassNow = 1;
      n.f5.accept(this, Scope);
      InClassNow = 0;
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
      if ( InClassNow==1 ){  //it comes from ClassDeclaration not from VarDeclaration in MethodDeclaration
        String Type = n.f0.accept(this, argu);
        String Identifier = n.f1.accept(this, argu);
        ArrayList< ArrayList<String>> ForThisClass = ClassRowFields.get(argu.get(0));
        ArrayList<String> Pair = new ArrayList<String>();
        String ClassParent = ClassExtend.get(argu.get(0));
        if ( ForThisClass.isEmpty() ){ //no var declarations for this class before
          if ( ClassParent!=null ){
            ArrayList< ArrayList<String>> InfoParent = ClassRowFields.get(ClassParent);
            if ( !InfoParent.isEmpty() ){//there were some declarations before
              Pair.add(Identifier);
              ArrayList<String> lastArrayList = InfoParent.get(InfoParent.size()-1);
              String lastOffset = lastArrayList.get(1);
              String lastIdentifier = lastArrayList.get(0);
              ArrayList<String> toSearch = new ArrayList<String>();
              toSearch.add(lastIdentifier);
              toSearch.add(ClassParent);
              String lastType = ClassFields.get(toSearch);
              int lastTypeNum = 8;
              if ( lastType.equals("IntegerType") ){
                lastTypeNum = 4;
              }
              else if ( lastType.equals("BooleanType") ){
                lastTypeNum = 1;
              }
              Pair.add(String.valueOf(Integer.parseInt(lastOffset)+lastTypeNum));
              ForThisClass.add(Pair);
            }
            else{
              Pair.add(Identifier);
              Pair.add(String.valueOf(0));
              ForThisClass.add(Pair);
            }
          }
          else{
            Pair.add(Identifier);
            Pair.add(String.valueOf(0));
            ForThisClass.add(Pair);
          }
        }
        else{
          Pair.add(Identifier);
          ArrayList<String> lastArrayList = ForThisClass.get(ForThisClass.size()-1);
          String lastOffset = lastArrayList.get(1);
          String lastIdentifier = lastArrayList.get(0);
          ArrayList<String> toSearch = new ArrayList<String>();
          toSearch.add(lastIdentifier);
          toSearch.add(argu.get(0));
          String lastType = ClassFields.get(toSearch);
          int lastTypeNum = 8;
          if ( lastType.equals("IntegerType") ){
            lastTypeNum = 4;
          }
          else if ( lastType.equals("BooleanType") ){
            lastTypeNum = 1;
          }
          Pair.add(String.valueOf(Integer.parseInt(lastOffset)+lastTypeNum));
          ForThisClass.add(Pair);
        }
      }
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
      String MethoType = n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String MethodName = n.f2.accept(this, argu);
      ArrayList< ArrayList<String>> ForThisClass = ClassRowFunctions.get(ClassName);
      ArrayList<String> Pair = new ArrayList<String>();
      String ClassParent = ClassExtend.get(ClassName);
      if ( ForThisClass.isEmpty() ){ //no var declarations for this class before
        if ( ClassParent!=null ){
          ArrayList< ArrayList<String>> InfoParent = ClassRowFunctions.get(ClassParent);
          if ( !InfoParent.isEmpty() ){//there were some declarations before
            ArrayList<String> toSearch = new ArrayList<String>();
            toSearch.add(MethodName);
            toSearch.add(ClassParent);
            if ( FunctionTypes.containsKey(toSearch) ){ //exist also at parent's class
              Pair.add(MethodName);
              String lastOffset = null;
              for ( int i=0; i<InfoParent.size(); i++ ){
                ArrayList<String> MethodParent = InfoParent.get(i);
                if ( MethodName.equals(MethodParent.get(0)) ){
                  lastOffset = MethodParent.get(1);
                }
              }
              Pair.add(String.valueOf(Integer.parseInt(lastOffset)));
              ForThisClass.add(Pair);
            }
            else{
              Pair.add(MethodName);
              Pair.add(String.valueOf(0));
              ForThisClass.add(Pair);
            }
          }
          else{
            Pair.add(MethodName);
            Pair.add(String.valueOf(0));
            ForThisClass.add(Pair);
          }
        }
        else{
          Pair.add(MethodName);
          Pair.add(String.valueOf(0));
          ForThisClass.add(Pair);
        }
      }
      else{
        Pair.add(MethodName);
        ArrayList<String> lastArrayList = ForThisClass.get(ForThisClass.size()-1);
        String lastOffset = lastArrayList.get(1);
        Pair.add(String.valueOf(Integer.parseInt(lastOffset)+8));
        ForThisClass.add(Pair);
      }
      ArrayList<String> Scope = new ArrayList<String>();
      Scope.add(MethodName);
      Scope.add(ClassName);
      n.f3.accept(this, argu);
      n.f4.accept(this, Scope); //parameters have value
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      if ( n.f8.present() ){
        n.f8.accept(this, Scope);
      }
      n.f9.accept(this, argu);
      String Identifier = n.f10.accept(this, Scope);  //also needs here for type errors
      String Type = IdentifierAndCheck(Identifier,Scope);
      ArrayList<String> ForPrototype = new ArrayList<String>(Scope);
      if ( Scope.size()==3 ){  //delete last argument
        ForPrototype.remove(2);
      }
      ArrayList<String> Prototype = FunctionTypes.get(ForPrototype);
      String ReturnType = Prototype.get(0);
      if ( Type!=ReturnType ){
        throw new InvalidReturnType(Type,ReturnType,MethodName,ClassName);
      }
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
      String Identifier = n.f1.accept(this, argu);
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
      String Identifier = n.f0.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      String MethodName = argu.get(0);
      String ClassName = argu.get(1);
      argu.clear();
      argu.add(MethodName);
      argu.add(ClassName);
      argu.add(Identifier); //for knownig the new ClassName() what to do
      n.f1.accept(this, argu);
      String TypeIdentifier = n.f2.accept(this, argu);
      if ( TypeIdentifier.equals("this") ){
        if ( !isPredecessor(Type,ClassName) ){
          throw new UnsupportedInheritance(Type,ClassName,argu.get(0),argu.get(1));
        }
        return _ret;
      }
      String TypeExpression = IdentifierAndCheck(TypeIdentifier,argu);
      if ( TypeExpression!=Type ){
        throw new InvalidAssign(Type,TypeExpression,argu.get(0),argu.get(1));
      }
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
      String Identifier = n.f0.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      if ( Type!="ArrayType" ){
        throw new NotAnArray("ArrayAssignmentStatement",Identifier,Type,argu.get(0),argu.get(1));
      }
      n.f1.accept(this, argu);
      String index = n.f2.accept(this, argu);
      String Typeindex = IdentifierAndCheck(index,argu);
      if ( Typeindex!="IntegerType" ){
        throw new NotAnInt(Typeindex,argu.get(0),argu.get(1));
      }
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      String TypeIdentifier = n.f5.accept(this, argu);
      String TypeExpression = IdentifierAndCheck(TypeIdentifier,argu);
      if ( TypeExpression!="IntegerType" ){
        throw new InvalidArrayAssign(Identifier,TypeExpression,argu.get(0),argu.get(1));
      }
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
      String Identifier = n.f2.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      if ( Type!="BooleanType" ){
        throw new OnlyBoolean("IfStatement",Type,argu.get(0),argu.get(1));
      }
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
      String Identifier = n.f2.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      if ( Type!="BooleanType" ){
        throw new OnlyBoolean("WhileStatement",Type,argu.get(0),argu.get(1));
      }
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
      String Identifier = n.f2.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      if ( Type!="IntegerType" && Type!="BooleanType" ){
        throw new InvalidPrint(Type,argu.get(0),argu.get(1));
      }
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
      filterPass("BooleanType","logical &&","left",TypeLeft,argu);
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      filterPass("BooleanType","logical &&","right",TypeRight,argu);
      return "BooleanType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
   public String visit(CompareExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String TypeLeft = n.f0.accept(this, argu);
      filterPass("IntegerType","compare <","left",TypeLeft,argu);
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      filterPass("IntegerType","compare <","right",TypeRight,argu);
      return "BooleanType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public String visit(PlusExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String TypeLeft = n.f0.accept(this, argu);
      filterPass("IntegerType","plus +","left",TypeLeft,argu);
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      filterPass("IntegerType","plus +","right",TypeRight,argu);
      return "IntegerType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public String visit(MinusExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String TypeLeft = n.f0.accept(this, argu);
      filterPass("IntegerType","minus -","left",TypeLeft,argu);
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      filterPass("IntegerType","minus -","right",TypeRight,argu);
      return "IntegerType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public String visit(TimesExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String TypeLeft = n.f0.accept(this, argu);
      filterPass("IntegerType","times *","left",TypeLeft,argu);
      n.f1.accept(this, argu);
      String TypeRight = n.f2.accept(this, argu);
      filterPass("IntegerType","times *","right",TypeRight,argu);
      return "IntegerType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public String visit(ArrayLookup n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String ArrayName = n.f0.accept(this, argu);
      String Type = IdentifierAndCheck(ArrayName,argu);
      if ( Type!="ArrayType" ){
        throw new NotAnArray("ArrayLookup",ArrayName,Type,argu.get(0),argu.get(1));
      }
      n.f1.accept(this, argu);
      String index = n.f2.accept(this, argu);
      String Typeindex = IdentifierAndCheck(index,argu);
      if ( Typeindex!="IntegerType" ){
        throw new InvalidArrayIndex(ArrayName,index,Typeindex,argu.get(0),argu.get(1));
      }
      n.f3.accept(this, argu);
      return "IntegerType";
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public String visit(ArrayLength n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String ArrayName = n.f0.accept(this, argu);
      String Type = IdentifierAndCheck(ArrayName,argu);
      if ( Type!="ArrayType" ){
        throw new NotAnArray("ArrayLength",ArrayName,Type,argu.get(0),argu.get(1));
      }
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return "IntegerType";
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
      String ClassName = n.f0.accept(this, argu);
      if ( ClassName.equals("this") ){ //check in FunctionFields
        ClassName = argu.get(1);
      }
      String Type = IdentifierAndCheck(ClassName,argu);
      if ( !ClassExtend.containsKey(Type) ){
        throw new UnknownObjectName(Type,argu.get(0),argu.get(1));
      }
      n.f1.accept(this, argu);
      String MethodName = n.f2.accept(this, argu);
      ArrayList<String> AllArguments = checkMethod(MethodName,Type,argu);
      if ( AllArguments==null ){
        throw new DoesNotExistMethodInClass(MethodName,Type,argu.get(0),argu.get(1));
      }
      ArrayList<String> AllArgumentsCheck = new ArrayList<String>(AllArguments);
      KeepTypes(AllArgumentsCheck);
      String ReturnType = AllArgumentsCheck.get(0);
      AllArgumentsCheck.remove(0);  //remove return type
      n.f3.accept(this, argu);
      ArrayList<String> AllArgumentsCall = new ArrayList<String>();
      AllArgumentsCall.add(argu.get(0));  //to use IdentifierAndCheck
      AllArgumentsCall.add(argu.get(1));
      if ( n.f4.present() ){
        n.f4.accept(this, AllArgumentsCall);
      }
      AllArgumentsCall.remove(0);
      AllArgumentsCall.remove(0);
      if ( !equalsAndParents(AllArgumentsCheck,AllArgumentsCall) ){
        throw new DifferentPrototype(MethodName,argu.get(0),argu.get(1));
      }
      n.f5.accept(this, argu);
      return ReturnType;
   }

   /**
    * f0 -> Expression()
    * f1 -> ExpressionTail()
    */
   public String visit(ExpressionList n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      String Identifier = n.f0.accept(this, argu);
      String Type = null;
      if ( Identifier.equals("this") ){
        Type = argu.get(1);
      }
      else{
        Type = IdentifierAndCheck(Identifier,argu);
      }
      argu.add(Type);
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
      String Identifier = n.f1.accept(this, argu);
      String Type = null;
      if ( Identifier.equals("this") ){
        Type = argu.get(1);
      }
      else{
        Type = IdentifierAndCheck(Identifier,argu);
      }
      argu.add(Type);
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
      String Identifier = n.f3.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      if ( Type!="IntegerType" ){
        throw new InvalidAllocationIndex(argu.get(0),argu.get(1));
      }
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
      //  argu.size()==2
      String Type = Identifier; //just the type after new
      if ( argu.size()==3 ){  //return the type of Identifier if it is called from AssignmentStatement
        Type = IdentifierAndCheck(argu.get(2),argu);
        if ( !isPredecessor(Type,Identifier) ){ //at Objects AssignmentStatement
          throw new UnsupportedInheritance(Type,Identifier,argu.get(0),argu.get(1));
        }
        String MethodName = argu.get(0);
        String ClassName = argu.get(1);
        argu.clear();
        argu.add(MethodName);
        argu.add(ClassName);
        // argu.remove(2); //for occasion new ClassName()
      }
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return Type;
   }

   /**
    * f0 -> "!"
    * f1 -> Clause()
    */
   public String visit(NotExpression n, ArrayList<String> argu) throws Exception {
      String _ret=null;
      n.f0.accept(this, argu);
      String Type = n.f1.accept(this, argu);
      filterPass("BooleanType","logical !","right",Type,argu);
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
      String Identifier = n.f1.accept(this, argu);
      String Type = IdentifierAndCheck(Identifier,argu);
      n.f2.accept(this, argu);
      return Type;
   }

}
