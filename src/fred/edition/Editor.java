package alma.edition;

import alma.common.models.vo.CategorieVO;
import alma.common.models.vo.NewsVO;
import alma.common.services.TopicSessionMessageReceiver;


public class Editor extends TopicSessionMessageReceiver implements Runnable {

	public String destName;
	public String destName2;
	public CategorieVO categorie;
	public String name;
	public NewsVO news;
	public boolean available;

	public Editor(String destName, String destName2, CategorieVO categorie, String name) throws InterruptedException {
		super(destName,categorie,name);
		this.destName = destName;
		this.destName2 = destName2;
		this.categorie = categorie;
		this.name = name;
		this.news = null;
		this.available = true;
	}
	
	public void treat(NewsVO news){
		if (available){
			this.news = news;
			System.out.println(">>> " +this.name + " treats the news !");
			this.available = false;
			
			//send the news to the editor in chief
			SendNewsFromEditor sendNews = new SendNewsFromEditor(destName2);
			
			if(news.id != 0){
				sendNews.send(news);
			}else{
				System.out.println("The new release can't be sent because not id found !");
			}
			
		}else {
			System.out.println(this.name + " can't treat the news because he treats another news !");
		}
	}
		
}
