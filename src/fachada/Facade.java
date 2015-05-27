/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import compilador.AnalisadorLexico;
import java.util.ArrayList;

/**
 *
 * @author Fabricia
 */
public class Facade {
    
    private final AnalisadorLexico alexico = AnalisadorLexico.getInstance();
    
    public boolean analisarLexicamente(String texto){
        return alexico.analisar(texto);
    }
    
    public ArrayList<String> getTokens(){
        return alexico.getTokens();
    }
    
    public void limparListaTokens(){
        alexico.limparListaTokens();
    }
    
    public String getSimboloInvalido(){
        return alexico.getSimboloInvalido();
    }
}
