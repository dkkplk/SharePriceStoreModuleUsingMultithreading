package com.kristal.pricemodule.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.kristal.pricemodule.model.ConvertedPriceModel;
import com.kristal.pricemodule.producer.PriceStoreProducer;

public class PriceConvertorConsumer implements Runnable {
	private final BlockingQueue<ConvertedPriceModel> queue;
	private final double poisonPill;

	public PriceConvertorConsumer(BlockingQueue<ConvertedPriceModel> queue, int poisonPill) {
		this.queue = queue;
		this.poisonPill = poisonPill;
	}

	public void run() {
		try {
			while (true) {
				ConvertedPriceModel price = queue.take();
				if (price.getPrice() == poisonPill) {
					return;
				}
				callPriceStoreConsumer(price);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void callPriceStoreConsumer(ConvertedPriceModel price) {
		int BOUND = 10;
		int N_PRODUCERS = 4;
		int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
		int poisonPill = Integer.MAX_VALUE;
		int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
		int mod = N_CONSUMERS % N_PRODUCERS;
		BlockingQueue<ConvertedPriceModel> queue = new LinkedBlockingQueue<>(BOUND);

		for (int i = 1; i < N_PRODUCERS; i++) {
			new Thread(new PriceStoreProducer(queue, poisonPill, poisonPillPerProducer, price)).start();
		}

		for (int j = 0; j < N_CONSUMERS; j++) {
			new Thread(new PriceStoreConsumer()).start();
		}

		new Thread(new PriceStoreProducer(queue, poisonPill, poisonPillPerProducer + mod, price)).start();

	}
}