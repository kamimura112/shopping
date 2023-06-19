
package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Driver;

public class BeanCartDB extends BeanShopDB {
	private String id;
	private String name;
	private String comment;
	private int price;
	private int stock;
	private String photo;
	private int buy;
	private String after;

	public BeanCartDB() {
	} // 中身が空のコンストラクタが必要である。これが無いとJSPの<jsp:useBean>などでエラーが出る

	public BeanCartDB(String id, String name, String comment, int price, int stock, String photo, int buy,
			String after) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.price = price;
		this.stock = stock;
		this.photo = photo;
		this.buy = buy;
		this.after = after;
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

	public int getBuy() {
		return buy;
	}

	public String getAfter() {
		return after;
	}

	public List<BeanCartDB> DBtoList(String toId, String stringBuy, String value) {
		List<BeanCartDB> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			Driver.class.getDeclaredConstructor().newInstance();
			con = DriverManager.getConnection("jdbc:mariadb://localhost/studyDB1", "root", "");

			if (stringBuy == null) {
				stringBuy = "0";
			}

			int toBuy = Integer.parseInt(stringBuy);

			String sql = "update cart set buy = buy + ? where id = ?";//追加購入
			ps = con.prepareStatement(sql);
			ps.setInt(1, toBuy);
			ps.setString(2, toId);

			if ("delete".equals(value)) { //キャンセル
				sql = "delete from cart where id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, toId);

			}

			if ("later".equals(value)) {
				sql = "update cart set after = 'later' where id = ?"; //後で買うリストへ
				ps = con.prepareStatement(sql);
				ps.setString(1, toId);
			}

			if ("cart".equals(value)) {
				sql = "update cart set after = null where id = ?"; //カートに戻す
				ps = con.prepareStatement(sql);
				ps.setString(1, toId);
			}

			int count = ps.executeUpdate();

			if (count == 0) {
				sql = "insert into cart value(?, ?, null)"; //新規購入
				ps = con.prepareStatement(sql);
				ps.setString(1, toId);
				ps.setInt(2, toBuy);
				ps.executeUpdate();
			}
			sql = "select shop.id, name, comment, price, stock, photo, buy, after from shop join cart on shop.id = cart.id";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String comment = rs.getString("comment");
				int price = rs.getInt("price");
				int stock = rs.getInt("stock");
				String photo = rs.getString("photo");
				int buy = rs.getInt("buy");
				String after = rs.getString("after");
				list.add(new BeanCartDB(id, name, comment, price, stock, photo, buy, after));
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
