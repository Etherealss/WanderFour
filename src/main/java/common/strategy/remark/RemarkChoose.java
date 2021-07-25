package common.strategy.remark;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.dto.CommentDto;

import java.util.List;

/**
 * @author wtk
 * @description Remark -> Comment和Reply 评论和回复的统称
 * @date 2021-07-23
 */
public class RemarkChoose {

    private Logger logger = LoggerFactory.getLogger("root");

    /**
     * 查询策略
     */
    @Setter
    @Getter
    private RemarkQueryStrategy remarkQueryStrategy;

    public RemarkChoose(RemarkQueryStrategy remarkQueryStrategy) {
        this.remarkQueryStrategy = remarkQueryStrategy;
    }


    /**
     * 获取评论或回复的记录
     * @param parentId
     * @return
     */
    public List<CommentDto> getRemarkData(long parentId) {
        return remarkQueryStrategy.getRemarkData(parentId);
    }
}
