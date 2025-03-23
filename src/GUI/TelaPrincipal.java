package GUI;

import Command.ComandoAdicionarTarefa;
import Command.ComandoRemoverTarefa;
import Factory.FabricaTarefa;
import Model.Tarefa;
import Observer.GerenciadorTarefas;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private GerenciadorTarefas gerenciador;
    private JPanel painelTarefas;
    private JTextField campoDescricao;
    private JTextField campoPrazo;

    public TelaPrincipal() {
        // Configurações da janela
        setTitle("ToDoListPPOO - Gerenciador de Tarefas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Inicializa o gerenciador de tarefas e o observer
        gerenciador = new GerenciadorTarefas();
        gerenciador.adicionarObserver(this::notificarUsuario);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de entrada de dados
        JPanel painelEntrada = new JPanel(new GridLayout(3, 2, 5, 5));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Nova Tarefa"));

        JLabel labelDescricao = new JLabel("Descrição:");
        campoDescricao = new JTextField();
        JLabel labelPrazo = new JLabel("Prazo (opcional):");
        campoPrazo = new JTextField();

        painelEntrada.add(labelDescricao);
        painelEntrada.add(campoDescricao);
        painelEntrada.add(labelPrazo);
        painelEntrada.add(campoPrazo);

        // Botão para adicionar tarefa
        JButton botaoAdicionar = new JButton("Adicionar Tarefa");
        botaoAdicionar.setBackground(new Color(0, 150, 0)); // Verde
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.addActionListener(this::adicionarTarefa);

        painelEntrada.add(new JLabel()); // Espaço vazio
        painelEntrada.add(botaoAdicionar);

        // Painel de tarefas (cards)
        painelTarefas = new JPanel(new WrapLayout()); // WrapLayout para organizar os cards
        painelTarefas.setBorder(BorderFactory.createTitledBorder("Tarefas Cadastradas"));
        JScrollPane scrollTarefas = new JScrollPane(painelTarefas);
        scrollTarefas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Botão para remover tarefa
        JButton botaoRemover = new JButton("Remover Tarefa Selecionada");
        botaoRemover.setBackground(new Color(150, 0, 0)); // Vermelho
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.addActionListener(this::removerTarefa);

        // Adiciona componentes ao painel principal
        painelPrincipal.add(painelEntrada, BorderLayout.NORTH);
        painelPrincipal.add(scrollTarefas, BorderLayout.CENTER);
        painelPrincipal.add(botaoRemover, BorderLayout.SOUTH);

        // Adiciona o painel principal à janela
        add(painelPrincipal);
        setVisible(true);
    }

    // Método para adicionar tarefa
    private void adicionarTarefa(ActionEvent e) {
        String descricao = campoDescricao.getText().trim();
        String prazo = campoPrazo.getText().trim();

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A descrição da tarefa é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarefa tarefa = FabricaTarefa.criarTarefa(
            prazo.isEmpty() ? "Simples" : "ComPrazo",
            descricao,
            prazo.isEmpty() ? null : prazo
        );

        ComandoAdicionarTarefa comando = new ComandoAdicionarTarefa(gerenciador, tarefa);
        comando.executar();

        // Atualiza a lista de tarefas na interface
        atualizarPainelTarefas();

        // Limpa os campos de entrada
        campoDescricao.setText("");
        campoPrazo.setText("");
    }

    // Método para remover tarefa
    private void removerTarefa(ActionEvent e) {
        int indiceSelecionado = painelTarefas.getComponentCount() > 0 ? painelTarefas.getComponentZOrder(painelTarefas.getComponent(0)) : -1;

        if (indiceSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para remover!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarefa tarefa = gerenciador.getTarefas().get(indiceSelecionado);
        ComandoRemoverTarefa comando = new ComandoRemoverTarefa(gerenciador, tarefa);
        comando.executar();

        // Atualiza a lista de tarefas na interface
        atualizarPainelTarefas();
    }

    // Método para atualizar o painel de tarefas
    private void atualizarPainelTarefas() {
        painelTarefas.removeAll();
        for (Tarefa tarefa : gerenciador.getTarefas()) {
            JPanel card = criarCardTarefa(tarefa);
            painelTarefas.add(card);
        }
        painelTarefas.revalidate();
        painelTarefas.repaint();
    }

    // Método para criar um card de tarefa
    private JPanel criarCardTarefa(Tarefa tarefa) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 100));

        // Descrição da tarefa
        JLabel labelDescricao = new JLabel(tarefa.toString());
        labelDescricao.setFont(new Font("Arial", Font.BOLD, 12));
        card.add(labelDescricao, BorderLayout.CENTER);

        // Botão para exibir detalhes (modal)
        JButton botaoDetalhes = new JButton("Detalhes");
        botaoDetalhes.setBackground(new Color(0, 120, 215)); // Azul
        botaoDetalhes.setForeground(Color.WHITE);
        botaoDetalhes.addActionListener(e -> exibirDetalhesTarefa(tarefa));

        card.add(botaoDetalhes, BorderLayout.SOUTH);

        return card;
    }

    // Método para exibir detalhes da tarefa em um modal
    private void exibirDetalhesTarefa(Tarefa tarefa) {
        JDialog modal = new JDialog(this, "Detalhes da Tarefa", true);
        modal.setSize(300, 200);
        modal.setLocationRelativeTo(this);

        JPanel painelDetalhes = new JPanel(new BorderLayout(10, 10));
        painelDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelDetalhes = new JLabel("<html><b>Descrição:</b> " + tarefa.toString() + "<br><b>Prazo:</b> " +
            (tarefa instanceof Model.TarefaComPrazo ? ((Model.TarefaComPrazo) tarefa).getPrazo() : "N/A") + "</html>");
        painelDetalhes.add(labelDetalhes, BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(e -> modal.dispose());
        painelDetalhes.add(botaoFechar, BorderLayout.SOUTH);

        modal.add(painelDetalhes);
        modal.setVisible(true);
    }

    // Método para notificar o usuário (Observer)
    private void notificarUsuario(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Notificação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}