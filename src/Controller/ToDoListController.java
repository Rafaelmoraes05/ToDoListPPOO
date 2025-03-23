package Controller;

import GUI.PainelTarefas;
import Observer.GerenciadorTarefas;

public class ToDoListController {
    private GerenciadorTarefas gerenciador;
    private PainelTarefas painelTarefas;

    public ToDoListController(GerenciadorTarefas gerenciador, PainelTarefas painelTarefas) {
        this.gerenciador = gerenciador;
        this.painelTarefas = painelTarefas;
    }

    public void atualizarPainelTarefas() {
        painelTarefas.atualizarLista(gerenciador.getTarefas());
    }
}
