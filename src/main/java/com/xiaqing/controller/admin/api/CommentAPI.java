package com.xiaqing.controller.admin.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaqing.model.CommentModel;
import com.xiaqing.model.UserModel;
import com.xiaqing.service.ICommentService;
import com.xiaqing.utils.HttpUtil;
import com.xiaqing.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/api-admin-comment"})
public class CommentAPI extends HttpServlet {
	
	@Inject
	private ICommentService commentService;

	private static final long serialVersionUID = -915988021506484384L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		CommentModel commentModel =  HttpUtil.of(request.getReader()).toModel(CommentModel.class);
		commentModel = commentService.save(commentModel);
		mapper.writeValue(response.getOutputStream(), commentModel);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		CommentModel updateComment =  HttpUtil.of(request.getReader()).toModel(CommentModel.class);
		updateComment.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
		updateComment = commentService.update(updateComment);
		mapper.writeValue(response.getOutputStream(), updateComment);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		CommentModel commentModel =  HttpUtil.of(request.getReader()).toModel(CommentModel.class);
		commentService.delete(commentModel.getIds());
		mapper.writeValue(response.getOutputStream(), "{}");
	}
}

