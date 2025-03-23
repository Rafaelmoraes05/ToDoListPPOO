package Model;

public class TarefaComPrazo implements Tarefa {
    private String descricao;
    private String prazo;

    public TarefaComPrazo(String descricao, String prazo) {
        this.descricao = descricao;
        this.prazo = prazo;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Tarefa com Prazo: " + descricao + " (Prazo: " + prazo + ")");
    }

    @Override
    public String toString() {
        return "Tarefa com Prazo: " + descricao + " (Prazo: " + prazo + ")";
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }
}