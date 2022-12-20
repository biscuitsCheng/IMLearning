package cn.corey.demo.day1;


import cn.corey.demo.DemoConstants;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DemoFileChannel {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        readFile("/Users/biscuits/soft/IMLearning/src/main/resources/demo/file.txt");
        long cost = (System.currentTimeMillis() - begin);
        System.out.println("Cost:" + cost);

        copyFile("/Users/biscuits/soft/IMLearning/src/main/resources/demo/file.txt", "/Users/biscuits/soft/IMLearning/src/main/resources/demo/file1.txt");
    }

    private static void copyFile(String srcPath, String destPath) {
        try (FileChannel src = FileChannel.open(Paths.get(srcPath), new StandardOpenOption[]{StandardOpenOption.READ});
             FileChannel out = FileChannel.open(Paths.get(destPath), new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE})) {
            src.transferTo(0, src.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readFile(String srcPath) {
        try (FileChannel open = FileChannel.open(Paths.get(srcPath), new StandardOpenOption[]{StandardOpenOption.READ})) {
            MappedByteBuffer buffer = open.map(FileChannel.MapMode.READ_ONLY, 0L, open.size());
            StringBuilder sb = new StringBuilder();
            int byteLength = DemoConstants.BUFFER_SIZE;

            byte[] bytes = new byte[byteLength];
            while (buffer.hasRemaining()) {
                int length = Math.min(buffer.remaining(), byteLength);
                if (length != byteLength) {
                    bytes = new byte[length];
                }
                buffer.get(bytes, 0, length);
                String str = new String(bytes, StandardCharsets.UTF_8);
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
