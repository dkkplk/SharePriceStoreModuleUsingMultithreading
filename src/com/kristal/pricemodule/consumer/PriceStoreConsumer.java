package com.kristal.pricemodule.consumer;

import java.util.Map.Entry;

import com.kristal.pricemodule.inmemorystore.InMemoryStore;
import com.kristal.pricemodule.model.ConvertedPriceModel;

public class PriceStoreConsumer implements Runnable {

	public PriceStoreConsumer() {

	}

	public void run() {
		while (true) {
			InMemoryStore store = InMemoryStore.getInstance();

			for (Entry<String, ConvertedPriceModel> s : store.getStore().entrySet()) {
				System.out.println(Thread.currentThread().getName() + " result: " + s.getKey() + " " + s.getValue());
			}
		}
	}
}