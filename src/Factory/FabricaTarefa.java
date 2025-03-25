package Factory;

import Model.Tarefa;
import Model.TarefaComPrazo;
import Model.TarefaSimples;

public class FabricaTarefa {
    public static Tarefa criarTarefa(String tipo, String titulo, String descricao, String prazo) {
        if (tipo.equals("Simples")) {
            return new TarefaSimples(titulo, descricao);
        } else if (tipo.equals("ComPrazo")) {
            return new TarefaComPrazo(titulo, descricao, prazo);
        }
        return null;
    }
}