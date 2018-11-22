/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AVLTree;

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
    No<K, V> pivo;
    //No<K, V> vetor[] = new No<K, V>[];
    
    public Arvore(){ 
        this.raiz = null;
        this.tamanhoArvore = 0;
        this.pivo = null;
    }
    
    //na inserção/remoção checar o peso do nó, se tiver no intervalo 0, apenas insere/remove, senao, faz o balanceamento
    public V inserir(K chave, V valor){
        if(valor!= null){
            if(this.raiz == null){
                this.raiz = new No<>(chave, valor, null);
                this.tamanhoArvore++;
                return valor;
            }
            this.calcularPesosNos(this.raiz);
            this.inserirNoPriv(chave, valor, this.raiz);
            this.balancearArvore(this.pivo);
            this.pivo = null;
            return valor;
        }
        return null;
    }
    
    //Insere um novo no
    private No<K, V> inserirNoPriv(K chave, V valor, No<K, V> no){
        if(no.calcularPeso()!= 0){
            this.pivo = no;
        }
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
    
    private void balancearArvore(No<K, V> no){
        if(no != null){
            int oldPeso = no.getPeso();
            //rotacao esquerda
            if(oldPeso < no.calcularPeso()){
                oldPeso = no.esquerdo.getPeso();
                //rotacao esquerda-direita
                if(oldPeso > no.esquerdo.calcularPeso()){
                    this.rotacionarParaEsquerda(no.esquerdo);
                }
                this.rotacionarParaDireita(no);
            }
            //rotacao direita
            if(oldPeso > no.calcularPeso()){
                oldPeso = no.direito.getPeso();
                //rotacao direita-esquerda
                if(oldPeso < no.direito.calcularPeso()){
                    this.rotacionarParaDireita(no.direito);
                }
                this.rotacionarParaEsquerda(no);

            }
        }
    }
    
    //
    private void rotacionarParaDireita(No<K, V> no){
        if(no == this.raiz){
            no.esquerdo.pai = null;
            this.raiz = no.esquerdo;
        }else{
            no.esquerdo.pai = no.pai;
        }
        no.pai = no.esquerdo;
        if(no.esquerdo.direito != null){
            no.esquerdo = no.esquerdo.direito;
            no.esquerdo.pai = no;
        }else{
            no.esquerdo = null;
        }
        no.pai.direito = no;
        if(no.pai != this.raiz){
            no.pai.pai.esquerdo = no.pai;
        }
        
    }
    
    //
    private void rotacionarParaEsquerda(No<K, V> no){
        if(no == this.raiz){
            no.direito.pai = null;
            this.raiz = no.direito;
        }else{
            no.direito.pai = no.pai;
        }
        no.pai = no.direito;
        if(no.direito.esquerdo != null){
            no.direito = no.direito.esquerdo;
            no.direito.pai = no;
        }else{
            no.direito = null;
        }
        no.pai.esquerdo = no;
        if(no.pai != this.raiz){
            no.pai.pai.direito = no.pai;
        }
    }
    
    private void calcularPesosNos(No<K, V> no){
        if(no != null){
            no.calcularPeso();
            this.calcularPesosNos(no.esquerdo);
            this.calcularPesosNos(no.direito);
        }
        
    }
    //PROBLEMA EM REMOVER
    public K remover(K chave){
        if(this.raiz == null || chave == null){
            return null;
        }
        if(this.raiz.getChave() == chave){
            this.limpar();
            return chave;
        }
        removerNoPriv(chave, this.raiz);
        this.tamanhoArvore--;
        
        return chave;
       
    }

    private void removerNoPriv(K chave, No<K, V> no){
        if(no.getChave().compareTo(chave) == 0){
            //Caso 1, no folha
            if(no.esquerdo == null && no.direito == null){
                if(no.pai.esquerdo == no){
                no.pai.esquerdo = null;

                }
                if(no.pai.direito == no){
                no.pai.direito = null;

                }
            }
            //Fim caso 1
            
            //Caso 2, subarvore
            if(no.direito != null && no.esquerdo == null){
                no.direito.pai = no.pai;
                if(no.pai.esquerdo == no){
                no.pai.esquerdo = no.direito;
                }
                if(no.pai.direito == no){
                no.pai.direito = no.direito;
                }
            }
            if(no.esquerdo != null && no.direito == null){
                no.esquerdo.pai = no.pai;
                if(no.pai.esquerdo == no){
                no.pai.esquerdo = no.esquerdo;

                }
                if(no.pai.direito == no){
                no.pai.direito = no.esquerdo;
                }
 //               this.calcularPesosNos(this.raiz);
//                no = no.pai;
//                while(no != this.raiz){
//                    if(no.getPeso() > 1){
//                        this.rotacionarParaDireita(no);
//                    }
 //                   if(no.getPeso() < -1){
 //                       this.rotacionarParaEsquerda(no);
 //                   }
  //                  no = no.pai;
 //               }
            }
            //Fim caso 2
            
            //Caso 3
            if(no.esquerdo != null && no.direito != null){
                No<K, V> noRes = this.percorrerEsquerdo(no.direito);
                if(noRes.direito != null){
                    noRes.direito.pai = noRes.pai;
                    noRes.pai.esquerdo = noRes.direito;
                }else{
                    noRes.pai.esquerdo = null;
                }
                no.setChave(noRes.getChave());
                no.setValor(noRes.getValor());
            }
            //Fim caso 3
        }
        //esquerdo
        if(no.getChave().compareTo(chave) > 0){
            if(no.esquerdo != null){
            removerNoPriv(chave, no.esquerdo);
            }
        }
        //direito
        if(no.getChave().compareTo(chave) < 0){
            if(no.direito != null){
            removerNoPriv(chave, no.direito);
            }
        }
    }
    
    public No<K, V> percorrerEsquerdo(No<K, V> no){
        if(no.esquerdo != null){
            percorrerEsquerdo(no.esquerdo);
        }
        return no;
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
    
    public void listar(){
        this.percorrerOrdem(this.raiz);
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
