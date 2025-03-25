package Model;

public interface Tarefa {
    String getTitulo();
    String getDescricao();
    String getPrazo();
    Status getStatus();
    void setStatus(Status status);
    String toString();
}