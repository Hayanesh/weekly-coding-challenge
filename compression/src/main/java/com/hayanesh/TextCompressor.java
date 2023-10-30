package com.hayanesh;

import com.hayanesh.codec.Codec;
import com.igormaznitsa.jbbp.utils.JBBPUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class TextCompressor {
    private final SystemHelper system;
    private final Codec codec;

    public static TextCompressor usingCodec(Codec codec){
       return new TextCompressor(new SystemHelper(), codec);
    }

    private TextCompressor(SystemHelper system, Codec codec) {
        this.system = system;
        this.codec = codec;
    }

    public void compress(Path filePath) throws IOException {
        String content = system.readFile(filePath);
        byte[] compressedBytes = codec.encode(content);

        int in = content.getBytes().length;
        int out = compressedBytes.length;
        log.info("Compression Ratio : {} [IN : {} bytes, OUT : {} bytes]", compressionRation(in, out) , in, out);

        saveCompressedText(compressedBytes, filePath);
    }
    public String decompress(Path filePath) {
        byte[] compressedBytes = system.readBytesFromFile(filePath);
        return codec.decode(compressedBytes);
    }

    private void saveCompressedText(byte[] content, Path filePath) throws IOException {
        String location = system.parentPath(filePath);
        String fileName = system.fileName(filePath);
        system.writeFile(Path.of(location,  fileName + ".hf"), content);
    }

    private static double compressionRation(double in , double out){
        return (double) Math.round((in / out) * 100) / 100;
    }

    static class SystemHelper {
        public String readFile(Path path)  {

            try(var bufferedReader = Files.newBufferedReader(path)) {
                String line;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder
                            .append(line)
                            .append("\n");
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                throw new IllegalStateException(path + " no such file or directory", e);
            }
        }

        public byte[] readBytesFromFile(Path path){
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        public void writeFile(Path path, byte[] content) throws IOException {
            if(deleteAndCreateFile(path)){
               Files.write(path, content);
            }
        }

        private String parentPath(Path filePath){
            return filePath.getParent().toString();
        }

        private String fileName(Path filePath){
            return filePath.getFileName().toString().split("\\.")[0];
        }

        private boolean deleteAndCreateFile(Path path) throws IOException {
            if(path.toFile().exists()){
                Files.delete(path);
            }

            return new File(path.toString()).createNewFile();
        }
    }
}
