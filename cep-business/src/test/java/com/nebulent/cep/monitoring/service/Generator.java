/**
 * 
 */
package com.nebulent.cep.monitoring.service;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.nebulent.cep.monitoring.esper.EsperBean;

/**
 * @author Max Fedorov
 *
 */
public class Generator implements Runnable{

	private int maxTransactions = 50;
    private int maxTransactionDelay = 20; // max 20 seconds between messages
    
    int chanceToLaunch = 2; // e.g. 1/2
    Random rand = new Random(System.currentTimeMillis());
    
	private ScheduledThreadPoolExecutor commandExecutor = new ScheduledThreadPoolExecutor(10);
    private AtomicInteger executionCount = new AtomicInteger(1);
    private CountDownLatch lock = new CountDownLatch(1);
    
    private Runnable command;
    private EsperBean esperBean;
    
    /**
     * @param command
     * @param esperBean
     */
    public Generator(EsperBean esperBean) {
    	this.esperBean = esperBean;
	}

	/**
	 * @return the maxTransactions
	 */
	public int getMaxTransactions() {
		return maxTransactions;
	}

	/**
	 * @return the maxTransactionDelay
	 */
	public int getMaxTransactionDelay() {
		return maxTransactionDelay;
	}

	/**
	 * @return the executionCount
	 */
	public AtomicInteger getExecutionCount() {
		return executionCount;
	}

	/**
	 * @return the command
	 */
	public Runnable getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Runnable command) {
		this.command = command;
	}

	/**
	 * @return the esperBean
	 */
	public EsperBean getEsperBean() {
		return esperBean;
	}

	/**
	 * @return the lock
	 */
	public CountDownLatch getLock() {
		return lock;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Generator started...");
		//if(getExecutionCount().get() < getMaxTransactions()){
        //    int random = Math.abs(rand.nextInt());
        //    if(random % chanceToLaunch == 0){
        //        getExecutionCount().getAndIncrement();
                scheduleCommand(getCommand(), 1);
        //    }
		//	else{
	    //        System.out.println("Create new transaction denied: " + getExecutionCount().get() + " in progress");
	    //    }
		//}
	}
	
	/**
     * 
     */
    public void destroy() {
        System.out.println("*** Terminating core");
        commandExecutor.shutdownNow();
        executionCount = new AtomicInteger(0);
        lock.countDown();
    }

    /**
     * @param command
     * @param delay
     * @param interval
     */
    public void scheduleCommand(Runnable command, long delay, long interval) {
    	commandExecutor.scheduleAtFixedRate(command, delay, interval, TimeUnit.SECONDS);
    }
    
    /**
     * @param command
     * @param delay
     */
    public void scheduleCommand(Runnable command, long delay) {
        commandExecutor.schedule(command, delay, TimeUnit.SECONDS);
    }
}
