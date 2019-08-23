package question1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class GroupeDeContributeurs extends Cotisant implements Iterable<Cotisant>{
  private List<Cotisant> liste;
  
  public GroupeDeContributeurs(String nomDuGroupe){
    super(nomDuGroupe);
    // a completer
    this.liste = new ArrayList<Cotisant>();
  }
  
  public void ajouter(Cotisant cotisant){
    // a completer
    if(!liste.contains(cotisant)){
        liste.add(cotisant);
        cotisant.setParent(this);
    }
  }
  
  
  public int nombreDeCotisants(){
    int nombre = 0;
    // a completer
        Iterator<Cotisant> i = liste.iterator();
     while(i.hasNext()){     
       Cotisant cot = i.next();
       if(cot instanceof Contributeur){
           nombre ++;
        }
        else{ nombre += cot.nombreDeCotisants();}
        
    }    
    
    return nombre;
  }
  
  public String toString(){
    String str = new String();
    // a completer
    for(Cotisant cot :liste){
        str+=cot.toString()+" ";
    }
    
    return str;
  }
  
  public List<Cotisant> getChildren(){
    return this.liste;
    // a completer
  }
  
  public void debit(int somme) throws SoldeDebiteurException,RuntimeException{
      if(somme<0)throw new RuntimeException("somme négative !!!");
      for(Cotisant cot :liste){
       try{                             
        cot.debit(somme); 
         } 
         catch( SoldeDebiteurException e){throw new SoldeDebiteurException();} 
       
        }
        
  }
  
  public void credit(int somme){
    // a completer
    
     if(somme<0)throw new RuntimeException("somme négative !!!");
     
       for(Cotisant cot:liste){
           cot.credit(somme);
        }
  }
  
  public int solde(){
    // a completer
    int solde = 0;
   for(Cotisant cot: liste)
    solde+=cot.solde();
            
    return solde;
  }
  
  // mÃ©thodes fournies
  
 public Iterator<Cotisant> iterator(){
    return new GroupeIterator(liste.iterator());
  }
  
  private class GroupeIterator implements Iterator<Cotisant>{
    private Stack<Iterator<Cotisant>> stk;
    
    public GroupeIterator(Iterator<Cotisant> iterator){
      this.stk = new Stack<Iterator<Cotisant>>();
      this.stk.push(iterator);
    }
    
    public boolean hasNext(){
      if(stk.empty()){
        return false;
      }else{
         Iterator<Cotisant> iterator = stk.peek();
         if( !iterator.hasNext()){
           stk.pop();
           return hasNext();
         }else{
           return true;
         }
      }
    }
    
    public Cotisant next(){
      if(hasNext()){
        Iterator<Cotisant> iterator = stk.peek();
        Cotisant cpt = iterator.next();
        if(cpt instanceof GroupeDeContributeurs){
          GroupeDeContributeurs gr = (GroupeDeContributeurs)cpt;
          stk.push(gr.liste.iterator());
        }
        return cpt;
      }else{
        throw new NoSuchElementException();
      }
    }
    public void remove(){
      throw new UnsupportedOperationException();
    }
  }
  

  public <T> T accepter(Visiteur<T> visiteur){
    return visiteur.visite(this);
  }
  

}
