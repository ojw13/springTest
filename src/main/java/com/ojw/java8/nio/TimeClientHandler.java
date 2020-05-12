package com.ojw.java8.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio客户端
 * @author ojw
 * @date 2020/4/8 0008
 */
@Slf4j
public class TimeClientHandler extends Thread {

    /**
     * 服务器端的ip
     */
    private String host;
    private String localhost = "127.0.0.1";
    private String str ;
    /**
     * 服务器端的端口号
     */
    private int port;
    /**
     * 多路服用选择器
     */
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandler(String host, int port,String str){
        this.host = host == null ? localhost: host;
        this.port = port;
        this.str = str;
        try {
            //初始化一个Selector，工厂方法
            selector = Selector.open();
            //初始化一个SocketChannel，工厂方法
            socketChannel = SocketChannel.open();
            //设置非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * 首先尝试连接服务端
     * @throws IOException
     */
    public void doConnect() throws IOException {
        //如果连接成功，像多路复用器selector监听读请求
        if(socketChannel.connect(new InetSocketAddress(this.host, this.port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
            //执行写操作，像服务器端发送数据
            doWrite(socketChannel);
        }else {
            //监听连接请求
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }


    public void doWrite(SocketChannel sc) throws IOException {
        //构造请求消息体
        byte [] bytes = this.str.getBytes();
        //构造ByteBuffer
        ByteBuffer write = ByteBuffer.allocate(bytes.length);
        //将消息体写入发送缓冲区
        write.put(bytes);
        write.flip();
        //调用channel的发送方法异步发送
        sc.write(write);
        //通过hasRemaining方法对发送结果进行判断，如果消息全部发送成功，则返回true
        if(!write.hasRemaining()){
            log.info("send order 2 server successd");
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        while (!stop){
            try {
                selector.select(500);
                Set<SelectionKey> keys =  selector.selectedKeys();
                Iterator<SelectionKey> its =keys.iterator();
                SelectionKey key = null;
                while (its.hasNext()){
                    key = its.next();
                    its.remove();
                    try {
                        handle(key);
                    }catch (Exception e){
                        log.error(e.getMessage());
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        //释放所有与该多路复用器selector关联的资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel sc = (SocketChannel) key.channel();
            if(key.isConnectable()){
                //如果连接成功，监听读请求
                if(sc.finishConnect()){
                    sc.register(this.selector, SelectionKey.OP_READ);
                    //像服务端发送数据
                    doWrite(sc);
                }else{
                    System.exit(1);
                }
            }
            //监听到读请求，从服务器端接受数据
            if(key.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(byteBuffer);
                if(readBytes > 0){
                    byteBuffer.flip();
                    byte []  bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    log.info("now body is ======>{}", body);
                }else if(readBytes < 0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    public void stopClient(){
        this.stop = true;
    }
}
