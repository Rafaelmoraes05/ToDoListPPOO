package GUI;

import javax.swing.*;
import java.util.List;
import Model.Tarefa;

public class PainelTarefas extends JPanel {
    private JList<String> listaTarefas;

    public PainelTarefas() {
        listaTarefas = new JList<>();
        add(new JScrollPane(listaTarefas));
    }

    public void atualizarLista(List<Tarefa> tarefas) {
        String[] dadosTarefas = tarefas.stream()
            .map(Tarefa::toString)
            .toArray(String[]::new);
        listaTarefas.setListData(dadosTarefas);
    }
}