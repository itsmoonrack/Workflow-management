package alma.edition;

import alma.common.vo.EditorVO;

public class DummyEditor {
	
	public static EditorVO generate() {
		EditorVO editor = new EditorVO();
		editor.categorie = "Sport";
		return editor;
	}

}
