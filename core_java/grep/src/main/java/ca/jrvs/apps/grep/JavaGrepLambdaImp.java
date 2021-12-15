package ca.jrvs.apps.grep;


import org.apache.log4j.BasicConfigurator;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp{

    public static void main(String[] args) {
        if(args.length!= 3)
        {
            throw new IllegalArgumentException("Usage: JavaGrep regex rootPath outFile");
        }
        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            javaGrepLambdaImp.process();
        }
        catch(Exception e)
        {
            javaGrepLambdaImp.logger.error("Error: Process failed.", e);
        }
    }

    public List<String> readLines(File inputFiles) throws IOException{
        List<String> lines = Files.lines(Paths.get( inputFiles.getAbsolutePath()))
                .collect(Collectors.toList());

        return lines;
    }

    @Override
    public List<File> listFiles(String rootDir)
    {
        List<File> files = new ArrayList<File>();
        try{
            files = Files.walk(Paths.get(rootDir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            return files;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return files;
    }



}