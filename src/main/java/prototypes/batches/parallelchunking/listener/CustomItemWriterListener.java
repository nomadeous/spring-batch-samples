/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototypes.batches.parallelchunking.listener;

import java.util.List;
import org.springframework.batch.core.ItemWriteListener;

public class CustomItemWriterListener implements ItemWriteListener<Object> {

    @Override
    public void beforeWrite(List<? extends Object> items) {
        System.out.println("ItemWriteListener - beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends Object> items) {
        System.out.println("ItemWriteListener - afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Object> items) {
        System.out.println("ItemWriteListener - onWriteError");
    }

}
