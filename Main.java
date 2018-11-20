/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArvoreAVL;

/**
 *
 * @author rraffael
 */
public class Main {
    public static void main(String[] args){
        
        Arvore<Integer, String> pessoas = new Arvore<>();

        pessoas.inserir(3, "serei excluido");

        
        pessoas.inserir(2, "teste");
        pessoas.inserir(1, "teste1");
        pessoas.inserir(4, "teste2");
        pessoas.inserir(5, "teste3");
        
        
        pessoas.listar();
        //for (String elemento : arvore1) {
        //    System.out.println(elemento);
        //}
        
    }
    
}
