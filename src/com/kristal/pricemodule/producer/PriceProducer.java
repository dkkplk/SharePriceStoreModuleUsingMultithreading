package com.kristal.pricemodule.producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import com.kristal.pricemodule.model.PriceModel;

public class PriceProducer implements Runnable {

	private final BlockingQueue<PriceModel> numbersQueue;
	private final int poisonPill;
	private final int poisonPillPerProducer;

	public PriceProducer(BlockingQueue<PriceModel> numbersQueue, int poisonPill, int poisonPillPerProducer) {
		this.numbersQueue = numbersQueue;
		this.poisonPill = poisonPill;
		this.poisonPillPerProducer = poisonPillPerProducer;
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
			Random r = new Random();
			char c = (char) (r.nextInt(26) + 'a');
			numbersQueue.put(new PriceModel(String.valueOf(c), (double) ThreadLocalRandom.current().nextInt(100)));
		}
		for (int j = 0; j < poisonPillPerProducer; j++) {
			Random r = new Random();
			char c = (char) (r.nextInt(26) + 'a');
			numbersQueue.put(new PriceModel(String.valueOf(c), (double) poisonPill));
		}
	}
}