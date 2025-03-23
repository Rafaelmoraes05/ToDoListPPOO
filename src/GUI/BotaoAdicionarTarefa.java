package GUI;

import Command.Command;
import javax.swing.*;

public class BotaoAdicionarTarefa extends JButton {
    private Command command;

    public BotaoAdicionarTarefa(Command comando) {
        super("Adicionar Tarefa");
        this.command = comando;
        addActionListener(e -> comando.executar());
    }
}
