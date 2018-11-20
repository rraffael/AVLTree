/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArvoreAVL;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author rraffael
 * @param <K>
 * @param <V>
 */
    public class Arvore <K extends Comparable <K>, V> implements Iterable<V> {
    
    No<K, V> raiz;
    int tamanhoArvore;
    //No<K, V> vetor[] = new No<K, V>[];
    
    public Arvore(){ 
        this.raiz = null;
        this.tamanhoArvore = 0;
    }
    
    //na inserção/remoção checar o peso do nó, se tiver no intervalo 0, apenas insere/remove, senao, faz o balanceamento
    
    
    
    public V inserir(K chave, V valor){
    if(valor!= null){
        if(this.raiz == null){
            this.raiz = new No<>(chave, valor, null);
            this.tamanhoArvore++;
            return valor;
        }
        this.inserirNoPriv(chave, valor, this.raiz);
        return valor;
    }
        return null;
    }
    //Insere um novo no
    private No<K, V> inserirNoPriv(K chave, V valor, No<K, V> no){
        //Se o numero já estiver presente na árvore, retornará nulo
        if(no.getChave().compareTo(chave) == 0){
            return null;
        }
        //Esquerdo
        if(no.getChave().compareTo(chave) > 0){
            if(no.esquerdo == null){
                No<K, V> noRes = new No<>(chave, valor, no);
                no.esquerdo = noRes;
                this.tamanhoArvore++;
                return noRes;
            }
            inserirNoPriv(chave, valor, no.esquerdo);
        }
        
        //Direito
        if(no.getChave().compareTo(chave) < 0){
            if(no.direito == null){
                No<K, V> noRes = new No<>(chave, valor, no);
                no.direito = noRes;
                this.tamanhoArvore++;
                return noRes;
            }
            inserirNoPriv(chave, valor, no.direito);
        }
        return null;
    }
    
    
    public No<K, V> buscarNo(K chave){
         if(this.raiz == null){
             return null;
         }
         return buscarNoPriv(this.raiz, chave);
     }
    
    private No<K, V> buscarNoPriv(No<K, V> no, K chave){
        if(no.getChave().compareTo(chave) == 0){
            return no;
        }
        if(no.getChave().compareTo(chave) > 0){
            if(no.esquerdo != null){
                return buscarNoPriv(no.esquerdo, chave);
            }
        }
        if(no.getChave().compareTo(chave) < 0){
            if(no.direito != null){
                return buscarNoPriv(no.direito, chave);
            }
        }
        return null;
    }
    
    public void limpar(){
        this.raiz = null;
        this.tamanhoArvore = 0;
    }
    
    public int tamanho(){
        return this.tamanhoArvore;
    }
    
    public K remover(K chave){
        if(this.raiz == null || chave == null){
            return null;
        }
        No<K, V> no = removerNoPriv(chave, this.raiz);
        if(no != null){
        this.tamanhoArvore--;
        }
        return chave;
       
    }

    private No<K, V> removerNoPriv(K chave, No<K, V> no){
        if(no.getChave().compareTo(chave) == 0){
            //Caso 1, no folha
            if(no.esquerdo == null && no.direito == null){
                if(no.pai != null){
                    if(no.pai.esquerdo == no){
                    no.pai.esquerdo = null;
                    return no;
                    }
                    if(no.pai.direito == no){
                    no.pai.direito = null;
                    return no;
                    }
                }else{
                    this.limpar();
                    return null;
                }
            }
            //Fim caso 1
            
            //Caso 2, subarvore
            if(no.direito != null && no.esquerdo == null){
                if(no.pai != null){
                    no.direito.pai = no.pai;
                    if(no.pai.esquerdo == no){
                    no.pai.esquerdo = no.direito;
                    return no;
                    }
                    if(no.pai.direito == no){
                    no.pai.direito = no.direito;
                    return no;
                    }
                }else{
                    this.raiz = no.direito;
                    return no;
                }
            }
            if(no.esquerdo != null && no.direito == null){
                if(no.pai != null){
                    no.esquerdo.pai = no.pai;
                    if(no.pai.esquerdo == no){
                    no.pai.esquerdo = no.esquerdo;
                    return no;
                    }
                    if(no.pai.direito == no){
                    no.pai.direito = no.esquerdo;
                    return no;
                    }
                }else{
                    this.raiz = no.esquerdo;
                    return no;
                }
            }
            //Fim caso 2
            
            //Caso 3
            if(no.esquerdo != null && no.direito != null){
                No<K, V> noRes = this.percorrerEsquerdo(no.direito);
                //duvida, cria uma pilha de situacoes caso 3 em arvores muito grandes
                if(noRes.direito != null){
                    noRes.direito.pai = noRes.pai;
                    noRes.pai.esquerdo = noRes.direito;
                }
                no.setChave(noRes.getChave());
                no.setValor(noRes.getValor());
                return no;
            }
            //Fim caso 3
        }
        //esquerdo
        if(no.getChave().compareTo(chave) > 0){
            if(no.esquerdo != null){
            removerNoPriv(chave, no.esquerdo);
            return no;
            }
        }
        //direito
        if(no.getChave().compareTo(chave) < 0){
            if(no.direito != null){
            removerNoPriv(chave, no.direito);
            return no;
            }
        }
        return null;
    }
    
    public No<K, V> percorrerEsquerdo(No<K, V> no){
        if(no.esquerdo != null){
            percorrerEsquerdo(no.esquerdo);
        }
        return no;
    }
    
    public void listar(){
        this.percorrerLargura();
    }
    
    private void percorrerSimetrico(No<K, V> no){
        if(no != null){
            percorrerSimetrico(no.esquerdo);
            System.out.println(no.chave);
            percorrerSimetrico(no.direito);
        }
        
    }
    
    private void percorrerOrdemContraria(No<K, V> no){
        if(no != null){
            percorrerOrdemContraria(no.esquerdo);
            percorrerOrdemContraria(no.direito);
            System.out.println(no.chave);
        }
    }
    
    private void percorrerOrdem(No<K, V> no){
        if(no != null){
            System.out.println(no.chave);
            percorrerOrdem(no.esquerdo);
            percorrerOrdem(no.direito);
        }
    }
    
    //private fila/lista percorrerLargura
    //fila.adicionar, arvore.getRaiz;
    //while(filanaovazia)
    //ex: remove 22, printa, adiciona os filhos dele, e parte para proximo elemento da fila
    //checa primeiro esquerda depois direito depois repete o while.
    //remove 1 elemento, adiciona esquerdo, direito e printa o no removido.
    private void percorrerLargura(){
            
        LinkedList<No> lista = new LinkedList<>();
        lista.add(this.raiz);
        
        while(!lista.isEmpty()){
            No<K, V> no = lista.removeFirst();
            if(no == null){
                continue;
            }
            System.out.println(no);
            lista.add(no.esquerdo);
            lista.add(no.direito);
        }
        System.out.println("FIM");
    }
    
    
    
    @Override
    public Iterator<V> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
