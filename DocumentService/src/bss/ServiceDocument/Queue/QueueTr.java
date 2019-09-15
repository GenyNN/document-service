package bss.ServiceDocument.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import bss.ServiceDocument.utils.DBKParams;

public class QueueTr<T> {
	private List<T> m_Queue = new ArrayList<T>();
	private BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
	private int m_iMax = 10;
	
	public void Max(int iVal) 
	{ 
		synchronized(m_Queue)
		{
			m_iMax = iVal;
		}
	}
	public int Max()
	{
		synchronized(m_Queue)
		{
			return m_iMax;
		}
	}
	
	public void Push(T obj) throws InterruptedException
	{
		queue.put(obj);
	}
	public T Pop() throws InterruptedException
	{		
		return queue.poll(20, TimeUnit.SECONDS);
	}
	
	public T Pop(int ms) throws InterruptedException {
		return queue.poll(ms, TimeUnit.MILLISECONDS);
	}	
	
	
	public void Initialize(DBKParams params)
	{
		String strMax = params.GetString("Max");
		if(strMax != null)
			this.Max(Integer.parseInt(strMax));
	}
	
}
