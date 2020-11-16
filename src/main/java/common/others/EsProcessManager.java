package common.others;

import common.enums.EsEnum;
import common.enums.WritingType;
import common.factory.ServiceFactory;
import common.util.EsUtil;
import common.util.FileUtil;
import org.apache.log4j.Logger;
import pojo.bo.EsBo;
import pojo.po.Article;
import pojo.po.Posts;
import pojo.po.Writing;
import service.EsService;
import service.WritingService;

import java.io.IOException;
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

	public static Process getEsProcess() {
		return esProcess;
	}

	public static void setEsProcess(Process esProcess) {
		EsProcessManager.esProcess = esProcess;
	}

	/**
	 * 初始化ES数据，建立索引和文档
	 */
	public static void esDataInit() {
		logger.info("初始化ES数据");
		try {
			boolean isEsHostConnected = EsUtil.isEsHostConnected();
			if (!isEsHostConnected) {
				throw new IOException("ES连接失败");
			}

			EsService esService = ServiceFactory.getEsService();

			// 判断是否存在索引
			boolean existsIndex = esService.existsIndex(EsEnum.INDEX_NAME);
			if (!existsIndex) {
				logger.trace("创建ES索引");
				esService.createWritingIndex();
			}

			// 检查和补充数据
			EsProcessManager.addUnExistWritingToEs(esService, WritingType.ARTICLE);
			EsProcessManager.addUnExistWritingToEs(esService, WritingType.POSTS);

		} catch (Exception e) {
			logger.error("ES初始化ES数据失败：" + e.getMessage());
		}
	}

	/**
	 * 获取所有文章和问贴id，将需要的数据添加到ES文档库
	 * @param writingType
	 */
	private static void addUnExistWritingToEs(EsService esService, WritingType writingType) throws Exception {
		WritingService<?> service;

		if (writingType == WritingType.ARTICLE) {
			service = ServiceFactory.getArticleService();
		} else {
			service = ServiceFactory.getPostsService();
		}

		List<Long> allIds = service.getAllWritingsId();
		// 判断ES是否有对应文档，获取需要添加的文档
		List<Long> addIds = esService.checkWritingsExist(WritingType.ARTICLE, allIds);
		// 获取数据
		List<EsBo> addWritings = service.getWritingListByIds(addIds);

		// 将不存在的文档添加到ES中
		String add = esService.bulkDoc(EsEnum.INDEX_NAME, "add", addWritings);

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
	public static void destroyEsProcess() {
		esProcess.destroy();
	}

}
