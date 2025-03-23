package Factory;

import Model.Tarefa;
import Model.TarefaSimples;
import Model.TarefaComPrazo;

public class FabricaTarefa {
    public static Tarefa criarTarefa(String tipo, String descricao, String prazo) {
        if (tipo.equals("Simples")) {
            return new TarefaSimples(descricao);
        } else if (tipo.equals("ComPrazo")) {
            return new TarefaComPrazo(descricao, prazo);
        }
        return null;
    }
}