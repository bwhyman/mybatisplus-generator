# mybabtis-plus-generator
### Introduction
mybabtis-plus-generator is a plugin of mybatis-generator-plugin for integrating and generating mybatis-plus/lombok/spring annotations.

Model Examples
```java
@Data
@NoArgsConstructor
@TableName("user")
public class User {
    private Long id;
    private String name;
    private LocalDateTime updateTime;
    @Version
    private Integer version;
}
```
Mapper Examples
```java
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
```
### Notice
Set mybatis-generator-plugin overwrite is true. It will not generate mapper but model only.  
It will not generate mybatis-plus Primarykey annotation, which you should set it up in springboot properties.   
It will clean mappers' methods, because interface BaseMapper provide the common CURD operation.  

### Configuration
pom.xml
```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.0</version>
    <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>com.github.bwhyman</groupId>
            <artifactId>mybatisplus-generator</artifactId>
            <version>1.0</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>{mysql.version}</version>
        </dependency>
    </dependencies>
</plugin>
```
generatorConfig.xml
```xml
<context id="context" targetRuntime="MyBatis3">
    <plugin type="com.github.bwhyman.mybatisplusgenerator.MyBatisPlusPluginAdapter" />
    <commentGenerator type="com.github.bwhyman.mybatisplusgenerator.MyBatisPlusCommentGenerator" />
    <!-- database config -->
    ...........
    <javaTypeResolver>
        <property name="useJSR310Types" value="true" />
    </javaTypeResolver>
    <javaModelGenerator
        targetPackage="com.example.your-project.your-module.entity"
        targetProject="src/main/java" />

    <javaClientGenerator
            type="ANNOTATEDMAPPER"
            targetPackage="com.example.your-project.your-module.mapper"
            targetProject="src/main/java" />
    <table tableName="%" />
</context>
```