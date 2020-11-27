package com.company.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<ConexionCliente> clientesConectados = new ArrayList();

    public static void broadcast(String s,Socket socket){
        for(ConexionCliente cliente : clientesConectados){
            if(cliente.socket != socket) {
                cliente.enviarMensaje(s);
            }
        }
    }

    public static void main(String[] args) {

        int puerto = 1234;
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(puerto);

            while (true) {
                Socket socket = servidor.accept();
                System.out.println("Conexion realizada");

                ConexionCliente cc = new ConexionCliente(socket,socket.getInetAddress().toString());
                cc.start();
                clientesConectados.add(cc);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        } finally{
            try {
                servidor.close();
            } catch (IOException ex) {
               System.out.println(ex);
            }
        }
    }
}