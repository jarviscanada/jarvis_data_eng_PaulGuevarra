#Introduction

The Java Grep Application is an application to mimic the Linux grep command, searching files in the directory, and regex pattern matching with strings within the files. This application includes two different implementations of reading and writing files, one using BuffererReader and FileOutputStream and the other using Lambda and Stream APIs. This application implements Maven as a java tool to streamline the build process.
#Quick start
The variables that are required to run the program (variables are inputted in order of listed):

- regex_pattern: the regex pattern to be searched
- src_dir: the root directory path to be searched
- outfile: the output file name

The application can be by using a jar file or by using the docker image. 

- Using the Jar file:
  - Enter the command 
    - `mvn clean package`
    - `java -jar target/grep-1.0-SNAPSHOT.jar ${regex_pattern} ${src_dir} ./out/${outfile}`
- Using the Docker image:
  - Enter the command
    - `docker pull paulguevarra/grep`
    - `docker run --rm -v pwd/data:data -v pwd/out:out paulguevarra/grep ${regex_pattern} ${src_dir} ./out/${outfile}`
  

##Pseudocode

```matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
##Performance Issue

If the file size is larger than the heap of the JVM, the application outputs an OutOfMemoryError exception. The implemented List data structure can get too large and hinder performance if the file contain to many lines to parse through. Using the Stream APIs implementation, rather than using BufferedReader and Lists, to process data would solve this issue. Since streams do not store data and allow for elements to be computer on the demand, this allows memory to be saved in larger sizes. 
#Test
The Grep Application was tested by inputting sample data into the arguments of the program. The sample data was input manually on the command line, using different combinations of regex string patterns, root directory paths, and out filenames. The output file is then compared to the output using the Linux grep command. If there are any discrepancies that could be determined, they would be debugged and fixed.
#Deployment
The Java Grep Application was first turned into a jar file and then turned into a docker image by implementing a DockerFile. The docker image was then uploaded to the Docker hub to be accessed on https://hub.docker.com/repository/docker/paulguevarra/grep. The Docker Image can be also be pulled using the command `docker pull paulguevarra/grep`.

#Improvement

1. Implement a GUI to easily input different regex patterns and other arguments. 
2. Implement a more memory-efficient solution to fix the performance issue. 
3. Allow the user to manipulate text files such as replacing or deleting text.
