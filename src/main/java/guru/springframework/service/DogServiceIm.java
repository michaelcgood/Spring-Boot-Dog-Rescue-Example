package guru.springframework.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class DogServiceIm implements DogService {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private long generatedKey;
	private String rescuedstring;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void addADog(String name, Date rescued, Boolean vaccinated){
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("INSERT INTO dog(name,rescued,vaccinated)VALUES(?,?,?)",name,rescued,vaccinated );
		
	}
	
	public void deleteADOG(String name, Long id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DELETE FROM dog WHERE name='"+name+"' AND id="+id);
	}
	
	public List<String> atriskdogs(Date rescued){
		String sql = "SELECT * FROM dog WHERE rescued < '"+ rescued+"' AND vaccinated = '0'";
		List<String> dogList = new ArrayList<String>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			public List extractData(ResultSet rs) throws SQLException {
				
				while (rs.next()) {
					String name = rs.getString("name");
					dogList.add(name);
				}
				return dogList;
			}
		}
				
		
	);
	System.out.println("doglist");
	return dogList;	
	}

	public long getGeneratedKey(String name, Date rescued, Boolean vaccinated) {
		String sql ="INSERT INTO dog(name,rescued,vaccinated)VALUES(?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		KeyHolder holder = new GeneratedKeyHolder();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		rescuedstring = formatter.format(rescued);
		System.out.println(rescuedstring);
		java.sql.Date rescuedsql = java.sql.Date.valueOf(rescuedstring);
		System.out.println(rescuedsql);
		jdbcTemplate.update(new PreparedStatementCreator() {
		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			PreparedStatement statement = connection.prepareStatement(sql.toString(),
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, name);
			statement.setDate(2, rescuedsql );
			statement.setBoolean(3, vaccinated);
			return statement;
		}
		}, holder);
		generatedKey = holder.getKey().longValue();
		System.out.println("generated key is " + generatedKey);
		return generatedKey;
	}

}
