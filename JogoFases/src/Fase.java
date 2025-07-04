public class Fase {
    private int numero;
    private int numeroInimigos;
    private int velocidade;
    
    public Fase(int numero) {
        this.numero = numero;
        this.numeroInimigos = 2 + numero;
        this.velocidade = 1 + (numero - 1) / 2; 
    }
    
    public int getNumero() { 
        return numero; 
    }
    
    public int getNumeroInimigos() { 
        return numeroInimigos; 
    }
    
    public int getVelocidade() { 
        return velocidade; 
    }
}