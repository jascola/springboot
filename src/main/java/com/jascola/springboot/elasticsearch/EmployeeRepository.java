package com.jascola.springboot.elasticsearch;

import com.jascola.model.entity.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


/*Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。只要你定义一个接口，然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。

在继承各种Repository（JPA、Elasticsearch等相关）接口时，spring boot的启动器和接口的相对位置要注意。
启动器处于根目录中，XXRepository处于对应的子目录下，此时是可以被成功注入。
如果将启动器移动到其他平行目录或者子目录，就算使用scanBasePackages指定扫描目录也无法将XXRepository成功注入，会产生错误。
*/
@Repository
public interface EmployeeRepository extends ElasticsearchRepository<Employee,String> {
    Employee queryEmployeeById(String id);
}
