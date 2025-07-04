import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Jogo extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    private static final int LARGURA = 800;
    private static final int ALTURA = 600;
    private static final int DELAY = 16; 
    
    private Timer timer;
    private Jogador jogador;
    private Objetivo objetivo;
    private ArrayList<Inimigo> inimigos;
    private ArrayList<ParticulasGlitter> glitters;
    private Fase faseAtual;
    private boolean jogoAtivo;
    private boolean gameOver;
    
    public Jogo() {
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.PINK);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        iniciarJogo();
    }
    
    public void iniciarJogo() {
        faseAtual = new Fase(1);
        jogoAtivo = true;
        gameOver = false;
    
        glitters = new ArrayList<>();
        
        Random rand = new Random();
        jogador = new Jogador(rand.nextInt(LARGURA - 40), rand.nextInt(ALTURA - 35));
      
        criarObjetivo();
        criarInimigos();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    private void criarObjetivo() {
        Random rand = new Random();
        int x, y;
        
        do {
            x = rand.nextInt(LARGURA - 70);
            y = rand.nextInt(ALTURA - 55);
        } while (Math.abs(x - jogador.getX()) < 100 && Math.abs(y - jogador.getY()) < 100);
        
        objetivo = new Objetivo(x, y);
    }
    
    private void criarInimigos() {
        inimigos = new ArrayList<>();
        Random rand = new Random();
        
        for (int i = 0; i < faseAtual.getNumeroInimigos(); i++) {
            int x, y;
            do {
                x = rand.nextInt(LARGURA - 35);
                y = rand.nextInt(ALTURA - 30);
            } while ((Math.abs(x - jogador.getX()) < 80 && Math.abs(y - jogador.getY()) < 80) ||
                     (Math.abs(x - objetivo.getX()) < 80 && Math.abs(y - objetivo.getY()) < 80));
            
            inimigos.add(new Inimigo(x, y, faseAtual.getVelocidade()));
        }
    }
    
    private void proximaFase() {
        faseAtual = new Fase(faseAtual.getNumero() + 1);
        jogador.aumentarVelocidade();
        criarObjetivo();
        criarInimigos();
       
        criarGlittersCelebracao();
    }
    
    private void criarGlittersDestruicao(int x, int y) {
        Random rand = new Random();
        int numeroParticulas = 15 + rand.nextInt(11);
        
        for (int i = 0; i < numeroParticulas; i++) {
            double posX = x + 17.5 + (rand.nextDouble() - 0.5) * 20;
            double posY = y + 15 + (rand.nextDouble() - 0.5) * 15;
            
            glitters.add(new ParticulasGlitter(posX, posY));
        }
    }
    
    private void criarGlittersCelebracao() {
        Random rand = new Random();
        

        for (int i = 0; i < 30; i++) {
            double posX = rand.nextDouble() * LARGURA;
            double posY = rand.nextDouble() * ALTURA / 2;
            
            glitters.add(new ParticulasGlitter(posX, posY));
        }
    }
   
    private void atualizarGlitters() {
        for (int i = glitters.size() - 1; i >= 0; i--) {
            ParticulasGlitter glitter = glitters.get(i);
            glitter.atualizar();
 
            if (!glitter.estaViva() || glitter.foraDaTela(LARGURA, ALTURA)) {
                glitters.remove(i);
            }
        }
    }
    
    private void verificarColisoes() {
        if (jogador.colideCom(objetivo)) {
            proximaFase();
            return;
        }
        
        ArrayList<RaioArcoIris> raios = jogador.getRaios();
        for (int i = inimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = inimigos.get(i);
            
            for (int j = raios.size() - 1; j >= 0; j--) {
                RaioArcoIris raio = raios.get(j);
                if (raio.colideCom(inimigo)) {
                    criarGlittersDestruicao(inimigo.getX(), inimigo.getY());
                
                    inimigos.remove(i);
                    raios.remove(j);
                    break;
                }
            }
        }
     
        for (Inimigo inimigo : inimigos) {
            if (jogador.colideCom(inimigo)) {
                gameOver = true;
                jogoAtivo = false;
                timer.stop();
                return;
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (jogoAtivo) {
            jogador.desenhar(g);
            jogador.desenharRaios(g);
            objetivo.desenhar(g);
            
            for (Inimigo inimigo : inimigos) {
                inimigo.desenhar(g);
            }

            for (ParticulasGlitter glitter : glitters) {
                glitter.desenhar(g);
            }
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("FASE: " + faseAtual.getNumero(), 10, 25);
            g.drawString("Inimigos: " + faseAtual.getNumeroInimigos(), 10, 45);
            g.drawString("Velocidade: " + jogador.getVelocidade(), 10, 65);
            
            g.drawString("Use WASD para mover", 10, ALTURA - 60);
            g.drawString("Use ESPAÇO ou CLIQUE para disparar", 10, ALTURA - 40);
            g.drawString("Mire com o mouse", 10, ALTURA - 20);
        }
        
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            String texto = "BYE BYE";
            int x = (LARGURA - fm.stringWidth(texto)) / 2;
            int y = ALTURA / 2;
            g.drawString(texto, x, y);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            texto = "Fase alcançada: " + faseAtual.getNumero();
            fm = g.getFontMetrics();
            x = (LARGURA - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, y + 30);
            
            texto = "Pressione R para reiniciar";
            fm = g.getFontMetrics();
            x = (LARGURA - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, y + 60);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (jogoAtivo) {
            jogador.mover(LARGURA, ALTURA);
            
            for (Inimigo inimigo : inimigos) {
                inimigo.mover(LARGURA, ALTURA);
            }

            atualizarGlitters();
            
            verificarColisoes();
        }
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (jogoAtivo) {
            jogador.processarTecla(e.getKeyCode(), true);
        } else if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            iniciarJogo();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (jogoAtivo) {
            jogador.processarTecla(e.getKeyCode(), false);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (jogoAtivo) {
            jogador.dispararComMouse();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if (jogoAtivo) {
            jogador.atualizarPosicaoMouse(e.getPoint());
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (jogoAtivo) {
            jogador.atualizarPosicaoMouse(e.getPoint());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo de Fases - POO");
        Jogo jogo = new Jogo();
        
        frame.add(jogo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}