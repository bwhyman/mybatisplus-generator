# mybabtis-plus-generator
### Introduction
mybabtis-plus-generator is a plugin of mybatis-generator-plugin for integrating and generating mybatis-plus/lombok/spring annotations.


### Properties
version, string, optional. The column name of the optimistic locker. Adding @Version annotation for the field.  
The column default value should be 0.
OptimisticLockerInnerInterceptor should be provided in spring container.  

never, string, optional. The column name which the value generated by the database, such as update_time/create_time. 
Adding @TableField(updateStrategy = FieldStrategy.NEVER) annotation for the field.  

serializable, boolean, optional. The Model would implement Serializable.   

builder, boolean, optional. Adding @Builder and @AllArgsConstructor

Model Examples

```java
@Setter
@Getter
@ToString
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    @TableField(updateStrategy = FieldStrategy.NEVER)
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
            <version>1.0.4</version>
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
    <commentGenerator type="com.github.bwhyman.mybatisplusgenerator.MyBatisPlusCommentGenerator">
        <!-- Optional. @Version -->
        <property name="version" value="version"/>
        <!-- Optional. @TableField(updateStrategy = FieldStrategy.NEVER)  -->
        <property name="never" value="create_time, update_time"/>
        <!-- Optional. serializable -->
        <property name="serializable" value="false"/>
        <!-- Optional. @builder -->
        <property name="builder" value="false"/>
    </commentGenerator>
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