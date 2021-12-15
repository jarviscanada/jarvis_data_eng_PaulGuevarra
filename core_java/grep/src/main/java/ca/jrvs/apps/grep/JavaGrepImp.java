package ca.jrvs.apps.grep;


import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args)
    {
        if(args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

       BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        }
        catch (Exception ex)
        {

            javaGrepImp.logger.error("Error: Unable to process", ex);
        }


    }
    @Override
    public void process() throws IOException {
        ArrayList<String> linesMatched = new ArrayList<String>();
        for(File file: listFiles(getRootPath()))
        {
            for(String line : readLines(file))
            {
                if(containsPattern(line))
                {
                    linesMatched.add(line);
                }
            }
        }
        writeToFile(linesMatched);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> fileList = new ArrayList<File>();
        File rootFile = new File(rootDir);

        File[] rootFiles = rootFile.listFiles();

        if(rootFiles!=null)
        {
            for(File file: rootFiles)
            {
                if(file.isFile())
                {
                    fileList.add(file);
                }
                else if(file.isDirectory())
                {
                    fileList.addAll(listFiles(file.getAbsolutePath()));

                }
            }
        }
        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException, IOException {
        List<String> lines = new ArrayList<String>();
        String line = "";
        BufferedReader bR = new BufferedReader(new FileReader(inputFile));
        line = bR.readLine();

        while(line!=(null))
        {
            lines.add(line);
            line =bR.readLine();
        }


        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.matches(getRegex(),line);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
    FileOutputStream fileOutput = new FileOutputStream(getOutFile(),false);

    for(String line: lines)
    {
        byte b[] = line.getBytes();
        fileOutput.write(b);
        fileOutput.write("\n".getBytes());
    }
    fileOutput.close();

    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath=rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
