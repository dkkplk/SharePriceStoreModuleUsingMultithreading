package com.kristal.pricemodule.producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.kristal.pricemodule.inmemorystore.InMemoryStore;
import com.kristal.pricemodule.model.ConvertedPriceModel;
import com.kristal.pricemodule.model.PriceModel;

public class PriceStoreProducer implements Runnable {

	private final BlockingQueue<ConvertedPriceModel> numbersQueue;
	private final int poisonPill;
	private final int poisonPillPerProducer;
	private InMemoryStore store;
	private ConvertedPriceModel price;

	public PriceStoreProducer(BlockingQueue<ConvertedPriceModel> numbersQueue, int poisonPill, int poisonPillPerProducer,ConvertedPriceModel price) {
		this.numbersQueue = numbersQueue;
		this.poisonPill = poisonPill;
		this.poisonPillPerProducer = poisonPillPerProducer;
		this.store = InMemoryStore.getInstance();
		this.price = price;
	}

	public void run() {
		try {
			generateNumbers();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void generateNumbers() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			
			store.putInStore(price.getCompanyName(),price);
		}
		for (int j = 0; j < poisonPillPerProducer; j++) {
			
			store.putInStore(price.getCompanyName(),price);
			
		}
	}
}