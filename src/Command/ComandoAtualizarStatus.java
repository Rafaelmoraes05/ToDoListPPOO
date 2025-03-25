package Command;

import Model.Status;
import Model.Tarefa;
import Observer.GerenciadorTarefas;

public class ComandoAtualizarStatus implements Command {
    private GerenciadorTarefas gerenciador;
    private Tarefa tarefa;
    private Status novoStatus;

    public ComandoAtualizarStatus(GerenciadorTarefas gerenciador, Tarefa tarefa, Status novoStatus) {
        this.gerenciador = gerenciador;
        this.tarefa = tarefa;
        this.novoStatus = novoStatus;
    }

    @Override
    public void executar() {
        gerenciador.atualizarStatusTarefa(tarefa, novoStatus);
    }
}