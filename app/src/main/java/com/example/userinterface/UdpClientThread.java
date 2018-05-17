package com.example.userinterface;

import android.os.Message;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientThread {

    String dstAddress, mes1;
    int dstPort;
    private boolean running;
    Tab2Fragment.UdpClientHandler handler;
    TextView textViewState;

    DatagramSocket socket;
    InetAddress address;

    public UdpClientThread(String addr, String mes, Tab2Fragment.UdpClientHandler handler) {
        super();
        dstAddress = addr;
        dstPort = 1024;
        mes1 = mes;
        this.handler = handler;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    private void sendState(String state){
        handler.sendMessage(
                Message.obtain(handler,
                        Tab2Fragment.UdpClientHandler.UPDATE_STATE, state));
    }

    public void run() {
        sendState("connecting...");

        running = true;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(dstAddress);

            // send request
            byte[] buf = new byte[256];
            //String mes = "Hi";
            buf = mes1.getBytes();

            DatagramPacket packet =
                    new DatagramPacket(buf, buf.length, address, dstPort);
            socket.send(packet);

            sendState("connected");

            // get response
            //packet = new DatagramPacket(buf, buf.length);
            //socket.receive(packet);

            // String line = new String(packet.getData(), 0, packet.getLength());

            //  handler.sendMessage(
            //        Message.obtain(handler, Tab2Fragment.UdpClientHandler.UPDATE_MSG, line));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                socket.close();
                handler.sendEmptyMessage(Tab2Fragment.UdpClientHandler.UPDATE_END);
            }
        }

    }

}
