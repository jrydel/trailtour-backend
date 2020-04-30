package cz.jr.trailtour.backend.repository.mysql;

/**
 * Created by Jiří Rýdel on 3/11/20, 10:50 AM
 */
public class UpdateCounterParam extends UpdateParam {

    public UpdateCounterParam() {
        super("counter", "counter + 1");
    }
}
