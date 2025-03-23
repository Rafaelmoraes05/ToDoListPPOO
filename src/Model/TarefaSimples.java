package Model;

public class TarefaSimples implements Tarefa {
    private String descricao;

    public TarefaSimples(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Tarefa Simples: " + descricao);
    }
}