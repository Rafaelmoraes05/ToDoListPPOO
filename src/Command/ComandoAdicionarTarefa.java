package Command;

import Model.Tarefa;
import Observer.GerenciadorTarefas;

public class ComandoAdicionarTarefa implements Command {
    private GerenciadorTarefas gerenciador;
    private Tarefa tarefa;

    public ComandoAdicionarTarefa(GerenciadorTarefas gerenciador, Tarefa tarefa) {
        this.gerenciador = gerenciador;
        this.tarefa = tarefa;
    }

    @Override
    public void executar() {
        gerenciador.adicionarTarefa(tarefa);
    }
}
