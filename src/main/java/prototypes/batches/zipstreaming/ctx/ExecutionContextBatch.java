/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypes.batches.zipstreaming.ctx;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author JL06436S
 */
public class ExecutionContextBatch {

    private static final ExecutionContextBatch instance;

    static {
        instance = new ExecutionContextBatch();
    }

    public static ExecutionContextBatch getInstance() {
        return instance;
    }

    public Date getPreviousEndDate() {
        return Calendar.getInstance().getTime();
    }
    
    public void setPreviousEndDate() {
        //
    }
}
