package alma.common.vo;

import alma.edition.PublishSubscribeComponent;


public class EditorVO extends PublishSubscribeComponent{

	private static String destName;
	public static CategorieVO categorie;
	public static String name;

	public EditorVO(String destName, CategorieVO categorie, String name) {
		super(destName,categorie,name);
		this.destName = destName;
		this.categorie = categorie;
		this.name = name;
	}
		
}
