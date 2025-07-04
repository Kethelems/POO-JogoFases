import java.awt.*;
import java.util.Random;

public class ParticulasGlitter {
    private double x, y;
    private double velocidadeX, velocidadeY;
    private double gravidade;
    private Color cor;
    private int tamanho;
    private int tempoVida;
    private int tempoVidaMaximo;
    private Random rand;
    private float brilho;
    private int fase; 
    
    public ParticulasGlitter(double x, double y) {
        this.rand = new Random();
        this.x = x;
        this.y = y;
     
        double angulo = rand.nextDouble() * 2 * Math.PI;
        double velocidade = 2 + rand.nextDouble() * 4;
        this.velocidadeX = Math.cos(angulo) * velocidade;
        this.velocidadeY = Math.sin(angulo) * velocidade - 1; 
      
        this.gravidade = 0.1;

        this.cor = gerarCorBrilhante();
      
        this.tamanho = 2 + rand.nextInt(4);
        
        this.tempoVidaMaximo = 60 + rand.nextInt(60);
        this.tempoVida = tempoVidaMaximo;
        
        this.brilho = 1.0f;
        this.fase = rand.nextInt(60);
    }
    
    private Color gerarCorBrilhante() {
        Color[] coresPossveis = {
            Color.YELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.CYAN,
            Color.MAGENTA,
            new Color(255, 215, 0), 
            new Color(192, 192, 192), 
            new Color(255, 20, 147),
            new Color(0, 255, 255),
            new Color(50, 205, 50) 
        };
        
        return coresPossveis[rand.nextInt(coresPossveis.length)];
    }
   
    public void atualizar() {
        x += velocidadeX;
        y += velocidadeY;

        velocidadeY += gravidade;
   
        velocidadeX *= 0.98;
      
        tempoVida--;
     
        fase = (fase + 1) % 60;
        brilho = 0.7f + 0.3f * (float)Math.sin(fase * Math.PI / 15);
      
        if (rand.nextInt(10) == 0) {
            velocidadeX += (rand.nextDouble() - 0.5) * 0.2;
            velocidadeY += (rand.nextDouble() - 0.5) * 0.2;
        }
    }
  
    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        float alpha = (float) tempoVida / tempoVidaMaximo;
        alpha = Math.max(0, Math.min(1, alpha * brilho));
        
        if (alpha > 0) {

            Color corComAlpha = new Color(
                cor.getRed(), 
                cor.getGreen(), 
                cor.getBlue(), 
                (int)(255 * alpha)
            );
            
            g2d.setColor(corComAlpha);
          
            desenharEstrela(g2d, (int)x, (int)y, tamanho);
  
            if (alpha > 0.5f) {
                g2d.setColor(new Color(255, 255, 255, (int)(128 * alpha)));
                g2d.fillOval((int)x - 1, (int)y - 1, 2, 2);
            }
        }
    }
    
    private void desenharEstrela(Graphics2D g2d, int cx, int cy, int tamanho) {
        int[] x_pontos = new int[8];
        int[] y_pontos = new int[8];
    
        for (int i = 0; i < 8; i++) {
            double angulo = Math.PI * i / 4.0;
            int raio = (i % 2 == 0) ? tamanho : tamanho / 2;
            x_pontos[i] = (int) (cx + raio * Math.cos(angulo));
            y_pontos[i] = (int) (cy + raio * Math.sin(angulo));
        }
        
        g2d.fillPolygon(x_pontos, y_pontos, 8);
    }
    
    public boolean estaViva() {
        return tempoVida > 0;
    }
   
    public boolean foraDaTela(int larguraTela, int alturaTela) {
        return x < -10 || x > larguraTela + 10 || y > alturaTela + 10;
    }
   
    public double getX() { return x; }
    public double getY() { return y; }
    public int getTempoVida() { return tempoVida; }
}