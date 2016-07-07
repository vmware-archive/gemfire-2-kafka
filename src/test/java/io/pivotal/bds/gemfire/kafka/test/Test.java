package io.pivotal.bds.gemfire.kafka.test;

import java.nio.file.Paths;
import java.util.Random;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.internal.util.StopWatch;

import io.pivotal.domain.PriceQuote;

public class Test {

	private static final String tickers = "AAPL,PVTL,SWA,VMW,DELL,TSLA,GOOG,MSFT,AMZN,FB,INTC";
	
    public static void main(String[] args) throws Exception {
    	
    	System.out.println(Paths.get(".").toAbsolutePath().toString());
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "target/test-classes/client-cache.xml");
        ClientCache cc = ccf.create();

        Region<String, PriceQuote> r1 = cc.getRegion("priceQuotes");

        
    	Random r = new Random(System.currentTimeMillis());
        String[] tickerSymbols = tickers.split(",");
        int tickerIx = 0;
        int priceInPennies = 0;
        
        StopWatch sw = new StopWatch(true);

        for (int i=0; i< 1000000; i++) {
        	tickerIx = r.nextInt(tickerSymbols.length);
        	priceInPennies = r.nextInt(10000);
        	String ticker = tickerSymbols[tickerIx];
            PriceQuote quote = new PriceQuote(ticker, System.currentTimeMillis(), priceInPennies);
            r1.put(ticker, quote);
            
            if (i % 10000 == 0) {
            	System.out.println("Output " + i +  " messages in " + sw.elapsedTimeMillis() + " millis");
            }
        }
        
        sw.stop();
        System.out.println("Output 1 million messages in " + sw.elapsedTimeMillis()/1000 + " seconds");
    }
}
