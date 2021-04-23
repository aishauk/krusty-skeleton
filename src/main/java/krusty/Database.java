package krusty;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;

import static krusty.Jsonizer.anythingToJson;
import static krusty.Jsonizer.toJson;

public class Database {
	/**
	 * Modify it to fit your environment and then use this string when connecting to your database!
	 */
	private static final String jdbcString = "jdbc:mysql://localhost/krusty";

	// For use with MySQL or PostgreSQL
	private static final String jdbcUsername = "lth";
	private static final String jdbcPassword = "123456";
	private Connection connection;

	public void connect() {
		// Connect to database here
		try {

			connection = DriverManager.getConnection(jdbcString,jdbcUsername,jdbcPassword) ;
		}
		catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	// TODO: Implement and change output in all methods below!

	public String getCustomers(Request req, Response res) {
		String customers="{}";
		PreparedStatement ps = null;
		try{
			String sql = "select * from customers";
			ps = connection.prepareStatement(sql);
			customers=toJson(ps.executeQuery(),"customers"); 

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public String getRawMaterials(Request req, Response res) {

		String rawMaterials="{}";
		PreparedStatement ps = null;
		try{
			String sql = "select * from RawMaterials";
			ps = connection.prepareStatement(sql);
			rawMaterials=toJson(ps.executeQuery(),"raw-materials");

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rawMaterials;
	}

	public String getCookies(Request req, Response res) {

		String cookies="{}";
		PreparedStatement ps = null;
		try{
			String sql = "select * from Cookies";
			ps = connection.prepareStatement(sql);
			cookies=toJson(ps.executeQuery(),"cookies");

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cookies;
	}

	public String getRecipes(Request req, Response res) {

		String recipes="{}";
		PreparedStatement ps = null;
		try{
			String sql = "select cookie, raw_material, Recipes.amount AS amount, RawMaterials.unit AS unit" +
					" from Recipes,RawMaterials where RawMaterials.name=Recipes.raw_material";
			ps = connection.prepareStatement(sql);
			recipes=toJson(ps.executeQuery(),"recipes");

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipes;
	}

	public String getPallets(Request req, Response res) {
		try{
			StringBuilder sb= new StringBuilder();
			sb.append("select id, cookie, production_date, customer, IF(isBlocked,'yes','no') AS blocked\n" +
					"from Pallets\n" +
					"left outer join Orders on Pallets.order_nbr= Orders.order_nbr\n");

			Map<String,String> map=new HashMap<>();
			if(req.queryParams("from")!=null) {
				map.put("production_date >=",req.queryParams("from"));
			}
			if(req.queryParams("to")!=null) {
				map.put("production_date <=", req.queryParams("to"));
			}
			if(req.queryParams("cookie")!=null) {
				map.put("cookie = ",req.queryParams("cookie"));
			}
			if(req.queryParams("blocked")!=null) {

				if(req.queryParams("blocked").equals("yes")){
					map.put("isBlocket = ","true");

				} else map.put("isBlocket = ","false");;
			}
			
			if(!map.isEmpty()) {
				sb.append("where ");

				Iterator<Entry<String, String>> entries = map.entrySet().iterator();
				if (entries.hasNext()) {
					Entry<String, String> entry = entries.next();
					sb.append(entry.getKey()+" \""+entry.getValue()+"\" ");
				}
				while(entries.hasNext()) {
					sb.append(" and ");
					Entry<String, String> entry = entries.next();
					sb.append(entry.getKey()+" \""+entry.getValue()+"\" ");
				}
			}
			sb.append("\n"
					+ "order by production_date DESC;");
			
			//System.out.println(sb.toString());

			PreparedStatement ps= connection.prepareStatement(sb.toString());
			ResultSet rs=ps.executeQuery();

			return toJson(rs,"pallets");

		}catch (SQLException e) {
			e.printStackTrace();
		}
		return "{}";
		
	}

	public String reset(Request req, Response res) {
		try {
			Statement stmt = connection.createStatement();

			InputStream resourceAsStream = getClass().getResource("/reset.sql").openStream();
			if (resourceAsStream == null)
				throw new IOError(new IOException("Could not find reset.sql"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
			reader.lines()
			.filter(line -> !line.trim().startsWith("--") || !line.trim().isEmpty())
			.forEach(line -> {
				try {
					stmt.addBatch(line.trim());
				} catch (SQLException e) {
					System.out.println("Line: " + line);
					e.printStackTrace();
				}
			});

			stmt.executeBatch();
			stmt.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Could not open reset file.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "{}";
	}

	public String createPallet(Request req, Response res) {
		PreparedStatement ps;
		if (req.queryParams("cookie") != null) {
			String cookie=req.queryParams("cookie");
			List<String> cookies = new ArrayList<>();

			try {
				String sql = " select * from Cookies";
				ps = connection.prepareStatement(sql);
				ResultSet rs= ps.executeQuery(sql);
				while(rs.next()){
					cookies.add(rs.getString("name"));
				}

				if (!cookies.contains(cookie))
					return anythingToJson("unknown cookie", "status");

				//update raw materials  according to the recipe of the cookie
				updateRawMaterials(cookie);
				
				// create pallet
				String query="insert into Pallets(cookie, production_date, isBlocked) values('"+cookie+"', '"
							+java.sql.Date.valueOf(java.time.LocalDate.now())+"', false)";
				PreparedStatement ps2= connection.prepareStatement(query);
				ps2.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
				ResultSet rs1 = ps2.getGeneratedKeys();
					
				while(rs.next()){
					return"{\n" +
							"  \"status\": \"ok\",\n" +
							"  \"id\": "+rs.getInt("1")+"\n" +
							"}";
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return anythingToJson("error", "status");
	}

	private void updateRawMaterials(String cookie) {
		try {
			String sql="select * from Recipes where cookie=?";
			PreparedStatement ps= connection.prepareStatement(sql);
			ps.setString(1,cookie);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				String rawMaterial= rs.getString("raw_material");
				int amount= rs.getInt("amount");
				amount*=54;
				String query="update RawMaterials set amount= amount-?" +
						" where name=?;";
				PreparedStatement ps2= connection.prepareStatement(query);
				ps2.setInt(1,amount);
				ps2.setString(2,rawMaterial);
				ps2.executeUpdate();
			}
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
}
