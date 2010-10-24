package alma.news;

import alma.common.models.vo.CategorieVO;
import alma.common.models.vo.NewsVO;

public class DummyNews {

	public static NewsVO generate() {
		NewsVO news = new NewsVO();
		news.id = 1;
		news.author = "John Doe";
		news.categories.add(CategorieVO.POLITIQUE);
		news.categories.add(CategorieVO.SANTE);
		news.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vestibulum, justo id tempor ullamcorper, metus lacus placerat elit, eu facilisis metus dui sit amet nibh. Quisque sollicitudin, turpis eget sagittis facilisis, leo dolor laoreet eros, malesuada auctor ligula lorem vel turpis. Mauris sollicitudin imperdiet nisl, quis ultricies sapien faucibus vel. Praesent consequat nulla vitae ipsum aliquam vel iaculis tortor pharetra. Nam ut lacus ut velit elementum aliquam. Sed vestibulum nibh et urna scelerisque ac viverra dui ullamcorper. Suspendisse fringilla diam ac odio iaculis consectetur. Morbi placerat egestas mi tincidunt bibendum. Mauris pellentesque sem id mauris commodo non egestas lacus interdum. Sed sollicitudin lorem ut felis auctor aliquam. Ut vitae eros massa, sed consequat quam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis bibendum sem id diam ultrices scelerisque. Vestibulum non ipsum vel odio aliquet condimentum ut at quam. Suspendisse vitae augue sed tellus lobortis sodales eget vel mauris. Fusce id mauris quis diam rhoncus iaculis.";
		return news;
	}
}
