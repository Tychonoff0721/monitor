package com.example.dsl.metadata;

import com.example.dsl.ast.TargetScope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMetadataRepo implements MetadataRepo {
    
    private final JdbcTemplate jdbcTemplate;

    public JdbcMetadataRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Instance> instanceMapper = (rs, rowNum) -> new Instance(
            rs.getString("instance_id"),
            rs.getString("cluster_name"),
            rs.getString("service_name"),
            rs.getString("component"),
            rs.getString("host"),
            rs.getInt("port"),
            rs.getString("role")
    );

    private final RowMapper<EndpointTemplate> templateMapper = (rs, rowNum) -> new EndpointTemplate(
            rs.getString("endpoint_key"),
            rs.getString("component"),
            rs.getString("protocol"),
            rs.getInt("port"),
            rs.getString("prefix")
    );

    @Override
    public EndpointTemplate findEndpointTemplate(String endpointKey) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM endpoint_templates WHERE endpoint_key = ?", 
                    templateMapper, 
                    endpointKey);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Instance findOneInstance(String service, String component, String role) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM instances WHERE service_name = ? AND component = ? AND role = ? LIMIT 1",
                    instanceMapper,
                    service, component, role);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Instance> findInstances(String service, String component) {
        return jdbcTemplate.query(
                "SELECT * FROM instances WHERE service_name = ? AND component = ?",
                instanceMapper,
                service, component);
    }

    @Override
    public List<Instance> resolveInstances(com.example.dsl.ast.Target target, String requiredComponent) {
        TargetScope scope = target.getScope();
        String name = target.getName().replace("\"", "");
        
        String sql = "SELECT * FROM instances WHERE component = ?";
        
        if (scope == TargetScope.CLUSTER) {
            sql += " AND cluster_name = '" + name + "'";
        } else if (scope == TargetScope.SERVICE) {
            if (name.contains("/")) {
                String[] parts = name.split("/");
                sql += " AND cluster_name = '" + parts[0] + "' AND service_name = '" + parts[1] + "'";
            } else {
                sql += " AND service_name = '" + name + "'";
            }
        }
        
        return jdbcTemplate.query(sql, instanceMapper, requiredComponent);
    }
}
