package controller;

import common.enums.TargetType;
import org.apache.log4j.Logger;
import pojo.po.LikeRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/13
 */
@WebServlet("/UserLikeServlet")
public class UserLikeController extends BaseServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LikeRecord likeRecord = getUserLikeData(req);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	private LikeRecord getUserLikeData(HttpServletRequest req){
		LikeRecord likeRecord = new LikeRecord();
		likeRecord.setUserid(req.getParameter("userid"));
		likeRecord.setTargetId(Long.valueOf(req.getParameter("targetId")));
		likeRecord.setTargetType(TargetType.getCode(req.getParameter("targetType")));
		likeRecord.setLikeState(Integer.parseInt(req.getParameter("likeState")));
		return likeRecord;
	}
}
