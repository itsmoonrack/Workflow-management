package alma.common.vo;


public class EditorVO {

	private static String destName;
	public static String categorie;

	public EditorVO(){
		
	}
	
	public EditorVO(String destName) {
		this.destName = destName;
	}
	
	public EditorVO(String destName, String categorie) {
		this.destName = destName;
		this.categorie = categorie;
	}
		
}
