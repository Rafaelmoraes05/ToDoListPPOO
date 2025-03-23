package Observer;

public class NotificacaoGUI implements Observer {
    @Override
    public void atualizar(String mensagem) {
        System.out.println("Notificação na GUI: " + mensagem);
    }
}
