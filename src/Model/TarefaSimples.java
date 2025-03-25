package Model;

public class TarefaSimples implements Tarefa {
    private String titulo;
    private String descricao;
    private Status status;

    public TarefaSimples(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = Status.PENDENTE;
    }

    @Override public String getTitulo() { return titulo; }
    @Override public String getDescricao() { return descricao; }
    @Override public String getPrazo() { return "N/A"; }
    @Override public Status getStatus() { return status; }
    @Override public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return titulo + " - " + descricao + " | Status: " + status;
    }
}