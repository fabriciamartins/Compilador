/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fachada;

import ui.Home;
import compilador.AnalisadorLexico;
import java.util.ArrayList;

/**
 *
 * @author Fabricia
 */
public class Facade {
    
    private AnalisadorLexico alexico = AnalisadorLexico.getInstance();
    private static Home visual = Home.getInstance();
    
    public void analisarLexicamente(String texto){
        boolean resultadoLexico = alexico.analisar(texto);
        visual.mostrarResultado(resultadoLexico);
    }
    
    public ArrayList<String> getTokens(){
        return alexico.getTokens();
    }
}
