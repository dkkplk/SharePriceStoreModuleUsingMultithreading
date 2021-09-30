package com.kristal.pricemodule.producer;

import java.util.concurrent.BlockingQueue;

import com.kristal.pricemodule.model.ConvertedPriceModel;
import com.kristal.pricemodule.model.PriceModel;

public class PriceConvertorProducer implements Runnable {

	private final BlockingQueue<ConvertedPriceModel> numbersQueue;
	private final int poisonPill;
	private final int poisonPillPerProducer;
	private PriceModel price;

	public PriceConvertorProducer(BlockingQueue<ConvertedPriceModel> numbersQueue, int poisonPill,
			int poisonPillPerProducer, PriceModel price) {
		this.numbersQueue = numbersQueue;
		this.poisonPill = poisonPill;
		this.poisonPillPerProducer = poisonPillPerProducer;
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
			numbersQueue.put(new ConvertedPriceModel(price.getCompanyName(), price.getSharePrice()));
		}
		for (int j = 0; j < poisonPillPerProducer; j++) {
			numbersQueue.put(new ConvertedPriceModel(price.getCompanyName(), (double) poisonPill));
		}
	}
}