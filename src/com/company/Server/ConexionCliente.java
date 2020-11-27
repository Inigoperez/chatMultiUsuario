package com.company.Server;

import java.io.*;
import java.net.Socket;

public class ConexionCliente extends Thread{

    Socket socket;
    String nombre;
    BufferedWriter bw ;

    ConexionCliente(Socket socket,String nombre){
        this.socket = socket;
        this.nombre = nombre;
        try{
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void enviarMensaje(String s){
        s = nombre+":"+s;
        try{
            bw.write(s);
            bw.newLine();
            bw.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String linea;
            while((linea = br.readLine()) != null){
                Server.broadcast(linea,socket);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
