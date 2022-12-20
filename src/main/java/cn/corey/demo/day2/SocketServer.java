//package cn.corey.demo.day2;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.*;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//
//public class SocketServer {
//    private static boolean stop = false;
//
//    public static void main(String[] args) {
//        try {
//            ServerSocketChannel socketChannel = ServerSocketChannel.open();
//            socketChannel.bind(InetSocketAddress.createUnresolved("127.0.0.1", 6666), 10);
//            while (!stop) {
//                SocketChannel channel = socketChannel.accept();
//                Socket socket = channel.socket();
//                InputStream inputStream = socket.getInputStream();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
