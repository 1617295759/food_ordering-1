package web.servlet.Order;

import dao.MealDao;
import dao.OrderDao;
import domain.Meal;
import domain.Order;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/queryOrderServlet")
public class QueryOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String orderID = request.getParameter("orderID");
        String userID = request.getParameter("userID");
        //3.封装Order对象
        Order tempOrder = new Order();
        tempOrder.setOrderID(orderID);
        tempOrder.setUserID(userID);
        //4.调用OrderDao的queryOrder方法
        OrderDao dao = new OrderDao();
        Order order = dao.queryOrder(tempOrder);
        //5.判断order是否查询成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(order == null){
        //订单查询失败
            System.out.println("订单查询失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "订单查询失败！");
        }else{
        //订单查询成功
            System.out.println("订单查询成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "订单查询成功！");
            jsonObject.put("order",order);
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
