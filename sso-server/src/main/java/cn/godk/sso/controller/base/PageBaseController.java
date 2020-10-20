package cn.godk.sso.controller.base;

import cn.godk.sso.bean.Permit;
import cn.godk.sso.conf.ProjectConfig;
import cn.godk.sso.rollback.constant.RollbackConstant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * 页面相关 base Controller
 *
 * @author wt
 * @program project-sso
 * @create 2020-09-22  16:21
 */
@Setter
@Getter
public class PageBaseController extends BaseController {
    @Resource
    private ProjectConfig projectConfig;

    /**
     * 请求成功转发
     *
     * @param backUrl 回调路径
     * @param permit  token内容
     * @return
     */
    public String success(String backUrl, Permit permit) {
        StringBuilder rollback = new StringBuilder();
        if (StringUtils.isNotBlank(backUrl)) {
            rollback.append(backUrl);
            if (backUrl.contains("?")) {
                rollback.append("&");
            } else {
                rollback.append("?");
            }
            // TODO 这里可能需要更改
            rollback.append(RollbackConstant.SERVICE_TICKET).append("=").append(permit.getKey());
        } else {
            rollback.append("/");
        }
        return "redirect:" + rollback.toString();
    }
}
