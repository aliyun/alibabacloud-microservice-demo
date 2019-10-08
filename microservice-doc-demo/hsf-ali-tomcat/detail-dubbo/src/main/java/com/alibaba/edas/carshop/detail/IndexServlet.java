package com.alibaba.edas.carshop.detail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.edas.carshop.itemcenter.Item;
import com.alibaba.edas.carshop.itemcenter.ItemService;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = -112210702214857712L;

	@Override
	public void doGet( HttpServletRequest req, HttpServletResponse resp )
		throws IOException {

		final ItemService itemService = ( ItemService )
			StartListener.CONTEXT.getBean( "item" );

		Item item = itemService.getItemByName(
			req.getParameter("name"));

		PrintWriter writer = resp.getWriter();
		writer.write(String.format("Item Id: %s, Name: %s",
			item.getItemId(), item.getItemName() ));
	}
	
	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
		throws ServletException, IOException {
	}

}
