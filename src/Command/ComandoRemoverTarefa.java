package Command;

import Model.Tarefa;
import Observer.GerenciadorTarefas;

public class ComandoRemoverTarefa implements Command {
    private GerenciadorTarefas gerenciador;
    private Tarefa tarefa;

    public ComandoRemoverTarefa(GerenciadorTarefas gerenciador, Tarefa tarefa) {
        this.gerenciador = gerenciador;
        this.tarefa = tarefa;
    }

    @Override
    public void executar() {
        gerenciador.removerTarefa(tarefa);
    }
}
