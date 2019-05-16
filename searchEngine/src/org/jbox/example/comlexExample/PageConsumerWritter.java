package org.jbox.example.comlexExample;


import org.jbox.dao.Page;
import org.jbox.indexer.IndexWriter;





public class PageConsumerWritter implements Runnable{
	private PagePool pool;
	private org.jbox.indexer.IndexWriter indexWriter;
	public PageConsumerWritter(PagePool pool,IndexWriter indexWriter){
		this.pool = pool;
		this.indexWriter= indexWriter;
	}
	private void write(){
		while(true){
			Page p = pool.takeCuttedPage();
			try{
				indexWriter.saveIndex(p);
			}catch(RuntimeException re){
				System.out.println("error occur when writting "+p.getUrl()+",and it is skiped.");
				continue;
			}
		}
	}
	public void run() {
		write();	
	}
}
