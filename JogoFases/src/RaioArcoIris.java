import java.awt.*;

public class RaioArcoIris extends Personagem {
    private static final int VELOCIDADE = 12;
    private static final int TEMPO_VIDA = 120;
    
    private double velocidadeX, velocidadeY;
    private int tempoVida;
    private int fase;
  
    public RaioArcoIris(int x, int y, double direcaoX, double direcaoY) {
        super(x, y, 12, 8, Color.YELLOW);
      
        this.velocidadeX = direcaoX * VELOCIDADE;
        this.velocidadeY = direcaoY * VELOCIDADE;
        
        this.tempoVida = TEMPO_VIDA;
        this.fase = 0;
    }

    public RaioArcoIris(int x, int y, int direcaoX, int direcaoY) {
        super(x, y, 12, 8, Color.YELLOW);

        if (direcaoX != 0 && direcaoY != 0) {
            double fator = 1.0 / Math.sqrt(2);
            this.velocidadeX = direcaoX * VELOCIDADE * fator;
            this.velocidadeY = direcaoY * VELOCIDADE * fator;
        } else {
            this.velocidadeX = direcaoX * VELOCIDADE;
            this.velocidadeY = direcaoY * VELOCIDADE;
        }
        
        this.tempoVida = TEMPO_VIDA;
        this.fase = 0;
    }
    
    @Override
    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color[] cores = getCoresArcoIris();
 
        for (int i = 0; i < cores.length; i++) {
            g2d.setColor(cores[i]);
            int offset = i;
            g2d.fillOval(x - offset, y - offset, largura + (offset * 2), altura + (offset * 2));
        }

        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 2, y + 1, largura - 4, altura - 2);

        desenharRastro(g2d);

        fase = (fase + 1) % 60;
    }
 
    private Color[] getCoresArcoIris() {
        float hue = (fase / 60.0f) % 1.0f;
        
        Color cor1 = Color.getHSBColor(hue, 0.8f, 1.0f);
        Color cor2 = Color.getHSBColor((hue + 0.2f) % 1.0f, 0.6f, 0.9f);
        Color cor3 = Color.getHSBColor((hue + 0.4f) % 1.0f, 0.4f, 0.8f);
        
        return new Color[]{cor3, cor2, cor1};
    }
    
    private void desenharRastro(Graphics2D g2d) {
        for (int i = 1; i <= 3; i++) {
            int rastroX = x - (int)(velocidadeX * i / 2);
            int rastroY = y - (int)(velocidadeY * i / 2);

            float alpha = 0.3f - (i * 0.1f);
            if (alpha > 0) {
                Color corRastro = new Color(1.0f, 1.0f, 1.0f, alpha);
                g2d.setColor(corRastro);
                g2d.fillOval(rastroX, rastroY, largura - i, altura - i);
            }
        }
    }

    public void mover() {
        x += (int)velocidadeX;
        y += (int)velocidadeY;
        tempoVida--;
    }
    
    public boolean estaVivo() {
        return tempoVida > 0;
    }

    public boolean foraDaTela(int larguraTela, int alturaTela) {
        return x < -largura || x > larguraTela || y < -altura || y > alturaTela;
    }
    
    public boolean colideCom(Personagem outro) {
        int centroX = x + largura / 2;
        int centroY = y + altura / 2;
        int outroCentroX = outro.getX() + outro.getLargura() / 2;
        int outroCentroY = outro.getY() + outro.getAltura() / 2;
        
        double distancia = Math.sqrt(
            Math.pow(centroX - outroCentroX, 2) + 
            Math.pow(centroY - outroCentroY, 2)
        );
        
        double raioColisao = (Math.min(largura, altura) + Math.min(outro.getLargura(), outro.getAltura())) / 4.0;
        
        return distancia < raioColisao;
    }
 
    public float getPorcentagemVida() {
        return (float) tempoVida / TEMPO_VIDA;
    }
    
    public double getVelocidadeX() { return velocidadeX; }
    public double getVelocidadeY() { return velocidadeY; }
    public int getTempoVida() { return tempoVida; }
}