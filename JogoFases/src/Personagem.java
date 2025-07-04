import java.awt.*;

public abstract class Personagem {
    protected int x, y;
    protected int largura, altura;
    protected Color cor;
    
    public Personagem(int x, int y, int largura, int altura, Color cor) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.cor = cor;
    }
    
    public abstract void desenhar(Graphics g);
  
    public boolean colideCom(Personagem outro) {
        Rectangle rect1 = new Rectangle(x, y, largura, altura);
        Rectangle rect2 = new Rectangle(outro.x, outro.y, outro.largura, outro.altura);
        return rect1.intersects(rect2);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getLargura() { return largura; }
    public int getAltura() { return altura; }
}