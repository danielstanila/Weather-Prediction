package test;

import process.FileContent;
import process.ProcessExecutor;

public class Test {

    private static final String PATH = "svm_light/";

    public static void main(String[] args) {
        ProcessExecutor executor = new ProcessExecutor();

        FileContent stdOutput;

        stdOutput = executor.execute(PATH + "svm_learn", PATH + "example1/train.dat", PATH + "example1/model").readStdOutput();
        stdOutput.forEach(System.out::println);

        System.out.println();

        stdOutput = executor.execute(PATH + "svm_classify", PATH + "example1/test.dat", PATH + "example1/model", PATH + "example1/predictions").readStdOutput();
        stdOutput.forEach(System.out::println);

    }
}
