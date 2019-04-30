import syntaxtree.*;
import visitor.*;
import java.io.*;
import semantic.*;

class Main {
    public static void main (String [] args){
	if(args.length != 1){
	    System.err.println("Usage: java Driver <inputFile>");
	    System.exit(1);
	}
	FileInputStream fis = null;
	try{
	    fis = new FileInputStream(args[0]);
	    MiniJavaParser parser = new MiniJavaParser(fis);
	    System.err.println("Program parsed successfully.");
	    SymbolTableVisitor SymbolTable = new SymbolTableVisitor();
	    Goal root = parser.Goal();
      try{
	      System.out.println(root.accept(SymbolTable, null));
        SymbolTable.printSymbolTable();
      }
      catch(Exception e){
        System.out.println(e.getMessage());
      }
	}
	catch(ParseException ex){
	    System.out.println(ex.getMessage());
	}
	catch(FileNotFoundException ex){
	    System.err.println(ex.getMessage());
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
