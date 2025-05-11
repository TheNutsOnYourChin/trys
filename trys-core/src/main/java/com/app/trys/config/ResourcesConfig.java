package com.app.trys.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author linjf
 * @since 2023/3/22
 */
@Getter
@Configuration
public class ResourcesConfig {

	@Value("${resources.path-images:/images}")
	private String imagesPath;
	@Value("${resources.path-views:/views}")
	private String viewsPath;
}
