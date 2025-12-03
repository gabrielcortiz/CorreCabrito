import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Dimension;


public class JanelaJogo extends javax.swing.JFrame {
    private AreaDeJogo jogo;
    private JButton botao0;
    private JButton botao1;
    private JButton botao2;
    private JButton botao3;
    private JButton botao4;
    private JButton botao5;
    private JPanel painelPrincipal;
    private JLabel statusLabel;


    public JanelaJogo(){
        setSize(1200,800);
        //setContentPane(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jogo = new AreaDeJogo();

        // 3. Configurar Menus
        JMenuBar menurbar = new JMenuBar();
        JMenu menujogo = new JMenu("Jogo");
        JMenu menuautoria = new JMenu("Autoria");

        setJMenuBar(menurbar);
        menurbar.add(menujogo);
        menurbar.add(menuautoria);

        JMenuItem itemSair = new JMenuItem("Sair");
        menujogo.add(itemSair);

        JMenuItem itemReiniciar = new JMenuItem("Reiniciar");
        menujogo.add(itemReiniciar);

        JMenuItem itemVerNomes = new JMenuItem("Ver nomes");
        menuautoria.add(itemVerNomes);

        // 4. Ações dos Menus
        itemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });

        itemReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jogo = new AreaDeJogo();
                atualizarInterface();
                atualizarStatus();
            }
        });

        itemVerNomes.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null,"Gabriel Camargo Ortiz \n Bernardo Gomes Dorneles");
            }
        });


        setLayout(new BorderLayout());

        // Inicializamos o texto
        statusLabel = new JLabel("Clique em Reiniciar para começar.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Deixei maior para ver melhor

        // Adicionamos o Painel de botões no CENTRO
        add(painelPrincipal, BorderLayout.CENTER);

        // Adicionamos o texto no RODAPÉ (Sul)
        add(statusLabel, BorderLayout.SOUTH);

        // (Removemos a linha 'painelPrincipal.add(statusLabel)' que causava o erro)

        // 6. Configurar Botões
        configurarBotao(botao0,0);
        configurarBotao(botao1,1);
        configurarBotao(botao2,2);
        configurarBotao(botao3,3);
        configurarBotao(botao4,4);
        configurarBotao(botao5,5);

        // 7. Atualização Final
        atualizarStatus();
        atualizarInterface();

        //pack();
        setLocationRelativeTo(null);
    }

    // método auxiliar
    private void configurarBotao(JButton botao, int posicao){
        botao.setBackground(Color.LIGHT_GRAY);
        botao.setForeground(Color.BLACK);

        botao.setFont(new Font("Arial", Font.BOLD, 40));

        botao.setPreferredSize(new Dimension(250, 150));
        botao.setFocusPainted(false);
        botao.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 2, true));

        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    boolean antes = jogo.getSuperPuloJaUsado();
                    jogo.tentarMover(posicao);
                    if (!antes && jogo.getSuperPuloJaUsado()){
                        JOptionPane.showMessageDialog(null, "Super Pulo utilizado! O cabrito se cansou .");
                    }

                } catch (MovimentoInvalidoException e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());

                } catch (CapturaException e) {
                    String mensagem = e.getMessage() + "\nTotal de jogadas: " + jogo.getTotalDeJogadas();
                    JOptionPane.showMessageDialog(null,mensagem);
                    jogo = new AreaDeJogo();
                }
                atualizarInterface();
                atualizarStatus();
            }
        });
    }

    private void atualizarInterface() {
        JButton[] botoes = {botao0, botao1, botao2, botao3, botao4, botao5};
        int[][] matriz = jogo.getMatrizAdjacencia();

        int posicaoAtual;
        if (jogo.getTurnoAtual() == Turno.CABRITO) {
            posicaoAtual = jogo.getPosicaoCabrito();
        } else {
            posicaoAtual = jogo.getPosicaoCarcara();
        }

        for (int i = 0; i< botoes.length; i ++){
            botoes[i].setText("");
            botoes[i].setBackground(Color.LIGHT_GRAY);
            botoes[i].setBorder(new javax.swing.border.LineBorder(Color.BLACK, 2, true));

            if (matriz[posicaoAtual][i] == 1){
                botoes[i].setBackground(new Color(144,238,144));
            }
        }


        int posCabrito = jogo.getPosicaoCabrito();
        int posCarcara = jogo.getPosicaoCarcara();

        if (botoes[posCabrito] != null) {
            botoes[posCabrito].setText("Cabrito");
        }

        if (botoes[posCarcara] != null) {
            botoes[posCarcara].setText("Carcara");
        }
        repaint();
    }

    private void atualizarStatus() {
        String texto = "Próxima jogada: ";

        // Verifica de quem é a vez e monta a mensagem
        if (jogo.getTurnoAtual() == Turno.CABRITO) {
            texto += "CABRITO (C)";
        } else {
            texto += "CARCARÁ (A)";
        }

        statusLabel.setText(texto);
    }

}
