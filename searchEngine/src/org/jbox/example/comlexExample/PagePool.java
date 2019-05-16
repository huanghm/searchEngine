package org.jbox.example.comlexExample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.jbox.configuration.Configuration;
import org.jbox.dao.Page;


public class PagePool {
	private BlockingQueue<Page> unCuttedPageQueue = new LinkedBlockingQueue<Page>(10);
	private BlockingQueue<Page> cuttedPageQueue = new LinkedBlockingQueue<Page>(10);
	public PagePool(){
	}
	public void putUncuttedPage(Page p){
		try {
			unCuttedPageQueue.put(p);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public Page takeUncuttedPage(){
		try {
			return unCuttedPageQueue.take();//poll(timeOut,timeUnit);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public void putCuttedPage(Page p){
		try {
			cuttedPageQueue.put(p);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public Page takeCuttedPage(){
		try {
			return cuttedPageQueue.take();//poll(timeOut,timeUnit);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) throws Exception{
		PagePool pool = new PagePool();
		Configuration cfg = Configuration.config();
		PageProducer pp = new PageProducer(pool,cfg.buildWebSpider());		
		PageConsumerCutter pcc = new PageConsumerCutter(pool,cfg.buildCutterBox());
		PageConsumerWritter pcw = new PageConsumerWritter(pool,cfg.buildIndexWriter());
		Executor exe = Executors.newCachedThreadPool();
		exe.execute(pp);
		
		exe.execute(pcc);
		
		exe.execute(pcw);
	
	}
}
