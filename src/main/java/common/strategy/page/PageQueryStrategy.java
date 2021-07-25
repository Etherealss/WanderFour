package common.strategy.page;

import common.exception.specific.ActionFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.bo.PageBo;

import java.util.List;

/**
 * @author wtk
 * @description 分页数据查询策略
 * 子类可以实现不同的页面数据查询方式，如按id查询数据，按name查询数据
 * 通过泛型可以实现对多种数据的分页，例如音乐分页、歌手分页
 * @param <T> 不同的po类型，用于指定分页的数据类型，例如音乐分页、歌手分页
 * @date 2021-06-17
 */
public abstract class PageQueryStrategy<T> {

    protected Logger logger = LoggerFactory.getLogger("root");

    protected int curPage;
    protected int start;
    protected int offset;

    /**
     * 获取记录数
     * @return
     */
    protected abstract int getTotalCount();

    /**
     * 查询多条记录
     * @return
     */
    protected abstract List<T> getDataList();

    /**
     * 获取分页，属于工具方法
     * @return
     */
    public PageBo<T> bulidPage() throws ActionFailException {
        // 计算查询索引，其实页面是第1页，但数据库第一条记录的索引是0，所以减1
        int start = (curPage - 1) * offset;

        /*
        执行策略 获取请求目标范围内的歌手数。用于计算总页码
         */
        int totalCount = this.getTotalCount();

        // 计算总页码数
        // 如果 总记录数可以整除以每页显示的记录数，那么总页数就是它们的商
        // 否则 说明有几条数据要另开一页显示，总页数+1
        int totalPage = totalCount / offset;
        totalPage = totalCount % offset == 0 ? totalPage : totalPage + 1;
        if (curPage > totalPage) {
            logger.warn("当前页码超出最大页码，无匹配项目：当前页数：{}, 总页数：{}", curPage, totalPage);
            throw new ActionFailException("当前页码超出最大页码，无匹配项目");
        }

        /*
        执行策略，获取当前页的歌手数据
         */
        List<T> rangeSinger = this.getDataList();

        // 包装页面信息
        PageBo<T> page = new PageBo<>(curPage, offset);
        page.setTotalCount(totalCount);
        page.setList(rangeSinger);
        page.setTotalPage(totalPage);

        return page;
    }
}
