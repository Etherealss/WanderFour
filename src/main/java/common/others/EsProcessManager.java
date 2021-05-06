package common.others;

import common.enums.EsEnum;
import common.enums.WritingType;
import common.util.EsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import pojo.bo.EsBo;
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

    private static Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

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
    public static void esDataInit(ApplicationContext applicationContext) {
        logger.info("初始化ES数据");

        EsService esService = (EsService) applicationContext.getBean("esService");
        try {
            boolean isEsHostConnected = EsUtil.isEsHostConnected();
            if (!isEsHostConnected) {
                throw new IOException("ES连接失败");
            }
            // 判断是否存在索引
            boolean existsIndex = esService.existsIndex(EsEnum.INDEX_NAME_WRITING);
            if (!existsIndex) {
                logger.trace("创建ES索引");
                esService.createWritingIndex();
            }
        } catch (Exception e) {
            logger.error("创建ES索引异常", e);
        }

        WritingService<? extends Writing> articleService =
                (WritingService<? extends Writing>) applicationContext.getBean("articleService");
        WritingService<? extends Writing> postsService =
                (WritingService<? extends Writing>) applicationContext.getBean("postsService");

        try {
            // 检查和补充数据
            EsProcessManager.addUnExistWritingToEs(esService, articleService, WritingType.ARTICLE);
        } catch (Exception e) {
            logger.error("ES添加文章文档异常", e);
        }
        try {
            EsProcessManager.addUnExistWritingToEs(esService, postsService, WritingType.POSTS);
        } catch (Exception e) {
            logger.error("ES添加问贴文档异常", e);
        }


    }

    /**
     * 获取所有文章和问贴id，将需要的数据添加到ES文档库
     * @param esService
     * @param writingService
     */
    private static void addUnExistWritingToEs(EsService esService, WritingService<? extends Writing> writingService, WritingType writingType) {

        logger.trace("获取所有文章/问贴id");
        List<Long> allIds = writingService.getAllWritingsId();

        // 判断ES是否有对应文档，获取需要添加的文档
        logger.trace("判断ES是否有对应文档，获取需要添加的文档");
        List<Long> addIds = esService.checkWritingsExist(writingType, allIds);

        // 获取需要添加到ES的数据
        logger.trace("获取需要添加到ES的数据");
        List<EsBo> addWritings = writingService.getWritingListByIds(addIds);

        // 将不存在的文档添加到ES中
        logger.trace("将不存在的文档添加到ES中");
        String add = esService.bulkDoc(EsEnum.INDEX_NAME_WRITING, EsEnum.ACTION_ADD, addWritings);

        logger.trace("ES数据更新完毕");
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
