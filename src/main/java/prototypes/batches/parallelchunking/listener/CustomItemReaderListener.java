/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prototypes.batches.parallelchunking.listener;

import org.springframework.batch.core.ItemReadListener;
 
public class CustomItemReaderListener implements ItemReadListener<Object> {
 
	@Override
	public void beforeRead() {
		System.out.println("ItemReadListener - beforeRead");
	}
 
	@Override
	public void afterRead(Object item) {
		System.out.println("ItemReadListener - afterRead");
	}
 
	@Override
	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener - onReadError");
	}
 
}