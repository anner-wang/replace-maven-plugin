package com.fr;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Replace {

    private String[] origins;
    private String[] targets;

    public Replace(String[] origins, String[] targets) {
        this.origins = origins;
        this.targets = targets;
    }


    private List<String> listDir(String path, List<String> allFile) {
        File file = new File(path);
        File[] fileList = file.listFiles();
        for (File subFile : fileList) {
            String newPath = subFile.getAbsolutePath();
            File target = new File(newPath);
            if (target.isDirectory()) {
                listDir(newPath, allFile);
            } else if (newPath.endsWith("java")) {
                allFile.add(newPath);
            }
        }
        return allFile;
    }

    private void replace(String path, String origin, String targin) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        //内存流
        CharArrayWriter tempStream = new CharArrayWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            tempStream.write(line.replaceAll(origin, targin) + "\n");
        }
        reader.close();
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }

    public void run() throws MojoExecutionException, MojoFailureException {
        String path = System.getProperty("user.dir");
        if (origins.length != targets.length) {
            System.err.println("pom.xml参数数量不匹配，替换失败");
            return;
        }
        List<String> allFile = new LinkedList<String>();
        listDir(path, allFile);
        for (String s : allFile) {
            try {
                for (int i = 0; i < origins.length; i++) {
                    replace(s, origins[i], targets[i]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
