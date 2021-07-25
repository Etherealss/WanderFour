package common.strategy.page;

import common.exception.specific.ActionFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.bo.PageBo;

/**
 * @author wtk
 * @description 分页策略选择类，等价于策略模式中的上下文类
 * @date 2021-06-16
 */
public class PageChoose<T> {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略
     */
    private PageQueryStrategy<T> pageQueryStrategy;

    /**
     * 选择查询策略
     * @param strategy <div>
     * <li></li>
     * <li></li>
     * </div>
     */
    public PageChoose(PageQueryStrategy<T> strategy) {
        this.pageQueryStrategy = strategy;
    }

    /**
     * 获取Page分页对象
     * @return
     */
    public PageBo<T> getPage() throws ActionFailException {
        return pageQueryStrategy.bulidPage();
    }
}
