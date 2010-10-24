package alma.news;

import java.util.Date;

import alma.common.models.vo.CategorieVO;
import alma.common.models.vo.NewsVO;

public class DummyNews {
	
	public static NewsVO[] news = new NewsVO[] {
		new NewsVO("La cote de popularité de Nicolas Sarkozy passe sous les 30 %", "LEMONDE.FR", new Date(2010, 10, 24), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.POLITIQUE),
		new NewsVO("Ligue 1 : défaite du PSG, l'OL accroché par Arles-Avignon", "LEMONDE.FR", new Date(2010, 10, 23), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.SPORT, CategorieVO.LOISIRS),
		new NewsVO("Les ministres des finances du G20 s'accordent pour réformer le FMI", "L'économiste", new Date(2010, 10, 22), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.ECONOMIE),
		new NewsVO("ISF : les députés réduisent l'avantage pour l'investissement dans les PME", "LEMONDE.FR", new Date(2010, 10, 23), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.ECONOMIE, CategorieVO.SOCIETE),
		new NewsVO("Guérir les troubles alimentaires du nouveau-né", "Sciences et Avenir", new Date(2010, 10, 23), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.SANTE, CategorieVO.CULTURE, CategorieVO.SOCIETE),
		new NewsVO("Dépistage du cancer: Un marqueur prometteur", "Sciences et Avenir", new Date(2010, 10, 23), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultrices, augue sed venenatis auctor, lacus erat adipiscing mi, vitae tincidunt arcu lectus sed est. Donec quam massa, vehicula ut elementum vulputate, auctor at libero. Cras auctor facilisis ullamcorper.", CategorieVO.SANTE),
	};
}
