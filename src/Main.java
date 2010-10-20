import alma.common.vo.CategorieVO;
import alma.common.vo.EditorVO;
import alma.news.NewsPool;


public class Main {
	
	public static void main(String[] args) {
		
		//First we create 3 editors with a specific press domain
		EditorVO Alphonse = new EditorVO("topic1",CategorieVO.SANTE,"Alphonse");
		EditorVO Didier = new EditorVO("topic1", CategorieVO.SPORT, "Didier");
		EditorVO JC = new EditorVO("topic1", CategorieVO.CULTURE, "JC");
		
		NewsPool newsPool = new NewsPool("topic1");
		newsPool.send();
	}
}
