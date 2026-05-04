package io.github.eggy03.application.ui.swingworkers;

import javax0.license3j.HardwareBinder;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MachineIDWorker extends SwingWorker<String, Void> {

    private static final Logger log = LoggerFactory.getLogger(MachineIDWorker.class);

    private final JTextField machineIdTextField;

    public MachineIDWorker(@NonNull JTextField machineIdTextField) {
        this.machineIdTextField = Objects.requireNonNull(machineIdTextField, "machineIdTextField cannot be null");
    }

    @Override
    protected String doInBackground() throws Exception {
        return new HardwareBinder().getMachineIdString();
    }

    @Override
    protected void done() {
        try {
            machineIdTextField.setText(get());
        } catch (InterruptedException e) {
            log.warn("Interrupted while fetching MachineID");
            log.debug("Stack trace for interruption", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.warn("Failed to fetch MachineID");
            log.debug("Stack trace for failure", e);
        }
    }
}
