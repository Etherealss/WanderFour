package common.others;

import common.enums.EsEnum;
import common.enums.WritingType;
import common.factory.ServiceFactory;
import common.util.EsUtil;
import common.util.FileUtil;
import org.apache.log4j.Logger;
import pojo.po.Article;
import pojo.po.Posts;
import service.EsService;
import service.WritingService;

import java.util.List;

/**
 * @author 寒洲
 * @description Es进程管理
 * @date 2020/11/16
 */
public class EsProcessManager {

	private static Logger logger = Logger.getLogger(EsProcessManager.class);

	/** ES进程对象 */
	private static Process esProcess;

	/**
	 * 初始化ES数据，建立索引和文档
	 */
	public static void esDataInit() {
		logger.info("初始化ES数据");
		boolean isEsHostConnected = EsUtil.isEsHostConnected();
		if (!isEsHostConnected){
			logger.info("启动ES");
			// 获取elasticsearch.bat文件路径，通过Runtime.getRuntime().exec()方法启动
			esProcess = FileUtil.runProcess(EsUtil.getEsPath());
		}
		EsService esService = ServiceFactory.getEsService();
		boolean existsIndex = esService.existsIndex(EsEnum.INDEX_NAME);
		// 判断是否存在索引
		if (!existsIndex) {
			logger.trace("创建ES索引");
			esService.createWritingIndex();
		}
		try {
			EsProcessManager.addUnExistWritingToEs(esService, WritingType.ARTICLE);


			WritingService<Posts> postsWritingService = ServiceFactory.getPostsService();
			List<Long> postsIds = postsWritingService.getAllWritingsId();
			List<Long> posts = esService.checkWritingsExist(WritingType.POSTS, postsIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有文章和问贴id
	 * @param writingType
	 */
	private static void addUnExistWritingToEs(EsService esService, WritingType writingType) throws Exception {

		WritingService<Article> articleWritingService = ServiceFactory.getArticleService();
		List<Long> articleIds = articleWritingService.getAllWritingsId();
		// 判断ES是否有对应文档
		List<Long> article = esService.checkWritingsExist(WritingType.ARTICLE, articleIds);
		// 将不存在的文档添加到ES中

	}

	/**
	 * ES进程是否存活
	 * @return
	 */
	public static boolean isEsProcessAlive() {
		return esProcess.isAlive();
	}

	/**
	 * 关闭ES进程
	 */
	public static void destroyEsProcess(){
		esProcess.destroy();
	}

}
