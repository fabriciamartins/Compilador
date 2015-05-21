/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Fabricia
 */
public class AnalisadorLexico {
    
    private static AnalisadorLexico instance;
    
    private String[] n = {"0","1","2","3","4","5","6","7","8","9"};
    private ArrayList<String> num = new ArrayList<String>(Arrays.asList(n));
    private String[] l = {"a","b","c","d","e","f","g","h","i","j",
        "k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private ArrayList<String> letras = new ArrayList<String>(Arrays.asList(l));
    private String[] op = {",",";","(",")","{","}","[","]","+",
        "-","*","/","=","<",">","!","&","||"};
    private ArrayList<String> sinais = new ArrayList<String>(Arrays.asList(op));
    private String[] palavras = {"int","void","float","char","return",
        "if","while"}; 
    private ArrayList<String> palavrasReservadas = 
            new ArrayList<String>(Arrays.asList(palavras));
    
    private ArrayList<String> tokens ;
    private String token;
    private boolean passou;
    private String[] simbolos;
    
    private int indexLinha = 0;
    
    private AnalisadorLexico(){
        this.tokens = new ArrayList<String>();
        this.passou = false;
        this.token = "";
    }
    
    public static AnalisadorLexico getInstance(){
        if(instance == null){
            instance = new AnalisadorLexico();
        }
        return instance;
    }
    
    public void adicionarToken(String tipo){
        tokens.add(token+","+tipo);
        limparToken();
        passou = true;
    }
    
    public boolean analisar(String texto){
        texto = texto.replaceAll("\n", "");
        simbolos = texto.split("");
        
        
        for(int i=0; i < simbolos.length; i++){
            if(isLetra(simbolos[i])){
                token += simbolos[i];
                analisarPalavra(i);
            }
            
            else if(isSinal(simbolos[i])){
                
                if((i == simbolos.length-1)){
                    token += simbolos[i];
                    adicionarToken("OPERADOR");
                }
                
                else if((simbolos[i].equals("<") || simbolos[i].equals(">") || 
                        simbolos[i].equals("=")) && (simbolos[i+1].equals("=")) ){
                    
                    token += simbolos[i] + simbolos[i+1];
                    adicionarToken("OPERADOR");
                    i++; 
                }
                
                else if((simbolos[i].equals("&") && simbolos[i+1].equals("&"))
                    || (simbolos[i].equals("|") && simbolos[i+1].equals("|"))){
                    
                    token += simbolos[i] + simbolos[i+1];
                    adicionarToken("OPERADOR");
                    i++;
                }
                
                else{
                    token += simbolos[i];
                    adicionarToken("OPERADOR");
                }
            }//fim do else if dos sinais
            
            else if(isNumero(simbolos[i])){
                analisarNumeros(i);
            }
            
            else{
                if(isEspaco(simbolos[i])){
                    limparToken();
                    passou = true;
                }
                else{
                    passou = false;
                }
            }
            
        }//fim do for
        
        return passou;
    }
    
    
    public void analisarNumeros(int index){
        token += simbolos[index];
        
        if(isLetra(simbolos[index-1])){
            analisarPalavra(index);
        }
        
        else if(index == simbolos.length-1){
            adicionarToken("NUM");
        }
        
        else if((isEspaco(simbolos[index+1]))
                || (isSinal(simbolos[index+1]))){
            adicionarToken("NUM");
        }
        
    }
    
    public ArrayList<String> getTokens(){
        return this.tokens;
    }
    
    public void analisarPalavra(int index){
        if((isLetra(simbolos[index])) || (isNumero(simbolos[index]))){
            
            //System.out.println("token - "+token);
            
            if(isPalavraReservada(token)){
                
                if(index == simbolos.length-1){
                    adicionarToken("PALAVRARESERVADA");
                }
                
                else if((isEspaco(simbolos[index+1])) 
                        || (isSinal(simbolos[index+1]))){
                    adicionarToken("PALAVRARESERVADA");
                }
            }
            
            else if(index == simbolos.length-1){
                adicionarToken("ID");
            }
            
            else if((isEspaco(simbolos[index+1])) || (isSinal(simbolos[index+1]))){
                adicionarToken("ID");
            }
        }

    }//fim do método
    
    public boolean isEspaco(String valor){
        return (valor.equals(" ")) || (valor.equals(""));
    }
    
    public void limparToken(){
        this.token = "";
    }
    
    public boolean isPalavraReservada(String valor){
        return (palavrasReservadas.contains(valor));
    }
    
    public boolean isSinal(String valor){
        return (sinais.contains(valor));
    }
        
    public boolean isLetra(String valor){
        return (letras.contains(valor));
    }
    
    public boolean isNumero(String valor){
        return (num.contains(valor));
    }
}
