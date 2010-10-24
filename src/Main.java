import alma.edition.PublishSubscribeService;
import alma.news.NewsPoolService;
import alma.publication.PublisherService;


public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		//Lance les 3 services persistents: Publisher, NewsPool, PublishSubscribe
		new NewsPoolService();
		new PublishSubscribeService();
		new PublisherService();
		
//		//First, we create a release, this release contains many news
//		ReleaseVO release1 = new ReleaseVO(1);
//		
//		//we create a news
//		NewsVO news1 = new NewsVO();
//		
//		//we add the news in the release
//		release1.addNews(news1);
//		release1.addCategories(CategorieVO.SANTE);
//		
//		//After, we create 3 editors with a specific press domain
//		EditorVO Alphonse = new EditorVO("topic1",CategorieVO.SANTE,"Alphonse");
//		EditorVO Didier = new EditorVO("topic1", CategorieVO.SPORT, "Didier");
//		EditorVO JC = new EditorVO("topic1", CategorieVO.CULTURE, "JC");
//		
//		//The NewsPool sends the release in the topic
//		NewsPoolService newsPool = new NewsPoolService("topic1");
//		newsPool.send(release1);
//		
//		// Push
	}
}
