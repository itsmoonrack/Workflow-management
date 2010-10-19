import alma.news.NewsPool;


public class Main {
	
	public static void main(String[] args) {
		NewsPool newsPool = new NewsPool("news");
		newsPool.send();
	}

}
