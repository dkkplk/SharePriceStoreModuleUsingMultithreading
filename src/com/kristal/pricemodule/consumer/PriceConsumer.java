package com.kristal.pricemodule.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.kristal.pricemodule.model.ConvertedPriceModel;
import com.kristal.pricemodule.model.PriceModel;
import com.kristal.pricemodule.producer.PriceConvertorProducer;

public class PriceConsumer implements Runnable {
	private final BlockingQueue<PriceModel> queue;
	private final int poisonPill;

	public PriceConsumer(BlockingQueue<PriceModel> queue, int poisonPill) {
		this.queue = queue;
		this.poisonPill = poisonPill;
	}

	public void run() {
		try {
			while (true) {
				PriceModel price = queue.take();
				if (price.getSharePrice().equals(poisonPill)) {
					return;
				}
				callPriceConvertor(price);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void callPriceConvertor(PriceModel price) {
		int BOUND = 10;
		int N_PRODUCERS = 4;
		int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
		int poisonPill = Integer.MAX_VALUE;
		int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;
		int mod = N_CONSUMERS % N_PRODUCERS;
		BlockingQueue<ConvertedPriceModel> queue = new LinkedBlockingQueue<>(BOUND);

		for (int i = 1; i < N_PRODUCERS; i++) {
			new Thread(new PriceConvertorProducer(queue, poisonPill, poisonPillPerProducer,price)).start();
		}

		for (int j = 0; j < N_CONSUMERS; j++) {
			new Thread(new PriceConvertorConsumer(queue, poisonPill)).start();
		}

		new Thread(new PriceConvertorProducer(queue, poisonPill, poisonPillPerProducer + mod,price)).start();

		
	}
}