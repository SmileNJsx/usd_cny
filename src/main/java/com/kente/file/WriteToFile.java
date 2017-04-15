package com.kente.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class WriteToFile {
    /**
     * 根据文件路径写入文本
     * 使用NIO
     * @throws IOException 
     */
    public static int writeByFilePath(String filePath, String content) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            FileChannel channel = fos.getChannel();
            
            ByteBuffer src = Charset.forName("utf8").encode(content);
            channel.write(src);
            
            channel.close();
            fos.close();
        } catch (IOException e) {
            return 0;
        }
        return 1;
    }
    
}
