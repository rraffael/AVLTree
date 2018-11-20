/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AVLTree;

/**
 *
 * @author rraffael
 */
public class No<K extends Comparable<K>, V> {
    
    No<K, V> pai, esquerdo, direito;
    K chave;
    V valor;
    private int peso;
    public No(K chave, V valor, No<K, V> pai){
        this.pai = pai;
        this.chave = chave;
        this.valor = valor;
        this.peso = 0;
    }
    
    public int getPeso(){
        if(this.peso != 0){
            this.peso = 0;
        }
        if(this.esquerdo == null && this.direito == null){
            return peso;
        }
        if(this.esquerdo != null){
            this.percorrerEsquerda(this.esquerdo);
        }
        if(this.direito != null){
            this.percorrerDireito(this.direito);
        }
        return peso;
    }
    
    private void percorrerEsquerda(No<K, V> no){
        peso++;
        if(this.esquerdo != null){
            this.percorrerEsquerda(no.esquerdo);            
        }
        if(this.direito != null){
            this.percorrerEsquerda(no.direito);
        }
    }
    
    private void percorrerDireito(No<K, V> no){
        peso--;
        if(this.esquerdo != null){
            this.percorrerDireito(no.esquerdo);            
        }
        if(this.direito != null){
            this.percorrerDireito(no.direito);
        }
    }

    public No<K, V> getPai() {
        return pai;
    }

    public void setPai(No<K, V> pai) {
        this.pai = pai;
    }

    public No<K, V> getEsquerdo() {
        return esquerdo;
    }

    public void setEsquerdo(No<K, V> esquerdo) {
        this.esquerdo = esquerdo;
    }

    public No<K, V> getDireito() {
        return direito;
    }

    public void setDireito(No<K, V> direito) {
        this.direito = direito;
    }

    public K getChave() {
        return chave;
    }

    public void setChave(K chave) {
        this.chave = chave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

}
