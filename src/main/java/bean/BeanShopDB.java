
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Driver;

public class BeanShopDB {
	private String id;
	private String name;
	private String comment;
	private int price;
	private int stock;
	private String photo;

	public BeanShopDB() {
	} // 中身が空のコンストラクタが必要である。これが無いとJSPの<jsp:useBean>などでエラーが出る

	public BeanShopDB(String id, String name, String comment, int price, int stock, String photo) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.price = price;
		this.stock = stock;
		this.photo = photo;
	}

	public String getId() {
		return id;
	} // getterという。フィールドの値を返すだけだが、JSPから頻繁に利用される

	public String getName() {
		return name;
	} // getter

	public String getComment() {
		return comment;
	} // getter

	public int getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public String getPhoto() {
		return photo;
	}

	public List<BeanShopDB> DBtoList(String text, String order) {
		List<BeanShopDB> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Driver.class.getDeclaredConstructor().newInstance();
			con = DriverManager.getConnection("jdbc:mariadb://localhost/studyDB1", "root", "");

			String sql = "select id, name, comment, price, stock, photo from shop where name like ? order by price";
			if ("desc".equals(order)) {
				sql = "select id, name, comment, price, stock, photo from shop where name like ? order by price desc";
			}
			ps = con.prepareStatement(sql);
			ps.setString(1, ("%" + text + "%"));
			if (text == null) {
				ps.setString(1, "%" + "" + "%");
			}
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String comment = rs.getString("comment");
				int price = rs.getInt("price");
				int stock = rs.getInt("stock");
				String photo = rs.getString("photo");
				list.add(new BeanShopDB(id, name, comment, price, stock, photo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
