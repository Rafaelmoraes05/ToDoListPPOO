package Observer;

import Model.Tarefa;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorTarefas {
    private List<Tarefa> tarefas = new ArrayList<>();
    private List<Observer> observadores = new ArrayList<>();

    public void adicionarObserver(Observer observer) {
        observadores.add(observer);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        notificarObservadores("Nova tarefa adicionada: " + tarefa.toString());
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
        notificarObservadores("Tarefa removida: " + tarefa.toString());
    }

    private void notificarObservadores(String mensagem) {
        for (Observer observer : observadores) {
            observer.atualizar(mensagem);
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }
}