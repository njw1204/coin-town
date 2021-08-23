package kr.njw.springstudy1.repository.impl;

import kr.njw.springstudy1.model.Member;
import kr.njw.springstudy1.repository.MemberRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id")
                .usingColumns("email", "password", "name");

        Map<String, Object> params = new HashMap<>();
        params.put("email", member.getEmail());
        params.put("password", member.getPassword());
        params.put("name", member.getName());

        Number id = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        member.setId(id.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM member WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(Member.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build());
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (rs != null) try { rs.close(); } catch (Exception ignored) {}
            if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
            if (conn != null) try { this.releaseConnection(conn); } catch (Exception ignored) {}
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "SELECT * FROM member WHERE name = ?";

        return this.jdbcTemplate.query(
                sql,
                (rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .build(),
                name
        ).stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";

        return this.jdbcTemplate.query(sql, (rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .name(rs.getString("name"))
                .build()
        );
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(this.dataSource);
    }

    private void releaseConnection(Connection conn) {
        DataSourceUtils.releaseConnection(conn, this.dataSource);
    }
}
