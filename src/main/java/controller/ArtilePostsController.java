package controller;

import common.dto.ResultState;
import common.enums.Partition;
import common.factory.ServiceFactory;
import pojo.Writing;
import pojo.po.*;
import service.WritingService;
import service.impl.WritingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
@WebServlet("/ArtilePostsServlet")
public class ArtilePostsController extends BaseServlet {

	public void action(Map<String, Object> info, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String> map = ascertainPartitionAndType(req);
		Writing writing = getWriting(map.get("partition"), map.get("type"));
		setWritingWithData(req, writing);
		WritingService<? extends Writing> service = getService(map.get("partition"), map.get("type"));

		// 获取请求标识
		String methodName = req.getParameter("action");
		logger.debug("method = " + methodName);

		Method method;
		try {
			/*
			获取指定类的字节码对象
			这里的this指的是继承BaseServlet对象
			通过类的字节码对象获取方法的字节码对象
			通过反射调用方法
			 */
			method = this.getClass().getDeclaredMethod(methodName, Map.class, Writing.class, WritingService.class);
			method.invoke(this, info, writing, service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add(Map<String, Object> info, Writing writing, WritingService<? extends Writing> service) {
		ResultState state = service.publishNewWriting(writing);
	}

	public void delete(Map<String, Object> info, Writing writing, WritingService<? extends Writing> service) {

	}

	public void post(Map<String, Object> info, Writing writing, WritingService<? extends Writing> service) {

	}

	public void get(Map<String, Object> info, Writing writing, WritingService<? extends Writing> service) {

	}

	private void setWritingWithData(HttpServletRequest req, Writing writing) {
		String category = req.getParameter("category");
		String authorId = req.getParameter("authorId");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String label1 = req.getParameter("label1");
		String label2 = req.getParameter("label2");
		String label3 = req.getParameter("label3");
		String label4 = req.getParameter("label4");
		String label5 = req.getParameter("label5");
		// 赋值
		writing.setCategory(category);
		writing.setAuthorId(authorId);
		writing.setTitle(title);
		writing.setContent(content);
		writing.setLabel1(label1);
		writing.setLabel2(label2);
		writing.setLabel3(label3);
		writing.setLabel4(label4);
		writing.setLabel5(label5);
	}
	private Map<String, String> ascertainPartitionAndType(HttpServletRequest req) {
		Map<String, String> map = new HashMap<>();
		String p = req.getParameter("partition");
		String type = req.getParameter("type");
		map.put("partition", p);
		map.put("type", type);
		return map;
	}
	/**
	 * 根据分区和类型获取实例
	 * @param partition 分区
	 * @param type      帖子 or 文章
	 * @return 具体的作品实例
	 */
	private Writing getWriting(String partition, String type) {
		//TODO 优化两个获取方法
		/*
		 * 本来想用Map包装Service和Writing，但是需要转为Object
		 * 获取时需要强转，会失去原有的实例的泛型
		 */
		boolean bool = "article".equals(type);
		Writing writing = null;
		Partition p = Partition.getPartition(partition);
		if (p == LearningArticle.PARTITION) {
			if (bool) {
				writing = new LearningArticle();
			} else {
				writing = new LearningPosts();
			}
		} else if (p == MajorArticle.PARTITION) {
			if (bool) {
				writing = new MajorArticle();
			} else {
				writing = new MajorPosts();
			}
		} else if (p == CollegeArticle.PARTITION) {
			if (bool) {
				writing = new CollegeArticle();
			} else {
				writing = new CollegePosts();
			}
		}
		assert writing != null;
		return writing;
	}
	/**
	 * 根据分区和类型获取Service
	 * @param partition 分区
	 * @param type      帖子 or 文章
	 * @return 对应的Service
	 */
	private WritingService<? extends Writing> getService(String partition, String type) {
		//TODO 优化Service获取
		boolean bool = "article".equals(type);
		WritingService<? extends Writing> service = null;
		Partition p = Partition.getPartition(partition);
		if (p == LearningArticle.PARTITION) {
			if (bool) {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<LearningArticle>());
			} else {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<LearningPosts>());
			}
		} else if (p == MajorArticle.PARTITION) {
			if (bool) {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<MajorArticle>());
			} else {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<MajorPosts>());
			}
		} else if (p == CollegeArticle.PARTITION) {
			if (bool) {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<CollegeArticle>());
			} else {
				service = ServiceFactory.getWritingService(new WritingServiceImpl<CollegePosts>());
			}
		}
		return service;
	}

//	/**
//	 * 根据分区和类型获取实例
//	 * @param partition 分区
//	 * @param type      帖子 or 文章
//	 * @return 具体的作品实例
//	 */
//	private WritingType getWritingType(String partition, String type) {
//		/*
//		 * 本来想用Map包装Service和Writing，但是需要转为Object
//		 * 获取时需要强转，会失去原有的实例的泛型
//		 */
//		boolean bool = "article".equals(type);
//		Partition p = Partition.getPartition(partition);
//		if (p == LearningArticle.PARTITION) {
//			return bool ? WritingType.LEARNING_ARTICLE : WritingType.LEARNING_POSTS;
//		} else if (p == MajorArticle.PARTITION) {
//			return bool ? WritingType.MAJOR_ARTICLE : WritingType.MAJOR_POSTS;
//		}
//		assert p == CollegeArticle.PARTITION;
//		return bool ? WritingType.COLLEGE_ARTICLE : WritingType.COLLEGE_POSTS;
//	}
//
//	/**
//	 * 获取作品类型参数
//	 * @param req
//	 * @return
//	 */
//	private Map<String, String> ascertainPartitionAndType(HttpServletRequest req) {
//		Map<String, String> map = new HashMap<>();
//		String p = req.getParameter("partition");
//		String type = req.getParameter("type");
//		map.put("partition", p);
//		map.put("type", type);
//		return map;
//	}
//
//	/**
//	 * 根据分区和类型获取实例
//	 * @param writingType 确定分区和作品类型
//	 * @return 具体的作品实例
//	 */
//	private Writing getWriting(WritingType writingType) {
//		//TODO 优化两个获取方法
//		/*
//		 * 本来想用Map包装Service和Writing，但是需要转为Object
//		 * 获取时需要强转，会失去原有的实例的泛型
//		 */
//		Writing writing = null;
//		switch (writingType){
//			case LEARNING_ARTICLE:
//				return new LearningArticle();
//			case LEARNING_POSTS:
//				return new LearningPosts();
//			case MAJOR_ARTICLE:
//				return new MajorArticle();
//			case MAJOR_POSTS:
//				return new MajorPosts();
//			case COLLEGE_ARTICLE:
//				return new CollegeArticle();
//			case COLLEGE_POSTS:
//				return new CollegePosts();
//		}
//		assert writing != null;
//		return writing;
//	}



//	/**
//	 * 根据分区和类型获取实例
//	 *
//	 * @param partition 分区
//	 * @param type      帖子 or 文章
//	 *
//	 * @return
//	 */
//	private Writing getArticleOrPost(String partition, String type) {
//		boolean bool = "article".equals(type);
//		Writing writing = null;
//		Partition p = Partition.getPartition(partition);
//		if (p == LearningArticle.PARTITION) {
//			writing = bool ? new LearningArticle() : new LearningPosts();
//		} else if (p == MajorArticle.PARTITION) {
//			writing = bool ? new MajorArticle() : new MajorPosts();
//		} else if (p == CollegeArticle.PARTITION) {
//			writing = bool ? new CollegeArticle() : new CollegePosts();
//		}
//		assert writing != null;
//		return writing;
//	}
//	/**
//	 * 根据分区和类型获取实例
//	 *
//	 * @param partition 分区
//	 * @param type      帖子 or 文章
//	 *
//	 * @return
//	 */
//	private <T extends Writing> ArticleService<T> getArticleOrPost(String partition, String type, T t) {
//		boolean bool = "article".equals(type);
//		Writing writing = null;
//		Partition p = Partition.getPartition(partition);
//		if (p == LearningArticle.PARTITION) {
//			writing = bool ? new LearningArticle() : new LearningPosts();
//
//			return (ArticleService<T>) ServiceFactory.getWritingService(t);
//
//		} else if (p == MajorArticle.PARTITION) {
//			writing = bool ? new MajorArticle() : new MajorPosts();
//		} else if (p == CollegeArticle.PARTITION) {
//			writing = bool ? new CollegeArticle() : new CollegePosts();
//		}
//		assert writing != null;
//		return writing;
//	}
}
