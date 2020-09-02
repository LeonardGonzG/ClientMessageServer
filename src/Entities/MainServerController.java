package Entities;

import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextPane;

/**
 *
 * @author Leonardo González G.
 */
public class MainServerController implements Runnable {

    private SocketController controllerServer;
    private Thread theThread = null;
    private JTextPane privateMessages;
    private JTextPane publicMessages;
    private JList<String> userNames;
    private JLabel numUsers;

    private String resp = "";
    private String nameUser = "";

    public MainServerController(String name, int port) {
        try {
            controllerServer = new SocketController(name, port);
            controllerServer.readText();
        } catch (IOException ex) {
            System.out.println("Falla en conexion con el servidor: " + name + ":" + port);
        }
    }

    public Thread loadDataServer(JTextPane privateMessages, JTextPane publicMessages, JList<String> userNames, JLabel numUser, String nameUser) {

        this.privateMessages = privateMessages;
        this.publicMessages = publicMessages;
        this.userNames = userNames;
        this.numUsers = numUser;
        this.nameUser = nameUser;

        if (theThread == null) {
            this.theThread = new Thread((Runnable) this);
            theThread.start();
        }
        return theThread;
    }

    @Override
    public void run() {

        while (true) {
            resp = this.controllerServer.readText();

            System.out.println("" + resp); // Leer secuencialmente en la 

            if (resp.startsWith("4010")) { // Mensaje  privados a recibir

                String message = resp.substring(5).trim();
                this.privateMessages.replaceSelection(message + "\n");

            } else if (resp.startsWith("5000")) { //Listado de nombre actualizado

                DefaultListModel<String> model = new DefaultListModel<>();
                String[] listNameUsers = resp.substring(5).split(";");

                for (String name : listNameUsers) {

                    String nameList = name.trim();
                    if (!nameList.equals(this.nameUser.toLowerCase())) {
                        model.addElement(nameList);
                    }

                }
                userNames.setModel(model);
                this.numUsers.setText("" + ((listNameUsers.length)-1));

            } else if (resp.startsWith("2010")) { // Mensajes publicos a recibir

                String message = resp.substring(5).trim();
                this.publicMessages.replaceSelection(message + "\n");
            }

        }

    }

    public Thread getThread() {
        return this.theThread;
    }

    public String getResponse() {
        return resp;
    }

    public String writeReadText(String command) {
        return controllerServer.writeReadText(command);
    }

    public void updateUsers(String command) {
        this.controllerServer.writeText(command);
    }
}
