package com.kente.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class TestJavaNIO {
    static void readNIO() throws IOException {
        String pathname = "D:/test.txt";
        FileInputStream fin = null;
        fin = new FileInputStream(new File(pathname));
        FileChannel channel = fin.getChannel();
        
        int capacity = 100;
        ByteBuffer bf = ByteBuffer.allocate(capacity);
        System.out.println(bf.limit()+","+bf.capacity()+","+bf.position());
        
        int length = -1;
//        System.out.println(channel.read(bf));
        while((length = channel.read(bf)) != -1) {
            System.out.println("hi");
            bf.clear();
            byte[] bytes = bf.array();
            System.out.write(bytes, 0, length);
            System.out.println();
            
            System.out.println(bf.limit()+","+bf.capacity()+","+bf.position());
        }
        
        channel.close();
        fin.close();
    }
    
    static void writeNIO() throws IOException {
        String filename = "D:/out.txt";
        FileOutputStream fos = null;
        
        fos = new FileOutputStream(new File(filename));
        FileChannel channel = fos.getChannel();
        
        ByteBuffer src = Charset.forName("utf8").encode("你好你好你好");
        System.out.println(src.capacity()+","+src.limit());
        int length = 0;
        
        while((length = channel.write(src)) != 0) {
            System.out.println(length);
        }
        channel.close();
        fos.close();
        
    }
    
    static void testReadAndWriteNIO() throws IOException {
        String pathname = "D:/test.txt";
        FileInputStream fin = null;
        String filename = "D:/out.txt";
        FileOutputStream fos = null;
        
        fin = new FileInputStream(new File(pathname));
        FileChannel channel = fin.getChannel();
        
        int capacity = 100;
        ByteBuffer bf = ByteBuffer.allocate(capacity);
        System.out.println(bf.capacity()+","+bf.limit()+","+bf.position());
        
        int length = -1;
        fos = new FileOutputStream(new File(filename));
        FileChannel outChannel = fos.getChannel();
        
        while((length = channel.read(bf)) != -1) {
            bf.flip();
            int outlength = 0;
            while((outlength = outChannel.write(bf)) != 0) {
                System.out.println("read"+length+"write"+outlength);
            }
            bf.clear();
        }
        
        channel.close();
        outChannel.close();
        fin.close();
        fos.close();
    }
    
    public static void main(String[] args) throws IOException {
//        byte[] a = "11".getBytes();
//        String s = Byte.toString(a[0]);
//        System.out.println(s);
//        readNIO();
        testReadAndWriteNIO();
    }
}
