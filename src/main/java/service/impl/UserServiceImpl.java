package service.impl;

import common.enums.ApplicationConfig;
import common.util.FileUtil;
import common.util.Md5Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.po.User;
import common.enums.ResultType;
import dao.UserDao;
import service.UserService;

import java.io.IOException;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/2
 */
public class UserServiceImpl implements UserService {
    private Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao dao;

    @Override
    public ResultType checkUserExist(String email) {
        //检查账号是否已存在
        if (dao.countUserByEmail(email) == 1) {
            //存在
            return ResultType.IS_REGISTED;
        } else {
            //不存在
            return ResultType.USER_UN_FOUND;
        }
    }

    @Override
    public User getUserEmailAndPw(Long id) {
        return dao.getUserEmailAndPwById(id);
    }


    @Override
    public User validateUserLogin(String email, String paasword) {
        User user = dao.selectUserBySign(email, paasword);
        setUserAvatarStream(user);
        return user;
    }

    @Override
    public Long registerNewUser(User user) {
        // TODO 修改接口
        switch (user.getUserType()) {
            case "高中生":
                user.setUserType("SENIOR");
                break;
            case "大学生":
                user.setUserType("COLLEGE");
                break;
            case "教师":
                user.setUserType("TEACHER");
                break;
            default:
                logger.error("错误的UserType类型");
        }
        // 获取默认头像的数据流，封装到user对象中
        String avatarPath;
        //根据性别加载默认头像
        if (user.getSex()) {
            avatarPath = ApplicationConfig.AVATAR_DEFAULT_PATH_BOY;
        } else {
            avatarPath = ApplicationConfig.AVATAR_DEFAULT_PATH_GIRL;
        }
        user.setAvatarPath(avatarPath);

        // 用户id
        Long lastInsertId;
        // 头像保存的路径
        String avatarSavePath;
        try {
            dao.registerNewUser(user);
            // MyBatis插入新id
            lastInsertId = user.getId();
            // 新的头像路径，拼接储存的文件路径和文件名
            avatarSavePath = ApplicationConfig.AVATAR_PATH + lastInsertId + ".png";
            dao.updateUserAvatarPath(lastInsertId, avatarSavePath);
        } catch (Exception e) {
            logger.error("注册用户异常",e);
            return null;
        }

        // 复制头像图片，以userId命名
        String base64Str = null;
        try {
            base64Str = FileUtil.getBase64ByImgPath(avatarPath);
            FileUtil.generateImageByBase64(base64Str, avatarSavePath);
        } catch (IOException e) {
            logger.error("注册用户复制头像图片出现异常",e);
            return null;
        }
        return lastInsertId;
    }

    @Override
    public void updateUserInfo(User user) {
        dao.updateUserInfo(user);
    }

    @Override
    public User getUserInfo(Long userid) {
        User user = dao.getUserById(userid);
        setUserAvatarStream(user);
        return user;
    }

    @Override
    public ResultType updateUserPw(Long userid, String originalPw, String newPw) {
        User user = dao.getUserEmailAndPwById(userid);
        //Md5加密
        originalPw = Md5Utils.md5Encode(user.getEmail() + originalPw);
        logger.debug("\n修改密码：原始密码：" + user.getPassword() + "\n修改密码：新的密码：" + originalPw);
        if (originalPw.equals(user.getPassword())) {
            //密码相同，可以修改
            dao.updateUserPw(userid, newPw);
            return ResultType.SUCCESS;
        } else {
            //原密码错误
            return ResultType.PW_ERROR;
        }
    }

    private void setUserAvatarStream(User user) {
        if (user != null) {
            //图片数据转码
            try {
                byte[] imgStream = FileUtil.getFileStream(user.getAvatarPath());
                String imgByBase64 = FileUtil.getImgByBase64(imgStream);
                user.setAvatarPath(imgByBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
