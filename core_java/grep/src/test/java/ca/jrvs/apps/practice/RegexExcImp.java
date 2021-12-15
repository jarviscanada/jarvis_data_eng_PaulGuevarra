package ca.jrvs.apps.practice;



public class RegexExcImp implements RegexExc {
    @Override
    public boolean matchJpeg(String filename) {
        return(filename.matches("[\\s\\S]*\\.(jpeg|jpg)"));

    }

    @Override
    public boolean matchIP(String ip) {
        return(ip.matches("\\d{4}\\.\\d{4}\\.\\d{4}\\.\\d{4}"));

    }

    @Override
    public boolean isEmptyLine(String line) {
        return(line.isEmpty());

    }



}
