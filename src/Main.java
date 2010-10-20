import alma.common.vo.CategorieVO;
import alma.common.vo.EditorVO;
import alma.common.vo.NewsVO;
import alma.common.vo.ReleaseVO;
import alma.news.NewsPool;


public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		//First, we create a release, this release contains many news
		ReleaseVO release1 = new ReleaseVO(1);
		
		//we create a news
		NewsVO news1 = new NewsVO();
		
		//we add the news in the release
		release1.addNews(news1);
		release1.addCategories(CategorieVO.SANTE);
		
		//After, we create 3 editors with a specific press domain
		EditorVO Alphonse = new EditorVO("topic1",CategorieVO.SANTE,"Alphonse");
		EditorVO Didier = new EditorVO("topic1", CategorieVO.SPORT, "Didier");
		EditorVO JC = new EditorVO("topic1", CategorieVO.CULTURE, "JC");
		
		//The NewsPool sends the release in the topic
		NewsPool newsPool = new NewsPool("topic1");
		newsPool.send(release1);
	}
}
