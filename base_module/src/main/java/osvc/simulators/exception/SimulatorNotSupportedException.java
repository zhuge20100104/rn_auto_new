package osvc.simulators.exception;

public class SimulatorNotSupportedException extends Exception {
    public SimulatorNotSupportedException(String simType) {
        super("Not supported simulator,Please check simulator type: ["+simType+"]!!");
    }
}
