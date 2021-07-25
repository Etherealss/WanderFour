package common.strategy.page.query;

import common.strategy.page.PageQueryStrategy;

/**
 * @author wtk
 * @description
 * @date 2021-07-23
 */
public abstract class QueryByIdStragegy<T> extends PageQueryStrategy<T> {

    protected long id;
}
