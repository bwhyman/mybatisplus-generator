package com.github.bwhyman.mybatisplusgenerator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class MyBatisPlusPluginAdapter extends PluginAdapter {
    private Path mapperTargetPackagePath;

    @Override
    public boolean validate(List<String> list) {
        String baseD = System.getProperty("user.dir");
        String modelTargetProject = context.getJavaModelGeneratorConfiguration().getTargetProject();
        String modelTargetPackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        String mapperTargetProject = context.getJavaClientGeneratorConfiguration().getTargetProject();
        String mapperTargetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();
        try {
            Path modelTargetPackagePath =
                    Path.of(baseD).resolve(Path.of(modelTargetProject)).resolve(Path.of(modelTargetPackage.replace(
                    ".", "/")));
            mapperTargetPackagePath =
                    Path.of(baseD).resolve(Path.of(mapperTargetProject)).resolve(Path.of(mapperTargetPackage.replace(
                            ".", "/")));
            Files.createDirectories(modelTargetPackagePath);
            Files.createDirectories(mapperTargetPackagePath);
        } catch (IOException e) {
            log.error("create package error.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addImportedType("com.baomidou.mybatisplus.annotation.TableName");
        topLevelClass.addAnnotation("@TableName(\"" + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() +
                "\")");
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String fileName = interfaze.getType().getShortName();
        String extName = ".java";
        if (Files.exists(mapperTargetPackagePath.resolve(Path.of(fileName + extName)))) {
            log.info("{} exists.", fileName);
            return false;
        }
        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        interfaze.addAnnotation("@Repository");
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        interfaze.addAnnotation("@Mapper");

        String baseRecord = introspectedTable.getBaseRecordType();
        interfaze.addImportedType(new FullyQualifiedJavaType(baseRecord));

        interfaze.addImportedType(new FullyQualifiedJavaType("com.baomidou.mybatisplus.core.mapper.BaseMapper"));
        String generic = baseRecord.substring(baseRecord.lastIndexOf(".") + 1);
        String ex = "BaseMapper<" + generic + ">";
        FullyQualifiedJavaType f = new FullyQualifiedJavaType(ex);
        interfaze.addSuperInterface(f);
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        String version = "version";
        if (field.getName().toLowerCase().equals(version)) {
            topLevelClass.addImportedType("com.baomidou.mybatisplus.annotation.Version");
            field.addAnnotation("@Version");
        }
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicCountMethodGenerated(Method method, Interface interfaze,
                                                   IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze,
                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze,
                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMultipleMethodGenerated(Method method, Interface interfaze,
                                                            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicInsertMultipleHelperMethodGenerated(Method method, Interface interfaze,
                                                                  IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectManyMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicSelectOneMethodGenerated(Method method, Interface interfaze,
                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze,
                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                               IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMultipleMethodGenerated(Method method, Interface interfaze,
                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralCountMethodGenerated(Method method, Interface interfaze,
                                                     IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralDeleteMethodGenerated(Method method, Interface interfaze,
                                                      IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralSelectDistinctMethodGenerated(Method method, Interface interfaze,
                                                              IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralSelectMethodGenerated(Method method, Interface interfaze,
                                                      IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralUpdateMethodGenerated(Method method, Interface interfaze,
                                                      IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectListFieldGenerated(Field field, Interface interfaze,
                                                  IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectOneMethodGenerated(Method method, Interface interfaze,
                                                  IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateAllColumnsMethodGenerated(Method method, Interface interfaze,
                                                         IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateSelectiveColumnsMethodGenerated(Method method, Interface interfaze,
                                                               IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                     IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                         IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                          IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                          IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                   IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                      IntrospectedTable introspectedTable) {
        return super.providerSelectByExampleWithoutBLOBsMethodGenerated(method, topLevelClass, introspectedTable);
    }

    @Override
    public boolean providerUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                   IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                   IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                      IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                      IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }
}
