  package no.uio.ifi.asp.scanner;
  import java.io.*;
  import java.util.*;
  import no.uio.ifi.asp.main.*;
  import static no.uio.ifi.asp.scanner.TokenKind.*;
  public class Scanner {
      private LineNumberReader sourceFile = null;
      private String curFileName;
      private ArrayList<Token> curLineTokens = new ArrayList<>();
      private Stack<Integer> indents = new Stack<>();
      private final int TABDIST = 4;

      public Scanner(String fileName) {
  	     curFileName = fileName;
  	     indents.push(0);

  	try {
  	    sourceFile = new LineNumberReader(
  			    new InputStreamReader(
  				new FileInputStream(fileName),
  				"UTF-8"));
  	} catch (IOException e) {
  	    scannerError("Cannot read " + fileName + "!");
  	}
      }

    private void scannerError(String message) {
  	   String m = "Asp scanner error";
  	   if (curLineNum() > 0)
  	    m += " on line " + curLineNum();
  	    m += ": " + message;
  	Main.error(m);
      }
    public Token curToken() {
  	   while (curLineTokens.isEmpty()) {
  	      readNextLine();
  	    }
        return curLineTokens.get(0);
      }

      public void readNextToken() {
  	     if (! curLineTokens.isEmpty())
  	      curLineTokens.remove(0);
          }

          private void readNextLine() {
        	   curLineTokens.clear();
        	// Read the next line:
        	   String line = null;
           	 try {
           	      line = sourceFile.readLine();
           	      if (line == null) {
               		   sourceFile.close();
               		    sourceFile = null;
           	        } else {
                      Main.log.noteSourceLine(curLineNum(), line);
           	        }
           	      } catch (IOException e) {
           	        sourceFile = null;
           	        scannerError("Unspecified I/O error!");
           	      }
                  //Finds the null line and make a e-o-f token
                  if(line != null){
                    int curChar = 0;
                    String indentStr = expandLeadingTabs(line);

                    if (line.isBlank() || line.charAt(0) == '#'){
                    }else{
                      addIndDedSymbol(line);

                      while(curChar < line.length()) {
                        if(line.charAt(curChar) == '#'){
                          break;
                        }

                        String stringMaker = "";
                        String nameT = "";
                        String number = "";
                        char character = line.charAt(curChar);

                        curChar++;

                        if (character == '"' || character == '\'') {
                          boolean isString = true;
                          try{
                            while(isString) {
                              if (line.charAt(curChar) == '"' || line.charAt(curChar) == '\'') {
                                curChar++;
                                isString = false;
                                break;
                              }
                              stringMaker += line.charAt(curChar++);
                            }
                            if(isString == false) {
                              Token strToken = new Token(stringToken,(curLineNum()));
                              strToken.stringLit = stringMaker;
                              curLineTokens.add(strToken);
                              stringMaker="";
                            }
                          }catch(Exception e){
                            scannerError("Fant ikke sluttfnutt");
                          }
                        }

                        if(isLetterAZ(character)) {
                          nameT += character;
                          try {
                            while ((isLetterAZ(line.charAt(curChar)) || isDigit(line.charAt(curChar)))){
                              nameT += line.charAt(curChar++);
                            }
                            //Gir en feilmelding dersom det kommer et tegn som ikke er med i ASP
                            if(!isValid(line.charAt(curChar)) && line.charAt(curChar) != ' '){
                              scannerError(Character.toString(line.charAt(curChar)) + " er ikke med i ASP.");
                            }
                            for(TokenKind tk : EnumSet.range(andToken, whileToken)) {
                              if(tk.toString().equals(nameT)) {
                                curLineTokens.add(new Token(tk, curLineNum()));
                                nameT="";
                              }
                            }
                          } catch(Exception e) {}
                          }

                          for(TokenKind tokenK : EnumSet.range(andToken, whileToken)) {
                            if(tokenK.toString().equals(nameT)) {
                              curLineTokens.add(new Token(tokenK, curLineNum()));
                              nameT="";
                            }
                          }
                          if(nameT != ""){
                            Token nameTok = new Token(nameToken,(curLineNum()));
                            nameTok.name = nameT;
                            curLineTokens.add(nameTok);
                            nameT = "";
                          }

                      if(isDigit(character)){
                          Boolean intLit = true;
                          number += character;

                          try{
                            int counter = 0;
                            while(isDigit(line.charAt(curChar)) || line.charAt(curChar) == '.'){
                              counter++;
                              if(line.charAt(curChar) == '.'){
                                intLit = false;
                              }

                              if(counter == 1 && character == '0' && line.charAt(curChar) != '.'){
                                Token intToken = new Token(integerToken, curLineNum());
                                intToken.integerLit = Integer.parseInt(number);
                                curLineTokens.add(intToken);
                                number = "";
                              }
                              number += line.charAt(curChar++);
                            }
                            //Gir en feilmelding dersom det kommer et tegn som ikke er med i ASP
                            if(!isValid(line.charAt(curChar)) && line.charAt(curChar) != ' '){
                              scannerError(Character.toString(line.charAt(curChar)) + " er ikke med i ASP.");
                            }
                            if(intLit == true){
                              Token it = new Token(integerToken,(curLineNum()));
                              it.integerLit = Integer.parseInt(number);
                              curLineTokens.add(it);
                              number = "";
                            }
                            if(intLit == false){
                              Token ft = new Token(floatToken,(curLineNum()));
                              ft.floatLit = Double.parseDouble(number);
                              curLineTokens.add(ft);
                              number = "";
                            }
                          } catch(Exception e) {}

                            if(number != ""){
                              if(intLit == true){
                                Token it = new Token(integerToken,(curLineNum()));
                                it.integerLit = Integer.parseInt(number);
                                curLineTokens.add(it);
                                number = "";
                              }
                              if(intLit == false){
                                Token ft = new Token(floatToken,(curLineNum()));
                                ft.floatLit = Double.parseDouble(number);
                                curLineTokens.add(ft);
                                number = "";
                              }
                            }
                          }

                          Boolean doubleOperator = false;
                          for(TokenKind tokens : EnumSet.range(astToken, semicolonToken)) {
                              if(character == '=') {
                                try{
                                  if(line.charAt(curChar++) == '='){
                                    curLineTokens.add(new Token(doubleEqualToken, curLineNum()));
                                    doubleOperator = true;
                                  }else{
                                    curChar--;
                                  }
                                }catch(Exception e){}
                              }else if(character == '!'){
                                try{
                                  if(line.charAt(curChar++) == '='){
                                    curLineTokens.add(new Token(notEqualToken, curLineNum()));
                                    doubleOperator = true;
                                  }else{
                                    curChar--;
                                  }
                                }catch(Exception e){}
                              }else if(character == '<'){
                                try{
                                  if(line.charAt(curChar++) == '='){
                                    curLineTokens.add(new Token(lessEqualToken, curLineNum()));
                                    doubleOperator = true;
                                  }else{
                                    curChar--;
                                  }
                                }catch(Exception e){}
                              }else if(character == '>'){
                                try{
                                  if(line.charAt(curChar++) == '='){
                                    curLineTokens.add(new Token(greaterEqualToken, curLineNum()));
                                    doubleOperator = true;
                                  }else{
                                    curChar--;
                                  }
                                }catch(Exception e){}
                              }else if(character == '/'){
                                try{
                                  if(line.charAt(curChar++) == '/'){
                                    curLineTokens.add(new Token(doubleSlashToken, curLineNum()));
                                    doubleOperator = true;
                                  }else{
                                    curChar--;
                                  }
                                }catch(Exception e){}
                              }
                                if(tokens.toString().equals(String.valueOf(character)) && doubleOperator == false){
                                  curLineTokens.add(new Token(tokens, curLineNum()));
                                }
                           }
                      }

                      curLineTokens.add(new Token(newLineToken,curLineNum()));

                    }

                    for (Token tk : curLineTokens) {
                      //Dette printer ut token for token. Kan fjerne kommentar for å se detaljert utskrift.
                      //System.out.println(tk.kind.image);
                    }

                  }else{
                    for (int i : indents ) {
                      if(i > 0){
                        Token dedent = new Token(dedentToken, curLineNum());
                        curLineTokens.add(dedent);
                      }
                    }

                    Token eToken = new Token(eofToken, curLineNum());
                    curLineTokens.add(eToken);
                  }

                  for (Token t: curLineTokens){
                    Main.log.noteToken(t);
                  }
              }

          public int curLineNum() {
  	         return sourceFile!=null ? sourceFile.getLineNumber() : 0;
           }

           public boolean isSpecialSymbol(char c){
             for(TokenKind tokens : EnumSet.range(astToken, semicolonToken)) {
               if(tokens.toString().equals(String.valueOf(c))){
                 return true;
               }
             }
             return false;
           }

           public boolean isValid(char c){
             if(!isLetterAZ(c) && !isDigit(c) && isSpecialSymbol(c) == false){
               return false;
             }
             return true;
           }

           public void addIndDedSymbol(String line){
             String tabs = expandLeadingTabs(line);
             int counter = findIndent(tabs);
             if (counter > indents.peek()){
               indents.push(counter);
               Token indent = new Token(indentToken, curLineNum());
               curLineTokens.add(indent);

             }else{
               while(counter < indents.peek()){
                 indents.pop();
                 Token dedent = new Token(dedentToken, curLineNum());
                 curLineTokens.add(dedent);
               }
             }
             if(counter != indents.peek()){
               System.out.println("Indenteringsfeil");
             }
           }

    private int findIndent(String s) {
      int indent = 0;
  	  while (indent<s.length() && s.charAt(indent)==' ') indent++;
  	  return indent;
      }

      private void createEoF(){
        Token tok = new Token(eofToken);
        curLineTokens.add(tok);
      }

      private String expandLeadingTabs(String s) {
  	     String newS = "";
  	     for (int i = 0;  i < s.length();  i++) {
  	        char c = s.charAt(i);
  	         if (c == '\t') {
  		         do {
  		          newS += " ";
  		         } while (newS.length()%TABDIST > 0);
  	          } else if (c == ' ') {
                newS += " ";
      	      } else {
                newS += s.substring(i);
  		          break;
  	      }
  	   }
  	    return newS;
      }

      private boolean isLetterAZ(char c) {
  	     return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
      }

      private boolean isDigit(char c) {
  	     return '0'<=c && c<='9';
      }

      public boolean isCompOpr() {
  	     TokenKind k = curToken().kind;
         if(k == lessToken || k == greaterToken || k == doubleEqualToken || k == greaterEqualToken ||
         k == lessEqualToken || k == notEqualToken){
           return true;
         }
  	   return false;
      }

      public boolean isFactorOpr() {
  	    TokenKind k = curToken().kind;
        if(k == astToken || k == slashToken || k == percentToken || k == doubleSlashToken){
          return true;
        }
  	    return false;
      }

      public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        if(k == plusToken || k == minusToken){
          return true;
        }
  	    return false;
      }

      public boolean anyEqualToken() {
  	    for (Token t: curLineTokens) {
  	      if (t.kind == equalToken) return true;
          if (t.kind == semicolonToken) return false;
  	    }
  	    return false;
      }
  }
