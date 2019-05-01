import syntaxtree.*;
import visitor.*;
import java.io.*;
import java.util.*;
import semantic.*;

class Main {
  public static void main (String [] args){
  	// if(args.length != 1){
  	//     System.err.println("Usage: java Driver <inputFile>");
  	//     System.exit(1);
  	// }
    for ( int i=0; i<args.length; i++ ){
    	FileInputStream fis = null;
      System.out.println("----------------------"+args[i]+"-------------------------");
    	try{
    	    fis = new FileInputStream(args[0]);
    	    MiniJavaParser parser = new MiniJavaParser(fis);
    	    System.err.println("Program parsed successfully.");
    	    SymbolTableVisitor SymbolTable = new SymbolTableVisitor();
    	    Goal root = parser.Goal();
          root.accept(SymbolTable, null);
          // SymbolTable.printSymbolTable();
          TypeCheckingVisitor TypeChecking = new TypeCheckingVisitor();
          root.accept(TypeChecking, null);
    	}
    	catch(ParseException ex){
    	    System.out.println(ex.getMessage());
    	}
    	catch(FileNotFoundException ex){
    	    System.err.println(ex.getMessage());
    	}
      catch(Exception e){ //because that throw the SymbolTableVisitor class
        System.out.println("Semantic error");
        System.out.println(e.getMessage()); //overrided at files in folder semantic
      }
    	finally{
    	    try{
    		      if(fis != null) fis.close();
    	    }
    	       catch(IOException ex){
    		      System.err.println(ex.getMessage());
    	    }
    	}
    }
  }
}
