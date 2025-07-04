import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Jogador extends Personagem {
    private static final int VELOCIDADE_BASE = 3;
    private static final int COOLDOWN_DISPARO = 15;
    
    private int velocidade;
    private boolean[] teclas;
    private ArrayList<RaioArcoIris> raios;
    private int cooldownAtual;
    private Point posicaoMouse;
    
    public Jogador(int x, int y) {
        super(x, y, 40, 35, Color.WHITE);
        this.velocidade = VELOCIDADE_BASE;
        this.teclas = new boolean[256];
        this.raios = new ArrayList<>();
        this.cooldownAtual = 0;
        this.posicaoMouse = new Point(x + largura/2, y);
    }
    
    @Override
    public void desenhar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 8, y + 15, 24, 16);
    
        g2d.fillOval(x + 5, y + 8, 18, 15);
        
        g2d.setColor(Color.YELLOW);
        int[] chifre_x = {x + 14, x + 12, x + 16};
        int[] chifre_y = {y + 2, y + 10, y + 10};
        g2d.fillPolygon(chifre_x, chifre_y, 3);
        
        g2d.setColor(Color.ORANGE);
        g2d.drawLine(x + 13, y + 4, x + 15, y + 6);
        g2d.drawLine(x + 13, y + 6, x + 15, y + 8);
   
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 10, y + 28, 4, 6);
        g2d.fillRect(x + 16, y + 28, 4, 6);
        g2d.fillRect(x + 22, y + 28, 4, 6);
        g2d.fillRect(x + 28, y + 28, 4, 6);
      
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(x + 2, y + 5, 6, 8);
        g2d.setColor(Color.CYAN);
        g2d.fillOval(x + 4, y + 3, 6, 8);
        g2d.setColor(Color.PINK);
        g2d.fillOval(x + 6, y + 4, 6, 8);
      
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(x + 32, y + 18, 6, 10);
        g2d.setColor(Color.CYAN);
        g2d.fillOval(x + 34, y + 20, 6, 8);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + 8, y + 12, 2, 2);
        g2d.fillOval(x + 12, y + 12, 2, 2);
    
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
        int chifre_x_centro = x + 14;
        int chifre_y_centro = y + 6;
        g2d.drawLine(chifre_x_centro, chifre_y_centro, posicaoMouse.x, posicaoMouse.y);
   
        g2d.setStroke(new BasicStroke(1.0f)); 
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawOval(x + 8, y + 15, 24, 16); 
        g2d.drawOval(x + 5, y + 8, 18, 15);  
    }
    
    public void processarTecla(int keyCode, boolean pressionada) {
        teclas[keyCode] = pressionada;
    }
    
  
    public void atualizarPosicaoMouse(Point novaPosicao) {
        this.posicaoMouse = novaPosicao;
    }
    
    public void mover(int larguraJanela, int alturaJanela) {
        int deltaX = 0, deltaY = 0;
    
        if (teclas[KeyEvent.VK_W] || teclas[KeyEvent.VK_UP]) {
            deltaY -= velocidade;
        }
        if (teclas[KeyEvent.VK_S] || teclas[KeyEvent.VK_DOWN]) {
            deltaY += velocidade;
        }
        if (teclas[KeyEvent.VK_A] || teclas[KeyEvent.VK_LEFT]) {
            deltaX -= velocidade;
        }
        if (teclas[KeyEvent.VK_D] || teclas[KeyEvent.VK_RIGHT]) {
            deltaX += velocidade;
        }
        
        if (deltaX != 0 && deltaY != 0) {

            double fator = 1.0 / Math.sqrt(2);
            deltaX = (int) (deltaX * fator);
            deltaY = (int) (deltaY * fator);
        }
        x += deltaX;
        y += deltaY;
       
        x = Math.max(0, Math.min(x, larguraJanela - largura));
        y = Math.max(0, Math.min(y, alturaJanela - altura));
     
        if (cooldownAtual > 0) {
            cooldownAtual--;
        }
      
        if (teclas[KeyEvent.VK_SPACE] && cooldownAtual == 0) {
            disparar();
            cooldownAtual = COOLDOWN_DISPARO;
        }
     
        for (int i = raios.size() - 1; i >= 0; i--) {
            RaioArcoIris raio = raios.get(i);
            raio.mover();
            
            if (!raio.estaVivo() || raio.foraDaTela(larguraJanela, alturaJanela)) {
                raios.remove(i);
            }
        }
    }
   
    private void disparar() {
        int chifre_x = x + largura / 2;
        int chifre_y = y + 10;
     
        double deltaX = posicaoMouse.x - chifre_x;
        double deltaY = posicaoMouse.y - chifre_y;
       
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  
        if (distancia < 1) {
            deltaX = 1; 
            deltaY = 0;
            distancia = 1;
        }
     
        double direcaoX = deltaX / distancia;
        double direcaoY = deltaY / distancia;
        
        raios.add(new RaioArcoIris(chifre_x, chifre_y, direcaoX, direcaoY));
    }
   
    public void dispararComMouse() {
        if (cooldownAtual == 0) {
            disparar();
            cooldownAtual = COOLDOWN_DISPARO;
        }
    }
    
    public void desenharRaios(Graphics g) {
        for (RaioArcoIris raio : raios) {
            raio.desenhar(g);
        }
    }
    
    public ArrayList<RaioArcoIris> getRaios() {
        return raios;
    }
    
    public void aumentarVelocidade() {
        velocidade = Math.min(velocidade + 1, VELOCIDADE_BASE + 10);
    }
    
    public int getVelocidade() {
        return velocidade;
    }
    
    public Point getPosicaoMouse() {
        return posicaoMouse;
    }
}