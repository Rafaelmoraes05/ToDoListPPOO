package GUI;

import Command.ComandoAdicionarTarefa;
import Command.ComandoAtualizarStatus;
import Command.ComandoRemoverTarefa;
import Factory.FabricaTarefa;
import Model.Status;
import Model.Tarefa;
import Observer.GerenciadorTarefas;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private GerenciadorTarefas gerenciador;
    private JPanel painelTarefas;
    private JTextField campoTitulo;
    private JTextField campoDescricao;
    private JTextField campoPrazo;
    private JPanel cardSelecionado = null;

    public TelaPrincipal() {
        // Configurações da janela
        setTitle("ToDoListPPOO - Gerenciador de Tarefas");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa o gerenciador e observer
        gerenciador = new GerenciadorTarefas();
        gerenciador.adicionarObserver(this::notificarUsuario);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de entrada
        JPanel painelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Nova Tarefa"));

        // Componentes de entrada
        painelEntrada.add(new JLabel("Título:"));
        campoTitulo = new JTextField();
        painelEntrada.add(campoTitulo);

        painelEntrada.add(new JLabel("Descrição:"));
        campoDescricao = new JTextField();
        painelEntrada.add(campoDescricao);

        painelEntrada.add(new JLabel("Prazo:"));
        campoPrazo = new JTextField();
        painelEntrada.add(campoPrazo);

        // Botão de adicionar
        JButton botaoAdicionar = new JButton("Adicionar Tarefa");
        botaoAdicionar.setBackground(new Color(46, 125, 50));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.addActionListener(this::adicionarTarefa);
        painelEntrada.add(new JLabel());
        painelEntrada.add(botaoAdicionar);

        // Painel de tarefas
        painelTarefas = new JPanel(new WrapLayout());
        painelTarefas.setBorder(BorderFactory.createTitledBorder("Tarefas Cadastradas"));
        JScrollPane scrollTarefas = new JScrollPane(painelTarefas);
        scrollTarefas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Botão de remover
        JButton botaoRemover = new JButton("Remover Tarefa Selecionada");
        botaoRemover.setBackground(new Color(198, 40, 40));
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.addActionListener(this::removerTarefa);

        // Montagem do layout
        painelPrincipal.add(painelEntrada, BorderLayout.NORTH);
        painelPrincipal.add(scrollTarefas, BorderLayout.CENTER);
        painelPrincipal.add(botaoRemover, BorderLayout.SOUTH);

        add(painelPrincipal);
        setVisible(true);
    }

    private void adicionarTarefa(ActionEvent e) {
        String titulo = campoTitulo.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String prazo = campoPrazo.getText().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O título é obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Tarefa tarefa = FabricaTarefa.criarTarefa(
            prazo.isEmpty() ? "Simples" : "ComPrazo",
            titulo,
            descricao,
            prazo.isEmpty() ? null : prazo
        );

        new ComandoAdicionarTarefa(gerenciador, tarefa).executar();
        atualizarPainelTarefas();
        
        campoTitulo.setText("");
        campoDescricao.setText("");
        campoPrazo.setText("");
    }

    private void removerTarefa(ActionEvent e) {
        if (cardSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione uma tarefa clicando nela antes de remover!", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Obtém a Tarefa associada ao card selecionado
        Tarefa tarefaSelecionada = (Tarefa) cardSelecionado.getClientProperty("tarefa");
    
        if (tarefaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Erro ao obter a tarefa selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente remover a tarefa:\n" + tarefaSelecionada.getTitulo() + "?",
            "Confirmar Remoção",
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacao == JOptionPane.YES_OPTION) {
            new ComandoRemoverTarefa(gerenciador, tarefaSelecionada).executar();
            cardSelecionado = null;
            atualizarPainelTarefas();
        }
    }

    private void atualizarPainelTarefas() {
        painelTarefas.removeAll();
        for (Tarefa tarefa : gerenciador.getTarefas()) {
            painelTarefas.add(criarCardTarefa(tarefa));
        }
        painelTarefas.revalidate();
        painelTarefas.repaint();
    }

    private JPanel criarCardTarefa(Tarefa tarefa) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(getCorStatus(tarefa.getStatus()), 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(250, 150));
    
        // Armazena a referência da tarefa dentro do JPanel
        card.putClientProperty("tarefa", tarefa);
    
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarCard(card);
            }
        });
    
        JLabel lblTitulo = new JLabel(tarefa.getTitulo());
        lblTitulo.setFont(new Font("Sans-serif", Font.BOLD, 14));
    
        String html = "<html><div style='margin-top:5px;'>" +
                     "<b>Descrição:</b> " + tarefa.getDescricao() + "<br>" +
                     "<b>Prazo:</b> " + (tarefa.getPrazo() != null ? tarefa.getPrazo() : "N/A") + "<br>" +
                     "<b>Status:</b> " + tarefa.getStatus() +
                     "</div></html>";
        JLabel lblDetalhes = new JLabel(html);
    
        JButton btnDetalhes = new JButton("Alterar Status");
        btnDetalhes.setBackground(new Color(33, 150, 243));
        btnDetalhes.setForeground(Color.WHITE);
        btnDetalhes.addActionListener(e -> mostrarDialogoStatus(tarefa));
    
        JPanel contentPanel = new JPanel(new BorderLayout(5, 10));
        contentPanel.add(lblTitulo, BorderLayout.NORTH);
        contentPanel.add(lblDetalhes, BorderLayout.CENTER);
        contentPanel.add(btnDetalhes, BorderLayout.SOUTH);
    
        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    private void selecionarCard(JPanel card) {
        if (cardSelecionado != null) {
            cardSelecionado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(getCorStatus(((Tarefa) cardSelecionado.getClientProperty("tarefa")).getStatus()), 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }
    
        cardSelecionado = card;
    
        if (cardSelecionado == null || cardSelecionado.getParent() == null) {
            return;
        }
    
        Container parent = cardSelecionado.getParent();
        int index = parent.getComponentZOrder(cardSelecionado);
    
        if (index == -1) {
            return;
        }
    
        cardSelecionado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    private void mostrarDialogoStatus(Tarefa tarefa) {
        JDialog dialog = new JDialog(this, "Alterar Status", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JComboBox<Status> comboStatus = new JComboBox<>(Status.values());
        comboStatus.setSelectedItem(tarefa.getStatus());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            Status novoStatus = (Status) comboStatus.getSelectedItem();
            new ComandoAtualizarStatus(gerenciador, tarefa, novoStatus).executar();
            atualizarPainelTarefas();
            dialog.dispose();
        });

        panel.add(new JLabel("Selecione o novo status:"), BorderLayout.NORTH);
        panel.add(comboStatus, BorderLayout.CENTER);
        panel.add(btnSalvar, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private Color getCorStatus(Status status) {
        switch (status) {
            case PENDENTE: return new Color(239, 154, 154); // Vermelho claro
            case FAZENDO: return new Color(255, 245, 157); // Amarelo claro
            case CONCLUIDO: return new Color(165, 214, 167); // Verde claro
            default: return Color.LIGHT_GRAY;
        }
    }

    private void notificarUsuario(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Notificação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal());
    }
}