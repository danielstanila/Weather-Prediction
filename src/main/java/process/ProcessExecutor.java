package process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessExecutor {

    private Process process;

    public ExecutedProcess execute(String... commands) {
        try {
            process = Runtime.getRuntime().exec(commands);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ExecutedProcess();
    }

    public class ExecutedProcess {

        private ExecutedProcess() {
        }

        private FileContent readStream(InputStream stream) {
            FileContent output = new FileContent();

            try(InputStreamReader streamReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.addLine(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }

        public FileContent readStdOutput() {
            return readStream(process.getInputStream());
        }

        public FileContent readStdError() {
            return readStream(process.getErrorStream());
        }
    }
}
