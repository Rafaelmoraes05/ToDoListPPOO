package Model;

public class TarefaComPrazo implements Tarefa {
    private String titulo;
    private String descricao;
    private String prazo;
    private Status status;

    public TarefaComPrazo(String titulo, String descricao, String prazo) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.status = Status.PENDENTE;
    }

    @Override public String getTitulo() { return titulo; }
    @Override public String getDescricao() { return descricao; }
    @Override public String getPrazo() { return prazo; }
    @Override public Status getStatus() { return status; }
    @Override public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return titulo + " - " + descricao + " | Prazo: " + prazo + " | Status: " + status;
    }
}