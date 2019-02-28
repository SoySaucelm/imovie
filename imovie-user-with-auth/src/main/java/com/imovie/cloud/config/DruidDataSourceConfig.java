package com.imovie.cloud.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidDataSourceConfig implements EnvironmentAware {

    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    private Environment env;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    /**
     * DataSource
     *
     * @return data source
     */
    @Bean("dataSource")
    public DataSource dataSource() {
        return buildDataSource(buildDbProperties("spring.datasource.movie."));
    }

    private Map<String, Object> buildDbProperties(String prefix) {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, prefix);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", propertyResolver.getProperty("type"));
        map.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
        map.put("url", propertyResolver.getProperty("url"));
        map.put("username", propertyResolver.getProperty("username"));
        map.put("password", propertyResolver.getProperty("password"));
        return map;
    }


    /**
     * 创建DataSource
     *
     * @param dsMap
     * @return
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            Object type = dsMap.get("type");
            if (type == null)
                type = DATASOURCE_TYPE_DEFAULT;

            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);

            String driverClassName = dsMap.get("driver-class-name").toString();
            String url = dsMap.get("url").toString();
            String username = dsMap.get("username").toString();
            String password = dsMap.get("password").toString();

            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * ServletRegistrationBean,
     *
     * @return
     * @see com.alibaba.druid.support.http.ResourceServlet
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean druid = new ServletRegistrationBean();
        druid.setServlet(new StatViewServlet());
        druid.setUrlMappings(Arrays.asList("/druid/*"));
        Map<String, String> params = new HashMap<>();
        params.put("loginUsername", "admin");
        params.put("loginPassword", "admin");
        druid.setInitParameters(params);
        return druid;
    }

    /**
     * @return
     * @see WebStatFilter
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean fitler = new FilterRegistrationBean();
        fitler.setFilter(new WebStatFilter());
        fitler.setUrlPatterns(Arrays.asList("/*"));
        fitler.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return fitler;
    }

}
