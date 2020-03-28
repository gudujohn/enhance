# plugins
### plugin-mybatis
    1. mybatis插件提供基础的curd
    2. mybatis插件提供通用的查询对象
    3. annotation定义模型
    
-  **使用说明** 
    1. 对于表模型约束主键：java类型:String;name:id(后续所有的模型都继承Model.java定义的基础模型)
    2. 使用此插件时需要定义Model、ModelMapper和相应的sql的provider

 **demo** 
```
//模型定义demo
@ModelMapping(tableName = "TestModel")//定义模型对应的表名
public class TestModel extends Model {
    private String name;
    @ModelColumn(columnName = "FOREIGNKEY_ID") //用来定义模型中与数据库中名称不一致的情况
    private String foreignKeyId
    @ModelIgnore //用来定义非模型属性
    private ForeignModel foreignModel;
    ...
}
//mapper定义demo
public interface TestModelMapper extends ModelMapper<TestModel> {
}
//sqlProvider //如果没有定制的查询可以不定义
public class TestModelSqlProvider extends SqlProvider {
}
```
 **QueryCriteria查询条件**
```
QueryCriteria queryCriteria = new QueryCriteria();
//queryCriteria.add(Restrictions.eq("name", name));
//queryCriteria.add(Restrictions.in("id", ids));
//queryCriteria.add(Restrictions.like("name", name));
//queryCriteria.add(Restrictions.in("id", ids));
//queryCriteria.add(Restrictions.and(Restrictions.eq("name", name), Restrictions.eq("name", name)));
//Restrictions这里提供了很多的查询条件有待发现哦
...
//queryCriteria.addAsc("name");
//排序定义
...
System.out.println(this.find(TestModel.class, queryCriteria, ordering));
``` 
 **基础curd接口定义** 
```
public interface ModelMapper<T> {
    /**
     * 新增
     * @param model
     * @return
     */
    @InsertProvider(type = SqlProvider.class, method = "create")
    Integer create(Model model);

    /**
     * 通过id删除
     * @param clazz
     * @param id
     * @return
     */
    @DeleteProvider(type = SqlProvider.class, method = "deleteById")
    Integer deleteById(Class<T> clazz, String id);
    /**
     * 全属性更新
     * @param model
     * @return
     */
    @UpdateProvider(type = SqlProvider.class, method = "update")
    Integer update(Model model);
    /**
     * 增量更新
     * @param model
     * @return
     */
    @UpdateProvider(type = SqlProvider.class, method = "updateSelective")
    Integer updateSelective(Model model);
    /**
     * 通过id查询
     * @param clazz
     * @param id
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "findById")
    T findById(Class<T> clazz, String id);
    /**
     * 通过ids查询
     * @param clazz
     * @param ids
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "findByIds")
    List<T> findByIds(Class<T> clazz, String[] ids);
    /**
     * 查询全部
     * @param clazz
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "findAll")
    List<T> findAll(Class<T> clazz);
    /**
     * 条件查询
     * @param clazz
     * @param queryCriteria
     * @param ordering
     * @return
     */
    @SelectProvider(type = SqlProvider.class, method = "find")
    List<T> find(Class<T> clazz, QueryCriteria queryCriteria, Ordering ordering);
}

```