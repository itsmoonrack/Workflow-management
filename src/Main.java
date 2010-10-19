import alma.edition.PublishSubscribeComponent;
import alma.news.NewsPool;


public class Main {
	
	public static void main(String[] args) {
		
		PublishSubscribeComponent PSC = new PublishSubscribeComponent("topic1");
		Thread ThreadPSC = new Thread(PSC);
		ThreadPSC.start();
		
		NewsPool newsPool = new NewsPool("topic1");
		newsPool.send();
		
	}
}
